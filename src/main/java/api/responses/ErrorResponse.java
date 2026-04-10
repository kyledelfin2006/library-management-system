package api.responses;

public class ErrorResponse {
    private final String error;
    private String details;
    private final int statusCode;
    private final long timestamp;

    // Used when the response issues an error.
    public ErrorResponse(String error, int statusCode) {
        this.error = error;
        this.statusCode = statusCode;
        this.timestamp = System.currentTimeMillis();
    }

    public ErrorResponse(String error, String details, int statusCode) {
        this.error = error;
        this.details = details;
        this.statusCode = statusCode;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters
    public String getError() {
        return error;
    }

    public String getDetails() {
        return details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public long getTimestamp() {
        return timestamp;
    }
}