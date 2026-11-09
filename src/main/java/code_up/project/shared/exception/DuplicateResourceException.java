package code_up.project.shared.exception;

import code_up.project.shared.enums.ErrorCode;

public final class DuplicateResourceException extends BusinessException {
    public DuplicateResourceException(String resourceType, String field, Object value) {
        super(ErrorCode.DUPLICATE_RESOURCE,
                "%s already exists with %s: %s".formatted(resourceType, field, value));
    }
}