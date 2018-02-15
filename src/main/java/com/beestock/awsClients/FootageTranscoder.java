package com.beestock.awsClients;

import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClientBuilder;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.beestock.config.AmazonTranscoderCredentials;

import static com.beestock.config.EnvironmentVars.REGION;

/**
 * FootageTranscoder creates a transcoder job using Amazon transcoder.
 * It uses AWS credentials provided by AmazonTranscoderCredentials class.
 */
public class FootageTranscoder {

    private static final AmazonElasticTranscoderClientBuilder clientBuilder = getTranscoderClientBuilderWithSettings();

    /**
     * This creates a transcoder job for the jobRequest being received
     * @param jobRequest
     */
    public static void createJob(CreateJobRequest jobRequest) {
        clientBuilder.build()
                .createJob(jobRequest);
    }

    /**
     * This creates a builder for Amazon elastic transcoder with the required credentials
     * @return
     */
    private static AmazonElasticTranscoderClientBuilder getTranscoderClientBuilderWithSettings() {
        return AmazonElasticTranscoderClient.builder()
                .withRegion(REGION)
                .withCredentials(AmazonTranscoderCredentials.getCredentialsProvider());
    }
}
