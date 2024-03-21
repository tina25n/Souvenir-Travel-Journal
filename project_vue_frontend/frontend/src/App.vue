<script setup>
import { RouterView } from 'vue-router'
import { VAppBar, VBtn, VAppBarTitle } from 'vuetify/components'
</script>

<script>
import {Logout} from "../services/LoginServices";
import router from "@/router";

export default {
  methods: {
    logout() {
      Logout();
      router.push('/login');
    },
  }
}

</script>

<template>
  <v-layout ref="app" class="rounded rounded-md">
    <v-app-bar color="teal" scroll-behavior="elevate">
      <template v-slot:prepend>
        <v-icon icon="mdi-dog-service" size="xxx-large"></v-icon>
      </template>
      <v-app-bar-title>Furry Friends Grooming Salon</v-app-bar-title>
      <template v-slot:append>
<!--        <Suspense>-->
<!--          <BreadCrumbs/>-->
<!--        </Suspense>-->
        <v-menu open-on-hover>
          <template v-slot:activator="{ props }">
            <v-btn icon="mdi-dots-vertical"
                   color="white"
                   v-bind="props">
            </v-btn>
          </template>
          <v-list>
            <v-list-item>
              <v-btn @click="logout">
                <v-list-item-title>Logout</v-list-item-title>
              </v-btn>
            </v-list-item>
          </v-list>
        </v-menu>
      </template>
    </v-app-bar>

    <v-main class="align-center justify-center" style="min-height: 300px">
      <RouterView />
    </v-main>

  </v-layout>
</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
