package code_up.project.shared.exception;

import code_up.project.shared.enums.ErrorCode;

public final class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(ErrorCode.VALIDATION_ERROR, message);
    }
}