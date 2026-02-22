import { create } from 'zustand';
import type { SeismapMap, DataBounds } from '../types/map';

interface MapStore {
    currentMap: SeismapMap | null;
    savedMaps: SeismapMap[];
    dataBounds: DataBounds | null;
    setCurrentMap: (map: SeismapMap) => void;
    setSavedMaps: (maps: SeismapMap[]) => void;
    setDataBounds: (bounds: DataBounds) => void;
    updateCurrentMap: (patch: Partial<SeismapMap>) => void;
}

export const useMapStore = create<MapStore>((set) => ({
    currentMap: null,
    savedMaps: [],
    dataBounds: null,

    setCurrentMap: (map) => set({ currentMap: map }),
    setSavedMaps: (maps) => set({ savedMaps: maps }),
    setDataBounds: (bounds) => set({ dataBounds: bounds }),
    updateCurrentMap: (patch) =>
        set((state) =>
            state.currentMap ? { currentMap: { ...state.currentMap, ...patch } } : {}
        ),
}));
