<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPkgName}">

<#if genBaseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${entityPkgName}">
    <#list fields as field>
    <#if field.isPrimaryKey>
        <id column="${field.name}" property="${field.javaName}" />
    <#else >
        <result column="${field.name}" property="${field.javaName}" />
    </#if>
    </#list>
    </resultMap>
</#if>

<#if genBaseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        ${baseColumnList}
    </sql>

</#if>
</mapper>
