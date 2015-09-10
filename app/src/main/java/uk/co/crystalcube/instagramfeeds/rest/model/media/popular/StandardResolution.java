
package uk.co.crystalcube.instagramfeeds.rest.model.media.popular;

import com.google.gson.annotations.Expose;

public class StandardResolution {

    @Expose
    private String url;
    @Expose
    private Long width;
    @Expose
    private Long height;

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public StandardResolution withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 
     * @return
     *     The width
     */
    public Long getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(Long width) {
        this.width = width;
    }

    public StandardResolution withWidth(Long width) {
        this.width = width;
        return this;
    }

    /**
     * 
     * @return
     *     The height
     */
    public Long getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(Long height) {
        this.height = height;
    }

    public StandardResolution withHeight(Long height) {
        this.height = height;
        return this;
    }

}
