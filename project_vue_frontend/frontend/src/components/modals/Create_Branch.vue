<script setup>
</script>

<script>
import {VCol} from "vuetify/components";
import {insertBranch2} from "../../../services/BranchServices";

export default {
  data: () => ({
    branchName_val: null,
    strAddr_val: null,
    city_val: null,
    postalCode_val: null,
    province_val: null,
  }),
  methods: {
    close() {
      this.$emit("close");
    },
    async submit() {
      await insertBranch2([this.branchName_val, this.strAddr_val, this.city_val, this.postalCode_val, this.province_val]);
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
        <v-card-title>Create New Branch</v-card-title>
        <v-sheet width="600">
          <v-form @submit.prevent>
            <v-col cols="12">
              <v-row>
                <v-col style="padding: 0 0 0 12px">
                  <v-text-field v-model="branchName_val"
                                label="Branch Name">
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