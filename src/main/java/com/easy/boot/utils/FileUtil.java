package com.easy.boot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.easy.boot.common.properties.EasyFile;
import com.easy.boot.exception.FileException;
import com.easy.boot.exception.enums.SystemErrorEnum;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author zoe
 * @date 2023/8/6
 * @description 文件相关工具类
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    private FileUtil(){}

    /**
     * 支持的图片mimeType
     */
    private static final Map<String, String> IMAGE_MIME_TYPE_MAP = MapUtil.newHashMap();
    /**
     * 支持的文件mimeType
     */
    private static final Map<String, String> FILE_MIME_TYPE_MAP = MapUtil.newHashMap();
    /**
     * 支持的文件对应的魔数
     */
    private static final Map<String, String> FILE_MAGIC_MAP = MapUtil.newHashMap();
    /**
     * 支持的图片对应的魔数
     */
    private static final Map<String, String> IMAGE_MAGIC_MAP = MapUtil.newHashMap();


    static {
        IMAGE_MIME_TYPE_MAP.put("png", "image/png");
        IMAGE_MIME_TYPE_MAP.put("jpg", "image/jpeg");
        IMAGE_MIME_TYPE_MAP.put("jpeg", "image/jpeg");
        IMAGE_MIME_TYPE_MAP.put("gif", "image/gif");
        IMAGE_MIME_TYPE_MAP.put("bmp", "image/bmp");
        IMAGE_MIME_TYPE_MAP.put("tiff", "image/tiff");

        FILE_MIME_TYPE_MAP.put("xls", "application/vnd.ms-excel");
        FILE_MIME_TYPE_MAP.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        FILE_MIME_TYPE_MAP.put("doc", "application/msword");
        FILE_MIME_TYPE_MAP.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        FILE_MIME_TYPE_MAP.put("ppt", "application/vnd.ms-powerpoint");
        FILE_MIME_TYPE_MAP.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        FILE_MIME_TYPE_MAP.put("wps", "application/vnd.ms-works");
        FILE_MIME_TYPE_MAP.put("pdf", "application/pdf");

        FILE_MAGIC_MAP.put("xls", "D0CF11E0");
        FILE_MAGIC_MAP.put("xlsx", "504B0304");
        FILE_MAGIC_MAP.put("doc", "D0CF11E0");
        FILE_MAGIC_MAP.put("docx", "504B0304");
        FILE_MAGIC_MAP.put("ppt", "0xD0CF11E0A1B11AE1");
        FILE_MAGIC_MAP.put("pptx", "504B0304");
        FILE_MAGIC_MAP.put("wps", "d0cf11e0a1b11ae10000");
        FILE_MAGIC_MAP.put("pdf", "255044462D312E");

        IMAGE_MAGIC_MAP.put("png", "89504E47");
        IMAGE_MAGIC_MAP.put("jpg", "FFD8FF");
        IMAGE_MAGIC_MAP.put("jpeg", "FFD8FF");
        IMAGE_MAGIC_MAP.put("gif", "47494638");
        IMAGE_MAGIC_MAP.put("bmp", "424D");
        IMAGE_MAGIC_MAP.put("tiff", "49492A00");
    }


    /**
     * 上传
     * @param multipartFile
     * @param filePath 要上传的路径
     * @throws IOException
     */
    public static void upload(MultipartFile multipartFile, String filePath) throws IOException {
        File file = new File(filePath);
        // 判断文件夹是否存在 不存在则新建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        multipartFile.transferTo(file);
    }

    /**
     * 下载文件
     * @param path 文件总路径
     * @param response
     * @throws IOException
     */
    public static void download(String path, HttpServletResponse response) throws IOException {
        // 获取文件名
        String[] filenames = path.split("/");
        String filename = filenames[filenames.length - 1];
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        //通过文件的路径读取文件字节流
        byte[] bytes = FileUtil.readBytes(path);
        //通过response的输出流返回文件
        ServletOutputStream os = response.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();
    }

    /**
     * 校验文件
     * @param file 文件
     * @param easyFile 文件配置
     */
    public static void checkFile(MultipartFile file, EasyFile easyFile) {
        if (file == null || file.isEmpty()) {
            throw new FileException("文件不能为空");
        }
        // 校验文件名
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new FileException("文件名不能为空");
        }
        String[] fileTypes = easyFile.getFileType().split(",");
        // 校验文件拓展名
        checkExtension(filename, fileTypes);
        // 获取文件mime
        String mimeType = file.getContentType();
        // 校验MIME
        checkMimeType(filename, mimeType, FILE_MIME_TYPE_MAP);
        // 获取文件头
        String header = getFileHeader(file);
        // 校验魔数
        checkMagic(filename, header, FILE_MAGIC_MAP);
        // 校验文件大小
        checkSize(file.getSize(), easyFile.getMaxFileSize());
    }

    /**
     * 校验图片
     * @param file 文件
     * @param easyFile 文件配置
     */
    public static void checkImage(MultipartFile file, EasyFile easyFile) {
        if (file == null || file.isEmpty()) {
            throw new FileException("图片不能为空");
        }
        // 校验图片名
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new FileException("图片名不能为空");
        }
        String[] imageTypes = easyFile.getImageType().split(",");
        // 校验图片拓展名
        checkExtension(filename, imageTypes);
        // 获取图片mime
        String mimeType = file.getContentType();
        // 校验MIME
        checkMimeType(filename, mimeType, IMAGE_MIME_TYPE_MAP);
        // 获取文件magic
        String header = getFileHeader(file);
        // 校验魔数
        checkMagic(filename, header, IMAGE_MAGIC_MAP);
        // 校验图片大小
        checkSize(file.getSize(), easyFile.getMaxImageSize());
    }

    /**
     * 校验文件扩展名
     * @param filename 文件名
     * @param extension 校验用的文件类型数组
     */
    public static void checkExtension(String filename, String... extension) {
        int index = filename.lastIndexOf(".");
        if (index < 0) {
            throw new FileException("不支持无法识别的文件类型");
        }
        // 校验文件后缀
        String suffix = filename.substring(index + 1).toLowerCase();
        boolean isAllow = false;
        for (String s : extension) {
            if (s.equals(suffix)) {
                isAllow = true;
                break;
            }
        }
        if (!isAllow) {
            throw new FileException("文件类型不支持");
        }
    }

    /**
     * 校验mimeType
     * @param filename 文件名
     * @param mimeType 文件mime
     * @param mimeTypeMap 校验用的mimeMap
     */
    public static void checkMimeType(String filename, String mimeType, Map<String, String> mimeTypeMap) {
        // 用文件后缀去匹配mimeType 匹配不成功或者对不上，都抛出异常
        String suffix = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        String mime = mimeTypeMap.get(suffix);
        if (mime == null || !mime.equals(mimeType)) {
            throw new FileException("文件类型不支持");
        }
    }

    /**
     * 获取文件头
     *
     * @param file 文件
     * @return 16 进制的文件头信息
     */
    private static String getFileHeader(MultipartFile file) {
        byte[] bytes = new byte[28];
        try {
            InputStream inputStream = file.getInputStream();
            inputStream.read(bytes, 0, 28);
            inputStream.close();
            return bytesToHex(bytes);
        } catch (Exception e) {
            throw new FileException("文件类型不支持");
        }
    }

    /**
     * 将字节数组转换成16进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * 校验魔数
     * @param filename 文件名
     * @param header 文件头
     * @param imageMagicMap 魔数map
     */
    private static void checkMagic(String filename, String header, Map<String, String> imageMagicMap) {
        String suffix = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        String magicCode = imageMagicMap.get(suffix);
        if (magicCode != null) {
            magicCode = magicCode.toLowerCase();
        }
        if (magicCode == null || !header.startsWith(magicCode)) {
            throw new FileException("文件类型不支持");
        }
    }

    /**
     * 校验文件大小
     * @param fileSize 当前文件大小
     * @param mSize 文件最大大小，单位/MB
     */
    public static void checkSize(long fileSize, Integer mSize) {
        long maxSize = mSize * 1024 * 1024;
        if (fileSize > maxSize) {
            throw new FileException(SystemErrorEnum.UPLOAD_MAX_SIZE_EXCEEDED_ERROR.getCode(), "文件大小不能超过" + mSize + "MB");
        }
    }

    /**
     * 获取文件完整路径
     * @param filename 文件名
     * @param uploadPath 上传路径
     * @return
     */
    public static String getFilePath(String filename, String uploadPath) {
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        int year = DateUtil.thisYear();
        int month = DateUtil.thisMonth() + 1;
        int day = DateUtil.thisDayOfMonth();
        String dir = year + "/" + month + "/" + day + "/";
        long snowflakeNextId = IdUtil.getSnowflakeNextId();
        // 文件命名 -> 上传路径/年/月/日/唯一id.后缀
        filename = uploadPath + dir + snowflakeNextId + "." + suffix;
        return filename;
    }

    /**
     * 校验下载路径
     * @param path 路径
     */
    public static void checkPath(String path, EasyFile easyFile) {
        if (StrUtil.isEmpty(path)) {
            throw new FileException(SystemErrorEnum.DOWNLOAD_ERROR.getCode(), "下载的文件路径为空");
        }
        // 判断文件是否存在
        File file = new File(path);
        if (!file.exists()) {
            throw new FileException(SystemErrorEnum.DOWNLOAD_ERROR.getCode(), "下载的文件不存在");
        }
        // 任务文件下载漏洞防护
        try {
            String canonicalFilePath = new File(easyFile.getFilePath()).getCanonicalPath();
            String canonicalImagePath = new File(easyFile.getImagePath()).getCanonicalPath();
            String parent = file.getCanonicalFile().getParent();
            if (!parent.startsWith(canonicalFilePath) && !parent.startsWith(canonicalImagePath)) {
                throw new FileException(SystemErrorEnum.DOWNLOAD_ERROR.getCode(), "下载的文件路径异常");
            }
        } catch (IOException e) {
            throw new FileException(SystemErrorEnum.DOWNLOAD_ERROR.getCode(), "下载的文件路径解析异常");
        }
    }

    /**
     * 获取Excel完整路径
     * @param excelPath excel上传路径
     * @param moduleName 模块名称
     * @return
     */
    public static String getFullPath(String excelPath, String moduleName) {
        String format = "yyyy-MM-dd";
        String date = DateUtil.date().toString(format);
        // 加入一个随机的uuid路径，防止同时操作一个文件
        String id = IdUtil.randomUUID();
        String fullPath = excelPath + date + "/" + id +"/";
        format = "yyyy-MM-dd HH_mm_ss";
        date = DateUtil.date().toString(format);
        String filename = moduleName + "-" + date + ".xlsx";
        fullPath = fullPath + filename;
        File file = new File(fullPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return fullPath;
    }

    /**
     * 获取映射路径
     * @param fullFilePath 完整路径
     * @param filePath 文件上传路径
     * @param fileMapPath 文件映射路径
     * @return
     */
    public static String getMapPath(String fullFilePath, String filePath, String fileMapPath) {
        return fullFilePath.replace(filePath, fileMapPath);
    }

    /**
     * 逆分析实际路径
     * @param path 路径
     * @param easyFile 文件配置
     * @return
     */
    public static String inverseAnalysis(String path, EasyFile easyFile) {
        path = path.substring(path.indexOf("/"));
        if (path.startsWith(easyFile.getFileMapPath())) {
            return path.replace(easyFile.getFileMapPath(), easyFile.getFilePath());
        } else if (path.startsWith(easyFile.getImageMapPath())) {
            return path.replace(easyFile.getImageMapPath(), easyFile.getImagePath());
        }
        throw new FileException("下载的文件路径异常");
    }

    /**
     * 获取excel类型
     * @param file
     * @return
     */
    public static ExcelTypeEnum getExcelType(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new FileException("文件名不能为空");
        }
        // 获取文件后缀
        String suffix = filename.substring(filename.lastIndexOf(".")).toLowerCase();
        if (ExcelTypeEnum.XLS.getValue().equals(suffix)) {
            return ExcelTypeEnum.XLS;
        } else if (ExcelTypeEnum.XLSX.getValue().equals(suffix)) {
            return ExcelTypeEnum.XLSX;
        } else if (ExcelTypeEnum.CSV.getValue().equals(suffix)) {
            return ExcelTypeEnum.CSV;
        }
        throw new FileException("文件类型不支持");
    }
}
