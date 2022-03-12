CREATE TABLE `task`
(
    `id`          bigint(20) not null AUTO_INCREMENT,,
    `name`        varchar(50) not null,
    `description` varchar(50) not null,
    `date_time`   datetime not null,
    PRIMARY KEY (id)
);