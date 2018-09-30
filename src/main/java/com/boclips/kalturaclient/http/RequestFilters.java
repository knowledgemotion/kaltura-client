package com.boclips.kalturaclient.http;

import lombok.Getter;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestFilters {
    private List<RequestFilter> filters = new ArrayList();

    public RequestFilters() {
    }

    public RequestFilters add(String key, String value) {
        filters.add(new RequestFilter(key, value));
        return this;
    }

    public Map<String, Object> toMap() {
        return filters.stream().collect(Collectors.toMap(RequestFilter::getName, RequestFilter::getValue));
    }

    public List<NameValuePair> toPairs() {
        return new ArrayList<>(filters);
    }

    @Getter
    private class RequestFilter implements NameValuePair {
        private String name;
        private String value;

        RequestFilter(String key, String value) {
            this.name = key;
            this.value = value;
        }
    }
}
