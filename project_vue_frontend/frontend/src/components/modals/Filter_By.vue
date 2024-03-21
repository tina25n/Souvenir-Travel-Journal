<script setup>
import { VCol } from 'vuetify/lib/components/index.mjs';
</script>

<script>
  import * as PetOwner from "../../../services/PetOwnerServices";
  import * as Pet from "../../../services/PetServices";
  import * as Res from "../../../services/ReservationsServices";

  export default {
    data: (props) => ({
      haircut: false,
      teeth: false,
      nail: false,
      shampoo: false,
      fur_length: null,
      fur_type: null,
      dental_c: null,
      clean: null,
      nail_sens: null,
      date_val: null,
      time_val: null,
      POName: null,
      POPhoneNumber: null,
      PetName: null,
      PetSize: null,
      PetAge: null,
      PetType: null,
      SIN: null,
      and_or_val1: 'AND',
      and_or_val2: 'AND'
    }),
    methods: {
        close() {
            this.$emit("close");
        },
        
        async submit() {
          //const newResTable = await Res.getReservationsTableBySINPO(this.SIN, this.POName, this.POPhoneNum, this.and_or_val1, this.and_or_val2);
          console.log("HI:" + this.SIN + this.POName + this.POPhoneNumber + this.and_or_val1);
          this.$emit("update-res-table", {SIN: this.SIN, POName: this.POName, POPhoneNumber: this.POPhoneNumber, op1: this.and_or_val1, op2: this.and_or_val2});
          this.close();
         // location.reload();
        },
    components: { VCol }
  }
}
</script>

<template>
  <transition name="modal-fade">
    <div class="modal-backdrop">
      <v-card>
        <v-btn style="float: right;" icon="$close" variant="text" 
            @click="close" color ="red-lighten-1"></v-btn>
        <v-sheet width = 475>
        <v-card-title > Filter by</v-card-title>
          <v-row justify="center" no-gutters>
            <v-col cols="10">
                  <v-text-field v-model="POName" label="Pet Owner Name" class="pl-10"
                  :rules="[() => !!POName || 'This field is required']"
                  ></v-text-field>
            </v-col>
        </v-row>
        <v-row justify="center" no-gutters>
            <v-col cols="5">
                <v-select label="Combination"
                    :items="['AND', 'OR']" class="mx-auto" v-model="and_or_val1"
                ></v-select>
            </v-col>
        </v-row>
        <v-row justify="center" no-gutters>
            <v-col cols="10">
                  <v-text-field v-model="POPhoneNumber" label="Pet Owner Phone Number" type="number" class="px-4"
                  :rules="[() => !!POPhoneNumber || 'This field is required']"
                  ></v-text-field>
            </v-col>
          </v-row>
          <v-row justify="center" no-gutters> 
            <v-col cols="5">
                <v-select label="Combination"
                    :items="['AND', 'OR']" class="mx-auto" v-model="and_or_val2"
                ></v-select>
            </v-col>
          </v-row>
          <!-- Needed if creating reservation for new pet-->
          <v-row justify="center" no-gutters >
            <v-col>
                <v-responsive class="px-4 mx-auto" max-width="400">
                    <v-text-field label="SIN" hide-details="auto" 
                    class = "pb-6" v-model ="SIN"
                    :rules="[() => !!SIN|| 'This field is required']"
                    ></v-text-field>
                </v-responsive>
            </v-col>
        </v-row>

        <v-btn type="submit" color ="deep-purple-lighten-2" block class="mt-2" @click = submit
        :disabled="(!SIN || !POName || !POPhoneNumber)">Save</v-btn>
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