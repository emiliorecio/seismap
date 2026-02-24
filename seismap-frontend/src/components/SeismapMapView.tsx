import React, { useEffect, useRef } from 'react';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import ImageLayer from 'ol/layer/Image';
import OSM from 'ol/source/OSM';
import ImageWMS from 'ol/source/ImageWMS';
import { fromLonLat } from 'ol/proj';
import 'ol/ol.css';
import type { SeismapMap } from '../types/map';
import { buildCqlFilter } from '../utils/cqlFilter';

interface SeismapMapViewProps {
    centerLon?: number;
    centerLat?: number;
    zoom?: number;
    currentMap?: SeismapMap | null;
    styleName?: string;
}

const GEOSERVER_WMS_URL = '/geoserver/seismap/wms';
const LAYER_NAME = 'seismap:eventandaveragemagnitudes';

const SeismapMapView: React.FC<SeismapMapViewProps> = ({
    centerLon = -65,
    centerLat = -32,
    zoom = 5,
    currentMap = null,
    styleName = 'seismap_default',
}) => {
    const mapRef = useRef<HTMLDivElement>(null);
    const olMapRef = useRef<Map | null>(null);
    const wmsLayerRef = useRef<ImageLayer<ImageWMS> | null>(null);

    // ── Initialize map + WMS layer ─────────────────────────────────
    useEffect(() => {
        if (!mapRef.current) return;

        // ImageWMS sends ONE request per viewport instead of many tiles,
        // avoiding GeoServer 429 rate limiting errors.
        const wmsSource = new ImageWMS({
            url: GEOSERVER_WMS_URL,
            params: {
                LAYERS: LAYER_NAME,
                SRS: 'EPSG:900913',
                STYLES: styleName,
                CQL_FILTER: currentMap ? buildCqlFilter(currentMap) || undefined : undefined,
            },
            serverType: 'geoserver',
            ratio: 1,
        });

        const wmsLayer = new ImageLayer({
            source: wmsSource,
            opacity: 0.85,
        });

        wmsLayerRef.current = wmsLayer;

        const map = new Map({
            target: mapRef.current,
            layers: [
                new TileLayer({ source: new OSM() }),
                wmsLayer,
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
            wmsLayerRef.current = null;
        };
    }, []);

    // ── Update WMS params when filters or style change ─────────────
    useEffect(() => {
        const source = wmsLayerRef.current?.getSource();
        if (!source) return;

        const cql = currentMap ? buildCqlFilter(currentMap) : '';
        source.updateParams({
            STYLES: styleName,
            CQL_FILTER: cql || undefined,
        });
    }, [
        currentMap?.minDateType, currentMap?.minDate,
        currentMap?.minDateRelativeAmount, currentMap?.minDateRelativeUnits,
        currentMap?.maxDateType, currentMap?.maxDate,
        currentMap?.maxDateRelativeAmount, currentMap?.maxDateRelativeUnits,
        currentMap?.minDepthType, currentMap?.minDepth,
        currentMap?.maxDepthType, currentMap?.maxDepth,
        currentMap?.minMagnitudeType, currentMap?.minMagnitude,
        currentMap?.maxMagnitudeType, currentMap?.maxMagnitude,
        styleName,
    ]);

    return (
        <div
            ref={mapRef}
            style={{ width: '100%', height: '100%', position: 'relative' }}
        />
    );
};

export default SeismapMapView;
