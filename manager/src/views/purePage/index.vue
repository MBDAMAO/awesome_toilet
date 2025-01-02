<template>
    <PureLayout>
        <RouterView></RouterView>
    </PureLayout>
</template>
<script setup lang='ts'>
import { RouterView } from 'vue-router';
import PureLayout from '@/layout/PureLayout.vue';
</script>
<style scoped></style>