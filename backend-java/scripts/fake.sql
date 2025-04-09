-- 用户表（User）
INSERT INTO AWESOME_TOILET.User (account, name, email, create_time, update_time)
VALUES ('user1', '张三', 'zhangsan@example.com', NOW(), NOW()),
       ('user2', '李四', 'lisi@example.com', NOW(), NOW()),
       ('user3', '王五', 'wangwu@example.com', NOW(), NOW()),
       ('user4', '赵六', 'zhaoliu@example.com', NOW(), NOW()),
       ('user5', '孙七', 'sunqi@example.com', NOW(), NOW()),
       ('user6', '周八', 'zhouba@example.com', NOW(), NOW()),
       ('user7', '吴九', 'wujiu@example.com', NOW(), NOW()),
       ('user8', '郑十', 'zhengshi@example.com', NOW(), NOW());

-- 用户认证表（User_Auth）
INSERT INTO AWESOME_TOILET.User_Auth (uid, account, password)
VALUES (1, 'user1', 'password1'),
       (2, 'user2', 'password2'),
       (3, 'user3', 'password3'),
       (4, 'user4', 'password4'),
       (5, 'user5', 'password5'),
       (6, 'user6', 'password6'),
       (7, 'user7', 'password7'),
       (8, 'user8', 'password8');

-- 登录历史表（Login_History）
INSERT INTO AWESOME_TOILET.Login_History (ip_address, location, platform, time)
VALUES ('192.168.1.1', '北京', 'Windows', NOW()),
       ('192.168.1.2', '上海', 'MacOS', NOW() - INTERVAL 1 HOUR),
       ('192.168.1.3', '广州', 'iOS', NOW() - INTERVAL 2 HOUR),
       ('192.168.1.4', '深圳', 'Android', NOW() - INTERVAL 3 HOUR),
       ('192.168.1.5', '杭州', 'Linux', NOW() - INTERVAL 4 HOUR),
       ('192.168.1.6', '成都', 'Windows', NOW() - INTERVAL 5 HOUR),
       ('192.168.1.7', '重庆', 'MacOS', NOW() - INTERVAL 6 HOUR),
       ('192.168.1.8', '武汉', 'iOS', NOW() - INTERVAL 7 HOUR);

-- 角色表（Role）
INSERT INTO AWESOME_TOILET.Role ()
VALUES (),
       (),
       (),
       (),
       (),
       (),
       (),
       ();

-- 厕所表（Toilet）
INSERT INTO AWESOME_TOILET.Toilet (owner, name, location, status, pits,
                                   devices, description, create_time, update_time)
VALUES (1, '公共厕所A', '地点A', 1, 3, 3, '这是公共厕所A的描述', NOW(), NOW()),
       (2, '公共厕所B', '地点B', 1, 3, 3, '这是公共厕所B的描述', NOW(), NOW()),
       (3, '公共厕所C', '地点C', 1, 3, 3, '这是公共厕所C的描述', NOW(), NOW()),
       (4, '公共厕所D', '地点D', 1, 3, 3, '这是公共厕所D的描述', NOW(), NOW()),
       (5, '公共厕所E', '地点E', 1, 3, 3, '这是公共厕所E的描述', NOW(), NOW()),
       (6, '公共厕所F', '地点F', 1, 3, 3, '这是公共厕所F的描述', NOW(), NOW()),
       (7, '公共厕所G', '地点G', 1, 3, 3, '这是公共厕所G的描述', NOW(), NOW()),
       (8, '公共厕所H', '地点H', 1, 3, 3, '这是公共厕所H的描述', NOW(), NOW());

-- 报警表（Alarm）
INSERT INTO AWESOME_TOILET.Alarm (type, toilet, device, message, create_time)
VALUES (1, 1, 1, '设备1报警', NOW()),
       (2, 2, 2, '设备2报警', NOW() - INTERVAL 1 HOUR),
       (3, 3, 3, '设备3报警', NOW() - INTERVAL 2 HOUR),
       (4, 4, 4, '设备4报警', NOW() - INTERVAL 3 HOUR),
       (5, 5, 5, '设备5报警', NOW() - INTERVAL 4 HOUR),
       (6, 6, 6, '设备6报警', NOW() - INTERVAL 5 HOUR),
       (7, 7, 7, '设备7报警', NOW() - INTERVAL 6 HOUR),
       (8, 8, 8, '设备8报警', NOW() - INTERVAL 7 HOUR);

-- 设备表（Device）
INSERT INTO AWESOME_TOILET.Device (owner, name, description, toilet, status, create_time, update_time)
VALUES (1, '设备1', '这是设备1的描述', 1, 1, NOW(), NOW()),
       (2, '设备2', '这是设备2的描述', 2, 1, NOW(), NOW()),
       (3, '设备3', '这是设备3的描述', 3, 1, NOW(), NOW()),
       (4, '设备4', '这是设备4的描述', 4, 1, NOW(), NOW()),
       (5, '设备5', '这是设备5的描述', 5, 1, NOW(), NOW()),
       (6, '设备6', '这是设备6的描述', 6, 1, NOW(), NOW()),
       (7, '设备7', '这是设备7的描述', 7, 1, NOW(), NOW()),
       (8, '设备8', '这是设备8的描述', 8, 1, NOW(), NOW());

