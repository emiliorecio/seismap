DROP VIEW magnitudedatabounds;

DROP VIEW databounds;

DROP VIEW eventandaveragemagnitudes;

DROP TABLE magnitude;

DROP TABLE agency;

SELECT DropGeometryColumn('event', 'location');
DROP INDEX event_oid;
DROP TABLE event;
