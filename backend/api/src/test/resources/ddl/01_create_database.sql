DROP TABLE IF EXISTS `test`.`executed_file`;
CREATE TABLE `test`.`executed_file` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `file` text COLLATE utf8mb4_bin NOT NULL,
 `drops` int(11) NOT NULL,
 `size` bigint(20) NOT NULL,
 `recorded_at` datetime NOT NULL,
 `channel` text COLLATE utf8mb4_bin NOT NULL,
 `title` text COLLATE utf8mb4_bin NOT NULL,
 `channelName` text COLLATE utf8mb4_bin NOT NULL,
 `duration` double NOT NULL,
 `status` varchar(20) NOT NULL,
 PRIMARY KEY (`id`),
 UNIQUE KEY `file` (`file`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

DROP TABLE IF EXISTS `test`.`splitted_file`;
CREATE TABLE `test`.`splitted_file` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `executed_file_id` bigint(20) NOT NULL,
 `file` text COLLATE utf8mb4_bin NOT NULL,
 `size` bigint(20) NOT NULL,
 `duration` double NOT NULL,
 `status` varchar(20) NOT NULL,
 PRIMARY KEY (`id`),
 UNIQUE KEY `file` (`file`) USING HASH,
 KEY `executed_file_id` (`executed_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

DROP TABLE IF EXISTS `test`.`created_file`;
CREATE TABLE `test`.`created_file` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
 `splitted_file_id` bigint(20) NOT NULL,
 `file` text COLLATE utf8mb4_bin NOT NULL,
 `size` bigint(20) NOT NULL,
 `mime` varchar(100),
 `encoding` varchar(100),
 `status` varchar(20) NOT NULL,
 PRIMARY KEY (`id`),
 UNIQUE KEY `file` (`file`) USING HASH,
 KEY `splitted_file_id` (`splitted_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

DROP TABLE IF EXISTS `test`.`program`;
CREATE TABLE `test`.`program` (
 `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
 `name` varchar(255) NOT NULL,
 `executed_file_id` bigint(20) NOT NULL,
 `status` varchar(20) NOT NULL,
 UNIQUE KEY `name` (`name`) USING HASH,
 KEY `executed_file_id` (`executed_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
INSERT INTO `test`.`program`(id, name, executed_file_id, status) VALUES (1, 'program1', 1, 'COMPLETED');
INSERT INTO `test`.`program`(id, name, executed_file_id, status) VALUES (2, 'program2', 2, 'ERROR');
INSERT INTO `test`.`program`(id, name, executed_file_id, status) VALUES (3, 'program3', 3, 'REGISTERED');
INSERT INTO `test`.`program`(id, name, executed_file_id, status) VALUES (4, 'program4', 4, 'COMPLETED');
INSERT INTO `test`.`program`(id, name, executed_file_id, status) VALUES (5, 'program5', 5, 'COMPLETED');
INSERT INTO `test`.`program`(id, name, executed_file_id, status) VALUES (6, 'program6', 6, 'COMPLETED');
INSERT INTO `test`.`program`(id, name, executed_file_id, status) VALUES (7, 'program7', 7, 'COMPLETED');
INSERT INTO `test`.`program`(id, name, executed_file_id, status) VALUES (8, 'program8', 8, 'COMPLETED');
INSERT INTO `test`.`program`(id, name, executed_file_id, status) VALUES (9, 'program9', 9, 'COMPLETED');
INSERT INTO `test`.`program`(id, name, executed_file_id, status) VALUES (10, 'program10', 10, 'COMPLETED');
INSERT INTO `test`.`program`(id, name, executed_file_id, status) VALUES (11, 'program11', 11, 'COMPLETED');
