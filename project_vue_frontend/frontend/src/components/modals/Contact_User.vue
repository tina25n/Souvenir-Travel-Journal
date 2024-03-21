<script setup>
import ContactTable from "@/components/tables/ContactTable.vue"
import PetTable from "@/components/tables/PetTable.vue"
</script>

<script>
import {VCol} from "vuetify/components";

export default {
  methods: {
    close() {
      this.$emit("close");
    },
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
        <v-card-title>Contact Accounts</v-card-title>
        <v-sheet width="600">
          <Suspense>
            <ContactTable/>
              <template #fallback>
                Loading...
              </template>
          </Suspense>
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