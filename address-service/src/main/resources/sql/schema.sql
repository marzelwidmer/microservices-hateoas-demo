-- CUSTOMERS
CREATE TABLE IF NOT EXISTS Address
(
    id              VARCHAR(60) DEFAULT RANDOM_UUID() PRIMARY KEY ,
    street_name     VARCHAR NOT NULL,
    street_nr       VARCHAR NOT NULL
);
