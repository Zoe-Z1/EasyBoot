package cn.easy.boot3.common.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @author zoe
 * @date 2023/8/8
 * @description 拉黑时长转换器
 */
public class LongBlacklistDurationToStringConvert implements Converter<Long> {

    private static final String STR = "永久";

    /**
     * 导入转换
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Long convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return STR.equals(cellData.getStringValue()) ? 0 : Long.parseLong(cellData.getStringValue());
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
    public WriteCellData<?> convertToExcelData(Long value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData(value.intValue() == 0 ? STR : value.toString());
    }
}
