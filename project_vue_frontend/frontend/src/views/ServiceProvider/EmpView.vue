<script setup>
import WorkScheduleTable from "@/components/tables/WorkScheduleTable.vue";
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
</script>

<script>
import {initiateWorkScheduleTable} from "../../../services/ServiceProviderServices";

export default {
  methods: {
    async initiateWorkSchedule() {
      await initiateWorkScheduleTable();
      location.reload();
    }
  }
}

</script>

<template>
    <Suspense>
      <BreadCrumbsByRole/>
    </Suspense>
  <div style="margin-bottom: 12px">
    <v-card>
      <v-card-title>
        Work Schedule
        <v-btn color="red-lighten-1" style="float: right;" @click="initiateWorkSchedule">Reset WSs</v-btn>
        <v-btn color="deep-purple-lighten-2" style="float: right;">Create WS</v-btn>
      </v-card-title>
    </v-card>
    <Suspense>
      <WorkScheduleTable/>
    </Suspense>
  </div>
  </template>