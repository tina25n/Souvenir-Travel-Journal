const oracledb = require('oracledb');
const loadEnvFile = require('./utils/envUtil');

const envVariables = loadEnvFile('./.env');

// Database configuration setup. Ensure your .env file has the required database credentials.
const dbConfig = {
    user: `ora_xyzarman`,
    password: `a19407006`,
    connectString: `${envVariables.ORACLE_HOST}:${envVariables.ORACLE_PORT}/${envVariables.ORACLE_DBNAME}`,
    poolMax: 2
};


// // ----------------------------------------------------------
// // Wrapper to manage OracleDB actions, simplifying connection handling.
// async function withOracleDB(action) {
//     let connection;
//     try {
//         connection = await oracledb.getConnection(dbConfig);
//         return await action(connection);
//     } catch (err) {
//         console.error(err);
//         throw err;
//     } finally {
//         if (connection) {
//             try {
//                 await connection.close();
//             } catch (err) {
//                 console.error(err);
//             }
//         }
//     }
// }

// ----------------------------------------------------------
// Wrapper to manage OracleDB actions, simplifying connection handling.
let poolMade = false;
async function withOracleDB(action) {
    let connection;
    try {
        if (!poolMade) {
            await oracledb.createPool(dbConfig);
            poolMade = true;
        }

        connection = await oracledb.getConnection();
        return await action(connection);
    } catch (err) {
        console.error(err);
        throw err;
    } finally {
        if (connection) {
            try {
                await connection.close();
            } catch (err) {
                console.error(err);
            }
        }
    }
}


// ----------------------------------------------------------
// Core functions for database operations
// Modify these functions, especially the SQL queries, based on your project's requirements and design.
async function testOracleConnection() {
    return await withOracleDB(async (connection) => {
        return true;
    }).catch(() => {
        return false;
    });
}

async function fetchDemotableFromDb() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT * FROM DEMOTABLE');
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function initiateDemotable() {
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute(`DROP TABLE DEMOTABLE`);
        } catch(err) {
            console.log('Table might not exist, proceeding to create...');
        }

        const result = await connection.execute(`
            CREATE TABLE DEMOTABLE (
                id NUMBER PRIMARY KEY,
                name VARCHAR2(20),
                password VARCHAR2(20)
            )
        `);
        return true;
    }).catch(() => {
        return false;
    });
}

async function insertDemotable(id, name, password) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `INSERT INTO DEMOTABLE (id, name, password) VALUES (:id, :name, :password)`,
            [id, name, password],
            { autoCommit: true }
        );

        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function updateNameDemotable(oldName, newName) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `UPDATE DEMOTABLE SET name=:newName where name=:oldName`,
            [newName, oldName],
            { autoCommit: true }
        );

        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function countDemotable() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT Count(*) FROM DEMOTABLE');
        return result.rows[0][0];
    }).catch(() => {
        return -1;
    });
}

// ----------------------------------------------------------
// Functions for user account info (name/password/role) and logging in
//
async function fetchUsersFromDb() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT * FROM USERS');
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function fetchMyUser(id) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT * FROM USERS WHERE id=:id', [id]);
        return result.rows;
    }).catch(() => {
        return [];
    })
}

// Initiate a User Table and populate an admin user

async function initiateUsers() {
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute(`DROP TABLE USERS CASCADE CONSTRAINTS`);
        } catch(err) {
            console.log('User table might not exist, proceeding to create...');
        }
        const result = await connection.execute(`
            CREATE TABLE USERS (
                id INT PRIMARY KEY,
                email VARCHAR2(50) UNIQUE,
                password VARCHAR2(20),
                role INT,
                POName VARCHAR2(30),
                POPhoneNumber CHAR(10),
                SIN INT,
                FOREIGN KEY (POPhoneNumber, POName) REFERENCES PetOwner(POPhoneNumber, POName),
                FOREIGN KEY (SIN) REFERENCES SERVICEPROVIDER(SIN)
            )
        `);
        await insertUsers(1, 'admin@cpsc.com', 'admin123', 2, null, null, null);
        await insertUsers(2, 'customer@cpsc.com', 'pass1', 0, 'George', '7781112222', null);
        await insertUsers(3, 'sp@cpsc.com', 'pass2', 1, null, null, 111222333);
        return true;
    }).catch(() => {
        return false;
    });
}

async function insertUsers(id, email, password, role, POName, POPhoneNumber, SIN) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `INSERT INTO USERS (id, email, password, role, POName, POPhoneNumber, SIN) VALUES (:id, :email, :password, :role, :POName, :POPhoneNumber, :SIN)`,
            [id, email, password, role, POName, POPhoneNumber, SIN],
            { autoCommit: true }
        );
        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function updateUser(email, password, name, phoneNumber) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `UPDATE USERS SET password=:password, POName=:name, POPhoneNumber=:phoneNumber WHERE email=:email`,
            [password, name, phoneNumber, email],
            { autoCommit: true }
        );
        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function deleteUser(email) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `DELETE FROM USERS WHERE email=:email`,
            [email],
            {autoCommit: true}
        );
        return true;
    }).catch(() => {
        return false;
    })
}

