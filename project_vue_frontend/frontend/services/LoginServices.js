import axios from 'axios';

/** File for API Call for Login
 *
 */

export var Authenticated = CheckAuthenticated();
export async function Login([email, password]) {
    const data = {email: email, password: password}
    axios.post('http://localhost:65535/login', data).then( (res) => {
        if (res.status === 200) {
            localStorage.setItem("id", res.data.result.at(0));
            localStorage.setItem("email", res.data.result.at(1));
            localStorage.setItem("role", res.data.result.at(2));
        }
        CheckAuthenticated();
        return res.data.data;
    });
}
export function CheckAuthenticated() {
    console.log("Checking Auth!!");
    let username = localStorage.getItem("email");
    Authenticated = (username !== null);
    return (username !== null);
}

export function Logout() {
    localStorage.removeItem("id");
    localStorage.removeItem("email");
    localStorage.removeItem("name");
    localStorage.removeItem("role");
    CheckAuthenticated();
    return 1;
}