package cn.mooncode.common.config;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * Web 配置类
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2024/11/22 22:08
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 允许跨域的路径
     */
    public static final String MAPPING = "/**";

    /**
     * 添加跨域配置
     *
     * @param registry 跨域注册中心
     */
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        if (CharSequenceUtil.isBlank(MAPPING)) {
            return;
        }

        Arrays.stream(MAPPING.split(StrPool.COMMA))
                .map(String::trim)
                .filter(CharSequenceUtil::isNotBlank)
                .forEach(item -> {
                    //设置允许跨域的路径
                    registry.addMapping(item)
                            //设置允许跨域请求的域名
                            .allowedOrigins("*")
                            //是否允许证书 不再默认开启
                            .allowCredentials(true)
                            //设置允许的方法
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                            .allowedHeaders("*")
                            //跨域允许时间
                            .maxAge(3600);
                });
    }

}

