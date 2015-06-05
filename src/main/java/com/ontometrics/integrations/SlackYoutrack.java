package com.ontometrics.integrations;

import com.ontometrics.integrations.configuration.ConfigurationFactory;
import com.ontometrics.integrations.configuration.EventProcessorConfiguration;
import com.ontometrics.integrations.configuration.YouTrackInstance;
import com.ontometrics.integrations.configuration.YouTrackInstanceFactory;
import com.ontometrics.integrations.jobs.InvalidConfigurationException;
import com.ontometrics.integrations.jobs.JobStarter;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlackYoutrack {

    private static Logger logger = LoggerFactory.getLogger(SlackYoutrack.class);

    private static JobStarter jobStarter;

    private static void checkConfiguration() throws InvalidConfigurationException {
        Configuration configuration = ConfigurationFactory.get();
        try {
            EventProcessorConfiguration.instance();
        } catch (Exception ex) {
            throw new InvalidConfigurationException("Could not initialize event processor configuration", ex);
        }
        YouTrackInstance youTrackInstance = null;
        try {
            youTrackInstance = YouTrackInstanceFactory.createYouTrackInstance(configuration);
        } catch (Exception ex) {
            throw new InvalidConfigurationException("Invalid YouTrack configuration, please check that URL is correct", ex);
        }
        try {
            youTrackInstance.getBaseUrl();
        } catch (Exception ex) {
            throw new InvalidConfigurationException("Invalid YouTrack base url", ex);
        }
        try {
            youTrackInstance.getFeedUrl();
        } catch (Exception ex) {
            throw new InvalidConfigurationException("Invalid YouTrack feed url", ex);
        }
    }


    public static void main(String ... args) {
        logger.info("Starting up, checking that configuration is correct");
        try {
            checkConfiguration();
            jobStarter = new JobStarter();
            jobStarter.scheduleTasks();
        } catch (Exception ex) {
            logger.error("Failed to initialize application", ex);
            throw ex;
        }

    }
}
