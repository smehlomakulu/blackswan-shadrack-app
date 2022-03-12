package com.blackswandata.usertask.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskCreationFailureException extends Exception {

    private String reason;

    public TaskCreationFailureException(String reason) {
        super(reason);
        this.reason = reason;
    }
}
