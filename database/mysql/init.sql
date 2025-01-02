CREATE DATABASE IF NOT EXISTS AWESOME_TOILET;

-- GENERAL TABLES --

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.User
(
    uid         INT AUTO_INCREMENT PRIMARY KEY,
    account     VARCHAR(20) UNIQUE NOT NULL,
    name        VARCHAR(20)        NOT NULL,
    email       VARCHAR(50)        NULL,
    create_time TIMESTAMP,
    update_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.User_Auth
(
    uid      INT PRIMARY KEY,
    account  VARCHAR(20) NOT NULL,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Login_History
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    ip_address VARCHAR(20) NOT NULL,
    location   VARCHAR(50) NULL,
    platform   VARCHAR(50) NULL,
    time       TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Role
(
    id INT AUTO_INCREMENT PRIMARY KEY
);
-- BUSINESS TABLES --

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Toilet
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50),
    location    VARCHAR(20),
    create_time TIMESTAMP,
    update_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Alarm
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    type        INT,
    toilet      INT NOT NULL,
    device      INT NOT NULL,
    message     VARCHAR(200),
    create_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Device
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(20),
    description VARCHAR(200),
    status      INT,
    create_time TIMESTAMP,
    update_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Device_Binding
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    device INT,
    toilet INT
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Traffic
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    toilet         INT NOT NULL,
    pit            INT NOT NULL,
    TotalVisitors  INT,
    MaleVisitors   INT,
    FemaleVisitors INT,
    Timestamp      TIMESTAMP
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Toilet_Area
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    toilet INT         NOT NULL,
    name   VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Pit
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    toilet INT NOT NULL,
    status INT
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Comment
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    toilet      INT NOT NULL,
    area        INT,
    suggestion  VARCHAR(200),
    type        INT,
    create_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Message
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user INT,
    message VARCHAR(500),
    create_time TIMESTAMP
);