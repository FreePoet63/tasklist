insert into users (name, username, password)
values ('John Smith', 'smith190@gmail.com', '$2a$10$ddAw.G41GPWHNPEgeSrl0uGELmjR4880vTXs2aLj2gCr/xUHltfam'),
       ('Jack Jolly', 'jakky@yahoo.com', '$2a$10$LUMx2hbFCVI3rlPG1FXf3ea7nyw7ShIdDPy6EqjveHqy1wHmSL4vK');

insert into tasks (title, description, status, expiration_date)
values ('Buy milk', null, 'TODO', '2024-02-23 12:43:00'),
       ('Do homework', 'Math, History', 'IN_PROGRESS', '2024-03-08 22:30:00'),
       ('Clean computer', null, 'DONE', null),
       ('Call mother', 'Ask about Big Love', 'TODO', '2024-05-10 10:00:00');

insert into users_tasks (task_id, user_id)
values (1, 2),
       (2, 2),
       (3, 2),
       (4, 1);

insert into users_roles (user_id, role)
values (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');