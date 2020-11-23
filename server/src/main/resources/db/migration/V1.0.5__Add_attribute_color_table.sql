create table attribute_colors (
	id integer unsigned auto_increment primary key,
	attribute_id integer unsigned,
	color varchar(255),
	'condition' varchar(255),
	foreign key (attribute_id) references attributes(id)
);