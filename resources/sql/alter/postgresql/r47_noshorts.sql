ALTER TABLE trip ALTER COLUMN trip_bikes_allowed TYPE integer;
ALTER TABLE route ALTER COLUMN route_bikes_allowed TYPE integer;
ALTER TABLE bike_option ALTER COLUMN bike_option_id TYPE integer;

ALTER TABLE fare ALTER COLUMN payment_method TYPE integer;
ALTER TABLE payment_method ALTER COLUMN payment_method_id TYPE integer;

ALTER TABLE fare ALTER COLUMN transfers TYPE integer;
ALTER TABLE transfer_limit ALTER COLUMN transfer_limit_id TYPE integer;