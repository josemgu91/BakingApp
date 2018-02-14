package com.josemgu91.bakingapp.domain.entities;

/**
 * Created by jose on 2/14/18.
 */

public class Step {

    private final String shortDescription;
    private final String longDescription;
    private final String pictureUrl;
    private final String videoUrl;

    public Step(String shortDescription, String longDescription, String pictureUrl, String videoUrl) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Step step = (Step) o;

        if (shortDescription != null ? !shortDescription.equals(step.shortDescription) : step.shortDescription != null)
            return false;
        if (longDescription != null ? !longDescription.equals(step.longDescription) : step.longDescription != null)
            return false;
        if (pictureUrl != null ? !pictureUrl.equals(step.pictureUrl) : step.pictureUrl != null)
            return false;
        return videoUrl != null ? videoUrl.equals(step.videoUrl) : step.videoUrl == null;
    }

    @Override
    public int hashCode() {
        int result = shortDescription != null ? shortDescription.hashCode() : 0;
        result = 31 * result + (longDescription != null ? longDescription.hashCode() : 0);
        result = 31 * result + (pictureUrl != null ? pictureUrl.hashCode() : 0);
        result = 31 * result + (videoUrl != null ? videoUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Step{" +
                "shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}
