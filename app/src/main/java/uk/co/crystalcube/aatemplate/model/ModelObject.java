package uk.co.crystalcube.aatemplate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.androidannotations.annotations.EBean;

/**
 * Created by tanny on 04/02/15.
 */
@EBean
public class ModelObject {

    @Expose @SerializedName("id")
    private String id;

    @Expose @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
