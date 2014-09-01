-- Since testing will be against an HSQLDB, and actual runs will run from PostgreSQL, the date functions are not
-- consistent.  Define a function for date_part.
--DROP FUNCTION date_part;
--CREATE FUNCTION date_part (part varchar(16), t timestamp) RETURNS INT RETURN extract(part from t);

-- SQL statements which are executed at application startup if hibernate.hbm2ddl.auto is 'create' or 'create-drop'
insert into dfuser(user_id, email, pass, first_name, last_name, registration_date, enabled) values (111, 'admin', 'Ss/jICpf9c9GeJj8WKqx1hUClEE=', 'Admin', 'User', CURDATE(), true);
insert into user_role (role_id, role_name, conditional) values (1111, 'admin', false);
insert into user_role (role_id, role_name, conditional) values (2222, 'member', false);
insert into user_role (role_id, role_name, conditional) values (3333, 'guest', true);
insert into user_user_role (user_id, role_id) values (111, 1111);
insert into user_role_group (role_id, member_of_role) values (1111, 2222);

insert into bike_option(bike_option_id, bike_option_description) values(11111, 'test bike option');

insert into payment_method(payment_method_id, payment_method_description) values(1, 'blood');

insert into pickup_type(pickup_type_id, pickup_type_description) values(1, 'one ton');

insert into transfer_limit(transfer_limit_id, transfer_limit_description) values(1, 'unlimited');

insert into transfer_type(transfer_type_id, transfer_type_description, is_timed) values(1, 'test transfer type', false);

insert into calendar_exception_type(calendar_exception_type_id, calendar_exception_type_description) values (1, 'test exception');

insert into calendar_exception_type(calendar_exception_type_id, calendar_exception_type_description) values (2, 'test exception 2');

insert into agency_group(agency_group_id, group_name) values (1, 'test agency group');

insert into agency(agency_id, agency_url, agency_timezone, agency_name, agency_short_name, agency_lang) values (11, 'http://www.google.com','America/Los_Angeles', 'Test Agency', 'ta', 'en');

insert into agency_group_map(agency_group_id, agency_id) values (1, 11);

insert into user_agency_map(agency_id, user_id) values (11, 111);

insert into route_type(route_type_id, route_type_description) values(111111, 'Bus');

insert into route(route_id, route_short_name, route_long_name, route_color, route_text_color, route_bikes_allowed, route_type, agency_id) values(1, 'tr1', 'test route', '#000000', '#FFFFFF', 11111, 111111, 11);

insert into block(block_id, block_label, agency_id) values (1, 'test block', 11);

insert into direction(direction_id, direction_label, agency_id) values(1, 'sideways', 11);

insert into zone(zone_id, zone_name, agency_id) values(1, 'test zone', 11);

--station
insert into stop(stop_id, stop_name, stop_code, stop_lat, stop_lon, location_type, agency_id, zone_id, direction_id) values(1, 'test station 1', 'tstat1', 45.5, -122.4, 1, 11, 1, 1);
insert into stop(stop_id, stop_name, stop_code, stop_lat, stop_lon, location_type, agency_id, zone_id, direction_id) values(2, 'test station 2', 'tstat2', 45.2, -122.3, 1, 11, 1, 1);

--stop
insert into stop(stop_id, stop_name, stop_code, stop_lat, stop_lon, location_type, agency_id, zone_id, direction_id, stop_list_order) values(3, 'test stop 1', 'tstop1', 45.1, -122.7, 0, 11, 1, 1, 0);
insert into stop(stop_id, stop_name, stop_code, stop_lat, stop_lon, location_type, agency_id, zone_id, direction_id, stop_list_order) values(4, 'test stop 2', 'tstop2', 45.4, -122.5, 0, 11, 1, 1, 0);

insert into service_schedule_group(service_schedule_group_id, service_schedule_group_label, agency_id) values(1, 'test schedule', 11);

insert into transfer(transfer_id, from_stop_id, to_stop_id, transfer_type, min_transfer_time, agency_id) values (1, 1, 2, 1, 600, 11);

insert into transfer(transfer_id, from_stop_id, to_stop_id, transfer_type, min_transfer_time, agency_id) values (2, 3, 4, 1, 600, 11);

insert into fare(fare_id, price, currency_type, payment_method, transfers, transfer_duration, agency_id) values(1, 2.5, 'USD', 1, 1, 90, 11);

insert into fare_rule(fare_rule_id, fare_id, route_id, origin_id, agency_id) values (1, 1, 1, 1, 11);

insert into calendar(calendar_id, service_label, service_schedule_group_id, monday, tuesday, wednesday, thursday, friday, saturday, sunday) values (1, 'test calendar', 1, false, true, false, true, false, false, false);

insert into calendar(calendar_id, service_label, service_schedule_group_id, monday, tuesday, wednesday, thursday, friday, saturday, sunday) values (2, 'test calendar 2', 1, false, false, false, false, false, true, true);

insert into calendar_date(calendar_date_id, date, description, exception_type, service_added, service_removed, agency_id) values (1, '2010-07-28', 'test calendar date', 1, 1, 2, 11);

insert into calendar_date_exception(calendar_date_exception_id, calendar_date_id, exception_type, service_exception) values(1, 1, 1, 1);
insert into trip(trip_id, trip_headsign, trip_start_time, trip_bikes_allowed, route_id, service_id, start_time_carryover) values(1, 'test headsign', '10:00:00', 11111, 1, 1, false);

insert into trip(trip_id, trip_headsign, trip_start_time, trip_bikes_allowed, route_id, service_id, start_time_carryover, based_on) values(2, 'test headsign 2', '10:00:00', 11111, 1, 1, false, 1);

insert into stop_time(stop_time_id, arrival_time, departure_time, stop_sequence, pickup_type_id, drop_off_type_id, trip_id, stop_id, agency_id, arrival_time_carryover, departure_time_carryover) values(1, '07:15:00', '07:22:00', 1, 1, 1, 1, 3, 11, false, false);

insert into stop_time(stop_time_id, arrival_time, departure_time, stop_sequence, pickup_type_id, drop_off_type_id, trip_id, stop_id, agency_id, arrival_time_carryover, departure_time_carryover) values(2, '07:23:00', '07:25:00', 2, 1, 1, 1, 4, 11, false, false);
