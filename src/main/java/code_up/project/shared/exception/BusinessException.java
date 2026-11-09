package code_up.project.shared.exception;


import code_up.project.shared.enums.ErrorCode;

/**
 * Base sealed exception for all business exceptions.
 * Only permitted subclasses can extend this.
 */
public sealed class BusinessException extends RuntimeException
        permits DuplicateResourceException, InvalidStateTransitionException, ResourceNotFoundException, ValidationException {

    private final ErrorCode errorCode;
    private final transient Object[] args;

    public BusinessException(ErrorCode errorCode, String message, Object... args) {
        super(message);
        this.errorCode = errorCode;
        this.args = args;
    }

    public BusinessException(ErrorCode errorCode, String message, Throwable cause, Object... args) {
        super(message, cause);
        this.errorCode = errorCode;
        this.args = args;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Object[] getArgs() {
        return args;
    }
}
