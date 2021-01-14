CREATE TABLE Users (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    username varchar(64) NOT NULL UNIQUE,
    password varchar(64) NOT NULL,
    firstname varchar(64),
    lastname varchar(64),
    email varchar(64),
    address varchar(64),
    role varchar(64)
);

CREATE TABLE Clients (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    userid int references Users(id),
    isnhs Boolean
);

CREATE TABLE Employees (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    userid int references Users(id),
    isfulltime Boolean
);

CREATE TABLE BookingSlots (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    employeeid int references Employees(id),
    clientid int references Clients(id),
    issurgery Boolean,
    date Date,
    starttime Time,
    endtime Time,
    slot Real,
    hasbeenpaid Boolean
);

CREATE TABLE Operations (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    employeeid int references Employees(id),
    clientid int references Clients(id),
    date Date,
    starttime Time,
    endtime Time,
    charge Real,
    slot int,
    is_paid Boolean
);

CREATE TABLE Prescriptions (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    clientid int references Clients(id),
    employeeid int references Employees(id),
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
    clientid int REFERENCES Clients(id),
    name varchar(64),
    address varchar(64)
);

CREATE TABLE ApiCredentials (
    googlemapsapisecret varchar(128)
);