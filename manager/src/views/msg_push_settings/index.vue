<template>
    <div class="alert-push-management">
        <h2>告警账号推送管理</h2>

        <el-card class="management-card">
            <div class="header">
                <h3>推送方式配置</h3>
                <el-button type="primary" @click="addNewConfig">新增配置</el-button>
            </div>

            <el-table :data="pushConfigs" style="width: 100%" border>
                <el-table-column prop="name" label="配置名称" width="180" />
                <el-table-column prop="type" label="推送类型" width="120">
                    <template #default="{ row }">
                        <el-tag :type="row.type === 'email' ? 'success' : 'info'">
                            {{ row.type === 'email' ? '邮箱' : row.type }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="account" label="推送账号" />
                <el-table-column prop="status" label="状态" width="100">
                    <template #default="{ row }">
                        <el-switch v-model="row.status" active-value="enabled" inactive-value="disabled"
                            @change="handleStatusChange(row)" />
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180">
                    <template #default="{ row }">
                        <el-button size="small" @click="editConfig(row)">编辑</el-button>
                        <el-button size="small" type="danger" @click="deleteConfig(row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <!-- 新增/编辑配置对话框 -->
        <el-dialog v-model="dialogVisible" :title="isEditing ? '编辑推送配置' : '新增推送配置'" width="50%">
            <el-form :model="currentConfig" label-width="120px">
                <el-form-item label="配置名称" required>
                    <el-input v-model="currentConfig.name" placeholder="请输入配置名称" />
                </el-form-item>

                <el-form-item label="推送类型" required>
                    <el-select v-model="currentConfig.type" placeholder="请选择推送类型">
                        <el-option label="邮箱" value="email" />
                        <!-- 可以添加其他推送类型 -->
                    </el-select>
                </el-form-item>

                <template v-if="currentConfig.type === 'email'">
                    <el-form-item label="邮箱地址" required>
                        <el-input v-model="currentConfig.account" placeholder="请输入邮箱地址" />
                    </el-form-item>

                    <el-form-item label="SMTP服务器">
                        <el-input v-model="currentConfig.smtpServer" placeholder="smtp.example.com" />
                    </el-form-item>

                    <el-form-item label="SMTP端口">
                        <el-input-number v-model="currentConfig.smtpPort" :min="1" :max="65535" />
                    </el-form-item>

                    <el-form-item label="SSL加密">
                        <el-switch v-model="currentConfig.sslEnabled" />
                    </el-form-item>
                </template>

                <el-form-item label="状态">
                    <el-switch v-model="currentConfig.status" active-value="enabled" inactive-value="disabled" />
                </el-form-item>
            </el-form>

            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="saveConfig">保存</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { PushConfig } from '@/types/alert'

// 推送配置数据
const pushConfigs = ref<PushConfig[]>([
    {
        id: '1',
        name: '主邮箱通知',
        type: 'email',
        account: 'admin@example.com',
        smtpServer: 'smtp.example.com',
        smtpPort: 465,
        sslEnabled: true,
        status: 'enabled'
    },
    {
        id: '2',
        name: '备用邮箱通知',
        type: 'email',
        account: 'backup@example.com',
        smtpServer: 'smtp.backup.com',
        smtpPort: 587,
        sslEnabled: false,
        status: 'disabled'
    }
])

// 对话框控制
const dialogVisible = ref(false)
const isEditing = ref(false)

// 当前编辑的配置
const currentConfig = reactive<PushConfig>({
    id: '',
    name: '',
    type: 'email',
    account: '',
    smtpServer: '',
    smtpPort: 465,
    sslEnabled: true,
    status: 'enabled'
})

// 加载配置数据
const loadConfigs = async () => {
    try {
        // 这里替换为实际的API调用
        // const res = await getPushConfigs()
        // pushConfigs.value = res.data
    } catch (error) {
        ElMessage.error('加载配置失败')
    }
}

// 新增配置
const addNewConfig = () => {
    isEditing.value = false
    Object.assign(currentConfig, {
        id: '',
        name: '',
        type: 'email',
        account: '',
        smtpServer: '',
        smtpPort: 465,
        sslEnabled: true,
        status: 'enabled'
    })
    dialogVisible.value = true
}

// 编辑配置
const editConfig = (config: PushConfig) => {
    isEditing.value = true
    Object.assign(currentConfig, JSON.parse(JSON.stringify(config)))
    dialogVisible.value = true
}

// 保存配置
const saveConfig = async () => {
    if (!currentConfig.name || !currentConfig.account) {
        ElMessage.warning('请填写完整配置信息')
        return
    }

    try {
        if (isEditing.value) {
            // 更新操作
            const index = pushConfigs.value.findIndex(item => item.id === currentConfig.id)
            if (index !== -1) {
                pushConfigs.value[index] = { ...currentConfig }
            }
        } else {
            // 新增操作
            currentConfig.id = Date.now().toString()
            pushConfigs.value.unshift({ ...currentConfig })
        }

        dialogVisible.value = false
        ElMessage.success('保存成功')

        // 这里可以添加实际的API调用
        // await savePushConfig(currentConfig)
    } catch (error) {
        ElMessage.error('保存失败')
    }
}

// 删除配置
const deleteConfig = async (id: string) => {
    try {
        await ElMessageBox.confirm('确定要删除此推送配置吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        })

        pushConfigs.value = pushConfigs.value.filter(item => item.id !== id)
        ElMessage.success('删除成功')

        // 这里可以添加实际的API调用
        // await deletePushConfig(id)
    } catch {
        // 用户取消了删除
    }
}

// 状态变更处理
const handleStatusChange = async (config: PushConfig) => {
    try {
        ElMessage.success(`已${config.status === 'enabled' ? '启用' : '禁用'}配置`)
        // 这里可以添加实际的API调用
        // await updatePushConfigStatus(config.id, config.status)
    } catch (error) {
        ElMessage.error('状态更新失败')
    }
}

onMounted(() => {
    loadConfigs()
})
</script>

<style scoped>
.alert-push-management {
    padding: 20px;
}

.management-card {
    margin-top: 20px;
}

.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}
</style>