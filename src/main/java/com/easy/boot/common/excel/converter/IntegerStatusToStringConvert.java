package com.easy.boot.common.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zoe
 * @date 2023/8/8
 * @description 状态转换器
 */
public class IntegerStatusToStringConvert implements Converter<Integer> {

    private static final Map<Integer, String> STATUS_MAP = new HashMap<>();
    static {
        STATUS_MAP.put(1, "正常");
        STATUS_MAP.put(2, "禁用");
    }

    /**
     * 导入转换
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        for (Integer integer : STATUS_MAP.keySet()) {
            String value = STATUS_MAP.get(integer);
            if (value.equals(cellData.getStringValue())) {
                return integer;
            }
        }
        // 默认设置禁用
        return 2;
    }

    /**
     * 导出转换
     * @param value
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(STATUS_MAP.get(value));
    }
}
