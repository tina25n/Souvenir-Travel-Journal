<script setup>
let breadcrumbs;

let role = localStorage.getItem("role");
if (role === '2') {
  breadcrumbs = [
      {title: "Account Info", disabled: false, href: '/'},
    {title: "Manage Database", disabled: false, href: '/admin/user-table'}];
}
if (role === '1') {
  breadcrumbs = [
    {title: "Account Info", disabled: false, href: '/'},
    {title: "Manage Branch & Reservations", disabled: false, href: '/service-provider/services-table'}];
}
if (role === '0') {
  breadcrumbs = [
    {title: "Account Info", disabled: false, href: '/'},
    {title: "My Pets and Reservations", disabled: false, href: '/user'}];
}
</script>

<template>
  <div>
    <v-breadcrumbs
        :items="breadcrumbs"
        divider="|"
    ></v-breadcrumbs>
  </div>
</template>