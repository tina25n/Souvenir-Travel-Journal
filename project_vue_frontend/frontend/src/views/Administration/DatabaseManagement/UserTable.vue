<script setup>
import UsersTable from "@/components/tables/UsersTable.vue";
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
import Create_User from "@/components/modals/Create_User.vue";
import Contact_User from "@/components/modals/Contact_User.vue"
</script>

<script>
import {initiateUserTable} from "../../../../services/UserServices";
export default {
  data: () => ({
    add_user: false,
    contact_user: false,
  }),
  methods: {
    async initiateUsers() {
      await initiateUserTable();
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
      <v-card id="UserTable">
        <v-card-title>
          User Accounts
          <v-btn color="red-lighten-1" style="float: right;" @click="initiateUsers">Reset Users</v-btn>
          <v-btn color="deep-purple-lighten-2" style="float: right;" @click="add_user = true">Create User</v-btn>
          <v-btn color="teal" style="float: right;" @click="contact_user = true">Contact Users</v-btn>
        </v-card-title>
        <v-card-subtitle>To create a user account for a Customer or Service Provider, you must first add the individual to the Pet Owner or Service Provider tables.</v-card-subtitle>
      </v-card>
      <Suspense>
        <UsersTable/>
        <template #fallback>
          Loading...
        </template>
      </Suspense>
    </div>
  </div>
  <Create_User v-if="add_user" @close = "add_user = false"/>
  <Contact_User v-if="contact_user" @close = "contact_user = false"/>
</template>