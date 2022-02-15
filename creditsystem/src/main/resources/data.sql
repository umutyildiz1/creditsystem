DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS Credit CASCADE;

-- TABLE CREATİON

CREATE TABLE Customer(
    sid BIGINT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    salary BIGINT DEFAULT 0 NOT NULL,
    phone_number VARCHAR(11) NOT NULL UNIQUE,
    credit_score INT NOT NULL
);

CREATE TABLE Credit(
    sid BIGINT PRIMARY KEY,
    credit_result boolean,
    credit_limit INT
);

-- FOREIGN KEY CONSTRAINTS

ALTER TABLE Credit
ADD CONSTRAINT fk_credit_customer FOREIGN KEY(sid) REFERENCES Customer(sid) ON DELETE CASCADE;

-- FAKE DATA INSERTION

INSERT INTO Customer(sid, customer_name, surname, salary, phone_number,credit_score)
VALUES (11111111111,'Ali','Yılmaz',2500,11111111111,450),
       (22222222222,'Veli','Yıldız',2500,22222222222,500),
       (33333333333,'Ayşe','Öz',5000,33333333333,500),
       (44444444444,'Fatma','Özdemir',7500,44444444444,500),
       (55555555555,'Nergis','Yağcı',7500,55555555555,1001);

