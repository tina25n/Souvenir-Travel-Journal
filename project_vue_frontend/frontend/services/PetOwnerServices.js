import axios from "axios";

export async function getPetOwnersTable() {
    const res = await axios.get(`http://localhost:65535/petowners`);
    console.log(res.data.data);
    return createPetOwnerArray(res.data.data);
}

function createPetOwner([poName, poAddress, poPhoneNumber]) {
    return {poName: poName, poAddress: poAddress, poPhoneNumber: poPhoneNumber};
}
function createPetOwnerArray(data) {
    let result = [];
    for (let i = 0; i < data.length; i++) {
        result.push(createPetOwner(data[i]));
    }
    console.log(result);
    return result;
}

export async function createPetOwnerTable() {
    const res = await axios.post(`http://localhost:65535/create-petowners`);
    console.log(res);
}

export async function insertPetOwner(poName, poAddress, poPhoneNumber) {
    const res = await axios.post(`http://localhost:65535/insert-petowner`, {poName: poName, poAddress: poAddress, poPhoneNumber: poPhoneNumber});
    console.log(res);
}

export async function updatePetOwner(poName, newPoAddress, poPhoneNumber) {
    const res = await axios.post(`http://localhost:65535/update-petowner`, {poName: poName, poAddress: newPoAddress, poPhoneNumber: poPhoneNumber});
    console.log(res);
}

export async function deletePetOwner(poName, poPhoneNumber) {
    const res = await axios.post(`http://localhost:65535/delete-petowner`, {name: poName, phone: poPhoneNumber});
    console.log(res);
}