CREATE TABLE question(
    id bigint auto_increment not null ,
    title varchar(50) not null ,
    description varchar(2000) not null ,
    gmt_create bigint not null ,
    gmt_modified bigint not null ,
    creator bigint not null ,
    comment_count int default 0 not null ,
    view_count int default 0 not null ,
    like_count int default 0 not null ,
    tag varchar(256) not null ,
    account_id varchar(100) not null ,
    primary key (id)
)engine=innodb default charset=utf8;