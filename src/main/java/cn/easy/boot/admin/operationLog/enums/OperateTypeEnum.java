package cn.easy.boot.admin.operationLog.enums;

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
     * 其他
     */
    OTHER("其他"),

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
     * 创建
     */
    CREATE("创建"),

    /**
     * 编辑
     */
    UPDATE("编辑"),

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
    GENERATE("生成代码"),

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

    /**
     * 强制下线
     */
    KICKOUT("强制下线"),


    ;

    private String msg;

    public String getMsg(){
        return this.msg;
    }

    OperateTypeEnum(String msg){
        this.msg = msg;
    }
}
