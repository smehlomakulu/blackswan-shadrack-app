package com.blackswandata.usertask.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskNotFoundException extends Exception {

    private String reason;

    public TaskNotFoundException(String reason) {
        super(reason);
        this.reason = reason;
    }
}
