<script setup>
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
import HoursOfOperation from "@/components/tables/HoursOfOperation.vue";
import BranchTable from "@/components/tables/BranchTable.vue";
import Create_Branch from "@/components/modals/Create_Branch.vue";
</script>

<script>
import {initiateBranch2Table, initiateHoursOfOperation} from "../../../../services/BranchServices";
export default {
  data: () => ({
    add_branch: false,
    add_hoo: false,
  }),
  methods: {
    async initiateHoursOfOperationTable() {
      await initiateHoursOfOperation();
      location.reload();
    },
    async initiateBranchTable() {
      await initiateBranch2Table()
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
  <div>
    <v-card id="BranchTable">
      <v-card-title>
        Branches
        <v-btn color="red-lighten-1" style="float: right;" @click="initiateBranchTable">Reset Branch</v-btn>
        <v-btn color="deep-purple-lighten-2" style="float: right;" @click="add_branch = true">Create Branch</v-btn>
      </v-card-title>
      <Suspense>
        <BranchTable/>
        <template #fallback>
          Loading...
        </template>
      </Suspense>
    </v-card>
  </div>
  <div>
    <v-card>
      <v-card-title>
        Hours Of Operation
        <v-btn position="relative" color="red-lighten-1" style="float: right;" @click="initiateHoursOfOperationTable">Initialize HOO</v-btn>
      </v-card-title>
    </v-card>
    <Suspense>
      <HoursOfOperation/>
      <template #fallback>
        Loading...
      </template>
    </Suspense>
  </div>
  <Create_Branch v-if="add_branch" @close = "add_branch = false"/>
 </template>