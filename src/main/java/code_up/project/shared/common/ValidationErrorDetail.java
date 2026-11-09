package code_up.project.shared.common;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Field-level validation error detail")
public record ValidationErrorDetail(
        @Schema(description = "Error code for programmatic handling", example = "REQUIRED")
        String code,

        @Schema(description = "Human-readable error message", example = "Email is required")
        String message
) {}