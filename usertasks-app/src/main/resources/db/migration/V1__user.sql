CREATE TABLE IF NOT EXISTS `users`
(
    `id`                  bigint(20) not null AUTO_INCREMENT,
    `user_name`           varchar(50) not null,
    `first_name`          varchar(50) not null,
    `last_name`           varchar(50) not null,
    primary key (id),
    unique key uk_uname(user_name)
);