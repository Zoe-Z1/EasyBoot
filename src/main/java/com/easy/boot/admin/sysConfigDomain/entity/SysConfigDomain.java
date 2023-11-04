package com.easy.boot.admin.sysConfigDomain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "SysConfigDomain对象", description = "系统配置域")
public class SysConfigDomain extends BaseEntity {

    @ApiModelProperty("系统配置域编码")
    @TableField("code")
    private String code;

    @ApiModelProperty("系统配置域名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("系统配置域状态 1：正常 2：禁用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("配置域类型 #1：自定义配置， 2：模板配置")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("渲染组件名称")
    @TableField("component_name")
    private String componentName;

    @ApiModelProperty("关联模板ID")
    @TableField("template_id")
    private Long templateId;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;
}
