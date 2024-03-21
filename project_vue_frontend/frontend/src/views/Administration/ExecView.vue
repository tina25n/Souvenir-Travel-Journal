<script setup>
import UsersTable from "@/components/tables/UsersTable.vue";
import ServiceProviderTable from "@/components/tables/ServiceProviderTable.vue";
import HoursOfOperation from "@/components/tables/HoursOfOperation.vue";
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
</script>

<script>
import {initiateServiceProviderTable} from "../../../services/ServiceProviderServices";
import {initiateHoursOfOperation} from "../../../services/BranchServices";

export default {
  methods: {
    async initiateSPs() {
      await initiateServiceProviderTable();
      location.reload();
    },
    async initiateHOO() {
      await initiateHoursOfOperation();
      location.reload();
    }
  }
}

</script>


<template>
  <Suspense>
    <BreadCrumbsByRole/>
  </Suspense>
    <div>
      <div style="margin-bottom: 12px">
        <v-card>
          <v-card-title>
            Service Providers
            <v-btn color="red-lighten-1" style="float: right;" @click="initiateSPs">Reset SPs</v-btn>
            <v-btn color="deep-purple-lighten-2" style="float: right;">Create SP</v-btn>
          </v-card-title>
        </v-card>
        <Suspense>
          <ServiceProviderTable/>
        </Suspense>
      </div>
      <div style="margin-bottom: 12px">
        <v-card>
          <v-card-title>
            Hours Of Operation
            <v-btn color="red-lighten-1" style="float: right;" @click="initiateHOO">Reset HOOs</v-btn>
            <v-btn color="deep-purple-lighten-2" style="float: right;">Create HOO</v-btn>
          </v-card-title>
        </v-card>
        <Suspense>
          <HoursOfOperation/>
        </Suspense>
      </div>
    </div>
  </template>