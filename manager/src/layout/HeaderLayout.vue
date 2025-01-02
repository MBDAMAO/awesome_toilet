<template>
    <div class="common-layout h-full w-full">
        <el-container>
            <el-header>
                <slot name="head"></slot>
            </el-header>
            <el-container>
                <el-aside>
                    <slot name="side"></slot>
                </el-aside>
                <el-main>
                    <slot name="main"></slot>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<script setup lang='ts'>
</script>
<style scoped>
.el-container {
    height: 100vh;
}

.el-header {
    padding: 0;
    height: 50px;
}

.el-aside {
    padding: 0;
    width: auto;
}

.el-main {
    padding: 16px;
}
</style>