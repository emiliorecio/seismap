/*
name: seismap:eventandaveragemagnitudes
sql view
parameter:
 - Name: magnitudeType
 - Default value: ML
 - Validation regular expression: ^[\w]+$
Attributes:
- Identifier: id
- Geometry: SRID 900913

Native/Declared SRS: EPSG:900913
Native Bounding Box: -20037508.34 -20037508.34 20037508.34 20037508.34
Lat/Lon Bounding Box: -180 -85.05112877764509 180 85.05112877764509

sql:
*/
SELECT
    id as fid,
    location as pointlocation,
    *,
    %magnitudeType%magnitude AS magnitude,
    %magnitudeType%index AS index,
    date_part('epoch', now() - date) as age
  FROM eventandaveragemagnitudes

/*
name: seismap:eventandaveragemagnitudes_depthlocation
sql view
parameter:
 - Name: magnitudeType
 - Default value: ML
 - Validation regular expression: ^[\w]+$
Attributes:
- Identifier: id
- Geometry: SRID 900913

Native/Declared SRS: EPSG:900913
Native Bounding Box: -20037508.34 -20037508.34 20037508.34 20037508.34
Lat/Lon Bounding Box: -180 -85.05112877764509 180 85.05112877764509

sql view:
*/
SELECT
    id as fid,
    depthlocation as pointlocation,
    *,
    %magnitudeType%magnitude AS magnitude,
    %magnitudeType%index AS index,
    date_part('epoch', now() - date) as age
  FROM eventandaveragemagnitudes