async function countUsers() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT Count(*) FROM USERS');
        return result.rows[0][0];
    }).catch(() => {
        return -1;
    });
}
async function LoginWithUserCredentials(email, password) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(`SELECT id, email, role
                                                 FROM USERS
                                                 WHERE email=:email AND password=:password`,
                                                [email, password],
                                                { autoCommit: true });
        return result.rows[0];
    }).catch(() => {
        return -1;
    });
}
// ----------------------------------------------------------
// Function for WorkSchedule Table
//


async function fetchWorkSchedule() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT * FROM WORKSCHEDULE');
        return result.rows;
    }).catch(() => {
        return [];
    });
}
async function initiateWorkSchedule(){
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute(`DROP TABLE WORKSCHEDULE`);
        } catch (err) {
            console.log('WorkSchedule Table might not exist, proceeding to create...');
        }

        const result = await connection.execute(`
            CREATE TABLE WORKSCHEDULE(
                SIN INT,
                day VARCHAR(10),
                startTime VARCHAR(5),
                endTime VARCHAR(5),
                PRIMARY KEY (SIN, day)
            )
        `);

        return true;
    }).catch(() => {
        return false;
    });
}

async function updateWorkSchedule(SIN, Day, StartTime, EndTime) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `UPDATE WORKSCHEDULE SET startTime=:StartTime, endTime=:EndTime where day=:Day AND SIN=:SIN`,
            [StartTime, EndTime, Day, SIN],
            { autoCommit: true }
        );
        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function fetchMyWorkSchedule(SIN) {
    return await withOracleDB(async(connection) => {
        const result = await connection.execute(
            `SELECT * FROM WORKSCHEDULE WHERE SIN=:SIN`,
            [SIN]
        );
        return result.rows;
    }).catch(() => {
        return [];
    });
}

// ----------------------------------------------------------
// Functions for ServiceProviders
//

async function fetchServiceProvidersFromDb() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT * FROM ServiceProvider');
        return result.rows;
    }).catch(() => {
        return [];
    });
}
async function initiateServiceProvider(){
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute(`DROP TABLE ServiceProvider CASCADE CONSTRAINTS`);
        } catch (err) {
            console.log('Service Provider Table might not exist, proceeding to create...');
        }

        await initiateWorkSchedule();

        const result = await connection.execute(`
            CREATE TABLE ServiceProvider(
                SIN INT PRIMARY KEY,
                name VARCHAR(30),
                branchName VARCHAR(30),
                dogAndCat INT,
                dogOnly INT,
                catOnly INT,
                FOREIGN KEY (branchName) REFERENCES BRANCH2(branchName) ON DELETE CASCADE
            )
        `);
        await insertServiceProvider(111222333, 'Sandy','Vancouver Kitsilano', 1, 0, 0);
        await insertServiceProvider(444555666, 'Mike', 'Downtown Calgary', 0, 1,0);
        return true;
    }).catch(() => {
        return false;
    });
}

async function insertServiceProvider(SIN, name, branchName, dogAndCat, dogOnly, catOnly) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `INSERT INTO ServiceProvider (SIN, name, branchName, dogAndCat, dogOnly, catOnly) VALUES (:SIN, :name, :branchName, :dogAndCat, :dogOnly, :catOnly)`,
            [SIN, name, branchName, dogAndCat, dogOnly, catOnly],
            { autoCommit: true }
        );
        if (result) {
            await connection.execute(`INSERT INTO WORKSCHEDULE (SIN, day, startTime, endTime) VALUES (:SIN, 'Sunday', NULL, NULL)`, [SIN], {autoCommit: true});
            await connection.execute(`INSERT INTO WORKSCHEDULE (SIN, day, startTime, endTime) VALUES (:SIN, 'Monday', NULL, NULL)`, [SIN], {autoCommit: true});
            await connection.execute(`INSERT INTO WORKSCHEDULE (SIN, day, startTime, endTime) VALUES (:SIN, 'Tuesday', NULL, NULL)`, [SIN], {autoCommit: true});
            await connection.execute(`INSERT INTO WORKSCHEDULE (SIN, day, startTime, endTime) VALUES (:SIN, 'Wednesday', NULL, NULL)`, [SIN], {autoCommit: true});
            await connection.execute(`INSERT INTO WORKSCHEDULE (SIN, day, startTime, endTime) VALUES (:SIN, 'Thursday', NULL, NULL)`, [SIN], {autoCommit: true});
            await connection.execute(`INSERT INTO WORKSCHEDULE (SIN, day, startTime, endTime) VALUES (:SIN, 'Friday', NULL, NULL)`, [SIN], {autoCommit: true});
            await connection.execute(`INSERT INTO WORKSCHEDULE (SIN, day, startTime, endTime) VALUES (:SIN, 'Saturday', NULL, NULL)`, [SIN], {autoCommit: true});
        }
        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function updateServiceProvider(SIN, name, branchName, dogAndCat, dogOnly, catOnly) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `UPDATE ServiceProvider SET name=:name, branchName=:branchName, dogAndCat=:dogAndCat, dogOnly=:dogOnly, catOnly=:catOnly where SIN=:SIN`,
            [name, branchName, dogAndCat, dogOnly, catOnly, SIN],
            { autoCommit: true }
        );
        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function deleteServiceProvider(SIN) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `DELETE FROM ServiceProvider WHERE SIN=:SIN`,
            [SIN],
            {autoCommit: true}
        );
        await connection.execute(
            `DELETE FROM WorkSchedule WHERE SIN=:SIN`,
            [SIN],
            {autoCommit: true}
        );
        return true;
    }).catch(() => {
        return false;
    })
}

