<script setup>
</script>

<script>
import {VCol} from "vuetify/components";
import {updateBranch1} from "../../../services/BranchServices";

export default {
  data: (props) => ({
    branchName_val: props.branch.branchName,
    day_val: props.branch.day,
    start_val: props.branch.startTime,
    end_val: props.branch.endTime,
  }),
  methods: {
    close() {
      this.$emit("close");
    },
    async submit() {
      await updateBranch1([this.branchName_val, this.day_val, this.start_val, this.end_val]);
      location.reload();
      close();
    }
  },
  components: { VCol },
  props: ['branch'],
};
</script>

<template>
  <transition name="modal-fade">
    <div class="modal-backdrop">
  <v-card class="align-center justify-center">
    <v-btn style="float: right;" variant="text"
           @click="close" color ="red-lighten-1">CANCEL EDIT</v-btn>
    <v-card-title>Edit Open Hours for {{this.branchName_val}} on {{this.day_val}}</v-card-title>
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
              <v-text-field v-model="day_val"
                            label="Day"
                            readonly="true">
              </v-text-field>
            </v-col>
          </v-row>
          <v-row>
            <v-col style="padding: 0 0 0 12px">
              <v-text-field v-model="start_val"
                            label="Opens"
              ></v-text-field>
            </v-col>
            <v-col style="padding: 0 12px 0 12px">
              <v-text-field
                  v-model="end_val"
                  label="Closes"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-col>
        <v-btn color = "teal" type="submit" @click="submit" block="true" class="mt-2">Update</v-btn>
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