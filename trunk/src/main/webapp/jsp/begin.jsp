<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div id="globalContainer">
  <div id="container">
    <h1>Seismap</h1>
    <div id="appMenu">
      <ul>
        <li><a id="newCategory" href="#">Nueva categoria</a></li>
        <li><a id="newMap" href="#">Nuevo mapa</a></li>
      </ul>
    </div>
    <div id="categoriesMenu">
      <ul>
        <c:forEach items="${categories}" var="category">
          <li class="category">${category.name}</li>
          <ul>
            <c:forEach items="${category.maps}" var="map">
              <li class="map">${map.name}</li>
            </c:forEach>
          </ul>
        </c:forEach>
      </ul>
    </div>
    <div id="mapsMenu">
      <ul>
        <c:forEach items="${maps}" var="map">
          <li class="map"><a href="${baseUrl}/map/${map.id}">${map.name}</a></li>
        </c:forEach>
      </ul>
    </div>