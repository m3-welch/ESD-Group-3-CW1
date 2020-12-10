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
    userid int references Users(id)
);

CREATE TABLE BookingSlots (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    employeeid int references Employees(id),
    clientid int references Clients(id),
    date Date,
    time Time
);

CREATE TABLE Operations (
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS identity (start with 1, increment by 1),
    employeeid int references Employees(id),
    clientid int references Clients(id),
    date Date,
    time Time,
    charge Real,
    slot int,
    isnhs Boolean references Clients(isnhs)
);
