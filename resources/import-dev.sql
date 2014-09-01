-- SQL statements which are executed at application startup if hibernate.hbm2ddl.auto is 'create' or 'create-drop'

-- admin password is blank
insert into user_role (role_id, role_name, conditional) values (1, 'admin', false);
insert into user_role (role_id, role_name, conditional) values (2, 'member', false);
insert into user_role (role_id, role_name, conditional) values (3, 'guest', true);
insert into user_user_role (user_id, role_id) values (1, 1);
insert into user_role_group (role_id, member_of_role) values (1, 2);
