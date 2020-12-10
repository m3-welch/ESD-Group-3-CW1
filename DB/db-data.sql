/* Insert Users */
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('meaydin', 'aydinme', 'Mehmet', 'Aydin', 'meaydin@test.com', 'Mehmets Address, London, NW4 0BH', 'doctor');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('eaydin', '12345me', 'Emin', 'Aydin', 'eaying@test.com', 'Emiin''s Address, Bristol, BS16', 'nurse');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('caidan', '5432@10', 'Charly', 'Aidan', 'caidan@test.com', '14 King Street, Aberdeen, AB24 1BR', 'client');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('princehassan', 'prince_passwd', 'Prince', 'Hassan', 'princehassan@test.com', 'Non-UK street, Non-UK Town, Non_UK', 'client');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('admin', 'admin_passwd', 'admin', 'admin', 'admin@test.com', 'admin', 'admin');

/* Insert Clients */
INSERT INTO Clients (userid, type) VALUES ((SELECT id FROM Users WHERE username = 'caidan'), 'NHS');
INSERT INTO Clients (userid, type) VALUES ((SELECT id FROM Users WHERE username = 'princehassan'), 'private');

/* Insert Employees */
INSERT INTO Employees (userid) VALUES ((SELECT id FROM Users WHERE username = 'meaydin'));
INSERT INTO Employees (userid) VALUES ((SELECT id FROM Users WHERE username = 'eaydin'));