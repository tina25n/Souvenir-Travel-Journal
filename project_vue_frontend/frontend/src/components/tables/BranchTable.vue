<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'
import {getBranchTable} from "../../../services/BranchServices";
import sp_branch from "@/components/modals/sp_branch.vue";

const branches = await getBranchTable();
const headers =
    [{title: 'Branch Name', align: 'start', key: 'branchName'},
      {title: 'Street Address', align: 'end', key: 'streetAddress'},
      {title: 'City', align: 'end', key: 'city'},
      {title: 'Postal Code', align: 'end', key: 'postalCode'},
      {title: 'Province', align: 'end', key: 'province'},
      {title: 'Actions', value: 'action'}
    ];
const itemsPerPage = 10;

</script>

<script>
import Edit_Branch from "@/components/modals/Edit_Branch.vue";
import {deleteBranch2} from "../../../services/BranchServices";

export default {
  data: () => ({
    edit_branch: false,
    branch_to_edit: null,
    view_sp: false,
  }),
  methods: {
    handleEditBranch(branch) {
      this.branch_to_edit = branch;
      this.edit_branch = true;
    },
    async handleDeleteBranch(branch) {
      await deleteBranch2(branch.branchName);
      location.reload();
    },
  },
  components: {
    Edit_Branch,
  }
}
</script>


<template>
  
  <div>
    
    <v-data-table
        v-model:items-per-page="itemsPerPage"
        :headers="headers"
        :items-length="branches.length"
        :items="branches"
        item-value="branchName"
        class="elevation-1">
      <template v-slot:item.action="{ item }">
        <v-btn icon elevation="0" density="compact" @click="view_sp= true">
          <v-tooltip
              activator="parent"
              location="top"
          >View Service Providers</v-tooltip>
          <v-icon icon="mdi-home" color="grey-darken-1"></v-icon>
        </v-btn>
        <v-btn icon elevation="0" density="compact" @click="handleEditBranch(item)">
          <v-icon icon="mdi-clipboard-edit-outline" color="grey-darken-1"></v-icon>
          <v-tooltip
              activator="parent"
              location="top"
          >Edit</v-tooltip>
        </v-btn>
        <v-btn icon elevation="0" density="compact" @click="handleDeleteBranch(item)">
          <v-tooltip
              activator="parent"
              location="top"
          >Delete</v-tooltip>
          <v-icon icon="mdi-delete-outline" color="grey-darken-1"></v-icon>
        </v-btn>
      </template>
    </v-data-table>
  </div>
  <div>
  <Suspense>
    <Edit_Branch v-if="edit_branch" @close="edit_branch=false" v-bind:branch=this.branch_to_edit />
  </Suspense>
  </div>
</template>