// ----------------------------------------------------------
// Functions for Branch1 - HoursOfOperation
//

async function fetchBranch1() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT * FROM BRANCH1');
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function initiateBranch1(){
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute(`DROP TABLE BRANCH1`);
        } catch (err) {
            console.log('Branch1 Table might not exist, proceeding to create...');
        }

        const result = await connection.execute(`
            CREATE TABLE BRANCH1(
                branchName VARCHAR(30),
                day VARCHAR(10),
                startTime VARCHAR(8),
                endTime VARCHAR(8),
                PRIMARY KEY (branchName, day)
            )
        `);
        return true;
    }).catch(() => {
        return false;
    });
}
async function updateBranch1(BranchName, Day, StartTime, EndTime) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `UPDATE BRANCH1
            SET startTime=:StartTime, endTime=:EndTime
            WHERE branchName=:BranchName AND day=:Day`,
            [StartTime, EndTime, BranchName, Day],
            { autoCommit: true }
        );
        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}


// ----------------------------------------------------------
// Functions for Branch2
//

async function fetchBranch2() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT * FROM BRANCH2');
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function fetchBranchNames() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT branchName FROM BRANCH2');
        return result.rows;
    }).catch(() => {
        return [];
    });
}
async function initiateBranch2(){
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute(`DROP TABLE BRANCH2 CASCADE CONSTRAINTS`);

        } catch (err) {
            console.log(err);
            console.log('Branch2 Table might not exist, proceeding to create...');
        }

        await initiateBranch1();

        const result = await connection.execute(`
            CREATE TABLE BRANCH2(
                branchName VARCHAR(30) PRIMARY KEY,
                streetAddress VARCHAR(50),
                city VARCHAR(20),
                postalCode VARCHAR(7),
                province VARCHAR(25)
            )
        `);
        await insertBranch2('Vancouver Kitsilano', '2000 McDonald St', 'Vancouver', 'V6K 3Y2', 'British Columbia');
        await insertBranch2('Downtown Calgary', '328 Centre St S', 'Calgary', 'T2G 4X6', 'Alberta');
        return true;
    }).catch(() => {
        return false;
    });
}

async function insertBranch2(BranchName, StreetAddress, City, PostalCode, Province) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `INSERT INTO BRANCH2 (branchName, streetAddress, city, postalCode, province) VALUES (:BranchName, :StreetAddress, :City, :PostalCode, :Province)`,
            [BranchName, StreetAddress, City, PostalCode, Province],
            { autoCommit: true }
        );
        if (result) {
            await connection.execute(`INSERT INTO BRANCH1 (branchName, day, startTime, endTime) VALUES (:BranchName, 'Sunday', NULL, NULL)`, [BranchName], {autoCommit: true});
            await connection.execute(`INSERT INTO BRANCH1 (branchName, day, startTime, endTime) VALUES (:BranchName, 'Monday', NULL, NULL)`, [BranchName], {autoCommit: true});
            await connection.execute(`INSERT INTO BRANCH1 (branchName, day, startTime, endTime) VALUES (:BranchName, 'Tuesday', NULL, NULL)`, [BranchName], {autoCommit: true});
            await connection.execute(`INSERT INTO BRANCH1 (branchName, day, startTime, endTime) VALUES (:BranchName, 'Wednesday', NULL, NULL)`, [BranchName], {autoCommit: true});
            await connection.execute(`INSERT INTO BRANCH1 (branchName, day, startTime, endTime) VALUES (:BranchName, 'Thursday', NULL, NULL)`, [BranchName], {autoCommit: true});
            await connection.execute(`INSERT INTO BRANCH1 (branchName, day, startTime, endTime) VALUES (:BranchName, 'Friday', NULL, NULL)`, [BranchName], {autoCommit: true});
            await connection.execute(`INSERT INTO BRANCH1 (branchName, day, startTime, endTime) VALUES (:BranchName, 'Saturday', NULL, NULL)`, [BranchName], {autoCommit: true});
        }
        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}
