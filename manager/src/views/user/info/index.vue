<template>
  <el-row :gutter="20">
    <!-- 左侧：个人信息卡片 -->
    <el-col :span="8">
      <el-card>
        <div class="text-center">
          <el-avatar :size="80" :src="user.avatar" />
          <h3>{{ user.name }}</h3>
          <p>{{ user.email }}</p>
        </div>
        <el-divider />
        <p><strong>注册时间：</strong>{{ user.registeredAt }}</p>
        <p><strong>手机号：</strong>{{ user.phone }}</p>
        <p><strong>所属企业：</strong>{{ user.company }}</p>
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
          <el-form-item label="首页链接" prop="homeUrl">
            <el-input v-model="form.homeUrl" />
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
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

interface User {
  name: string
  email: string
  phone: string
  avatar: string
  registeredAt: string
  company: string
  homeUrl: string
}

const user = ref<User>({
  name: '超级管理员',
  email: 'admin@example.com',
  phone: '17356777777',
  avatar: 'https://example.com/avatar.jpg',
  registeredAt: '2020-04-10 09:40:33',
  company: '杭州诚聚',
  homeUrl: 'http://www.baidu.com'
})

const form = ref({ ...user.value })

const rules = {
  name: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  homeUrl: [{ required: true, message: '请输入首页链接', trigger: 'blur' }]
}

const formRef = ref()

const onSubmit = () => {
  formRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success('保存成功')
    } else {
      ElMessage.error('请完善表单信息')
    }
  })
}

const onReset = () => {
  form.value = { ...user.value }
}
</script>

<style scoped>
.text-center {
  text-align: center;
}
</style>
