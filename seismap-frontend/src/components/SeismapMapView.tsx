import React, { useEffect, useRef } from 'react';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import { fromLonLat } from 'ol/proj';
import 'ol/ol.css';

interface SeismapMapViewProps {
    centerLon?: number;
    centerLat?: number;
    zoom?: number;
}

const SeismapMapView: React.FC<SeismapMapViewProps> = ({
    centerLon = -65,
    centerLat = -32,
    zoom = 5,
}) => {
    const mapRef = useRef<HTMLDivElement>(null);
    const olMapRef = useRef<Map | null>(null);

    useEffect(() => {
        if (!mapRef.current) return;

        const map = new Map({
            target: mapRef.current,
            layers: [
                new TileLayer({
                    source: new OSM(),
                }),
            ],
            view: new View({
                center: fromLonLat([centerLon, centerLat]),
                zoom,
            }),
        });

        olMapRef.current = map;

        return () => {
            map.setTarget(undefined);
            olMapRef.current = null;
        };
    }, []);

    return (
        <div
            ref={mapRef}
            style={{ width: '100%', height: '100%' }}
        />
    );
};

export default SeismapMapView;
