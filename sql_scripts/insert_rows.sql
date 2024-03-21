INSERT INTO PetOwner VALUES ('George', '5255 Main Mall Vancouver BC', '7781112222');
INSERT INTO PetOwner VALUES ('Justin Bieber', '2424 Main Mall Vancouver BC', '6047891287');
INSERT INTO PetOwner VALUES ('Taylor Swift', '4600 Cambie St Vancouver BC', '7781236788');
INSERT INTO PetOwner VALUES ('Soulja Boy', '9 Avenue Southwest Calgary AB', '2362319900');
INSERT INTO PetOwner VALUES ('Doja Cat', '13450 104 Ave Surrey BC', '6043218899');
INSERT INTO PetOwner VALUES ('Mariah Carey', '6111 River Rd Richmond BC', '7782139900');

INSERT INTO OwnsPet VALUES (2, 'Buttercup', 1, 'small', 'dog', 'Justin Bieber', '6047891287');
INSERT INTO OwnsPet VALUES (3, 'Olivia Benson', 2, 'small', 'cat', 'Taylor Swift', '7781236788');
INSERT INTO OwnsPet VALUES(4, 'Swag', 5, 'large', 'dog', 'Soulja Boy', '2362319900');
INSERT INTO OwnsPet VALUES(5, 'Kitty', 1, 'large', 'cat', 'Doja Cat', '6043218899');
INSERT INTO OwnsPet VALUES(6, 'Chantelle', 3, 'medium', 'dog', 'Mariah Carey', '7782139900');

INSERT INTO Branch2 VALUES ('Vancouver Kitsilano', '2000 McDonald St', 'Vancouver', 'V6K 3Y2', 'British Columbia');
INSERT INTO Branch1 VALUES ('Vancouver Kitsilano', 'Monday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Vancouver Kitsilano', 'Tuesday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Vancouver Kitsilano', 'Wednesday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Vancouver Kitsilano', 'Thursday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Vancouver Kitsilano', 'Friday', '9AM', '7PM');

INSERT INTO Branch2 VALUES ('Downtown Calgary', '328 Centre St S', 'Calgary', 'T2G 4X6', 'Alberta');
INSERT INTO Branch1 VALUES ('Downtown Calgary', 'Monday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Downtown Calgary', 'Tuesday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Downtown Calgary', 'Wednesday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Downtown Calgary', 'Thursday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Downtown Calgary', 'Friday', '9AM', '7PM');

INSERT INTO Branch2 VALUES ('Richmond Aberdeen', '4151 Hazelbridge Way', 'Richmond', 'V6X 4J7', 'British Columbia');
INSERT INTO Branch1 VALUES ('Richmond Aberdeen', 'Monday', '9AM', '6PM');
INSERT INTO Branch1 VALUES ('Richmond Aberdeen', 'Tuesday', '9AM', '6PM');
INSERT INTO Branch1 VALUES ('Richmond Aberdeen', 'Wednesday', '9AM', '6PM');
INSERT INTO Branch1 VALUES ('Richmond Aberdeen', 'Thursday', '9AM', '6PM');
INSERT INTO Branch1 VALUES ('Richmond Aberdeen', 'Friday', '9AM', '6PM');

INSERT INTO Branch2 VALUES ('Surrey Central', '10153 King George Blvd', 'Surrey', 'V3T 2W1', 'British Columbia');
INSERT INTO Branch1 VALUES ('Surrey Central', 'Monday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Surrey Central', 'Tuesday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Surrey Central', 'Wednesday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Surrey Central', 'Thursday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Surrey Central', 'Friday', '9AM', '7PM');

INSERT INTO Branch2 VALUES ('Country Hills Calgary', '1510 Country Hills Blvd NE', 'Calgary', 'T2G 4X6', 'Alberta');
INSERT INTO Branch1 VALUES ('Country Hills Calgary', 'Monday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Country Hills Calgary', 'Tuesday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Country Hills Calgary', 'Wednesday', '9AM', '7PM');
INSERT INTO Branch1 VALUES ('Country Hills Calgary', 'Thursday', '9AM', '7PM');

