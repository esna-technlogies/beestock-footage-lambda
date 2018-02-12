package com.beestock.enums;

public enum ResponseStatus {
    VALIDATION_JOB_CREATED(
        "SUCCESS",
        "Footage Lambda: Validation Job has been created successfully."
    ),

    TRANSCODE_JOB_CREATED(
        "SUCCESS",
        "Footage Lambda: Transcode Job has been created successfully."
    ),

    UNKNOWN_RESULT(
        "FAILED",
        "Footage Lambda: Unknown Failure"
    ),

    UNSUPPORTED_FORMAT(
        "FAILED",
        "Footage Lambda: Footage format is not supported"
    );



    private final String status;
    private final String message;

    ResponseStatus (String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus () {
        return this.status;
    }

    public String getMessage () {
        return this.message;
    }
}
