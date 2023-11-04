<template>
  <div class="vue-container">
    <common-form
      ref="commonForm"
      :disabled="disabled"
      :options="options"
      @handlerClick="handlerClick"
      @advancedSearch="handlerQuery"
    >
      <template slot="advanced-content">
        <el-form ref="advancedForm" style="margin-top: 20px;" :model="queryForm" label-width="80px">
<#list columns as column>
  <#if column.isAdvancedSearch == 0>
    <#if column.dictDomainCode?? && column.dictDomainCode != "" && (column.columnRemarks!?index_of('#') > -1)>
          <el-form-item label="${column.columnRemarks!?substring(0, column.columnRemarks!?index_of('#'))?trim}">
    <#else >
          <el-form-item label="${column.columnRemarks!}">
    </#if>
    <#if column.optElement == 'input' || column.optElement == 'textarea' ||  column.optElement == 'inputnumber'>
            <el-input v-model="queryForm.${column.javaName}" clearable placeholder="请输入${column.columnRemarks!}"/>
    </#if>
    <#if column.optElement == 'select' || column.optElement == 'radio' || column.optElement == 'checkbox'>
      <#if column.dictDomainCode?? && column.dictDomainCode != "">
        <#if (column.columnRemarks!?index_of('#') > -1)>
            <el-select v-model="queryForm.${column.javaName}" clearable style="width: 100%;" placeholder="请选择${column.columnRemarks!?substring(0, column.columnRemarks!?index_of('#'))?trim}">
        <#else >
            <el-select v-model="queryForm.${column.javaName}" clearable style="width: 100%;" placeholder="请选择${column.columnRemarks!}">
        </#if>
              <el-option
                v-for="(item, index) in ${column.javaName}List"
                :key="index"
                :label="item.label"
                  <#if column.javaType == 'Integer' || column.javaType == 'Long'>
                :value="Number(item.code)"
                  <#else >
                :value="item.code"
                  </#if>
              />
            </el-select>
      <#else >
            <el-input
              v-model.trim="queryForm.${column.javaName}"
              placeholder="请输入${column.columnRemarks!}"
              type="text"
              show-word-limit
              autocomplete="off"
            />
      </#if>
    </#if>
    <#if column.optElement == 'timepicker' || column.optElement == 'datapicker' || column.optElement == 'datetimepicker'>
            <el-date-picker
              style="width: 100%;"
              v-model="queryForm.${column.javaName}"
              type="datetimerange"
              placeholder="选择时间"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
            />
    </#if>
          </el-form-item>
  </#if>
</#list>
        </el-form>
      </template>
    </common-form>
    <common-table
      :table-data="data"
      :columns="columns"
      :height="height"
      :current-page="queryForm.pageNum"
      :page-size="queryForm.pageSize"
      :total="totalPage"
      :loading="loading"
      @handleSizeChange="handleSizeChange"
      @handleCurrentChange="handleCurrentChange"
      @handleSelectionChange="handleSelectionChange"
    >
      <!-- 列表序号 -->
      <template #serialNumber="scope">
        <span>{{
          Number(scope.index + 1) + (queryForm.pageNum - 1) * queryForm.pageSize
        }}</span>
      </template>
    <#list columns as column>
      <#if column.dictDomainCode?? && column.dictDomainCode != "">
      <!-- ${column.columnRemarks!} -->
      <template #${column.javaName}="scope">
        <template v-for="(item, index) in ${column.javaName}List">
          <el-tag
            <#if column.javaType == 'Integer' || column.javaType == 'Long'>
            v-if="scope.row.${column.javaName} === Number(item.code)"
            <#else >
            v-if="scope.row.${column.javaName} === item.code"
            </#if>
            :key="index"
          >{{ item.label }}</el-tag>
        </template>
      </template>
      <#elseif column.listShow == 0 && (column.optElement == 'timepicker' || column.optElement == 'datepicker' || column.optElement == 'datetimepicker') >
      <!-- ${column.columnRemarks!} -->
      <template #${column.javaName}="scope">
        {{ scope.row.${column.javaName} | formatTime }}
      </template>
      </#if>
    </#list>
      <!-- 列表操作按钮 -->
      <template #operation="scope">
        <el-button
          v-throttle
          v-permission="'${permission}:update'"
          :size="size"
          type="text"
          icon="el-icon-edit-outline"
          @click.stop="handlerSave(scope.row)"
        >编辑</el-button>
        <el-button
          v-throttle
          v-permission="'${permission}:del'"
          :size="size"
          type="text"
          icon="el-icon-delete"
          style="color: #F56C6C;"
          @click.stop="handlerDel(funs['del'], scope.row)"
        >删除</el-button>
      </template>
    </common-table>
    <!-- 新增or编辑组件 -->
    <add-or-update ref="addOrUpdate" <#if hasDict>:obj="{ <#list columns as column><#if column.dictDomainCode?? && column.dictDomainCode != "">${column.javaName}List, </#if></#list> }"</#if> @ok="getList" />
    <#if global.enableImport>
    <!-- 全局导入组件 -->
    <to-channel ref="toChannel" title="${remarks!}导入" @change="getList" />
    </#if>
  </div>
