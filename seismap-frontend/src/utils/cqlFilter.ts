import type { SeismapMap } from '../types/map';

/**
 * Build a CQL_FILTER string from the current map filter state.
 * Returns an empty string when no filters are active.
 *
 * GeoServer CQL reference:
 *   https://docs.geoserver.org/stable/en/user/filter/ecql_reference.html
 */
export function buildCqlFilter(map: SeismapMap): string {
    const clauses: string[] = [];

    // ── Date filters ──────────────────────────────────────────────────
    if (map.minDateType === 'ABSOLUTE' && map.minDate) {
        clauses.push(`date >= '${map.minDate}'`);
    } else if (map.minDateType === 'RELATIVE' && map.minDateRelativeAmount > 0) {
        const ms = relativeToMs(map.minDateRelativeAmount, map.minDateRelativeUnits);
        const iso = new Date(Date.now() - ms).toISOString();
        clauses.push(`date >= '${iso}'`);
    }

    if (map.maxDateType === 'ABSOLUTE' && map.maxDate) {
        clauses.push(`date <= '${map.maxDate}'`);
    } else if (map.maxDateType === 'RELATIVE' && map.maxDateRelativeAmount > 0) {
        const ms = relativeToMs(map.maxDateRelativeAmount, map.maxDateRelativeUnits);
        const iso = new Date(Date.now() - ms).toISOString();
        clauses.push(`date <= '${iso}'`);
    }

    // ── Depth filters ─────────────────────────────────────────────────
    if (map.minDepthType === 'ABSOLUTE') {
        clauses.push(`depth >= ${map.minDepth}`);
    }
    if (map.maxDepthType === 'ABSOLUTE') {
        clauses.push(`depth <= ${map.maxDepth}`);
    }

    // ── Magnitude filters ─────────────────────────────────────────────
    if (map.minMagnitudeType === 'ABSOLUTE') {
        clauses.push(`rankmagnitude >= ${map.minMagnitude}`);
    }
    if (map.maxMagnitudeType === 'ABSOLUTE') {
        clauses.push(`rankmagnitude <= ${map.maxMagnitude}`);
    }

    return clauses.join(' AND ');
}

/** Convert a relative amount + unit to milliseconds. */
function relativeToMs(amount: number, unit: string): number {
    switch (unit) {
        case 'MINUTE': return amount * 60_000;
        case 'HOUR':   return amount * 3_600_000;
        case 'DAY':    return amount * 86_400_000;
        case 'WEEK':   return amount * 604_800_000;
        case 'MONTH':  return amount * 2_592_000_000; // ~30 days
        case 'YEAR':   return amount * 31_536_000_000; // ~365 days
        default:       return amount * 86_400_000;
    }
}
