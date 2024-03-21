<script setup>
const items = [
    {
    title: 'Home',
    disabled: false,
    href: '/',
    },
    {
    title: 'User',
    disabled: false,
    href: '/user',
    },
    {
    title: 'Exec',
    disabled: false,
    href: '/exec',
    },
    {
    title: 'Emp',
    disabled: false,
    href: '/emp',
    }
]
</script>

<template>
  <div>
    <v-breadcrumbs
        :items="items"
        divider="-"
    ></v-breadcrumbs>
  </div>
</template>