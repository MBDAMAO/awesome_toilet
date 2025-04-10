-- 删除数据库（如果存在）
DROP DATABASE IF EXISTS AWESOME_TOILET;

-- 创建数据库
CREATE DATABASE IF NOT EXISTS AWESOME_TOILET;

-- 使用数据库
USE AWESOME_TOILET;

-- GENERAL TABLES --

-- 用户表
CREATE TABLE IF NOT EXISTS User
(
    uid         INT AUTO_INCREMENT PRIMARY KEY,
    account     VARCHAR(20) UNIQUE NOT NULL,
    name        VARCHAR(20)        NOT NULL,
    email       VARCHAR(50)        NULL,
    create_time TIMESTAMP,
    update_time TIMESTAMP
);

-- 用户认证表
CREATE TABLE IF NOT EXISTS User_Auth
(
    uid      INT PRIMARY KEY,
    account  VARCHAR(20) NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- 登录历史表
CREATE TABLE IF NOT EXISTS Login_History
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    ip_address VARCHAR(20) NOT NULL,
    location   VARCHAR(50) NULL,
    platform   VARCHAR(50) NULL,
    time       TIMESTAMP   NOT NULL
);

-- 角色表
CREATE TABLE IF NOT EXISTS Role
(
    id INT AUTO_INCREMENT PRIMARY KEY
);

-- BUSINESS TABLES --

-- 厕所表
CREATE TABLE IF NOT EXISTS Toilet
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    owner       INT,
    name        VARCHAR(50),
    location    VARCHAR(20),
    design_map  TEXT,
    status      INT,
    pits        INT,
    devices     INT,
    description VARCHAR(200),
    create_time TIMESTAMP,
    update_time TIMESTAMP
);

-- 报警表
CREATE TABLE IF NOT EXISTS Alarm
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    type        INT,
    toilet      INT NOT NULL,
    device      INT NULL,
    message     VARCHAR(200),
    create_time TIMESTAMP
);

-- 设备表
CREATE TABLE IF NOT EXISTS Device
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

-- 环境数据表
DROP TABLE IF EXISTS Environment_Data;
CREATE TABLE IF NOT EXISTS Environment_Data
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user        BIGINT COMMENT '关联用户ID',
    toilet      BIGINT COMMENT '关联厕所ID',
    temperature FLOAT COMMENT '温度(℃)',
    humidity    FLOAT COMMENT '湿度(%RH)',
    ammonia     FLOAT COMMENT '氨气浓度(ppm)',
    pm2d5       FLOAT COMMENT 'PM2.5浓度(μg/m³)',
    pm10        FLOAT COMMENT 'PM10浓度(μg/m³)',
    pressure    FLOAT COMMENT '气压(hPa)',
    h2s         FLOAT COMMENT '硫化氢浓度(ppm)',
    co2         FLOAT COMMENT '二氧化碳浓度(ppm)',
    tvoc        FLOAT COMMENT '总挥发性有机物(ppb)',
    timestamp   DATETIME COMMENT '数据记录时间',

    -- 索引
    INDEX idx_toilet (toilet),
    INDEX idx_timestamp (timestamp)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='环境监测数据表';

-- 环境阈值表
CREATE TABLE IF NOT EXISTS env_threshold
(
    id             BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    toilet         BIGINT(20)   NOT NULL COMMENT '厕所ID',
    parameter_name VARCHAR(255) NOT NULL COMMENT '参数名称',
    min_threshold  FLOAT COMMENT '最小阈值',
    max_threshold  FLOAT COMMENT '最大阈值',
    alert_enabled  TINYINT(1)   NOT NULL COMMENT '是否启用告警（0：否，1：是）',
    alert_message  VARCHAR(255) COMMENT '告警信息',
    created_at     DATETIME(6)  NOT NULL COMMENT '创建时间',
    updated_at     DATETIME(6)  NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='环境阈值表';

-- 用纸量统计表（按小时）
CREATE TABLE IF NOT EXISTS Paper_usage_hourly
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user        INT NOT NULL,
    toilet      INT NOT NULL,
    pit         INT NULL,
    device      INT NULL,
    paper_usage FLOAT,
    Timestamp   TIMESTAMP
);

-- 用纸量统计表（按天）
CREATE TABLE IF NOT EXISTS Paper_usage_daily
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user        INT NOT NULL,
    toilet      INT NOT NULL,
    pit         INT NULL,
    device      INT NULL,
    paper_usage FLOAT,
    Timestamp   TIMESTAMP
);

-- 用纸量统计表（按周）
CREATE TABLE IF NOT EXISTS Paper_usage_weekly
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user        INT NOT NULL,
    toilet      INT NOT NULL,
    pit         INT NULL,
    device      INT NULL,
    paper_usage FLOAT,
    Timestamp   TIMESTAMP
);

