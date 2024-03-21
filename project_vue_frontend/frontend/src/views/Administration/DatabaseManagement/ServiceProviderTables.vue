<script setup>
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
import ServiceProviderTable from "@/components/tables/ServiceProviderTable.vue";
import WorkScheduleTable from "@/components/tables/WorkScheduleTable.vue";
import Create_SP from "@/components/modals/Create_SP.vue";
</script>

<script>
import {initiateServiceProviderTable, initiateWorkScheduleTable} from "../../../../services/ServiceProviderServices";
export default {
  data: () => ({
    add_sp: false,
  }),
  methods: {
    async initiateWorkSchedule() {
      await initiateWorkScheduleTable();
      location.reload();
    },
    async initiateServiceProvider() {
      await initiateServiceProviderTable()
      location.reload();
    }
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
  <div style="margin-bottom: 12px">
    <v-card>
      <v-card-title>
        Service Providers
        <v-btn color="red-lighten-1" style="float: right;" @click="initiateServiceProvider">Reset SPs</v-btn>
        <v-btn color="deep-purple-lighten-2" style="float: right;" @click="add_sp=true">Create SP</v-btn>
      </v-card-title>
    </v-card>
    <Suspense>
      <ServiceProviderTable/>
      <template #fallback>
        Loading...
      </template>
    </Suspense>
  </div>
  <div style="margin-bottom: 12px">
    <v-card id="WSTable">
      <v-card-title>
        Work Schedules
        <v-btn color="red-lighten-1" style="float: right;" @click="initiateWorkSchedule">Initiate WS</v-btn>
      </v-card-title>
    </v-card>
    <Suspense>
      <WorkScheduleTable/>
      <template #fallback>
        Loading...
      </template>
    </Suspense>
  </div>
  <Suspense>
    <Create_SP v-if="add_sp" @close="add_sp=false"/>
  </Suspense>
</template>