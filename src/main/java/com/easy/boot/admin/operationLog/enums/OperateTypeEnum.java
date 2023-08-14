package com.easy.boot.admin.operationLog.enums;

/**
 * @author zoe
 * @describe 操作类别枚举
 * @date 2023/7/22
 */
public enum OperateTypeEnum {

    /**
     * 未知
     */
    UNKNOWN("未知"),

    /**
     * 登录
     */
    LOGIN("登录"),

    /**
     * 登出
     */
    LOGOUT("登出"),

    /**
     * 查询
     */
    SELECT("查询"),

    /**
     * 新增
     */
    INSERT("新增"),

    /**
     * 修改
     */
    UPDATE("修改"),

    /**
     * 删除
     */
    DELETE("删除"),

    /**
     * 上传
     */
    UPLOAD("上传"),

    /**
     * 下载
     */
    DOWNLOAD("下载"),

    /**
     * 导出
     */
    EXPORT("导出"),

    /**
     * 导入
     */
    IMPORT("导入"),

    /**
     * 生成代码
     */
    GENCODE("生成代码"),

    /**
     * 清空数据
     */
    CLEAR("清空数据"),

    /**
     * 执行定时任务
     */
    START_JOB("执行定时任务"),

    /**
     * 恢复/暂停定时任务
     */
    RESUME_OR_PAUSE_JOB("恢复/暂停定时任务"),


    ;

    private String msg;

    public String getMsg(){
        return this.msg;
    }

    OperateTypeEnum(String msg){
        this.msg = msg;
    }
}
