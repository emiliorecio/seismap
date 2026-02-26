import React, { useEffect, useRef, useState } from 'react';
import {
    Dialog, DialogTitle, DialogContent, DialogActions,
    Button, Typography, Table, TableBody, TableCell,
    TableContainer, TableHead, TableRow, Paper, Chip, Box, TablePagination, Tabs, Tab
} from '@mui/material';
import type { Page } from '../services/seismap';
import PlaceIcon from '@mui/icons-material/Place';
import { toLonLat } from 'ol/proj';
import Map from 'ol/Map';
import View from 'ol/View';
import ImageLayer from 'ol/layer/Image';
import ImageWMS from 'ol/source/ImageWMS';
import WKT from 'ol/format/WKT';
import { useMapStore } from '../store/mapStore';
import { buildCqlFilter } from '../utils/cqlFilter';

export interface EventSummary {
    id: number;
    date: string;
    depth: number;
    latitude: number;
    longitude: number;
    name?: string;
    reference?: string;
    rankMagnitude?: number;
}

interface Props {
    open: boolean;
    eventsPage: Page<EventSummary> | null;
    wkt: string | null;
    onClose: () => void;
    onClearPolygon: () => void;
    onPageChange: (newPage: number) => void;
}

function formatDate(iso: string) {
    return new Date(iso).toLocaleString('es-AR', {
        year: 'numeric', month: '2-digit', day: '2-digit',
        hour: '2-digit', minute: '2-digit',
    });
}

