package cn.mooncode.common.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件上传响应
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/2/20 14:03
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 490857592207472566L;

    /**
     * 原始文件名称
     */
    private String originalFilename;

    /**
     * 存储文件名称
     */
    private String storedFilename;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private long fileSize;

    /**
     * 文件类型
     */
    private String mimeType;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

}
