package code_up.project.shared.response;

import code_up.project.shared.common.ValidationErrorDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Map;

/**
 * Standard error response for all API errors.
 * Follows enterprise standard with structured validation errors.
 */
@Schema(description = "Standard API error response")
@JsonPropertyOrder({"success", "code", "message", "errors", "timestamp", "trace_id"})
public record ApiError(
        @Schema(description = "Indicates failure", example = "false")
        boolean success,

        @Schema(description = "Error code", example = "VALIDATION_ERROR")
        String code,

        @Schema(description = "Human-readable error message", example = "Validation failed")
        String message,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @Schema(description = "Field-level validation errors")
        Map<String, java.util.List<ValidationErrorDetail>> errors,

        @Schema(description = "Server response timestamp", example = "2026-05-09T10:00:00Z")
        Instant timestamp
) {

    /**
     * Create a simple error response without field errors.
     */
    public static ApiError of(String code, String message) {
        return new ApiError(
                false,
                code,
                message,
                null,
                Instant.now()
        );
    }

    /**
     * Create a validation error response with field-level errors.
     */
    public static ApiError validationError(
            String message,
            Map<String, java.util.List<ValidationErrorDetail>> errors
    ) {
        return new ApiError(
                false,
                "VALIDATION_ERROR",
                message,
                errors,
                Instant.now()
        );
    }
}
