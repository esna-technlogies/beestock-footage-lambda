package com.beestock.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

import static com.beestock.config.EnvironmentVars.AWS_ACCESS_KEY_ID;
import static com.beestock.config.EnvironmentVars.AWS_SECRET_ACCESS_KEY;

public class TranscoderCredentials {

    public static AWSCredentialsProvider getCredentialsProvider () {
        return new AWSCredentialsProvider() {
            public AWSCredentials getCredentials() {
                return getAWSCredentials();
            }

            public void refresh() {}
        };
    }

    private static AWSCredentials getAWSCredentials() {
        return new AWSCredentials() {
            public String getAWSAccessKeyId() {
                return AWS_ACCESS_KEY_ID;
            }

            public String getAWSSecretKey() {
                return AWS_SECRET_ACCESS_KEY;
            }
        };
    }
}
