<template>
  <div class="top">
    <common-form
      ref="commonForm"
      :disabled="disabled"
      :options="options"
      @handlerClick="handlerClick"
      @advancedSearch="getList"
    >
      <template slot="advanced-content">
        <el-form ref="advancedForm" style="margin-top: 20px;" :model="queryForm" label-width="80px">
          <el-form-item label="用户名">
            <el-input v-model="queryForm.username" clearable />
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="queryForm.name" clearable />
          </el-form-item>
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
      <#if column.dictDomainCode??>
      <!-- ${column.columnRemarks!} -->
      <template #${column.javaName}="scope">
        <template v-for="(item, index) in ${column.javaName}List">
          <el-tag
            v-if="scope.row.${column.javaName} == item.code"
            :key="index"
          >{{ item.label }}</el-tag>
        </template>
      </template>
      </#if>
      </#list>
      </template>
      <!-- 列表操作按钮 -->
      <template #operation="scope">
        <el-button
          v-throttle
          v-permission="'system:user:edit'"
          :size="size"
          type="text"
          icon="el-icon-edit-outline"
          @click.stop="handlerSave(scope.row)"
        >编辑</el-button>
        <el-button
          v-throttle
          v-permission="'system:user:del'"
          :size="size"
          type="text"
          icon="el-icon-delete"
          style="color: red"
          @click.stop="handlerDel(funs['del'], scope.row)"
        >删除</el-button>
      </template>
    </common-table>
    <!-- 新增or编辑组件 -->
    <add-or-update ref="addOrUpdate" <#if hasDict>:dict="{ <#list columns as column><#if column.dictDomainCode??>${column.javaName}List, </#if></#list> }"</#if> @ok="getList" />
    <!-- 全局导入组件 -->
    <to-channel ref="toChannel" title="${remarks!}导入" @change="getList" />
  </div>
</template>
<script>
  import { edit } from '@/api/system/user' // 后台数据接口
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
        <#if column.dictDomainCode??>
        ${column.javaName}List: [], // ${column.columnRemarks!}数组
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
          {
            prop: '${column.javaName}',
            label: '${column.columnRemarks!}',
            align: 'center',
            <#if column.dictDomainCode??>
            type: 'slot',
            slotType: '${column.javaName}'
            </#if>
          },
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
        ]
      }
    },
    async created() {
      <#if hasDict>
      // 通过全局方法取数据字典
      </#if>
      <#list columns as column>
      <#if column.dictDomainCode??>
      this.${column.javaName}List = await this.getDictInfo('${column.dictDomainCode}')
      </#if>
      </#list>
    },
    methods: {
      // 账号状态切换触发
      switchChange(e, form) {
        form.status = e === 1 ? 2 : 1
      },
      // 确认禁用或启用
      popConfirm(form) {
        this.loading = true
        form.status = form.status === 1 ? 2 : 1
        edit(form).then((res) => {
          if (res.code === 200) this.$message.success(form.status === 1 ? '账号已启用' : '账号已禁用')
        }).finally(() => {
          this.loading = false
        })
      }
    }
  }
</script>
