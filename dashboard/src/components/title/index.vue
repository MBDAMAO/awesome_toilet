<template>
    <div class="title">{{ title }}</div>
</template>
<script setup lang='ts'>
defineProps({ title: String })
</script>
<style scoped>
.title {
    font-size: 18px;
    color: rgba(0, 0, 0, .85);
    line-height: 28px;
    margin: 4px 0 18px 16px;
}
</style>