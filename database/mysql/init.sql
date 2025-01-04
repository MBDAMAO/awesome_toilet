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
    owner       INT,
    name        VARCHAR(50),
    location    VARCHAR(20),
    status      INT,
    description VARCHAR(200),
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
    owner       INT,
    name        VARCHAR(20),
    description VARCHAR(200),
    toilet      INT,
    status      INT,
    create_time TIMESTAMP,
    update_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Environment_Data
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    toilet            INT,
    temperature       FLOAT,
    humidity          FLOAT,
    paper_usage       INT,
    electricity_usage FLOAT,
    free_1            varchar(20),
    free_2            varchar(20),
    free_3            varchar(20),
    free_4            varchar(20),
    free_5            varchar(20),
    free_6            varchar(20),
    free_7            varchar(20),
    free_8            varchar(20),
    free_9            varchar(20),
    free_10           varchar(20),
    free_11           varchar(20),
    free_12           varchar(20),
    free_13           varchar(20),
    free_14           varchar(20),
    free_15           varchar(20),
    create_time       TIMESTAMP
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Traffic
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    toilet         INT NOT NULL,
    pit            INT NOT NULL,
    device         INT NOT NULL,
    TotalVisitors  INT,
    MaleVisitors   INT,
    FemaleVisitors INT,
    Timestamp      TIMESTAMP
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Toilet_Area
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    toilet      INT         NOT NULL,
    name        VARCHAR(20) NOT NULL,
    description VARCHAR(200),
    create_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS AWESOME_TOILET.Pit
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    toilet      INT NOT NULL,
    type        INT,
    coordinates VARCHAR(20),
    description VARCHAR(200),
    status      INT,
    create_time TIMESTAMP
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
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user        INT,
    message     VARCHAR(500),
    is_read     BOOLEAN,
    create_time TIMESTAMP
);