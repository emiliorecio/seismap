START TRANSACTION;
SET CONSTRAINTS ALL DEFERRED;

/* Application */
set @application_id =                                  1;
set @application_settingsCacheExpiration =             5000;
set @application_layerserveruri =                      'http://localhost:8080/geoserver';
set @application_legendsdirectory =                    'C:/user/workspace/seismap/src/main/sld/';
set @application_googleMapsApiKey =                    '';
set @application_eventMapZoom =                        7;
set @application_layerName =                           'eventandaveragemagnitudes';
set @application_depthLayerName =                      'eventandaveragemagnitudes_depthlocation';
set @application_affectedDistanceStyleName =           'seismap_affected-distance';
	
/* Map */
set @application_defaultmapname =                      'Nuevo mapa';
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
set @application_defaultmapmaxdatetype =               'NONE';
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
set @application_defaultmapminmagnitudetype =          'NONE';
set @application_defaultmapminmagnitude =              0;

/* Max magnitude */
set @application_defaultmapmaxmagnitudetype =          'NONE';
set @application_defaultmapmaxmagnitude =              10;

/* Animation */
set @application_defaultmapanimationtype =             'NONE';
set @application_defaultmapanimationstepduration =     5;
set @application_defaultmapanimationstepkeep =         0;
set @application_defaultmapanimationsteps =            10;
set @application_defaultmapreverseanimation =          'false';

INSERT INTO application(
	id,
	settingscacheexpiration,
	googlemapsapikey,
    eventmapzoom,
    layername,
    depthlayername,
    affecteddistancestylename,
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
	@application_settingsCacheExpiration,
	'@application_googleMapsApiKey',
    @application_eventMapZoom,
    '@application_layerName',
    '@application_depthLayerName',
    '@application_affectedDistanceStyleName',
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
  VALUES (1, 'Circulos - Color segun magnitud', 'seismap_circles-color-by-magnitude', 0, 1);

INSERT INTO style(id, name, sld, inapplicationindex, application_id)
  VALUES (2, 'Circulos - Color segun antiguedad', 'seismap_circles-color-by-age', 1, 1);

INSERT INTO style(id, name, sld, inapplicationindex, application_id)
  VALUES (3, 'Circulos - Color segun profundidad', 'seismap_circles-color-by-depth', 2, 1);

INSERT INTO style(id, name, sld, inapplicationindex, application_id)
  VALUES (4, 'Puntos - Color segun magnitud', 'seismap_dots-color-by-magnitude', 3, 1);

INSERT INTO style(id, name, sld, inapplicationindex, application_id)
  VALUES (5, 'Puntos - Color segun antiguedad', 'seismap_dots-color-by-age', 4, 1);

INSERT INTO style(id, name, sld, inapplicationindex, application_id)
  VALUES (6, 'Puntos  - Color segun profundidad', 'seismap_dots-color-by-depth', 5, 1);


/* Magnitude limits */
  
INSERT INTO magnitudelimits(magnitudetype, min, max)
    VALUES ('MB', 0, 6.5);

INSERT INTO magnitudelimits(magnitudetype, min, max)
    VALUES ('MS', 1, 10);

	INSERT INTO magnitudelimits(magnitudetype, min, max)
    VALUES ('MW', 2, 10);

INSERT INTO magnitudelimits(magnitudetype, min, max)
    VALUES ('MBLG', 0, 10);

	INSERT INTO magnitudelimits(magnitudetype, min, max)
    VALUES ('MC', 1, 12);

INSERT INTO magnitudelimits(magnitudetype, min, max)
    VALUES ('ML', 2, 10);

/* Admin user */

INSERT INTO seismapuser(id, email, name, passwordhash, administrator)
    VALUES (1, 'admin@seismap.com', 'Admin', '', true);
	
COMMIT;
