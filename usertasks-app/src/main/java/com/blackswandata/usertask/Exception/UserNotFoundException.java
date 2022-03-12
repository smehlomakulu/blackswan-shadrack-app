package com.blackswandata.usertask.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserNotFoundException extends Exception {

    private String reason;

    public UserNotFoundException(String reason) {
        super(reason);
        this.reason = reason;
    }
}
