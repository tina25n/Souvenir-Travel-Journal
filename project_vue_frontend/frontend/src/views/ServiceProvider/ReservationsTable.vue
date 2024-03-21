<script setup>
import SPReservationsTable from "@/components/tables/SPReservationsTable.vue"
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
import Create_Res from "../../components/modals/Create_Res.vue";
</script>

<script>
import {initiateReservationsTable} from "../../../services/ReservationsServices";
export default {
  data: () => ({
    create_res_sp: false
  }),
  methods: {
    async initiateReservations() {
      await initiateReservationsTable();
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
          My Reservations Table
          <v-btn color="red-lighten-1" style="float: right;" @click="initiateReservations">Reset Reservations</v-btn>
          <v-btn color="deep-purple-lighten-2" style="float: right;" 
          @click = "create_res_sp = true">Create Reservation</v-btn>
          <v-btn color="teal" style="float: right;" 
          >View Today's</v-btn>
        </v-card-title>
      </v-card>
      
      <Suspense>
        <SPReservationsTable/>
        <template #fallback>
          Loading...
        </template>
      </Suspense>
    </div>
  </div>
  <Create_Res v-if="create_res_sp" @close = "create_res_sp = false" v-model="create_res_pop_sp"></Create_Res>
</template>