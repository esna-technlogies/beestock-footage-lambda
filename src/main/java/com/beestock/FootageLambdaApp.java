package com.beestock;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.beestock.exception.FileFormatNotSupportedException;
import com.beestock.handler.TranscodeValidatedFootageHandler;
import com.beestock.handler.ValidateUploadedFootageHandler;
import com.beestock.models.FootageFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.beestock.config.EnvironmentVars.*;

/**
 * The {@code FootageLambdaApp} class a request handler for AWS Lambda function called handleRequest().
 *
 * It assumes to handle an input of type S3Event and return an output of type String
 */
public class FootageLambdaApp implements RequestHandler<S3Event, String> {

    private static Map<String, Consumer<FootageFile>> requestHandlerMap = new HashMap<>();

    private static final List<String> supportedInputFormatList = Arrays.asList(SUPPORTED_INPUT_FORMATS.split(","));

    public static void main(String[] args) {  }

    /**
     *
     * @param s3Event an input event of type S3Event
     * @param context allows you to access useful information available within the Lambda execution environment
     * @return
     */
    public String handleRequest(S3Event s3Event, Context context) {
        loadRequestHandlers();

        FootageFile footageFile = new FootageFile(s3Event.getRecords().get(0).getS3());

        try {
            assertFootageFileIsSupported(footageFile);
        } catch (Exception e) {
            return e.getMessage();
        }

        runRequestHandler(footageFile);

        return "DONE";
    }

    /**
     * It loads requestHandlerMap object with the available footage handlers
     */
    private void loadRequestHandlers() {
        requestHandlerMap.put(BUCKET_FOOTAGE_UPLOADS, ValidateUploadedFootageHandler::handle);
        requestHandlerMap.put(BUCKET_FOOTAGE_VALIDATED, TranscodeValidatedFootageHandler::handle);
    }

    /**
     * It runs the request handler from requestHandlerMap based on the footageFile bucket name
     * @param footageFile an object containing information about directory, name, extension
     */
    private void runRequestHandler(FootageFile footageFile) {
        requestHandlerMap.get(footageFile.getBucketName()).accept(footageFile);
    }

    /**
     * This method holds all tests needed to be done on the footage file to be sure it matches the requirements
     * @param footageFile an object containing information about directory, name, extension
     * @throws FileFormatNotSupportedException throws a well message indicating the extension of footage file is note supported
     */
    private void assertFootageFileIsSupported(FootageFile footageFile) throws FileFormatNotSupportedException {
        if (!supportedInputFormatList.contains(footageFile.getExtension())) {
            throw new FileFormatNotSupportedException(footageFile.getExtension());
        }
    }
}