<template>
  <div class="vue-container">
    <el-dialog
      ref="maxDialog"
      v-drag
      :title="ruleForm.id ? '编辑${remarks!}' : '新增${remarks!}'"
      :visible.sync="dialogVisible"
      width="450px"
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
        <el-row>
          <#list columns as column>
          <#if column.isForm == 0>
          <el-col<#if global.colSpan != 24> :span="${global.colSpan}"</#if>>
              <#if column.dictDomainCode?? && column.dictDomainCode != "" && (column.columnRemarks!?index_of('#') > -1)>
            <el-form-item label="${column.columnRemarks!?substring(0, column.columnRemarks!?index_of('#'))?trim}" prop="${column.javaName}">
              <#else >
            <el-form-item label="${column.columnRemarks!}" prop="${column.javaName}">
              </#if>
              <#if column.optElement == 'input'>
              <el-input
                v-model.trim="ruleForm.${column.javaName}"
                placeholder="请输入${column.columnRemarks}"
                type="text"
                show-word-limit
                autocomplete="off"
              />
              </#if>
              <#if column.optElement == 'textarea'>
              <el-input
                v-model.trim="ruleForm.${column.javaName}"
                placeholder="请输入${column.columnRemarks}"
                :rows="4"
                type="textarea"
                show-word-limit
                autocomplete="off"
              />
              </#if>
              <#if column.optElement == 'select'>
                <#if column.dictDomainCode?? && column.dictDomainCode != "" && (column.columnRemarks!?index_of('#') > -1)>
              <el-select v-model="ruleForm.${column.javaName}" style="width: 100%;" placeholder="请选择${column.columnRemarks!?substring(0, column.columnRemarks!?index_of('#'))?trim}">
                <el-option
                  v-for="(item, index) in obj.${column.javaName}List"
                  :key="index"
                  :label="item.label"
                  :value="<#if column.javaType == 'Integer' || column.javaType == 'Long'>Number(item.code)<#else >item.code</#if>"
                />
              </el-select>
                <#else >
              <el-input
                v-model.trim="ruleForm.${column.javaName}"
                placeholder="请输入${column.columnRemarks}"
                type="text"
                show-word-limit
                autocomplete="off"
              />
                </#if>
              </#if>
              <#if column.optElement == 'radio'>
                <#if column.dictDomainCode?? && column.dictDomainCode != "">
              <el-radio
                v-model="ruleForm.${column.javaName}"
                v-for="(item, index) in obj.${column.javaName}List"
                :key="index"
                :label="<#if column.javaType == 'Integer' || column.javaType == 'Long'>Number(item.code)<#else >item.code</#if>">
                {{ item.label }}
              </el-radio>
                <#else >
              <el-input
                v-model.trim="ruleForm.${column.javaName}"
                placeholder="请输入${column.columnRemarks}"
                type="text"
                show-word-limit
                autocomplete="off"
              />
                </#if>
              </#if>
              <#if column.optElement == 'checkbox'>
                <#if column.dictDomainCode?? && column.dictDomainCode != "">
              <el-checkbox-group v-model="ruleForm.${column.javaName}">
                <el-checkbox
                  v-for="(item, index) in obj.${column.javaName}List"
                  :key="index"
                  :label="item.code"
                >{{ item.label }}</el-checkbox>
              </el-checkbox-group>
                <#else >
              <el-input
                v-model.trim="ruleForm.${column.javaName}"
                placeholder="请输入${column.columnRemarks}"
                type="text"
                show-word-limit
                autocomplete="off"
              />
                </#if>
              </#if>
              <#if column.optElement == 'timepicker'>
              <el-time-picker
                v-model="ruleForm.${column.javaName}"
                placeholder="选择时间"
              />
              </#if>
              <#if column.optElement == 'datapicker'>
              <el-date-picker
                v-model="ruleForm.${column.javaName}"
                type="date"
                placeholder="选择日期"
              />
              </#if>
              <#if column.optElement == 'datetimepicker'>
              <el-date-picker
                v-model="ruleForm.${column.javaName}"
                type="datetime"
                placeholder="选择日期时间"
              />
              </#if>
              <#if column.optElement == 'inputnumber'>
              <el-input-number
                v-model="ruleForm.${column.javaName}"
                :min="0"
                placeholder="请输入${column.columnRemarks}"
                style="width: 100%;"
              />
              </#if>
            </el-form-item>
          </el-col>
          </#if>
          </#list>
        </el-row>
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
  name: '${className}Save',
  mixins: [mixin],
  data() {
    return {
      // 表单校验
      rules: {
        <#list columns as column>
        <#if column.isRequired == 0>
          <#if column.dictDomainCode?? && column.dictDomainCode != "" && (column.columnRemarks!?index_of('#') > -1)>
        ${column.javaName}: [{ required: true, message: '请输入${column.columnRemarks!?substring(0, column.columnRemarks!?index_of('#'))?trim}', trigger: 'change' }],
          <#else >
        ${column.javaName}: [{ required: true, message: '请输入${column.columnRemarks}', trigger: 'change' }],
          </#if>
        </#if>
        </#list>
      }
    }
  },
  methods: {
    // 弹出窗口加载
    openDialog(rows) {
      this.dialogVisible = true
      if (rows) {
        this.ruleForm = { ...this.ruleForm, ...rows }
      } else {
        this.ruleForm = { }
      }
    },
  }
}
</script>
