/* Insert Users */
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('tfirst', 'firstpassword', 'Tony', 'First', 'first@email.com', 'Firsts house, Bristol, BS7 0PS', 'doctor');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('jbest', 'jessthebest', 'Jess', 'Best', 'thebestnures@email.co.uk', 'Bests house, Bristol, BS16 1ZG', 'nurse');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('rsmith', '5432@10', 'Rob', 'Smith', 'robbiebobby@email.com', '14 King Street, Aberdeen, AB24 1BR', 'client');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('lizzo', 'unknownpassword', 'Liz', 'Brown', 'lizbrown@email.co.uk', 'Somewhere street, Smalltown', 'client');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('griffo', 'ahafunnyman', 'Peter', 'Hesitant', 'prettyhesitant@email.co.uk', 'Spooner Street, Quahog, Rhode Island', 'client');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('mhesi', 'stupupmeg', 'Meg', 'Hesitant', 'megmail@email.co.uk', 'Spooner Street, Quahog, Rhode Island', 'client');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('boneman', 'spinyman101', 'Greg', 'Bones', 'bones4days@email.co.uk', 'Spooky street, Transylvania', 'client');
INSERT INTO Users (username, password, firstname, lastname, email, address, role) VALUES ('admin', 'admin_passwd', 'admin', 'admin', 'admin@test.com', 'admin', 'admin');

/* Insert Clients */
INSERT INTO Clients (userid, isnhs) VALUES ((SELECT id FROM Users WHERE username = 'rsmith'), TRUE);
INSERT INTO Clients (userid, isnhs) VALUES ((SELECT id FROM Users WHERE username = 'lizzo'), FALSE);
INSERT INTO Clients (userid, isnhs) VALUES ((SELECT id FROM Users WHERE username = 'griffo'), TRUE);
INSERT INTO Clients (userid, isnhs) VALUES ((SELECT id FROM Users WHERE username = 'mhesi'), TRUE);
INSERT INTO Clients (userid, isnhs) VALUES ((SELECT id FROM Users WHERE username = 'boneman'), FALSE);

/* Insert Employees */
INSERT INTO Employees (userid, isfulltime) VALUES ((SELECT id FROM Users WHERE username = 'tfirst'), TRUE);
INSERT INTO Employees (userid, isfulltime) VALUES ((SELECT id FROM Users WHERE username = 'jbest'), FALSE);

/* Insert Operations */
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, is_paid, is_surgery, description) VALUES ((SELECT id FROM Employees WHERE id = 1), (SELECT id FROM Clients WHERE id = 1), '2021-01-01', '12:00:00', '13:00:00', 59.96, TRUE, TRUE, 'Change bandages');
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, is_paid, is_surgery, description) VALUES ((SELECT id FROM Employees WHERE id = 1), (SELECT id FROM Clients WHERE id = 1), '2021-01-02', '12:00:00', '13:00:00', 59.96, FALSE, TRUE, 'Brain surgery');
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, is_paid, is_surgery, description) VALUES ((SELECT id FROM Employees WHERE id = 2), (SELECT id FROM Clients WHERE id = 2), '2021-01-02', '12:00:00', '12:20:00', 9.98, FALSE, FALSE, 'Limb amputation');
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, is_paid, is_surgery, description) VALUES ((SELECT id FROM Employees WHERE id = 1), (SELECT id FROM Clients WHERE id = 1), '2020-12-16', '12:00:00', '12:30:00', 17.97, TRUE, FALSE, 'Physiotherapy');
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, is_paid, is_surgery, description) VALUES ((SELECT id FROM Employees WHERE id = 1), (SELECT id FROM Clients WHERE id = 1), '2020-12-14', '12:00:00', '13:00:00', 35.94, FALSE, FALSE, 'Remove cast');
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, is_paid, is_surgery, description) VALUES ((SELECT id FROM Employees WHERE id = 2), (SELECT id FROM Clients WHERE id = 2), '2021-12-02', '12:00:00', '13:00:00', 53.94, FALSE, TRUE, 'Coronavirus test');
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, is_paid, is_surgery, description) VALUES ((SELECT id FROM Employees WHERE id = 1), (SELECT id FROM Clients WHERE id = 1), '2021-08-05', '12:00:00', '12:30:00', 20.00, FALSE, TRUE, 'Colonoscopy');
INSERT INTO Operations (employeeid, clientid, date, starttime, endtime, charge, is_paid, is_surgery, description) VALUES ((SELECT id FROM Employees WHERE id = 2), (SELECT id FROM Clients WHERE id = 1), '2021-09-05', '09:00:00', '10:00:00', 10.00, FALSE, FALSE, 'Checkup');

/* Create Prescriptions */
INSERT INTO Prescriptions (clientid, employeeid, drug_name, dosage, is_repeat, date_start, date_end) VALUES ((SELECT id FROM Clients WHERE id = 1), (SELECT id FROM Employees WHERE id = 1), 'Codeine', '50mg daily', FALSE, '2020-12-15', '2021-01-15');
INSERT INTO Prescriptions (clientid, employeeid, drug_name, dosage, is_repeat, date_start, date_end) VALUES ((SELECT id FROM Clients WHERE id = 1), (SELECT id FROM Employees WHERE id = 2), 'Tramadol', '50mg daily', TRUE, '2020-12-10', '2021-01-10');
INSERT INTO Prescriptions (clientid, employeeid, drug_name, dosage, is_repeat, date_start, date_end) VALUES ((SELECT id FROM Clients WHERE id = 2), (SELECT id FROM Employees WHERE id = 1), 'Lactulose', '15mls daily', FALSE, '2020-12-15', '2021-01-15');
INSERT INTO Prescriptions (clientid, employeeid, drug_name, dosage, is_repeat, date_start, date_end) VALUES ((SELECT id FROM Clients WHERE id = 2), (SELECT id FROM Employees WHERE id = 1), 'Cyclizine', '50mg weekly', TRUE, '2020-11-17', '2020-12-17');

/* Insert Prices */
INSERT INTO Prices (appointmenttype, employeetype, priceperslot) VALUES ('surgery', 'doctor', 9.99);
INSERT INTO Prices (appointmenttype, employeetype, priceperslot) VALUES ('surgery', 'nurse', 8.99);
INSERT INTO Prices (appointmenttype, employeetype, priceperslot) VALUES ('consultation', 'doctor', 5.99);
INSERT INTO Prices (appointmenttype, employeetype, priceperslot) VALUES ('consultation', 'nurse', 4.99);

/* Insert referral */
INSERT INTO Referrals (employeeid, clientid, name, address) VALUES (1, (SELECT id FROM Clients WHERE id = 1), 'RUH Cardiac Ward', 'B45, RUH Bath, Combe Park, Bath, BA1 3NG');

/* Insert dummy data into ApiCredentials */
INSERT INTO ApiCredentials (googlemapsapisecret) VALUES ('ReplaceMeWithAValidGoogleMapsAPISecret');

/* Insert PendingPrescriptionExtensions */
INSERT INTO PendingPrescriptionExtensions (prescriptionid, newEndDate) VALUES (2, '2022-01-10');
INSERT INTO PendingPrescriptionExtensions (prescriptionid, newEndDate) VALUES (4, '2021-01-17');