INSERT INTO ServiceProvider VALUES (921343111, 'Gertrude', 'Vancouver Kitsilano', 1, 0, 0);
INSERT INTO ServiceProvider VALUES (123456789, 'Jerry', 'Downtown Calgary', 1, 0, 0);
INSERT INTO ServiceProvider VALUES (987654321, 'Tom', 'Richmond Aberdeen', 1, 0, 0);
INSERT INTO ServiceProvider VALUES (882671423, 'Boris', 'Surrey Central', 1, 0, 0);
INSERT INTO ServiceProvider VALUES (111222333, 'Alice', 'Country Hills Calgary', 1, 0, 0);

INSERT INTO Room1 VALUES ('shampoo room', 'medium'); 
INSERT INTO Room2 VALUES ('101', 'shampoo room');
INSERT INTO Room1 VALUES ('haircut room', 'small'); 
INSERT INTO Room2 VALUES ('102', 'haircut room');
INSERT INTO Room1 VALUES ('teeth cleaning room', 'large'); 
INSERT INTO Room2 VALUES ('103', 'teeth cleaning room');
INSERT INTO Room1 VALUES ('nail service room', 'small'); 
INSERT INTO Room2 VALUES ('104', 'nail service room');
INSERT INTO Room1 VALUES ('ear cleaning room', 'medium'); 
INSERT INTO Room2 VALUES ('105', 'ear cleaning room');

INSERT INTO Services VALUES(1, 'Everything', 50.00, 60.00, 40);
INSERT INTO Services VALUES(2, 'Everything', 50.00, 70.00, 80);
INSERT INTO Services VALUES(3, 'Everything', 50.00, 70.00, 80);
INSERT INTO Services VALUES(4, 'Everything', 50.00, 75.00, 100);
INSERT INTO Services VALUES(5, 'Everything', 50.00, 65.00, 60);

INSERT INTO RoomsHasServices VALUES (101, 1);
INSERT INTO RoomsHasServices VALUES (102, 2);
INSERT INTO RoomsHasServices VALUES (103, 3);
INSERT INTO RoomsHasServices VALUES (104, 4);
INSERT INTO RoomsHasServices VALUES (105, 5);

INSERT INTO Services_Inventory1 VALUES('Flea B Gone', 'hairspray');
INSERT INTO Services_Inventory2 VALUES(1, 'low', 'Flea B Gone', 1);
INSERT INTO Services_Inventory1 VALUES('2-in-1 for furry friends', 'shampoo');
INSERT INTO Services_Inventory2 VALUES(2, 'half', '2-in-1 for furry friends', 2);
INSERT INTO Services_Inventory1 VALUES('3-in-1 for furry friends', 'mousse');
INSERT INTO Services_Inventory2 VALUES(3, 'full', '3-in-1 for furry friends', 3);
INSERT INTO Services_Inventory1 VALUES('Shiny Coat Lover', 'shampoo');
INSERT INTO Services_Inventory2 VALUES(4, 'empty', 'Shiny Coat Lover', 4); 
INSERT INTO Services_Inventory1 VALUES('Snazzy Shine', 'conditioner');
INSERT INTO Services_Inventory2 VALUES(5, 'low', 'Snazzy Shine', 5);

INSERT INTO Haircut VALUES(1, 'soft', 1.2);
INSERT INTO Haircut VALUES(2, 'soft', 1);
INSERT INTO Haircut VALUES(3, 'rugged', 3);
INSERT INTO Haircut VALUES(4, 'rugged', 0.8);
INSERT INTO Haircut VALUES(5, 'hairless', 0);

INSERT INTO NailServices VALUES(1, 'low');
INSERT INTO NailServices VALUES(2, 'high');
INSERT INTO NailServices VALUES(3, 'medium');
INSERT INTO NailServices VALUES(4, 'low');
INSERT INTO NailServices VALUES(5, 'high');

INSERT INTO TeethCleaning VALUES(1, 'good');
INSERT INTO TeethCleaning VALUES(2, 'excellent');
INSERT INTO TeethCleaning VALUES(3, 'poor');
INSERT INTO TeethCleaning VALUES(4, 'very poor');
INSERT INTO TeethCleaning VALUES(5, 'poor');
	
