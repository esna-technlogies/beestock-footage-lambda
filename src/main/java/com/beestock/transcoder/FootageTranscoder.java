package com.beestock.transcoder;

import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoder;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClient;
import com.amazonaws.services.elastictranscoder.AmazonElasticTranscoderClientBuilder;
import com.amazonaws.services.elastictranscoder.model.CreateJobOutput;
import com.amazonaws.services.elastictranscoder.model.CreateJobRequest;
import com.amazonaws.services.elastictranscoder.model.JobInput;

import com.beestock.config.TranscoderCredentials;
import com.beestock.model.FootageFile;
import com.beestock.enums.AwsPresets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.beestock.config.EnvironmentVars.*;
import static com.beestock.enums.AwsPresets.*;


public class FootageTranscoder {
    private final FootageFile footageFile;
    private final List<AwsPresets> supportPresets = Arrays.asList(
            GENERIC_720P, GENERIC_480P_16_9, IPHONE4S, WEB, FULL_HD_1080I60, WEBM_720P);

    public FootageTranscoder(FootageFile file) {
        this.footageFile = file;
    }


    public void createValidationJob() { buildTranscoderClient().createJob(getValidationJobRequest()); }

    public void createTranscodeJob() {
        buildTranscoderClient().createJob(getTranscodeJobRequest());
    }


    private AmazonElasticTranscoder buildTranscoderClient () {
        AmazonElasticTranscoderClientBuilder transcoderClientBuilder = AmazonElasticTranscoderClient.builder();
        transcoderClientBuilder.setRegion(TRANSCODER_REGION);
        transcoderClientBuilder.setCredentials(TranscoderCredentials.getCredentialsProvider());

        return transcoderClientBuilder.build();
    }

    private CreateJobRequest getTranscodeJobRequest() {
        CreateJobRequest jobRequest = new CreateJobRequest();

        jobRequest.setPipelineId(PIPELINE_ID);
        jobRequest.setInput(getTranscodeJobInput());
        jobRequest.setOutputs(getTranscodeJobOutputs());

        return jobRequest;
    }

    private JobInput getTranscodeJobInput() {
        JobInput input = new JobInput();
        input.setKey(this.footageFile.getFileName() + ".mp4");

        return input;
    }

    private Collection<CreateJobOutput> getTranscodeJobOutputs() {
        Collection<CreateJobOutput> outputs = new ArrayList<>();

        supportPresets.forEach(awsPresets -> {
            CreateJobOutput output = new CreateJobOutput();

            output.withPresetId(awsPresets.getPresetId());
            output.withKey(this.footageFile.getFileName()
                    + "-" + awsPresets.getSuffix()
                    + "." + awsPresets.getExtension());

            outputs.add(output);
        });

        return outputs;
    }


    private CreateJobRequest getValidationJobRequest() {
        CreateJobRequest jobRequest = new CreateJobRequest();

        jobRequest.setPipelineId(VALIDATION_PIPELINE_ID);
        jobRequest.setInput(getValidationJobInput());
        jobRequest.setOutput(getValidationJobOutput());

        return jobRequest;
    }

    private JobInput getValidationJobInput() {
        JobInput input = new JobInput();
        input.setKey(this.footageFile.getFileName() + ".mp4");

        return input;
    }

    private CreateJobOutput getValidationJobOutput() {
        CreateJobOutput output = new CreateJobOutput();
        output.withKey(this.footageFile.getFileNameWithExtension());
        output.withPresetId(LowestQuality.getPresetId());

        return output;
    }
}
