<template>
    <div class="flex items-center box">
        <div class="title">{{ title }}</div>
        <slot></slot>
    </div>
</template>
<script setup lang='ts'>
defineProps({ title: String })
</script>
<style scoped>
.title {
    font-size: 18px;
    margin-right: 10px;
}

.box {
    line-height: 28px;
    height: 28px;
    margin: 4px 0 18px 16px;
}
</style>