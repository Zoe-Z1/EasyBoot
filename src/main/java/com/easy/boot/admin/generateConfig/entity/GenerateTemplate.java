package com.easy.boot.admin.generateConfig.entity;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "代码生成模板对象")
public class GenerateTemplate {

    @Schema(title = "Controller")
    private Template controller;

    @Schema(title = "Service")
    private Template service;

    @Schema(title = "ServiceImpl")
    private Template serviceImpl;

    @Schema(title = "Mapper")
    private Template mapper;

    @Schema(title = "Mapper.xml")
    private Template xml;

    @Schema(title = "Entity")
    private Template entity;

    @Schema(title = "CreateDTO")
    private Template createDTO;

    @Schema(title = "UpdateDTO")
    private UpdateDTO updateDTO;

    @Schema(title = "Query")
    private Template query;

    @Schema(title = "VO")
    private Template vo;

    @Schema(title = "Sql")
    private Sql sql;

    @Schema(title = "index.vue")
    private Template indexVue;

    @Schema(title = "save.vue")
    private Template saveVue;

    @Schema(title = "js")
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
