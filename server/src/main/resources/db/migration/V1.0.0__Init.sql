create table attributes (
	id integer identity primary key,
	name varchar(255),
	type varchar(255)
);

create table tickets (
	id integer identity primary key,
	number varchar(64),
	comment varchar(1024),
	tracked boolean
);

create table ticket_attributes (
	id integer identity primary key,
	ticket_id integer,
	attribute_id integer,
	value varchar(255),
	foreign key (ticket_id) references tickets(id),
	foreign key (attribute_id) references attributes(id)
);

create table dashboards (
	id integer identity primary key,
	name varchar(255),
	query varchar(512)
);

create table dashboard_columns (
	id integer identity primary key,
	dashboard_id integer,
	attribute_id integer,
	foreign key (dashboard_id) references dashboards(id),
	foreign key (attribute_id) references attributes(id)
);