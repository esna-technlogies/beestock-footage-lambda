package com.beestock.enums;

public enum AwsPresets {
    GENERIC_720P        ("1351620000001-000010", "generic-720p", "mp4"),
    GENERIC_480P_16_9   ("1351620000001-000010", "generic-480p-16_9", "mp4"),
    IPHONE4S            ("1351620000001-100020", "iphone4s", "mp4"),
    WEB                 ("1351620000001-100070", "web", "mp4"),
    FULL_HD_1080I60     ("1351620000001-100180", "full-hd-1080i60", "mp4"),
    WEBM_720P           ("1351620000001-100240", "webm-720p", "webm"),
    LowestQuality       ("1351620000001-000061", "generic-320x240", "mp4");

    private String suffix;
    private String presetId;
    private String extension;

    AwsPresets(String presetId, String suffix, String extension) {
        this.suffix = suffix;
        this.presetId = presetId;
        this.extension = extension;
    }

    public String getPresetId () { return this.presetId; }

    public String getSuffix() { return suffix; }

    public String getExtension() { return extension; }
}
