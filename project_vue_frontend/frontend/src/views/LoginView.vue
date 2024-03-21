<script setup>
</script>

<script>
import {Login} from "../../services/LoginServices";
import router from "@/router";

export default {
  data: () => ({
    email: '',
    password:'',
    loading: false,
  }),
  methods: {
    submit() {
      Login([this.email, this.password]);
      router.push('/');
    }
  }
}
</script>

<template>
  <v-card class="align-center justify-center">
    <v-card-title>Login</v-card-title>
    <v-sheet width="300">
      <v-form @submit.prevent>
        <v-col cols="12">
          <v-text-field
              v-model="email"
              label="Email"
          ></v-text-field>
          <v-text-field
              v-model="password"
              label="Password"
              type="password"
          ></v-text-field>
        </v-col>
        <v-btn color = "deep-purple-lighten-2" type="submit" @click="submit" block="true" class="mt-2" :loading="loading">Login</v-btn>
      </v-form>
    </v-sheet>

  </v-card>


</template>