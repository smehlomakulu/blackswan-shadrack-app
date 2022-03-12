package com.blackswandata.usertask.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserTaskResponse extends BaseResponse{

    private String userName;
    private String lastName;
    private Set<TaskResponse> taskResponses = new HashSet<>();

    public UserTaskResponse(Long userId, String name, String userName, String lastNmae, Set<TaskResponse> tasks) {
        this.id = userId;
        this.name = name;
        this.userName = userName;
        this.lastName = lastNmae;
        this.taskResponses = tasks;
    }

}
