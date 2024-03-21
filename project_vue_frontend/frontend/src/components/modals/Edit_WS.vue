<script setup>
</script>

<script>
import {VCol} from "vuetify/components";
import {updateWorkSchedule} from "../../../services/ServiceProviderServices";

export default {
  data: (props) => ({
    SIN_val: props.sp.SIN,
    day_val: props.sp.day,
    start_val: props.sp.startTime,
    end_val: props.sp.endTime,
  }),
  methods: {
    close() {
      this.$emit("close");
    },
    async submit() {
      await updateWorkSchedule(this.SIN_val, this.day_val, this.start_val, this.end_val);
      location.reload();
      close();
    },
    simpleHash(str) {
      let hash = 0
      for (let i = 0; i < str.length; i++) {
        hash = (hash << 5) - hash + str.charCodeAt(i)
        hash &= hash // Convert to 32bit integer
      }
      return (hash >>> 0).toString(36)
    }
  },
  components: { VCol },
  props: ['sp'],
};
</script>

<template>
  <transition name="modal-fade">
    <div class="modal-backdrop">
      <v-card class="align-center justify-center">
        <v-btn style="float: right;" variant="text" icon="$close"
               @click="close" color ="red-lighten-1"></v-btn>
        <v-card-title>Edit Work Schedule for {{simpleHash((this.SIN_val).toString())}} on {{this.day_val}}</v-card-title>
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
                  <v-text-field v-model="day_val"
                                label="Day"
                                readonly="true">
                  </v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col style="padding: 0 0 0 12px">
                  <v-text-field v-model="start_val"
                                label="Shift Start"
                  ></v-text-field>
                </v-col>
                <v-col style="padding: 0 12px 0 12px">
                  <v-text-field
                      v-model="end_val"
                      label="Shift End"
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