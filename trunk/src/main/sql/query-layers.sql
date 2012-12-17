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

sql view:
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