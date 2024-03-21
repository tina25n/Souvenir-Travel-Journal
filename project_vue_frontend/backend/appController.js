const express = require('express');
const appService = require('./appService');

const router = express.Router();

// ----------------------------------------------------------
// API endpoints
// Modify or extend these routes based on your project's needs.
router.get('/check-db-connection', async (req, res) => {
    const isConnect = await appService.testOracleConnection();
    if (isConnect) {
        res.send('connected');
    } else {
        res.send('unable to connect');
    }
});

router.get('/demotable', async (req, res) => {
    const tableContent = await appService.fetchDemotableFromDb();
    res.json({data: tableContent});
});

router.post("/initiate-demotable", async (req, res) => {
    const initiateResult = await appService.initiateDemotable();
    if (initiateResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/insert-demotable", async (req, res) => {
    const { id, name, password } = req.body;
    const insertResult = await appService.insertDemotable(id, name, password);
    if (insertResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/update-name-demotable", async (req, res) => {
    const { oldName, newName } = req.body;
    const updateResult = await appService.updateNameDemotable(oldName, newName);
    if (updateResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.get('/count-demotable', async (req, res) => {
    const tableCount = await appService.countDemotable();
    if (tableCount >= 0) {
        res.json({ 
            success: true,  
            count: tableCount
        });
    } else {
        res.status(500).json({ 
            success: false, 
            count: tableCount
        });
    }
});


// Users
router.get("/users",async (req, res) => {
    const tableContent = await appService.fetchUsersFromDb();
    res.json({data: tableContent});
});

router.get("/user/", async (req, res) => {
    let {id} = req.query;
    const getResult = await appService.fetchMyUser(id);
    res.json({data: getResult});
})

router.post("/initiate-users", async (req, res) => {
    const initiateResult = await appService.initiateUsers();
    if (initiateResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/insert-users", async (req, res) => {
    const {id, email, password, role, customerName, phoneNumber, SIN} = req.body;
    const insertResult = await appService.insertUsers(id, email, password, role, customerName, phoneNumber, SIN);
    if (insertResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/update-user", async (req, res) => {
    const {email, password, name, phoneNumber} = req.body;
    const updateResult = await appService.updateUser(email, password, name, phoneNumber);
    if (updateResult) {
        res.json({success: true});
    } else {
        res.status(500).json({success: false});
    }
})

router.delete('/delete-user/', async (req, res) => {
    let {email} = req.query;
    const deleteResult = await appService.deleteUser(email);
    if (deleteResult) {
        res.json({success: true});
    } else {
        res.status(500).json({success: false});
    }
})

router.get('/count-users', async (req, res) => {
    const tableCount = await appService.countUsers();
    if (tableCount >= 0) {
        res.json({
            success: true,
            count: tableCount
        });
    } else {
        res.status(500).json({
            success: false,
            count: tableCount
        });
    }
});

router.post('/login', async (req, res) => {
    const { email, password } = req.body;
    const result = await appService.LoginWithUserCredentials(email, password);
    if (result && result !== -1) {
        res.json({
            success: true,
            result: result
        });
    } else {
        res.status(500).json({ success: false });
    }
});

// Service Providers
router.get('/service-providers', async (req,res) => {
    const tableContent = await appService.fetchServiceProvidersFromDb();
    res.json({data: tableContent});
});

router.post('/initiate-sp', async (req, res) => {
    const initiateResult = await appService.initiateServiceProvider();
    if (initiateResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post('/insert-sp', async (req, res) => {
    const { SIN, name, branchName, dogAndCat, dogOnly, catOnly } = req.body;
    const insertResult = await appService.insertServiceProvider(SIN, name, branchName, dogAndCat, dogOnly, catOnly);
    if (insertResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post('/update-sp', async (req, res) => {
    const { SIN, name, branchName, dogAndCat, dogOnly, catOnly } = req.body;
    const updateResult = await appService.updateServiceProvider(SIN, name, branchName, dogAndCat, dogOnly, catOnly);
    if (updateResult) {
        res.json({success: true});
    } else {
        res.status(500).json({success: false});
    }
});


router.delete('/delete-sp/', async (req, res) => {
    let { SIN } = req.query;
    const deleteResult = await appService.deleteServiceProvider( SIN );
    if (deleteResult) {
        res.json({success: true});
    } else {
        res.status(500).json({success: false});
    }
})


// Work Schedule
router.get('/work-schedule', async (req,res) => {
    const tableContent = await appService.fetchWorkSchedule();
    res.json({data: tableContent});
});

router.get('/my-work-schedule/', async (req, res) => {
    let { SIN } = req.query;
    const getResult = await appService.fetchMyWorkSchedule(SIN);
    res.json({data: getResult});
});

router.post('/initiate-ws', async (req, res) => {
    const initiateResult = await appService.initiateWorkSchedule();
    if (initiateResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post('/insert-ws', async (req, res) => {
    const { SIN, day, startTime, endTime} = req.body;
    const insertResult = await appService.insertWorkSchedule(SIN, day, startTime, endTime);
    if (insertResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});
router.post('/update-ws', async (req, res) => {
    const { SIN, day, startTime, endTime} = req.body;
    const updateResult = await appService.updateWorkSchedule(SIN, day, startTime, endTime);
    if (updateResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

// Branch 1
router.get('/branch1', async (req, res) => {
    const tableContent = await appService.fetchBranch1();
    res.json({data: tableContent});
});

router.post('/initiate-branch1', async (req, res) => {
    const initiateResult = await appService.initiateBranch1();
    if (initiateResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post('/update-branch1', async (req, res) => {
    const { branchName, day, startTime, endTime } = req.body;
    const updateResult = await appService.updateBranch1(branchName, day, startTime, endTime);
    if (updateResult) {
        res.json({success: true});
    } else {
        res.status(500).json({success: false});
    }
});

// Branch 2
router.get('/branch2', async (req, res) => {
   const tableContent = await appService.fetchBranch2();
   res.json({data: tableContent});
});

router.get('/branch-names', async (req, res) => {
    const tableContent = await appService.fetchBranchNames();
    res.json({data: tableContent});
});

router.post('/initiate-branch2', async (req, res) => {
    const initiateResult = await appService.initiateBranch2();
    if (initiateResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post('/insert-branch2', async (req, res) => {
    const { branchName, streetAddress, city, postalCode, province } = req.body;
    const insertResult = await appService.insertBranch2(branchName, streetAddress, city, postalCode, province);
    if (insertResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post('/update-branch2', async (req, res) => {
    const { branchName, streetAddress, city, postalCode, province } = req.body;
    const updateResult = await appService.updateBranch2(branchName, streetAddress, city, postalCode, province);
    if (updateResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.delete('/delete-branch2', async (req, res) => {
    let {branchName} = req.query;
    const deleteResult = await appService.deleteBranch2(branchName);
    if (deleteResult) {
        res.json({success: true});
    } else {
        res.status(500).json({success: false});
    }
});

// Services

router.get('/services', async (req, res) => {
    const tableContent = await appService.fetchServicesFromDb();
    res.json({data: tableContent});
})
router.post('/initiate-services', async (req, res) => {
    const initiateResult = await appService.initiateServices();
    if (initiateResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
})

// Reservations

router.get('/reservations', async (req, res) => {
    const tableContent = await appService.fetchReservationsFromDb();
    res.json({data: tableContent});
})

router.get('/reservations-po', async (req, res) => {
    const {POName, POPhoneNumber} = req.query;
    const tableContent = await appService.fetchReservationsPO(POName, POPhoneNumber);
    res.json({data: tableContent});
})

router.get('/reservationsby', async (req, res) => {
        let { SIN, POName, POPhoneNumber, op1, op2} = req.query;
        const getResult = await appService.fetchReservationsBySINPO(SIN, POName, POPhoneNumber, op1, op2);
        res.json({data: getResult});
})

router.post('/initiate-reservations', async (req, res) => {
    const initiateResult = await appService.initiateReservations();
    if (initiateResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
})

// ----------------------
// Endpoints for PetOwners and OwnsPet
router.get("/petowners", async (req, res, next) => {
    const isQuery = Object.keys(req.query).length > 0
    if (isQuery) {
        if (!req.query.phone || !req.query.name || !req.query.phone.length || !req.query.name.length) {
            res.status(400).json({ error: "Missing/invalid required parameters" });
        }  else {
            const tableContent = await appService.fetchSpecificPetOwner(req.query.name, req.query.phone);
            res.json({data: tableContent});
        }
    } else {
        next();
    }
});

router.get("/petowners", async (req, res) => {
    const tableContent = await appService.fetchAllPetOwners();
    res.json({data: tableContent});
});

router.post("/create-petowners", async (req, res) => {
    const createResult = await appService.createPetOwners();
    if (createResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/insert-petowner", async (req, res) => {
    const { name, address, phone } = req.body;
    const insertResult = await appService.insertPetOwner(name, address, phone);
    if (insertResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/update-petowner", async (req, res) => {
    const { name, phone, newAddress} = req.body;
    const modifyResult = await appService.updatePetOwner(name, phone, newAddress);
    if (modifyResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/delete-petowner", async (req, res) => {
    const {name, phone} = req.body;
    const deleteResult = await appService.deletePetOwner(name, phone);
    if (deleteResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }

});

router.get("/pets", async (req, res, next) => {
    if (req.query.aid) {
        if (!req.query.aid.length) {
            res.status(400).json({ error: "Missing/invalid required parameters" });
        } else {
            const tableContent = await appService.fetchSpecificPet(req.query.aid);
            res.json({data: tableContent});
        }
    } else if (req.query.poname || req.query.pophone) {
        if (!req.query.poname || !req.query.pophone || !req.query.poname.length || !req.query.pophone.length) {
            console.log("Bad query for pets with poname and pophone")
            res.status(400).json({ error: "Missing/invalid required parameters" });
        } else {
            const tableContent = await appService.fetchOwnersPets(req.query.poname, req.query.pophone);
            res.json({data: tableContent});
        }
    } else {
        next();
    }
});

router.get("/pets", async (req, res) => {
    const tableContent = await appService.fetchAllPets();
    res.json({data: tableContent});
});

router.post("/create-pets", async (req, res) => {
    const createResult = await appService.createPets();
    if (createResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/insert-pet", async (req, res) => {
    const { aid, petName, petAge, petSize, petType, POName, POPhoneNumber } = req.body;
    const insertResult = await appService.insertPet(aid, petName, petAge, petSize, petType, POName, POPhoneNumber);
    if (insertResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/update-pet", async (req, res) => {
    const { aid, newPName, newPAge, newPSize, newPType, newOwnerName, newOwnerPhone } = req.body; 
    const modifyResult = await appService.updatePet(aid, newPName, newPAge, newPSize, newPType, newOwnerName, newOwnerPhone);
    if (modifyResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/delete-pet", async (req, res) => {
    const {aid} = req.body;
    const deleteResult = await appService.deletePet(aid);
    if (deleteResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }

});

// -------------------------
// Endpoints for Services_Inventory

router.get("/inventory", async (req, res, next) => {
    const isQuery = Object.keys(req.query).length > 0
    if (isQuery) {
        if (!req.query.productname || !req.query.productcode || isNaN(req.query.productcode) || !req.query.productname.length) {
            res.status(400).json({ error: "Missing/invalid required parameters" });
        }  else {
            const tableContent = await appService.fetchSpecificInventory(req.query.productname, req.query.productcode);
            res.json({data: tableContent});
        }
    } else {
        next();
    }
});

router.get("/inventory", async (req, res) => {
    const tableContent = await appService.fetchAllInventory();
    res.json({data: tableContent});
});

router.post("/create-inventory", async (req, res) => {
    const createResult = await appService.createInventory();
    if (createResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/insert-inventory", async (req, res) => {
    const { productName, productCode, productType, quantity, servicesID} = req.body;
    const insertResult = await appService.insertInventory(productName, productCode, productType, quantity, servicesID);
    if (insertResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/update-inventory", async (req, res) => {
    const { productName, productCode, newProductType, newQuantity, newServicesID} = req.body;
    const modifyResult = await appService.updateInventory(productName, productCode, newProductType, newQuantity, newServicesID);
    if (modifyResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/delete-inventory", async (req, res) => {
    const {productName, productCode} = req.body;
    const deleteResult = await appService.deleteInventory(productName, productCode);
    if (deleteResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});



// ------------------------------------
// Endpoints for Rooms
router.get("/room", async (req, res, next) => {
    const isQuery = Object.keys(req.query).length > 0
    if (isQuery) {
        if (!req.query.roomtype || !req.query.roomnumber || isNaN(req.query.roomnumber) || !req.query.roomtype.length) {
            res.status(400).json({ error: "Missing/invalid required parameters" });
        }  else {
            const tableContent = await appService.fetchSpecificRoom(req.query.roomtype, req.query.roomnumber);
            res.json({data: tableContent});
        }
    } else {
        next();
    }
});

router.get("/room", async (req, res) => {
    const tableContent = await appService.fetchAllRooms();
    res.json({data: tableContent});
});

router.post("/create-room", async (req, res) => {
    const createResult = await appService.createRooms();
    if (createResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/insert-room", async (req, res) => {
    const { roomType, roomNumber, roomSize} = req.body;
    const insertResult = await appService.insertRoom(roomType, roomNumber, roomSize);
    if (insertResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/update-room", async (req, res) => {
    const { roomType, roomNumber, newRoomSize} = req.body;
    const modifyResult = await appService.updateRoom(roomtype, roomnumber, newRoomSize);
    if (modifyResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/delete-room", async (req, res) => {
    const {roomType, roomNumber} = req.body;
    const deleteResult = await appService.deleteRoom(roomType, roomNumber);
    if (deleteResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

// -------------------------
// Endpoints for RoomsHasServices
router.get("/room/services", async (req, res, next) => {
    const isQuery = Object.keys(req.query).length > 0
    if (isQuery) {
        if (!req.query.roomnumber ||!req.query.servicesid || isNaN(req.query.roomnumber) || isNaN(req.query.servicesid)) {
            res.status(400).json({ error: "Missing/invalid required parameters" });
        }  else {
            const tableContent = await appService.fetchSpecificRoomsHasServices(req.query.roomnumber, req.query.servicesid);
            res.json({data: tableContent});
        }
    } else {
        next();
    }
});

router.get("/room/services", async (req, res) => {
    const tableContent = await appService.fetchAllRoomsHasServices();
    res.json({data: tableContent});
});

router.post("/create-room/services", async (req, res) => {
    const createResult = await appService.createRoomHasServices();
    if (createResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/insert-room/services", async (req, res) => {
    const { roomNumber, servicesID} = req.body;
    const insertResult = await appService.insertRoomsHasServices(roomNumber, servicesID);
    if (insertResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

router.post("/delete-room/services", async (req, res) => {
    const {roomNumber, servicesID} = req.body;
    const deleteResult = await appService.deleteRoomsHasServices(roomNumber, servicesID);
    if (deleteResult) {
        res.json({ success: true });
    } else {
        res.status(500).json({ success: false });
    }
});

module.exports = router;