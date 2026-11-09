package code_up.project.shared.enums;

import lombok.Getter;

/**
 * Enterprise error codes.
 * These codes are stable and should NEVER be changed or removed.
 * New codes can be added as needed.
 */
@Getter

public enum ErrorCode {

    // ─── General ───────────────────────────────────────────────
    SUCCESS("SUCCESS"),
    INTERNAL_ERROR("INTERNAL_ERROR"),
    VALIDATION_ERROR("VALIDATION_ERROR"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"),
    UNAUTHORIZED("UNAUTHORIZED"),
    FORBIDDEN("FORBIDDEN"),
    METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED"),
    RATE_LIMIT_EXCEEDED("RATE_LIMIT_EXCEEDED"),

    // ─── Authentication ────────────────────────────────────────
    INVALID_CREDENTIALS("INVALID_CREDENTIALS"),
    TOKEN_EXPIRED("TOKEN_EXPIRED"),
    TOKEN_INVALID("TOKEN_INVALID"),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED"),

    // ─── Business Errors ───────────────────────────────────────
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS"),
    INSUFFICIENT_BALANCE("INSUFFICIENT_BALANCE"),
    ORDER_ALREADY_CANCELLED("ORDER_ALREADY_CANCELLED"),
    DUPLICATE_RESOURCE("DUPLICATE_RESOURCE"),
    INVALID_STATE_TRANSITION("INVALID_STATE_TRANSITION"),

    // ─── Validation ────────────────────────────────────────────
    REQUIRED("REQUIRED"),
    INVALID_FORMAT("INVALID_FORMAT"),
    MIN_LENGTH("MIN_LENGTH"),
    MAX_LENGTH("MAX_LENGTH"),
    INVALID_EMAIL_FORMAT("INVALID_EMAIL_FORMAT"),
    OUT_OF_RANGE("OUT_OF_RANGE");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}

