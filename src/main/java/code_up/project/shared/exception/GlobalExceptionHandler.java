package code_up.project.shared.exception;

import code_up.project.shared.common.ValidationErrorDetail;
import code_up.project.shared.enums.ErrorCode;
import code_up.project.shared.response.ApiError;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Global exception handler that converts all exceptions
 * into the standard ApiError format with proper HTTP status codes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ─── 1. Validation Errors → HTTP 422 ───────────────────────

    /**
     * Handles @Valid annotation validation failures.
     * Returns HTTP 422 with structured field-level errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, List<ValidationErrorDetail>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        LinkedHashMap::new,
                        Collectors.mapping(this::toValidationErrorDetail, Collectors.toList())
                ));

        ApiError response = ApiError.validationError("validation error", errors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    /**
     * Handles constraint violations on path variables, query params, etc.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, List<ValidationErrorDetail>> errors = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String code = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
            String msg = violation.getMessage();
            errors.computeIfAbsent(field, k -> new ArrayList<>())
                    .add(new ValidationErrorDetail(code.toUpperCase(), msg));
        });

        ApiError response = ApiError.validationError("constraint violation error", errors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    /**
     * Handles missing request parameters (e.g., required query params).
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParam(MissingServletRequestParameterException ex) {
        var error = new ValidationErrorDetail("REQUIRED", ex.getParameterName() + " is required");
        var errors = Map.of(ex.getParameterName(), List.of(error));
        ApiError response = ApiError.validationError("missing request params", errors);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    // ─── 2. Business Exceptions → HTTP 409 / 404 / 400 ────────

    /**
     * Handles ResourceNotFoundException → HTTP 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        ApiError response = ApiError.of(ex.getErrorCode().getCode(), "Resource not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles DuplicateResourceException → HTTP 409.
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleDuplicate(DuplicateResourceException ex) {
        log.warn("Duplicate resource: {}", ex.getMessage());
        ApiError response = ApiError.of(ex.getErrorCode().getCode(), "duplicate resource");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Handles InvalidStateTransitionException → HTTP 409.
     */
    @ExceptionHandler(InvalidStateTransitionException.class)
    public ResponseEntity<ApiError> handleInvalidState(InvalidStateTransitionException ex) {
        log.warn("Invalid state transition: {}", ex.getMessage());
        ApiError response = ApiError.of(ex.getErrorCode().getCode(), "invalid stateF");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Handles generic BusinessException → HTTP 400.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(BusinessException ex) {
        log.warn("Business exception: {} - {}", ex.getErrorCode(), ex.getMessage());
        ApiError response = ApiError.of(ex.getErrorCode().getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ─── 3. Authentication / Authorization → HTTP 401 / 403 ────

    /**
     * Handles authentication errors → HTTP 401.
     */
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied() {
        ApiError response = ApiError.of(ErrorCode.FORBIDDEN.getCode(), "access denied");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    // ─── 4. Unexpected Errors → HTTP 500 ───────────────────────

    /**
     * Handles all unhandled exceptions → HTTP 500.
     * NEVER leaks internal details to the client.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex) {
        log.error("Unexpected error occurred", ex);
        ApiError response = ApiError.of(ErrorCode.INTERNAL_ERROR.getCode(), "internal errorF");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // ─── 5. HTTP Message Parsing Errors → HTTP 400 ─────────────

    /**
     * Handles malformed JSON requests.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleMalformedJson(HttpMessageNotReadableException ex) {
        log.warn("Malformed request body: {}", ex.getMessage());
        ApiError response = ApiError.of(ErrorCode.VALIDATION_ERROR.getCode(), "Malformed request body");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles type mismatch errors (e.g., string instead of number).
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value for parameter '%s': '%s'".formatted(
                ex.getName(), ex.getValue());
        ApiError response = ApiError.of(ErrorCode.VALIDATION_ERROR.getCode(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ─── Private Helpers ───────────────────────────────────────

    private ValidationErrorDetail toValidationErrorDetail(FieldError fieldError) {
        String code = fieldError.getCode() != null ? fieldError.getCode().toUpperCase() : "INVALID";
        String message = fieldError.getDefaultMessage();
        return new ValidationErrorDetail(code, message);
    }
}
