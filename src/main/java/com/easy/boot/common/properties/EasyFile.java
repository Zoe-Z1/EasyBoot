package com.easy.boot.common.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zoe
 * @date 2023/8/6
 * @description
 */

@ConfigurationProperties(prefix = "easy.file")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class EasyFile implements Serializable {

    private static final long serialVersionUID = 4078145361972526585L;

    /**
     * 访问前缀
     */
    private String prefix = "127.0.0.1:9000";

    /**
     * 文件映射路径
     */
    private String fileMapPath = "/file";

    /**
     * 图片映射路径
     */
    private String imageMapPath = "/image";

    /**
     * 文件上传路径
     */
    private String filePath = "/Users/zoe/Downloads/file";

    /**
     * 图片上传路径
     */
    private String imagePath = "/Users/zoe/Downloads/image";

    /**
     * 文件最大大小，单位/MB
     */
    private Integer maxFileSize = 10;

    /**
     * 图片最大大小，单位/MB
     */
    private Integer maxImageSize = 10;

    /**
     * 支持的文件类型 以,分割
     */
    private String fileType = "doc,docx,ppt,pptx,wps,xls,xlsx,pdf";

    /**
     * 支持的图片类型 以,分割
     */
    private String imageType = "jpg,jpeg,png,gif";

    public String getFileMapPath() {
        if (!fileMapPath.endsWith("/")) {
            fileMapPath = fileMapPath + "/";
        }
        if (!fileMapPath.startsWith("/")) {
            fileMapPath = "/" + fileMapPath;
        }
        return fileMapPath;
    }

    public String getImageMapPath() {
        if (!imageMapPath.endsWith("/")) {
            imageMapPath = imageMapPath + "/";
        }
        if (!imageMapPath.startsWith("/")) {
            imageMapPath = "/" + imageMapPath;
        }
        return imageMapPath;
    }

    public String getFilePath() {
        if (!filePath.endsWith("/")) {
            filePath = filePath + "/";
        }
        if (!filePath.startsWith("/")) {
            filePath = "/" + filePath;
        }
        return filePath;
    }

    public String getImagePath() {
        if (!imagePath.endsWith("/")) {
            imagePath = imagePath + "/";
        }
        if (!imagePath.startsWith("/")) {
            imagePath = "/" + imagePath;
        }
        return imagePath;
    }

}
