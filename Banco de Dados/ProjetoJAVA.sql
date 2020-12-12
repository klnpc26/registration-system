create database sistemavideoaula;

use sistemavideoaula;

create table estados(
id_estado int auto_increment primary key not null,
nome_estado varchar(50),
uf_estado varchar(2)
);
create table cidade(
id_cidade int auto_increment primary key not null,
nome_cidade varchar(50),
id_estado int,
constraint fk_estado foreign key (id_estado) references estados(id_estado)
);

create table bairro(
id_bairro int auto_increment primary key not null,
nome_bairro varchar(50),
id_cidade int,
constraint fk_cidade foreign key (id_cidade) references cidade(id_cidade)
);

create table telefone(
id_telefone int auto_increment primary key not null,
num_tel char(15)
);

create table clientes(
id_cliente int auto_increment primary key not null,
nome_cliente varchar(50),
endereco_cliente varchar(50),
rg_cliente varchar(12),
cpf_cliente varchar(12),
id_bairro int,
id_cidade int,
constraint fk_bairro2 foreign key (id_bairro) references bairro(id_bairro),
constraint fk_cidade2 foreign key (id_cidade) references cidade(id_cidade)
);

create table fornecedores(
id_fornecedor int auto_increment primary key not null,
nome_fornecedor varchar(50),
endereco varchar(50),
cnpj_fornecedor varchar(30),
id_cidade int,
id_bairro int,
constraint fk_cidade3 foreign key (id_cidade) references cidade(id_cidade),
constraint fk_bairro3 foreign key (id_bairro) references bairro(id_bairro)
);

create table produto(
id_produto int auto_increment primary key not null,
nome_produto varchar(50),
preco_compra numeric,
preco_venda numeric,
quantidade int,
id_fornecedor int,
constraint fk_fornecedor foreign key (id_fornecedor) references fornecedores(id_fornecedor)
);

create table venda(
id_venda int auto_increment primary key not null,
data_venda date,
valor_venda numeric,
id_cliente  int,
constraint fk_cliente foreign key (id_cliente) references clientes(id_cliente)
);
