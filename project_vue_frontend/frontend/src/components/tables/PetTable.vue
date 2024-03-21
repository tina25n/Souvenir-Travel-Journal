<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {getPetsTable} from "../../../services/PetServices";

const pets = await getPetsTable();
console.log(pets);
const headers = [{title: 'AID', align: 'start', key: 'aid'},
                  {title: 'Name', align: 'start', key: 'petName'},
                  {title: 'Age', align: 'start', key: 'petAge'},
                  {title: 'Size', align: 'start', key: 'petSize'},
                  {title: 'Type', align: 'start', key: 'petType'},
                  {title: 'Owner Name', align: 'start', key: 'POName'},
                  {title: 'Owner Phn#', align: 'start', key: 'POPhoneNumber'}]
const itemsPerPage = 5;

</script>

<template>
  <v-data-table
      v-model:items-per-page="itemsPerPage"
      :headers="headers"
      :items-length="pets.length"
      :items="pets"
      item-value="aid"
      class="elevation-1">
  </v-data-table>
</template>