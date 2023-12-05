package cn.easy.boot3.common.redis;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @describe redis 前缀/后缀 - key  枚举
 * @date 2023/7/22
 */
public enum RedisKeyEnum {

    /**
     * 后台登录验证码验证成功回话key
     */
    ADMIN_LOGIN_CAPTCHA("admin:login:captcha:","session"),

    /**
     * 防重复提交key
     */
    NO_REPEAT_SUBMIT("no:repeat:","submit"),

    /**
     * 锁key
     */
    LOCK("easy:redis:","lock"),

    /**
     * 用户详情key
     */
    USER_DETAIL("admin:user:","detail"),

    /**
     * 登录缓存用户详情
     */
    SA_USER_DETAIL("sa:admin:user:","detail"),

    /**
     * 用户信息key
     */
    USER_INFO("admin:user:","info"),

    /**
     * 用户黑名单key
     */
    USER_BLACKLIST("admin:blacklist:","user"),

    /**
     * IP黑名单key
     */
    IP_BLACKLIST("admin:blacklist:","ip"),

    /**
     * 黑名单详情key
     */
    BLACKLIST_DETAIL("admin:blacklist:","detail"),

    /**
     * 不永久拉黑的黑名单列表key
     */
    NOT_FOREVER_BLACKLIST("admin:blacklist:not:forever","list"),


    ;

    /**
     * key前缀
     */
    private String prefix;

    /**
     * key后缀
     */
    private String suffix;

    private String getPrefix() {
        return prefix;
    }

    private String getSuffix() {
        return suffix;
    }

    /**
     * 获取完整key -->> 带唯一参数
     * @param value
     * @return
     */
    public String getKey(Object value) {
        return this.getPrefix() + value + ":" + this.getSuffix();
    }

    /**
     * 获取完整key -->> 带唯一参数
     * @param values
     * @return
     */
    public List<String> getKeys(List<?> values) {
        return values.stream().map(item -> prefix + item + ":" + suffix).collect(Collectors.toList());
    }

    /**
     * 获取完整key --> 不带唯一参数
     * @return
     */
    public String getKey() {
        return this.getPrefix() + this.getSuffix();
    }

    RedisKeyEnum(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

}
