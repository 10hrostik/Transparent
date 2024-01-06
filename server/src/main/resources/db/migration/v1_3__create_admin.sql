insert into countries(name, phone_preffix) values ('Ukraine', 380);

insert into users(username, password, email, name, surname,
                  phone, account_non_expired, account_non_locked,
                  credentials_non_expired, enabled, role, country_id)
    values ('admin', 'admin', 'thehouseofwolves2019@gmail.com', 'Rostylsav', 'Horban',
                          0984601237, true, true, true, true, 'ROLE_ADMIN', 1)
