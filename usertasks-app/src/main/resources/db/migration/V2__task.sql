CREATE TABLE `task`
(
    `id`          bigint(20) not null AUTO_INCREMENT,
    `name`        varchar(50) not null,
    `description` varchar(50) not null,
    `date_time`   datetime default current_timestamp ON update current_timestamp,
    PRIMARY KEY (id)
);