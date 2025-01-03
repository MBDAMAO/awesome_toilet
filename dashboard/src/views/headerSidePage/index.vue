<template>
    <div class="h-full w-full">
        <HeaderLayout>
            <template #head>

                <Head></Head>
            </template>
            <template #side>
                <Side></Side>
            </template>
            <template #main>
                <RouterView></RouterView>
            </template>
        </HeaderLayout>
    </div>
</template>
<script setup lang='ts'>
import HeaderLayout from '@/layout/HeaderLayout.vue';
import Head from '@/components/head/index.vue'
import Side from '@/components/side/index.vue'
</script>
<style scoped></style>