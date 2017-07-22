create table Spittle (
	id identity,
	message varchar(140) not null,
	created_at timestamp not null,
	latitude double,
	longitude double
);

create table Spitter (
	id identity,
	username varchar(20) unique not null,
	password varchar(20) not null,
	first_name varchar(30) not null,
	last_name varchar(30) not null,
	email varchar(30) not null,
	enabled boolean not null,
	role varchar(20) not null
);

insert into Spitter(username, password, first_name, last_name, email, enabled, role) values
( 'fxkill2000', '123123', 'myfirstName', 'mylastName', 'mail@qq.com', true, 'admin');