<template>
  <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" label-position="right">
    <el-form-item label="厕所名称" prop="name">
      <el-input v-model="form.name" placeholder="请输入厕所名称" />
    </el-form-item>

    <el-form-item label="位置" prop="location">
      <el-input v-model="form.location" placeholder="请输入位置" />
    </el-form-item>

    <el-form-item label="状态" prop="status">
      <el-select v-model.number="form.status" placeholder="请选择状态">
        <el-option label="正常" :value="1" />
        <el-option label="异常" :value="2" />
        <el-option label="维护中" :value="3" />
        <el-option label="关闭" :value="4" />
      </el-select>
    </el-form-item>

    <el-form-item label="坑位数量" prop="pits">
      <el-input-number v-model="form.pits" :min="0" :max="20" />
    </el-form-item>

    <el-form-item label="设备数量" prop="devices">
      <el-input-number v-model="form.devices" :min="0" :max="50" />
    </el-form-item>

    <el-form-item label="备注" prop="description">
      <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入备注信息" />
    </el-form-item>

    <el-form-item class="flex justify-end gap-3 mt-6">
      <el-button @click="$emit('cancel')">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { ToiletItem } from '@/types'

const props = defineProps<{
  toilet: ToiletItem
}>()

const emit = defineEmits(['submit', 'cancel'])

const formRef = ref<FormInstance>()

const form = reactive({
  id: props.toilet.id,
  name: props.toilet.name,
  location: props.toilet.location,
  status: props.toilet.status,
  pits: props.toilet.pits,
  devices: props.toilet.devices,
  description: props.toilet.description
})

const rules = reactive<FormRules>({
  name: [
    { required: true, message: '请输入厕所名称', trigger: 'blur' },
    { min: 2, max: 20, message: '长度在2到20个字符', trigger: 'blur' }
  ],
  location: [
    { required: true, message: '请输入位置', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ],
  pits: [
    { required: true, message: '请输入坑位数量', trigger: 'blur' }
  ],
  devices: [
    { required: true, message: '请输入设备数量', trigger: 'blur' }
  ]
})

// 监听props变化更新表单
watch(() => props.toilet, (newVal) => {
  Object.assign(form, newVal)
}, { deep: true })

const submitForm = async () => {
  if (!formRef.value) return

  try {
    await formRef.value.validate()
    emit('submit', { ...form })
  } catch (error) {
    console.error('表单验证失败:', error)
  }
}
</script>
