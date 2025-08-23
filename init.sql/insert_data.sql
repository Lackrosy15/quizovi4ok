BEGIN TRANSACTION;

INSERT INTO users (id, login, name, password, role)
VALUES ('36690fd4-1bfa-4189-b29a-e93a0c4dd222', 'testUser@bk.ru', 'Тест', '$2a$10$mIe502NHzycQFJ6LxHv/quAGUm9MEOg3OzfzjXQnniCijgfA51QTe' 'USER'),
       ('747a2d9d-3bc0-4fea-ba01-05e5c79e5616', 'rodionova.viktoriya.99@bk.ru', 'Виктория', '$2a$10$NzX5pNj31JVZoO8WsfvJpedJCDc1Bw95OqwFsCyvkcEhJEQUP.xD.' 'ADMIN');

INSERT INTO quiz_categories (id, name)
VALUES ('1', 'Программирование'),
       ('2', 'Маркетинг'),
       ('3', 'Литература');
COMMIT;