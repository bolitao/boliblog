package xyz.bolitao.boliblog.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.bolitao.boliblog.exception.entity.BaseException;
import xyz.bolitao.boliblog.util.Result;

/**
 * TODO: log
 *
 * @author bolitao
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler<T> {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Result<T>> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>("系统异常: " + e.getMessage()));
    }

    @ExceptionHandler(value = ShiroException.class)
    public ResponseEntity<Result<T>> runtimeExceptionHandler(ShiroException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Result<>("Shiro 异常: " + e.getMessage()));
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<Result<T>> baseExceptionHandler(BaseException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>("业务异常: " + e.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Result<T>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Result<>("[业务]校验异常: " + e.getMessage()));
    }
}
