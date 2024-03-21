/*
 * These functions below are for various webpage functionalities. 
 * Each function serves to process data on the frontend:
 *      - Before sending requests to the backend.
 *      - After receiving responses from the backend.
 * 
 * To tailor them to your specific needs,
 * adjust or expand these functions to match both your 
 *   backend endpoints 
 * and 
 *   HTML structure.
 * 
 */

// This function checks the database connection and updates its status on the frontend.
async function checkDbConnection() {
    const statusElem = document.getElementById('dbStatus');
    const loadingGifElem = document.getElementById('loadingGif');

    const response = await fetch('/check-db-connection', {
        method: "GET"
    });

    // Hide the loading GIF once the response is received.
    loadingGifElem.style.display = 'none';
    // Display the statusElem's text in the placeholder.
    statusElem.style.display = 'inline';

    response.text()
    .then((text) => {
        statusElem.textContent = text;
    })
    .catch((error) => {
        statusElem.textContent = 'connection timed out';  // Adjust error handling if required.
    });
}

// Fetches data from the PetOwner and displays it.
// Store PetOwners here instead.
async function fetchAndDisplayPetOwner() {
    const tableElement = document.getElementById('petownertable');
    const tableBody = tableElement.querySelector('tbody');

    const response = await fetch('/petowners', {
        method: 'GET'
    });

    const testQueryResponse = await fetch('/petowners?name=John&phone=1234567890', {
        method: 'GET'
    });

    const testQueryResponseData = await testQueryResponse.json();
    if (testQueryResponseData.data) {
       console.log("Query returned: " + testQueryResponseData.data);
    }

    const responseData = await response.json();
    const PetOwnerContent = responseData.data;

    // Always clear old, already fetched data before new fetching process.
    if (tableBody) {
        tableBody.innerHTML = '';
    }

    PetOwnerContent.forEach(petowners => {
        const row = tableBody.insertRow();
        petowners.forEach((field, index) => {
            const cell = row.insertCell(index);
            cell.textContent = field;
        });
    });
}

// This function resets or initializes the PetOwner.
async function resetPetOwner() {

    const response = await fetch("/create-petowners", {
        method: 'POST'
    });

    const responseData = await response.json();

    if (responseData.success) {
        const messageElement = document.getElementById('resetResultMsg');
        messageElement.textContent = "PetOwner initiated successfully!";
        fetchPetOwnerData();
    } else {
        alert("Error initiating table!");
    }
}

// Inserts new records into the PetOwner.
async function insertPetOwner(event) {
    event.preventDefault();

    const phoneVal = document.getElementById('insertPhone').value;
    const nameVal = document.getElementById('insertName').value;
    const addrVal = document.getElementById('insertAddress').value;

    const response = await fetch('/insert-petowner', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: nameVal,
            address: addrVal,
            phone: phoneVal,
        })
    });

    const responseData = await response.json();
    const messageElement = document.getElementById('insertResultMsg');

    if (responseData.success) {
        messageElement.textContent = "Data inserted successfully!";
        fetchPetOwnerData();
    } else {
        messageElement.textContent = "Error inserting data!";
    }
}

// Updates names in the PetOwner.
async function updatePetOwner(event) {
    event.preventDefault();

    const nameVal = document.getElementById('updateOldName').value;
    const phoneVal = document.getElementById('updateOldPhone').value;
    //const newPhoneVal = document.getElementById('updateNewPhone').value;
    //const newNameVal = document.getElementById('updateNewName').value;
    const newAddressVal = document.getElementById('updateNewAddress').value;

    const response = await fetch('/update-petowner', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: nameVal,
            phone: phoneVal,
            newAddress: newAddressVal,
        })
    });

    const responseData = await response.json();
    const messageElement = document.getElementById('updateNameResultMsg');

    if (responseData.success) {
        messageElement.textContent = "Name updated successfully!";
        fetchPetOwnerData();
    } else {
        messageElement.textContent = "Error updating name!";
    }
}

// Placeholder query for PetOwner.
async function testPetOwner() {
    /*
    const response = await fetch("/count-PetOwner", {
        method: 'GET'
    });

    const responseData = await response.json();
    const messageElement = document.getElementById('countResultMsg');

    if (responseData.success) {
        const tupleCount = responseData.count;
        messageElement.textContent = `The number of tuples in PetOwner: ${tupleCount}`;
    } else {
        alert("Error in count users!");
    }
    */
}

