import { getAction, postAction } from '@/api/manage'

// ${remarks!}列表
const list = (params) => getAction('${global.requestMappingPrefix!}/${table.moduleName}/page', params)
// 获取${remarks!}详情
const detail = (params) => getAction(`${global.requestMappingPrefix!}/${table.moduleName}/detail/${r'${params}'}`)
// 删除${remarks!}
const del = (params) => postAction(`${global.requestMappingPrefix!}/${table.moduleName}/delete/${r'${params}'}`)
// 批量删除${remarks!}
const batchDel = (params) => postAction(`${global.requestMappingPrefix!}/${table.moduleName}/batchDel`, params)
// 新增${remarks!}
const create = (params) => postAction('${global.requestMappingPrefix!}/${table.moduleName}/create', params)
// 编辑${remarks!}
const edit = (params) => postAction('${global.requestMappingPrefix!}/${table.moduleName}/update', params)
<#if global.enableExport>
// 导出${remarks!}
const exports = (params) => postAction('${global.requestMappingPrefix!}/${table.moduleName}/export', params, 'blob')
</#if>
<#if global.enableImport>
// 下载${remarks!}模板
const download = (params) => postAction('${global.requestMappingPrefix!}/${table.moduleName}/download', params, 'blob')
// 导入${remarks!}
const imports = (params) => postAction('${global.requestMappingPrefix!}/${table.moduleName}/import', params)
</#if>

export {
  list,
  detail,
  del,
  batchDel,
  create,
  edit,
<#if global.enableExport>
  exports,
</#if>
<#if global.enableImport>
  download,
  imports
</#if>
}
