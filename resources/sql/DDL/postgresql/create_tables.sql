--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: idea; Type: SCHEMA; Schema: -; Owner: ideauser
--

CREATE SCHEMA idea;


ALTER SCHEMA idea OWNER TO ideauser;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = idea, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: address; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE address (
    address_id integer NOT NULL,
    street_line_1 character varying(64) NOT NULL,
    street_line_e character varying(64),
    city character varying(64) NOT NULL,
    state_province_code character varying(3),
    postal_code character varying(10),
    country character varying(64),
    street_line_2 character varying(64)
);


ALTER TABLE idea.address OWNER TO ideauser;

--
-- Name: address_address_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE address_address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.address_address_id_seq OWNER TO ideauser;

--
-- Name: address_address_id_seq; Type: SEQUENCE OWNED BY; Schema: idea; Owner: ideauser
--

ALTER SEQUENCE address_address_id_seq OWNED BY address.address_id;


--
-- Name: agency_agency_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE agency_agency_id_seq
    START WITH 45
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.agency_agency_id_seq OWNER TO ideauser;

--
-- Name: agency; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE agency (
    agency_id integer DEFAULT nextval('agency_agency_id_seq'::regclass) NOT NULL,
    agency_url character varying(100) DEFAULT ''::character varying NOT NULL,
    agency_timezone character varying(45) DEFAULT ''::character varying NOT NULL,
    agency_name character varying(120) NOT NULL,
    agency_short_name character varying(10) DEFAULT ''::character varying NOT NULL,
    agency_phone character varying(25) DEFAULT NULL::character varying,
    agency_fare_url character varying(255),
    date_last_modified timestamp without time zone,
    user_last_modified integer,
    address_id integer,
    agency_lat double precision,
    agency_lon double precision
);


ALTER TABLE idea.agency OWNER TO ideauser;

--
-- Name: agency_group_agency_group_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE agency_group_agency_group_id_seq
    START WITH 36
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.agency_group_agency_group_id_seq OWNER TO ideauser;

