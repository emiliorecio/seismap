<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="baseUri" value="${pageContext.request.contextPath}" scope="request" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Seismap <c:if test="${fn:length(param.title) > 0}"> - <c:out
      value="${param.title}" />
  </c:if></title>
<link rel="shortcut icon" href="${baseUri}/resources/icon/seismap.ico"
  type="image/x-icon"></link>
<link rel="stylesheet" href="${baseUri}/resources/css/seismap.css"
  type="text/css"></link>
<script type="text/javascript"
  src="http://maps.googleapis.com/maps/api/js?sensor=false&key=${applicationSettings.googleMapsApiKey}"
  type="text/javascript">
</script>
<script type="text/javascript"
  src="${baseUri}/resources/js/lib/openlayers/OpenLayers-debug.js"></script>
<link rel="stylesheet" type="text/css"
  href="${baseUri}/resources/css/lib/extjs/css/ext-all.css" />
<script type="text/javascript"
  src="${baseUri}/resources/js/lib/extjs/ext-base-debug.js"></script>
<script type="text/javascript"
  src="${baseUri}/resources/js/lib/extjs/ext-all-debug.js"></script>
<script type="text/javascript"
  src="${baseUri}/resources/js/lib/geoext/GeoExt-debug.js"></script>
<script type="text/javascript" src="${baseUri}/resources/js/constants.js"></script>
<script type="text/javascript">
  seismap.constants.user = {id: 1, name: 'UnUsuario', administrator: true};
  seismap.constants.baseUri = '${baseUri}';
  seismap.constants.styles = ${styles_json};
  seismap.constants.dataBounds = ${dataBounds_json};
  seismap.constants.dataBounds.minDate = seismap.constants.dataBounds.minDate == null ? null : new Date(seismap.constants.dataBounds.minDate);
  seismap.constants.dataBounds.maxDate = seismap.constants.dataBounds.maxDate == null ? null : new Date(seismap.constants.dataBounds.maxDate);
  seismap.constants.layerServerUri= '${baseUri}/layerServer/';
  seismap.constants.settings = ${applicationSettings_json};
  seismap.constants.magnitudeLimits = ${magnitudeLimits_json};
</script>
<script type="text/javascript" src="${baseUri}/resources/js/DateUnitsStore.js"></script>
<script type="text/javascript"
  src="${baseUri}/resources/js/MagnitudeTypeStore.js"></script>
<script type="text/javascript" src="${baseUri}/resources/js/StyleStore.js"></script>
<script type="text/javascript" src="${baseUri}/resources/js/EventStore.js"></script>
<script type="text/javascript" src="${baseUri}/resources/js/LocationEventStore.js"></script>
<script type="text/javascript" src="${baseUri}/resources/js/EventMagnitudeStore.js"></script>
<script type="text/javascript"
  src="${baseUri}/resources/js/SeismapViewport.ui.js"></script>
<script type="text/javascript" src="${baseUri}/resources/js/seismap.js"></script>
<script type="text/javascript" src="${baseUri}/resources/js/xds_index.js"></script>
<script type="text/javascript">
  seismap.ui.setMapData(${map_json});
</script>
</head>
<body></body>
</html>