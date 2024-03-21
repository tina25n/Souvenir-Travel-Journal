<script setup>
</script>

<script>
import {VCol} from "vuetify/components";
import {updateUserAccount} from "../../../services/UserServices";
export default {
  data: (props) => ({
    id_val: props.user.id,
    role_str: null,
    role_val: (props.user.role === 0 ? 'Customer' : (props.user.role === 1) ? 'Service Provider' : 'Admin'),
    email_val: props.user.email,
    password_val: props.user.password,
    name_val: props.user.customerName,
    phone_val: props.user.phoneNumber,
    SIN_val: props.user.SIN,
    isCustomer: props.user.role === 0,
    isServiceProvider: props.user.role === 1,
    roles: ['Customer', 'Service Provider', 'Admin']
  }),
  methods: {
    close() {
      this.$emit("close");
    },
    async submit() {
      await updateUserAccount([this.email_val, this.password_val, this.name_val, this.phone_val]);
      location.reload();
      close();
    }
  },
  components: { VCol },
  props: ['user'],
};

</script>

<template>
  <transition name="modal-fade">
    <div class="modal-backdrop">
      <v-card class="align-center justify-center">
        <v-btn style="float: right;" icon="$close" variant="text"
               @click="close" color ="red-lighten-1"></v-btn>
        <v-card-title>Edit User Account {{id_val}}</v-card-title>
        <v-sheet width="600">
          <v-form @submit.prevent>
            <v-col cols="12">
              <v-row>
                <v-col cols="4" style="padding: 0 0 0 12px">
                  <v-text-field v-model="id_val"
                                label="ID" disabled="true">
                  </v-text-field>
                </v-col>
                <v-col style="padding: 0 12px 0 12px">
                  <v-select v-model="role_val"
                            :items="roles"
                            label="Role"
                            disabled="true"
                  ></v-select>
                </v-col>
              </v-row>
              <v-row>
                <v-col style="padding: 0 0 0 12px">
                  <v-text-field v-model="email_val"
                                label="Email"
                                disabled="true"
                  ></v-text-field>
                </v-col>
                <v-col style="padding: 0 12px 0 12px">
                  <v-text-field
                      v-model="password_val"
                      label="Password"
                      type="password"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row v-if="isCustomer" style="margin-left: 4px">
                <h4>Customer Only Fields:</h4>
              </v-row>
              <v-row v-if="isCustomer">
                <v-col style="padding: 0 0 0 12px">
                  <v-text-field v-model="name_val"
                                label="Name"
                  ></v-text-field>
                </v-col>
                <v-col style="padding: 0 12px 0 12px">
                  <v-text-field
                      v-model="phone_val"
                      label="Phone Number"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row v-if="isServiceProvider" style="margin-left: 4px">
                <h4>Service Provider Only Fields:</h4>
              </v-row>
              <v-row v-if="isServiceProvider">
                <v-col style="padding: 0 12px 0 12px">
                  <v-text-field v-model="SIN_val"
                                label="SIN"
                                type="password"
                                disabled="true"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-col>
            <v-btn color = "deep-purple-lighten-2" type="submit" @click="submit" block="true" class="mt-2">Update</v-btn>
          </v-form>
        </v-sheet>

      </v-card>
    </div>
  </transition>
</template>

<style>
</style>