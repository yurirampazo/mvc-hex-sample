CREATE DATABASE customer_registry;

USE customer_registry;

CREATE TABLE IF NOT EXISTS customer(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    birth_date TIMESTAMP NOT NULL
 );

CREATE TABLE IF NOT EXISTS address(
    id INT AUTO_INCREMENT PRIMARY KEY,
    zip_code VARCHAR(12) NOT NULL,
    address_number VARCHAR(10) DEFAULT 'S/N',
    street_name VARCHAR(255) NOT NULL,
    neighbourhood VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(3) NOT NULL
);


CREATE TABLE customer_address (
    customer_id INT,
    address_id INT,
    PRIMARY KEY (customer_id, address_id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);