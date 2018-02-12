package com.beestock.model;

import com.beestock.enums.ResponseStatus;
import lombok.Data;

@Data
public class Response {

    private String status;
    private String message;

    public Response (ResponseStatus responseStatus) {
        this.status = responseStatus.getStatus();
        this.message = responseStatus.getMessage();
    }
}
