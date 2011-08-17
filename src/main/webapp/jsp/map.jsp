<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="head.jsp">
  <c:param name="title" value="" />
  <c:param name="page" value="mapPage" />
</c:import>
<script type="text/javascript"
  src="http://maps.google.com/maps/api/js?sensor=true"></script>
<!-- script type="text/javascript">
    document.write('<script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/tags/markermanager/1.0/src/markermanager' + (document.location.search.indexOf('packed') > -1 ? '_packed' : '') + '.js"><' + '/script>');
</script-->
<script type="text/javascript">
    mapPage.setMapData(${map_json});
    mapPage.setLayerServerUri('${layerServerUri}');
</script>

</head>
<body>
  <c:import url="begin.jsp" />
  <div id="map"></div>
  <c:import url="end.jsp" />
<textarea id="mapUris" style="width: 90%; height: 100px"></textarea>
</body>
</html>
