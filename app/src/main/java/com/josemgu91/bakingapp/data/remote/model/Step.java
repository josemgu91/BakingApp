package com.josemgu91.bakingapp.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jose on 2/15/18.
 */

public class Step {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("shortDescription")
    @Expose
    public String shortDescription;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("videoURL")
    @Expose
    public String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    public String thumbnailURL;

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }
}
