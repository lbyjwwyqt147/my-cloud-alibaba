package pers.liujunyi.cloud.centre.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.liujunyi.cloud.common.exception.DescribeException;
import pers.liujunyi.cloud.common.exception.ErrorCodeEnum;
import pers.liujunyi.cloud.common.restful.ResultInfo;
import pers.liujunyi.cloud.common.restful.ResultUtil;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/***
 * 异常处理
 * @author ljy
 */
@RestControllerAdvice
@Log4j2
public class PerGlobalExceptionHandler {

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     * @param e
     * @return
     */
    @ExceptionHandler(value = DescribeException.class)
    public ResultInfo handlerDescribeException(DescribeException e) {
        log.error("【系统业务逻辑异常】： ", e);
        return ResultUtil.error(ErrorCodeEnum.ERROR.getCode(), e.getMessage());
    }

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     * @param e
     * @return
     */
   /* @ExceptionHandler(value = Exception.class)
    public ResultInfo handlerException(Exception e) {
        log.error("【系统异常】： ", e);
        if (e instanceof DescribeException){
            DescribeException myException = (DescribeException) e;
            return ResultUtil.error(ErrorCodeEnum.ERROR.getCode(), myException.getMessage());
        } else if (e.getClass().getName().equals("org.springframework.security.authentication.InsufficientAuthenticationException")) {
            return ResultUtil.error(ErrorCodeEnum.CLIENT_NOT_AUTHORITY.getCode(), ErrorCodeEnum.CLIENT_NOT_AUTHORITY.getMessage());
        }
        return ResultUtil.error();
    }*/


    /**
     * 参数绑定 异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public ResultInfo handlerBindException(BindException e) {
        StringBuffer errorMsg = new StringBuffer();
        e.getAllErrors().stream().forEach(item -> errorMsg.append(item.getDefaultMessage()).append("."));
        log.error("【参数绑定失败】： ", e);
        log.info(errorMsg.toString());
        return ResultUtil.error(ErrorCodeEnum.PARAMS.getCode(), errorMsg.toString());
    }


    /**
     * 数据库表乐观锁 异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = ObjectOptimisticLockingFailureException.class)
    public ResultInfo ObjectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException e) {
        log.error("【数据库表乐观锁 数据版本号错误 异常】： ", e);
        return ResultUtil.info(ErrorCodeEnum.DATA_LOCK, null, false);
    }


    /**
     * validation 进行数据校验 异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultInfo handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuffer errorMsg = new StringBuffer();
        BindingResult result = e.getBindingResult();
        result.getAllErrors().stream().forEach(item -> errorMsg.append(item.getDefaultMessage()).append("."));
        log.error("【参数验证失败】： ", e);
        log.info(errorMsg.toString());
        return ResultUtil.error(ErrorCodeEnum.PARAMS.getCode(), errorMsg.toString());
    }


    /**
     * 405 - Method Not Allowed
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultInfo handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("【不支持当前请求方法】：", e);
        return ResultUtil.error(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
    }

    /**
     * 捕获校验参数异常方法
     * @param exception
     * @return
     */
    @ExceptionHandler(ValidationException.class)
    public ResultInfo handleValidationException(ValidationException exception) {
        log.error("【参数验证失败】：",exception);
        if (exception instanceof ConstraintViolationException){
            StringBuffer errorMsg = new StringBuffer();
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            violations.stream().forEach(item -> errorMsg.append(item.getMessage()).append("."));
            log.info(errorMsg.toString());
            return  ResultUtil.error(HttpStatus.BAD_REQUEST.value(), errorMsg.toString());
        }
        return  ResultUtil.error(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    /**
     * 400 - Bad Request
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultInfo handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("【参数验证失败】：", e);
        return  ResultUtil.error(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    /**
     * 400 - Bad Request
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultInfo handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("【参数解析失败】：", e);
        return  ResultUtil.error(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }



}