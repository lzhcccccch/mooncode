package cn.mooncode.common.config;


import cn.mooncode.common.domain.Result;
import cn.mooncode.common.domain.ResultHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 全局响应体处理器
 * 用于统一处理 REST 接口的响应数据格式
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/29 15:07
 */

@Slf4j
@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 判断是否需要对响应体进行处理
     *
     * @param returnType    方法返回类型参数，包含方法的返回值类型、注解等信息
     * @param converterType HTTP消息转换器类型，用于将对象转换为HTTP响应
     * @return true:需要处理 false:不需要处理
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 获取方法返回值类型
        Class<?> returnClass = returnType.getParameterType();

        // 获取方法名
        String methodName = Objects.nonNull(returnType.getMethod()) ? returnType.getMethod().getName() : "unknown";

        // 获取声明类名
        String declaringClassName = returnType.getDeclaringClass().getSimpleName();

        // 检查转换器类型
        String converterName = converterType.getSimpleName();

        // 记录详细的处理信息
        log.debug("返回方法[类名.方法名]: {}.{}", declaringClassName, methodName);
        log.debug("返回的参数类型: {}, 转换器: {}", returnClass.getName(), converterName);

        // 如果返回值已经是Result类型，则不需要再次处理
        if (Result.class.isAssignableFrom(returnClass)) {
            log.debug("返回值已经是Result类型, 无需再次处理");
            return false;
        }

        // 特定转换器的处理逻辑
        if (converterName.contains("String")) {
            log.debug("检测到String转换器, 需要特殊处理");
        }

        return true;
    }

    /**
     * 在响应体写入之前进行处理
     *
     * @param body                  原始响应体
     * @param returnType            方法返回类型参数
     * @param selectedContentType   选择的内容类型
     * @param selectedConverterType 选择的转换器类型
     * @param request               当前请求
     * @param response              当前响应
     * @return 处理后的响应体
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, @NonNull MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 获取请求相关信息
        String requestPath = request.getURI().getPath();
        String requestMethod = request.getMethod().toString();

        // 获取方法相关信息
        String methodName = returnType.getMethod() != null ? returnType.getMethod().getName() : "unknown";
        Class<?> returnClass = returnType.getParameterType();

        // 记录详细的处理信息
        log.debug("请求方式: {}, 请求路径: {}, 请求方法: {}", requestMethod, requestPath, methodName);
        log.debug("内容类型: {}, 转换器: {}", selectedContentType, selectedConverterType.getSimpleName());

        // 设置响应头
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // 根据不同的内容类型进行处理
        if (MediaType.TEXT_PLAIN.includes(selectedContentType)) {
            log.debug("处理文本响应类型");
        }

        // 根据不同的转换器类型进行处理
        if (selectedConverterType.getSimpleName().contains("String")) {
            // 当响应体是 String 类型时，进行 JSON 转换，因为 Spring 默认使用 StringHttpMessageConverter 处理字符串，不会将字符串识别为 JSON
            log.debug("处理字符串响应类型");
            return objectMapper.writeValueAsString(ResultHelper.success(body));
        }

        // 空值处理
        if (Objects.isNull(body)) {
            log.debug("处理空响应体");
            return ResultHelper.success(null);
        }

        // Result类型判断
        if (body instanceof Result<?>) {
            log.debug("响应体已经是Result类型, 无需再次处理");
            return body;
        }

        // 记录响应处理的详细信息
        log.debug("原始响应类型: {}", body.getClass().getName());
        log.debug("返回类型: {}", returnClass.getName());

        try {
            // 包装响应体
            return ResultHelper.success(body);
        } catch (Exception e) {
            log.error("处理响应体异常: {} {}", requestMethod, requestPath, e);
            return ResultHelper.fail("处理响应体异常: " + e.getMessage());
        }
    }

}