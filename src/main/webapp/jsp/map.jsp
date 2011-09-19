<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"
  scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:import url="head.jsp">
  <c:param name="title" value="" />
  <c:param name="page" value="mapPage" />
</c:import>
<!-- script src="http://maps.google.com/maps?file=api&amp;v=2.93&amp;key=ABQIAAAAPaKjvz57Y0S6WBMn1VBY3hRxOXTwvMiggV2cS06R5a_zKRpeehRKur1ZfEiN6cw-_RBbJpD6Wj7a9Q" type="text/javascript"></script -->
<script src="http://maps.googleapis.com/maps/api/js?sensor=false&key=ABQIAAAAZGb76tUmuhDhmzTOt03vvRQt9cMzzFoKpLa_OvQBEImBmCSethSshEsXz5lvNUCN_Qu7yA8uRituCQ" type="text/javascript"></script>
<!-- script type="text/javascript"
  src="http://maps.google.com/maps/api/js?sensor=false&key=ABQIAAAAZGb76tUmuhDhmzTOt03vvRRhsCxakqqB0zgBij64DyszH1gi_xRTukyHJrLy2LbxygBxNBRYVdXV-g"></script -->
<!-- script type="text/javascript">
    document.write('<script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/tags/markermanager/1.0/src/markermanager' + (document.location.search.indexOf('packed') > -1 ? '_packed' : '') + '.js"><' + '/script>');
</script-->
<!--  link rel="stylesheet" type="text/css" href="${baseUrl}/resources/css/lib/openlayers/default/style.css"/ --> 
<script type="text/javascript"
  src="${baseUrl}/resources/js/lib/openlayers/OpenLayers.js"></script>

<script type="text/javascript">
  mapPage.setMapData(${map_json});
  mapPage.setDataBounds(${dataBounds_json});
  mapPage.setStyles(${styles_json});
  mapPage.setMagnitudeLimits(${magnitudeLimits_json});
  mapPage.setLayerServerUri('${layerServerUri}');
</script>

