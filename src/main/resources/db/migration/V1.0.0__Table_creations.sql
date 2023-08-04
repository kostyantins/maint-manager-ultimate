drop sequence if exists maint_id_seq;
create sequence maint_id_seq
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;

drop sequence if exists maint_comments_id_seq;
create sequence maint_comments_id_seq
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;

create table if not exists maint
(
    "id"   int8                     not null default nextval('maint_id_seq'::regclass),
    "maint_identifier" varchar(50) collate "pg_catalog"."default"  not null,
    "capability_id"    varchar(50) collate "pg_catalog"."default"  not null,
    "created_date"     timestamp  not null default current_timestamp,
    "due_date"         timestamp  not null,
    "sovle_priority_id" varchar(50) collate "pg_catalog"."default"  not null,
    "estimate"         int8,
    "fix_version"      varchar(50) collate "pg_catalog"."default",
    "client"           varchar(50) collate "pg_catalog"."default"  not null,

    primary key (id),
    constraint maint_constraint unique (maint_identifier)
);

create table if not exists maint_comments
(
    "id"               int8          not null default nextval('maint_comments_id_seq'::regclass),
    "comment_text"     varchar(255)  collate "pg_catalog"."default"  not null,
    "created_date"     timestamp     not null default current_timestamp,
    "maint_id"         int8          not null,

    primary key (id),
    foreign key (maint_id) references maint (id) on delete no action on update no action
);

create table if not exists maint_profile
(
    "maint_id"                      int8          not null,
    "pbr_planed_date"         timestamp,
    "pbr_real_date"           timestamp,
    "pbr_conclusion"          varchar(255) collate "pg_catalog"."default",
    "definition_of_ready"     varchar(255) collate "pg_catalog"."default",
    "definition_of_done"      varchar(255) collate "pg_catalog"."default",

    primary key (maint_id),
    foreign key (maint_id) references maint (id) on delete no action on update no action
);

-- todo investigate what purpose of it
-- alter sequence maint_id_seq owned by maint.id;
-- alter sequence maint_comments_id_seq owned by maint_comments.id;

-- The example of 'view table'
-- create or replace view working_place_state (working_place_id, state) as
-- select id,
--        case
--            when (select count(*) from users where users.assigned_working_place_id = wp.id) = 1
--                then 'ASSIGNED'
--            when (select count(*) from blocking where blocking.working_place_id = wp.id) = 1
--                then 'BLOCKED'
--            when (select count(*) from reservation where working_place_id = wp.id) = 1
--                then 'RESERVED'
--            else 'FREE'
--            end
-- from working_place wp;