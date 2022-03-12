package com.blackswandata.usertask.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseResponse {

    protected Long id;
    protected String name;
}
