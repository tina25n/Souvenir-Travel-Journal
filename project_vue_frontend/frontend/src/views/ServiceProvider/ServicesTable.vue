<script setup>
import ServicesTable from "@/components/tables/ServicesTable.vue"
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
</script>

<script>
import {initiateServicesTable} from "../../../services/ServiceServices";
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
    <v-list-item title="Manage Branch & Reservations"></v-list-item>
    <v-divider></v-divider>
    <v-list-item href="/service-provider/reservations-table" title="My Reservations Table"></v-list-item>
    <v-list-item href="/service-provider/my-work-schedule-table" title="My Work Schedule Table"></v-list-item>
    <v-list-item href="/service-provider/services-table" title="Services Table"></v-list-item>
    <v-list-item href="/service-provider/inventory" title="Branch Inventory Table"></v-list-item>
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