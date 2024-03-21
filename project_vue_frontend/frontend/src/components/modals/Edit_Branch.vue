<script setup>
</script>

<script>
import {VCol} from "vuetify/components";
import {updateBranch2} from "../../../services/BranchServices";

export default {
  data: (props) => ({
    branchName_val: props.branch.branchName,
    strAddr_val: props.branch.streetAddress,
    city_val: props.branch.city,
    postalCode_val: props.branch.postalCode,
    province_val: props.branch.province,
  }),
  methods: {
    close() {
      this.$emit("close");
    },
    async submit() {
      await updateBranch2([this.branchName_val, this.strAddr_val, this.city_val, this.postalCode_val, this.province_val]);
      location.reload();
      close();
    }
  },
  components: { VCol },
  props: ['branch'],
};
</script>

<template>
  <v-card class="align-center justify-center">
    <v-btn style="float: right;" variant="text"
           @click="close" color ="red-lighten-1">CANCEL EDIT</v-btn>
    <v-card-title>Edit Branch {{this.branchName_val}}</v-card-title>
    <v-sheet>
      <v-form @submit.prevent>
        <v-col cols="12">
          <v-row>
            <v-col style="padding: 0 0 0 12px">
              <v-text-field v-model="branchName_val"
                            label="Branch Name"
                            readonly="true">
              </v-text-field>
            </v-col>
            <v-col style="padding: 0 12px 0 12px">
              <v-text-field v-model="strAddr_val"
                            label="Street Address">
              </v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="4" style="padding: 0 0 0 12px">
              <v-text-field v-model="city_val"
                            label="City"
              ></v-text-field>
            </v-col>
            <v-col cols="4" style="padding: 0 12px 0 12px">
              <v-text-field
                  v-model="postalCode_val"
                  label="Postal Code"
              ></v-text-field>
            </v-col>
            <v-col cols="4" style="padding: 0 12px 0 0">
              <v-text-field
                  v-model="province_val"
                  label="Province"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-col>
        <v-btn color = "teal" type="submit" @click="submit" block="true" class="mt-2">Update</v-btn>
      </v-form>
    </v-sheet>
  </v-card>
</template>