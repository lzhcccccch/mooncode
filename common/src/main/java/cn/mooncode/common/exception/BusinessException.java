package cn.mooncode.common.exception;


import java.io.Serial;

/**
 * 业务异常类
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/6/20 14:05
 */

public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5713565892096009481L;

    public BusinessException(String message) {
        super(message);
    }

}
