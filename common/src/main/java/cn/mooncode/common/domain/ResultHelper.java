package cn.mooncode.common.domain;


/**
 * 返回结果
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/29 14:14
 */

public class ResultHelper {

    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MESSAGE = "请求成功";
    public static final String ERROR_CODE = "500";
    public static final String ERROR_MESSAGE = "请求失败";

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return success(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> Result<T> success(String code, String message, T data) {
        return success(code, message, data, System.currentTimeMillis());
    }

    public static <T> Result<T> success(String code, String message, T data, long timestamp) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message)
                .setData(data)
                .setTimestamp(timestamp);
    }

    public static <T> Result<T> fail() {
        return fail(ERROR_CODE, ERROR_MESSAGE);
    }

    public static <T> Result<T> fail(String message) {
        return fail(ERROR_CODE, message, System.currentTimeMillis());
    }

    public static <T> Result<T> fail(String code, String message) {
        return fail(code, message, System.currentTimeMillis());
    }

    public static <T> Result<T> fail(String code, String message, long timestamp) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message)
                .setTimestamp(timestamp);
    }

}
