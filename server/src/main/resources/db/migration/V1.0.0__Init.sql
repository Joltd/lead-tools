create table attributes (
	id integer unsigned auto_increment primary key,
	name varchar(255),
	type varchar(255)
);

create table tickets (
	id integer unsigned auto_increment primary key,
	number varchar(64),
	comment varchar(1024),
	tracked boolean
);

create table ticket_attributes (
	id integer unsigned auto_increment primary key,
	ticket_id integer unsigned,
	attribute_id integer unsigned,
	value varchar(255),
	foreign key (ticket_id) references tickets(id),
	foreign key (attribute_id) references attributes(id)
);

create table dashboards (
	id integer unsigned auto_increment primary key,
	name varchar(255),
	query varchar(512)
);

create table dashboard_columns (
	id integer unsigned auto_increment primary key,
	dashboard_id integer unsigned,
	attribute_id integer unsigned,
	foreign key (dashboard_id) references dashboards(id),
	foreign key (attribute_id) references attributes(id)
);