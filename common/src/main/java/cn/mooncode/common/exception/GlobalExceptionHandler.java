package cn.mooncode.common.exception;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.mooncode.common.domain.Result;
import cn.mooncode.common.domain.ResultHelper;
import com.google.common.base.Throwables;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;


/**
 * 全局异常处理
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/29 15:06
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result<?> handleException(HttpRequestMethodNotSupportedException e) {
        log.error("请求方式不支持 :{}", Throwables.getStackTraceAsString(e));

        return ResultHelper.fail(e.getMethod() + " 请求不支持");
    }

    /**
     * 处理参数验证异常
     */
    @SneakyThrows
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Void> handleValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError firstFieldError = CollUtil.getFirst(bindingResult.getFieldErrors());
        String exceptionStr = Optional.ofNullable(firstFieldError)
                .map(FieldError::getDefaultMessage)
                .orElse(CharSequenceUtil.EMPTY);

        log.error("MethodArgumentNotValidException: [{}] {} [ex] {}", request.getMethod(), request.getRequestURI(), Throwables.getStackTraceAsString(ex));
        return ResultHelper.fail("处理参数验证异常 :" + exceptionStr);
    }

    /**
     * 缺少请求参数
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少请求参数 :{}", Throwables.getStackTraceAsString(e));

        return ResultHelper.fail("缺少请求参数 :" + e.getParameterName());
    }

    /**
     * 非法参数
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("违规参数 :{}", Throwables.getStackTraceAsString(e));

        return ResultHelper.fail("非法参数 :" + e.getMessage());
    }

    /**
     * 处理认证异常
     */
    @ExceptionHandler(value = {AuthenticationException.class, BadCredentialsException.class})
    public Result<Void> handleAuthenticationException(HttpServletRequest request, AuthenticationException ex) {
        log.error("AuthenticationException: [{}] {} [ex] {}", request.getMethod(), request.getRequestURI(), Throwables.getStackTraceAsString(ex));

        return ResultHelper.fail("认证失败 :" + ex.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.error("业务异常 :{}", Throwables.getStackTraceAsString(e));

        return ResultHelper.fail(e.getMessage());
    }

    /**
     * 处理未知异常
     */
    @ExceptionHandler(value = Throwable.class)
    public Result<Void> handleThrowable(HttpServletRequest request, Throwable throwable) {
        log.error("未知异常 : [{}] {} ", request.getMethod(), request.getRequestURI(), throwable);

        return ResultHelper.fail();
    }

}
