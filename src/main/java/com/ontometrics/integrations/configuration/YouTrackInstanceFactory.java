package com.ontometrics.integrations.configuration;

import org.apache.commons.configuration.Configuration;

public class YouTrackInstanceFactory {
    private static final String YT_FEED_URL = "http://ontometrics.com:8085";

    public static YouTrackInstance createYouTrackInstance(Configuration configuration) {
        return new YouTrackInstance.Builder().baseUrl(
                configuration.getString("PROP.YOUTRACK_URL", YT_FEED_URL)).build();
    }

}
