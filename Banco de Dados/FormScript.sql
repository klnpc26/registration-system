create database formulario;

use formulario;

create table estados(
id_estado int auto_increment primary key,
nome_estado varchar(50) not null,
uf_estado varchar(2) not null
);

create table cidade(
id_cidade int auto_increment not null primary key,
nome_cidade varchar(50) not null,
id_estado int,
constraint fk_estado foreign key (id_estado) references estados(id_estado)
);

create table  bairro(
id_bairro int auto_increment not null primary key,
nome_bairro varchar(50) not null,
id_cidade int,
constraint fk_cidade foreign key (id_cidade) references cidade(id_cidade)
);