// Fetches data from users and displays it.
async function fetchAndDisplayUsers() {
    const tableElement = document.getElementById('users');
    const tableBody = tableElement.querySelector('tbody');

    const response = await fetch('/users', {
        method: 'GET'
    });

    const responseData = await response.json();
    const usertableContent = responseData.data;

    // Always clear old, already fetched data before new fetching process.
    if (tableBody) {
        tableBody.innerHTML = '';
    }

    usertableContent.forEach(user => {
        const row = tableBody.insertRow();
        user.forEach((field, index) => {
            const cell = row.insertCell(index);
            cell.textContent = field;
        });
    });
}

// This function resets or initializes the users table.
async function resetUsers() {
    const response = await fetch("/initiate-users", {
        method: 'POST'
    });
    const responseData = await response.json();

    if (responseData.success) {
        const messageElement = document.getElementById('userResetResultMsg');
        messageElement.textContent = "users initiated successfully!";
        fetchUserTableData();
    } else {
        alert("Error initiating table!");
    }
}

// Inserts new records into the users table.
async function insertUsers(event) {
    event.preventDefault();

    const idValue = document.getElementById('insertUserId').value;
    const userNameValue = document.getElementById('insertUserName').value;
    const passValue = document.getElementById('insertUserPassword').value;
    const roleValue = document.getElementById('insertUserRole').value;
    const poNameValue = document.getElementById('insertPOName').value;
    const poPhoneNumValue = document.getElementById('insertPOPhoneNumber').value;
    const serviceProviderSINValue = document.getElementById('insertSPSIN').value;

    const response = await fetch('/insert-users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: idValue,
            userName: userNameValue,
            password: passValue,
            role: roleValue,
            POName: poNameValue,
            POPhoneNumber: poPhoneNumValue,
            SIN: serviceProviderSINValue
        })
    });

    const responseData = await response.json();
    const messageElement = document.getElementById('insertUserResultMsg');

    if (responseData.success) {
        messageElement.textContent = "Data inserted successfully!";
        fetchUserTableData();
    } else {
        messageElement.textContent = "Error inserting data!";
        fetchUserTableData();
    }
}

async function fetchAndDisplayHOO() {
    const tableElement = document.getElementById('hoo');
    const tableBody = tableElement.querySelector('tbody');

    const response = await fetch('/branch1', {
        method: 'GET'
    });

    const responseData = await response.json();
    const hootableContent = responseData.data;

    // Always clear old, already fetched data before new fetching process.
    if (tableBody) {
        tableBody.innerHTML = '';
    }

    hootableContent.forEach(user => {
        const row = tableBody.insertRow();
        user.forEach((field, index) => {
            const cell = row.insertCell(index);
            cell.textContent = field;
        });
    });
}

// Inserts new records into the HoursOfOperation table.
async function insertHOO(event) {
    event.preventDefault();

    const branchNameValue = document.getElementById('insertBranchName').value;
    const dayValue = document.getElementById('insertDay').value;
    const startTimeValue = document.getElementById('insertStartTime').value;
    const endTimeValue = document.getElementById('insertEndTime').value;

    const response = await fetch('/insert-branch1', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            branchName: branchNameValue,
            day: dayValue,
            startTime: startTimeValue,
            endTime: endTimeValue
        })
    });

    const responseData = await response.json();
    const messageElement = document.getElementById('insertHOOResultMsg');
    if (responseData.success) {
        messageElement.textContent = "Data inserted successfully!";
        fetchUserTableData();
    } else {
        messageElement.textContent = "Error inserting data!";
        fetchUserTableData();
    }
}



// ---------------------------------------------------------------
// Initializes the webpage functionalities.
// Add or remove event listeners based on the desired functionalities.
window.onload = function() {
    checkDbConnection();
    fetchPetOwnerData();
    document.getElementById("resetpetowner").addEventListener("click", resetPetOwner);
    document.getElementById("insertpetowner").addEventListener("submit", insertPetOwner);
    document.getElementById("updatepetowner").addEventListener("submit", updatePetOwner);
    //document.getElementById("countPetOwner").addEventListener("click", countPetOwner);
    fetchUserTableData();
    document.getElementById("resetUsers").addEventListener("click", resetUsers);
    document.getElementById("insertUsers").addEventListener("submit", insertUsers);
    document.getElementById("insertHOO").addEventListener("submit", insertHOO);
    fetchAndDisplayHOO();
};

// General function to refresh the displayed table data. 
// You can invoke this after any table-modifying operation to keep consistency.
function fetchPetOwnerData() {
    fetchAndDisplayPetOwner();
}

function fetchUserTableData() {
    fetchAndDisplayUsers();
}
