package com.beestock.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

/**
 * AmazonTranscoderCredentials receives the AWS credentials through environment variables.
 * It gives a provider that holds that AWS credentials.
 */
public class AmazonTranscoderCredentials {
    private static final String AWS_ACCESS_KEY_ID      = System.getenv("ACCESS_KEY_ID");
    private static final String AWS_SECRET_ACCESS_KEY  = System.getenv("SECRET_ACCESS_KEY");

    /**
     *
     * @return an object that hold the AWSCredentials object
     */
    public static AWSCredentialsProvider getCredentialsProvider () {
        return new AWSCredentialsProvider() {
            public AWSCredentials getCredentials() {
                return getAWSCredentials();
            }

            public void refresh() {}
        };
    }

    /**
     *
     * @return an object with getters for AWSAccessKeyId and AWSSecretKey
     */
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
