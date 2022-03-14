CREATE TABLE `users_task`
(
    `id`          bigint(20) not null AUTO_INCREMENT,
    `user_id`     bigint(20) not null,
    `task_id`     bigint(20) not null,
    PRIMARY KEY (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id)
    REFERENCES users(id),
    CONSTRAINT fk_task FOREIGN KEY (task_id)
    REFERENCES task(id),
    UNIQUE(`user_id`, `task_id`)
);