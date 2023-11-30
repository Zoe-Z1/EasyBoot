package com.easy.boot3.common.generator.config;

import cn.hutool.core.util.StrUtil;
import com.easy.boot3.common.generator.db.convert.ColumnConvertHandler;
import com.easy.boot3.exception.GeneratorException;
import lombok.*;

/**
 * @author zoe
 * @date 2023/8/15
 * @description 数据源参数配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceConfig {

    /**
     * 数据库连接地址
     */
    private String url;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库字段转换处理器
     */
    private ColumnConvertHandler columnConvertHandler;

    public String getUrl() {
        if (StrUtil.isEmpty(url)) {
            throw new GeneratorException("数据库连接url不能为空");
        }
        return url;
    }

    public String getUsername() {
        if (StrUtil.isEmpty(username)) {
            throw new GeneratorException("数据库连接账号不能为空");
        }
        return username;
    }

    public String getPassword() {
        if (StrUtil.isEmpty(password)) {
            throw new GeneratorException("数据库连接密码不能为空");
        }
        return password;
    }

    public ColumnConvertHandler getColumnConvertHandler() {
        if (columnConvertHandler == null) {
            columnConvertHandler = ColumnConvertHandler.defaultHandler();
        }
        return columnConvertHandler;
    }

}
