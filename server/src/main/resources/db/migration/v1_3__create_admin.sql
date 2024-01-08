insert into countries(name, phone_preffix) values ('Ukraine', 380);

insert into users(username, password, email, name, surname,
                  phone, account_non_expired, account_non_locked,
                  credentials_non_expired, enabled, role, country_id)
    values ('admin', 'admin', 'c17b88979738f9f53cf437a8f7d12e5e4590c4ea1ec4d8caf1a2c78ba833d5b5', 'Rostylsav', 'Horban',
                          0984601237, true, true, true, true, 'ROLE_ADMIN', 1)
