package cn.easy.boot.common.excel.handler;

import cn.easy.boot.admin.dataDict.entity.DataDict;
import cn.easy.boot.admin.dataDictDomain.service.IDataDictDomainService;
import cn.easy.boot.common.excel.EasyExcelSelect;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import cn.easy.boot.common.excel.enums.ExcelSelectDataLoadTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/8/9
 * @description Excel导出下拉框处理器
 */
@Slf4j
@AllArgsConstructor
public class ExportExcelSelectCellWriteHandler implements CellWriteHandler {

    private Class<?> headClass;

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        Sheet sheet = writeSheetHolder.getSheet();
        Field[] fields = headClass.getDeclaredFields();
        String fieldName = head.getFieldName();
        for (Field field : fields) {
            if (field.isAnnotationPresent(EasyExcelSelect.class) && fieldName.equals(field.getName())) {
                EasyExcelSelect easyExcelSelect = field.getAnnotation(EasyExcelSelect.class);
                String[] values = easyExcelSelect.values();
                if (easyExcelSelect.loadType() == ExcelSelectDataLoadTypeEnum.QUERY) {
                    IDataDictDomainService dataDictDomainService = SpringUtil.getBean(IDataDictDomainService.class);
                    List<DataDict> dataDicts = dataDictDomainService.selectListByDomainCode(easyExcelSelect.code());
                    values = dataDicts.stream().map(DataDict::getLabel).collect(Collectors.toList()).toArray(new String[dataDicts.size()]);
                }
                if (values.length == 0) {
                    return;
                }
                DataValidationHelper helper = sheet.getDataValidationHelper();
                CellRangeAddressList addressList = new CellRangeAddressList(1, 1048575, columnIndex, columnIndex);
                DataValidationConstraint constraint = helper.createExplicitListConstraint(values);
                DataValidation dataValidation = helper.createValidation(constraint, addressList);
                dataValidation.setSuppressDropDownArrow(true);
                sheet.addValidationData(dataValidation);
            }
        }
    }

}
