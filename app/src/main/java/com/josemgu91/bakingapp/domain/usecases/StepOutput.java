package com.josemgu91.bakingapp.domain.usecases;

/**
 * Created by jose on 2/14/18.
 */

public class StepOutput {

    private final String shortDescription;
    private final String longDescription;
    private final String pictureUrl;
    private final String videoUrl;

    public StepOutput(String shortDescription, String longDescription, String pictureUrl, String videoUrl) {
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.pictureUrl = pictureUrl;
        this.videoUrl = videoUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
