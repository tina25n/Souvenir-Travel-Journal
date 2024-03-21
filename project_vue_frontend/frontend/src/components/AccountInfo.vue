<script setup>
import {getMyUserDetails} from "../../services/UserServices";
const user = await getMyUserDetails();
const role = getUserRole();
function getUserRole() {
  if (user.role === 0) {
    return 'Pet Owner';
  } else if (user.role === 1) {
    return 'Service Provider';
  } else {
    return 'Admin';
  }
}
</script>

<script>
  export default {
    data: () => ({
      acct_edit: true
  }),
  methods: {
    close() {
      this.$emit("close");
    },
    async submit() {
      await updateUserAccount([user.email, user.password, user.POName, user.POPhoneNumber]);
      location.reload();
      close();
    }
  },
}
</script>
<template>
  <v-card width="600">
    <v-card-title>Hello, {{user.email}}!
      <v-btn color = "deep-purple-lighten-2" style="float: right;" @click = "acct_edit ? acct_edit = (!acct_edit) : submit" 
                    :text = "acct_edit ? 'Edit Details' : 'Save Details'" 
                    :disabled="!acct_edit && !(user.POName||user.password||user.POPhoneNumber)"
                    > </v-btn>
    </v-card-title>
    <v-form>
      <v-col>
        <v-row>
          <h3 style="padding-left: 16px;">Account Details</h3>
        </v-row>
      </v-col>
      <v-col cols="12">
        <v-row>
          <v-col>
            <v-text-field v-model="user.id" disabled="true" label="ID"></v-text-field>
          </v-col>
          <v-col>
            <v-text-field v-model="user.email" disabled="true" label="Email"></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-text-field v-model="user.password" type="password" 
            :rules="[() => !!user.password || 'This field is required']"
            :disabled="acct_edit" label="Password"></v-text-field>
          </v-col>
          <v-col>
            <v-text-field v-model="role" disabled="true" label="Role"></v-text-field>
          </v-col>
        </v-row>
        <v-row v-if="(user.role === 0)">
          <v-col>
            <v-text-field v-model="user.POName" 
            :rules="[() => !!user.POName || 'This field is required']"
            :disabled="acct_edit" label="Name"></v-text-field>
          </v-col>
          <v-col>
            <v-text-field v-model="user.POPhoneNumber" 
            :rules="[() => !!user.POPhoneNumber || 'This field is required']"
            :disabled="acct_edit" label="Phone Number"></v-text-field>
          </v-col>
        </v-row>
        <v-row v-if="(user.role === 1)">
          <v-col>
            <v-text-field v-model="user.SIN" type="password" disabled="true" label="SIN"></v-text-field>
          </v-col>
        </v-row>
      </v-col>
    </v-form>
  </v-card>
</template>