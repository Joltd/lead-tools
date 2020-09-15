create table attribute_colors (
	id integer generated by default as identity(start with 1 increment by 1) primary key,
	attribute_id integer,
	color varchar(255),
	condition varchar(255),
	foreign key (attribute_id) references attributes(id)
);