<script setup>
import { VCol } from 'vuetify/lib/components/index.mjs';
</script>

<script>
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
      POPhoneNum: null,
      PetName: null,
      PetSize: null,
      PetAge: null,
      PetType: null,
    }),
    methods: {
        close() {
            this.$emit("close");
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
        <v-card-title > Enter Reservation Details</v-card-title>
          <v-row justify="center" no-gutters>
            <v-col cols="7">
                  <v-text-field v-model="POName" label="Pet Owner Name" class="pl-10"
                  :rules="[() => !!POName || 'This field is required']"
                  ></v-text-field>
            </v-col>
            <v-col cols="5">
                  <v-text-field v-model="POPhoneNum" label="Phone Number" class="px-4"
                  :rules="[() => !!POPhoneNum || 'This field is required']"
                  ></v-text-field>
            </v-col>
          </v-row>

          <!-- Needed if creating reservation for new pet-->
          <v-row justify="center" no-gutters >
            <v-col>
                <v-responsive class="px-4 mx-auto" max-width="500">
                    <v-text-field label="Pet name" hide-details="auto" 
                    :disabled="pet_edit" class = "pb-6" v-model ="name_val"
                    :rules="[() => !!name_val || 'This field is required']"></v-text-field>
                </v-responsive>
            </v-col>
        </v-row>
        <v-row justify="center" no-gutters >
            <v-col cols ="3" >
                <v-select label="Type" :disabled="pet_edit"
                    :items="['Cat', 'Dog']" class="mx-auto" v-model="type_val"
                    :rules="[() => !!type_val || 'This field is required']"
                ></v-select>
            </v-col>
            <v-col cols = "5">
                <v-select label ="Size" :disabled="pet_edit" v-model = "size_val"
                    :items="['Small', 'Medium', 'Large']" class="pl-4 mx-auto" 
                    :rules="[() => !!size_val || 'This field is required']"
                ></v-select>
            </v-col>
            <v-col cols = "3">
                <v-responsive class="pl-4 mx-auto" max-width="100">
                    <v-text-field label="Age" hide-details="auto" v-model ="age_val"
                    :rules="[() => !!age_val || 'This field is required']"
                    :disabled="pet_edit" type="number" min="0"></v-text-field>
                </v-responsive>
            </v-col>
        </v-row>
        <v-row justify="center" no-gutters >
            <v-col cols="6">
                <v-text-field v-model="date_val" label="Service Date" class="pl-4"
                :rules="[() => !!date_val || 'This field is required']"
                type="date"></v-text-field>
            </v-col>
            <v-col cols="5">
                <v-text-field v-model="time_val" label="Service Time" class="px-4"
                :rules="[() => !!time_val || 'This field is required']"
                type="time"></v-text-field>
            </v-col>
        </v-row>

        <div>
        <h3 class = "pl-4">Select Services</h3>
        <v-row  justify="center" no-gutters >
            <v-col cols="4">
            <v-checkbox-btn type="checkbox" v-model="haircut" label="Haircut"  color ="teal"
            class="pl-4" @click="()=>{fur_length=null; fur_type=null}"></v-checkbox-btn>
            </v-col>
            <v-col>
            <v-checkbox-btn v-model="shampoo" color ="teal" @click="()=>{clean=null; fur_type=null}"
            label="Shampoo and Ear Cleaning" class="pl-4"></v-checkbox-btn>
            </v-col>
        </v-row>
        <v-row  justify="center" no-gutters >
            <v-col cols="6.5">
            <v-checkbox-btn v-model="nail"  color ="teal" @click="()=>{nail_sens=null}"
            label="Manicure/Pedicure" class="pl-4"></v-checkbox-btn>
            </v-col>
            <v-col>
            <v-checkbox-btn v-model="teeth" color ="teal" @click="()=>{dental_c=null}"
            label="Teeth Cleaning" class="pl-4"></v-checkbox-btn>
            </v-col>
        </v-row>
        <!-- Options -->
        <v-row justify="center" no-gutters>
            <v-col cols="4">
                <v-select label="Fur Length" :disabled="! haircut" class = "pl-2" v-model="fur_length"
                            :items="['Short', 'Medium', 'Long']"  :rules="[() => !!fur_length || 'This field is required']"
                              ></v-select>
            </v-col>
            <v-col cols="3.5">
                <v-select label="Fur Type" :disabled="!shampoo && !haircut" v-model="fur_type" 
                :rules="[() => !!fur_type || 'This field is required']"
                                :items="['Soft', 'Rugged', 'Hairless']" class = "pl-2"
                              ></v-select>
            </v-col>
            <v-col cols="3.5">
                <v-select label="Cleanliness" :disabled="!shampoo" class = "px-2" v-model="clean" 
                :rules="[() => !!clean || 'This field is required']"
                                :items="['Excellent', 'Good', 'Poor', 'Very Poor']"
                              ></v-select>
            </v-col>
        </v-row>
        <v-row justify="center" no-gutters>
            <v-col cols="6">
                <v-select label="Nail Sensitivity" :disabled="!nail" class = "pl-4" v-model="nail_sens"
                :rules="[() => !!nail_sens || 'This field is required']"
                                :items="['Low', 'Medium', 'High']"
                              ></v-select>
            </v-col>
            <v-col cols="6">
                <v-select label="Dental Condition" :disabled="!teeth" class = "px-4" v-model="dental_c"
                :rules="[() => !!dental_c || 'This field is required']"
                                :items="['Excellent', 'Good', 'Poor', 'Very Poor']"
                              ></v-select>
            </v-col>
        </v-row>
        </div>

        <v-btn type="submit" color ="deep-purple-lighten-2" block class="mt-2" @click = submit
        :disabled="!(teeth||haircut||nail||shampoo)">Save</v-btn>
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