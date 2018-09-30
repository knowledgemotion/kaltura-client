package com.boclips.kalturaclient.http;

import com.boclips.kalturaclient.media.resources.MediaListResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jdk.incubator.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
public class HttpClient {
    private String baseUrl;
    private jdk.incubator.http.HttpClient httpClient;

    public HttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = jdk.incubator.http.HttpClient.newHttpClient();
    }

    public MediaListResource listMediaEntries(String sessionToken, RequestFilters filters) {
        try {
            URI uri = new URIBuilder()
                    .setHost(this.baseUrl)
                    .setPath("/api_v3/service/media/action/list")
                    .addParameter("ks", sessionToken)
                    .addParameter("format", "1")
                    .addParameter("filter[statusIn]", "-2,-1,0,1,2,4,5,6,7")
                    .addParameters(filters.toPairs())
                    .build();

            jdk.incubator.http.HttpResponse<byte[]> response = httpClient.send(HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build(), jdk.incubator.http.HttpResponse.BodyHandler.asByteArray());

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), MediaListResource.class);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException("Something went wrong");
        }
    }

    public MediaListResource listMediaEntriesOld(String sessionToken, RequestFilters filters) {
        try {
            HttpResponse<MediaListResource> response = Unirest.get(this.baseUrl + "/api_v3/service/media/action/list")
                    .queryString(filters.toMap())
                    .queryString("ks", sessionToken)
                    .queryString("format", "1")
                    .queryString("filter[statusIn]", "-2,-1,0,1,2,4,5,6,7")
                    .asObject(MediaListResource.class);

            log.debug("/action/list returned: {} with body {}", response.getStatus(), response);

            MediaListResource mediaListResource = response.getBody();
            if (!ResponseObjectType.isSuccessful(mediaListResource.objectType)) {
                throw new UnsupportedOperationException(String.format("Error in Kaltura request: %s", mediaListResource.code));
            }

            return mediaListResource;
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMediaEntry(String sessionToken, String referenceId) {
        try {
            final HttpResponse<String> response = Unirest.post(this.baseUrl + "/api_v3/service/media/action/add")
                    .queryString("ks", sessionToken)
                    .queryString("format", "1")
                    .queryString("entry[mediaType]", 1)
                    .queryString("entry[objectType]", "KalturaMediaEntry")
                    .queryString("entry[referenceId]", referenceId)
                    .asString();

            log.debug("/action/add returned: {} with body {}", response.getStatus(), response);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    public void deleteMediaEntryByReferenceId(String sessionToken, String referenceId) {
        try {
            final HttpResponse<String> response = Unirest.post(this.baseUrl + "/api_v3/service/media/action/delete")
                    .queryString("ks", sessionToken)
                    .queryString("format", "1")
                    .queryString("entryId", referenceId)
                    .asString();

            log.debug("/action/delete returned: {} with body {}", response.getStatus(), response);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
}
