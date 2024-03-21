<script setup>

</script>

<script>
import * as Pet from "../../../services/PetServices";
import {VCol} from "vuetify/components";

export default {
  data: () => ({
    type_val: null,
    age_val: null,
    name_val: null,
    size_val: null,
    poname_val: null,
    poNum_val: null
  }),
  methods: {
    close() {
      this.$emit("close");
    },
    async savePet() {
      await Pet.insertPet(this.name_val, this.age_val, this.size_val, this.type_val, this.poname_val, this.poNum_val)
      this.$emit("updatePets")
      this.$emit("close");
      location.reload();
    }
  },
  components: { VCol }
};
</script>

<template>
  <transition name="modal-fade">
    <div class="modal-backdrop">
      <v-card>
        <v-btn style="float: right;" icon="$close" variant="text"
               @click="close" color ="red-lighten-1"></v-btn>
        <v-sheet width = 400>
          <v-card-title> Enter Pet Details</v-card-title>
          <v-row justify="center" no-gutters >
            <v-col>
              <v-responsive class="px-4 mx-auto" max-width="500">
                <v-text-field label="Pet name" hide-details="auto"
                              :disabled="pet_edit" class = "pb-6" v-model ="name_val"></v-text-field>
              </v-responsive>
            </v-col>
          </v-row>

          <v-row justify="center" no-gutters >
            <v-col cols ="3" >
              <v-select label="Type" :disabled="pet_edit"
                        :items="['Cat', 'Dog']" class="mx-auto" v-model="type_val"
              ></v-select>
            </v-col>
            <v-col cols = "5">
              <v-select label ="Size" :disabled="pet_edit" v-model = "size_val"
                        :items="['Small', 'Medium', 'Large']" class="pl-4 mx-auto"
              ></v-select>
            </v-col>
            <v-col cols = "3">
              <v-responsive class="pl-4 mx-auto" max-width="100">
                <v-text-field label="Age" hide-details="auto" v-model ="age_val"
                              :disabled="pet_edit" type="number" min="0"></v-text-field>
              </v-responsive>
            </v-col>
          </v-row>

          <v-row justify="center" no-gutters >
            <v-col>
              <v-responsive class="px-4 mx-auto" max-width="500">
                <v-text-field label="Pet Owner Name" hide-details="auto"
                              :disabled="pet_edit" class = "pb-6" v-model ="poname_val"></v-text-field>
              </v-responsive>
            </v-col>
            <v-col>
              <v-responsive class="pr-4 mx-auto" max-width="500">
                <v-text-field label="Phone Number" hide-details="auto"
                              :disabled="pet_edit" class = "pb-6" v-model ="poNum_val"></v-text-field>
              </v-responsive>
            </v-col>
          </v-row>
          <v-btn type="submit" color ="deep-purple-lighten-2" block class="mt-2" @click="($event) => {savePet()}"
                 :disabled="(type_val === null || size_val === null || age_val === null || name_val === null || poname_val === null|| poNum_val === null)">Save</v-btn>
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