package com.beestock.config;

import lombok.Data;

@Data
public class EnvironmentVars {
    public static final String TRANSCODER_REGION            = System.getenv("TRANSCODER_REGION");
    public static final String AWS_ACCESS_KEY_ID            = System.getenv("ACCESS_KEY_ID");
    public static final String AWS_SECRET_ACCESS_KEY        = System.getenv("SECRET_ACCESS_KEY");
    public static final String AWS_REGION                   = System.getenv("REGION");
    public static final String PIPELINE_ID                  = System.getenv("PIPELINE_ID");
    public static final String VALIDATION_PIPELINE_ID       = System.getenv("VALIDATION_PIPELINE_ID");
    public static final String BUCKET_FOOTAGE_UPLOADS       = System.getenv("BUCKET_FOOTAGE_UPLOADS");
    public static final String BUCKET_FOOTAGE_VALIDATED     = System.getenv("BUCKET_FOOTAGE_VALIDATED");
    public static final String BUCKET_FOOTAGE_TRANSCODED    = System.getenv("BUCKET_FOOTAGE_TRANSCODED");
}
