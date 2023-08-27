package com.easy.boot.common.generator.config;

import cn.hutool.core.util.StrUtil;
import com.easy.boot.exception.GeneratorException;
import lombok.*;

import java.util.Map;

/**
 * @author zoe
 * @date 2023/8/15
 * @description 全局参数配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalConfig {

    /**
     * 包名 例：com.easy.boot.admin
     */
    private String packageName;

    /**
     * RequestMapping 路径前缀
     */
    private String requestMappingPrefix;

    /**
     * 模板引擎根路径
     */
    private String templateRootPath;

    /**
     * 生成代码路径
     */
    private String outputPath;

    /**
     * 生成时是否覆盖已有文件，模板单独配置的优先级大于全局
     */
    private Boolean isOverride;

    /**
     * 生成完代码后是否打开目录
     */
    private Boolean isOpen;

    /**
     * 是否生成代码注释
     */
    private Boolean enableComment;

    /**
     * 作者
     */
    private String author;

    /**
     * 类注释时间格式化 例：yyyy/MM/dd
     */
    private String commentDateFormat;

    /**
     * 自定义的全局附加参数
     */
    private Map<String, Object> dataMap;

    public String getPackageName() {
        if (StrUtil.isEmpty(packageName)) {
            throw new GeneratorException("包名不能为空");
        }
        return packageName;
    }

    public String getRequestMappingPrefix() {
        if (StrUtil.isNotEmpty(requestMappingPrefix)) {
            if (!requestMappingPrefix.startsWith("/")) {
                requestMappingPrefix = "/" + requestMappingPrefix;
            }
            if (requestMappingPrefix.endsWith("/")) {
                requestMappingPrefix = requestMappingPrefix.substring(0, requestMappingPrefix.length() - 1);
            }
        }
        return requestMappingPrefix;
    }

    public String getTemplateRootPath() {
        if (StrUtil.isEmpty(templateRootPath)) {
            throw new GeneratorException("模板引擎根路径不能为空");
        }
        return templateRootPath;
    }

    public String getOutputPath() {
        if (StrUtil.isEmpty(outputPath)) {
            throw new GeneratorException("生成代码路径不能为空");
        }
        return outputPath;
    }

    public Boolean getIsOverride() {
        if (isOverride == null) {
            isOverride = false;
        }
        return isOverride;
    }

    public Boolean getIsOpen() {
        if (isOpen == null) {
            isOpen = false;
        }
        return isOpen;
    }

    public Boolean getEnableComment() {
        if (enableComment == null) {
            enableComment = true;
        }
        return enableComment;
    }

    public String getAuthor() {
        if (enableComment && StrUtil.isEmpty(author)) {
            throw new GeneratorException("作者不能为空");
        }
        return author;
    }

    public String getCommentDateFormat() {
        if (StrUtil.isEmpty(commentDateFormat)) {
            commentDateFormat = "yyyy/MM/dd";
        }
        return commentDateFormat;
    }
}
