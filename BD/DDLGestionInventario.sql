DROP DATABASE IF EXISTS GestionInventarioArq;
CREATE DATABASE GestionInventarioArq;
USE GestionInventarioArq;

CREATE TABLE Usuario (
    cve_usuario     INT AUTO_INCREMENT PRIMARY KEY,
    usuario         VARCHAR(20),
    password        VARCHAR(20),
    token           VARCHAR(100),
    a_paterno       VARCHAR(100),
    a_materno       VARCHAR(100),
    nombre          VARCHAR(100),
    email           VARCHAR(100)
);

