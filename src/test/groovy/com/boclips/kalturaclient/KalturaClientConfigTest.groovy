package com.boclips.kalturaclient

import spock.lang.Specification

class KalturaClientConfigTest extends Specification {

    def 'builder creates a config'() {
        when:
        KalturaClientConfig config = KalturaClientConfig.builder()
                .partnerId("partner-id")
                .userId("user-id")
                .secret("secret")
                .baseUrl("common://kaltura.com/api")
                .sessionTtl(120)
                .streamFlavorParamIds(Arrays.asList(new Flavor(Quality.MEDIUM, "2"), new Flavor(Quality.LOW, "1")))
                .build()

        then:
        config.partnerId == "partner-id"
        config.userId == "user-id"
        config.secret == "secret"
        config.baseUrl == "common://kaltura.com/api"
        config.sessionTtl == 120
        config.streamFlavorParamIds.size() == 2
        config.streamFlavorParamIds.get(0).getQuality() == Quality.MEDIUM
        config.streamFlavorParamIds.get(0).getId() == "2"
        config.streamFlavorParamIds.get(1).getQuality() == Quality.LOW
        config.streamFlavorParamIds.get(1).getId() == "1"
    }

    def 'throws when userId is blank'() {
        when:
        KalturaClientConfig.builder()
                .partnerId("partner id")
                .userId("")
                .secret("secret")
                .streamFlavorParamIds(Arrays.asList(new Flavor(Quality.LOW, "1")))
                .build()

        then:
        KalturaClientConfigException ex = thrown()
        ex.message == "Invalid user id: []"
    }

    def 'throws when partnerId is blank'() {
        when:
        KalturaClientConfig.builder()
                .partnerId("")
                .userId("user")
                .secret("secret")
                .streamFlavorParamIds(Arrays.asList(new Flavor(Quality.LOW, "1")))
                .build()

        then:
        KalturaClientConfigException ex = thrown()
        ex.message == "Invalid partner id: []"
    }

    def 'throws when secret is blank'() {
        when:
        KalturaClientConfig.builder()
                .partnerId("partnerid")
                .userId("user")
                .secret("")
                .streamFlavorParamIds(Arrays.asList(new Flavor(Quality.LOW, "1")))
                .build()

        then:
        KalturaClientConfigException ex = thrown()
        ex.message == "Invalid secret: []"
    }

    def 'throws when streamFlavorParamIds is null'() {
        when:
        KalturaClientConfig.builder()
                .partnerId("partnerid")
                .userId("user")
                .secret("secret")
                .streamFlavorParamIds()
                .build()

        then:
        KalturaClientConfigException ex = thrown()
        ex.message == "streamFlavorParamIds cannot be null"
    }

    def 'throws when streamFlavorParamIds is an empty list'() {
        when:
        KalturaClientConfig.builder()
                .partnerId("partnerid")
                .userId("user")
                .secret("secret")
                .streamFlavorParamIds(Collections.emptyList())
                .build()

        then:
        KalturaClientConfigException ex = thrown()
        ex.message == "streamFlavorParamIds cannot be empty"
    }

}