const EventsWithinDialog: React.FC<Props> = ({ open, eventsPage, wkt, onClose, onClearPolygon, onPageChange }) => {
    const [tab, setTab] = useState(0);
    const mapRef = useRef<HTMLDivElement>(null);
    const mapInstance = useRef<Map | null>(null);

    const { currentMap } = useMapStore();

    const handleClear = () => {
        onClearPolygon();
        onClose();
        setTab(0);
    };

    // Reset tab on close
    useEffect(() => {
        if (!open) {
            setTab(0);
        }
    }, [open]);

    useEffect(() => {
        if (tab !== 1 || !mapRef.current || !open || !wkt) return;

        // WKT bbox calculation and CQL conversion
        // The wkt from the map is natively EPSG:3857, which matches GeoServer's location column (EPSG:900913)
        const format = new WKT();
        const feature = format.readFeature(wkt);

        const cqlWkt = wkt;
        let mapExtent: number[] | undefined;

        const geom = feature.getGeometry();
        if (geom) {
            const extent = geom.getExtent();
            if (extent && extent.length === 4 && extent.every(isFinite)) {
                let minX = extent[0];
                let maxX = extent[2];
                if (minX >= maxX) {
                    minX -= 1000;
                    maxX += 1000;
                }
                // For the depth map, X is longitude *in meters* (EPSG:3857), Y is depth *in meters* (negative)
                // mapExtent defines the view viewport bounds in map units
                mapExtent = [minX, -750000, maxX, 0];
            }
        }

        // Base filters from Map Controls 
        const cqlParts: string[] = [];
        cqlParts.push(`WITHIN(location, ${cqlWkt})`);

        if (currentMap) {
            const mapCql = buildCqlFilter(currentMap);
            if (mapCql) {
                cqlParts.push(`(${mapCql})`);
            }
        }

        const cqlFilter = cqlParts.join(' AND ');

        if (!mapInstance.current) {
            const wmsSource = new ImageWMS({
                url: '/geoserver/seismap/wms',
                params: {
                    'LAYERS': `seismap:eventandaveragemagnitudes_depthlocation`,
                    'CQL_FILTER': cqlFilter,
                    'STYLES': `seismap_circles_depth`, // Enforce depth coloring for depth map
                    'SRS': 'EPSG:3857'
                },
                serverType: 'geoserver',
            });

            const layer = new ImageLayer({
                source: wmsSource,
            });

            mapInstance.current = new Map({
                target: mapRef.current,
                layers: [layer],
                view: new View({
                    projection: 'EPSG:3857',
                    center: [0, -375000],
                    zoom: 2,
                    showFullExtent: true,
                }),
            });
        } else {
            // Update filter
            const layers = mapInstance.current.getLayers().getArray();
            if (layers.length > 0) {
                const source = (layers[0] as ImageLayer<ImageWMS>).getSource();
                source?.updateParams({ 'CQL_FILTER': cqlFilter, 'STYLES': `seismap_circles_depth` });
            }
        }

        if (mapExtent) {
            try {
                // Ensure container size is updated before fitting
                setTimeout(() => {
                    if (mapInstance.current) {
                        mapInstance.current.updateSize();
                        mapInstance.current.getView().fit(mapExtent!, { padding: [20, 20, 20, 20] });
                    }
                }, 100);
            } catch (e) {
                console.warn('Could not fit extent for depth map:', e);
            }
        }

        return () => {
            // Let the instance persist during tab switching if possible to avoid flickering,
            // or destroy it cleanly. Since the div refs unmounts when tab changes, we should clean up.
            if (mapInstance.current) {
                mapInstance.current.setTarget(undefined);
                mapInstance.current = null;
            }
        };
    }, [tab, open, wkt, currentMap]);


    return (
        <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth
            PaperProps={{ sx: { bgcolor: 'background.paper', height: '80vh', display: 'flex', flexDirection: 'column' } }}>
            <DialogTitle sx={{ display: 'flex', alignItems: 'center', gap: 1, pb: 1 }}>
                <PlaceIcon color="primary" />
                Eventos en el área seleccionada
                <Chip label={eventsPage?.totalElements || 0} size="small" color="primary" sx={{ ml: 'auto' }} />
            </DialogTitle>

            <Tabs
                value={tab}
                onChange={(_, v) => setTab(v)}
                variant="fullWidth"
                textColor="primary"
                indicatorColor="primary"
                sx={{ borderBottom: 1, borderColor: 'divider' }}
            >
                <Tab label="Lista de Eventos" />
                <Tab label="Corte Transversal" />
            </Tabs>

            <DialogContent dividers sx={{ p: 0, overflow: 'hidden', flex: 1, display: 'flex', flexDirection: 'column' }}>
                {tab === 0 && (
                    <Box sx={{ flex: 1, display: 'flex', flexDirection: 'column', overflow: 'hidden' }}>
                        {!eventsPage || eventsPage.content.length === 0 ? (
                            <Box sx={{ p: 4, textAlign: 'center' }}>
                                <Typography color="text.secondary">
                                    No se encontraron eventos en esta área.
                                </Typography>
                            </Box>
                        ) : (
                            <TableContainer component={Paper} elevation={0} sx={{ flex: 1, overflowY: 'auto' }}>
                                <Table size="small" stickyHeader>
                                    <TableHead>
                                        <TableRow>
                                            <TableCell>Fecha</TableCell>
                                            <TableCell align="right">Prof. (km)</TableCell>
                                            <TableCell align="right">Lat</TableCell>
                                            <TableCell align="right">Lon</TableCell>
                                            <TableCell>Nombre / Referencia</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {eventsPage.content.map(ev => {
                                            const [lonDeg, latDeg] = toLonLat([ev.longitude, ev.latitude], 'EPSG:3857');
                                            return (
                                                <TableRow key={ev.id} hover>
                                                    <TableCell sx={{ whiteSpace: 'nowrap' }}>
                                                        {formatDate(ev.date)}
                                                    </TableCell>
                                                    <TableCell align="right">{ev.depth.toFixed(1)}</TableCell>
                                                    <TableCell align="right">{latDeg.toFixed(4)}</TableCell>
                                                    <TableCell align="right">{lonDeg.toFixed(4)}</TableCell>
                                                    <TableCell>
                                                        <Typography variant="body2" noWrap>
                                                            {ev.name || ev.reference || '—'}
                                                        </Typography>
                                                    </TableCell>
                                                </TableRow>
                                            );
                                        })}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        )}
                        {eventsPage && eventsPage.totalElements > 0 && (
                            <TablePagination
                                component="div"
                                count={eventsPage.totalElements}
                                page={eventsPage.number}
                                onPageChange={(_, newPage) => onPageChange(newPage)}
                                rowsPerPage={eventsPage.size}
                                rowsPerPageOptions={[]} // keep fixed size to avoid complex logic
                            />
                        )}
                    </Box>
                )}

                {tab === 1 && (
                    <Box sx={{ flex: 1, display: 'flex', flexDirection: 'column', position: 'relative' }}>
                        <Box sx={{ p: 1, bgcolor: '#f5f5f5', borderBottom: '1px solid #e0e0e0', textAlign: 'center' }}>
                            <Typography variant="body2" color="text.secondary">
                                Profundidad (km) v/s Longitud
                            </Typography>
                        </Box>
                        <Box
                            ref={mapRef}
                            sx={{ flex: 1, width: '100%', bgcolor: '#ffffff' }}
                        />
                    </Box>
                )}
            </DialogContent>

            <DialogActions>
                <Button onClick={handleClear} color="warning" variant="outlined" size="small">
                    Limpiar selección
                </Button>
                <Button onClick={onClose} variant="contained" size="small">
                    Cerrar
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default EventsWithinDialog;
export type { EventSummary as EventSummaryType };
