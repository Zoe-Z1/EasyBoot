<template>
  <div class="top">
    <el-dialog
      ref="maxDialog"
      v-drag
      :title="ruleForm.id ? '编辑${remarks!}' : '新增${remarks!}'"
      :visible.sync="dialogVisible"
      width="25rem"
      @closed="closedDialog"
    >
      <el-form
        ref="reform"
        v-loading="loading"
        :model="ruleForm"
        status-icon
        :rules="rules"
        :size="size"
        label-width="90px"
        :show-message="showMessage"
        element-loading-background="rgba(255, 255, 255, 0.8)"
        element-loading-text="数据正在加载中"
        element-loading-spinner="el-icon-loading"
      >
        <#list columns as column>
        <#if column.isForm == 0>
        <el-form-item label="${column.columnRemarks}" prop="${column.javaName}">
          <#if column.optElement == 'input'>
          <el-input
            v-model.trim="ruleForm.${column.javaName}"
            placeholder="请输入${column.columnRemarks}"
            type="text"
            :size="size"
            show-word-limit
            autocomplete="off"
          />
          </#if>
          <#if column.optElement == 'textarea'>
          <el-input
            v-model.trim="ruleForm.${column.javaName}"
            placeholder="请输入${column.columnRemarks}"
            type="text"
            :size="size"
            :rows="4"
            type="textarea"
            show-word-limit
            autocomplete="off"
          />
          </#if>
        </el-form-item>
        </#if>
        </#list>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button :size="size" @click="dialogVisible = false">取消</el-button>
        <el-button :size="size" :loading="loading" type="primary" @click="submitForm">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import { mixin } from '@/views/pages/mixin/save'
  export default {
    name: 'RoleSave',
    mixins: [mixin],
    data() {
      return {
        // 表单校验
        rules: {
          <#list columns as column>
          <#if column.isRequired == 0>
          ${column.javaName}: [{ required: true, message: '请输入${column.columnRemarks}', trigger: 'change' }],
          </#if>
          </#list>
        }
      }
    },
    methods: {

    }
  }
</script>
