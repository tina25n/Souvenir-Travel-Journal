<script setup>
import { VCol } from 'vuetify/lib/components/index.mjs';

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
  export default {
    methods: {
        close() {
            this.$emit("close");
        },
    components: { VCol }
  }
}
</script>

<template>
  <transition name="modal-fade">
    <div class="modal-backdrop">
      <v-card>
        <v-btn style="float: right;" icon="$close" variant="text" 
            @click="close" color ="red-lighten-1"></v-btn>
        <v-sheet width = 600>
        <v-card-title > Book a Reservation</v-card-title>
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

        
        </v-sheet>
      </v-card>
    </div>
  </transition>
</template>

<style>
  .modal-backdrop {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background-color: rgba(0, 0, 0, 0.3);
    display: flex;
    justify-content: center;
    align-items: center;
  }

</style>