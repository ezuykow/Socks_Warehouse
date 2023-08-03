-- liquibase formatted sql

-- changeset ezuykow:1
CREATE TABLE socks
(
    id          SERIAL PRIMARY KEY,
    color       VARCHAR(50) NOT NULL,
    operation   VARCHAR(50) NOT NULL,
    cotton_part INT         NOT NULL
);
