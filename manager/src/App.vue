<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
    <div id="app" class="h-full w-full">
        <RouterView></RouterView>
    </div>
</template>

<style scoped>
#app {
    background-color: var(--bg1);
}
</style>
