import axios from 'axios';
export async function getDemoTable() {
    const res = await axios.get('http://localhost:65535/demotable');
    return createUserArray(res.data.data);
}
function createUser([id, name]) {
    return {id: id, name: name}
}
function createUserArray(data) {
    let result = [];
    for (let i = 0; i < data.length; i++) {
        result.push(createUser(data[i]));
    }
    console.log(result);
    return result;
}
