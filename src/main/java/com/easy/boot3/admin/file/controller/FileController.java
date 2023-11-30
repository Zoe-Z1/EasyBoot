package com.easy.boot3.admin.file.controller;

import com.easy.boot3.admin.file.entity.UploadVO;
import com.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot3.common.base.BaseController;
import com.easy.boot3.common.base.Result;
import com.easy.boot3.common.excel.entity.UploadDTO;
import com.easy.boot3.common.log.EasyLog;
import com.easy.boot3.utils.FileUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author zoe
 * @date 2023/8/6
 * @description 文件上传/下载控制器
 */
@Slf4j
@Tag(name = "文件相关接口")
@RestController
@RequestMapping("/common")
public class FileController extends BaseController {


    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "上传文件")
    @EasyLog(module = "上传文件", operateType = OperateTypeEnum.UPLOAD)
    @PostMapping("/uploadFile")
    public Result<UploadVO> upload(UploadDTO dto) throws IOException {
        FileUtil.checkFile(dto.getFile(), easyFile);
        String filePath = FileUtil.getFilePath(dto.getFile().getOriginalFilename(), easyFile.getFilePath());
        FileUtil.upload(dto.getFile(), filePath);
        filePath = filePath.replace(easyFile.getFilePath(), easyFile.getFileMapPath());
        UploadVO uploadVO = UploadVO.builder()
                .url(filePath)
                .build();
        return Result.success(uploadVO);
    }

    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "上传图片")
    @EasyLog(module = "上传图片", operateType = OperateTypeEnum.UPLOAD)
    @PostMapping("/uploadImage")
    public Result<UploadVO> uploadImage(UploadDTO dto) throws IOException {
        FileUtil.checkImage(dto.getFile(), easyFile);
        String filePath = FileUtil.getFilePath(dto.getFile().getOriginalFilename(), easyFile.getImagePath());
        FileUtil.upload(dto.getFile(), filePath);
        filePath = filePath.replace(easyFile.getImagePath(), easyFile.getImageMapPath());
        UploadVO uploadVO = UploadVO.builder()
                .url(filePath)
                .build();
        return Result.success(uploadVO);
    }

    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "下载文件")
    @EasyLog(module = "下载文件", operateType = OperateTypeEnum.DOWNLOAD)
    @PostMapping("/download")
    public void download(String path) throws IOException {
        String filePath = FileUtil.inverseAnalysis(path, easyFile);
        FileUtil.checkPath(filePath, easyFile);
        FileUtil.download(filePath, response);
    }
}
