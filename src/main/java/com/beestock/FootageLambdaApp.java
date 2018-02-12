package com.beestock;

import com.beestock.transcoder.FootageTranscoder;
import com.beestock.model.FootageFile;
import com.beestock.model.Response;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

import java.util.Collections;
import java.util.List;

import static com.beestock.enums.ResponseStatus.*;
import static com.beestock.config.EnvironmentVars.*;
import static com.amazonaws.services.s3.event.S3EventNotification.S3Entity;


public class FootageLambdaApp implements RequestHandler<S3Event, Response> {

    private final List<String> supportedFormats = Collections.singletonList("mp4");


    public static void main(String[]args){ System.out.println("Hello From Footage Lambda!"); }


    public Response handleRequest(S3Event s3Event, Context context){
        S3Entity s3 = s3Event.getRecords().get(0).getS3();

        FootageFile file = new FootageFile(s3);
        FootageTranscoder transcoder = new FootageTranscoder(file);

        Response response;

        if(!this.isFormatSupported(file)){
            response = new Response(UNSUPPORTED_FORMAT);

        } else if (s3.getBucket().getName().equals(BUCKET_FOOTAGE_UPLOADS)) {
            transcoder.createValidationJob();

            response = new Response(VALIDATION_JOB_CREATED);

        } else if (s3.getBucket().getName().equals(BUCKET_FOOTAGE_VALIDATED)) {
            transcoder.createTranscodeJob();

            response = new Response(TRANSCODE_JOB_CREATED);

        } else {
            response = new Response(UNKNOWN_RESULT);
        }

        System.out.println(response.getMessage());

        return response;
    }

    private boolean isFormatSupported(FootageFile file){
        return supportedFormats.contains(file.getFileExtension());
    }
}