async function updateBranch2(branchName, streetAddress, city, postalCode, province) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `UPDATE Branch2
            SET streetAddress=:streetAddress, city=:city, postalCode=:postalCode, province=:province
            WHERE branchName=:branchName`,
            [streetAddress, city, postalCode, province, branchName],
            { autoCommit: true }
        );
        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}


async function deleteBranch2(branchName) {return await withOracleDB(async (connection) => {
    const result = await connection.execute(
        `DELETE FROM Branch2 WHERE branchName=:branchName`,
        [branchName],
        {autoCommit: true}
    );
    await connection.execute(
        `DELETE FROM Branch1 WHERE branchName=:branchName`,
        [branchName],
        {autoCommit: true}
    );
    return true;
}).catch(() => {
    return false;
})
}

// ----------------------------------------------------------
// Functions for Services
//

async function fetchServicesFromDb() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT * FROM Services');
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function initiateServices() {
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute(`DROP TABLE Services CASCADE CONSTRAINTS`);
        } catch (err) {
            console.log('Services table might not exist, proceeding to create');
        }

        const result = await connection.execute(`
            CREATE TABLE Services (
                ServicesID INTEGER PRIMARY KEY,
                ServicesName VARCHAR(30),
                BaseAmount DECIMAL(19,4),
                FinalRate DECIMAL(19,4),
                Duration INT
                )
        `);
        await insertServices(1, 'Medium Sized Dog Shampoo', 40.00, 60.00, 30);
        return true;
    }).catch(() => {
        return false;
    });
}

async function insertServices(servicesId, servicesName, baseAmount, finalRate, duration) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `INSERT INTO Services (ServicesID, ServicesName, BaseAmount, FinalRate, Duration) VALUES (:servicesId, :servicesName, :baseAmount, :finalRate, :duration)`,
            [servicesId, servicesName, baseAmount, finalRate, duration],
            { autoCommit: true }
        );
        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

// ----------------------------------------------------------
// Functions for Reservations
//

async function fetchReservationsFromDb() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(`SELECT * FROM Reservation`);
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function fetchReservationsPO(POName, POPhoneNumber) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(`SELECT * FROM Reservation WHERE POName=:POName AND POPhoneNumber=:POPhoneNumber`, [POName, POPhoneNumber]);
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function fetchReservationsBySINPO(SIN, POName, POPhoneNumber, op1, op2) {
        return await withOracleDB(async (connection) => {
            let result;
            if (op1 === 'AND') {
                if (op2 === 'AND') {
                    result = await connection.execute(`SELECT * FROM Reservation WHERE POName=:POName AND POPhoneNumber=:POPhoneNumber AND serviceProviderSIN=:SIN`,
                    [POName, POPhoneNumber, SIN]);
                } else {
                    result = await connection.execute(`SELECT * FROM Reservation WHERE POName=:POName AND POPhoneNumber=:POPhoneNumber OR serviceProviderSIN=:SIN`,
                    [POName, POPhoneNumber, SIN]);
                }
            } else {
                if (op2 === 'AND') {
                    result = await connection.execute(`SELECT * FROM Reservation WHERE POName=:POName OR POPhoneNumber=:POPhoneNumber AND serviceProviderSIN=:SIN`,
                    [POName, POPhoneNumber, SIN]);
                } else {
                    result = await connection.execute(`SELECT * FROM Reservation WHERE POName=:POName OR POPhoneNumber=:POPhoneNumber AND serviceProviderSIN=:SIN`,
                    [POName, POPhoneNumber, SIN]);
                }
            }
            return result.rows;
        }).catch(() => {
            return [];
        });
}

async function initiateReservations() {
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute(`DROP TABLE Reservation CASCADE CONSTRAINTS`);
        } catch(err) {
            console.log('Reservation Table might not exist, proceeding to create...');
        }

        const result = await connection.execute(`
            CREATE TABLE Reservation (
                RID NUMBER PRIMARY KEY,
                dateTimeService VARCHAR(20),
                dateTimeBooked VARCHAR(20),
                AID INTEGER,
                POPhoneNumber CHAR(10),
                POName VARCHAR2(30),
                servicesID INT,
                serviceProviderSIN INT,
                FOREIGN KEY (AID) REFERENCES OwnsPet(AID)
                    ON DELETE CASCADE,
                FOREIGN KEY (POPhoneNumber, POName) REFERENCES PetOwner(POPhoneNumber, POName)
                    ON DELETE CASCADE,
                FOREIGN KEY (ServicesID) REFERENCES Services(ServicesID) ON DELETE CASCADE,
                FOREIGN KEY (serviceProviderSIN) REFERENCES ServiceProvider(SIN) ON DELETE CASCADE
            )
        `);
        await insertReservations(1, `2023-12-10 14:00:00`, '2023-11-27 12:00:00', 1, "7781112222", "George", 1, 111222333)
        return true;
    }).catch(() => {
        return false;
    });
}

