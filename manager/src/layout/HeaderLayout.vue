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
.el-header {
    padding: 0;
    height: 50px;
}

.el-aside {
    padding: 0;
    width: auto;
    height: calc(100vh - 50px);
}

.el-main {
    padding: 16px;
    height: calc(100vh - 50px);
}
</style>