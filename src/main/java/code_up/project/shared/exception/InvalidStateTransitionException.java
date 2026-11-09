package code_up.project.shared.exception;

import code_up.project.shared.enums.ErrorCode;

public final class InvalidStateTransitionException extends BusinessException {
    public InvalidStateTransitionException(String currentState, String targetState) {
        super(ErrorCode.INVALID_STATE_TRANSITION,
                "Cannot transition from %s to %s".formatted(currentState, targetState));
    }
}