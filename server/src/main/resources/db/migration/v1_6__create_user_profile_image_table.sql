alter table attachments
    add column created_on date not null,
    add column created_by bigint not null;

create table user_profile_images(
    id bigserial primary key,
    filename varchar(255) not null,
    content_type varchar(255) not null,
    url varchar(255) not null,
    attachment_type varchar(255) not null,
    created_on date not null,
    main boolean default false,
    created_by bigint references users(id)
);