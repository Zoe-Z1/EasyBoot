<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPkgName}">

<#if genBaseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${entityPkgName}">
    <#list columns as column>
    <#if column.isPrimaryKey == 0>
        <id column="${column.columnName}" property="${column.javaName}" />
    <#else >
        <result column="${column.columnName}" property="${column.javaName}" />
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
