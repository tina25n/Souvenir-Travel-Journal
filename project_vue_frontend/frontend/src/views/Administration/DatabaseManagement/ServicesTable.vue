<script setup>
import ServicesTable from "@/components/tables/ServicesTable.vue"
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
</script>

<script>
import {initiateServicesTable} from "../../../../services/ServiceServices";
export default {
  methods: {
    async initiateServices() {
      await initiateServicesTable();
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
  <div>
    <div style="margin-bottom: 12px">
      <v-card>
        <v-card-title>
          Services Table
          <v-btn color="red-lighten-1" style="float: right;" @click="initiateServices">Reset Services</v-btn>
          <v-btn color="deep-purple-lighten-2" style="float: right;">Create Service</v-btn>
        </v-card-title>
      </v-card>
      <Suspense>
        <ServicesTable/>
        <template #fallback>
          Loading...
        </template>
      </Suspense>
    </div>
  </div>
</template>