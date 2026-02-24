package com.seismap.dto;

public class PolygonQueryRequest {
    /** WKT polygon string in EPSG:900913, e.g. POLYGON((x1 y1, x2 y2, ...)) */
    private String wkt;

    public PolygonQueryRequest() {
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }
}