-- 环境数据表（Environment_Data）
# INSERT INTO AWESOME_TOILET.Environment_Data (toilet, temperature, humidity, paper_usage, electricity_usage, create_time)
# VALUES (1, 25.5, 60.0, 100, 5.5, NOW()),
#        (2, 26.0, 65.0, 120, 6.0, NOW() - INTERVAL 1 HOUR),
#        (3, 24.5, 55.0, 90, 5.0, NOW() - INTERVAL 2 HOUR),
#        (4, 27.0, 70.0, 110, 7.0, NOW() - INTERVAL 3 HOUR),
#        (5, 23.5, 50.0, 80, 4.5, NOW() - INTERVAL 4 HOUR),
#        (6, 28.0, 75.0, 130, 8.0, NOW() - INTERVAL 5 HOUR),
#        (7, 22.5, 45.0, 70, 4.0, NOW() - INTERVAL 6 HOUR),
#        (8, 29.0, 80.0, 140, 9.0, NOW() - INTERVAL 7 HOUR);

-- 流量表（Traffic）
# INSERT INTO AWESOME_TOILET.Traffic (toilet, pit, device, TotalVisitors, MaleVisitors, FemaleVisitors, Timestamp)
# VALUES (1, 1, 1, 100, 60, 40, NOW()),
#        (2, 2, 2, 120, 70, 50, NOW() - INTERVAL 1 HOUR),
#        (3, 3, 3, 90, 50, 40, NOW() - INTERVAL 2 HOUR),
#        (4, 4, 4, 130, 80, 50, NOW() - INTERVAL 3 HOUR),
#        (5, 5, 5, 110, 65, 45, NOW() - INTERVAL 4 HOUR),
#        (6, 6, 6, 140, 90, 50, NOW() - INTERVAL 5 HOUR),
#        (7, 7, 7, 105, 60, 45, NOW() - INTERVAL 6 HOUR),
#        (8, 8, 8, 150, 95, 55, NOW() - INTERVAL 7 HOUR);

-- 厕所区域表（Toilet_Area）
INSERT INTO AWESOME_TOILET.Toilet_Area (toilet, name, description, create_time)
VALUES (1, '区域A', '这是区域A的描述', NOW()),
       (2, '区域B', '这是区域B的描述', NOW()),
       (3, '区域C', '这是区域C的描述', NOW()),
       (4, '区域D', '这是区域D的描述', NOW()),
       (5, '区域E', '这是区域E的描述', NOW()),
       (6, '区域F', '这是区域F的描述', NOW()),
       (7, '区域G', '这是区域G的描述', NOW()),
       (8, '区域H', '这是区域H的描述', NOW());

-- 坑位表（Pit）
INSERT INTO AWESOME_TOILET.Pit (toilet, type, coordinates, description, status, create_time)
VALUES (1, 1, '坐标1', '这是坑位1的描述', 1, NOW()),
       (2, 2, '坐标2', '这是坑位2的描述', 1, NOW()),
       (3, 3, '坐标3', '这是坑位3的描述', 1, NOW()),
       (4, 4, '坐标4', '这是坑位4的描述', 1, NOW()),
       (5, 5, '坐标5', '这是坑位5的描述', 1, NOW()),
       (6, 6, '坐标6', '这是坑位6的描述', 1, NOW()),
       (7, 7, '坐标7', '这是坑位7的描述', 1, NOW()),
       (8, 8, '坐标8', '这是坑位8的描述', 1, NOW());

-- 评论表（Comment）
INSERT INTO AWESOME_TOILET.Comment (toilet, area, suggestion, type, create_time)
VALUES (1, 1, '这是评论1的建议', 1, NOW()),
       (2, 2, '这是评论2的建议', 2, NOW() - INTERVAL 1 HOUR),
       (3, 3, '这是评论3的建议', 3, NOW() - INTERVAL 2 HOUR),
       (4, 4, '这是评论4的建议', 4, NOW() - INTERVAL 3 HOUR),
       (5, 5, '这是评论5的建议', 5, NOW() - INTERVAL 4 HOUR),
       (6, 6, '这是评论6的建议', 6, NOW() - INTERVAL 5 HOUR),
       (7, 7, '这是评论7的建议', 7, NOW() - INTERVAL 6 HOUR),
       (8, 8, '这是评论8的建议', 8, NOW() - INTERVAL 7 HOUR);

-- 消息表（Message）
INSERT INTO AWESOME_TOILET.Message (user, message, is_read, create_time)
VALUES (1, '这是消息1的内容', FALSE, NOW()),
       (2, '这是消息2的内容', TRUE, NOW() - INTERVAL 1 HOUR),
       (3, '这是消息3的内容', FALSE, NOW() - INTERVAL 2 HOUR),
       (4, '这是消息4的内容', TRUE, NOW() - INTERVAL 3 HOUR),
       (5, '这是消息5的内容', FALSE, NOW() - INTERVAL 4 HOUR),
       (6, '这是消息6的内容', TRUE, NOW() - INTERVAL 5 HOUR),
       (7, '这是消息7的内容', FALSE, NOW() - INTERVAL 6 HOUR),
       (8, '这是消息8的内容', TRUE, NOW() - INTERVAL 7 HOUR);