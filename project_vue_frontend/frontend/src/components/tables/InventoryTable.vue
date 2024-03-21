<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {getReservationsTable} from "../../../services/ReservationsServices";
import Edit_Prod from "../../components/modals/Edit_Prod.vue";

const reservations = await getReservationsTable();
const headers =
    [{title: 'Product Code', align: 'start', key: 'PID'},
      {title: 'Product Name', align: 'end', key: 'PName'},
      {title: 'Product Type', align: 'end', key: 'PType'},
      {title: 'Quantity', align: 'end', key: 'PQuantity'},
    ];
const itemsPerPage = 10;

</script>

<script>
  export default {
    data: () => ({
      edit_inv: false,
  }),
}
</script>

<template>
  <v-data-table
      style="overflow-x: scroll; max-width: 1000px"
      v-model:items-per-page="itemsPerPage"
      :headers="headers"
      :items-length="reservations.length"
      :items="reservations"
      item-value="id"
      class="elevation-1">
    <template v-slot:item.action="{ item }">
      <v-btn icon elevation="0" density="compact" @click = "edit_inv=true">
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
  <Edit_Prod v-if="edit_inv" @close ="edit_inv = false"></Edit_Prod>
</template>