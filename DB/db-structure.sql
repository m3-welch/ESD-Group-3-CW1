CREATE TABLE Users (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    username varchar(64) NOT NULL UNIQUE,
    password varchar(64) NOT NULL,
    firstname varchar(64),
    lastname varchar(64),
    email varchar(64),
    address varchar(64),
    role varchar(64),
    dob Date
);

CREATE TABLE Clients (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    userid int references Users(id) ON DELETE CASCADE,
    isnhs Boolean
);

CREATE TABLE Employees (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    userid int references Users(id) ON DELETE CASCADE,
    isfulltime Boolean
);

CREATE TABLE Operations (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    employeeid int,
    clientid int,
    date Date,
    starttime Time,
    endtime Time,
    charge Real,
    is_paid Boolean,
    is_surgery Boolean,
    description varchar(64)
);

CREATE TABLE Prescriptions (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    clientid int,
    employeeid int,
    drug_name varchar(64),
    dosage varchar(64),
    is_repeat Boolean,
    date_start Date,
    date_end Date
);

CREATE TABLE Prices (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    appointmenttype varchar(64),
    employeetype varchar(64),
    priceperslot Real
);

CREATE TABLE Referrals (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    employeeid int,
    clientid int,
    name varchar(64),
    address varchar(64)
);

CREATE TABLE ApiCredentials (
    googlemapsapisecret varchar(128)
);


CREATE TABLE SignupApproval (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    username varchar(64) NOT NULL UNIQUE,
    password varchar(64) NOT NULL,
    firstname varchar(64),
    lastname varchar(64),
    email varchar(64),
    address varchar(64),
    role varchar(64),
    dob Date,
    isnhs Boolean,
    isfulltime Boolean
);

CREATE TABLE PendingPrescriptionExtensions (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    prescriptionid int UNIQUE references Prescriptions(id),
    newEndDate date
);
