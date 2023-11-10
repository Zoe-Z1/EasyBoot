<#if parentMenuId??>
-- 菜单 SQL
insert into menu (id, parent_id, label, path, name, component, permission, cache, type, status, show_status, sort, create_by, create_username, create_time)
values(${menuId?c}, ${parentMenuId?c}, '${remarks!}', '${menuPath}', '${className}', '${component}/index', '${permission}', 1, 2, 1, 1, 0, '${createBy?c}', '${createUsername}', '${createTime?c}');

-- 接口 SQL
<#if queryEnable>
insert into menu (id, parent_id, label, permission, type, status, show_status, sort, create_by, create_username, create_time)
values(${pageMenuId?c}, ${menuId?c}, '分页获取${remarks!}列表', '${permission}:page', 3, 1, 1, 0, '${createBy?c}', '${createUsername}', '${createTime?c}');

<#else >
insert into menu (id, parent_id, label, permission, type, status, show_status, sort, create_by, create_username, create_time)
values(${pageMenuId?c}, ${menuId?c}, '获取${remarks!}列表', '${permission}:list', 3, 1, 1, 0, '${createBy?c}', '${createUsername}', '${createTime?c}');

</#if>
insert into menu (id, parent_id, label, permission, type, status, show_status, sort, create_by, create_username, create_time)
values(${detailMenuId?c}, ${menuId?c}, '获取${remarks!}详情', '${permission}:detail', 3, 1, 1, 0, '${createBy?c}', '${createUsername}', '${createTime?c}');

insert into menu (id, parent_id, label, permission, type, status, show_status, sort, create_by, create_username, create_time)
values(${createMenuId?c}, ${menuId?c}, '创建${remarks!}', '${permission}:create', 3, 1, 1, 0, '${createBy?c}', '${createUsername}', '${createTime?c}');

insert into menu (id, parent_id, label, permission, type, status, show_status, sort, create_by, create_username, create_time)
values(${updateMenuId?c}, ${menuId?c}, '编辑${remarks!}', '${permission}:update', 3, 1, 1, 0, '${createBy?c}', '${createUsername}', '${createTime?c}');

insert into menu (id, parent_id, label, permission, type, status, show_status, sort, create_by, create_username, create_time)
values(${delMenuId?c}, ${menuId?c}, '删除${remarks!}', '${permission}:del', 3, 1, 1, 0, '${createBy?c}', '${createUsername}', '${createTime?c}');

insert into menu (id, parent_id, label, permission, type, status, show_status, sort, create_by, create_username, create_time)
values(${batchDelMenuId?c}, ${menuId?c}, '批量删除${remarks!}', '${permission}:batch:del', 3, 1, 1, 0, '${createBy?c}', '${createUsername}', '${createTime?c}');

<#if global.enableImport>
insert into menu (id, parent_id, label, permission, type, status, show_status, sort, create_by, create_username, create_time)
values(${importMenuId?c}, ${menuId?c}, '导入${remarks!}', '${permission}:import', 3, 1, 1, 0, '${createBy?c}', '${createUsername}', '${createTime?c}');

insert into menu (id, parent_id, label, permission, type, status, show_status, sort, create_by, create_username, create_time)
values(${downloadMenuId?c}, ${menuId?c}, '下载${remarks!}导入模板', '${permission}:download', 3, 1, 1, 0, '${createBy?c}', '${createUsername}', '${createTime?c}');

</#if>
<#if global.enableExport>
insert into menu (id, parent_id, label, permission, type, status, show_status, sort, create_by, create_username, create_time)
values(${exportMenuId?c}, ${menuId?c}, '导出${remarks!}', '${permission}:export', 3, 1, 1, 0, '${createBy?c}', '${createUsername}', '${createTime?c}');
</#if>

</#if>

<#if (dictSqls?size > 0)>
-- 数据字典域SQL
</#if>

<#list dictSqls as dictSql>
insert into data_dict_domain (id, code, name, remarks, create_by, create_username, create_time)
values(${dictSql.domainId?c}, '${dictSql.code}', '${dictSql.name}', '${dictSql.remarks}', '${createBy?c}', '${createUsername}', '${createTime?c}');

</#list>

<#if (dictSqls?size > 0)>
-- 数据字典SQL
</#if>

<#list dictSqls as dictSql>
    <#list dictSql.list as dict>
insert into data_dict (id, domain_id, code, label, create_by, create_username, create_time)
values(${dict.id?c}, ${dictSql.domainId?c}, '${dict.code}', '${dict.label}', '${createBy?c}', '${createUsername}', '${createTime?c}');

    </#list>
</#list>