<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
window._AMapSecurityConfig = {
  securityJsCode: "0ffd599e5ab5b239d14706e319275e94",
};
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