--
-- Name: agency_group; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE agency_group (
    agency_group_id integer DEFAULT nextval('agency_group_agency_group_id_seq'::regclass) NOT NULL,
    group_name character varying(40) DEFAULT ''::character varying NOT NULL,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.agency_group OWNER TO ideauser;

--
-- Name: agency_group_map; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE agency_group_map (
    agency_id integer NOT NULL,
    agency_group_id integer NOT NULL
);


ALTER TABLE idea.agency_group_map OWNER TO ideauser;

--
-- Name: bike_option_bike_option_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE bike_option_bike_option_id_seq
    START WITH 3
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.bike_option_bike_option_id_seq OWNER TO ideauser;

--
-- Name: bike_option; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE bike_option (
    bike_option_id smallint DEFAULT nextval('bike_option_bike_option_id_seq'::regclass) NOT NULL,
    bike_option_description character varying(64)
);


ALTER TABLE idea.bike_option OWNER TO ideauser;

--
-- Name: block_block_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE block_block_id_seq
    START WITH 110
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.block_block_id_seq OWNER TO ideauser;

--
-- Name: block; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE block (
    block_id integer DEFAULT nextval('block_block_id_seq'::regclass) NOT NULL,
    block_label character varying(50) DEFAULT ''::character varying NOT NULL,
    agency_id integer NOT NULL,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.block OWNER TO ideauser;

--
-- Name: calendar_calendar_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE calendar_calendar_id_seq
    START WITH 159
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.calendar_calendar_id_seq OWNER TO ideauser;

--
-- Name: calendar; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE calendar (
    calendar_id integer DEFAULT nextval('calendar_calendar_id_seq'::regclass) NOT NULL,
    service_label character varying(50) NOT NULL,
    service_id integer,
    monday boolean DEFAULT false NOT NULL,
    tuesday boolean DEFAULT false NOT NULL,
    wednesday boolean DEFAULT false NOT NULL,
    thursday boolean DEFAULT false NOT NULL,
    friday boolean DEFAULT false NOT NULL,
    saturday boolean DEFAULT false NOT NULL,
    sunday boolean DEFAULT false NOT NULL,
    start_date date,
    end_date date,
    agency_id integer,
    service_schedule_group_id integer,
    serviceid smallint,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.calendar OWNER TO ideauser;

--
-- Name: calendar_date_calendar_date_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE calendar_date_calendar_date_id_seq
    START WITH 242
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.calendar_date_calendar_date_id_seq OWNER TO ideauser;

--
-- Name: calendar_date; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE calendar_date (
    calendar_date_id integer DEFAULT nextval('calendar_date_calendar_date_id_seq'::regclass) NOT NULL,
    date date DEFAULT '1970-01-01'::date NOT NULL,
    description character varying(255),
    exception_type integer,
    service_added integer,
    service_removed integer,
    agency_id integer,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.calendar_date OWNER TO ideauser;

--
-- Name: calendar_date_exception_calendar_date_exception_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE calendar_date_exception_calendar_date_exception_id_seq
    START WITH 935
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.calendar_date_exception_calendar_date_exception_id_seq OWNER TO ideauser;

--
-- Name: calendar_date_exception; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE calendar_date_exception (
    calendar_date_exception_id integer DEFAULT nextval('calendar_date_exception_calendar_date_exception_id_seq'::regclass) NOT NULL,
    calendar_date_id integer NOT NULL,
    exception_type integer NOT NULL,
    service_exception integer,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.calendar_date_exception OWNER TO ideauser;

--
-- Name: calendar_date_service_exceptions; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE calendar_date_service_exceptions (
    calendar_date_exception_id integer NOT NULL,
    agency_id integer NOT NULL,
    calendar_date_id integer NOT NULL,
    exception_type smallint NOT NULL,
    service_exception integer NOT NULL
);


ALTER TABLE idea.calendar_date_service_exceptions OWNER TO ideauser;

--
-- Name: calendar_exception_type_calendar_exception_type_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE calendar_exception_type_calendar_exception_type_id_seq
    START WITH 2
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.calendar_exception_type_calendar_exception_type_id_seq OWNER TO ideauser;

--
-- Name: calendar_exception_type; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE calendar_exception_type (
    calendar_exception_type_id integer DEFAULT nextval('calendar_exception_type_calendar_exception_type_id_seq'::regclass) NOT NULL,
    calendar_exception_type_description character varying(64)
);


ALTER TABLE idea.calendar_exception_type OWNER TO ideauser;

--
-- Name: direction_direction_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE direction_direction_id_seq
    START WITH 140
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.direction_direction_id_seq OWNER TO ideauser;

--
-- Name: direction; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE direction (
    direction_id integer DEFAULT nextval('direction_direction_id_seq'::regclass) NOT NULL,
    direction_label character varying(35) NOT NULL,
    is_inbound boolean,
    agency_id integer NOT NULL,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.direction OWNER TO ideauser;

--
-- Name: fare_fare_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE fare_fare_id_seq
    START WITH 125
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.fare_fare_id_seq OWNER TO ideauser;

--
-- Name: fare; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE fare (
    fare_id integer DEFAULT nextval('fare_fare_id_seq'::regclass) NOT NULL,
    price double precision DEFAULT 0 NOT NULL,
    currency_type character varying(3) DEFAULT ''::character varying NOT NULL,
    payment_method smallint DEFAULT 0 NOT NULL,
    transfers smallint,
    transfer_duration integer,
    agency_id integer,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.fare OWNER TO ideauser;

--
-- Name: fare_attributes; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE fare_attributes (
    fare_id smallint NOT NULL,
    agency_id smallint NOT NULL,
    currency_type character varying(3) NOT NULL,
    payment_method smallint NOT NULL,
    price double precision NOT NULL,
    transfer_duration integer,
    transfers smallint
);


ALTER TABLE idea.fare_attributes OWNER TO ideauser;

--
-- Name: fare_rule_fare_rule_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE fare_rule_fare_rule_id_seq
    START WITH 710
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.fare_rule_fare_rule_id_seq OWNER TO ideauser;

--
-- Name: fare_rule; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE fare_rule (
    fare_rule_id integer DEFAULT nextval('fare_rule_fare_rule_id_seq'::regclass) NOT NULL,
    fare_id integer,
    route_id integer,
    origin_id integer,
    destination_id integer,
    contains_id integer,
    agency_id integer,
    agencyid smallint,
    containsid smallint,
    destinationid smallint,
    originid smallint,
    routeid smallint,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.fare_rule OWNER TO ideauser;

--
-- Name: frequencies; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE frequencies (
    frequency_id smallint NOT NULL,
    trip_id smallint DEFAULT 0::smallint NOT NULL,
    start_time time without time zone DEFAULT '00:00:00'::time without time zone NOT NULL,
    end_time time without time zone DEFAULT '00:00:00'::time without time zone NOT NULL,
    headway_secs integer DEFAULT 0 NOT NULL,
    agency_id smallint DEFAULT 0::smallint NOT NULL,
    exact_times smallint DEFAULT 0::smallint NOT NULL
);


ALTER TABLE idea.frequencies OWNER TO ideauser;

--
-- Name: frequency_frequency_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE frequency_frequency_id_seq
    START WITH 533
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.frequency_frequency_id_seq OWNER TO ideauser;

--
-- Name: frequency; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE frequency (
    frequency_id integer DEFAULT nextval('frequency_frequency_id_seq'::regclass) NOT NULL,
    trip_id integer,
    start_time time without time zone DEFAULT '00:00:00'::time without time zone NOT NULL,
    end_time time without time zone DEFAULT '00:00:00'::time without time zone NOT NULL,
    headway_secs integer DEFAULT 0 NOT NULL,
    agency_id integer,
    exact_times boolean DEFAULT false NOT NULL,
    date_last_modified timestamp without time zone,
    user_last_modified integer,
    start_time_carryover boolean DEFAULT false NOT NULL,
    end_time_carryover boolean DEFAULT false NOT NULL
);


ALTER TABLE idea.frequency OWNER TO ideauser;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.hibernate_sequence OWNER TO ideauser;

--
-- Name: location_type; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE location_type (
    location_type_id integer NOT NULL,
    location_type_description character varying(128) NOT NULL
);


ALTER TABLE idea.location_type OWNER TO ideauser;
INSERT INTO idea.location_type(location_type_id, location_type_description) VALUES(0, 'stop');
INSERT INTO idea.location_type(location_type_id, location_type_description) VALUES(1, 'station');

--
-- Name: location_type_location_type_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE location_type_location_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.location_type_location_type_id_seq OWNER TO ideauser;

--
-- Name: location_type_location_type_id_seq; Type: SEQUENCE OWNED BY; Schema: idea; Owner: ideauser
--

ALTER SEQUENCE location_type_location_type_id_seq OWNED BY location_type.location_type_id;


--
-- Name: payment_method_payment_method_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE payment_method_payment_method_id_seq
    START WITH 2
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.payment_method_payment_method_id_seq OWNER TO ideauser;

--
-- Name: payment_method; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE payment_method (
    payment_method_id smallint DEFAULT nextval('payment_method_payment_method_id_seq'::regclass) NOT NULL,
    payment_method_description character varying(128) NOT NULL
);


ALTER TABLE idea.payment_method OWNER TO ideauser;

--
-- Name: pickup_type_pickup_type_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE pickup_type_pickup_type_id_seq
    START WITH 4
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.pickup_type_pickup_type_id_seq OWNER TO ideauser;

--
-- Name: pickup_type; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE pickup_type (
    pickup_type_id integer DEFAULT nextval('pickup_type_pickup_type_id_seq'::regclass) NOT NULL,
    pickup_type_description character varying(64)
);


ALTER TABLE idea.pickup_type OWNER TO ideauser;

SET search_path = public, pg_catalog;

--
-- Name: route_route_id_seq; Type: SEQUENCE; Schema: public; Owner: ideauser
--

CREATE SEQUENCE route_route_id_seq
    START WITH 240
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.route_route_id_seq OWNER TO ideauser;

SET search_path = idea, pg_catalog;

--
-- Name: route; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE route (
    route_id integer DEFAULT nextval('public.route_route_id_seq'::regclass) NOT NULL,
    route_short_name character varying(15) DEFAULT ''::character varying NOT NULL,
    route_long_name character varying(40) DEFAULT ''::character varying NOT NULL,
    route_description text,
    route_color character varying(7),
    route_text_color character varying(7),
    route_url character varying(255) DEFAULT NULL::character varying,
    route_bikes_allowed smallint DEFAULT 0 NOT NULL,
    route_type integer DEFAULT 3 NOT NULL,
    agency_id integer,
    route_desc character varying(255),
    route_type_id integer,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.route OWNER TO ideauser;

--
-- Name: route_type; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE route_type (
    route_type_id integer NOT NULL,
    route_type_description character varying(24) NOT NULL
);


ALTER TABLE idea.route_type OWNER TO ideauser;

--
-- Name: route_type_route_type_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE route_type_route_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.route_type_route_type_id_seq OWNER TO ideauser;

--
-- Name: route_type_route_type_id_seq; Type: SEQUENCE OWNED BY; Schema: idea; Owner: ideauser
--

ALTER SEQUENCE route_type_route_type_id_seq OWNED BY route_type.route_type_id;


--
-- Name: service_schedule_bound_service_schedule_bound_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE service_schedule_bound_service_schedule_bound_id_seq
    START WITH 153
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.service_schedule_bound_service_schedule_bound_id_seq OWNER TO ideauser;

--
-- Name: service_schedule_bound; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE service_schedule_bound (
    service_schedule_bound_id integer DEFAULT nextval('service_schedule_bound_service_schedule_bound_id_seq'::regclass) NOT NULL,
    service_schedule_group_id integer NOT NULL,
    start_date date NOT NULL,
    end_date date NOT NULL,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.service_schedule_bound OWNER TO ideauser;

--
-- Name: service_schedule_group_service_schedule_group_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE service_schedule_group_service_schedule_group_id_seq
    START WITH 82
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.service_schedule_group_service_schedule_group_id_seq OWNER TO ideauser;

--
-- Name: service_schedule_group; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE service_schedule_group (
    service_schedule_group_id integer DEFAULT nextval('service_schedule_group_service_schedule_group_id_seq'::regclass) NOT NULL,
    service_schedule_group_label character varying(50) NOT NULL,
    agency_id integer NOT NULL,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.service_schedule_group OWNER TO ideauser;

--
-- Name: shape_shape_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE shape_shape_id_seq
    START WITH 8
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.shape_shape_id_seq OWNER TO ideauser;

--
-- Name: shape; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE shape (
    shape_id integer DEFAULT nextval('shape_shape_id_seq'::regclass) NOT NULL,
    shape_description character varying(200) DEFAULT ''::character varying NOT NULL,
    agency_id integer,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.shape OWNER TO ideauser;

--
-- Name: shape_point_shape_point_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE shape_point_shape_point_id_seq
    START WITH 98310
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.shape_point_shape_point_id_seq OWNER TO ideauser;

--
-- Name: shape_point; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE shape_point (
    shape_point_id integer DEFAULT nextval('shape_point_shape_point_id_seq'::regclass) NOT NULL,
    shape_pt_lon double precision DEFAULT 0.000000 NOT NULL,
    shape_pt_lat double precision DEFAULT 0.000000 NOT NULL,
    shape_pt_sequence smallint DEFAULT 0 NOT NULL,
    shape_dist_traveled double precision,
    agency_id integer,
    shape_id integer,
    shape_segment_id integer NOT NULL
);


ALTER TABLE idea.shape_point OWNER TO ideauser;

--
-- Name: shape_segment_shape_segment_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE shape_segment_shape_segment_id_seq
    START WITH 3918
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.shape_segment_shape_segment_id_seq OWNER TO ideauser;

--
-- Name: shape_segment; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE shape_segment (
    shape_segment_id integer DEFAULT nextval('shape_segment_shape_segment_id_seq'::regclass) NOT NULL,
    shape_segment_description character varying(255),
    distance double precision NOT NULL,
    start_coordinate_id integer,
    end_coordinate_id integer,
    shape_segment_desc character varying(255)
);


ALTER TABLE idea.shape_segment OWNER TO ideauser;

--
-- Name: shape_segment_triproute_assoc; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE shape_segment_triproute_assoc (
    shape_segment_triproute_assoc integer NOT NULL,
    shape_segment_id integer,
    route_id integer,
    trip_id integer
);


ALTER TABLE idea.shape_segment_triproute_assoc OWNER TO ideauser;

--
-- Name: shape_segments_assoc; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE shape_segments_assoc (
    shape_segment_assoc_id integer NOT NULL,
    shape_segment_id integer,
    shape_id integer,
    segment_sequence smallint
);


ALTER TABLE idea.shape_segments_assoc OWNER TO ideauser;

--
-- Name: stop_stop_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE stop_stop_id_seq
    START WITH 9588
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.stop_stop_id_seq OWNER TO ideauser;

--
-- Name: stop; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE stop (
    stop_id integer DEFAULT nextval('stop_stop_id_seq'::regclass) NOT NULL,
    stop_name character varying(80) DEFAULT ''::character varying NOT NULL,
    stop_code character varying(18) DEFAULT NULL::character varying,
    stop_desc text NOT NULL,
    stop_comments character varying(200) DEFAULT NULL::character varying,
    stop_lat double precision,
    stop_lon double precision,
    stop_url character varying(255) DEFAULT NULL::character varying,
    stop_list_order integer NOT NULL,
    location_type integer,
    agency_id integer,
    zone_id integer,
    direction_id integer,
    parent_station integer,
    location_type_id integer,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.stop OWNER TO ideauser;

--
-- Name: stop_coordinate_coordinate_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE stop_coordinate_coordinate_id_seq
    START WITH 680
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.stop_coordinate_coordinate_id_seq OWNER TO ideauser;

--
-- Name: stop_coordinate; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE stop_coordinate (
    coordinate_id integer DEFAULT nextval('stop_coordinate_coordinate_id_seq'::regclass) NOT NULL,
    stop_coordinate_group_id integer NOT NULL,
    agency_id integer,
    stop_id integer NOT NULL,
    stop_description text NOT NULL,
    stop_lat double precision DEFAULT 0.000000,
    stop_lon double precision DEFAULT 0.000000
);


ALTER TABLE idea.stop_coordinate OWNER TO ideauser;

--
-- Name: stop_time_stop_time_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE stop_time_stop_time_id_seq
    START WITH 29304
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.stop_time_stop_time_id_seq OWNER TO ideauser;

--
-- Name: stop_time; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE stop_time (
    stop_time_id integer DEFAULT nextval('stop_time_stop_time_id_seq'::regclass) NOT NULL,
    arrival_time time without time zone DEFAULT '00:00:00'::time without time zone,
    departure_time time without time zone DEFAULT '00:00:00'::time without time zone,
    shape_dist_traveled double precision,
    timing_point boolean,
    stop_sequence smallint DEFAULT 0 NOT NULL,
    pickup_type_id integer DEFAULT 0 NOT NULL,
    drop_off_type_id integer DEFAULT 0 NOT NULL,
    agency_id integer,
    trip_id integer,
    stop_id integer,
    drop_off_type integer,
    pickup_type integer,
    date_last_modified timestamp without time zone,
    user_last_modified integer,
    arrival_time_carryover boolean DEFAULT false NOT NULL,
    departure_time_carryover boolean DEFAULT false NOT NULL,
    stopsequence integer
);


ALTER TABLE idea.stop_time OWNER TO ideauser;

--
-- Name: transfer_transfer_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE transfer_transfer_id_seq
    START WITH 77
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.transfer_transfer_id_seq OWNER TO ideauser;

--
-- Name: transfer; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE transfer (
    transfer_id integer DEFAULT nextval('transfer_transfer_id_seq'::regclass) NOT NULL,
    from_stop_id integer NOT NULL,
    to_stop_id integer NOT NULL,
    transfer_type integer NOT NULL,
    min_transfer_time integer,
    agency_id integer NOT NULL,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.transfer OWNER TO ideauser;

--
-- Name: transfer_limit_transfer_limit_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE transfer_limit_transfer_limit_id_seq
    START WITH 3
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.transfer_limit_transfer_limit_id_seq OWNER TO ideauser;

--
-- Name: transfer_limit; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE transfer_limit (
    transfer_limit_id smallint DEFAULT nextval('transfer_limit_transfer_limit_id_seq'::regclass) NOT NULL,
    transfer_limit_description character varying(128) NOT NULL
);


ALTER TABLE idea.transfer_limit OWNER TO ideauser;

--
-- Name: transfer_type_transfer_type_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE transfer_type_transfer_type_id_seq
    START WITH 4
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.transfer_type_transfer_type_id_seq OWNER TO ideauser;

--
-- Name: transfer_type; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE transfer_type (
    transfer_type_id integer DEFAULT nextval('transfer_type_transfer_type_id_seq'::regclass) NOT NULL,
    transfer_type_description character varying(128)
);


ALTER TABLE idea.transfer_type OWNER TO ideauser;

--
-- Name: trip_trip_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE trip_trip_id_seq
    START WITH 3417
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.trip_trip_id_seq OWNER TO ideauser;

--
-- Name: trip; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE trip (
    trip_id integer DEFAULT nextval('trip_trip_id_seq'::regclass) NOT NULL,
    trip_headsign character varying(35),
    trip_start_time time with time zone,
    trip_grid_header character varying(20) DEFAULT '0'::character varying NOT NULL,
    trip_bikes_allowed smallint DEFAULT 0 NOT NULL,
    route_id integer NOT NULL,
    service_id integer,
    block_id integer,
    shape_id integer,
    direction_id integer,
    based_on integer,
    basedon integer,
    shapeid smallint,
    agency_id integer,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.trip OWNER TO ideauser;

--
-- Name: user_user_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE user_user_id_seq
    START WITH 32
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.user_user_id_seq OWNER TO ideauser;

--
-- Name: user; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE "user" (
    user_id integer DEFAULT nextval('user_user_id_seq'::regclass) NOT NULL,
    email character varying(40) DEFAULT ''::character varying NOT NULL,
    pass character varying(40) DEFAULT ''::character varying NOT NULL,
    first_name character varying(15) DEFAULT ''::character varying NOT NULL,
    last_name character varying(30) DEFAULT ''::character varying NOT NULL,
    active character varying(32) DEFAULT NULL::character varying,
    registration_date timestamp without time zone DEFAULT '1970-01-01 00:00:00'::timestamp without time zone NOT NULL,
    read_only boolean DEFAULT true NOT NULL,
    address_id integer
);


ALTER TABLE idea."user" OWNER TO ideauser;

--
-- Name: user_account; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE user_account (
    id bigint NOT NULL,
    enabled boolean NOT NULL,
    password_hash character varying(255),
    username character varying(255) NOT NULL
);


ALTER TABLE idea.user_account OWNER TO ideauser;

--
-- Name: user_account_role; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE user_account_role (
    account_id bigint NOT NULL,
    member_of_role integer NOT NULL
);


ALTER TABLE idea.user_account_role OWNER TO ideauser;

--
-- Name: user_agency_map; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE user_agency_map (
    agency_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE idea.user_agency_map OWNER TO ideauser;

--
-- Name: user_permission; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE user_permission (
    id integer NOT NULL,
    action character varying(255),
    discriminator character varying(255),
    recipient character varying(255),
    target character varying(255)
);


ALTER TABLE idea.user_permission OWNER TO ideauser;

--
-- Name: user_permissions; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE user_permissions (
    id integer NOT NULL,
    action character varying(255),
    discriminator character varying(255),
    recipient character varying(255),
    target character varying(255)
);


ALTER TABLE idea.user_permissions OWNER TO ideauser;

--
-- Name: user_pub_associations; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE user_pub_associations (
    permission_id smallint NOT NULL,
    agency_id smallint,
    user_id smallint DEFAULT 0::smallint NOT NULL
);


ALTER TABLE idea.user_pub_associations OWNER TO ideauser;

--
-- Name: user_role; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE user_role (
    role_id integer NOT NULL,
    role_name character varying(64) NOT NULL,
    conditional boolean DEFAULT false NOT NULL
);


ALTER TABLE idea.user_role OWNER TO ideauser;

--
-- Name: user_role_group; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE user_role_group (
    role_id integer NOT NULL,
    member_of_role integer NOT NULL
);


ALTER TABLE idea.user_role_group OWNER TO ideauser;

--
-- Name: user_role_role_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE user_role_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.user_role_role_id_seq OWNER TO ideauser;

--
-- Name: user_role_role_id_seq; Type: SEQUENCE OWNED BY; Schema: idea; Owner: ideauser
--

ALTER SEQUENCE user_role_role_id_seq OWNED BY user_role.role_id;


--
-- Name: user_user_role; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE user_user_role (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE idea.user_user_role OWNER TO ideauser;

--
-- Name: zone_zone_id_seq; Type: SEQUENCE; Schema: idea; Owner: ideauser
--

CREATE SEQUENCE zone_zone_id_seq
    START WITH 77
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE idea.zone_zone_id_seq OWNER TO ideauser;

--
-- Name: zone; Type: TABLE; Schema: idea; Owner: ideauser; Tablespace: 
--

CREATE TABLE zone (
    zone_id integer DEFAULT nextval('zone_zone_id_seq'::regclass) NOT NULL,
    zone_name character varying(30) DEFAULT NULL::character varying,
    agency_id integer,
    date_last_modified timestamp without time zone,
    user_last_modified integer
);


ALTER TABLE idea.zone OWNER TO ideauser;

--
-- Name: address_id; Type: DEFAULT; Schema: idea; Owner: ideauser
--

ALTER TABLE address ALTER COLUMN address_id SET DEFAULT nextval('address_address_id_seq'::regclass);


--
-- Name: location_type_id; Type: DEFAULT; Schema: idea; Owner: ideauser
--

ALTER TABLE location_type ALTER COLUMN location_type_id SET DEFAULT nextval('location_type_location_type_id_seq'::regclass);


--
-- Name: route_type_id; Type: DEFAULT; Schema: idea; Owner: ideauser
--

ALTER TABLE route_type ALTER COLUMN route_type_id SET DEFAULT nextval('route_type_route_type_id_seq'::regclass);


--
-- Name: role_id; Type: DEFAULT; Schema: idea; Owner: ideauser
--

ALTER TABLE user_role ALTER COLUMN role_id SET DEFAULT nextval('user_role_role_id_seq'::regclass);


--
-- Name: address_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY address
    ADD CONSTRAINT address_pkey PRIMARY KEY (address_id);


--
-- Name: agency_group_map_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY agency_group_map
    ADD CONSTRAINT agency_group_map_pkey PRIMARY KEY (agency_id, agency_group_id);


--
-- Name: agency_group_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY agency_group
    ADD CONSTRAINT agency_group_pkey PRIMARY KEY (agency_group_id);


--
-- Name: agency_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY agency
    ADD CONSTRAINT agency_pkey PRIMARY KEY (agency_id);


--
-- Name: bike_option_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY bike_option
    ADD CONSTRAINT bike_option_pkey PRIMARY KEY (bike_option_id);


--
-- Name: block_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY block
    ADD CONSTRAINT block_pkey PRIMARY KEY (block_id);


--
-- Name: calendar_date_exception_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY calendar_date_exception
    ADD CONSTRAINT calendar_date_exception_pkey PRIMARY KEY (calendar_date_exception_id);


--
-- Name: calendar_date_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY calendar_date
    ADD CONSTRAINT calendar_date_pkey PRIMARY KEY (calendar_date_id);


--
-- Name: calendar_date_service_exceptions_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY calendar_date_service_exceptions
    ADD CONSTRAINT calendar_date_service_exceptions_pkey PRIMARY KEY (calendar_date_exception_id);


--
-- Name: calendar_exception_type_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY calendar_exception_type
    ADD CONSTRAINT calendar_exception_type_pkey PRIMARY KEY (calendar_exception_type_id);


--
-- Name: calendar_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY calendar
    ADD CONSTRAINT calendar_pkey PRIMARY KEY (calendar_id);


--
-- Name: direction_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY direction
    ADD CONSTRAINT direction_pkey PRIMARY KEY (direction_id);


--
-- Name: fare_attributes_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY fare_attributes
    ADD CONSTRAINT fare_attributes_pkey PRIMARY KEY (fare_id);


--
-- Name: fare_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY fare
    ADD CONSTRAINT fare_pkey PRIMARY KEY (fare_id);


--
-- Name: fare_rule_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fare_rule_pkey PRIMARY KEY (fare_rule_id);


--
-- Name: frequencies_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY frequencies
    ADD CONSTRAINT frequencies_pkey PRIMARY KEY (frequency_id);


--
-- Name: frequency_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY frequency
    ADD CONSTRAINT frequency_pkey PRIMARY KEY (frequency_id);


--
-- Name: location_type_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY location_type
    ADD CONSTRAINT location_type_pkey PRIMARY KEY (location_type_id);


--
-- Name: payment_method_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY payment_method
    ADD CONSTRAINT payment_method_pkey PRIMARY KEY (payment_method_id);


--
-- Name: pickup_type_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY pickup_type
    ADD CONSTRAINT pickup_type_pkey PRIMARY KEY (pickup_type_id);


--
-- Name: pk_user_user_role; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY user_user_role
    ADD CONSTRAINT pk_user_user_role PRIMARY KEY (user_id, role_id);


--
-- Name: route_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY route
    ADD CONSTRAINT route_pkey PRIMARY KEY (route_id);


--
-- Name: route_type_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY route_type
    ADD CONSTRAINT route_type_pkey PRIMARY KEY (route_type_id);


--
-- Name: service_schedule_bound_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY service_schedule_bound
    ADD CONSTRAINT service_schedule_bound_pkey PRIMARY KEY (service_schedule_bound_id);


--
-- Name: service_schedule_group_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY service_schedule_group
    ADD CONSTRAINT service_schedule_group_pkey PRIMARY KEY (service_schedule_group_id);


--
-- Name: shape_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY shape
    ADD CONSTRAINT shape_pkey PRIMARY KEY (shape_id);


--
-- Name: shape_point_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY shape_point
    ADD CONSTRAINT shape_point_pkey PRIMARY KEY (shape_point_id);


--
-- Name: shape_segment_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY shape_segment
    ADD CONSTRAINT shape_segment_pkey PRIMARY KEY (shape_segment_id);


--
-- Name: shape_segment_triproute_assoc_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY shape_segment_triproute_assoc
    ADD CONSTRAINT shape_segment_triproute_assoc_pkey PRIMARY KEY (shape_segment_triproute_assoc);


--
-- Name: shape_segments_assoc_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY shape_segments_assoc
    ADD CONSTRAINT shape_segments_assoc_pkey PRIMARY KEY (shape_segment_assoc_id);


--
-- Name: stop_coordinate_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY stop_coordinate
    ADD CONSTRAINT stop_coordinate_pkey PRIMARY KEY (coordinate_id);


--
-- Name: stop_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT stop_pkey PRIMARY KEY (stop_id);


--
-- Name: stop_time_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT stop_time_pkey PRIMARY KEY (stop_time_id);


--
-- Name: transfer_limit_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY transfer_limit
    ADD CONSTRAINT transfer_limit_pkey PRIMARY KEY (transfer_limit_id);


--
-- Name: transfer_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY transfer
    ADD CONSTRAINT transfer_pkey PRIMARY KEY (transfer_id);


--
-- Name: transfer_type_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY transfer_type
    ADD CONSTRAINT transfer_type_pkey PRIMARY KEY (transfer_type_id);


--
-- Name: trip_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT trip_pkey PRIMARY KEY (trip_id);


--
-- Name: uniq_role_group; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY user_role_group
    ADD CONSTRAINT uniq_role_group UNIQUE (role_id, member_of_role);


--
-- Name: user_account_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY user_account
    ADD CONSTRAINT user_account_pkey PRIMARY KEY (id);


--
-- Name: user_account_role_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY user_account_role
    ADD CONSTRAINT user_account_role_pkey PRIMARY KEY (account_id, member_of_role);


--
-- Name: user_account_username_key; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY user_account
    ADD CONSTRAINT user_account_username_key UNIQUE (username);


--
-- Name: user_agency_map_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY user_agency_map
    ADD CONSTRAINT user_agency_map_pkey PRIMARY KEY (agency_id, user_id);


--
-- Name: user_email_key; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_email_key UNIQUE (email);


--
-- Name: user_permission_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY user_permissions
    ADD CONSTRAINT user_permission_pkey PRIMARY KEY (id);


--
-- Name: user_permission_pkey1; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY user_permission
    ADD CONSTRAINT user_permission_pkey1 PRIMARY KEY (id);


--
-- Name: user_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);


--
-- Name: user_pub_associations_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY user_pub_associations
    ADD CONSTRAINT user_pub_associations_pkey PRIMARY KEY (permission_id);


--
-- Name: user_role_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (role_id);


--
-- Name: zone_pkey; Type: CONSTRAINT; Schema: idea; Owner: ideauser; Tablespace: 
--

ALTER TABLE ONLY zone
    ADD CONSTRAINT zone_pkey PRIMARY KEY (zone_id);


--
-- Name: agency_address_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY agency
    ADD CONSTRAINT agency_address_id_fkey FOREIGN KEY (address_id) REFERENCES address(address_id);


--
-- Name: agency_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY agency
    ADD CONSTRAINT agency_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: agency_group_map_agency_group_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY agency_group_map
    ADD CONSTRAINT agency_group_map_agency_group_id_fkey FOREIGN KEY (agency_group_id) REFERENCES agency_group(agency_group_id);


--
-- Name: agency_group_map_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY agency_group_map
    ADD CONSTRAINT agency_group_map_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: block_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY block
    ADD CONSTRAINT block_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: calendar_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar
    ADD CONSTRAINT calendar_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: calendar_date_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date
    ADD CONSTRAINT calendar_date_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: calendar_date_exception_calendar_date_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date_exception
    ADD CONSTRAINT calendar_date_exception_calendar_date_id_fkey FOREIGN KEY (calendar_date_id) REFERENCES calendar_date(calendar_date_id);


--
-- Name: calendar_date_exception_service_exception_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date_exception
    ADD CONSTRAINT calendar_date_exception_service_exception_fkey FOREIGN KEY (service_exception) REFERENCES calendar(calendar_id);


--
-- Name: calendar_date_service_added_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date
    ADD CONSTRAINT calendar_date_service_added_fkey FOREIGN KEY (service_added) REFERENCES calendar(calendar_id);


--
-- Name: calendar_date_service_removed_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date
    ADD CONSTRAINT calendar_date_service_removed_fkey FOREIGN KEY (service_removed) REFERENCES calendar(calendar_id);


--
-- Name: calendar_service_schedule_group_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar
    ADD CONSTRAINT calendar_service_schedule_group_id_fkey FOREIGN KEY (service_schedule_group_id) REFERENCES service_schedule_group(service_schedule_group_id);


--
-- Name: direction_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY direction
    ADD CONSTRAINT direction_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fare_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare
    ADD CONSTRAINT fare_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fare_payment_method_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare
    ADD CONSTRAINT fare_payment_method_fkey FOREIGN KEY (payment_method) REFERENCES payment_method(payment_method_id);


--
-- Name: fare_rule_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fare_rule_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fare_rule_contains_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fare_rule_contains_id_fkey FOREIGN KEY (contains_id) REFERENCES zone(zone_id);


--
-- Name: fare_rule_destination_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fare_rule_destination_id_fkey FOREIGN KEY (destination_id) REFERENCES zone(zone_id);


--
-- Name: fare_rule_fare_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fare_rule_fare_id_fkey FOREIGN KEY (fare_id) REFERENCES fare(fare_id);


--
-- Name: fare_rule_origin_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fare_rule_origin_id_fkey FOREIGN KEY (origin_id) REFERENCES zone(zone_id);


--
-- Name: fare_rule_route_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fare_rule_route_id_fkey FOREIGN KEY (route_id) REFERENCES route(route_id);


--
-- Name: fare_transfers_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare
    ADD CONSTRAINT fare_transfers_fkey FOREIGN KEY (transfers) REFERENCES transfer_limit(transfer_limit_id);


--
-- Name: fk2fd82e3e214f9d; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare
    ADD CONSTRAINT fk2fd82e3e214f9d FOREIGN KEY (transfers) REFERENCES transfer_limit(transfer_limit_id);


--
-- Name: fk2fd82e45a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare
    ADD CONSTRAINT fk2fd82e45a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fk2fd82e856166e6; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare
    ADD CONSTRAINT fk2fd82e856166e6 FOREIGN KEY (payment_method) REFERENCES payment_method(payment_method_id);


--
-- Name: fk2fd82eb22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare
    ADD CONSTRAINT fk2fd82eb22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk36080245a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT fk36080245a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fk3608026b6535dc; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT fk3608026b6535dc FOREIGN KEY (parent_station) REFERENCES stop(stop_id);


--
-- Name: fk3608028b0dbe80; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT fk3608028b0dbe80 FOREIGN KEY (location_type_id) REFERENCES location_type(location_type_id);


--
-- Name: fk360802ae1d52ff; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT fk360802ae1d52ff FOREIGN KEY (direction_id) REFERENCES direction(direction_id);


--
-- Name: fk360802b22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT fk360802b22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk360802be9e4e4e; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT fk360802be9e4e4e FOREIGN KEY (location_type) REFERENCES location_type(location_type_id);


--
-- Name: fk360802c6b78975; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT fk360802c6b78975 FOREIGN KEY (zone_id) REFERENCES zone(zone_id);


--
-- Name: fk36742545a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT fk36742545a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fk36742566c0594b; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT fk36742566c0594b FOREIGN KEY (based_on) REFERENCES trip(trip_id);


--
-- Name: fk3674259433dfde; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT fk3674259433dfde FOREIGN KEY (service_id) REFERENCES calendar(calendar_id);


--
-- Name: fk367425ae1d52ff; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT fk367425ae1d52ff FOREIGN KEY (direction_id) REFERENCES direction(direction_id);


--
-- Name: fk367425b22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT fk367425b22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk367425b4b52abf; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT fk367425b4b52abf FOREIGN KEY (block_id) REFERENCES block(block_id);


--
-- Name: fk367425dd87813f; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT fk367425dd87813f FOREIGN KEY (route_id) REFERENCES route(route_id);


--
-- Name: fk367425fa7f6b32; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT fk367425fa7f6b32 FOREIGN KEY (trip_bikes_allowed) REFERENCES bike_option(bike_option_id);


--
-- Name: fk36ebcb1c39281f; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT fk36ebcb1c39281f FOREIGN KEY (address_id) REFERENCES address(address_id);


--
-- Name: fk3923ac45a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY zone
    ADD CONSTRAINT fk3923ac45a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fk3923acb22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY zone
    ADD CONSTRAINT fk3923acb22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk4c58b7eb355ff50e; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY transfer
    ADD CONSTRAINT fk4c58b7eb355ff50e FOREIGN KEY (transfer_type) REFERENCES transfer_type(transfer_type_id);


--
-- Name: fk4c58b7eb45a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY transfer
    ADD CONSTRAINT fk4c58b7eb45a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fk4c58b7eb552e9620; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY transfer
    ADD CONSTRAINT fk4c58b7eb552e9620 FOREIGN KEY (from_stop_id) REFERENCES stop(stop_id);


--
-- Name: fk4c58b7ebb22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY transfer
    ADD CONSTRAINT fk4c58b7ebb22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk4c58b7ebe4872bf1; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY transfer
    ADD CONSTRAINT fk4c58b7ebe4872bf1 FOREIGN KEY (to_stop_id) REFERENCES stop(stop_id);


--
-- Name: fk597c48d45a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY block
    ADD CONSTRAINT fk597c48d45a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fk597c48db22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY block
    ADD CONSTRAINT fk597c48db22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk6129b4ea45a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT fk6129b4ea45a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fk6129b4ea5d0421b5; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT fk6129b4ea5d0421b5 FOREIGN KEY (stop_id) REFERENCES stop(stop_id);


--
-- Name: fk6129b4ea83b992f1; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT fk6129b4ea83b992f1 FOREIGN KEY (drop_off_type_id) REFERENCES pickup_type(pickup_type_id);


--
-- Name: fk6129b4ea8e2c8ad5; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT fk6129b4ea8e2c8ad5 FOREIGN KEY (trip_id) REFERENCES trip(trip_id);


--
-- Name: fk6129b4eaa57ff44e; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT fk6129b4eaa57ff44e FOREIGN KEY (pickup_type_id) REFERENCES pickup_type(pickup_type_id);


--
-- Name: fk6129b4eab22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT fk6129b4eab22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk6129b4eacd10a62b; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT fk6129b4eacd10a62b FOREIGN KEY (drop_off_type) REFERENCES pickup_type(pickup_type_id);


--
-- Name: fk6129b4eaf7c409ee; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT fk6129b4eaf7c409ee FOREIGN KEY (pickup_type) REFERENCES pickup_type(pickup_type_id);


--
-- Name: fk67ab24945a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY route
    ADD CONSTRAINT fk67ab24945a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fk67ab249805a1a56; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY route
    ADD CONSTRAINT fk67ab249805a1a56 FOREIGN KEY (route_bikes_allowed) REFERENCES bike_option(bike_option_id);


--
-- Name: fk67ab249b22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY route
    ADD CONSTRAINT fk67ab249b22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk67ab249db94cf12; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY route
    ADD CONSTRAINT fk67ab249db94cf12 FOREIGN KEY (route_type_id) REFERENCES route_type(route_type_id);


--
-- Name: fk67ab249ef198a38; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY route
    ADD CONSTRAINT fk67ab249ef198a38 FOREIGN KEY (route_type) REFERENCES route_type(route_type_id);


--
-- Name: fk71a3d8f647d14a20; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_user_role
    ADD CONSTRAINT fk71a3d8f647d14a20 FOREIGN KEY (role_id) REFERENCES user_role(role_id);


--
-- Name: fk71a3d8f6c490a115; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_user_role
    ADD CONSTRAINT fk71a3d8f6c490a115 FOREIGN KEY (user_id) REFERENCES "user"(user_id);


--
-- Name: fk74fc040f45a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date
    ADD CONSTRAINT fk74fc040f45a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fk74fc040f93431bef; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date
    ADD CONSTRAINT fk74fc040f93431bef FOREIGN KEY (service_added) REFERENCES calendar(calendar_id);


--
-- Name: fk74fc040fa02984da; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date
    ADD CONSTRAINT fk74fc040fa02984da FOREIGN KEY (exception_type) REFERENCES calendar_exception_type(calendar_exception_type_id);


--
-- Name: fk74fc040fb22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date
    ADD CONSTRAINT fk74fc040fb22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk74fc040fe25adcf; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date
    ADD CONSTRAINT fk74fc040fe25adcf FOREIGN KEY (service_removed) REFERENCES calendar(calendar_id);


--
-- Name: fk9cb968a018fd7f27; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY service_schedule_bound
    ADD CONSTRAINT fk9cb968a018fd7f27 FOREIGN KEY (service_schedule_group_id) REFERENCES service_schedule_group(service_schedule_group_id);


--
-- Name: fk9cb968a0b22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY service_schedule_bound
    ADD CONSTRAINT fk9cb968a0b22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk9d0125a145a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY service_schedule_group
    ADD CONSTRAINT fk9d0125a145a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fk9d0125a1b22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY service_schedule_group
    ADD CONSTRAINT fk9d0125a1b22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_agency_group_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY agency_group
    ADD CONSTRAINT fk_agency_group_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_agency_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY agency
    ADD CONSTRAINT fk_agency_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_block_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY block
    ADD CONSTRAINT fk_block_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_bound_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY service_schedule_bound
    ADD CONSTRAINT fk_bound_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_calendar_date_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date
    ADD CONSTRAINT fk_calendar_date_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_calendar_exception_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date_exception
    ADD CONSTRAINT fk_calendar_exception_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_calendar_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar
    ADD CONSTRAINT fk_calendar_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_direction_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY direction
    ADD CONSTRAINT fk_direction_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_exception_type_calendar_exception_type; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date
    ADD CONSTRAINT fk_exception_type_calendar_exception_type FOREIGN KEY (exception_type) REFERENCES calendar_exception_type(calendar_exception_type_id);


--
-- Name: fk_fare_rule_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fk_fare_rule_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_fare_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare
    ADD CONSTRAINT fk_fare_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_frequency_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY frequency
    ADD CONSTRAINT fk_frequency_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_role_member_of; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_role_group
    ADD CONSTRAINT fk_role_member_of FOREIGN KEY (member_of_role) REFERENCES user_role(role_id);


--
-- Name: fk_role_role; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_role_group
    ADD CONSTRAINT fk_role_role FOREIGN KEY (role_id) REFERENCES user_role(role_id);


--
-- Name: fk_route_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY route
    ADD CONSTRAINT fk_route_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_service_group_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY service_schedule_group
    ADD CONSTRAINT fk_service_group_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_stop_time_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT fk_stop_time_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_stop_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT fk_stop_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_transfer_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY transfer
    ADD CONSTRAINT fk_transfer_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_trip_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT fk_trip_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fk_user_role_role; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_user_role
    ADD CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES user_role(role_id);


--
-- Name: fk_user_role_user; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_user_role
    ADD CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES "user"(user_id);


--
-- Name: fk_zone_user_last_modified; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY zone
    ADD CONSTRAINT fk_zone_user_last_modified FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fkab611c051c39281f; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY agency
    ADD CONSTRAINT fkab611c051c39281f FOREIGN KEY (address_id) REFERENCES address(address_id);


--
-- Name: fkab611c05b22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY agency
    ADD CONSTRAINT fkab611c05b22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fkafe6646d3c2f5d3; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fkafe6646d3c2f5d3 FOREIGN KEY (destination_id) REFERENCES zone(zone_id);


--
-- Name: fkafe6646d45a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fkafe6646d45a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fkafe6646d8d062235; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fkafe6646d8d062235 FOREIGN KEY (fare_id) REFERENCES fare(fare_id);


--
-- Name: fkafe6646db22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fkafe6646db22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fkafe6646dc17693bb; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fkafe6646dc17693bb FOREIGN KEY (origin_id) REFERENCES zone(zone_id);


--
-- Name: fkafe6646dd9be3e22; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fkafe6646dd9be3e22 FOREIGN KEY (contains_id) REFERENCES zone(zone_id);


--
-- Name: fkafe6646ddd87813f; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY fare_rule
    ADD CONSTRAINT fkafe6646ddd87813f FOREIGN KEY (route_id) REFERENCES route(route_id);


--
-- Name: fkb5be481fa02984da; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date_exception
    ADD CONSTRAINT fkb5be481fa02984da FOREIGN KEY (exception_type) REFERENCES calendar_exception_type(calendar_exception_type_id);


--
-- Name: fkb5be481fb22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date_exception
    ADD CONSTRAINT fkb5be481fb22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fkb5be481fb37eab92; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date_exception
    ADD CONSTRAINT fkb5be481fb37eab92 FOREIGN KEY (calendar_date_id) REFERENCES calendar_date(calendar_date_id);


--
-- Name: fkb5be481fe109203e; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar_date_exception
    ADD CONSTRAINT fkb5be481fe109203e FOREIGN KEY (service_exception) REFERENCES calendar(calendar_id);


--
-- Name: fkbbc6d66a1864e5d5; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_role_group
    ADD CONSTRAINT fkbbc6d66a1864e5d5 FOREIGN KEY (member_of_role) REFERENCES user_role(role_id);


--
-- Name: fkbbc6d66a47d14a20; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_role_group
    ADD CONSTRAINT fkbbc6d66a47d14a20 FOREIGN KEY (role_id) REFERENCES user_role(role_id);


--
-- Name: fkbd989dc5b22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY agency_group
    ADD CONSTRAINT fkbd989dc5b22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fkc6a0077f45a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY direction
    ADD CONSTRAINT fkc6a0077f45a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fkc6a0077fb22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY direction
    ADD CONSTRAINT fkc6a0077fb22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fkcb520dd545a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_coordinate
    ADD CONSTRAINT fkcb520dd545a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fke10707e245a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY agency_group_map
    ADD CONSTRAINT fke10707e245a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fke10707e270a097b4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY agency_group_map
    ADD CONSTRAINT fke10707e270a097b4 FOREIGN KEY (agency_group_id) REFERENCES agency_group(agency_group_id);


--
-- Name: fked7b131645a81c15; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_agency_map
    ADD CONSTRAINT fked7b131645a81c15 FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: fked7b1316c490a115; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_agency_map
    ADD CONSTRAINT fked7b1316c490a115 FOREIGN KEY (user_id) REFERENCES "user"(user_id);


--
-- Name: fkf55efb3e18fd7f27; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar
    ADD CONSTRAINT fkf55efb3e18fd7f27 FOREIGN KEY (service_schedule_group_id) REFERENCES service_schedule_group(service_schedule_group_id);


--
-- Name: fkf55efb3eb22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY calendar
    ADD CONSTRAINT fkf55efb3eb22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fkfbd3855c8e2c8ad5; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY frequency
    ADD CONSTRAINT fkfbd3855c8e2c8ad5 FOREIGN KEY (trip_id) REFERENCES trip(trip_id);


--
-- Name: fkfbd3855cb22f9be4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY frequency
    ADD CONSTRAINT fkfbd3855cb22f9be4 FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: fkfe2a433c1864e5d5; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_account_role
    ADD CONSTRAINT fkfe2a433c1864e5d5 FOREIGN KEY (member_of_role) REFERENCES user_role(role_id);


--
-- Name: fkfe2a433c47dc38b4; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_account_role
    ADD CONSTRAINT fkfe2a433c47dc38b4 FOREIGN KEY (account_id) REFERENCES user_account(id);


--
-- Name: frequency_trip_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY frequency
    ADD CONSTRAINT frequency_trip_id_fkey FOREIGN KEY (trip_id) REFERENCES trip(trip_id);


--
-- Name: route_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY route
    ADD CONSTRAINT route_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: route_route_bikes_allowed_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY route
    ADD CONSTRAINT route_route_bikes_allowed_fkey FOREIGN KEY (route_bikes_allowed) REFERENCES bike_option(bike_option_id);


--
-- Name: route_route_type_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY route
    ADD CONSTRAINT route_route_type_fkey FOREIGN KEY (route_type) REFERENCES route_type(route_type_id);


--
-- Name: service_schedule_bound_service_schedule_group_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY service_schedule_bound
    ADD CONSTRAINT service_schedule_bound_service_schedule_group_id_fkey FOREIGN KEY (service_schedule_group_id) REFERENCES service_schedule_group(service_schedule_group_id);


--
-- Name: service_schedule_group_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY service_schedule_group
    ADD CONSTRAINT service_schedule_group_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: shape_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY shape
    ADD CONSTRAINT shape_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: shape_point_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY shape_point
    ADD CONSTRAINT shape_point_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: shape_point_shape_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY shape_point
    ADD CONSTRAINT shape_point_shape_id_fkey FOREIGN KEY (shape_id) REFERENCES shape(shape_id);


--
-- Name: shape_segment_end_coordinate_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY shape_segment
    ADD CONSTRAINT shape_segment_end_coordinate_id_fkey FOREIGN KEY (end_coordinate_id) REFERENCES stop(stop_id);


--
-- Name: shape_segment_start_coordinate_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY shape_segment
    ADD CONSTRAINT shape_segment_start_coordinate_id_fkey FOREIGN KEY (start_coordinate_id) REFERENCES stop(stop_id);


--
-- Name: shape_user_last_modified_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY shape
    ADD CONSTRAINT shape_user_last_modified_fkey FOREIGN KEY (user_last_modified) REFERENCES "user"(user_id);


--
-- Name: stop_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT stop_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: stop_coordinate_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_coordinate
    ADD CONSTRAINT stop_coordinate_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: stop_direction_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT stop_direction_id_fkey FOREIGN KEY (direction_id) REFERENCES direction(direction_id);


--
-- Name: stop_location_type_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT stop_location_type_fkey FOREIGN KEY (location_type) REFERENCES location_type(location_type_id);


--
-- Name: stop_parent_station_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT stop_parent_station_fkey FOREIGN KEY (parent_station) REFERENCES stop(stop_id);


--
-- Name: stop_time_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT stop_time_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: stop_time_drop_off_type_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT stop_time_drop_off_type_id_fkey FOREIGN KEY (drop_off_type_id) REFERENCES pickup_type(pickup_type_id);


--
-- Name: stop_time_pickup_type_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT stop_time_pickup_type_id_fkey FOREIGN KEY (pickup_type_id) REFERENCES pickup_type(pickup_type_id);


--
-- Name: stop_time_stop_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT stop_time_stop_id_fkey FOREIGN KEY (stop_id) REFERENCES stop(stop_id);


--
-- Name: stop_time_trip_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop_time
    ADD CONSTRAINT stop_time_trip_id_fkey FOREIGN KEY (trip_id) REFERENCES trip(trip_id);


--
-- Name: stop_zone_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY stop
    ADD CONSTRAINT stop_zone_id_fkey FOREIGN KEY (zone_id) REFERENCES zone(zone_id);


--
-- Name: transfer_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY transfer
    ADD CONSTRAINT transfer_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: transfer_from_stop_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY transfer
    ADD CONSTRAINT transfer_from_stop_id_fkey FOREIGN KEY (from_stop_id) REFERENCES stop(stop_id);


--
-- Name: transfer_to_stop_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY transfer
    ADD CONSTRAINT transfer_to_stop_id_fkey FOREIGN KEY (to_stop_id) REFERENCES stop(stop_id);


--
-- Name: transfer_transfer_type_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY transfer
    ADD CONSTRAINT transfer_transfer_type_fkey FOREIGN KEY (transfer_type) REFERENCES transfer_type(transfer_type_id);


--
-- Name: trip_based_on_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT trip_based_on_fkey FOREIGN KEY (based_on) REFERENCES trip(trip_id);


--
-- Name: trip_block_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT trip_block_id_fkey FOREIGN KEY (block_id) REFERENCES block(block_id);


--
-- Name: trip_direction_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT trip_direction_id_fkey FOREIGN KEY (direction_id) REFERENCES direction(direction_id);


--
-- Name: trip_route_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT trip_route_id_fkey FOREIGN KEY (route_id) REFERENCES route(route_id);


--
-- Name: trip_service_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT trip_service_id_fkey FOREIGN KEY (service_id) REFERENCES calendar(calendar_id);


--
-- Name: trip_trip_bikes_allowed_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY trip
    ADD CONSTRAINT trip_trip_bikes_allowed_fkey FOREIGN KEY (trip_bikes_allowed) REFERENCES bike_option(bike_option_id);


--
-- Name: user_address_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_address_id_fkey FOREIGN KEY (address_id) REFERENCES address(address_id);


--
-- Name: user_agency_map_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_agency_map
    ADD CONSTRAINT user_agency_map_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: user_agency_map_user_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY user_agency_map
    ADD CONSTRAINT user_agency_map_user_id_fkey FOREIGN KEY (user_id) REFERENCES "user"(user_id);


--
-- Name: zone_agency_id_fkey; Type: FK CONSTRAINT; Schema: idea; Owner: ideauser
--

ALTER TABLE ONLY zone
    ADD CONSTRAINT zone_agency_id_fkey FOREIGN KEY (agency_id) REFERENCES agency(agency_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

