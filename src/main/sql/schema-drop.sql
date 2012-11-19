/* Foreign keys */

ALTER TABLE application
  DROP CONSTRAINT application_defaultmapstyle_fk;

ALTER TABLE category
  DROP CONSTRAINT catergory_application_id_fk;

ALTER TABLE magnitude
  DROP CONSTRAINT magnitude_event_id_fk;
ALTER TABLE magnitude
  DROP CONSTRAINT magnitude_reportingagency_id_fk;

ALTER TABLE style
  DROP CONSTRAINT style_application_id_fk;

ALTER TABLE stylevariable
  DROP CONSTRAINT stylevariable_style_id_fk;

ALTER TABLE map
  DROP CONSTRAINT map_style_id_fk;
ALTER TABLE map
  DROP CONSTRAINT map_user_id_fk;
ALTER TABLE map
  DROP CONSTRAINT map_category_id_fk;

/* Views */
  
DROP VIEW databounds;

DROP VIEW magnitudedatabounds;

DROP TRIGGER magnitudelimits_ct ON magnitudelimits;

DROP FUNCTION magnitudelimits_ct();

DROP TRIGGER magnitude_it ON magnitude;

DROP TRIGGER magnitude_dt ON magnitude; 

DROP TRIGGER magnitude_ut ON magnitude;

DROP FUNCTION magnitude_it();

DROP FUNCTION magnitude_dt();

DROP FUNCTION magnitude_ut();

DROP TRIGGER event_it ON event;

DROP TRIGGER event_dt ON event;  

DROP TRIGGER event_ut ON event;  

DROP FUNCTION event_it();

DROP FUNCTION event_dt();

DROP FUNCTION event_ut();

DROP FUNCTION eventandaveragemagnitudes_refresh_table();

DROP FUNCTION eventandaveragemagnitudes_refresh_row(id BIGINT);

SELECT DropGeometryColumn('eventandaveragemagnitudes', 'depthlocation');

SELECT DropGeometryColumn('eventandaveragemagnitudes', 'location');

DROP TABLE eventandaveragemagnitudes;

DROP VIEW eventandaveragemagnitudes_unmaterialized;

/* Tables */

SELECT DropGeometryColumn('map', 'center');
DROP INDEX map_oid;
DROP TABLE map;

DROP TABLE stylevariable;

DROP TABLE seismapuser;

DROP TABLE magnitudelimits;

DROP TABLE magnitude;

SELECT DropGeometryColumn('event', 'location');
DROP INDEX event_oid;
DROP TABLE event;

DROP TABLE category;

DROP TABLE application;

DROP TABLE style;

DROP TABLE agency;

