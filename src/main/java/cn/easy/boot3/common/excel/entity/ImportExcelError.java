package cn.easy.boot3.common.excel.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zoe
 * @date 2023/8/9
 * @description 导入excel错误批注实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportExcelError {

    /**
     * 错误行下标
     */
    private Integer rowIndex;

    /**
     * 错误列下标
     */
    private Integer columnIndex;

    /**
     * 批注
     */
    private String msg;

}
