<script setup>
import {getDemoTable} from '../../../services/DemoServices.js'
import { VDataTable } from 'vuetify/labs/VDataTable'

const users = await getDemoTable();
const headers = [{title: 'ID', align: 'start', key: 'id'}, {title: 'Name', align: 'end', key: 'name'}];
const itemsPerPage = 5;

</script>

<template>
    <v-data-table
        v-model:items-per-page="itemsPerPage"
        :headers="headers"
        :items-length="users.length"
        :items="users"
        item-value="id"
        class="elevation-1">
    </v-data-table>
</template>