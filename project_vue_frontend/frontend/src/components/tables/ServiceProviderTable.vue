<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {getServiceProvidersTable} from "../../../services/ServiceProviderServices";

const serviceProviders = await getServiceProvidersTable();
const headers =
    [ {title: 'SIN (hash)', align: 'start', key: 'SIN'},
      {title: 'Name', align: 'end', key: 'name'},
      {title: 'Branch', align: 'end', key: 'branchName'},
      {title: 'Dog And Cat Services', align: 'end', key: 'dogAndCat'},
      {title: 'Dog Services Only', align: 'end', key: 'dogOnly'},
      {title: 'Cat Services Only', align: 'end', key: 'catOnly'},
      {title: 'Actions', align:'end', key: 'action'}
    ];
const itemsPerPage = 10;
</script>

<script>
import Edit_SP from "@/components/modals/Edit_SP.vue";
import {deleteServiceProvider} from "../../../services/ServiceProviderServices";

export default {
  data: () => ({
    edit_sp: false,
    sp_to_edit: null,
  }),
  methods: {
    handleEditSP(sp) {
      console.log(sp);
      this.sp_to_edit = sp;
      this.edit_sp = true;
    },
    async handleDeleteSP(sp) {
      await deleteServiceProvider(sp.SIN);
      //location.reload();
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
    Edit_SP
  }
}
</script>

<template>
  <v-data-table
      v-model:items-per-page="itemsPerPage"
      :headers="headers"
      :items-length="serviceProviders.length"
      :items="serviceProviders"
      item-value="SIN"
      class="elevation-1">
      <template v-slot:item.SIN="{ item }">
        {{simpleHash((item.SIN).toString())}}
      </template>
    <template v-slot:item.action="{ item }">
      <v-btn icon elevation="0" density="compact" @click="handleEditSP(item)">
        <v-icon icon="mdi-clipboard-edit-outline" color="grey-darken-1"></v-icon>
        <v-tooltip
            activator="parent"
            location="top"
        >Edit</v-tooltip>
      </v-btn>
      <v-btn icon elevation="0" density="compact" @click="handleDeleteSP(item)">
        <v-tooltip
            activator="parent"
            location="top"
        >Delete</v-tooltip>
        <v-icon icon="mdi-delete-outline" color="grey-darken-1"></v-icon>
      </v-btn>
    </template>
  </v-data-table>
  <Suspense>
    <Edit_SP v-if="edit_sp" @close="edit_sp = false" v-bind:sp="this.sp_to_edit" />
  </Suspense>
</template>