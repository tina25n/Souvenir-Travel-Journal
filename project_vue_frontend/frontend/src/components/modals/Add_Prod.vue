<script setup>

</script>

<script>
  import {getMyUserDetails} from "../../../services/UserServices";
  import * as Pet from "../../../services/PetServices";
  import {VCol} from "vuetify/components";

  export default {
    data: () => ({
      PID: null,
      type: null,
      name: null,
      quantity: null
    }),
    methods: {
        close() {
            this.$emit("close");
        },
        async savePet() {
          const petowner = await getMyUserDetails();
          await Pet.insertPet(this.name_val, this.age_val, this.size_val, this.type_val, petowner.POName, petowner.POPhoneNumber)
          this.$emit("updatePets")
          this.$emit("close");
          //location.reload();
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
        <v-card-title> Add New Product </v-card-title>
        <v-row justify="center" no-gutters >
          <v-col cols = "8">
              <v-responsive class="pl-8 mx-auto" max-width="600">
                    <v-text-field label="Product Name" hide-details="auto" 
                    :disabled="prod_edit" class = "pb-6" v-model ="name"
                    :rules="[() => !!name|| 'This field is required']"></v-text-field>
              </v-responsive>
          </v-col>
          <v-col cols ="4">
                <v-responsive class="px-4 mx-auto" max-width="500">
                    <v-text-field label="PID" hide-details="auto" 
                    :disabled="prod_edit" class = "pb-6" v-model ="PID" type="number" min="0"
                    :rules="[() => !!PID|| 'This field is required']"></v-text-field>
                </v-responsive>
            </v-col>
            
        </v-row>

        <v-row justify="center" no-gutters >
          
            <v-col cols = "5">
                <v-select label ="Quantity" :disabled="prod_edit" v-model = "quantity"
                    :items="['Empty', 'Low', 'Half', 'Full']" class="mx-auto" 
                    :rules="[() => !!quantity || 'This field is required']"
                ></v-select>
            </v-col>
            <v-col cols = "5">
                <v-responsive class="pl-4 mx-auto" max-width="400">
                    <v-text-field label="Type" hide-details="auto" v-model ="type"
                    :rules="[() => !!type || 'This field is required']"
                    :disabled="prod_edit"></v-text-field>
                </v-responsive>
            </v-col>
        </v-row>
        <v-btn type="submit" color ="deep-purple-lighten-2" block class="mt-2" @click="($event) => {savePet()}"
        :disabled="(type === null || quantity === null || name === null || PID === null)">Save</v-btn>
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