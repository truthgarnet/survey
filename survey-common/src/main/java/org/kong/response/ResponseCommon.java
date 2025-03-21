package org.kong.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseCommon<T> {

    private int code;
    private String msg;
    private T data;

    @Builder
    public ResponseCommon(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
