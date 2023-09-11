package com.easy.boot.admin.generate.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/9/7
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("代码生成模板对象")
public class GenerateTemplate {

    @ApiModelProperty("Controller")
    private Template controller;

    @ApiModelProperty("Service")
    private Template service;

    @ApiModelProperty("ServiceImpl")
    private Template serviceImpl;

    @ApiModelProperty("Mapper")
    private Template mapper;

    @ApiModelProperty("Mapper.xml")
    private Template xml;

    @ApiModelProperty("Entity")
    private Template entity;

    @ApiModelProperty("CreateDTO")
    private Template createDTO;

    @ApiModelProperty("UpdateDTO")
    private Template updateDTO;

    @ApiModelProperty("Query")
    private Template query;

    @ApiModelProperty("VO")
    private Template vo;

    @ApiModelProperty("Sql")
    private Sql sql;

    public static GenerateTemplate defaultBuild(Boolean isOverride) {
        return GenerateTemplate.builder()
                .controller(new Template(isOverride))
                .service(new Template(isOverride))
                .serviceImpl(new Template(isOverride))
                .mapper(new Template(isOverride))
                .xml(new Template(isOverride))
                .entity(new Template(isOverride))
                .createDTO(new Template(isOverride))
                .updateDTO(new Template(isOverride))
                .query(new Template(isOverride))
                .vo(new Template(isOverride))
                .sql(new Sql(isOverride))
                .build();
    }
}
