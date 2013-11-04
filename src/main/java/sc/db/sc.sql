-- Scand Java Test MVC
DROP SCHEMA IF EXISTS sc;
CREATE SCHEMA sc;

USE sc;

CREATE TABLE users(
    login VARCHAR(40) NOT NULL, 
    password VARCHAR(40) NOT NULL,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    `Language` VARCHAR(2),
    PRIMARY KEY(login)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLR rememberme(
    username VARCHAR(40) NOT NULL,
    token VARCHAR(40) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

REVOKE ALL ON *.* FROM 'netbeans'@'localhost'; 
DROP USER 'netbeans'@'localhost';
CREATE USER 'netbeans'@'localhost' IDENTIFIED BY 'netbeans';
GRANT ALL PRIVILEGES ON sc.* TO 'netbeans'@'localhost';