START TRANSACTION;
SET CONSTRAINTS ALL DEFERRED;

/* Application */
set @application_id =                                  1;
set @application_layerserveruri =                      'http://localhost@12345/geoserver';
set @application_legendsdirectory =                    'C:/workspace/seismap/src/main/sld/';

/* Map */
set @application_defaultmapname =                      'New map';
set @application_defaultmapdescription =               '';
set @application_defaultmapcenterlatitude =            -31.534167;
set @application_defaultmapcenterlongitude =           -68.526111;
set @application_defaultmapzoom =                      10;
set @application_defaultmapstyle_id =                  1;

/* Min date */
set @application_defaultmapmindatetype =               'RELATIVE';
set @application_defaultmapmindate =                   'null';
set @application_defaultmapmindaterelativeamount =     1;
set @application_defaultmapmindaterelativeunits =      'DAY';

/* Max date */
set @application_defaultmapmaxdatetype =               'RELATIVE';
set @application_defaultmapmaxdate =                   'null';
set @application_defaultmapmaxdaterelativeamount =     0;
set @application_defaultmapmaxdaterelativeunits =      'HOUR';

/* Min depth */
set @application_defaultmapmindepthtype =              'NONE';
set @application_defaultmapmindepth =                  0;

/* Max depth */
set @application_defaultmapmaxdepthtype =              'NONE';
set @application_defaultmapmaxdepth =                  0;

/* Magnitude */
set @application_defaultmapmagnitudetype =             'ML';
set @application_defaultmaplistunmeasured =            'true';

/* Min magnitude */
set @application_defaultmapminmagnitudetype =          'ABSOLUTE';
set @application_defaultmapminmagnitude =              0.1;

/* Max magnitude */
set @application_defaultmapmaxmagnitudetype =          'ABSOLUTE';
set @application_defaultmapmaxmagnitude =              0;

/* Animation */
set @application_defaultmapanimationtype =             'DATE';
set @application_defaultmapanimationstepduration =     5;
set @application_defaultmapanimationstepkeep =         0;
set @application_defaultmapanimationsteps =            10;
set @application_defaultmapreverseanimation =          'false';

INSERT INTO application(
	id,
	defaultmapanimationstepduration,
	defaultmapanimationstepkeep, 
	defaultmapanimationsteps,
	defaultmapcenterlatitude,
	defaultmapcenterlongitude, 
	defaultmapdescription,
	defaultmaplistunmeasured,
	defaultmapmaxdate, 
	defaultmapmaxdaterelativeamount,
	defaultmapmaxdepth,
	defaultmapmaxmagnitude, 
	defaultmapmindate,
	defaultmapmindaterelativeamount,
	defaultmapmindepth, 
	defaultmapminmagnitude,
	defaultmapreverseanimation,
	defaultmapzoom, 
	layerserveruri,
	defaultmapanimationtype, defaultmapmagnitudetype, 
	defaultmapmaxdaterelativeunits,
	defaultmapmaxdatetype,
	defaultmapmaxdepthtype, 
	defaultmapmaxmagnitudetype,
	defaultmapmindaterelativeunits,
	defaultmapmindatetype, 
	defaultmapmindepthtype, 
	defaultmapminmagnitudetype,
	defaultmapstyle_id, 
	defaultmapname,
	legendsdirectory)
  VALUES (
	@application_id,
	@application_defaultmapanimationstepduration,
	@application_defaultmapanimationstepkeep, 
	@application_defaultmapanimationsteps,
	@application_defaultmapcenterlatitude,
	@application_defaultmapcenterlongitude, 
	'@application_defaultmapdescription',
	@application_defaultmaplistunmeasured,
	@application_defaultmapmaxdate,
	@application_defaultmapmaxdaterelativeamount,
	@application_defaultmapmaxdepth,
	@application_defaultmapmaxmagnitude, 
	@application_defaultmapmindate,
	@application_defaultmapmindaterelativeamount,
	@application_defaultmapmindepth, 
	@application_defaultmapminmagnitude,
	@application_defaultmapreverseanimation,
	@application_defaultmapzoom, 
	'@application_layerserveruri',
	'@application_defaultmapanimationtype',
	'@application_defaultmapmagnitudetype', 
	'@application_defaultmapmaxdaterelativeunits',
	'@application_defaultmapmaxdatetype',
	'@application_defaultmapmaxdepthtype', 
	'@application_defaultmapmaxmagnitudetype',
	'@application_defaultmapmindaterelativeunits',
	'@application_defaultmapmindatetype', 
	'@application_defaultmapmindepthtype', 
	'@application_defaultmapminmagnitudetype',
	@application_defaultmapstyle_id,
	'@application_defaultmapname',
	'@application_legendsdirectory');


/* Styles */

INSERT INTO style(id, name, sld, inapplicationindex, application_id)
  VALUES (1, 'Círculos - Color según magnitud', 'seismap_circles-color-by-magnitude', 0, 1);

INSERT INTO style(id, name, sld, inapplicationindex, application_id)
  VALUES (2, 'Círculos - Color según antiguedad', 'seismap_circles-color-by-age', 1, 1);

INSERT INTO style(id, name, sld, inapplicationindex, application_id)
  VALUES (3, 'Círculos - Color según profundidad', 'seismap_circles-color-by-depth', 2, 1);

INSERT INTO style(id, name, sld, inapplicationindex, application_id)
  VALUES (4, 'Puntos - Color según magnitud', 'seismap_dots-color-by-magnitude', 3, 1);

INSERT INTO style(id, name, sld, inapplicationindex, application_id)
  VALUES (5, 'Puntos - Color según antiguedad', 'seismap_dots-color-by-age', 4, 1);

INSERT INTO style(id, name, sld, inapplicationindex, application_id)
  VALUES (6, 'Puntos  - Color según profundidad', 'seismap_dots-color-by-depth', 5, 1);


/* Magnitude limits */
  
INSERT INTO magnitudelimits(magnitudetype, max, min)
    VALUES ('MB', 0, 10);

INSERT INTO magnitudelimits(magnitudetype, max, min)
    VALUES ('MS', 0, 10);

	INSERT INTO magnitudelimits(magnitudetype, max, min)
    VALUES ('MW', 0, 10);

INSERT INTO magnitudelimits(magnitudetype, max, min)
    VALUES ('MBLG', 0, 10);

	INSERT INTO magnitudelimits(magnitudetype, max, min)
    VALUES ('MC', 0, 10);

INSERT INTO magnitudelimits(magnitudetype, max, min)
    VALUES ('ML', 0, 10);

COMMIT;
