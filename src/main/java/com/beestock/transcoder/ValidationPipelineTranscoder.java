package com.beestock.transcoder;

import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.JobInput;
import com.beestock.awsClients.FootageTranscoder;
import com.beestock.models.FootageFile;

import static com.beestock.config.EnvironmentVars.*;
import static com.beestock.enums.AWSTranscoderPresets.LowestQuality;

public class ValidationPipelineTranscoder {

    public static void createJob(FootageFile footageFile) {
        FootageTranscoder.createJob(
                new CreateJobRequest()
                        .withPipelineId(VALIDATION_PIPELINE_ID)
                        .withInput(getValidationJobInput(footageFile))
                        .withOutputs(getValidationJobOutput(footageFile))
        );
    }

    private static JobInput getValidationJobInput(FootageFile footageFile) {
        String jobKey = String.format(
                "%s/%s.%s",
                footageFile.getDirectory(),
                footageFile.getName(),
                footageFile.getExtension());

        return new JobInput().withKey(jobKey);
    }

    private static CreateJobOutput getValidationJobOutput(FootageFile footageFile) {
        String outputKey = String.format(
                "%s/%s.%s",
                footageFile.getDirectory(),
                footageFile.getName(),
                footageFile.getExtension());

        return new CreateJobOutput()
                .withPresetId(LowestQuality.getPresetId())
                .withKey(outputKey);
    }
}
