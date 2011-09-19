CREATE TABLE agency
(
  id BIGINT NOT NULL,
  code CHARACTER VARYING(3) NOT NULL,
  CONSTRAINT agency_pkey PRIMARY KEY (id),
  CONSTRAINT agency_code_key UNIQUE (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE agency OWNER TO postgres;


CREATE TABLE event
(
  id BIGINT NOT NULL,
  date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  depth NUMERIC(4,1) NOT NULL,
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
  id BIGINT NOT NULL,
  "type" CHARACTER VARYING(8) NOT NULL,
  "value" NUMERIC(3,1) NOT NULL,
  reportingagency_id BIGINT NOT NULL,
  event_id BIGINT NOT NULL,
  CONSTRAINT magnitude_pkey PRIMARY KEY (id),
  CONSTRAINT fk_event_id FOREIGN KEY (event_id)
      REFERENCES event (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_reportingagency_id FOREIGN KEY (reportingagency_id)
      REFERENCES agency (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE magnitude OWNER TO postgres;

CREATE VIEW eventandaveragemagnitudes AS 
  SELECT
      /* Basic event data */
      event.*,
      /* Magnitudes by type - aka: (ml, mb, ms, ...)magnitude */
      avg(mlmagnitude.value) AS mlmagnitude,
      avg(mbmagnitude.value) AS mbmagnitude,
      avg(msmagnitude.value) AS msmagnitude,
      avg(mwmagnitude.value) AS mwmagnitude,
      avg(mblgmagnitude.value) AS mblgmagnitude,
      avg(mcmagnitude.value) AS mcmagnitude,
      /* Max and min magnitude expected limits by type */
      mlmagnitudelimits.min AS minmlmagnitude, mlmagnitudelimits.max AS maxmlmagnitude,
      mbmagnitudelimits.min AS minmbmagnitude, mbmagnitudelimits.max AS maxmbmagnitude,
      msmagnitudelimits.min AS minmsmagnitude, msmagnitudelimits.max AS maxmsmagnitude,
      mwmagnitudelimits.min AS minmwmagnitude, mwmagnitudelimits.max AS maxmwmagnitude,
      mblgmagnitudelimits.min AS minmblgmagnitude, mblgmagnitudelimits.max AS maxmblgmagnitude,
      mcmagnitudelimits.min AS minmcmagnitude, mcmagnitudelimits.max AS maxmcmagnitude,
      rankmagnitudelimits.min AS minrankmagnitude, rankmagnitudelimits.max AS maxrankmagnitude,
      /* Magnitudes by type normalized by expected limits (0 to 1 expected) - aka: (ml, mb, ms, ...)index */
      (avg(mlmagnitude.value) - mlmagnitudelimits.min) / (mlmagnitudelimits.max - mlmagnitudelimits.min) as mlindex,
      (avg(mbmagnitude.value) - mbmagnitudelimits.min) / (mbmagnitudelimits.max - mbmagnitudelimits.min) as mbindex,
      (avg(msmagnitude.value) - msmagnitudelimits.min) / (msmagnitudelimits.max - msmagnitudelimits.min) as msindex,
      (avg(mwmagnitude.value) - mwmagnitudelimits.min) / (mwmagnitudelimits.max - mwmagnitudelimits.min) as mwindex,
      (avg(mblgmagnitude.value) - mblgmagnitudelimits.min) / (mblgmagnitudelimits.max - mblgmagnitudelimits.min) as mblgindex,
      (avg(mcmagnitude.value) - mcmagnitudelimits.min) / (mcmagnitudelimits.max - mcmagnitudelimits.min) as mcindex,
      /* Greatest normalized magnitude multiplied by 10 (0 to 10 expected) - aka: rankmagnitude */
      greatest(
        (avg(mlmagnitude.value) - mlmagnitudelimits.min) / (mlmagnitudelimits.max - mlmagnitudelimits.min),
        (avg(mbmagnitude.value) - mbmagnitudelimits.min) / (mbmagnitudelimits.max - mbmagnitudelimits.min),
        (avg(msmagnitude.value) - msmagnitudelimits.min) / (msmagnitudelimits.max - msmagnitudelimits.min),
        (avg(mwmagnitude.value) - mwmagnitudelimits.min) / (mwmagnitudelimits.max - mwmagnitudelimits.min),
        (avg(mblgmagnitude.value) - mblgmagnitudelimits.min) / (mblgmagnitudelimits.max - mblgmagnitudelimits.min),
        (avg(mcmagnitude.value) - mcmagnitudelimits.min) / (mcmagnitudelimits.max - mcmagnitudelimits.min)) * 10 as rankmagnitude,
      /* Greatest normalized magnitude (0 to 1 expected) - aka: rankindex */
      greatest(
        (avg(mlmagnitude.value) - mlmagnitudelimits.min) / (mlmagnitudelimits.max - mlmagnitudelimits.min),
        (avg(mbmagnitude.value) - mbmagnitudelimits.min) / (mbmagnitudelimits.max - mbmagnitudelimits.min),
        (avg(msmagnitude.value) - msmagnitudelimits.min) / (msmagnitudelimits.max - msmagnitudelimits.min),
        (avg(mwmagnitude.value) - mwmagnitudelimits.min) / (mwmagnitudelimits.max - mwmagnitudelimits.min),
        (avg(mblgmagnitude.value) - mblgmagnitudelimits.min) / (mblgmagnitudelimits.max - mblgmagnitudelimits.min),
        (avg(mcmagnitude.value) - mcmagnitudelimits.min) / (mcmagnitudelimits.max - mcmagnitudelimits.min)) as rankindex
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
      event.location,
      event.name,
      event.note,
      event.reference,
      minmlmagnitude, maxmlmagnitude,
      minmbmagnitude, maxmbmagnitude,
      minmsmagnitude, maxmsmagnitude,
      minmwmagnitude, maxmwmagnitude,
      minmblgmagnitude, maxmblgmagnitude,
      minmcmagnitude, maxmcmagnitude,
      minrankmagnitude, maxrankmagnitude;
ALTER VIEW eventandaveragemagnitudes OWNER TO postgres;

CREATE VIEW databounds AS 
  SELECT
      1 AS id,
      min(depth) AS mindepth,
      max(depth) AS maxdepth,
      min(date) AS mindate,
      max(date) AS maxdate
   FROM eventandaveragemagnitudes;
ALTER VIEW databounds OWNER TO postgres;

CREATE VIEW magnitudedatabounds AS
  SELECT 1 as databound_id, 'RANK'::CHARACTER VARYING(4) as magnitudetype, min(rankmagnitude) AS min, max(rankmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1 as databound_id, 'ML'::CHARACTER VARYING(4) as magnitudetype, min(mlmagnitude) AS min, max(mlmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1 as databound_id, 'MB'::CHARACTER VARYING(4) as magnitudetype, min(mbmagnitude) AS min, max(mbmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1 as databound_id, 'MS'::CHARACTER VARYING(4) as magnitudetype, min(msmagnitude) AS min, max(msmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1 as databound_id, 'MW'::CHARACTER VARYING(4) as magnitudetype, min(mwmagnitude) AS min, max(mwmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1 as databound_id, 'MBLG'::CHARACTER VARYING(4) as magnitudetype, min(mblgmagnitude) AS min, max(mblgmagnitude) AS max FROM eventandaveragemagnitudes
  UNION
  SELECT 1 as databound_id, 'MC'::CHARACTER VARYING(4) as magnitudetype, min(mcmagnitude) AS min, max(mcmagnitude) AS max FROM eventandaveragemagnitudes;
ALTER VIEW magnitudedatabounds OWNER TO postgres;
