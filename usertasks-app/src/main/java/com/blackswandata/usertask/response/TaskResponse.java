package com.blackswandata.usertask.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponse extends BaseResponse{

    private String description;
    private String status;
    private String date;

    public TaskResponse(Long id, String name, String description, String status, String date) {
        this.id = id;
        this.name = name;
        this.description =description;
        this.status = status;
        this.date = date;
    }
}
