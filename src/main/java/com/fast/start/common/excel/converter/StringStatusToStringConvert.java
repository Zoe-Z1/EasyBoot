package com.fast.start.common.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.fast.start.admin.operationLog.enums.OperateStatusEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/8/8
 * @description 操作状态转换器
 */
public class StringStatusToStringConvert implements Converter<String> {

    /**
     * 导入转换
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        List<OperateStatusEnum> list = Arrays.stream(OperateStatusEnum.values()).filter(res -> res.getMsg().equals(cellData.getStringValue())).collect(Collectors.toList());
        return list.isEmpty() ? "" : String.valueOf(list.get(0));
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
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(OperateStatusEnum.valueOf(value).getMsg());
    }
}
