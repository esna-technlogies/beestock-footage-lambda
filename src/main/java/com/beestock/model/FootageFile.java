package com.beestock.model;

import lombok.Data;
import org.apache.commons.io.FilenameUtils;

import static com.amazonaws.services.s3.event.S3EventNotification.S3Entity;

@Data
public class FootageFile {
    private String bucketName;
    private String fileName;
    private String fileExtension;
    private String fileNameWithExtension;

    public FootageFile(S3Entity s3) {
        this.bucketName = s3.getBucket().getName();
        this.fileNameWithExtension = s3.getObject().getKey();
        this.fileExtension = FilenameUtils.getExtension(this.fileNameWithExtension);
        this.fileName = FilenameUtils.getBaseName(this.fileNameWithExtension);
    }
}
