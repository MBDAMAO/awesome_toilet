-- 创建环境监测数据表
CREATE DATABASE AWESOME_TOILET;

CREATE TABLE AWESOME_TOILET.EnvironmentData (
    EnvironmentDataID INT AUTO_INCREMENT PRIMARY KEY,
    Temperature DECIMAL(5, 2),
    Humidity DECIMAL(5, 2),
    VOCLevels DECIMAL(5, 2),
    PM25Levels DECIMAL(5, 2),
    Timestamp DATETIME
);

-- 创建厕位状态数据表
CREATE TABLE AWESOME_TOILET.ToiletStatus (
    ToiletID INT AUTO_INCREMENT PRIMARY KEY,
    Occupied BOOLEAN,
    Timestamp DATETIME
);

-- 创建客流量数据表
CREATE TABLE AWESOME_TOILET.TrafficData (
    TrafficID INT AUTO_INCREMENT PRIMARY KEY,
    TotalVisitors INT,
    MaleVisitors INT,
    FemaleVisitors INT,
    Timestamp DATETIME
);

-- 创建紧急报警数据表
CREATE TABLE AWESOME_TOILET.AlarmData (
    AlarmID INT AUTO_INCREMENT PRIMARY KEY,
    ToiletID INT,
    Timestamp DATETIME,
    FOREIGN KEY (ToiletID) REFERENCES ToiletStatus (ToiletID)
);

-- 创建评价数据表
CREATE TABLE AWESOME_TOILET.Reviews (
    ReviewID INT AUTO_INCREMENT PRIMARY KEY,
    ToiletID INT,
    Rating INT,
    Comment TEXT,
    Timestamp DATETIME,
    FOREIGN KEY (ToiletID) REFERENCES ToiletStatus (ToiletID)
);