INSERT INTO ShampooAndEarCleaning VALUES(1, 'excellent', 'soft');
INSERT INTO ShampooAndEarCleaning VALUES(2, 'good', 'soft');
INSERT INTO ShampooAndEarCleaning VALUES(3, 'good', 'rugged');
INSERT INTO ShampooAndEarCleaning VALUES(4, 'poor', 'rugged');
INSERT INTO ShampooAndEarCleaning VALUES(5, 'poor', 'hairless');

INSERT INTO Reservation VALUES(1, '2023-12-10 14:00:00', '2023-11-27 12:00:00', 1, '7781112222', 'George', 1, 111222333);
INSERT INTO Reservation VALUES(2, '2023-10-31 12:00:00', '2023-10-19 18:05:06', 1, '6047891287', 'Justin Bieber', 1, 921343111);
INSERT INTO Reservation VALUES(3, '2023-10-31 13:00:00', '2023-10-20 09:46:57', 2, '7781236788', 'Taylor Swift', 2, 123456789);
INSERT INTO Reservation VALUES(4, '2023-11-11 15:30:00', '2023-10-25 6:39:54', 3, '2362319900', 'Soulja Boy', 3, 987654321);
INSERT INTO Reservation VALUES(5, '2023-11-12 10:15:00', '2023-11-01 12:00:01', 4, '6043218899', 'Doja Cat', 4, 882671423);
INSERT INTO Reservation VALUES(6, '2024-01-16 14:00:00', '2023-12-25 18:05:06', 5, '7782139900', 'Mariah Carey', 5, 111222333);


INSERT INTO Users VALUES(1, 'admin@cpsc.com', 'admin123', 2, null, null, null);
INSERT INTO Users VALUES(2, 'customer@cpsc.com', 'pass1', 0, 'George', '7781112222', null);
INSERT INTO Users VALUES(3, 'sp@cpsc.com', 'pass2', 1, null, null, 111222333);

INSERT INTO Users VALUES(4, 'JB@cpsc.com', 'jb123', 0, 'Justin Bieber', '6047891287', null);
INSERT INTO Users VALUES(5, 'TS@cpsc.com', 'ts123', 0, 'Taylor Swift', '7781236788', null);
INSERT INTO Users VALUES(6, 'SB@cpsc.com', 'sb123', 0, 'Soulja Boy', '2362319900', null);
INSERT INTO Users VALUES(7, 'DC@cpsc.com', 'dc123', 0, 'Doja Cat', '6043218899', null); 
INSERT INTO Users VALUES(8, 'MC@cpsc.com','mc123', 0, 'Mariah Carey', '7782139900', null); 

INSERT INTO Users VALUES(9, 'Gertrud@cpsc.com', 'ge123', 1, null, null, 921343111);
INSERT INTO Users VALUES(10, 'Jerry@cpsc.com', 'je123', 1, null, null, 123456789);
INSERT INTO Users VALUES(11, 'Tom@cpsc.com', 'to123', 1, null, null, 987654321);
INSERT INTO Users VALUES(12, 'Boris@cpsc.com', 'bo123', 1, null, null, 882671423);
INSERT INTO Users VALUES(13, 'Alice@cpsc.com', 'al123', 1, null, null, 111222333);

INSERT INTO WorkSchedule VALUES(921343111, 'Monday', '10AM', '5PM');
INSERT INTO WorkSchedule VALUES(123456789, 'Tuesday', '9AM', '6PM');
INSERT INTO WorkSchedule VALUES(987654321, 'Wednesday', '10AM', '5PM');
INSERT INTO WorkSchedule VALUES(882671423, 'Thursday', '10AM', '5PM');
INSERT INTO WorkSchedule VALUES(111222333, 'Monday', '9AM', '4PM');
INSERT INTO WorkSchedule VALUES(111222333, 'Tuesday', '9AM', '4PM');
INSERT INTO WorkSchedule VALUES(111222333, 'Sunday', '10AM', '6PM');

COMMIT;