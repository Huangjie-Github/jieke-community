create table notification
(
	id bigint auto_increment,
	notifier bigint not null comment '发起通知的人',
	receiver bigint not null comment '接收通知的人',
	outerId bigint not null comment '回复类型ID',
	type int null comment '通知的类型',
	gmt_create bigint null comment '创建时间',
	status int default 0 null comment '状态',
	primary key (id)
)engine = innodb default charset = utf8;
