import axios from "axios";

export async function getPetsTable() {
    const res = await axios.get(`http://localhost:65535/pets`);
    console.log(res.data);
    return createPetsArray(res.data.data);
}

export async function getOwnersPets(poName, poPhoneNumber) {
    const res = await axios.get(`http://localhost:65535/pets?poname=${poName}&pophone=${poPhoneNumber}`);
    console.log(res.data);
    return createPetsArray(res.data.data);
}

function createPet([aid, name, age, psize, ptype, poname, pophone]) {
    return {aid: aid, petName: name, petAge: age, petSize: psize, petType: ptype, POName: poname, POPhoneNumber: pophone};
}

function createPetsArray(data) {
    let result = []
    for (let i = 0; i < data.length; i++) {
        result.push(createPet(data[i]));
    }
    return result;
}

export async function resetPetsTable() {
    const res = await axios.post(`http://localhost:65535/create-pets`);
    console.log(res.data);
    return res;
}

export async function insertPet(name, age, size, type, poname, pophone) {
    let aid = parseInt(localStorage.getItem("aid-counter")) || 20; // arb chose 20 because 1-5 is occupied
    localStorage.setItem("aid-counter", aid + 1);

    let aidArray = []
    if (localStorage.getItem("aids") !== null) {
        aidArray = JSON.parse(sessionStorage.getItem("aids"))
    }
    aidArray.push(aid)
    sessionStorage.setItem("aids", JSON.stringify(aidArray))
    console.log(aid + " was aid")
    aidArray.forEach(aid => console.log("AID: " + aid));
    //const p = createPet([aid, name, age, size, type, poname, pophone])
    //console.log(p.aid + " " + p.petName + " " + p.petAge + " " + p.petSize + " " + p.petType + " " + p.ownerName + " " + p.ownerPhone);

    const res = await axios.post(`http://localhost:65535/insert-pet`, createPet([aid, name, age, size, type, poname, pophone]));
    console.log(res.data);
    return res;
}

export async function updatePet(aid, name, age, size, type, poname, pophone) {
    // Need to know AID 
    //const header = { 'Content-Type': 'application/json' };
    //const res = await axios.post(`http://localhost:65535/update-pet`, JSON.stringify({aid: aid, newPName: name, newPAge: age, newPSize: size, newPType: type, newOwnerName: poname, newOwnerPhone: pophone}), {
    //    headers: header
    //});
    const res = await axios.post(`http://localhost:65535/update-pet`, {aid: aid, newPName: name, newPAge: age, newPSize: size, newPType: type, newOwnerName: poname, newOwnerPhone: pophone});
    return res;
}

export async function deletePet(aid) {
    const res = await axios.post(`http://localhost:65535/delete-pet`, {aid: aid});
    return res;
}
