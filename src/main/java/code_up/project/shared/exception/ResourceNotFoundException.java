package code_up.project.shared.exception;

import code_up.project.shared.enums.ErrorCode;

public final class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String resourceType, Object identifier) {
        super(ErrorCode.RESOURCE_NOT_FOUND,
                "%s not found with identifier: %s".formatted(resourceType, identifier));
    }
}
