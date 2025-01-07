DROP DATABASE IF EXISTS musiclibrarydb;
CREATE DATABASE IF NOT EXISTS musiclibrarydb;

USE musiclibrarydb;

DROP TABLE IF EXISTS users;
CREATE TABLE users(
        userId   INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
        username varchar(50) NOT NULL,
        password varchar(60) NOT NULL,
        firstName varchar(50),
        lastName varchar(50),
        email varchar(100) UNIQUE NOT NULL,
        isAdmin boolean NOT NULL DEFAULT false
        /*PRIMARY KEY(username)*/

);

# --Songs from x Ed Sheeran
INSERT INTO users (username, password, firstName, lastName, email, isAdmin) VALUES ('julie', 'j123', 'Julie', 'Ola', 'julie@Ola.com',TRUE);