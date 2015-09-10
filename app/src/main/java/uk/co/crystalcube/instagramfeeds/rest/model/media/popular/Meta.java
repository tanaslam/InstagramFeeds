
package uk.co.crystalcube.instagramfeeds.rest.model.media.popular;

import com.google.gson.annotations.Expose;

public class Meta {

    @Expose
    private Long code;

    /**
     * 
     * @return
     *     The code
     */
    public Long getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(Long code) {
        this.code = code;
    }

    public Meta withCode(Long code) {
        this.code = code;
        return this;
    }

}
