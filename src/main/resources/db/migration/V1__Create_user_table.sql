CREATE TABLE user (
    id int auto_increment not null ,
    account_id varchar(100) not null ,
    name varchar(50) not null ,
    token char(36) not null ,
    gmt_create bigint not null ,
    gmt_modified bigint not null ,
    avatar_url varchar(256) not null ,
    primary key (id)
)engine=innodb default charset=utf8;