package code_up.project.shared.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;


@Schema(description = "Standard API success response")
@JsonPropertyOrder({"success", "code", "message", "data", "meta", "timestamp", "trace_id"})
public record ApiResponse<T>(
        @Schema(description = "Indicates success", example = "true")
        boolean success,

        @Schema(description = "Business code", example = "SUCCESS")
        String code,

        @Schema(description = "Human-readable message", example = "User fetched successfully")
        String message,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Schema(description = "Response payload")
        T data,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Schema(description = "Pagination or additional metadata")
        Object meta,

        @Schema(description = "Server response timestamp", example = "2026-05-09T10:00:00Z")
        Instant timestamp
) {

    /**
     * Create a success response with data.
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(
                true,
                "SUCCESS",
                message,
                data,
                null,
                Instant.now()
        );
    }

    /**
     * Create a success response with data and pagination meta.
     */
    public static <T> ApiResponse<T> success(T data, Object meta, String message) {
        return new ApiResponse<>(
                true,
                "SUCCESS",
                message,
                data,
                meta,
                Instant.now()
        );
    }

    /**
     * Create a success response with no data (e.g., delete).
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(
                true,
                "SUCCESS",
                message,
                null,
                null,
                Instant.now()
        );
    }
}
