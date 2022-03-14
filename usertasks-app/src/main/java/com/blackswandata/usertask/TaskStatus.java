package com.blackswandata.usertask;

import com.blackswandata.usertask.response.TaskResponse;

public enum TaskStatus {

    PENDING("PENDING"),
    DONE("DONE");

    private String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }
}
