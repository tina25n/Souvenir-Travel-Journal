<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {getMyWorkScheduleTable} from "../../../services/ServiceProviderServices";
import {getMyUserDetails} from "../../../services/UserServices";

const myUserDetails = await getMyUserDetails();
const workSchedule = await getMyWorkScheduleTable(myUserDetails.SIN);
const headers =
    [ {title: 'Day', align: 'end', key: 'day'},
      {title: 'Start Time', align: 'end', key: 'startTime'},
      {title: 'End Time', align: 'end', key: 'endTime'},
      {title: 'Actions', align: 'end', value: 'action'}
    ];
const itemsPerPage = 10;
</script>

<script>
import Edit_WS from "@/components/modals/Edit_WS.vue";

export default {
  data: () => ({
    edit_ws: false,
    ws_to_edit: null,
  }),
  methods: {
    handleEditWS(ws) {
      this.ws_to_edit = ws;
      this.edit_ws = true;
    },
    simpleHash(str) {
      let hash = 0
      for (let i = 0; i < str.length; i++) {
        hash = (hash << 5) - hash + str.charCodeAt(i)
        hash &= hash // Convert to 32bit integer
      }
      return (hash >>> 0).toString(36)
    }
  },
  components: {
    Edit_WS,
  }
}
</script>

<template>
  <v-data-table
      v-model:items-per-page="itemsPerPage"
      :headers="headers"
      :items-length="workSchedule.length"
      :items="workSchedule"
      item-value="SIN"
      class="elevation-1">
    <template v-slot:item.action="{ item }">
      <v-btn icon elevation="0" density="compact" @click="handleEditWS(item)">
        <v-icon icon="mdi-clipboard-edit-outline" color="grey-darken-1"></v-icon>
        <v-tooltip
            activator="parent"
            location="top"
        >Edit</v-tooltip>
      </v-btn>
    </template>
  </v-data-table>
  <Suspense>
    <Edit_WS v-if="edit_ws" @close="edit_ws=false" v-bind:sp="this.ws_to_edit" />
  </Suspense>
</template>