</head>
<body>
  <c:import url="begin.jsp" />
  <table><tr><td>
        <div id="map"> 
        </div> 
        <div id="wrapper"> 
            <div id="location">location</div> 
            <div id="scale"> 
            </div> 
        </div> 
  </td><td>
  <div id="mapControls">
    <form name="mapConfiguration" autocomplete="off">
      <table>
        <tr>
          <td>Nombre</td>
          <td><input name="name" value="${map.name}"/></td>
        </tr>
        <tr>
          <td>Descripci&oacute;n</td>
          <td><input name="description" value="${map.description}"/></td>
        </tr>
        <tr>
          <td>Centro en</td>
          <td>
            <table>
              <tr>
                <td>Latitud</td>
                <td><input name="centerLatitude" value="${map.centerLatitude}"/></td>
                <td>&ordm;</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td>Longitud</td>
                <td><input name="centerLongitude" value="${map.centerLongitude}"/></td>
                <td>&ordm;</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>Zoom:</td>
          <td><input name="zoom" value="${map.zoom}"/></td>
        </tr>
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td><a id="showInitialViewLink" href="#">Mostar vista inicial</a></td>
                <td><a id="useCurrentViewLink" href="#">Usar vista actual</a></td>
              </tr>
            </table>
          </td>
        </tr>
        <!-- Style -->
        <tr>
          <td>Estilo</td>
          <td>
            <select name="style">
              <c:forEach var="style" items="${styles}" >
              <option value="${style.id}" ${style.id ==  map.style.id ? 'selected="selected"' : ''}>${style.name}</option>
              </c:forEach>
            </select>
          </td>
        </tr>
        <!-- Min Date -->
        <tr>
          <td>Desde</td>
          <td>
            <table>
              <tr>
                <td><input name="minDateType" type="radio" value="NONE" ${map.minDateType == 'NONE' ? 'checked="checked"' : ''}/></td>
                <td>Siempre</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td><input name="minDateType" type="radio" value="RELATIVE" ${map.minDateType == 'RELATIVE' ? 'checked="checked"' : ''}/></td>
                <td>Hace</td>
                <td><input name="minDateRelativeAmount" value="${map.minDateRelativeAmount}"  ${map.minDateType != 'RELATIVE' ? 'disabled="disabled"' : ''}/></td>
                <td>
                  <select name="minDateRelativeUnits" ${map.minDateType != 'RELATIVE' ? 'disabled="disabled"' : ''}>
                    <option value="MINUTE" ${'MINUTE' ==  map.minDateRelativeUnits ? 'selected="selected"' : ''}>minutos</option>
                    <option value="HOUR" ${'HOUR' ==  map.minDateRelativeUnits ? 'selected="selected"' : ''}>horas</option>
                    <option value="DAY" ${'DAY' ==  map.minDateRelativeUnits ? 'selected="selected"' : ''}>dias</option>
                    <option value="WEEK" ${'WEEK' ==  map.minDateRelativeUnits ? 'selected="selected"' : ''}>semanas</option>
                    <option value="MONTH" ${'MONTH' ==  map.minDateRelativeUnits ? 'selected="selected"' : ''}>meses</option>
                    <option value="YEAR" ${'YEAR' ==  map.minDateRelativeUnits ? 'selected="selected"' : ''}>a&ntilde;os</option>
                  </select>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
          </td>
          <td>
            <table>
              <tr>
                <td><input name="minDateType" type="radio" value="ABSOLUTE" ${map.minDateType == 'ABSOLUTE' ? 'checked="checked"' : ''}/></td>
                <td><input name="minDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${map.minDate}" />" ${map.minDateType != 'ABSOLUTE' ? 'disabled="disabled"' : ''}/></td>
                <td><input name="minTime" value="<fmt:formatDate pattern="hh:mm:ss" value="${map.minDate}" />" ${map.minDateType != 'ABSOLUTE' ? 'disabled="disabled"' : ''}/></td>
              </tr>
            </table>
          </td>
        </tr>
        <!-- Max Date -->
        <tr>
          <td>Hasta</td>
          <td>
            <table>
              <tr>
                <td><input name="maxDateType" type="radio" value="NONE" ${map.maxDateType == 'NONE' ? 'checked="checked"' : ''}/></td>
                <td>Ahora</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td><input name="maxDateType" type="radio" value="RELATIVE" ${map.maxDateType == 'RELATIVE' ? 'checked="checked"' : ''}/></td>
                <td>Hace</td>
                <td><input name="maxDateRelativeAmount" value="${map.maxDateRelativeAmount}" ${map.maxDateType != 'RELATIVE' ? 'disabled="disabled"' : ''}/></td>
                <td>
                  <select name="maxDateRelativeUnits" ${map.maxDateType != 'RELATIVE' ? 'disabled="disabled"' : ''}>
                    <option value="MINUTE" ${'MINUTE' ==  map.maxDateRelativeUnits ? 'selected="selected"' : ''}>minutos</option>
                    <option value="HOUR" ${'HOUR' ==  map.maxDateRelativeUnits ? 'selected="selected"' : ''}>horas</option>
                    <option value="DAY" ${'DAY' ==  map.maxDateRelativeUnits ? 'selected="selected"' : ''}>dias</option>
                    <option value="WEEK" ${'WEEK' ==  map.maxDateRelativeUnits ? 'selected="selected"' : ''}>semanas</option>
                    <option value="MONTH" ${'MONTH' ==  map.maxDateRelativeUnits ? 'selected="selected"' : ''}>meses</option>
                    <option value="YEAR" ${'YEAR' ==  map.maxDateRelativeUnits ? 'selected="selected"' : ''}>a&ntilde;os</option>
                  </select>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td><input name="maxDateType" type="radio" value="ABSOLUTE" ${map.maxDateType == 'ABSOLUTE' ? 'checked="checked"' : ''}/></td>
                <td><input name="maxDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${map.maxDate}" />" ${map.maxDateType != 'ABSOLUTE' ? 'disabled="disabled"' : ''}/></td>
                <td><input name="maxTime" value="<fmt:formatDate pattern="hh:mm:ss" value="${map.maxDate}" />" ${map.maxDateType != 'ABSOLUTE' ? 'disabled="disabled"' : ''}/></td>
              </tr>
            </table>
          </td>
        </tr>
        <!-- Max Depth -->
        <tr>
          <td>Desde</td>
          <td>
            <table>
              <tr>
                <td><input name="maxDepthType" type="radio" value="NONE" ${map.maxDepthType == 'NONE' ? 'checked="checked"' : ''}/></td>
                <td>Lo m&aacute;s profundo</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td><input name="maxDepthType" type="radio" value="ABSOLUTE" ${map.maxDepthType == 'ABSOLUTE' ? 'checked="checked"' : ''}/></td>
                <td><input name="maxDepth" value="${map.maxDepth}" ${map.maxDepthType != 'ABSOLUTE' ? 'disabled="disabled"' : ''}/></td>
              </tr>
            </table>
          </td>
        </tr>
        <!-- Min Depth -->
        <tr>
          <td>Hasta</td>
          <td>
            <table>
              <tr>
                <td><input name="minDepthType" type="radio" value="NONE" ${map.minDepthType == 'NONE' ? 'checked="checked"' : ''}/></td>
                <td>La superficie</td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td><input name="minDepthType" type="radio" value="ABSOLUTE" ${map.minDepthType == 'ABSOLUTE' ? 'checked="checked"' : ''}/></td>
                <td><input name="minDepth" value="${map.minDepth}" ${map.minDepthType != 'ABSOLUTE' ? 'disabled="disabled"' : ''}/></td>
              </tr>
            </table>
          </td>
        </tr>
        <!-- Magnitude Type -->
        <tr>
          <td>Escala</td>
          <td>
            <select name="magnitudeType">
              <option value="RANK" ${'RANK' ==  map.magnitudeType ? 'selected="selected"' : ''}>Ranking</option>
              <option value="ML" ${'ML' ==  map.magnitudeType ? 'selected="selected"' : ''}>ML</option>
              <option value="MB" ${'MB' ==  map.magnitudeType ? 'selected="selected"' : ''}>MB</option>
              <option value="MS" ${'MS' ==  map.magnitudeType ? 'selected="selected"' : ''}>MS</option>
              <option value="MW" ${'MW' ==  map.magnitudeType ? 'selected="selected"' : ''}>MW</option>
              <option value="MBLG" ${'MBLG' ==  map.magnitudeType ? 'selected="selected"' : ''}>MBLG</option>
              <option value="MC" ${'MC' ==  map.magnitudeType ? 'selected="selected"' : ''}>MC</option>
            </select>
          </td>
        </tr>
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td><input name="listUnmeasured" type="checkbox" value="true" ${map.listUnmeasured ? 'checked="checked"' : ''}/></td>
                <td>Mostrar no medidos</td>
              </tr>
            </table>
          </td>
        </tr>
        <!-- Animation Type -->
        <tr>
          <td>Animar por</td>
          <td>
            <select name="animationType">
              <option value="NONE" ${'NONE' ==  map.animationType ? 'selected="selected"' : ''}>nada</option>
              <option value="DATE" ${'DATE' ==  map.animationType ? 'selected="selected"' : ''}>fecha</option>
              <option value="DEPTH" ${'DEPTH' ==  map.animationType ? 'selected="selected"' : ''}>profundidad</option>
              <option value="MAGNITUDE" ${'MAGNITUDE' ==  map.animationType ? 'selected="selected"' : ''}>magnitud</option>
            </select>
          </td>
        </tr>
        <!-- Animation Steps -->
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td>En</td>
                <td><input name="animationSteps" value="${map.animationSteps}" ${map.animationType == 'NONE' ? 'disabled="disabled"' : ''}/></td>
                <td>cuadros</td>
              </tr>
            </table>
          </td>
        </tr>
        <!-- Animation Step Duration -->
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td>De</td>
                <td><input name="animationStepDuration" value="${map.animationStepDuration}" ${map.animationType == 'NONE' ? 'disabled="disabled"' : ''}/></td>
                <td>segundos cada uno</td>
              </tr>
            </table>
          </td>
        </tr>
        <!-- Animation Step Keep -->
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td>Preservando el</td>
                <td><input name="animationStepKeep" value="${map.animationStepKeep * 100}" ${map.animationType == 'NONE' ? 'disabled="disabled"' : ''}/></td>
                <td>% del cuadro anterior</td>
              </tr>
            </table>
          </td>
        </tr>
        <!-- Reverse Animation -->
        <tr>
          <td></td>
          <td>
            <table>
              <tr>
                <td><input name="reverseAnimation" type="checkbox" value="true" ${map.reverseAnimation ? 'checked="checked"' : ''} ${map.animationType == 'NONE' ? 'disabled="disabled"' : ''}/></td>
                <td>Invertir animaci&oacute;n</td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form>
  </div>
  </td></tr></table>
  <button onclick="var v='Visible layers';for (var i=0;i<mapPage.layers.length;i++){if(mapPage.layers[i].getVisibility()) v+=' ' +i; } alert(v);">asd</button>
<textarea id="mapUris" style="width: 90%; height: 100px"></textarea>
  <c:import url="end.jsp" />
</body>
</html>
