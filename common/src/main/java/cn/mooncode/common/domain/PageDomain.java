package cn.mooncode.common.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分页数据
 * <p>
 * author: lzhch
 * version: v1.0
 * date: 2025/2/11 11:01
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageDomain implements Serializable {
    @Serial
    private static final long serialVersionUID = 8287231521969420305L;

    /**
     * 当前页码
     */
    private Integer pageIndex = 1;

    /**
     * 每页显示记录数
     */
    private Integer pageSize = 10;

    /**
     * 表格排序列名
     */
    private String tableOrderName;

    /**
     * 排序方式: desc or asc
     */
    private String tableOrderSort;

}
