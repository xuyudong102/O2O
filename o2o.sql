drop database o2o;
create database o2o;
use o2o;
/* 创建tb_area表*/
/**/
create table tb_area(
	area_id int not null auto_increment ,
	area_name varchar(200) not null,
	priority int not null default 0,
	create_time datetime default null,
	last_edit_time datetime default null,
	constraint tb_area_area_id_pk primary key(area_id),
	constraint tb_area_area_name_uk unique(area_name)
)engine=innodb auto_increment=1 default charset=utf8;
