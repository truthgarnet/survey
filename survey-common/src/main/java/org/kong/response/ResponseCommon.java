package org.kong.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
