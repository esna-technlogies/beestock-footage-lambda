package com.beestock.handler;

import com.beestock.transcoder.ValidationPipelineTranscoder;
import com.beestock.models.FootageFile;

/**
 * This handler class will be responsible for handling any request for validating uploaded footage file
 */
public class ValidateUploadedFootageHandler {
    public static void handle(FootageFile footageFile) {
        ValidationPipelineTranscoder.createJob(footageFile);
    }
}
