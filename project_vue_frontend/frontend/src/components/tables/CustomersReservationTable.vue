<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {getReservationsTablePO} from "../../../services/ReservationsServices";
import Edit_Res_Mod from "@/components/modals/Edit_Res.vue"
import {getMyUserDetails} from "../../../services/UserServices";

const user = await getMyUserDetails();
const reservations = await getReservationsTablePO(user.POName, user.POPhoneNumber);
const headers =
[{title: 'RID', align: 'start', key: 'RID'},
      {title: 'Date Time Service', align: 'end', key: 'dateTimeService'},
      {title: 'AID', align: 'end', key: 'AID'},
      {title: 'PO Phone Number', align: 'end', key: 'POPhoneNumber'},
      {title: 'PO Name', align: 'end', key: 'POName'},
      {title: 'Services ID', align: 'end', key: 'servicesID'},
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
  </v-data-table>
  <Edit_Res_Mod v-if="edit_res" @close = "edit_res = false" v-model="edit_res_pop"></Edit_Res_Mod>
</template>