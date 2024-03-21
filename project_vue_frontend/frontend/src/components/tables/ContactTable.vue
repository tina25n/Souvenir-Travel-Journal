<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {getUsersTable} from "../../../services/UserServices";


const users = await getUsersTable();
const headers =
    [{title: 'Name', align: 'end', key: 'POName'},
    {title: 'Email', align: 'end', key: 'email'},
    {title: 'Phone Number', align: 'end', key: 'POPhoneNumber'},
    ];
const itemsPerPage = 10;

</script>

<template>
  <v-data-table
      style="overflow-x: scroll; max-width: 1000px"
      v-model:items-per-page="itemsPerPage"
      :headers="headers"
      :items-length="users.length"
      :items="users"
      item-value="id"
      class="elevation-1">
  </v-data-table>

</template>