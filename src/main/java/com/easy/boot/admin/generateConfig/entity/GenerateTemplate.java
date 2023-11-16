package com.easy.boot.admin.generateConfig.entity;

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
    private UpdateDTO updateDTO;

    @ApiModelProperty("Query")
    private Template query;

    @ApiModelProperty("VO")
    private Template vo;

    @ApiModelProperty("Sql")
    private Sql sql;

    @ApiModelProperty("index.vue")
    private Template indexVue;

    @ApiModelProperty("save.vue")
    private Template saveVue;

    @ApiModelProperty("js")
    private Template js;

    public static GenerateTemplate defaultBuild() {
        return GenerateTemplate.builder()
                .controller(new Template())
                .service(new Template())
                .serviceImpl(new Template())
                .mapper(new Template())
                .xml(new Template())
                .entity(new Template())
                .createDTO(new Template())
                .updateDTO(new UpdateDTO())
                .query(new Template())
                .vo(new Template())
                .sql(new Sql())
                .indexVue(new Template())
                .saveVue(new Template())
                .js(new Template())
                .build();
    }
}
