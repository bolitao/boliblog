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
 * @author bolitao
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler<T> {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Result<T>> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result<>("-1",
                "系统异常: " + e.getMessage(), null));
    }

    @ExceptionHandler(value = ShiroException.class)
    public ResponseEntity<Result<T>> runtimeExceptionHandler(ShiroException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Result<>("20001",
                "Shiro 异常: " + e.getMessage(), null));
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<Result<T>> baseExceptionHandler(BaseException e) {
        log.error(e.getMessage());
        // TODO: 枚举。 -2 为通用基础业务错误
        String code = (e.getCode() == null || "".equals(e.getCode())) ? "-2" : e.getCode();
        return ResponseEntity.status(e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Result<>(code, "业务异常: " + e.getMessage(), null));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Result<T>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
//        BindingResult bindingResult = e.getBindingResult();
//        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        String message;
        if (e.getBindingResult().getFieldError() == null) {
            message = e.getMessage();
        } else {
            message = e.getBindingResult().getFieldError().getDefaultMessage();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Result<>("21000", "[业务]校验异常: " + message, null));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Result<T>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Result<>("22000", "参数错误: " + e.getMessage(),
                null));
    }
}
