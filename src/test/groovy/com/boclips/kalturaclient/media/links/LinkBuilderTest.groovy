package com.boclips.kalturaclient.media.links

import com.boclips.kalturaclient.Flavor
import com.boclips.kalturaclient.KalturaClientConfig
import com.boclips.kalturaclient.Quality
import com.boclips.kalturaclient.media.streams.StreamFormat
import spock.lang.Specification

class LinkBuilderTest extends Specification {

    private LinkBuilder linkBuilder
    private KalturaClientConfig config

    def "setup"() {
        config = KalturaClientConfig.builder()
                .partnerId("partner1")
                .userId("user")
                .secret("secret")
                .streamFlavorParamIds(Arrays.asList(
                        new Flavor(Quality.LOW, "1"),
                        new Flavor(Quality.MEDIUM, "2"),
                        new Flavor(Quality.HIGH, "3"),
                ))
                .build()
        linkBuilder = new LinkBuilder(config)
    }

    def "can build thumbnail urls"() {
        given:
        String entryId = "media-entry-id"

        when:
        String thumbnailUrl = linkBuilder.getThumbnailUrl(entryId)

        then:
        thumbnailUrl.contains("entry_id/media-entry-id")
        thumbnailUrl.contains("width/{thumbnailWidth}")
    }

    def "can build video preview urls"() {
        given:
        String entryId = "media-entry-id"

        when:
        String thumbnailUrl = linkBuilder.getVideoPreviewUrl(entryId)

        then:
        thumbnailUrl.contains("entry_id/media-entry-id")
        thumbnailUrl.contains("width/{thumbnailWidth}")
        thumbnailUrl.contains("vid_slices/{thumbnailCount}")
    }

    def "can build hls stream urls"() {
        given:
        String entryId = "media-entry-id"
        StreamFormat streamTechnique = StreamFormat.APPLE_HDS

        when:
        String hlsStream = linkBuilder.getStreamUrl(entryId, streamTechnique)

        then:
        hlsStream.contains("entryId/media-entry-id")
        hlsStream.contains("format/" + streamTechnique.code)
        hlsStream.contains("flavorParamIds/1,2,3")
    }
}
