<script setup>
</script>

<script>
import {VCol} from "vuetify/components";
import {createUserAccount} from "../../../services/UserServices";

export default {
  data: () => ({
    id_val: null,
    email_val: null,
    password_val: null,
    role_val: null,
    role_str: null,
    name_val: null,
    phone_val: null,
    SIN_val: null,
    selectedCustomer: false,
    selectedServiceProvider: false,
    roles: ['Customer', 'Service Provider', 'Admin']
  }),
  methods: {
    close() {
      this.$emit("close");
    },
    handleSelectRole() {
      if (this.role_str === 'Customer') {
        this.role_val = 0;
        this.selectedServiceProvider = false;
        this.selectedCustomer = true;
        this.SIN_val = null;
      } else if (this.role_str === 'Service Provider') {
        this.role_val = 1;
        this.selectedCustomer = false;
        this.selectedServiceProvider = true;
        this.name_val = null;
        this.phone_val = null;
      } else {
        this.role_val = 2;
        this.selectedCustomer = false;
        this.selectedServiceProvider = false;
        this.name_val = null;
        this.phone_val = null;
        this.SIN_val = null;
      }
    },
    formatPhoneNumber() {
      this.phone_val = this.phone_val.replaceAll('-', '');
      this.phone_val = this.phone_val.replaceAll('(', '');
      this.phone_val = this.phone_val.replaceAll(')', '');
    },
    async submit() {
      this.formatPhoneNumber();
      await createUserAccount([this.id_val, this.email_val, this.password_val, this.role_val, this.name_val, this.phone_val, this.SIN_val]);
      location.reload();
      close();
    }
  },
  components: { VCol }
};
</script>

<template>
  <transition name="modal-fade">
    <div class="modal-backdrop">
      <v-card class="align-center justify-center">
        <v-btn style="float: right;" icon="$close" variant="text"
               @click="close" color ="red-lighten-1"></v-btn>
        <v-card-title>Create New User Account</v-card-title>
        <v-sheet width="600">
          <v-form @submit.prevent>
            <v-col cols="12">
              <v-row>
                <v-col cols="4" style="padding: 0 0 0 12px">
                  <v-text-field v-model="id_val"
                                label="ID"
                                hint="Must be unique*">
                  </v-text-field>
                </v-col>
                <v-col style="padding: 0 12px 0 12px">
                  <v-select v-model="role_str"
                            :items="roles"
                            @update:modelValue="handleSelectRole"
                            label="Role"
                  ></v-select>
                </v-col>
              </v-row>
              <v-row>
                <v-col style="padding: 0 0 0 12px">
                  <v-text-field v-model="email_val"
                                label="Email"
                                hint="Must be unique*"
                  ></v-text-field>
                </v-col>
                <v-col style="padding: 0 12px 0 12px">
                  <v-text-field
                      v-model="password_val"
                      label="Password"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row v-if="selectedCustomer" style="margin-left: 4px">
                <h4>Customer Only Fields:</h4>
              </v-row>
              <v-row v-if="selectedCustomer">
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
              <v-row v-if="selectedServiceProvider" style="margin-left: 4px">
                <h4>Service Provider Only Fields:</h4>
              </v-row>
              <v-row v-if="selectedServiceProvider">
                <v-col style="padding: 0 12px 0 12px">
                  <v-text-field v-model="SIN_val"
                                label="SIN"
                  ></v-text-field>
                </v-col>
              </v-row>
              </v-col>
            <v-btn color = "deep-purple-lighten-2" type="submit" @click="submit" block="true" class="mt-2">Submit</v-btn>
          </v-form>
        </v-sheet>

      </v-card>
    </div>
  </transition>
</template>

<style>
.modal-backdrop {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>