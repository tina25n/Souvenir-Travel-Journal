<script setup>
import DemoTable from "@/components/tables/DemoTable.vue";
import Add_Pet_Mod from "@/components/modals/Add_Pet.vue";
import Add_Res_Mod from "@/components/modals/Add_Res.vue";
import CustomersReservationTable from "@/components/tables/CustomersReservationTable.vue";
import BreadCrumbsByRole from "@/components/BreadCrumbsByRole.vue";
import { ref } from "vue";
</script>

<script>
import {getMyUserDetails} from "../../../services/UserServices";
import * as Pet from "../../../services/PetServices";
export default {
  data: () => ({
    pet_edit: true,
    add_pet: false,
    add_res: false,
    show_pets: false,
    name: null,
    type: null,
    size: null,
    age: null,
    res_fields: null,
    reservation: true,
    fur_length: null,
    fur_type: null,
    dental_c: null,
    clean: null,
    nail_sens: null,
    date_val: null,
    time_val: null,
    haircut: false,
    teeth: false,
    nail: false,
    shampoo: false,
    pets: ["candy"],
    current_pet: null,
    remove_pet_is_pressed: false,
    edit_pet_is_pressed: false,
    pets_map: ref([]),
    reservations: ["res 1"],
  }),
  methods: {
    async updatePetView() {
      console.log("Updating Pets View!");
      const petOwner = await getMyUserDetails();
      const petsArray = await Pet.getOwnersPets(petOwner.POName, petOwner.POPhoneNumber)
      let newPetsMap = []
      petsArray.forEach(pet => { newPetsMap.push(pet)})
      this.pets_map = newPetsMap;
    },
    async setCurrentPet(pet) {
      this.current_pet = pet;

      if (this.remove_pet_is_pressed) {
        console.log("Removing Pet!")
        this.remove_pet_is_pressed = false;
        const result = await Pet.deletePet(this.current_pet.aid);
        this.updatePetView();

        if (!result.data.success) { 
          alert("Failed to remove pet!");
        }
      } else if (this.edit_pet_is_pressed) {
        console.log("Editing Pet!");
        this.edit_pet_is_pressed = false;
        const result = await Pet.updatePet(this.current_pet.aid, this.current_pet.petName, this.current_pet.petAge,
                                           this.current_pet.petSize, this.current_pet.petType,
                                           this.current_pet.ownerName, this.current_pet.ownerPhone)
        this.updatePetView();

        if (!result.data.success) { 
          alert("Failed to edit pet!")
        
        }
      }
    }
  }
}
</script>

<template>
  <Suspense>
    <BreadCrumbsByRole/>
  </Suspense>
  <h1>Welcome, pet lover!</h1>
  <v-card class="align-center justify-center">
    <v-card-title>My Pets
      <v-btn color = "teal" style="float: right; " @click="add_pet = true"> Add Pet</v-btn>
      <v-btn color = "teal" style="float: right; margin-right: 10px" @click=" { show_pets = !show_pets; if (show_pets) {updatePetView() } else {pets_map = []}}">
        {{ show_pets ? 'Hide Pets' : 'Show Pets'  }} </v-btn>
    </v-card-title>
    <v-sheet width="800">
      <v-card class="align-center justify-center">
        <!--<v-list :items="items">
          <v-btn style="float: right;">Remove Pet</v-btn>
          <v-btn style="float: right;">Edit Pet</v-btn>
        </v-list> -->
        <v-expansion-panels v-for="data in pets_map" :key="data.aid">
          <v-expansion-panel @click="$event => {setCurrentPet(data)}">
            <v-expansion-panel-title>{{ data.petName }} </v-expansion-panel-title>
            <v-expansion-panel-text>

              <!--<v-card-title> Pet Details</v-card-title>-->
              <div>
                <v-row>
                  <v-col cols="5">
                    <v-responsive class="mx-auto" max-width="500">
                      <v-text-field label="Pet name" hide-details="auto" :disabled="pet_edit" v-model="data.petName"></v-text-field>
                    </v-responsive>
                  </v-col>
                  <v-col cols ="2">
                    <v-select label="Type" :disabled="pet_edit" v-model="data.petType"
                              :items="['Cat', 'Dog']"
                    ></v-select>
                  </v-col>
                  <v-col cols = "3">
                    <v-select label ="Size" :disabled="pet_edit" v-model="data.petSize"
                              :items="['Small', 'Medium', 'Large']"
                    ></v-select>
                  </v-col>
                  <v-col cols = "2">
                    <v-responsive class="mx-auto" max-width="100">
                      <v-text-field label="Age" hide-details="auto" type="number" min="0"
                                    :disabled="pet_edit" v-model="data.petAge"></v-text-field>
                    </v-responsive>
                  </v-col>

                  <v-col cols = "2">
                    <v-responsive class="mx-auto" max-width="100">
                      <v-text-field label="AID" hide-details="auto" type="number" min="0"
                                    :disabled="true" v-model="data.aid"></v-text-field>
                    </v-responsive>
                  </v-col>
                </v-row>
              </div>
              <div>
                <v-btn color= "red-lighten-1" style="float: right;" @click="remove_pet_is_pressed = true;">Remove Pet</v-btn>
                <v-btn color = "deep-purple-lighten-2" style="float: right;" @click="$event => {pet_edit = !pet_edit; if(pet_edit) {edit_pet_is_pressed = true;}}"
                       :text = "pet_edit ? 'Edit Pet' : 'Save Pet'"></v-btn>
              </div>
            </v-expansion-panel-text>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-card>
    </v-sheet>
  </v-card>
    <div style="margin-bottom: 12px">
      <v-card>
        <v-card-title>
          My Reservations Table
        </v-card-title>
      </v-card>
      
      <Suspense>
        <CustomersReservationTable/>
        <template #fallback>
          Loading...
        </template>
      </Suspense>
    </div>
  <Add_Pet_Mod v-if="add_pet" @close = "add_pet = false" v-on:updatePets="updatePetView" v-model="pet_pop"></Add_Pet_Mod>
  <Add_Res_Mod v-if="add_res" @close = "add_res = false" v-model="res_pop"></Add_Res_Mod>
</template>
