CREATE TABLE PetOwner (
    POName VARCHAR2(30),
    POAddress VARCHAR2(50),
    POPhoneNumber CHAR(10),
    PRIMARY KEY (POPhoneNumber, POName)
);

CREATE TABLE OwnsPet (
    AID INTEGER PRIMARY KEY,
    PName VARCHAR2(30),
    PAge INTEGER,
    PSize VARCHAR2(10) NOT NULL CHECK (PSize IN ('small', 'medium', 'large')), /* ENUM('small', 'medium', 'large') */
    PType VARCHAR2(10) NOT NULL CHECK (PType IN ('cat', 'dog')), /* ENUM('cat', 'dog'), */
    POName VARCHAR2(30),
    POPhoneNumber CHAR(10),
    FOREIGN KEY (POPhoneNumber, POName) REFERENCES PetOwner(POPhoneNumber, POName) ON DELETE CASCADE
);

CREATE TABLE Branch2 (
    branchName VARCHAR(30) PRIMARY KEY,
    streetAddress VARCHAR(50),
    city VARCHAR(20),
    postalCode VARCHAR(7),
    province VARCHAR(25)
);

CREATE TABLE Branch1(
    branchName VARCHAR(30),
    day VARCHAR(10),
    startTime VARCHAR(8),
    endTime VARCHAR(8),
    PRIMARY KEY (branchName, day),
    FOREIGN KEY (branchName) REFERENCES Branch2(branchName) ON DELETE CASCADE
);

CREATE TABLE ServiceProvider (
    SIN INT PRIMARY KEY,
    name VARCHAR(30),
    branchName VARCHAR(30),
    dogAndCat INT,
    dogOnly INT,
    catOnly INT,
    FOREIGN KEY (branchName) REFERENCES Branch2(branchName) ON DELETE CASCADE
);

CREATE TABLE Room1(
    RoomType VARCHAR2(30) PRIMARY KEY,
    RSize VARCHAR2(10) NOT NULL CHECK (RSize IN ('small', 'medium', 'large')) /* ENUM('small', 'medium', 'large') */
);

CREATE TABLE Room2(
    RoomNumber INTEGER PRIMARY KEY,
    RoomType VARCHAR2(30),
    FOREIGN KEY (RoomType) REFERENCES Room1(RoomType)
        ON DELETE CASCADE /* ON UPDATE CASCADE */
);

CREATE TABLE Services(
    ServicesID INTEGER PRIMARY KEY,
    ServicesName VARCHAR(30),
    BaseAmount DECIMAL(19,4),
    FinalRate DECIMAL(19,4),
    Duration INT
);

CREATE TABLE RoomsHasServices(
    RoomNumber INTEGER,
    ServicesID INTEGER,
    PRIMARY KEY (RoomNumber, ServicesID),
    FOREIGN KEY (RoomNumber) REFERENCES Room2(RoomNumber),
    FOREIGN KEY (ServicesID) REFERENCES Services(ServicesID)
);

CREATE TABLE Services_Inventory1(
    ProductName VARCHAR2(30) PRIMARY KEY,
    ProductType VARCHAR2(30)
);

CREATE TABLE Services_Inventory2(
    ProductCode INTEGER,
    Quantity VARCHAR2(10) NOT NULL CHECK (Quantity IN ('empty', 'low', 'half', 'full')), /* ENUM('empty', 'low', 'half', 'full'), */
    ProductName VARCHAR2(30),
    ServicesID INTEGER,
    PRIMARY KEY (ProductCode),
    FOREIGN KEY (ProductName) REFERENCES Services_Inventory1(ProductName)
        ON DELETE CASCADE, /* ON UPDATE CASCADE, */
    FOREIGN KEY (ServicesID) REFERENCES Services(ServicesID)
        ON DELETE SET NULL /* ON UPDATE CASCADE */
);

CREATE TABLE Haircut(
    ServicesID INTEGER PRIMARY KEY,
    HaircutFurType VARCHAR2(10) NOT NULL CHECK (HaircutFurType IN ('soft', 'rugged', 'hairless')), /* ENUM('soft', 'rugged', 'hairless'), */
    FurLength FLOAT,
    FOREIGN KEY (ServicesID) REFERENCES Services(ServicesID)
        ON DELETE CASCADE /* ON UPDATE CASCADE */
);

CREATE TABLE NailServices(
    ServicesID INTEGER PRIMARY KEY,
    Sensitivity VARCHAR2(10) NOT NULL CHECK (Sensitivity IN ('low', 'medium', 'high')), /* ENUM('low', 'medium', 'high'), */
    FOREIGN KEY (ServicesID) REFERENCES Services(ServicesID)
        ON DELETE CASCADE /* ON UPDATE CASCADE */
);


CREATE TABLE TeethCleaning(
    ServicesID INTEGER PRIMARY KEY,
    DentalCondition VARCHAR2(15) NOT NULL CHECK (DentalCondition IN ('excellent', 'good', 'poor', 'very poor')), /* ENUM('excellent', 'good', 'poor', 'very poor') */
    FOREIGN KEY (ServicesID) REFERENCES Services(ServicesID)
        ON DELETE CASCADE /* ON UPDATE CASCADE */
);

CREATE TABLE ShampooAndEarCleaning(
    ServicesID INTEGER PRIMARY KEY,
    Cleanliness VARCHAR2(15) NOT NULL CHECK (Cleanliness IN ('excellent', 'good', 'poor', 'very poor')), /* ENUM('excellent', 'good', 'poor', 'very poor'), */
    SECFurType VARCHAR2(10) NOT NULL CHECK (SECFurType IN ('soft', 'rugged', 'hairless')), /* ENUM('soft', 'rugged', 'hairless'), */
    FOREIGN KEY (ServicesID) REFERENCES Services(ServicesID)
        ON DELETE CASCADE /* ON UPDATE CASCADE */
);

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
);

CREATE TABLE Users (
    id INT PRIMARY KEY,
    email VARCHAR2(50) UNIQUE,
    password VARCHAR2(20),
    role INT,
    POName VARCHAR2(30),
    POPhoneNumber CHAR(10),
    SIN INT,
    FOREIGN KEY (POPhoneNumber, POName) REFERENCES PetOwner(POPhoneNumber, POName) ON DELETE CASCADE,
    FOREIGN KEY (SIN) REFERENCES SERVICEPROVIDER(SIN) ON DELETE CASCADE
);

CREATE TABLE WorkSchedule(
    SIN INT,
    day VARCHAR(10),
    startTime VARCHAR(5),
    endTime VARCHAR(5),
    PRIMARY KEY (SIN, day) /* FOREIGN KEY (SIN) REFERENCES ServiceProvider(SIN) ON DELETE CASCADE */
);

GRANT SELECT ON PetOwner TO PUBLIC;
GRANT SELECT ON OwnsPet TO PUBLIC;
GRANT SELECT ON Reservation TO PUBLIC;
/* GRANT ALL ON Branch1 TO role_administrators */