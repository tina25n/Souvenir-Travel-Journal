<script setup>
import {getBranchNames} from "../../../services/BranchServices";

const branchNames = await getBranchNames();
const selectDogOrCat = ['Dogs And Cats', 'Dogs Only', 'Cats Only']
</script>

<script>
import {updateServiceProvider} from "../../../services/ServiceProviderServices";

export default {
  data: (props) => ({
    SIN_val: props.sp.SIN,
    name_val: props.sp.name,
    branchName_val: props.sp.branchName,
    dogAndCat_val: props.sp.dogAndCat,
    dog_val: props.sp.dogOnly,
    cat_val: props.sp.catOnly,
    selectDogAndCat_str: (props.sp.dogAndCat === 1 ? 'Dogs And Cats' : (props.sp.dogOnly === 1) ? 'Dogs Only' : 'Cats Only'),
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
      } else if (this.selectDogAndCat_str === 'Dogs Only') {
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
      await updateServiceProvider(this.SIN_val, this.name_val, this.branchName_val, this.dogAndCat_val, this.dog_val, this.cat_val);
      location.reload();
      close();
    }
  },
  props: ['sp'],
};
</script>

<template>
  <v-card class="align-center justify-center">
    <v-btn style="float: right;" variant="text"
           @click="close" color ="red-lighten-1">CANCEL EDIT</v-btn>
    <v-card-title>Edit Service Provider {{this.name_val}}</v-card-title>
    <v-sheet>
      <v-form @submit.prevent>
        <v-col cols="12">
              <v-row>
                <v-col style="padding: 0 0 0 12px">
                  <v-text-field v-model="SIN_val"
                                label="SIN"
                                type="password"
                                readonly="true">
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
        <v-btn color = "teal" type="submit" @click="submit" block="true" class="mt-2">Update</v-btn>
      </v-form>
    </v-sheet>
  </v-card>
</template>