seismap = {};
seismap.constants = {};
seismap.constants.googleProjectionLimits = 20037508.34;
seismap.constants.eventMapZoom = 7;
seismap.constants.layerName = 'seismap:eventandaveragemagnitudes';
seismap.constants.dateUnits = [ {
  id : 'MINUTE',
  name : 'Minutos'
}, {
  id : 'HOUR',
  name : 'Horas'
}, {
  id : 'DAY',
  name : 'Días'
}, {
  id : 'WEEK',
  name : 'Semanas'
}, {
  id : 'MONTH',
  name : 'Meses'
}, {
  id : 'YEAR',
  name : 'Años'
} ];

seismap.constants.magnitudeTypes = [ {
  id : 'RANK',
  name : 'Seismap Rank'
}, {
  id : 'ML',
  name : 'Magnitud de Richter/Local (ML)'
}, {
  id : 'MB',
  name : 'Magnitud de cuerpo de onda (Mb)'
}, {
  id : 'MS',
  name : 'Magnitud de onda superficial (Ms)'
}, {
  id : 'MW',
  name : 'Magnitud de momento (MMS/Mw)'
}, {
  id : 'MBLG',
  name : 'Magnitud logarítmica de cuerpo de onda (MbLg)'
}, {
  id : 'MC',
  name : 'Magnitud de Mercalli (MC/MM/MMI)'
} ];

Ext.data.SortTypes.asNullableFloat = function(s) {
  var val = parseFloat(String(s).replace(/,/g, ""));
  return isNaN(val) ? Number.NEGATIVE_INFINITY : val;
};

Ext.data.SortTypes.asNullableInt = function(s) {
  var val = parseInt(String(s).replace(/,/g, ""), 10);
  return isNaN(val) ? Number.NEGATIVE_INFINITY : val;
};