package com.fast.start.admin.file.controller;

import com.fast.start.admin.file.entity.UploadVO;
import com.fast.start.admin.operationLog.enums.OperateTypeEnum;
import com.fast.start.common.base.BaseController;
import com.fast.start.common.base.Result;
import com.fast.start.common.excel.UploadDTO;
import com.fast.start.common.log.FastLog;
import com.fast.start.common.properties.FastFile;
import com.fast.start.exception.FileException;
import com.fast.start.utils.FileUtil;
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
    private FastFile fastFile;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "上传文件")
    @FastLog(module = "上传文件", operateType = OperateTypeEnum.UPLOAD)
    @PostMapping("/uploadFile")
    public Result<UploadVO> upload(UploadDTO dto) {
        FileUtil.checkFile(dto.getFile(), fastFile);
        String filePath = FileUtil.getFilePath(dto.getFile().getOriginalFilename(), fastFile.getFilePath());
        try {
            FileUtil.upload(dto.getFile(), filePath);
            filePath = filePath.replace(fastFile.getFilePath(), fastFile.getFileMapPath());
            UploadVO uploadVO = UploadVO.builder()
                    .url(fastFile.getPrefix() + filePath)
                    .build();
            return Result.success(uploadVO);
        } catch (IOException e) {
            log.error("文件上传失败 e --->>> ", e);
            throw new FileException("文件上传失败");
        }
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "上传图片")
    @FastLog(module = "上传图片", operateType = OperateTypeEnum.UPLOAD)
    @PostMapping("/uploadImage")
    public Result<UploadVO> uploadImage(UploadDTO dto) {
        FileUtil.checkImage(dto.getFile(), fastFile);
        String filePath = FileUtil.getFilePath(dto.getFile().getOriginalFilename(), fastFile.getImagePath());
        try {
            FileUtil.upload(dto.getFile(), filePath);
            filePath = filePath.replace(fastFile.getImagePath(), fastFile.getImageMapPath());
            UploadVO uploadVO = UploadVO.builder()
                    .url(fastFile.getPrefix() + filePath)
                    .build();
            return Result.success(uploadVO);
        } catch (IOException e) {
            log.error("图片上传失败 e --->>> ", e);
            throw new FileException("图片上传失败");
        }
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "下载文件")
    @FastLog(module = "下载文件", operateType = OperateTypeEnum.DOWNLOAD)
    @PostMapping("/download")
    public void download(String path) {
        String filePath = FileUtil.inverseAnalysis(path, fastFile);
        FileUtil.checkPath(filePath, fastFile);
        try {
            FileUtil.download(filePath, response);
        } catch (IOException e) {
            log.error("文件下载失败 e --->>> ", e);
            throw new FileException("文件下载失败");
        }
    }
}
