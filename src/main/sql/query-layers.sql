SELECT
    id as fid,
    location as pointlocation,
    *,
    %magnitudeType%magnitude AS magnitude,
    %magnitudeType%index AS index,
    date_part('epoch', now() - date) as age
  FROM eventandaveragemagnitudes

SELECT
    id as fid,
    depthlocation as pointlocation,
    *,
    %magnitudeType%magnitude AS magnitude,
    %magnitudeType%index AS index,
    date_part('epoch', now() - date) as age
  FROM eventandaveragemagnitudes