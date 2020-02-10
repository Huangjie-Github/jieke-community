create table comment(
    id bigint auto_increment not null ,
    parent_id bigint not null comment '父类id',
    type int not null comment '父类类型',
    commentator bigint not null comment '评论人id',
    gmt_create bigint not null comment '创建时间',
    gmt_modified bigint not null comment '更新时间',
    like_count bigint not null comment '点赞数',
    content varchar(2000) not null comment '评论内容',
    primary key (id)
)engine=innodb default charset=utf8;