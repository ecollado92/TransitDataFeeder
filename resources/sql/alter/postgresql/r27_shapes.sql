ALTER TABLE shape ADD COLUMN date_last_modified timestamp without time zone;

ALTER TABLE shape ADD COLUMN user_last_modified integer;

ALTER TABLE shape ADD FOREIGN KEY(user_last_modified) REFERENCES user(user_id);

-- --------------------------------------------------------
--
-- Table structure for table `shape_segments_assoc`
--
CREATE TABLE shape_segment_map (
  shape_id integer NOT NULL,
  shape_segment_id integer NOT NULL,
  segment_sequence integer NOT NULL DEFAULT 0,
  PRIMARY KEY (shape_id, shape_segment_id, segment_sequence),
  FOREIGN KEY (shape_id) REFERENCES shape(shape_id),
  FOREIGN KEY (shape_segment_id) REFERENCES shape_segment(shape_segment_id)
);

ALTER TABLE shape_point ALTER COLUMN shape_pt_sequence TYPE INT;

ALTER TABLE shape_point ADD COLUMN date_last_modified timestamp without time zone;

ALTER TABLE shape_point ADD COLUMN user_last_modified integer;

ALTER TABLE shape_point ADD FOREIGN KEY(user_last_modified) REFERENCES user(user_id);

CREATE TABLE shape_segment_trip_map (
  shape_segment_id integer NOT NULL,
  trip_id integer NOT NULL,
  PRIMARY KEY (shape_segment_id, trip_id),
  FOREIGN KEY (shape_segment_id) REFERENCES shape_segment(shape_segment_id),
  FOREIGN KEY (trip_id) REFERENCES trip(trip_id)
);

INSERT INTO shape_segment_trip_map (shape_segment_id, trip_id)
SELECT shape_segment_id, trip_id FROM shape_segment_triproute_assoc WHERE trip_id IS NOT NULL;

CREATE TABLE shape_segment_route_map (
  shape_segment_id integer NOT NULL,
  route_id integer NOT NULL,
  PRIMARY KEY (shape_segment_id, route_id),
  FOREIGN KEY (shape_segment_id) REFERENCES shape_segment(shape_segment_id),
  FOREIGN KEY (route_id) REFERENCES route(route_id)
);

INSERT INTO shape_segment_route_map (shape_segment_id, route_id)
SELECT shape_segment_id, route_id FROM shape_segment_triproute_assoc WHERE route_id IS NOT NULL;

ALTER TABLE shape_segment ADD COLUMN shape_id INT;

ALTER TABLE shape_segment ADD FOREIGN KEY(shape_id) REFERENCES shape(shape_id);
