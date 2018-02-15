package com.beestock.models;

import org.apache.commons.io.FilenameUtils;

import static com.amazonaws.services.s3.event.S3EventNotification.S3Entity;

/**
 * This is a model that represents a footage file.
 *
 * It holds information like: bucketName, name, extension, directory.
 */
public class FootageFile {
    private String bucketName;
    private String name;
    private String extension;
    private String directory;


    public FootageFile(S3Entity s3) {
        String fileNameWithExtension = s3.getObject().getKey();

        this.bucketName = s3.getBucket().getName();
        this.name = FilenameUtils.getBaseName(fileNameWithExtension);
        this.extension = FilenameUtils.getExtension(fileNameWithExtension);
        this.directory = FilenameUtils.getFullPathNoEndSeparator(fileNameWithExtension);
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    public String getDirectory() {
        return directory;
    }
}
