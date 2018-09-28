package com.boclips.kalturaclient;

import com.boclips.kalturaclient.media.MediaEntry;
import com.boclips.kalturaclient.session.RestSessionGenerator;
import com.boclips.kalturaclient.session.SessionRetriever;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface KalturaClient {
    static KalturaClient create(KalturaClientConfig config) {
        return new KalturaClientV3(config, new RestSessionGenerator(new SessionRetriever(config), config.getSessionTtl()));
    }

    Map<String, List<MediaEntry>> getMediaEntriesByReferenceIds(Collection<String> referenceIds);

    List<MediaEntry> getMediaEntriesByReferenceId(String referenceIds);

    void deleteMediaEntriesByReferenceId(String referenceId);

    void createMediaEntry(String referenceId);
}
