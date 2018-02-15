package com.beestock.transcoder;

import com.amazonaws.services.elastictranscoder.model.*;
import com.beestock.awsClients.FootageTranscoder;
import com.beestock.enums.AWSTranscoderPresets;
import com.beestock.models.FootageFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.beestock.config.EnvironmentVars.*;

public class TranscodingPipelineTranscoder {
    private final static List<String> supportedOutputFormatList = Arrays.asList(SUPPORTED_OUTPUT_FORMATS.split(","));


    public static void createJob(FootageFile footageFile) {
        FootageTranscoder.createJob(
                new CreateJobRequest()
                        .withPipelineId(PIPELINE_ID)
                        .withInput(getTranscodeJobInput(footageFile))
                        .withOutputs(getTranscodeJobOutputs(footageFile))
        );
    }

    private static JobInput getTranscodeJobInput(FootageFile footageFile) {
        String inputKey = String.format(
                "%s/original.%s",
                footageFile.getDirectory(),
                footageFile.getExtension());

        return new JobInput().withKey(inputKey);
    }

    private static Collection<CreateJobOutput> getTranscodeJobOutputs(FootageFile footageFile) {
        Collection<CreateJobOutput> outputs = new ArrayList<>();

        supportedOutputFormatList.forEach(format -> {
            AWSTranscoderPresets preset = AWSTranscoderPresets.valueOf(format.toUpperCase());

            String outputKey = String.format(
                    "%s/%s.%s",
                    footageFile.getDirectory(),
                    preset.getSuffix(),
                    preset.getExtension());

            outputs.add(new CreateJobOutput()
                    .withPresetId(preset.getPresetId())
                    .withKey(outputKey)
                    .withWatermarks(getJobWatermark()));
        });

        return outputs;
    }

    private static JobWatermark getJobWatermark() {
        return new JobWatermark()
                .withInputKey(WATERMARK_INPUT_KEY)
                .withPresetWatermarkId(WATERMARK_ID);
    }
}
