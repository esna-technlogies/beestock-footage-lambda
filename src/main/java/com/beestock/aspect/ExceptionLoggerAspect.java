package com.beestock.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * ExceptionLoggerAspect defines pointcuts and advices for exceptions thrown in any step of the Lambda function FootageLambdaApp::handleRequest
 */
@Aspect
public class ExceptionLoggerAspect {
    /**
     * A pointcut matches assertFootageFileIsSupported() method with any number of arguments
     */
    @Pointcut("execution( * assertFootageFileIsSupported(..) )")
    public void assertFootageFileIsSupported() {
    }

    /**
     * An advices runs whenever an exception at assertFootageFileIsSupported() pointcut
     * @param error an object holds the exception object
     */
    @AfterThrowing(value = "assertFootageFileIsSupported()", throwing = "error")
    public void checkingIfFileIsSupported(Throwable error) {
        printLog(error.getMessage());
    }


    /**
     * A helper method to use for logging a standard error message
     * @param message the error message that will be logged
     */
    private void printLog(String message) {
        System.out.println(String.format("BEESTOCK-ERROR: %s", message));
    }
}
