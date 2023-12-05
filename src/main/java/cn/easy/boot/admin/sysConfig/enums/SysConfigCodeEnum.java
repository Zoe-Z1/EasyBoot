package cn.easy.boot.admin.sysConfig.enums;

/**
 * @author zoe
 * @date 2023/10/31
 * @description 系统配置编码枚举
 */
public enum SysConfigCodeEnum {

    /**
     * 数据字典获取方式 1：每次加载页面获取 2：全局缓存
     */
    DATA_DICT_GET_WAY("data_dict_get_way", "数据字典获取方式"),
    /**
     * RANDOM (随机),SLIDER (滑块验证码),ROTATE (旋转验证码),CONCAT (滑动还原验证码),WORD_IMAGE_CLICK (文字点选验证码)
     */
    CAPTCHA_TYPE("captcha_type", "验证码类型"),
    /**
     * IP解析接口地址
     */
    IP_PARSE_URL("ip_parse_url", "IP解析接口地址"),




    ;

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    SysConfigCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