async function insertReservations(RID, dateTimeService, dateTimeBooked, AID, POPhoneNumber, POName, servicesID, serviceProviderSIN) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `INSERT INTO Reservation (RID, dateTimeService, dateTimeBooked, AID, POPhoneNumber, POName, servicesID, serviceProviderSIN) VALUES (:RID, :dateTimeService, :dateTimeBooked, :AID, :POPhoneNumber, :POName, :servicesID, :serviceProviderSIN)`,
            [RID, dateTimeService, dateTimeBooked, AID, POPhoneNumber, POName, servicesID, serviceProviderSIN],
            { autoCommit: true }
        );
        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

// ------------------------------------------------------------
// Databse operations for PetOwners annd OwnsPet

async function fetchAllPetOwners() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT * FROM PetOwner');
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function fetchSpecificPetOwner(name, phone) {
    return await withOracleDB(async (connection) => {
        phone = String(phone).replaceAll('-', '')
        if (phone.length !== 10) {
            console.log(`Invalid phone number: ${phone}`);
            return []; // or error code status in JSON
        }
        const result = await connection.execute(
            `SELECT * FROM PetOwner WHERE POName=:name AND POPhoneNumber=:phone`,
            [name, phone]
        );
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function createPetOwners() {
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute('DROP TABLE PetOwner CASCADE CONSTRAINTS'); //CASCADE CONSTRAINTS');
        } catch(err) {
            console.log('Table might not exist, proceeding to create...');
        }

        const result = await connection.execute(`
            CREATE TABLE PetOwner (
                POName VARCHAR2(30),
                POAddress VARCHAR2(50),
                POPhoneNumber CHAR(10),
                PRIMARY KEY (POPhoneNumber, POName)
            )
        `);
        await insertPetOwner('George', '1234 Pet St.', '778-111-2222');
        return true;
    }).catch(() => {
        return false;
    });
}

