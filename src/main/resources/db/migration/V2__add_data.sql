INSERT INTO authors (id, first_name, last_name, patronymic, date_of_birth, date_of_death, biography)
VALUES (
    1,
    'Достоевский',
    'Федор',
    'Михайлович',
    '1821-11-11',
    '1881-02-09',
    'Великий русский писатель.'
);

INSERT INTO authors (id, first_name, last_name, patronymic, date_of_birth, date_of_death, biography)
VALUES (
    2,
    'Тургенев',
    'Иван',
    'Сергеевич',
    '1818-11-09',
    '1883-08-22',
    'Великий русский писатель.'
);

INSERT INTO books (id, title, description, author_id)
VALUES (
    1,
    'Записки охотника',
    'Цикл рассказов Ивана Сергеевича Тургенева, состоящий из 25 произведений.',
    2
);

INSERT INTO books (id, title, description, author_id)
VALUES (
    2,
    'Муму',
    'Рассказ Ивана Сергеевича Тургенева.',
    2
);

INSERT INTO books (id, title, description, author_id)
VALUES (
    3,
    'Отцы и дети',
    'Роман Ивана Сергеевича Тургенева.',
    2
);

INSERT INTO books (id, title, description, author_id)
VALUES (
    4,
    'Преступление и наказание',
    'Роман Федора Михайловича Достоевского. Одно из самых известных произведений русской литературы.',
    1
);

INSERT INTO books (id, title, description, author_id)
VALUES (
    5,
    'Белые ночи',
    'Повесть Федора Михайловича Достоевского, которая состоит из пяти глав: четыре из них описывают петербургские ночи, а пятая -- утро.',
    1
);

INSERT INTO books (id, title, description, author_id)
VALUES (
    6,
    'Идиот',
    'Роман Федора Михайловича Достоевского.',
    1
);