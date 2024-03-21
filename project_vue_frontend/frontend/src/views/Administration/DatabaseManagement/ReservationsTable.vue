<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
import Create_Res from "../../../components/modals/Create_Res.vue";
import Filter_By from "../../../components/modals/Filter_By.vue"; 
</script>

<script>
import * as Res from "../../../../services/ReservationsServices";
export default {
  computed: {
    headers() {
      return [{title: 'RID', align: 'start', key: 'RID'},
      {title: 'Date/Time of Service', align: 'end', key: 'dateTimeService'},
      {title: 'AID', align: 'end', key: 'AID'},
      {title: 'PO Phone Number', align: 'end', key: 'POPhoneNumber'},
      {title: 'PO Name', align: 'end', key: 'POName'},
      {title: 'servicesID', align: 'end', key: 'servicesID'},
      {title: 'SIN (hash)', align: 'end', key: 'SIN'}];
    }
  },
  data: () => ({
    create_res: false,
    filter_by: false,
    SIN: null,
    POName: null,
    POPhoneNumber: null,
    op1: null,
    op2: null,
    filtered: false,
    reservations: [{SIN: 123, POPhoneNumber: 123, POName: 123}, {SIN: 124, POPhoneNumber: 123, POName: "ax"}]
  }),
  methods: {
    async initiateReservations() {
      //await Res.initiateReservationsTable();
      //location.reload();
      this.headers = [{title: 'RID', align: 'start', key: 'RID'},
      {title: 'Date/Time of Service', align: 'end', key: 'dateTimeService'},
      {title: 'Date/Time Booked', align: 'end', key: 'dateTimeBooked'},
      {title: 'AID', align: 'end', key: 'AID'},
      {title: 'PO Phone Number', align: 'end', key: 'POPhoneNumber'},
      {title: 'PO Name', align: 'end', key: 'POName'},
      {title: 'servicesID', align: 'end', key: 'servicesID'},
      {title: 'SIN (hash)', align: 'end', key: 'SIN'},];

      this.reservations = await Res.getReservationsTable();
    },
    async filterBy({SIN, POName, POPhoneNumber, op1, op2}) {

      this.SIN = SIN;
      this.POName = POName;
      this.POPhoneNumber = POPhoneNumber;
      this.op1 = op1;
      this.op2 = op2;

      this.headers = [{title: 'SIN', align: 'start', key: 'SIN'},
      {title: 'PO Phone Number', align: 'end', key: 'POPhoneNumber'},
      {title: 'PO Name', align: 'end', key: 'POName'}
    ];
      this.reservations = await Res.getReservationsTableBySINPO(SIN, POName, POPhoneNumber, op1, op2);
      
    }
  },
  created: async function(){
    this.reservations = await Res.getReservationsTable();
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
          Reservations Table
          <v-btn color="red-lighten-1" style="float: right;" @click="initiateReservations">Reset</v-btn>
          <v-btn color="deep-purple-lighten-2" style="float: right;" @click="{ filter_by=true;}">Filter</v-btn>
          <v-btn color="deep-purple-lighten-2" style="float: right;" 
          @click="create_res=true">Create</v-btn>
        </v-card-title>
      </v-card>
      <v-data-table :headers="headers" :items="reservations" />
    </div>
  </div>
  <Create_Res v-if ="create_res" @close = "create_res = false" 
  v-model = "create_res_pop_ad"></Create_Res>
  <Filter_By v-if ="filter_by" @close = "filter_by=false" 
  v-model = "filter_by_pop_ad" @update-res-table="filterBy"></Filter_By>
</template>

