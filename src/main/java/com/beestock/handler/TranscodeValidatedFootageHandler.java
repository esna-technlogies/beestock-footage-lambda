package com.beestock.handler;

import com.beestock.awsClients.FootageS3;
import com.beestock.models.FootageFile;
import com.beestock.transcoder.TranscodingPipelineTranscoder;

import static com.beestock.config.EnvironmentVars.BUCKET_FOOTAGE_UPLOADS;

/**
 * This handler class will be responsible for handling any request for transcoding a validated footage file
 */
public class TranscodeValidatedFootageHandler {
    public static void handle(FootageFile footageFile) {

        FootageS3.copyToUnapprovedFilesBucket(footageFile, BUCKET_FOOTAGE_UPLOADS);

        TranscodingPipelineTranscoder.createJob(footageFile);
    }
}