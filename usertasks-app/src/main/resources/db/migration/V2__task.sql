CREATE TABLE `task`
(
    `id`          bigint(20) not null AUTO_INCREMENT,
    `name`        varchar(50) not null,
    `description` varchar(50) not null,
    `status`      enum('PENDING', 'DONE') default 'PENDING',
    `date_time`   datetime default current_timestamp,
    PRIMARY KEY (id)
);