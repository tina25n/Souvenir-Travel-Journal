<script setup>
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
import InventoryTable from "@/components/tables/InventoryTable.vue"
import Add_Prod from "../../components/modals/Add_Prod.vue";
</script>

<script>
export default {
  data: () =>({
    add_prod: false
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
          Branch Inventory Table
          <v-btn color="red-lighten-1" style="float: right;" @click="initiateReservations">Reset</v-btn>
          <v-btn color="deep-purple-lighten-2" style="float: right;" @click = "add_prod = true">Add Product</v-btn>
        </v-card-title>
      </v-card>
      <Suspense>
        <InventoryTable/>
        <template #fallback>
          Loading...
        </template>
      </Suspense>
    </div>
  </div>
  <Add_Prod v-if = "add_prod" @close = "add_prod = false"></Add_Prod>
</template>