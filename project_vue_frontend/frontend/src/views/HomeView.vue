<script setup>
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
import AccountInfo from "@/components/AccountInfo.vue";
</script>

<template>
      <BreadCrumbsByRole/>
      <Suspense>
        <AccountInfo/>
      </Suspense>
</template>