async function insertPetOwner(name, address, phone) {
    return await withOracleDB(async (connection) => {
        phone = String(phone).replaceAll('-', '')
        if (phone.length !== 10) {
            console.log(`Invalid phone number: ${phone}`);
            return false; // or error code status in JSON
        }
        const result = await connection.execute(
            `INSERT INTO PetOwner (POName, POAddress, POPhoneNumber) VALUES (:name, :address, :phone)`,
            [name, address, phone],
            { autoCommit: true }
        );

        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function updatePetOwner(name, phone, newAddress) {
    return await withOracleDB(async (connection) => {
        phone = String(phone).replaceAll('-', '')
        if (phone.length !== 10) {
            console.log(`Invalid phone number: ${phone}`);
            return false; // or error code status in JSON
        }
        const result = await connection.execute(
            `UPDATE PetOwner SET POAddress=:newAddress
            WHERE POName=:name AND POPhoneNumber=:phone`,
            [newAddress, name, phone],
            { autoCommit: true }
        );

        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function deletePetOwner(name, phone) {
    return await withOracleDB(async (connection) => {
        try {
            const result = await connection.execute(
                `DELETE FROM PetOwner WHERE POName=:name AND POPhoneNumber=:phone`,
                [name, phone],
                { autoCommit: true }
            );
            return true;
        } catch(err) {
            console.log(`PetOwner with name: ${name}, and phone: ${phone}, does not exist in database.`)
            return false;
        }
    }).catch(() => {
        return false;
    });
}

async function fetchAllPets() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute('SELECT * FROM OwnsPet');
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function fetchSpecificPet(aid) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `SELECT * FROM OwnsPet WHERE AID=:aid`,
            [aid]
        );
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function fetchOwnersPets(ownerName, ownerPhone) {
    return await withOracleDB(async (connection) => {
        ownerPhone = String(ownerPhone).replaceAll('-', '')
        if (ownerPhone.length !== 10) {
            console.log(`fetchOwnersPets: Invalid phone number = ${ownerPhone}`);
            return false; // or error code status in JSON
        }
        const result = await connection.execute(
            `SELECT * FROM OwnsPet WHERE POName=:ownerName AND POPhoneNumber=:ownerPhone`,
            [ownerName, ownerPhone]
        );
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function createPets() {
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute('DROP TABLE OwnsPet CASCADE CONSTRAINTS');
        } catch(err) {
            console.log('Table might not exist, proceeding to create...');
        }

        const result = await connection.execute(`
            CREATE TABLE OwnsPet (
                AID INTEGER PRIMARY KEY,
                PName VARCHAR2(30),
                PAge INTEGER,
                PSize VARCHAR2(10) NOT NULL CHECK (PSize IN ('small', 'medium', 'large')),
                PType VARCHAR2(10) NOT NULL CHECK (PType IN ('cat', 'dog')),
                POName VARCHAR2(30),
                POPhoneNumber CHAR(10),
                FOREIGN KEY (POPhoneNumber, POName) REFERENCES PetOwner(POPhoneNumber, POName)
            )
        `);

        await insertPet(1, 'Maple', 6, 'small', 'dog', 'George', '7781112222');
        return true;
    }).catch(() => {
        return false;
    });
}

async function insertPet(aid, petName, petAge, petSize, petType, POName, POPhoneNumber) {
    return await withOracleDB(async (connection) => {
        POPhoneNumber = String(POPhoneNumber).replaceAll('-', '')
        if (POPhoneNumber.length !== 10) {
            console.log(`Invalid phone number: ${POPhoneNumber}`);
            return false; // or error code status in JSON
        }
        const result = await connection.execute(
            `INSERT INTO OwnsPet (AID, PName, PAge, PSize, PType, POName, POPhoneNumber) 
            VALUES (:aid, :petName, :petAge, :petSize, :petType, :POName, :POPhoneNumber)`,
            [aid, petName, petAge, petSize.toLowerCase(), petType.toLowerCase(), POName, POPhoneNumber],
            { autoCommit: true }
        );

        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function updatePet(aid, newPName, newPAge, newPSize, newPType, newOwnerName, newOwnerPhone) {
    return await withOracleDB(async (connection) => {
        newOwnerPhone = String(newOwnerPhone).replaceAll('-', '')
        if (newOwnerPhone.length !== 10) {
            console.log(`Invalid phone number: ${newOwnerPhone}`);
            return false; // or error code status in JSON
        }
        const result = await connection.execute(
            `UPDATE OwnsPet SET PName=:newPName, PAge=:newPAge, PSize=:newPSize, PType=:newPType,
            POName=:newOwnerName, POPhoneNumber=:newOwnerPhone
            WHERE AID=:aid`,
            [newPName, newPAge, newPSize.toLowerCase(), newPType.toLowerCase(), newOwnerName, newOwnerPhone, aid],
            { autoCommit: true }
        );

        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function deletePet(aid) {
    return await withOracleDB(async (connection) => {
        try {
            const result = await connection.execute(
                `DELETE FROM OwnsPet WHERE AID=:aid`,
                [aid],
                { autoCommit: true }
            );
            return true;
        } catch(err) {
            console.log(`OwnsPet with AID: ${aid}, does not exist in database.`)
            return false;
        }
    }).catch(() => {
        return false;
    });
}



// ------------------------------------------------------------
// Database operations for Inventory

async function fetchAllInventory() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `SELECT * 
            FROM Services_Inventory1 si1, Services_Inventory2 si2
            WHERE si1.ProductName = si2.ProductName`
        );
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function fetchSpecificInventory(productName, productCode) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `SELECT * 
            FROM Services_Inventory1 si1, Services_Inventory2 si2
            WHERE si1.ProductName = :productName AND si2.ProductCode = :productCode AND
            si1.ProductName = si2.ProductName`,
            [productName, productCode]
        );
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function createInventory() {
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute('DROP TABLE Services_Inventory2');
            await connection.execute('DROP TABLE Services_Inventory1');
        } catch(err) {
            console.log('Table(s) might not exist, proceeding to create...');
        }

        const result1 = await connection.execute(`
            CREATE TABLE Services_Inventory1 (
                ProductName VARCHAR2(30) PRIMARY KEY,
                ProductCode VARCHAR2(30),
            )
        `);
        const result2 = await connection.execute(`
            CREATE TABLE Services_Inventory2 (
                ProductCode INTEGER,
                Quantity VARCHAR2(10) NOT NULL CHECK (Quantity IN ('empty', 'low', 'half', 'full')),
                ProductName VARCHAR2(30),
                ServicesID INTEGER,
                PRIMARY KEY (ProductCode),
                FOREIGN KEY (ProductName) REFERENCES Services_Inventory1(ProductName)
                    ON DELETE CASCADE,
                FOREIGN KEY (ServicesID) REFERENCES Services(ServicesID)
                    ON DELETE SET NULL
        `);

        return true;
    }).catch(() => {
        return false;
    });
}

async function insertInventory(productName, productCode, productType, quantity, servicesID) {
    return await withOracleDB(async (connection) => {
        const result1 = await connection.execute(
            `INSERT INTO Services_Inventory1 (ProductName, ProductType) 
            VALUES (:productName, :productType)`,
            [productName, productType],
            { autoCommit: true }
        );

        const result2 = await connection.execute(
            `INSERT INTO Services_Inventory2 (ProductCode, Quantity, ProductName, ServicesID) 
            VALUES (:productCode, :quantity, :productName, :servicesID)`,
            [productCode, quantity.toLowerCase(), productName, servicesID],
            { autoCommit: true }
        );

        return (result1.rowsAffected && result1.rowsAffected > 0) && (result2.rowsAffected && result2.rowsAffected > 0);
    }).catch(() => {
        return false;
    });
}

async function updateInventory(productName, productCode, newProductType, newQuantity, newServicesID) {
    return await withOracleDB(async (connection) => {
        const result1 = await connection.execute(
            `UPDATE Services_Inventory1 SET ProductType=:newProductType
            WHERE ProductName=:productName AND ProductName IN (SELECT ProductName FROM Services_Inventory2 WHERE ProductCode=:productCode)`,
            [newProductType, productName, productCode],
            { autoCommit: true }
        );

        const result2 = await connection.execute(
            `UPDATE Services_Inventory2 SET Quantity=:newQuantity, ServicesID=:newServicesID
            WHERE ProductCode=:productCode AND ProductCode IN (SELECT ProductCode FROM Services_Inventory1 WHERE ProductName=:productName)`,
            [newQuantity.toLowerCase(), newServicesID, productCode, productName],
            { autoCommit: true }
        );

        return (result1.rowsAffected && result1.rowsAffected > 0) && (result2.rowsAffected && result2.rowsAffected > 0);
    }).catch(() => {
        return false;
    });
}

async function deleteInventory(productName, productCode) {
    return await withOracleDB(async (connection) => {
        try {
            const result2 = await connection.execute(
                `DELETE FROM Services_Inventory2 WHERE ProductCode=:productCode`,
                [productCode],
                { autoCommit: true }
            );

            const result1 = await connection.execute(
                `DELETE FROM Services_Inventory1 WHERE ProductName=:productName`,
                [productName],
                { autoCommit: true }
            );
            return true;
        } catch(err) {
            console.log(`Inventory with ProductName: ${productName}, ProductCode: ${productCode}, does not exist in database.`)
            return false;
        }
    }).catch(() => {
        return false;
    });
}

// ------------------------------------------------------------
// Database operations for Rooms

async function fetchAllRooms() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `SELECT * 
            FROM Room1 r1, Room r2
            WHERE r1.RoomType = r2.RoomType`
        );
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function fetchSpecificRoom(roomType, roomNumber) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `SELECT * 
            FROM Room1 r1, Room r2
            WHERE r1.RoomType = :roomType AND r2.RoomNumber = :roomNumber AND
            r1.RoomType = r2.RoomType`,
            [roomType, roomNumber]
        );
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function createRooms() {
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute('DROP TABLE Room2');
            await connection.execute('DROP TABLE Room1');
        } catch(err) {
            console.log('Table(s) might not exist, proceeding to create...');
        }

        const result1 = await connection.execute(`
            CREATE TABLE Room1 (
                RoomType VARCHAR2(30) PRIMARY KEY,
                RSize VARCHAR2(10) NOT NULL CHECK (RSize IN ('small', 'medium', 'large'))
            )
        `);
        const result2 = await connection.execute(`
            CREATE TABLE Room2 (
                RoomNumber INTEGER PRIMARY KEY,
                RoomType VARCHAR2(30),
                FOREIGN KEY (RoomType) REFERENCES Room1(RoomType)
                    ON DELETE CASCADE
        `);

        return true;
    }).catch(() => {
        return false;
    });
}

async function insertRoom(roomType, roomNumber, roomSize) {
    return await withOracleDB(async (connection) => {
        const result1 = await connection.execute(
            `INSERT INTO Room1 (RoomType, RSize) 
            VALUES (:roomType, :rSize)`,
            [roomType, roomSize.toLowerCase()],
            { autoCommit: true }
        );

        const result2 = await connection.execute(
            `INSERT INTO Room2 (RoomNumber, RoomType) 
            VALUES (:roomNumber, :roomType)`,
            [roomNumber, roomType],
            { autoCommit: true }
        );

        return (result1.rowsAffected && result1.rowsAffected > 0) && (result2.rowsAffected && result2.rowsAffected > 0);
    }).catch(() => {
        return false;
    });
}

async function updateRoom(roomType, roomNumber, newRoomSize) {
    return await withOracleDB(async (connection) => {
        const result1 = await connection.execute(
            `UPDATE Room1 SET RSize=:newRoomSize
            WHERE RoomType=:roomType AND RoomType IN (SELECT RoomType FROM Room2 WHERE roomNumber=:roomNumber)`,
            [newRoomSize.toLowerCase(), roomType, roomNumber],
            { autoCommit: true }
        );

        return (result1.rowsAffected && result1.rowsAffected > 0);
    }).catch(() => {
        return false;
    });
}

async function deleteRoom(roomType, roomNumber) {
    return await withOracleDB(async (connection) => {
        try {
            const result = await connection.execute(
                `DELETE FROM Room2 WHERE RoomNumber=:roomNumber AND RoomType=:roomType`,
                [roomNumber, roomType],
                { autoCommit: true }
            );
            /* Shouldn't need this because of ON DELETE CASCADE
            const result2 = await connection.execute(
                `DELETE FROM Room1 WHERE RoomType=:roomType`,
                [roomType],
                { autoCommit: true }
            );
            */
            return true;
        } catch(err) {
            console.log(`Room with RoomNumber: ${roomNumber}, RoomType: ${roomType}, does not exist in database.`)
            return false;
        }
    }).catch(() => {
        return false;
    });
}

// ------------------------------------------------------------
// Database operations for RoomsHasServices

async function fetchAllRoomsHasServices() {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `SELECT * FROM RoomHasServices`
        );
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function fetchSpecificRoomsHasServices(roomNumber, servicesID) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `SELECT * FROM RoomHasServices
            WHERE RoomNumber = :roomNumber AND ServicesID = :servicesID`,
            [roomNumber, servicesID]
        );
        return result.rows;
    }).catch(() => {
        return [];
    });
}

