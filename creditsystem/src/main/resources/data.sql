DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS Credit CASCADE;
DROP TABLE IF EXISTS security_user CASCADE;

-- TABLE CREATION

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
    credit_result VARCHAR(50),
    credit_limit INT
);

CREATE TABLE security_user(
    sid BIGINT PRIMARY KEY,
    username VARCHAR(25),
    password VARCHAR(255)
);

-- FOREIGN KEY CONSTRAINTS

ALTER TABLE Credit
ADD CONSTRAINT fk_credit_customer FOREIGN KEY(sid) REFERENCES Customer(sid) ON DELETE CASCADE;

-- FAKE DATA INSERTION

INSERT INTO Customer(sid, customer_name, surname, salary, phone_number,credit_score)
VALUES (11111111111,'Ali','Yılmaz',2500,10101010512,450),
       (22222222222,'Veli','Yıldız',2500,14578632152,500),
       (33333333333,'Ayşe','Öz',5000,14788745214,500),
       (44444444444,'Fatma','Özdemir',7500,47474563251,500),
       (55555555555,'Nergis','Yağcı',7500,98975426541,1001);

