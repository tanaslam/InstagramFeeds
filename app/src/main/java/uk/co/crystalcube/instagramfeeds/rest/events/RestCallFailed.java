package uk.co.crystalcube.instagramfeeds.rest.events;

/**
 * <p>
 * The event that indicates that REST call returned error.
 * </p>
 *
 * @author tanny
 *         Created: 08/09/15.
 */
public class RestCallFailed extends RestCallComplete {

    private int statusCode = 404;
    private String error;

    /**
     * Contracts object with default 404 code.
     *
     * @param callType REST end-point type
     */
    public RestCallFailed(CallType callType) {
        super(callType, false);
    }

    /**
     * Contracts object with default custom error code and message.
     *
     * @param callType   The call type
     * @param statusCode HTTP status code returned from end-point
     * @param error      The custom error code
     */
    public RestCallFailed(CallType callType, int statusCode, String error) {
        this(callType);
        this.statusCode = statusCode;
        this.error = error;
    }

}
