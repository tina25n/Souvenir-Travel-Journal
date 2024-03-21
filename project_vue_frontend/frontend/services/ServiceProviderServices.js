import axios from 'axios';

/** File for API Calls to Service Provider and Work Schedule Tables
 *
 */
export async function getServiceProvidersTable() {
    const res = await axios.get('http://localhost:65535/service-providers');
    return createServiceProviderArray(res.data.data);
}
function createServiceProvider([SIN, name, branchName, dogAndCat, dogOnly, catOnly]) {
    return {SIN: SIN, name: name, branchName: branchName, dogAndCat: dogAndCat, dogOnly: dogOnly, catOnly: catOnly}
}
function createServiceProviderArray(data) {
    let result = [];
    for (let i = 0; i < data.length; i++) {
        result.push(createServiceProvider(data[i]));
    }
    return result;
}

export async function initiateServiceProviderTable() {
    await axios.post('http://localhost:65535/initiate-sp');
}

export async function insertServiceProviderTable(SIN, name, branchName, dogAndCat, dogOnly, catOnly) {
    let serviceProvider=  createServiceProvider([SIN, name, branchName, dogAndCat, dogOnly, catOnly]);
    const res = await axios.post('http://localhost:65535/insert-sp', serviceProvider);
    console.log(res);
}

export async function updateServiceProvider(SIN, name, branchName, dogAndCat, dogOnly, catOnly) {
    let serviceProvider=  createServiceProvider([SIN, name, branchName, dogAndCat, dogOnly, catOnly]);
    const res = await axios.post('http://localhost:65535/update-sp', serviceProvider);
    console.log(res);
}

export async function deleteServiceProvider(SIN) {
    await axios.delete(`http://localhost:65535/delete-sp?SIN=${SIN}`);
}

// Work Schedule

export async function getWorkScheduleTable() {
    const res = await axios.get('http://localhost:65535/work-schedule');
    return createWorkScheduleArray(res.data.data);
}

export async function getMyWorkScheduleTable(SIN) {
    const res = await axios.get(`http://localhost:65535/my-work-schedule?SIN=${SIN}`);
    return createWorkScheduleArray(res.data.data);
}
function createWorkSchedule([SIN, day, startTime, endTime]) {
    return {SIN: SIN, day: day, startTime: startTime, endTime: endTime}
}
function createWorkScheduleArray(data) {
    let result = [];
    for (let i = 0; i < data.length; i++) {
        result.push(createWorkSchedule(data[i]));
    }
    return result;
}
export async function initiateWorkScheduleTable() {
    await axios.post('http://localhost:65535/initiate-ws');
}

export async function updateWorkSchedule(SIN, day, startTime, endTime) {
    const ws = createWorkSchedule([SIN, day, startTime, endTime])
    await axios.post('http://localhost:65535/update-ws', ws);
}