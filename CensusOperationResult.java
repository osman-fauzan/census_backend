import java.util.List;

/**
 * Wrapper for backend operation results.
 */
public class CensusOperationResult {
    private final boolean success;
    private final String message;
    private final List<?> data;

    public CensusOperationResult(boolean success, String message, List<?> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<?> getData() {
        return data;
    }
}