<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {getServicesTable} from "../../../services/ServiceServices";

const services = await getServicesTable();
const headers =
    [{title: 'Service ID', align: 'start', key: 'servicesId'},
      {title: 'Name', align: 'end', key: 'servicesName'},
      {title: 'Base Amount', align: 'end', key: 'baseAmount'},
      {title: 'Final Rate', align: 'end', key: 'finalRate'},
      {title: 'Duration', align: 'end', key: 'duration'}
    ];
const itemsPerPage = 10;

</script>

<template>
  <v-data-table
      v-model:items-per-page="itemsPerPage"
      :headers="headers"
      :items-length="services.length"
      :items="services"
      item-value="id"
      class="elevation-1">
    <template v-slot:item.action="{ item }">
      <v-btn icon elevation="0" density="compact">
        <v-icon icon="mdi-clipboard-edit-outline" color="grey-darken-1"></v-icon>
        <v-tooltip
            activator="parent"
            location="top"
        >Edit</v-tooltip>
      </v-btn>
      <v-btn icon elevation="0" density="compact">
        <v-tooltip
            activator="parent"
            location="top"
        >Delete</v-tooltip>
        <v-icon icon="mdi-delete-outline" color="grey-darken-1"></v-icon>
      </v-btn>
    </template>
  </v-data-table>
</template>