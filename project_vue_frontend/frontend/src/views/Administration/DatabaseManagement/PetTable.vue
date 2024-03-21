<script setup>
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
import PetTable from "@/components/tables/PetTable.vue"
import Create_Pet from "@/components/modals/Create_Pet.vue";
</script>

<script>
import {resetPetsTable} from "../../../../services/PetServices";

export default {
  data: () => ({
    add_pet: false,
  }),
  methods: {
    async initiatePets() {
      await resetPetsTable();
      location.reload();
    },
  }
}

</script>

<template>
  <Suspense>
    <BreadCrumbsByRole/>
  </Suspense>
  <v-navigation-drawer width="auto">
    <v-list-item title="Database Management"></v-list-item>
    <v-divider></v-divider>
    <v-list-item href="/admin/user-table" title="User Account Table"></v-list-item>
    <v-list-item href="/admin/branch-tables" title="Branch Tables"></v-list-item>
    <v-list-item href="/admin/service-provider-tables" title="Service Providers Tables"></v-list-item>
    <v-list-item href="/admin/services-table" title="Services Table"></v-list-item>
    <v-list-item href="/admin/reservations-table" title="Reservations Table"></v-list-item>
    <v-list-item href='/admin/pets-table' title="Pets Table"></v-list-item>
  </v-navigation-drawer>
  <v-card>
    <v-card-title>
      All Pets
      <v-btn color="red-lighten-1" style="float: right;" @click="initiatePets">Reset Pet Table</v-btn>
      <v-btn color="deep-purple-lighten-2" style="float: right;" @click="add_pet=true">Create Pet</v-btn>
    </v-card-title>
    <Suspense>
      <PetTable/>
      <template #fallback>
        Loading...
      </template>
    </Suspense>
  </v-card>
  <Create_Pet v-if="add_pet" @close="add_pet=false"/>
</template>