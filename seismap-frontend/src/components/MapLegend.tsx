import React from 'react';
import { Box, Paper, Typography } from '@mui/material';

interface MapLegendProps {
    styleName: string;
}

/** Style labels for display in the legend title */
const STYLE_LABELS: Record<string, string> = {
    seismap_default: 'Por defecto',
    seismap_circles_magnitude: 'Círculos — Magnitud',
    seismap_circles_depth: 'Círculos — Profundidad',
    seismap_circles_age: 'Círculos — Antigüedad',
    seismap_points_magnitude: 'Puntos — Magnitud',
    seismap_points_depth: 'Puntos — Profundidad',
    seismap_points_age: 'Puntos — Antigüedad',
};

const MapLegend: React.FC<MapLegendProps> = ({ styleName }) => {
    if (styleName === 'seismap_default') return null;

    const legendUrl = `/api/maps/legend?name=${encodeURIComponent(styleName)}`;
    const label = STYLE_LABELS[styleName] ?? styleName;

    return (
        <Paper
            elevation={4}
            sx={{
                position: 'absolute',
                bottom: 24,
                right: 24,
                zIndex: 1000,
                p: 1.5,
                bgcolor: 'rgba(18, 18, 30, 0.88)',
                backdropFilter: 'blur(8px)',
                borderRadius: 2,
                minWidth: 120,
            }}
        >
            <Typography
                variant="caption"
                sx={{ color: 'grey.300', fontWeight: 600, mb: 0.5, display: 'block' }}
            >
                {label}
            </Typography>
            <Box
                component="img"
                src={legendUrl}
                alt="Leyenda del mapa"
                sx={{ display: 'block', maxWidth: 180 }}
                onError={(e: React.SyntheticEvent<HTMLImageElement>) => {
                    e.currentTarget.style.display = 'none';
                }}
            />
        </Paper>
    );
};

export default MapLegend;
