import axios from "axios";

export async function getServicesTable() {
    const res = await axios.get('http://localhost:65535/services');
    console.log(res.data.data);
    return createServiceArray(res.data.data);
}
function createService([servicesId, servicesName, baseAmount, finalRate, duration]) {
    return {servicesId: servicesId, servicesName: servicesName, baseAmount: baseAmount, finalRate: finalRate, duration: duration};
}
function createServiceArray(data) {
    let result = [];
    for (let i = 0; i < data.length; i++) {
        result.push(createService(data[i]));
    }
    console.log(result);
    return result;
}
export async function initiateServicesTable() {
    const res = await axios.post('http://localhost:65535/initiate-services');
    console.log(res);
}