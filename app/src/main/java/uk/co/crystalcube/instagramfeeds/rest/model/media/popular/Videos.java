
package uk.co.crystalcube.instagramfeeds.rest.model.media.popular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Videos {

    @SerializedName("low_bandwidth")
    @Expose
    private LowBandwidth lowBandwidth;
    @SerializedName("standard_resolution")
    @Expose
    private StandardResolution standardResolution;
    @SerializedName("low_resolution")
    @Expose
    private LowResolution lowResolution;

    /**
     * 
     * @return
     *     The lowBandwidth
     */
    public LowBandwidth getLowBandwidth() {
        return lowBandwidth;
    }

    /**
     * 
     * @param lowBandwidth
     *     The low_bandwidth
     */
    public void setLowBandwidth(LowBandwidth lowBandwidth) {
        this.lowBandwidth = lowBandwidth;
    }

    public Videos withLowBandwidth(LowBandwidth lowBandwidth) {
        this.lowBandwidth = lowBandwidth;
        return this;
    }

    /**
     * 
     * @return
     *     The standardResolution
     */
    public StandardResolution getStandardResolution() {
        return standardResolution;
    }

    /**
     * 
     * @param standardResolution
     *     The standard_resolution
     */
    public void setStandardResolution(StandardResolution standardResolution) {
        this.standardResolution = standardResolution;
    }

    public Videos withStandardResolution(StandardResolution standardResolution) {
        this.standardResolution = standardResolution;
        return this;
    }

    /**
     * 
     * @return
     *     The lowResolution
     */
    public LowResolution getLowResolution() {
        return lowResolution;
    }

    /**
     * 
     * @param lowResolution
     *     The low_resolution
     */
    public void setLowResolution(LowResolution lowResolution) {
        this.lowResolution = lowResolution;
    }

    public Videos withLowResolution(LowResolution lowResolution) {
        this.lowResolution = lowResolution;
        return this;
    }

}
