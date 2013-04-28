CREATE TABLE agency
(
  id BIGSERIAL NOT NULL,
  code CHARACTER VARYING(3) NOT NULL,
  CONSTRAINT agency_pkey PRIMARY KEY (id),
  CONSTRAINT agency_code_key UNIQUE (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE agency OWNER TO postgres;

CREATE TABLE application
(
  id BIGSERIAL NOT NULL,
  settingscacheexpiration bigint,
  googlemapsapikey character varying(255),
  eventmapzoom integer,
  layername character varying(255),
  depthlayername character varying(255),
  affecteddistancestylename character varying(255),
  defaultmapanimationstepduration real,
  defaultmapanimationstepkeep real,
  defaultmapanimationsteps integer,
  defaultmapcenterlatitude double precision,
  defaultmapcenterlongitude double precision,
  defaultmapdescription character varying(255),
  defaultmaplistunmeasured boolean,
  defaultmapmaxdate timestamp without time zone,
  defaultmapmaxdaterelativeamount real,
  defaultmapmaxdepth real,
  defaultmapmaxmagnitude real,
  defaultmapmindate timestamp without time zone,
  defaultmapmindaterelativeamount real,
  defaultmapmindepth real,
  defaultmapminmagnitude real,
  defaultmapreverseanimation boolean,
  defaultmapzoom integer,
  layerserveruri character varying(255),
  defaultmapanimationtype character varying(255),
  defaultmapmagnitudetype character varying(255),
  defaultmapmaxdaterelativeunits character varying(255),
  defaultmapmaxdatetype character varying(255),
  defaultmapmaxdepthtype character varying(255),
  defaultmapmaxmagnitudetype character varying(255),
  defaultmapmindaterelativeunits character varying(255),
  defaultmapmindatetype character varying(255),
  defaultmapmindepthtype character varying(255),
  defaultmapminmagnitudetype character varying(255),
  defaultmapstyle_id bigint,
  defaultmapname character varying(255),
  legendsdirectory character varying(255),
  CONSTRAINT application_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE application OWNER TO postgres;

CREATE TABLE category
(
  id BIGSERIAL NOT NULL,
  inapplicationindex integer NOT NULL,
  "name" character varying(255) NOT NULL,
  application_id bigint NOT NULL,
  CONSTRAINT category_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE category OWNER TO postgres;

CREATE TABLE event
(
  id BIGSERIAL NOT NULL,
  damageddistance integer,
  date timestamp without time zone NOT NULL,
  depth real NOT NULL,
  "name" character varying(255),
  notes character varying(255),
  perceiveddistance integer,
  reference character varying(255),
  CONSTRAINT event_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=TRUE
);
ALTER TABLE event OWNER TO postgres;

CREATE INDEX event_oid ON event (oid);

SELECT AddGeometryColumn('event', 'location', 900913, 'POINT', 2);

CREATE TABLE magnitude
(
  id BIGSERIAL NOT NULL,
  "type" character varying(255) NOT NULL,
  "value" real NOT NULL,
  reportingagency_id bigint NOT NULL,
  event_id bigint NOT NULL,
  CONSTRAINT magnitude_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE magnitude OWNER TO postgres;

CREATE TABLE magnitudelimits
(
  magnitudetype character varying(255) NOT NULL,
  min real NOT NULL,
  max real NOT NULL,
  CONSTRAINT magnitudelimits_pkey PRIMARY KEY (magnitudetype)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE magnitudelimits OWNER TO postgres;

CREATE TABLE seismapuser
(
  id BIGSERIAL NOT NULL,
  email character varying(255) NOT NULL,
  "name" character varying(255) NOT NULL,
  passwordhash character varying(255),
  administrator boolean,
  CONSTRAINT seismapuser_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE seismapuser OWNER TO postgres;

CREATE TABLE style
(
  id BIGSERIAL NOT NULL,
  "name" character varying(255) NOT NULL,
  sld character varying(255),
  inapplicationindex integer,
  application_id bigint,
  CONSTRAINT style_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE style OWNER TO postgres;

CREATE TABLE stylevariable
(
  style_id BIGSERIAL NOT NULL,
  "value" character varying(255) NOT NULL,
  "name" character varying(255) NOT NULL,
  CONSTRAINT stylevariable_pkey PRIMARY KEY (style_id, name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE stylevariable OWNER TO postgres;

CREATE TABLE map
(
  id BIGSERIAL NOT NULL,
  animationstepkeep real NOT NULL,
  animationsteps integer NOT NULL,
  animationtype character varying(255) NOT NULL,
  description character varying(255) NOT NULL,
  incategoryindex integer,
  inuserindex integer,
  listunmeasured boolean NOT NULL,
  magnitudetype integer NOT NULL,
  maxdate timestamp without time zone NOT NULL,
  maxdaterelativeamount real NOT NULL,
  maxdaterelativeunits character varying(255) NOT NULL,
  maxdatetype character varying(255) NOT NULL,
  maxdepth real,
  maxdepthtype character varying(255) NOT NULL,
  maxmagnitude real,
  maxmagnitudetype character varying(255) NOT NULL,
  mindate timestamp without time zone NOT NULL,
  mindaterelativeamount real NOT NULL,
  mindaterelativeunits character varying(255) NOT NULL,
  mindatetype character varying(255) NOT NULL,
  mindepth real,
  mindepthtype character varying(255) NOT NULL,
  minmagnitude real,
  minmagnitudetype character varying(255) NOT NULL,
  "name" character varying(255) NOT NULL,
  reverseanimation boolean NOT NULL,
  zoom integer NOT NULL,
  user_id bigint,
  category_id bigint,
  animationstepduration real NOT NULL,
  style_id bigint,
  CONSTRAINT map_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=TRUE
);
ALTER TABLE map OWNER TO postgres;

CREATE INDEX map_oid ON map (oid);

SELECT AddGeometryColumn('map', 'center', 900913, 'POINT', 2);

/* Foreign keys */

ALTER TABLE application
  ADD CONSTRAINT application_defaultmapstyle_fk FOREIGN KEY (defaultmapstyle_id)
      REFERENCES style (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE;

ALTER TABLE category
  ADD CONSTRAINT catergory_application_id_fk FOREIGN KEY (application_id)
      REFERENCES application (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE;

ALTER TABLE magnitude
  ADD CONSTRAINT magnitude_event_id_fk FOREIGN KEY (event_id)
      REFERENCES event (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE magnitude
  ADD CONSTRAINT magnitude_reportingagency_id_fk FOREIGN KEY (reportingagency_id)
      REFERENCES agency (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE style
  ADD CONSTRAINT style_application_id_fk FOREIGN KEY (application_id)
      REFERENCES application (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE;

ALTER TABLE stylevariable
  ADD CONSTRAINT stylevariable_style_id_fk FOREIGN KEY (style_id)
      REFERENCES style (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE map
  ADD CONSTRAINT map_style_id_fk FOREIGN KEY (style_id)
      REFERENCES style (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE map
  ADD CONSTRAINT map_user_id_fk FOREIGN KEY (user_id)
      REFERENCES seismapuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE map
  ADD CONSTRAINT map_category_id_fk FOREIGN KEY (category_id)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;      

/* Views */

CREATE VIEW eventandaveragemagnitudes_unmaterialized AS 
    SELECT
        /* Basic event data */
        event.id,
        event.date,
        event.depth,
        event.name,
        event.notes,
        event.reference,
        event.perceiveddistance,
        event.damageddistance,
        /* Magnitudes by type - aka: (ml, mb, ms, ...)magnitude */
        avg(mlmagnitude.value)::real AS mlmagnitude,
        avg(mbmagnitude.value)::real AS mbmagnitude,
        avg(msmagnitude.value)::real AS msmagnitude,
        avg(mwmagnitude.value)::real AS mwmagnitude,
        avg(mblgmagnitude.value)::real AS mblgmagnitude,
        avg(mcmagnitude.value)::real AS mcmagnitude,
        /* Max and min magnitude expected limits by type */
        mlmagnitudelimits.min AS minmlmagnitude, mlmagnitudelimits.max AS maxmlmagnitude,
        mbmagnitudelimits.min AS minmbmagnitude, mbmagnitudelimits.max AS maxmbmagnitude,
        msmagnitudelimits.min AS minmsmagnitude, msmagnitudelimits.max AS maxmsmagnitude,
        mwmagnitudelimits.min AS minmwmagnitude, mwmagnitudelimits.max AS maxmwmagnitude,
        mblgmagnitudelimits.min AS minmblgmagnitude, mblgmagnitudelimits.max AS maxmblgmagnitude,
        mcmagnitudelimits.min AS minmcmagnitude, mcmagnitudelimits.max AS maxmcmagnitude,
        rankmagnitudelimits.min AS minrankmagnitude, rankmagnitudelimits.max AS maxrankmagnitude,
        /* Magnitudes by type normalized by expected limits (0 to 1 expected) - aka: (ml, mb, ms, ...)index */
        (avg(mlmagnitude.value) - mlmagnitudelimits.min) / (mlmagnitudelimits.max - mlmagnitudelimits.min)::real as mlindex,
        (avg(mbmagnitude.value) - mbmagnitudelimits.min) / (mbmagnitudelimits.max - mbmagnitudelimits.min)::real as mbindex,
        (avg(msmagnitude.value) - msmagnitudelimits.min) / (msmagnitudelimits.max - msmagnitudelimits.min)::real as msindex,
        (avg(mwmagnitude.value) - mwmagnitudelimits.min) / (mwmagnitudelimits.max - mwmagnitudelimits.min)::real as mwindex,
        (avg(mblgmagnitude.value) - mblgmagnitudelimits.min) / (mblgmagnitudelimits.max - mblgmagnitudelimits.min)::real as mblgindex,
        (avg(mcmagnitude.value) - mcmagnitudelimits.min) / (mcmagnitudelimits.max - mcmagnitudelimits.min)::real as mcindex,
        /* Greatest normalized magnitude multiplied by 10 (0 to 10 expected) - aka: rankmagnitude */
        (greatest(
          (avg(mlmagnitude.value) - mlmagnitudelimits.min) / (mlmagnitudelimits.max - mlmagnitudelimits.min),
          (avg(mbmagnitude.value) - mbmagnitudelimits.min) / (mbmagnitudelimits.max - mbmagnitudelimits.min),
          (avg(msmagnitude.value) - msmagnitudelimits.min) / (msmagnitudelimits.max - msmagnitudelimits.min),
          (avg(mwmagnitude.value) - mwmagnitudelimits.min) / (mwmagnitudelimits.max - mwmagnitudelimits.min),
          (avg(mblgmagnitude.value) - mblgmagnitudelimits.min) / (mblgmagnitudelimits.max - mblgmagnitudelimits.min),
          (avg(mcmagnitude.value) - mcmagnitudelimits.min) / (mcmagnitudelimits.max - mcmagnitudelimits.min)) * 10)::real as rankmagnitude,
        /* Greatest normalized magnitude (0 to 1 expected) - aka: rankindex */
        greatest(
          (avg(mlmagnitude.value) - mlmagnitudelimits.min) / (mlmagnitudelimits.max - mlmagnitudelimits.min),
          (avg(mbmagnitude.value) - mbmagnitudelimits.min) / (mbmagnitudelimits.max - mbmagnitudelimits.min),
          (avg(msmagnitude.value) - msmagnitudelimits.min) / (msmagnitudelimits.max - msmagnitudelimits.min),
          (avg(mwmagnitude.value) - mwmagnitudelimits.min) / (mwmagnitudelimits.max - mwmagnitudelimits.min),
          (avg(mblgmagnitude.value) - mblgmagnitudelimits.min) / (mblgmagnitudelimits.max - mblgmagnitudelimits.min),
          (avg(mcmagnitude.value) - mcmagnitudelimits.min) / (mcmagnitudelimits.max - mcmagnitudelimits.min))::real as rankindex,
        /* Location */
        event.location,
        /* Depth location */
        st_setsrid(
          st_makepoint(
            st_x(event.location),
            - event.depth * 1000
          ),
          900913
        ) AS depthlocation
      FROM
        event
        LEFT JOIN magnitude mlmagnitude ON event.id = mlmagnitude.event_id AND mlmagnitude.type = 'ML'::CHARACTER VARYING(4)
        LEFT JOIN magnitude mbmagnitude ON event.id = mbmagnitude.event_id AND mbmagnitude.type = 'MB'::CHARACTER VARYING(4)
        LEFT JOIN magnitude msmagnitude ON event.id = msmagnitude.event_id AND msmagnitude.type = 'MS'::CHARACTER VARYING(4)
        LEFT JOIN magnitude mwmagnitude ON event.id = mwmagnitude.event_id AND mwmagnitude.type  = 'MW'::CHARACTER VARYING(4)
        LEFT JOIN magnitude mblgmagnitude ON event.id = mblgmagnitude.event_id AND mblgmagnitude.type  = 'MBLG'::CHARACTER VARYING(4)
        LEFT JOIN magnitude mcmagnitude ON event.id = mcmagnitude.event_id AND mcmagnitude.type = 'MC'::CHARACTER VARYING(4)
        LEFT JOIN magnitudelimits mlmagnitudelimits ON mlmagnitudelimits.magnitudetype = 'ML'::CHARACTER VARYING(4)
        LEFT JOIN magnitudelimits mbmagnitudelimits ON mbmagnitudelimits.magnitudetype = 'MB'::CHARACTER VARYING(4)
        LEFT JOIN magnitudelimits msmagnitudelimits ON msmagnitudelimits.magnitudetype = 'MS'::CHARACTER VARYING(4)
        LEFT JOIN magnitudelimits mwmagnitudelimits ON mwmagnitudelimits.magnitudetype = 'MW'::CHARACTER VARYING(4)
        LEFT JOIN magnitudelimits mblgmagnitudelimits ON mblgmagnitudelimits.magnitudetype = 'MBLG'::CHARACTER VARYING(4)
        LEFT JOIN magnitudelimits mcmagnitudelimits ON mcmagnitudelimits.magnitudetype = 'MC'::CHARACTER VARYING(4)
        LEFT JOIN magnitudelimits rankmagnitudelimits ON rankmagnitudelimits.magnitudetype = 'RANK'::CHARACTER VARYING(4)
      GROUP BY
        event.id,
        event.date,
        event.depth,
        event.name,
        event.notes,
        event.reference,
        event.perceiveddistance,
        event.damageddistance,
        minmlmagnitude, maxmlmagnitude,
        minmbmagnitude, maxmbmagnitude,
        minmsmagnitude, maxmsmagnitude,
        minmwmagnitude, maxmwmagnitude,
        minmblgmagnitude, maxmblgmagnitude,
        minmcmagnitude, maxmcmagnitude,
        minrankmagnitude, maxrankmagnitude,
        event.location,
        st_setsrid(
          st_makepoint(
            st_x(event.location),
            - event.depth * 1000
          ),
          900913
        );
ALTER VIEW eventandaveragemagnitudes_unmaterialized OWNER TO postgres;

CREATE TABLE eventandaveragemagnitudes AS 
  SELECT *
    FROM eventandaveragemagnitudes_unmaterialized;

ALTER TABLE eventandaveragemagnitudes OWNER TO postgres;

ALTER TABLE eventandaveragemagnitudes ADD PRIMARY KEY (id);

ALTER TABLE eventandaveragemagnitudes SET WITH OIDS;

CREATE INDEX eventandaveragemagnitudes_oid ON eventandaveragemagnitudes (oid);

ALTER TABLE eventandaveragemagnitudes DROP COLUMN location;
SELECT AddGeometryColumn('eventandaveragemagnitudes', 'location', 900913, 'POINT', 2);

ALTER TABLE eventandaveragemagnitudes DROP COLUMN depthlocation;
SELECT AddGeometryColumn('eventandaveragemagnitudes', 'depthlocation', 900913, 'POINT', 2);

CREATE FUNCTION eventandaveragemagnitudes_refresh_row(eventid BIGINT) RETURNS VOID 
SECURITY DEFINER 
LANGUAGE 'plpgsql' AS $$ 
BEGIN
  DELETE  
    FROM eventandaveragemagnitudes event
    WHERE event.id = eventid; 
  INSERT INTO eventandaveragemagnitudes  
    SELECT *
      FROM eventandaveragemagnitudes_unmaterialized event 
      WHERE event.id = eventid; 
END
$$;

CREATE FUNCTION eventandaveragemagnitudes_refresh_table() RETURNS VOID 
SECURITY DEFINER LANGUAGE 'plpgsql' AS $$ 
BEGIN
  TRUNCATE TABLE eventandaveragemagnitudes RESTART IDENTITY;
  INSERT INTO eventandaveragemagnitudes  
    SELECT *
      FROM eventandaveragemagnitudes_unmaterialized event;
END
$$;

CREATE FUNCTION event_ut() RETURNS TRIGGER 
SECURITY DEFINER LANGUAGE 'plpgsql' AS $$ 
BEGIN 
  IF old.id = new.id THEN 
    PERFORM eventandaveragemagnitudes_refresh_row(new.id); 
  ELSE 
    PERFORM eventandaveragemagnitudes_refresh_row(old.id); 
    PERFORM eventandaveragemagnitudes_refresh_row(new.id); 
  END IF; 
  RETURN NULL; 
END
$$;

CREATE FUNCTION event_dt() RETURNS TRIGGER
SECURITY DEFINER LANGUAGE 'plpgsql' AS $$ 
BEGIN 
  PERFORM eventandaveragemagnitudes_refresh_row(old.id); 
  RETURN NULL; 
END 
$$; 

CREATE FUNCTION event_it() RETURNS TRIGGER
SECURITY DEFINER LANGUAGE 'plpgsql' AS $$ 
BEGIN 
  PERFORM eventandaveragemagnitudes_refresh_row(new.id); 
  RETURN NULL; 
END 
$$;

CREATE TRIGGER event_ut AFTER UPDATE ON event 
  FOR EACH ROW EXECUTE PROCEDURE event_ut();  
  
CREATE TRIGGER event_dt AFTER DELETE ON event 
  FOR EACH ROW EXECUTE PROCEDURE event_dt();  
  
CREATE TRIGGER event_it AFTER INSERT ON event 
  FOR EACH ROW EXECUTE PROCEDURE event_it();

CREATE FUNCTION magnitude_ut() RETURNS TRIGGER
SECURITY DEFINER LANGUAGE 'plpgsql' AS $$ 
BEGIN 
  IF old.event_id = new.event_id THEN
    PERFORM eventandaveragemagnitudes_refresh_row(new.event_id);
  ELSE 
    PERFORM eventandaveragemagnitudes_refresh_row(old.event_id);
    PERFORM eventandaveragemagnitudes_refresh_row(new.event_id);
  END IF;
  RETURN NULL;
END
$$;

CREATE FUNCTION magnitude_dt() RETURNS TRIGGER
SECURITY DEFINER LANGUAGE 'plpgsql' AS $$ 
BEGIN 
  PERFORM eventandaveragemagnitudes_refresh_row(old.event_id); 
  RETURN NULL; 
END 
$$; 

CREATE FUNCTION magnitude_it() RETURNS TRIGGER
SECURITY DEFINER LANGUAGE 'plpgsql' AS $$ 
BEGIN 
  PERFORM eventandaveragemagnitudes_refresh_row(new.event_id); 
  RETURN NULL; 
END 
$$;

CREATE TRIGGER magnitude_ut AFTER UPDATE ON magnitude 
  FOR EACH ROW EXECUTE PROCEDURE magnitude_ut();  
  
CREATE TRIGGER magnitude_dt AFTER DELETE ON magnitude 
  FOR EACH ROW EXECUTE PROCEDURE magnitude_dt();  
  
CREATE TRIGGER magnitude_it AFTER INSERT ON magnitude 
  FOR EACH ROW EXECUTE PROCEDURE magnitude_it();

CREATE FUNCTION magnitudelimits_ct() RETURNS TRIGGER
SECURITY DEFINER LANGUAGE 'plpgsql' AS $$ 
BEGIN 
  PERFORM eventandaveragemagnitudes_refresh_table(); 
  RETURN NULL; 
END 
$$;

CREATE TRIGGER magnitudelimits_ct AFTER UPDATE OR DELETE OR INSERT ON magnitudelimits 
  FOR EACH STATEMENT EXECUTE PROCEDURE magnitudelimits_ct();

CREATE VIEW databounds AS 
  SELECT
      1::bigint AS id,
      min(depth) AS mindepth,
      max(depth) AS maxdepth,
      min(date) AS mindate,
      max(date) AS maxdate
   FROM eventandaveragemagnitudes;
ALTER VIEW databounds OWNER TO postgres;

CREATE VIEW magnitudedatabounds AS
  SELECT 1::bigint as databound_id, 'RANK'::CHARACTER VARYING(4) as magnitudetype, min(rankmagnitude) AS min, max(rankmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1::bigint as databound_id, 'ML'::CHARACTER VARYING(4) as magnitudetype, min(mlmagnitude) AS min, max(mlmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1::bigint as databound_id, 'MB'::CHARACTER VARYING(4) as magnitudetype, min(mbmagnitude) AS min, max(mbmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1::bigint as databound_id, 'MS'::CHARACTER VARYING(4) as magnitudetype, min(msmagnitude) AS min, max(msmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1::bigint as databound_id, 'MW'::CHARACTER VARYING(4) as magnitudetype, min(mwmagnitude) AS min, max(mwmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1::bigint as databound_id, 'MBLG'::CHARACTER VARYING(4) as magnitudetype, min(mblgmagnitude) AS min, max(mblgmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1::bigint as databound_id, 'MC'::CHARACTER VARYING(4) as magnitudetype, min(mcmagnitude) AS min, max(mcmagnitude) AS max FROM eventandaveragemagnitudes;
ALTER VIEW magnitudedatabounds OWNER TO postgres;
