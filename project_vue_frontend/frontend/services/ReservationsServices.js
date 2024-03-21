import axios from "axios";

export async function getReservationsTable() {
    const res = await axios.get('http://localhost:65535/reservations');
    console.log(res.data.data);
    return createServiceArray(res.data.data);
}

export async function getReservationsTablePO(POName, POPhoneNumber) {
    const res = await axios.get(`http://localhost:65535/reservations-po?POName=${POName}&POPhoneNumber=${POPhoneNumber}`);
    console.log(res.data.data);
    return createServiceArray(res.data.data);
}

export async function getReservationsTableBySINPO(SIN, POName, POPhoneNumber, op1, op2) {
    const res = await axios.get(`http://localhost:65535/reservationsby?SIN=${SIN}&POName=${POName}&POPhoneNumber=${POPhoneNumber}&op1=${op1}&op2=${op2}`);
    console.log(res.data.data);
    return createServiceArray(res.data.data);
}

function createService([RID, dateTimeService, dateTimeBooked, AID, POPhoneNumber, POName, servicesID, SIN]) {
    return {RID: RID, dateTimeService: dateTimeService, dateTimeBooked: dateTimeBooked, AID: AID, POPhoneNumber: POPhoneNumber,
            POName: POName, servicesID: servicesID, SIN: SIN};
}
function createServiceArray(data) {
    let result = [];
    for (let i = 0; i < data.length; i++) {
        result.push(createService(data[i]));
    }
    console.log(result);
    return result;
}
export async function initiateReservationsTable() {
    const res = await axios.post('http://localhost:65535/initiate-reservations');
    console.log(res);
}