package com.easy.boot.admin.file.controller;

import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.file.entity.UploadVO;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.excel.UploadDTO;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.properties.EasyFile;
import com.easy.boot.exception.FileException;
import com.easy.boot.utils.FileUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author zoe
 * @date 2023/8/6
 * @description 文件上传控制器
 */
@Slf4j
@Api(tags = "文件相关接口")
@RestController
@RequestMapping("/admin")
public class UploadController extends BaseController {

    @Resource
    private EasyFile easyFile;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "上传文件")
    @EasyLog(module = "上传文件", operateType = OperateTypeEnum.UPLOAD)
    @PostMapping("/uploadFile")
    public Result<UploadVO> upload(UploadDTO dto) {
        FileUtil.checkFile(dto.getFile(), easyFile);
        String filePath = FileUtil.getFilePath(dto.getFile().getOriginalFilename(), easyFile.getFilePath());
        try {
            FileUtil.upload(dto.getFile(), filePath);
            filePath = filePath.replace(easyFile.getFilePath(), easyFile.getFileMapPath());
            UploadVO uploadVO = UploadVO.builder()
                    .url(easyFile.getPrefix() + filePath)
                    .build();
            return Result.success(uploadVO);
        } catch (IOException e) {
            log.error("文件上传失败 e --->>> ", e);
            throw new FileException("文件上传失败");
        }
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "上传图片")
    @EasyLog(module = "上传图片", operateType = OperateTypeEnum.UPLOAD)
    @PostMapping("/uploadImage")
    public Result<UploadVO> uploadImage(UploadDTO dto) {
        FileUtil.checkImage(dto.getFile(), easyFile);
        String filePath = FileUtil.getFilePath(dto.getFile().getOriginalFilename(), easyFile.getImagePath());
        try {
            FileUtil.upload(dto.getFile(), filePath);
            filePath = filePath.replace(easyFile.getImagePath(), easyFile.getImageMapPath());
            UploadVO uploadVO = UploadVO.builder()
                    .url(easyFile.getPrefix() + filePath)
                    .build();
            return Result.success(uploadVO);
        } catch (IOException e) {
            log.error("图片上传失败 e --->>> ", e);
            throw new FileException("图片上传失败");
        }
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "下载文件")
    @EasyLog(module = "下载文件", operateType = OperateTypeEnum.DOWNLOAD)
    @PostMapping("/download")
    public void download(String path) {
        String filePath = FileUtil.inverseAnalysis(path, easyFile);
        FileUtil.checkPath(filePath, easyFile);
        try {
            FileUtil.download(filePath, response);
        } catch (IOException e) {
            log.error("文件下载失败 e --->>> ", e);
            throw new FileException("文件下载失败");
        }
    }
}