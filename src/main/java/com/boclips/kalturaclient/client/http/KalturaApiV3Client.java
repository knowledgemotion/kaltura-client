package com.boclips.kalturaclient.client.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.List;

public class KalturaApiV3Client {
    private String baseUrl;

    public KalturaApiV3Client(String baseUrl) {
        this.baseUrl = baseUrl;

        configureUniRest();
    }

    public List<MediaEntryResource> getMediaActionList(String sessionToken, List<String> referenceIds) {
        try {
            MediaListResource mediaListResource = Unirest.get(this.baseUrl + "/api_v3/service/media/action/list")
                    .queryString("ks", sessionToken)
                    .queryString("filter[referenceIdIn]", String.join(",", referenceIds))
                    .queryString("format", "1")
                    .asObject(MediaListResource.class)
                    .getBody();

            if (!ResponseObjectType.isSuccessful(mediaListResource.objectType)) {
                throw new UnsupportedOperationException(String.format("Error in Kaltura request: %s", mediaListResource.code));
            }

            return mediaListResource.objects;
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private void configureUniRest() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            {
                jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            }

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}