module com.boclips.kalturaclient {
    requires jdk.incubator.httpclient;
    requires static lombok;
    requires unirest.java;
    requires com.fasterxml.jackson.databind;
    requires httpclient;
    requires httpcore;

    exports com.boclips.kalturaclient;
}