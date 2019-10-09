package com.boclips.kalturaclient.media;

import com.boclips.kalturaclient.Flavor;
import com.boclips.kalturaclient.http.HttpClient;
import com.boclips.kalturaclient.http.RequestFilters;
import com.boclips.kalturaclient.media.resources.MediaListResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlavorListClient {
    private final HttpClient client;

    public FlavorListClient(HttpClient client) {
        this.client = client;
    }

    public List<Flavor> get() {
        return null;
    }

}