async function createRoomsHasServices() {
    return await withOracleDB(async (connection) => {
        try {
            await connection.execute('DROP TABLE RoomHasServices');
        } catch(err) {
            console.log('Table might not exist, proceeding to create...');
        }

        const result = await connection.execute(`
            CREATE TABLE RoomsHasServices (
                RoomNumber INTEGER,
                ServicesID INTEGER,
                PRIMARY KEY (RoomNumber, ServicesID),
                FOREIGN KEY (RoomNumber) REFERENCES Room2(RoomNumber),
                FOREIGN KEY (ServicesID) REFERENCES Services2(ServicesID)
            )
        `);

        return true;
    }).catch(() => {
        return false;
    });
}

async function insertRoomsHasServices(roomNumber, servicesID) {
    return await withOracleDB(async (connection) => {
        const result = await connection.execute(
            `INSERT INTO RoomHasServices (RoomNumber, ServicesID) 
            VALUES (:roomNumber, :servicesID)`,
            [roomNumber, servicesID],
            { autoCommit: true }
        );

        return result.rowsAffected && result.rowsAffected > 0;
    }).catch(() => {
        return false;
    });
}

async function deleteRoomsHasServices(roomNumber, servicesID) {
    return await withOracleDB(async (connection) => {
        try {
            const result = await connection.execute(
                `DELETE FROM RoomHasServices WHERE RoomNumber=:roomNumber AND ServicesID=:servicesID`,
                [roomNumber, servicesID],
                { autoCommit: true }
            );
            return true;
        } catch(err) {
            console.log(`RoomHasServices with RoomNumber: ${roomNumber}, ServicesID: ${servicesID}, does not exist in database.`)
            return false;
        }
    }).catch(() => {
        return false;
    });
}


