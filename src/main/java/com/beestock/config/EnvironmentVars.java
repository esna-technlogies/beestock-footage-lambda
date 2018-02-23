package com.beestock.config;

/**
 * A class with static fields that hold environment variables being passed from AWS console
 */
public class EnvironmentVars {
    public static final String REGION                       = System.getenv("REGION");
    public static final String PIPELINE_ID                  = System.getenv("PIPELINE_ID");
    public static final String VALIDATION_PIPELINE_ID       = System.getenv("VALIDATION_PIPELINE_ID");
    public static final String BUCKET_UNAPPROVED_FILES      = System.getenv("BUCKET_UNAPPROVED_FILES");
    public static final String SUPPORTED_INPUT_FORMATS      = System.getenv("SUPPORTED_INPUT_FORMATS");
    public static final String SUPPORTED_OUTPUT_FORMATS     = System.getenv("SUPPORTED_OUTPUT_FORMATS");
    public static final String BUCKET_FOOTAGE_UPLOADS       = System.getenv("BUCKET_FOOTAGE_UPLOADS");
    public static final String BUCKET_FOOTAGE_VALIDATED     = System.getenv("BUCKET_FOOTAGE_VALIDATED");
    public static final String WATERMARK_ID                 = System.getenv("WATERMARK_ID");
    public static final String WATERMARK_INPUT_KEY          = System.getenv("WATERMARK_INPUT_KEY");
}
