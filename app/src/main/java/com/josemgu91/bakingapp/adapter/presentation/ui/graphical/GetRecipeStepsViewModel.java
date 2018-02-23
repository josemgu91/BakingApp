/*
 * MIT License
 *
 * Copyright (c) 2018 José Miguel García Urrutia.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetRecipeStepsViewModel that = (GetRecipeStepsViewModel) o;

        return steps != null ? steps.equals(that.steps) : that.steps == null;
    }

    @Override
    public int hashCode() {
        return steps != null ? steps.hashCode() : 0;
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
    }

}
