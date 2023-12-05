package cn.easy.boot3.admin.sysConfigDomain.entity;

import cn.easy.boot3.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/07/29
* @description 系统配置域 实体
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_config_domain")
@Schema(title = "SysConfigDomain对象", description = "系统配置域")
public class SysConfigDomain extends BaseEntity {

    @Schema(title = "系统配置域编码")
    @TableField("code")
    private String code;

    @Schema(title = "系统配置域名称")
    @TableField("name")
    private String name;

    @Schema(title = "系统配置域状态 1：正常 2：禁用")
    @TableField("status")
    private Integer status;

    @Schema(title = "配置域类型 #1：自定义配置， 2：模板配置")
    @TableField("type")
    private Integer type;

    @Schema(title = "跳转组件路径")
    @TableField("path")
    private String path;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "关联模板ID")
    @TableField("template_id")
    private Long templateId;

    @Schema(title = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(title = "备注")
    @TableField("remarks")
    private String remarks;
}
