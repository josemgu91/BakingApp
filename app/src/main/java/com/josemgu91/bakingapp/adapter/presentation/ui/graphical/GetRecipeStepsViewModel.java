package com.josemgu91.bakingapp.adapter.presentation.ui.graphical;

import java.util.List;

/**
 * Created by jose on 2/16/18.
 */

public class GetRecipeStepsViewModel {

    private final List<Step> steps;

    public GetRecipeStepsViewModel(List<Step> steps) {
        this.steps = steps;
    }

    public List<Step> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "GetRecipeStepsViewModel{" +
                "steps=" + steps +
                '}';
    }

    public static class Step {

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
        public String toString() {
            return "Step{" +
                    "shortDescription='" + shortDescription + '\'' +
                    ", longDescription='" + longDescription + '\'' +
                    ", pictureUrl='" + pictureUrl + '\'' +
                    ", videoUrl='" + videoUrl + '\'' +
                    '}';
        }
    }

}
