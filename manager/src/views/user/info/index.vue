<template>
    <Title :title="'账号信息'"></Title>
    <div class="flex w-full">
        <Container class="w-1/3 h-[600px] mr-4 flex flex-col">
            <Title :title="'基本信息'"></Title>
            <div>
                <div class="h-[100px] w-[100px] rounded-full overflow-hidden">
                    <img src="https://damaoz.site/images/avatar.webp" class="object-contain" />
                </div>
            </div>
            <div>
                账号
            </div>
            <div>
                email
            </div>
            <div>
                名称
            </div>
        </Container>
        <Container class="w-2/3 h-[600px]">
            123
        </Container>
    </div>

</template>
<script setup lang='ts'>
import Container from '@/components/container/index.vue'
import Title from '@/components/title/index.vue'
</script>
<style scoped></style>