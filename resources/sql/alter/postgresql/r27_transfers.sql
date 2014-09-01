ALTER TABLE transfer_type ADD COLUMN is_timed BOOLEAN NOT NULL DEFAULT false;

UPDATE transfer_type SET is_timed = true WHERE transfer_type_description = 'Guaranteed timed transfer';

UPDATE transfer_type SET is_timed = true WHERE transfer_type_description = 'Transfer requires minimum time between arrival and departure';

