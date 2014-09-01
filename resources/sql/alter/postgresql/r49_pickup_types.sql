UPDATE pickup_type SET pickup_type_description = 'Regularly scheduled pickup/dropoff' WHERE pickup_type_id = 0;

UPDATE pickup_type SET pickup_type_description = 'No pickup/dropoff available' WHERE pickup_type_id = 1;
