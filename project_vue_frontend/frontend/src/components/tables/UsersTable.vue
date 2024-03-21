<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {getUsersTable} from "../../../services/UserServices";


const users = await getUsersTable();
const headers =
  [{title: 'ID', align: 'start', key: 'id'},
  {title: 'Email', align: 'end', key: 'email'},
  {title: 'Password', align: 'end', key: 'password'},
  {title: 'Role', align: 'end', key: 'role'},
  {title: 'Name', align: 'end', key: 'POName'},
  {title: 'Phone Number', align: 'end', key: 'POPhoneNumber'},
  {title: 'SIN (hash)', align: 'end', key: 'SIN'},
  {title: 'Actions', align: 'end', value: 'action'}
  ];
const itemsPerPage = 10;
</script>

<script>
import Edit_User from "@/components/modals/Edit_User.vue";
import {deleteUserAccount} from "../../../services/UserServices";

export default {
  data: () => ({
    edit_user: false,
    user_to_edit: null,
  }),
  methods: {
    handleEditUser(user) {
      this.user_to_edit = user;
      this.edit_user = true;
    },
    async handleDeleteUser(user) {
      await deleteUserAccount(user.email);
      location.reload();
    },
    simpleHash(str) {
      let hash = 0
      for (let i = 0; i < str.length; i++) {
        hash = (hash << 5) - hash + str.charCodeAt(i)
        hash &= hash // Convert to 32bit integer
      }
      return (hash >>> 0).toString(36)
    }
  },
  components: {
    Edit_User,
  }
}
</script>

<template>
  <v-data-table
      v-model:items-per-page="itemsPerPage"
      :headers="headers"
      :items-length="users.length"
      :items="users"
      item-value="id"
      class="elevation-1">
      <template v-slot:item.password="{}">
        *******
      </template>
      <template v-slot:item.SIN="{ item }">
        {{(item.SIN ? simpleHash((item.SIN).toString()) : null)}}
      </template>
      <template v-slot:item.role="{ item }">
        {{((item.role === 0) ? 'Pet Owner' : (item.role === 1) ? 'Service Provider' : 'Admin')}}
      </template>
      <template v-slot:item.action="{ item }">
        <v-btn icon elevation="0" density="compact" @click="handleEditUser(item)">
          <v-icon icon="mdi-clipboard-edit-outline" color="grey-darken-1"></v-icon>
          <v-tooltip
              activator="parent"
              location="top"
          >Edit</v-tooltip>
        </v-btn>
        <v-btn icon elevation="0" density="compact" @click="handleDeleteUser(item)">
          <v-tooltip
              activator="parent"
              location="top"
          >Delete</v-tooltip>
          <v-icon icon="mdi-delete-outline" color="grey-darken-1"></v-icon>
        </v-btn>
      </template>
  </v-data-table>
  <Suspense>
    <Edit_User v-if="edit_user" @close="edit_user=false" v-bind:user=this.user_to_edit />
  </Suspense>
</template>