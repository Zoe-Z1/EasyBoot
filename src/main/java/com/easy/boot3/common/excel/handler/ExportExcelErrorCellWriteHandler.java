package com.easy.boot3.common.excel.handler;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.easy.boot3.common.excel.entity.ImportExcelError;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.util.List;

/**
 * @author zoe
 * @date 2023/8/9
 * @description Excel导出错误数据自定义处理器
 */
@Slf4j
@AllArgsConstructor
public class ExportExcelErrorCellWriteHandler implements CellWriteHandler {

    private List<ImportExcelError> errors;


    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        Cell cell = context.getCell();
        if (CollUtil.isEmpty(errors)) {
            return;
        }
        for (ImportExcelError error : errors) {
            if (cell.getRowIndex() == error.getRowIndex() && cell.getColumnIndex() == error.getColumnIndex()) {
                // 存在批注，跳过
                if (cell.getCellComment() != null) {
                    continue;
                }
                log.info("对第{}行，第{}列进行错误批注", cell.getRowIndex(), cell.getColumnIndex());
                ClientAnchor anchor = new XSSFClientAnchor();
                anchor.setCol1(cell.getColumnIndex());
                anchor.setRow1(cell.getRowIndex());
                anchor.setCol2(cell.getColumnIndex() + 2);
                anchor.setRow2(cell.getRowIndex() + 2);
                Drawing<?> drawing = cell.getSheet().createDrawingPatriarch();
                Comment cellComment = drawing.createCellComment(anchor);
                cellComment.setString(new XSSFRichTextString(error.getMsg()));
                cell.setCellComment(cellComment);
            }
        }
    }
}
