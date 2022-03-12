package com.blackswandata.usertask.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse extends BaseResponse{

    private String userName;
    private String lastName;

    public UserResponse(Long id, String name, String userName, String lastNma) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.lastName = lastNma;
    }
}
