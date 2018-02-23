package com.beestock.awsClients;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.beestock.config.AmazonTranscoderCredentials;
import com.beestock.models.FootageFile;

import static com.beestock.config.EnvironmentVars.BUCKET_UNAPPROVED_FILES;
import static com.beestock.config.EnvironmentVars.REGION;

/**
 * FootageS3 class handles operations on AWS S3 service (ex: copyObject, deleteObject, etc...)
 */
public class FootageS3 {

    private static final AmazonS3ClientBuilder clientBuilder = getS3ClientBuilderWithSettings();

    /**
     * Returns an AmazonS3ClientBuilder with required AWS region and AWS credentials
     * @return the Amazon S3 client builder
     */
    private static AmazonS3ClientBuilder getS3ClientBuilderWithSettings() {
        return AmazonS3Client.builder()
                .withRegion(REGION)
                .withCredentials(AmazonTranscoderCredentials.getCredentialsProvider());
    }

    /**
     * It copies a footageFile from sourceBucketName to a destination bucket for un approved files.
     * It assumes a destination bucket name provided as environment variable.
     * @param footageFile a FootageFile object containing information about directory, name, extension
     * @param sourceBucketName the source bucket from which the footageFile will be copied
     */
    public static void copyToUnapprovedFilesBucket(FootageFile footageFile, String sourceBucketName) {
        String sourceKey = String.format(
                "%s/%s.%s",
                footageFile.getDirectory(),
                footageFile.getName(),
                footageFile.getExtension()
        );

        String destinationKey = String.format(
                "%s/original.%s",
                footageFile.getDirectory(),
                footageFile.getExtension()
        );

        copyObject(sourceBucketName, sourceKey, BUCKET_UNAPPROVED_FILES, destinationKey);
        deleteObject(sourceBucketName, sourceKey);
    }

    /**
     * This function just uses AmazonS3ClientBuilder class configured with required settings (ex: credentials) to perform copyObject operation
     * @param sourceBucketName the source bucket from which the bucket object will be copied
     * @param sourceKey this is the path with the filename of the bucket object under the source bucket
     * @param destinationBucketName the destination bucket where the bucket object will be copied
     * @param destinationKey this is the path with the filename of the bucket object under the destination bucket
     */
    private static void copyObject(String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey) {
        clientBuilder.build()
                .copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
    }

    /**
     * This function just uses AmazonS3ClientBuilder class configured with required settings (ex: credentials) to perform deleteObject operation
     * @param sourceBucketName the source bucket from which the bucket object will be copied
     * @param sourceKey this is the path with the filename of the bucket object under the source bucket
     */
    private static void deleteObject(String sourceBucketName, String sourceKey) {
        clientBuilder.build()
                .deleteObject(sourceBucketName, sourceKey);
    }
}
