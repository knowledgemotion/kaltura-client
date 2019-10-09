package com.boclips.kalturaclient.media

import com.boclips.kalturaclient.Flavor
import com.boclips.kalturaclient.http.HttpClient
import spock.lang.Specification

class FlavorListClientTest extends Specification {

    def "fetches all flavors from Kaltura"() {
        given:
        HttpClient httpClient = Mock(HttpClient)
        FlavorListClient client = new FlavorListClient(httpClient)

        when:
        List<Flavor> flavors = client.get()

        then:
        1 * httpClient.get() >> "???"
    }
}