-- 用水量统计表（按小时）
CREATE TABLE IF NOT EXISTS Water_usage_hourly
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user        INT NOT NULL,
    toilet      INT NOT NULL,
    pit         INT NULL,
    device      INT NULL,
    water_usage FLOAT,
    Timestamp   TIMESTAMP
);

-- 用水量统计表（按天）
CREATE TABLE IF NOT EXISTS Water_usage_daily
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user        INT NOT NULL,
    toilet      INT NOT NULL,
    pit         INT NULL,
    device      INT NULL,
    water_usage FLOAT,
    Timestamp   TIMESTAMP
);

-- 用水量统计表（按周）
CREATE TABLE IF NOT EXISTS Water_usage_weekly
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user        INT NOT NULL,
    toilet      INT NOT NULL,
    pit         INT NULL,
    device      INT NULL,
    water_usage FLOAT,
    Timestamp   TIMESTAMP
);

-- 用电量统计表（按小时）
CREATE TABLE IF NOT EXISTS Electricity_usage_hourly
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    user              INT NOT NULL,
    toilet            INT NOT NULL,
    pit               INT NULL,
    device            INT NULL,
    electricity_usage FLOAT,
    Timestamp         TIMESTAMP
);

-- 用电量统计表（按天）
CREATE TABLE IF NOT EXISTS Electricity_usage_daily
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    user              INT NOT NULL,
    toilet            INT NOT NULL,
    pit               INT NULL,
    device            INT NULL,
    electricity_usage FLOAT,
    Timestamp         TIMESTAMP
);

-- 用电量统计表（按周）
CREATE TABLE IF NOT EXISTS Electricity_usage_weekly
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    user              INT NOT NULL,
    toilet            INT NOT NULL,
    pit               INT NULL,
    device            INT NULL,
    electricity_usage FLOAT,
    Timestamp         TIMESTAMP
);

-- 流量统计表
CREATE TABLE IF NOT EXISTS Traffic
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user           INT NOT NULL,
    toilet         INT NOT NULL,
    pit            INT NULL,
    device         INT NULL,
    TotalVisitors  INT,
    MaleVisitors   INT,
    FemaleVisitors INT,
    Timestamp      TIMESTAMP
);

-- 流量统计表（按分钟）
CREATE TABLE IF NOT EXISTS Traffic_minutely_stats
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user           INT NOT NULL,
    toilet         INT NOT NULL,
    pit            INT NULL,
    device         INT NULL,
    MaleVisitors   INT,
    FemaleVisitors INT,
    Timestamp      TIMESTAMP
);

-- 流量统计表（按小时）
CREATE TABLE IF NOT EXISTS Traffic_hourly_stats
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user           INT NOT NULL,
    toilet         INT NOT NULL,
    pit            INT NULL,
    device         INT NULL,
    MaleVisitors   INT,
    FemaleVisitors INT,
    Timestamp      TIMESTAMP
);

-- 流量统计表（按天）
CREATE TABLE IF NOT EXISTS Traffic_daily_stats
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user           INT NOT NULL,
    toilet         INT NOT NULL,
    pit            INT NULL,
    device         INT NULL,
    MaleVisitors   INT,
    FemaleVisitors INT,
    Timestamp      TIMESTAMP
);

-- 流量统计表（按周）
CREATE TABLE IF NOT EXISTS Traffic_weekly_stats
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user           INT NOT NULL,
    toilet         INT NOT NULL,
    pit            INT NULL,
    device         INT NULL,
    MaleVisitors   INT,
    FemaleVisitors INT,
    Timestamp      TIMESTAMP
);

-- 流量统计表（按月）
CREATE TABLE IF NOT EXISTS Traffic_monthly_stats
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    user           INT NOT NULL,
    toilet         INT NOT NULL,
    pit            INT NULL,
    device         INT NULL,
    MaleVisitors   INT,
    FemaleVisitors INT,
    Timestamp      TIMESTAMP
);

-- 厕所区域表
CREATE TABLE IF NOT EXISTS Toilet_Area
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    toilet      INT         NOT NULL,
    name        VARCHAR(20) NOT NULL,
    description VARCHAR(200),
    create_time TIMESTAMP
);

-- 坑位表
CREATE TABLE IF NOT EXISTS Pit
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    toilet      INT NOT NULL,
    type        INT,
    coordinates VARCHAR(20),
    description VARCHAR(200),
    status      INT,
    create_time TIMESTAMP
);

-- 评论表
CREATE TABLE IF NOT EXISTS Comment
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    toilet      INT NOT NULL,
    area        INT,
    suggestion  VARCHAR(200),
    type        INT,
    create_time TIMESTAMP
);

-- 消息表
CREATE TABLE IF NOT EXISTS Message
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user        INT,
    message     VARCHAR(500),
    is_read     BOOLEAN,
    create_time TIMESTAMP
);