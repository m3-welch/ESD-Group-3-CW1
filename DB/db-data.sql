/* Insert Users */
INSERT INTO Users (username, password, firstname, lastname, email, address) VALUES ('meaydin', 'aydinme', 'Mehmet', 'Aydin', 'meaydin@test.com', 'Mehmets Address, London, NW4 0BH');
INSERT INTO Users (username, password, firstname, lastname, email, address) VALUES ('eaydin', '12345me', 'Emin', 'Aydin', 'eaying@test.com', 'Emiin''s Address, Bristol, BS16');
INSERT INTO Users (username, password, firstname, lastname, email, address) VALUES ('caidan', '5432@10', 'Charly', 'Aidan', 'caidan@test.com', '14 King Street, Aberdeen, AB24 1BR');
INSERT INTO Users (username, password, firstname, lastname, email, address) VALUES ('princehassan', 'prince_passwd', 'Prince', 'Hassan', 'princehassan@test.com', 'Non-UK street, Non-UK Town, Non_UK');
INSERT INTO Users (username, password, firstname, lastname, email, address) VALUES ('admin', 'admin_passwd', 'admin', 'admin', 'admin@test.com', 'admin');

/* Insert Clients */
INSERT INTO Clients (userid, type) VALUES ((SELECT id FROM Users WHERE username = 'caidan'), 'NHS');
INSERT INTO Clients (userid, type) VALUES ((SELECT id FROM Users WHERE username = 'princehassan'), 'private');

/* Insert Employees */
INSERT INTO Employees (userid) VALUES ((SELECT id FROM Users WHERE username = 'meaydin'));
INSERT INTO Employees (userid) VALUES ((SELECT id FROM Users WHERE username = 'eaydin'));

/* Insert Roles */
INSERT INTO Roles (role) VALUES ('doctor');
INSERT INTO Roles (role) VALUES ('nurse');
INSERT INTO Roles (role) VALUES ('client');
INSERT INTO Roles (role) VALUES ('admin');

/* Insert UserRoles */
INSERT INTO UserRoles (roleid, userid) VALUES ((SELECT id FROM Roles WHERE role = 'doctor'), (SELECT id FROM Users WHERE username = 'meaydin'));
INSERT INTO UserRoles (roleid, userid) VALUES ((SELECT id FROM Roles WHERE role = 'nurse'), (SELECT id FROM Users WHERE username = 'eaydin'));
INSERT INTO UserRoles (roleid, userid) VALUES ((SELECT id FROM Roles WHERE role = 'client'), (SELECT id FROM Users WHERE username = 'caidan'));
INSERT INTO UserRoles (roleid, userid) VALUES ((SELECT id FROM Roles WHERE role = 'client'), (SELECT id FROM Users WHERE username = 'princehassan'));
INSERT INTO UserRoles (roleid, userid) VALUES ((SELECT id FROM Roles WHERE role = 'admin'), (SELECT id FROM Users WHERE username = 'admin'));

