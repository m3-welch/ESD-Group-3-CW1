/* Insert Users */
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('meaydin', 'aydinme', 'Mehmet', 'Aydin', 'meaydin@test.com', 'Mehmets Address, London, NW4 0BH', 'doctor');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('eaydin', '12345me', 'Emin', 'Aydin', 'eaying@test.com', 'Emiin''s Address, Bristol, BS16', 'nurse');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('caidan', '5432@10', 'Charly', 'Aidan', 'caidan@test.com', '14 King Street, Aberdeen, AB24 1BR', 'client');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('princehassan', 'prince_passwd', 'Prince', 'Hassan', 'princehassan@test.com', 'Non-UK street, Non-UK Town, Non_UK', 'client');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('admin', 'admin_passwd', 'admin', 'admin', 'admin@test.com', 'admin', 'admin');

/* Insert Clients */
INSERT INTO Clients (userid, isnhs) VALUES ((SELECT id FROM Users WHERE username = 'caidan'), TRUE);
INSERT INTO Clients (userid, isnhs) VALUES ((SELECT id FROM Users WHERE username = 'princehassan'), FALSE);

/* Insert Employees */
INSERT INTO Employees (userid, isfulltime) VALUES ((SELECT id FROM Users WHERE username = 'meaydin'), TRUE);
INSERT INTO Employees (userid, isfulltime) VALUES ((SELECT id FROM Users WHERE username = 'eaydin'), FALSE);

/* Insert Operations */
INSERT INTO Operations (employeeid, clientid, date, time, charge, slot) VALUES ((SELECT id FROM Employees WHERE isdr = TRUE), (SELECT id FROM Clients WHERE isnhs = TRUE), '2021-01-01', '12:00:00', 200.00, 1); 
