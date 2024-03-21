<script setup>
import MyWorkScheduleTable from "@/components/tables/MyWorkScheduleTable.vue";
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
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
  <div style="margin-bottom: 12px">
    <v-card id="WSTable">
      <v-card-title>
        Work Schedule
      </v-card-title>
    </v-card>
    <Suspense>
      <MyWorkScheduleTable />
      <template #fallback>
        Loading...
      </template>
    </Suspense>
  </div>
</template>