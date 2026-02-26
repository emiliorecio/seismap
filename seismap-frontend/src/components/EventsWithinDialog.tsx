import React from 'react';
import {
    Dialog, DialogTitle, DialogContent, DialogActions,
    Button, Typography, Table, TableBody, TableCell,
    TableContainer, TableHead, TableRow, Paper, Chip, Box, TablePagination
} from '@mui/material';
import type { Page } from '../services/seismap';
import PlaceIcon from '@mui/icons-material/Place';
import { toLonLat } from 'ol/proj';

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

const EventsWithinDialog: React.FC<Props> = ({ open, eventsPage, onClose, onClearPolygon, onPageChange }) => {
    const handleClear = () => {
        onClearPolygon();
        onClose();
    };

    return (
        <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth
            PaperProps={{ sx: { bgcolor: 'background.paper' } }}>
            <DialogTitle sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                <PlaceIcon color="primary" />
                Eventos en el área seleccionada
                <Chip label={eventsPage?.totalElements || 0} size="small" color="primary" sx={{ ml: 'auto' }} />
            </DialogTitle>

            <DialogContent dividers sx={{ p: 0 }}>
                {!eventsPage || eventsPage.content.length === 0 ? (
                    <Box sx={{ p: 4, textAlign: 'center' }}>
                        <Typography color="text.secondary">
                            No se encontraron eventos en esta área.
                        </Typography>
                    </Box>
                ) : (
                    <TableContainer component={Paper} elevation={0}>
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
