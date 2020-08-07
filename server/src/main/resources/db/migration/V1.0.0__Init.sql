create table tickets (
	id bigint auto_increment,
	number varchar(64),
	comment varchar(1024),
	tracked boolean
)