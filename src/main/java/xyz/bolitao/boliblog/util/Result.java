package xyz.bolitao.boliblog.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author bolitao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    /**
     * http status
     */
    // @JsonIgnore
    private HttpStatus httpStatus;
    /**
     * business code
     */
    private String code;
    /**
     * message
     */
    private String message;
    /**
     * response payload
     */
    private T data;

    public Result(T data) {
        this.data = data;
    }

    public Result(String message) {
        this.message = message;
    }

    public Result<T> httpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public Result<T> code(String code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }
}