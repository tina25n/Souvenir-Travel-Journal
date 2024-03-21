import axios from 'axios';

/** File for API Calls to Branch1 (Hours of Operation) and Branch2 (General Branch) Tables
 *
 */
export async function getHoursOfOperationTable() {
    const res = await axios.get('http://localhost:65535/branch1');
    console.log(res.data.data);
    return createHoursOfOperationArray(res.data.data);
}
function createHoursOfOperation([branchName, day, startTime, endTime]) {
    return {branchName: branchName, day: day, startTime: startTime, endTime: endTime};
}
function createHoursOfOperationArray(data) {
    let result = [];
    for (let i = 0; i < data.length; i++) {
        result.push(createHoursOfOperation(data[i]));
    }
    console.log(result);
    return result;
}
export async function initiateHoursOfOperation() {
    const res = await axios.post('http://localhost:65535/initiate-branch1');
    console.log(res);
}

export async function updateBranch1([branchName, day, startTime, endTime]) {
    const body = createHoursOfOperation([branchName, day, startTime, endTime]);
    await axios.post('http://localhost:65535/update-branch1', body);
}

export async function getBranchTable() {
    const res = await axios.get('http://localhost:65535/branch2');
    console.log(res.data.data);
    return createBranchArray(res.data.data);
}

export async function getBranchNames() {
    const res = await axios.get('http://localhost:65535/branch-names');
    return createBranchNameArray(res.data.data);
}

function createBranchNameArray(data) {
    let result = [];
    for (let i = 0; i < data.length; i++) {
        result.push(data[i][0]);
    }
    console.log(result);
    return result;
}

function createBranch([branchName, streetAddress, city, postalCode, province]) {
    console.log([branchName, streetAddress, city, postalCode, province]);
    return {branchName: branchName, streetAddress: streetAddress, city: city, postalCode: postalCode, province: province};
}
function createBranchArray(data) {
    let result = [];
    for (let i = 0; i < data.length; i++) {
        result.push(createBranch(data[i]));
    }
    console.log(result);
    return result;
}
export async function initiateBranch2Table() {
    const res = await axios.post('http://localhost:65535/initiate-branch2');
    console.log(res);
}

export async function insertBranch2([branchName, streetAddress, city, postalCode, province]) {
    const body = createBranch([branchName, streetAddress, city, postalCode, province]);
    await axios.post('http://localhost:65535/insert-branch2', body);
}

export async function updateBranch2([branchName, streetAddress, city, postalCode, province]) {
    const body = createBranch([branchName, streetAddress, city, postalCode, province]);
    await axios.post('http://localhost:65535/update-branch2', body);
}

export async function deleteBranch2(branchName) {
    await axios.delete(`http://localhost:65535/delete-branch2?branchName=${branchName}`);
}