module.exports = {
    testOracleConnection,
    fetchDemotableFromDb,
    initiateDemotable, 
    insertDemotable, 
    updateNameDemotable, 
    countDemotable,

    fetchUsersFromDb,
    fetchMyUser,
    initiateUsers,
    insertUsers,
    updateUser,
    deleteUser,
    countUsers,
    LoginWithUserCredentials,

    fetchWorkSchedule,
    initiateWorkSchedule,
    updateWorkSchedule,
    fetchMyWorkSchedule,

    fetchServiceProvidersFromDb,
    initiateServiceProvider,
    insertServiceProvider,
    updateServiceProvider,
    deleteServiceProvider,

    fetchBranch1,
    initiateBranch1,
    updateBranch1,

    fetchBranch2,
    fetchBranchNames,
    initiateBranch2,
    insertBranch2,
    updateBranch2,
    deleteBranch2,

    fetchServicesFromDb,
    initiateServices,
    fetchReservationsFromDb,
    fetchReservationsBySINPO,
    fetchReservationsPO,
    initiateReservations,

    fetchAllPetOwners,
    fetchSpecificPetOwner,
    fetchOwnersPets,
    createPetOwners,
    insertPetOwner,
    updatePetOwner,
    deletePetOwner,
    fetchAllPets,
    fetchSpecificPet,
    createPets,
    insertPet,
    updatePet,
    deletePet,

    fetchAllInventory,
    fetchSpecificInventory,
    createInventory,
    insertInventory,
    updateInventory,
    deleteInventory,

    fetchAllRooms,
    fetchSpecificRoom,
    createRooms,
    insertRoom,
    updateRoom,
    deleteRoom,

    fetchAllRoomsHasServices,
    fetchSpecificRoomsHasServices,
    createRoomsHasServices,
    insertRoomsHasServices,
    deleteRoomsHasServices
};