</template>
<script>
<#--import { edit } from '@/api<#if uiModuleName??>/${uiModuleName}</#if>/${jsName}' // 后台数据接口-->
import { mixin } from '@/views/pages/mixin'
import AddOrUpdate from './components/save'
export default {
  name: '${className}',
  components: {
    AddOrUpdate
  },
  mixins: [mixin],
  data() {
    return {
      <#list columns as column>
      <#if column.dictDomainCode?? && column.dictDomainCode != "">
      ${column.javaName}List: [], // ${column.columnRemarks!}
      </#if>
      </#list>
      // 表头数组
      columns: [
        {
          type: 'selection',
          width: '60'
        },
        {
          prop: 'number',
          label: '序号',
          width: '70',
          type: 'slot',
          slotType: 'serialNumber'
        },
        <#list columns as column>
        <#if column.listShow == 0>
        {
          prop: '${column.javaName}',
          align: 'center',
          <#if (column.dictDomainCode?? && column.dictDomainCode != "") || column.optElement == 'timepicker' || column.optElement == 'datepicker' || column.optElement == 'datetimepicker'>
          type: 'slot',
          slotType: '${column.javaName}',
            <#if (column.columnRemarks!?index_of('#') > -1)>
          label: '${column.columnRemarks!?substring(0, column.columnRemarks!?index_of('#'))?trim}'
            <#else >
          label: '${column.columnRemarks!}'
            </#if>
          <#else >
          label: '${column.columnRemarks!}'
          </#if>
        },
        </#if>
        </#list>
        {
          type: 'slot',
          prop: 'operation',
          label: '操作',
          width: '168',
          slotType: 'operation',
          align: 'center',
          fixed: 'right'
        }
      ],
      <#if !global.enableImport || !global.enableExport>
      // 顶部表单按钮组
      options: [{
        type: 'button',
        icon: 'el-icon-refresh',
        handler: 'handlerRefresh',
        float: 'left',
        class: 'refreshBtn',
        text: '刷新'
      },
      {
        type: 'button',
        icon: 'el-icon-plus',
        handler: 'handlerSave',
        float: 'left',
        btnType: 'primary',
        permission: 'create',
        text: '新增'
      },
      <#if global.enableImport>
      {
        type: 'button',
        icon: 'el-icon-upload2',
        handler: 'handlerUpload',
        float: 'left',
        permission: 'import',
        text: '导入'
      },
      </#if>
      <#if global.enableExport>
      {
        type: 'button',
        icon: 'el-icon-download',
        handler: 'handlerExport',
        permission: 'export',
        btnType: 'info',
        float: 'left',
        text: '导出'
      },
      </#if>
      {
        type: 'button',
        icon: 'el-icon-delete',
        handler: 'handlerDels',
        class: 'deleteBtn',
        permission: 'batch:del',
        text: '删除',
        float: 'left'
      },
      {
        type: 'button',
        handler: 'handlerAdvanced',
        permission: 'page',
        class: 'refreshBtn',
        advanced: true,
        float: 'right',
        text: '高级查询'
      },
      {
        type: 'button',
        handler: 'handlerReset',
        permission: 'page',
        float: 'right',
        class: 'infoBtn',
        text: '重置'
      },
      {
        type: 'button',
        handler: 'handlerQuery',
        permission: 'page',
        float: 'right',
        text: '查询',
        btnType: 'primary'
      },
      {
        type: 'search',
        handler: 'handlerQuery',
        permission: 'page',
        bindValue: '',
        float: 'right',
        label: 'keyword',
        placeholder: '输入关键字搜索'
      }
      ]
      </#if>
    }
  },
  async created() {
    <#if hasDict>
    // 通过全局方法取数据字典
    </#if>
    <#list columns as column>
    <#if column.dictDomainCode?? && column.dictDomainCode != "">
    this.${column.javaName}List = await this.getDictInfo('${column.dictDomainCode}')
    </#if>
    </#list>
  },
  methods: {

  }
}
</script>
