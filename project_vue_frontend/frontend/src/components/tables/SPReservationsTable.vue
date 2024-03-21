<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {getReservationsTable} from "../../../services/ReservationsServices";
import Edit_Res_Mod from "@/components/modals/Edit_Res.vue"

const reservations = await getReservationsTable();
const headers =
[{title: 'RID', align: 'start', key: 'RID'},
      {title: 'dateTimeService', align: 'end', key: 'dateTimeService'},
      {title: 'AID', align: 'end', key: 'AID'},
      {title: 'PO Phone Number', align: 'end', key: 'POPhoneNumber'},
      {title: 'PO Name', align: 'end', key: 'POName'},
      {title: 'servicesID', align: 'end', key: 'servicesID'},
      {title: 'Actions', align: 'end', value: 'action'}
    ];
const itemsPerPage = 10;

</script>

<script>
  export default {
    data: () => ({
      edit_res: false,
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
      <v-btn icon elevation="0" density="compact" @click = "edit_res=true">
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
  <Edit_Res_Mod v-if="edit_res" @close = "edit_res = false" v-model="edit_res_pop"></Edit_Res_Mod>
</template>