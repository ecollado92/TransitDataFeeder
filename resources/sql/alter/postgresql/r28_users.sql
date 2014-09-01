ALTER TABLE user ADD COLUMN enabled boolean NOT NULL DEFAULT false;

UPDATE user SET enabled = true WHERE read_only = false;

ALTER TABLE user DROP COLUMN read_only;
