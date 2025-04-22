<template>
  <el-row :gutter="20">
    <!-- 左侧：个人信息卡片 -->
    <el-col :span="8">
      <el-card>
        <div class="text-center">
          <el-avatar :size="80" :src="user.avatar" />
          <h3>{{ user.name }}</h3>
          <p>{{ user.email }}</p>
          <p>{{ user.description }}</p>
        </div>
        <el-divider />
        <p><strong>注册时间：</strong>{{ user.create_time }}</p>
        <p><strong>更新时间：</strong>{{ user.update_time }}</p>
        <p><strong>手机号：</strong>{{ user.phone }}</p>
        <p><strong>生日：</strong>{{ user.birthday }}</p>
        <p><strong>角色：</strong>{{ user.role }}</p>
      </el-card>
    </el-col>

    <!-- 右侧：基本资料表单 -->
    <el-col :span="16">
      <el-card>
        <template #header>
          <span>基本资料</span>
        </template>
        <el-form :model="form" label-width="80px" :rules="rules" ref="formRef">
          <el-form-item label="昵称" prop="name">
            <el-input v-model="form.name" />
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" />
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="form.phone" />
          </el-form-item>
          <el-form-item label="生日" prop="birthday">
            <el-date-picker v-model="form.birthday" type="date" placeholder="选择日期" style="width: 100%"
              value-format="YYYY-MM-DD" />
          </el-form-item>
          <el-form-item label="简介" prop="description">
            <el-input v-model="form.description" type="textarea" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">保存</el-button>
            <el-button @click="onReset">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { self, updateSelf } from '@/apis/user'

interface User {
  name: string
  email: string
  phone: string
  avatar: string
  create_time: string
}

const user = ref<User>({
  name: '',
  email: '',
  phone: '',
  avatar: '',
  create_time: '',
})

const form = ref({})

const rules = {
  name: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
}

const formRef = ref()

const onSubmit = () => {
  formRef.value.validate((valid: boolean) => {
    if (valid) {
      updateSelf(form.value).then(res => {
        // user.value = res.data;
        // console.log(res)
        if (res.status == 200) {
          ElMessage.success('保存成功')
        } else {
          ElMessage.error('发生错误')
        }
      })

    } else {
      ElMessage.error('请完善表单信息')
    }
  })
}

const onReset = () => {
  form.value = { ...user.value }
}
onMounted(() => {
  self().then(res => {
    user.value = res.data;

    form.value.name = res.data.name
    form.value.email = res.data.email
    form.value.description = res.data.description
    form.value.phone = res.data.phone
    form.value.birthday = res.data.birthday
  })
})
</script>

<style scoped>
.text-center {
  text-align: center;
}
</style>
