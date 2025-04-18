DROP TABLE IF EXISTS person, person1, person2, person3, person4, "order";

-- PRIMARY KEY - UNIQUE and NOT NULL
CREATE TABLE IF NOT EXISTS person
(
    person_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name  VARCHAR(30)  NOT NULL,
    age   INTEGER CHECK (age >= 0 AND age <= 120) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    address  VARCHAR(30)  NOT NULL
    );

INSERT INTO person (name, age, email, address)
VALUES ('Иван Иванов', 25, 'ivan@example.com', 'Москва');

INSERT INTO person (name, age, email, address)
VALUES ('Анна Петрова', 32, 'anna@example.com', 'Санкт-Петербург');

INSERT INTO person (name, age, email, address)
VALUES ('Сергей Коваленко', 41, 'sergey@example.com', 'Киев');

CREATE TABLE IF NOT EXISTS "order" (
    order_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    person_id BIGINT NOT NULL REFERENCES person(person_id) ON DELETE CASCADE,
    item_name VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS item (
    item_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    item_name VARCHAR(50) UNIQUE NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    description VARCHAR(500)
)

