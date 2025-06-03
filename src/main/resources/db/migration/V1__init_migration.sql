CREATE TABLE authors(
    id INT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    patronymic VARCHAR(100),
    date_of_birth DATE,
    date_of_death DATE,
    biography VARCHAR(1000) NOT NULL
);

CREATE TABLE books(
    id INT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    author_id INT NOT NULL,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors(id)
        ON UPDATE CASCADE
);