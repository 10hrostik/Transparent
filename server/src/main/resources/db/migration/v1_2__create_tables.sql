create table countries(
    id bigserial primary key,
    name varchar(64) not null,
    phone_preffix smallint not null
);

create table users(
    id bigserial primary key,
    username varchar(64) not null,
    password varchar(64) not null,
    email varchar(64),
    name varchar(64),
    surname varchar(64),
    phone bigint,
    register_date date default current_date,
    login_date date,
    account_non_expired boolean not null,
    account_non_locked boolean not null,
    credentials_non_expired boolean not null,
    enabled boolean not null,
    role varchar(32) not null,
    countryId bigint references countries(id)
);

create table attachments(
    id bigserial primary key,
    filename varchar(255) not null,
    content_type varchar(255) not null,
    url varchar(255) not null,
    attachment_type varchar(255) not null
);

create table attachments_users(
    id bigserial primary key,
    user_id bigint references users(id),
    attachment_id bigint references attachments(id)
);

create table chats(
    id bigserial primary key,
    created_on date default current_date,
    created_by bigint not null,
    active boolean
);

create table channels(
    id bigserial primary key,
    created_on date default current_date,
    created_by bigint not null,
    active boolean
);

create table groups(
    id bigserial primary key,
    created_on date default current_date,
    created_by bigint not null,
    active boolean
);

create table chats_users(
    id bigserial primary key,
    user_id bigint references users(id),
    chat_id bigint references chats(id)
);

create table channels_users(
    id bigserial primary key,
    user_id bigint references users(id),
    channel_id bigint references channels(id)
);

create table groups_users(
    id bigserial primary key,
    user_id bigint references users(id),
    groups_id bigint references groups(id)
);