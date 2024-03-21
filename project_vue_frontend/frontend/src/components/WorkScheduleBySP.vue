<script setup>

// eslint-disable-next-line vue/valid-define-props
const props = defineProps(['MyWorkSchedule'])

console.log(props.MyWorkSchedule);
</script>

<template>
  <v-card>
    <v-list :items="props.MyWorkSchedule">
      <template v-slot:default="{ items }">
        <template
            v-for="(item, i) in items"
            :key="i"
        >
          <v-card v-bind="item.raw"></v-card>
        </template>
      </template>
    </v-list>
  </v-card>
</template>


<script>
export default {
  props: ['MyWorkSchedule'],
  setup(props) {
    // setup() receives props as the first argument.
    console.log(props.MyWorkSchedule)
  }
}
</script>