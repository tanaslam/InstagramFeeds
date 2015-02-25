
package uk.co.crystalcube.instagramfeeds.model.media.popular;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Likes {

    @Expose
    private Long count;
    @Expose
    private List<Datum> data = new ArrayList<Datum>();

    /**
     * 
     * @return
     *     The count
     */
    public Long getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Long count) {
        this.count = count;
    }

    public Likes withCount(Long count) {
        this.count = count;
        return this;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Likes withData(List<Datum> data) {
        this.data = data;
        return this;
    }

}
