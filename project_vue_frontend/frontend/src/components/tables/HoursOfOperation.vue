<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {getHoursOfOperationTable} from "../../../services/BranchServices";

const hoursOfOperation = await getHoursOfOperationTable();
const headers =
    [{title: 'Day', align: 'end', key: 'day'},
      {title: 'Opening Time', align: 'end', key: 'startTime'},
      {title: 'Closing Time', align: 'end', key: 'endTime'},
      {title: 'Actions', align: 'end', value: 'action'}
    ];
const groupBy = [{key: 'branchName', order: 'asc',},];
const itemsPerPage = 10;

</script>

<script>
import Edit_HOO from "@/components/modals/Edit_HOO.vue";

export default {
  data: () => ({
    edit_branch: false,
    branch_to_edit: null,
  }),
  methods: {
    handleEditBranch(branch) {
      this.branch_to_edit = branch;
      this.edit_branch = true;
    },
  },
  components: {
    Edit_HOO,
  }
}
</script>

<template>
  <v-data-table
      v-model:items-per-page="itemsPerPage"
      :headers="headers"
      :items-length="hoursOfOperation.length"
      :items="hoursOfOperation"
      :group-by="groupBy"
      class="elevation-1">
    <template v-slot:group-header="{ item, columns, toggleGroup, isGroupOpen }">
      <tr>
        <td :colspan="columns.length">
          <VBtn
              size="small"
              variant="text"
              :icon="isGroupOpen(item) ? '$expand' : '$next'"
              @click="toggleGroup(item)"
          ></VBtn>
          {{ item.value }}
        </td>
      </tr>
    </template>
    <template v-slot:item.action="{ item }">
      <v-btn icon elevation="0" density="compact">
        <v-icon icon="mdi-clipboard-edit-outline" color="grey-darken-1" @click="handleEditBranch(item)"></v-icon>
        <v-tooltip
            activator="parent"
            location="top"
        >Edit</v-tooltip>
      </v-btn>
    </template>
  </v-data-table>
  <Suspense>
    <Edit_HOO v-if="edit_branch" @close="edit_branch=false" v-bind:branch="this.branch_to_edit" />
  </Suspense>
</template>
