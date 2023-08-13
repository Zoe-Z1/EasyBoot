package com.fast.start.common.excel;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zoe
 * @date 2023/8/9
 * @description
 */
@Data
@Builder
@ApiModel(value = "Excel导入请求对象", description = "Excel导入请求对象")
public class UploadDTO {

    @ApiModelProperty(value = "文件")
    @JSONField(serialize = false)
    private MultipartFile file;
}
