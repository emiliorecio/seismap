import React, { useEffect, useState } from 'react';
import {
    Box,
    Drawer,
    AppBar,
    Toolbar,
    Typography,
    IconButton,
    Tabs,
    Tab,
    Tooltip,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import MapIcon from '@mui/icons-material/Map';
import AdminPanelSettingsIcon from '@mui/icons-material/AdminPanelSettings';
import { Link } from 'react-router-dom';
import SeismapMapView from './SeismapMapView';
import MapControlsPanel from './MapControlsPanel';
import SavedMapsPanel from './SavedMapsPanel';
import MapLegend from './MapLegend';
import { useMapStore } from '../store/mapStore';
import { mapService } from '../services/seismap';

const DRAWER_WIDTH = 320;

const MainLayout: React.FC = () => {
    const [open, setOpen] = useState(true);
    const [tab, setTab] = useState(0);
    const { currentMap, setCurrentMap, savedMaps, setSavedMaps, selectedStyle } = useMapStore();

    useEffect(() => {
        (async () => {
            try {
                const [defaultMap, maps] = await Promise.all([
                    mapService.getDefault(),
                    mapService.listByUser(),
                ]);
                if (defaultMap) setCurrentMap(defaultMap);
                setSavedMaps(maps);
            } catch (err) {
                console.error('Failed to load initial map data', err);
            }
        })();
    }, []);

    return (
        <Box sx={{ display: 'flex', height: '100vh', overflow: 'hidden' }}>
            <AppBar position="fixed" sx={{ zIndex: (t) => t.zIndex.drawer + 1 }}>
                <Toolbar variant="dense">
                    <IconButton color="inherit" onClick={() => setOpen(!open)} edge="start" sx={{ mr: 1 }}>
                        {open ? <ChevronLeftIcon /> : <MenuIcon />}
                    </IconButton>
                    <MapIcon sx={{ mr: 1 }} />
                    <Typography variant="h6" noWrap sx={{ flexGrow: 1 }}>
                        {currentMap?.name ?? 'Seismap'}
                    </Typography>
                    <Tooltip title="Administración">
                        <IconButton color="inherit" component={Link} to="/admin" size="small">
                            <AdminPanelSettingsIcon />
                        </IconButton>
                    </Tooltip>
                </Toolbar>
            </AppBar>

            <Drawer
                variant="persistent"
                open={open}
                sx={{
                    width: open ? DRAWER_WIDTH : 0,
                    flexShrink: 0,
                    transition: 'width 0.2s',
                    '& .MuiDrawer-paper': {
                        width: DRAWER_WIDTH,
                        boxSizing: 'border-box',
                        top: '40px',
                        height: 'calc(100% - 40px)',
                        display: 'flex',
                        flexDirection: 'column',
                    },
                }}
            >
                <Tabs
                    value={tab}
                    onChange={(_, v) => setTab(v)}
                    variant="fullWidth"
                    textColor="inherit"
                    indicatorColor="primary"
                    sx={{ borderBottom: 1, borderColor: 'divider', minHeight: 36 }}
                >
                    <Tab label="Filtros" sx={{ minHeight: 36, py: 0 }} />
                    <Tab label={`Mapas (${savedMaps.length})`} sx={{ minHeight: 36, py: 0 }} />
                </Tabs>
                <Box sx={{ flex: 1, overflowY: 'auto' }}>
                    {tab === 0 && <MapControlsPanel />}
                    {tab === 1 && <SavedMapsPanel />}
                </Box>
            </Drawer>

            <Box
                component="main"
                sx={{
                    flexGrow: 1,
                    height: '100vh',
                    pt: '40px',
                    transition: 'margin 0.2s',
                    ml: open ? `${DRAWER_WIDTH}px` : 0,
                    position: 'relative',
                }}
            >
                <SeismapMapView
                    centerLon={currentMap?.center?.x ?? -65}
                    centerLat={currentMap?.center?.y ?? -32}
                    zoom={currentMap?.zoom ?? 5}
                    currentMap={currentMap}
                    styleName={selectedStyle}
                />
                <MapLegend styleName={selectedStyle} />
            </Box>
        </Box>
    );
};

export default MainLayout;
