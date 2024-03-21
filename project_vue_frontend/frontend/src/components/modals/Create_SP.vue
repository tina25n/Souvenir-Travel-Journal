<script setup>
import {getBranchNames} from "../../../services/BranchServices";

const branchNames = await getBranchNames();
const selectDogOrCat = ['Dogs And Cats', 'Dogs Only', 'Cats Only']

</script>

<script>
import {VCol} from "vuetify/components";
import {insertServiceProviderTable} from "../../../services/ServiceProviderServices";

export default {
  data: () => ({
    SIN_val: null,
    name_val: null,
    branchName_val: null,
    selectDogAndCat_str: null,
    dogAndCat_val: 0,
    dog_val: 0,
    cat_val: 0
  }),
  methods: {
    close() {
      this.$emit("close");
    },
    addDogAndCatVal() {
      if (this.selectDogAndCat_str === 'Cats Only') {
        this.dogAndCat_val = 0;
        this.dog_val = 0;
        this.cat_val = 1;
      } else if (this.selectDogAndCat_str === 'Dog Only') {
        this.dogAndCat_val = 0;
        this.dog_val = 1;
        this.cat_val = 0;
      } else {
        this.dogAndCat_val = 1;
        this.dog_val = 0;
        this.cat_val = 0;
      }
    },
    async submit() {
      await insertServiceProviderTable(this.SIN_val, this.name_val, this.branchName_val, this.dogAndCat_val, this.dog_val, this.cat_val);
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
                <v-col style="padding: 0 0 0 12px">
                  <v-text-field v-model="SIN_val"
                                label="SIN">
                  </v-text-field>
                </v-col>
                <v-col style="padding: 0 12px 0 12px">
                  <v-text-field v-model="name_val"
                                label="Name">
                  </v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col style="padding: 0 12px 0 12px">
                  <v-select v-model="branchName_val"
                            :items="branchNames"
                            label="Branch"
                  ></v-select>
                </v-col>
                <v-col style="padding: 0 12px 0 12px">
                  <v-select v-model="selectDogAndCat_str"
                            :items="selectDogOrCat"
                            @update:modelValue="addDogAndCatVal"
                            label="Works With"
                  ></v-select>
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