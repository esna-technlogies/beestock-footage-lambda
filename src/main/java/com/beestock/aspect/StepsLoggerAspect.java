package com.beestock.aspect;

import com.beestock.models.FootageFile;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * StepsLoggerAspect defines pointcuts and advices for steps being done through executing the Lambda function FootageLambdaApp::handleRequest
 */
@Aspect
public class StepsLoggerAspect {
    /**
     * A pointcut matches handleRequest() method of com.beestock.FootageLambdaApp class with any number of arguments
     */
    @Pointcut("execution( * com.beestock.FootageLambdaApp.handleRequest(..) )")
    public void handleRequest() {
    }

    /**
     * A pointcut matches handle() method of com.beestock.handler.ValidateUploadedFootageHandler class with one argument of any type
     */
    @Pointcut("execution( void com.beestock.handler.ValidateUploadedFootageHandler.handle(*))")
    public void validateUploadedFootageHandler() {
    }

    /**
     * A pointcut matches handle() method of com.beestock.handler.TranscodeValidatedFootageHandler class with one argument of any type
     */
    @Pointcut("execution( void com.beestock.handler.TranscodeValidatedFootageHandler.handle(*))")
    public void transcodeValidatedFootageHandler() {
    }

    /**
     * A pointcut matches handle() method of any class under com.beestock.handler package with an argument of type com.beestock.models.FootageFile
     */
    @Pointcut("execution( * com.beestock.handler.*.handle(com.beestock.models.FootageFile))")
    public void handleFootageFileArgument() {
    }

    /**
     * A pointcut matches createJob() method of com.beestock.awsClients.FootageTranscoder class with any number of arguments
     */
    @Pointcut("execution( void com.beestock.awsClients.FootageTranscoder.createJob(*))")
    public void createTranscodeJob() {
    }

    /**
     * An advice that runs before handleRequest() pointcut
     */
    @Before("handleRequest()")
    public void handleRequestStarted() {
        printLog("Lambda Function Started");
    }

    /**
     * An advice that runs before validateUploadedFootageHandler() pointcut
     */
    @Before("validateUploadedFootageHandler()")
    public void handleValidateUploadedFootage() {
        printLog("Handle Validate-Uploaded-Footage Request");
    }

    /**
     *  An advice that runs before handleFootageFileArgument() pointcut
     * @param joinPoint this holds the join point of the pointcut
     */
    @Before("handleFootageFileArgument()")
    public void logFootageFileDetails(JoinPoint joinPoint) {
        FootageFile footageFile = (FootageFile) joinPoint.getArgs()[0];

        printLog("Footage File: " + String.format(
                "{ Bucket: %s, Directory: %s, Filename: %s.%s }",
                footageFile.getBucketName(),
                footageFile.getDirectory(),
                footageFile.getName(),
                footageFile.getExtension()));
    }

    /**
     *  An advice that runs before transcodeValidatedFootageHandler() pointcut
     */
    @Before("transcodeValidatedFootageHandler()")
    public void handleTranscodeValidatedFootage() {
        printLog("Handle Transcode-Validated-Footage Request");
    }

    /**
     * An advice that runs after createTranscodeJob() pointcut
     */
    @After("createTranscodeJob()")
    public void transcodeJobCreated() {
        printLog("A Transcoder Job has been create successfully.");
    }

    /**
     *An advice that runs after handleRequest() pointcut
     */
    @After("handleRequest()")
    public void handleRequestFinished() {
        printLog("Lambda Function Finished");
    }

    /**
     * A helper method to use for logging a standard info message
     * @param message the info message that will be logged
     */
    private void printLog(String message) {
        System.out.println(String.format("BEESTOCK-LOG: %s", message));
    }
}
