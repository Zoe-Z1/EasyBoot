package com.easy.boot3.common.excel.converter;

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
 * @description 拉黑类型转换器
 */
public class IntegerBlacklistTypeToStringConvert implements Converter<Integer> {

    private static final Map<Integer, String> MAP = new HashMap<>();
    static {
        MAP.put(1, "账号");
        MAP.put(2, "IP");
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
        for (Integer integer : MAP.keySet()) {
            String value = MAP.get(integer);
            if (value.equals(cellData.getStringValue())) {
                return integer;
            }
        }
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
        return new WriteCellData(MAP.get(value));
    }
}
