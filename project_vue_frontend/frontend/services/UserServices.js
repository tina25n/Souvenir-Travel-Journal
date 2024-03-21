import axios from 'axios';

/** File for API Calls to User Table (Our table for user accounts)
 *  User Accounts can be associated with a Customer or a Service Provider
 *  Role 0 - Customer, Role 1 - Service Provider, Role - 2 Administrative
 */
export async function getUsersTable() {
    const res = await axios.get('http://localhost:65535/users');
    console.log(res.data.data);
    return createUserArray(res.data.data);
}
function createUser([id, email, password, role, POName, POPhoneNumber, SIN]) {
    return {id: id, email: email, password: password, role: role, POName: POName, POPhoneNumber: POPhoneNumber, SIN: SIN}
}
function createUserArray(data) {
    let result = [];
    for (let i = 0; i < data.length; i++) {
        result.push(createUser(data[i]));
    }
    console.log(result);
    return result;
}
export async function initiateUserTable() {
    const res = await axios.post('http://localhost:65535/initiate-users');
    console.log(res);
}

export async function getMyUserDetails() {
    let id = localStorage.getItem("id");
    const res = await axios.get(`http://localhost:65535/user?id=${id}`);
    return createUser(res.data.data[0]);
}

export async function createUserAccount([id, email, password, role, POName, POPhoneNumber, SIN]) {
    const body = createUser([id, email, password, role, POName, POPhoneNumber, SIN]);
    await axios.post('http://localhost:65535/insert-users', body);
}

export async function updateUserAccount([email, password, POName, POPhoneNumber]) {
    const body = {email: email, password: password, POName: POName, POPhoneNumber: POPhoneNumber};
    await axios.post('http://localhost:65535/update-user', body);
}

export async function deleteUserAccount(email) {
    console.log(email);
    await axios.delete(`http://localhost:65535/delete-user?email=${email}`);
}