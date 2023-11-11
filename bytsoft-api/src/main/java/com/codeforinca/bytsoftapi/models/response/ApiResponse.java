package com.codeforinca.bytsoftapi.models.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse
        implements java.io.Serializable
{

    public ApiResponse(
            String message,
            Object data,
            HttpStatus status
    ){
        this.message = message;
        this.data = data;
        this.status = status;
    }
    public ApiResponse(
            String message,
            Object data
    ){
        this.message = message;
        this.data = data;
    }

    public ApiResponse(
            String message
    ){
        this.message = message;
    }

    private String message;
    private Object data;
    private HttpStatus status;
    private String token;

}
