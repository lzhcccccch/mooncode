package cn.mooncode.common.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/6/3 17:26
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 5404568271057324865L;

    /**
     * 删除标识: 0 未删除, 1 已删除
     */
    private String delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注描述
     */
    private String remark;

}
