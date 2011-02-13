
var map = null;

function selectCapital() {
  lng = parseFloat(document.getElementById("capitals").value.split(",")[0]);
  lat = parseFloat(document.getElementById("capitals").value.split(",")[1]);
  scale = parseInt(document.getElementById("capitals").value.split(",")[2]);
  map.setCenter(new GLatLng(lat,lng),scale);
}

function selectCountry() {
  lng = parseFloat(document.getElementById("countries").value.split(",")[0]);
  lat = parseFloat(document.getElementById("countries").value.split(",")[1]);
  scale = parseInt(document.getElementById("countries").value.split(",")[2]);
  map.setCenter(new GLatLng(lat,lng),scale);
}

function selectVolcanoes() {
  lng = parseFloat(document.getElementById("volcanoes").value.split(",")[0]);
  lat = parseFloat(document.getElementById("volcanoes").value.split(",")[1]);
  scale = parseInt(document.getElementById("volcanoes").value.split(",")[2]);
  map.setCenter(new GLatLng(lat,lng),scale);
}

function selectLocation() {
  lng = parseFloat(document.getElementById("locations").value.split(",")[0]);
  lat = parseFloat(document.getElementById("locations").value.split(",")[1]);
  scale = parseInt(document.getElementById("locations").value.split(",")[2]);
  map.setCenter(new GLatLng(lat,lng),scale);
}

function load() {    
  if (GBrowserIsCompatible()) { 

// var parameter bzw. wikipedia coords auswerten (?var=...)

    var lat = 37;
    var lon = 3;
    var zoom = 2;
    var label = "";
    var url = window.location.href.split("=");
    if (url[1] != null) {
      var par=url[1].split(",");
      if (par[0] != null) lat = Number(par[0]);
      if (par[1] != null) lon = Number(par[1]);
      if (par[2] != null) zoom = Number(par[2]);
      if (par[3] != null && par[3] != '') label = par[3];
      else label = 'no title';
      if (par[0] == 37 && par[1] == 3) label = '';
      else label = decodeURIComponent(label);
    }
  
    var min = 0;
    var max = 11;

    var osmmax = 15;   

// ------- Layer Functions -------

    var license = "<a href='http://www.maps-for-free.com/html/about.html' target='_blank'>License</a>";
    var copyright = new GCopyright(1, new GLatLngBounds(new GLatLng(-90, -180), new GLatLng(90, 180)), 0, license);
    var copyrightCollection = new GCopyrightCollection("Relief Maps: ");
    copyrightCollection.addCopyright(copyright);

    var relief = [new GTileLayer(copyrightCollection, min, max)];
    relief[0].getTileUrl = function(a,b) {
      return "http://www.maps-for-free.com/layer/relief/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".jpg"; };
    reliefLayer = new GMapType(relief, G_SATELLITE_MAP.getProjection(), "Relief", {minResolution:min, maxResolution:max});

    var osmlicense = "<a href='http://www.openstreetmap.com' target='_blank'>License</a>";
    var osmcopyright = new GCopyright(1, new GLatLngBounds(new GLatLng(-90, -180), new GLatLng(90, 180)), 0, osmlicense);
    var osmcopyrightCollection = new GCopyrightCollection("OpenStreetsMap: ");
    osmcopyrightCollection.addCopyright(osmcopyright);

    var osm = [new GTileLayer(osmcopyrightCollection, min, osmmax)];
    osm[0].getTileUrl = function(a,b) {
      return "http://tile.openstreetmap.org/" + b + "/" + a.x + "/" + a.y + ".png"; };
    osmLayer = new GMapType(osm, G_SATELLITE_MAP.getProjection(), "OpenStreetMap", {minResolution:min, maxResolution:osmmax});

    // Water Layer
    function getWaterLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/water/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".gif";
      };            
      return layer;
    }
    // Admin Layer
    function getAdminLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/admin/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".gif";
      };            
      return layer;
    }
    // Street Layer
    function getStreetLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/streets/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".gif";
      };            
      return layer;
    }
    // Seismap Layer
    function getSeismapLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://localhost:7000/view/mapimage/view/" + b + "/" + a.x + "/" + a.y;
      };            
      return layer;
    }
    // Forest Layer
    function getForestLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/forest/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".gif";
      };            
      return layer;
    }
    // Tundra Layer
    function getTundraLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/tundra/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".gif";
      };            
      return layer;
    }
    // Swamp Layer
    function getSwampLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/swamp/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".gif";
      };            
      return layer;
    }
    // Grass Layer
    function getGrassLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/grass/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".gif";
      };            
      return layer;
    }
    // Crop Layer
    function getCropLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/crop/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".gif";
      };            
      return layer;
    }
    // Sand Layer
    function getSandLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/sand/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".gif";
      };            
      return layer;
    }
    // Ice Layer
    function getIceLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/ice/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".gif";
      };            
      return layer;
    }
    // Tile Layer
    function getTileLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/tiles/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".gif";
      };            
      return layer;
    }
    // Country Layer
    function getCountryLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/country/z" + b + "/row" + a.y + "/" + b + "_" + a.x + "-" + a.y + ".png";
      };            
      return layer;
    }
    // City Layer
    function getCityLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://www.maps-for-free.com/layer/city/" + b + "/" + a.x + "/" + a.y + ".png";
      };            
      return layer;
    }
    // UMP Layer
    function getUMPLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (a,b) {
        return "http://1.tiles.ump.waw.pl/ump_tiles/" + b + "/" + a.x + "/" + a.y + ".png?l=hybrid";
      };            
      return layer;
    }
    var interval = [1000, 1000,  750,  750,  750,  250,  250,  250, 200, 100,  50,  50,  25,  25, 25, 10,  3];
    // Contour Lines
    function getContourLayer() {
      var layer = new GTileLayer(copyrightCollection, min, max);
      layer.getTileUrl = function (tile, zoom) {
        return "http://www.heywhatsthat.com/bin/contour_tiles.cgi?x=" + tile.x + "&y=" + tile.y + "&zoom=" + zoom + "&interval=" + interval[zoom] + "&color=ff0000";
      };            
      return layer;
    }

// ------- Layer Control -------

    function LayerControl() { } 
    LayerControl.prototype = new GControl();         

    LayerControl.prototype.getDefaultPosition = function() {
      return new GControlPosition(G_ANCHOR_TOP_RIGHT, new GSize(7, 27));
    }

    LayerControl.prototype.initialize = function(map) {
      
      var me = this;        
      var container = document.createElement("div");

      // MapButtons selber erzeugen
      var mapDiv = me.createButton_("Map");
      var satDiv = me.createButton_("Satellite");
      var terDiv = me.createButton_("Terrain");
      var relDiv = me.createButton_("Relief");
      var osmDiv = me.createButton_("OSM");

      me.assignButtonEvent_(mapDiv, map, G_NORMAL_MAP, [satDiv, terDiv, relDiv, osmDiv]);
      me.assignButtonEvent_(satDiv, map, G_SATELLITE_MAP, [mapDiv, terDiv, relDiv, osmDiv]);
      me.assignButtonEvent_(terDiv, map, G_PHYSICAL_MAP, [satDiv, mapDiv, relDiv, osmDiv]);
      me.assignButtonEvent_(relDiv, map, reliefLayer, [mapDiv, terDiv, satDiv, osmDiv]);
      me.assignButtonEvent_(osmDiv, map, osmLayer, [mapDiv, satDiv, terDiv, relDiv]);

      GEvent.addListener(map, "maptypechanged", function() {
        if (map.getCurrentMapType() == G_NORMAL_MAP) GEvent.trigger(mapDiv, "click");
        else if (map.getCurrentMapType() == G_SATELLITE_MAP) GEvent.trigger(satDiv, "click");
        else if (map.getCurrentMapType() == G_PHYSICAL_MAP) GEvent.trigger(terDiv, "click");
        else if (map.getCurrentMapType() == reliefLayer) GEvent.trigger(relDiv, "click");
        else GEvent.trigger(osmDiv, "click");            
      });

      // LayerButton
      var layerButton = me.createButton_("Layer");
      //layerButton.style.width = "150px";
      //layerButton.style.fontSize = "10px";
      layerButton.style.marginRight = "8px";
      layerButton.firstChild.style.cssFloat = "left";
      layerButton.firstChild.style.styleFloat = "left";

      // Plus Button erzeugen
      var plusButton = document.createElement("div");
      plusButton.style.width = "15px";
      plusButton.style.cssFloat = "left";
      /* Achtung: Top gross schreiben */
      plusButton.style.marginTop = "4px";
      plusButton.style.styleFloat = "left";
      plusButton.innerHTML = "<img src='../iconies/down.gif'/>";

      // Expanded Div, indem die anderen Layer aufgelistet sind
      var expandedDiv = document.createElement("div");
      expandedDiv.style.width = "1px";
      expandedDiv.style.clear = "both";
      expandedDiv.style.padding = "2px";
      expandedDiv.style.display = "none";
      expandedDiv.style.styleFloat = "left"; // fuer IE7
      
      // Labeling (Google) Button
      labelButton = document.createElement("div");
      labelButton.style.width = "100%";
      labelButton.style.fontWeight = "bold";
      labelButton.innerHTML = "Google";            
      // Water Button
      waterButton = document.createElement("div");
      waterButton.style.fontWeight = "bold";
      waterButton.innerHTML = "Waterbodies";      
      // Admin Area Button
      adminButton = document.createElement("div");
      adminButton.innerHTML = "Admin&nbsp;Areas";
      // Street Button
      streetButton = document.createElement("div");
      streetButton.innerHTML = "Roads'n&nbsp;Rails";
      // Seismap Button
      seismapButton = document.createElement("div");
      seismapButton.innerHTML = "Seismap";
      // Forest Button
      forestButton = document.createElement("div");
      forestButton.innerHTML = "Forest&nbsp;Areas";
      // Tundra Button
      tundraButton = document.createElement("div");
      tundraButton.innerHTML = "Tundra&nbsp;Areas";
      // Swamp
      swampButton = document.createElement("div");
      swampButton.innerHTML = "Swampland";
      // Grass
      grassButton = document.createElement("div");
      grassButton.innerHTML = "Grassland";
      // crop
      cropButton = document.createElement("div");
      cropButton.innerHTML = "Cropland";
      // Sand
      sandButton = document.createElement("div");
      sandButton.innerHTML = "Sandy&nbsp;Soils";
      // Ice
      iceButton = document.createElement("div");
      iceButton.innerHTML = "Ice&nbsp;Sheets";
      // Tile Button
      tileButton = document.createElement("div");
      tileButton.innerHTML = "Tilenames";
      // City
      cityButton = document.createElement("div");
      cityButton.innerHTML = "Towns'n&nbsp;Cities";
      // Country
      countryButton = document.createElement("div");
      countryButton.innerHTML = "Countries";
      // ContourButton
      contourButton = document.createElement("div");
      contourButton.innerHTML = "Contours";
      // UMP Button
      umpButton = document.createElement("div");
      umpButton.innerHTML = "UMP&nbsp;(Level&nbsp;6)";

      // Separator Div erzeugen
      separatorDiv = document.createElement("div");
      separatorDiv.style.clear = "both";
      // Umbruch Br erzeugen
      brElement = document.createElement("br");
      // Koordinaten
      coords = document.createElement("div");
      coords.id = "coords";

      // Listener fuer Expanded Layer Div
      GEvent.addDomListener(layerButton.firstChild, "click", function() {
        if (me.keyExpanded) {
          me.keyExpanded = false;
          expandedDiv.style.display = "none";
        } else {
          me.keyExpanded = true;
          expandedDiv.style.display = "block";
        }
      });
      // Listener fuer Plus Button
      GEvent.addDomListener(plusButton, "click", function() {
        if (me.keyExpanded) {
          me.keyExpanded = false;
          expandedDiv.style.display = "none";
        } else {
          me.keyExpanded = true;
          expandedDiv.style.display = "block";
        }
      });
      // Listener fuer Label Button
      GEvent.addDomListener(labelButton, "click", function() {
        if (labelLayer.visible) {
          map.removeOverlay(labelLayer);
          labelLayer.visible = false;
        }
        else {
          map.addOverlay(labelLayer);
          labelLayer.visible = true;
        }
        me.toggleButton_HBr(labelButton, labelLayer.visible);
      });  
      // Listener fuer Water Button
      GEvent.addDomListener(waterButton, "click", function() {
        if (waterLayer.visible) {
          map.removeOverlay(waterLayer);
          waterLayer.visible = false;
        }
        else {
          map.addOverlay(waterLayer);
          waterLayer.visible = true;
        }
        me.toggleButton_HBr(waterButton, waterLayer.visible);
      });
      // Listener fuer Admin Button
      GEvent.addDomListener(adminButton, "click", function() {
        if (adminLayer.visible) {
          map.removeOverlay(adminLayer);
          adminLayer.visible = false;
        }
        else {
          map.addOverlay(adminLayer);
         adminLayer.visible = true;
        }
        me.toggleButton_HBr(adminButton, adminLayer.visible);
      });  
      // Listener fuer Street Button
      GEvent.addDomListener(streetButton, "click", function() {
        if (streetLayer.visible) {
          map.removeOverlay(streetLayer);
          streetLayer.visible = false;
        }
        else {
          map.addOverlay(streetLayer);
          streetLayer.visible = true;
        }
        me.toggleButton_HBr(streetButton, streetLayer.visible);
      });  
      // Listener fuer Seismap Button
      GEvent.addDomListener(seismapButton, "click", function() {
        if (seismapLayer.visible) {
          map.removeOverlay(seismapLayer);
          seismapLayer.visible = false;
        }
        else {
          map.addOverlay(seismapLayer);
          seismapLayer.visible = true;
        }
        me.toggleButton_HBr(seismapButton, seismapLayer.visible);
      });  
      // Listener fuer Forest Button
      GEvent.addDomListener(forestButton, "click", function() {
        if (forestLayer.visible) {
          map.removeOverlay(forestLayer);
          forestLayer.visible = false;
        }
        else {
          map.addOverlay(forestLayer);
          forestLayer.visible = true;
        }
        me.toggleButton_HBr(forestButton, forestLayer.visible);
      });  
      // Listener fuer Tundra Button
      GEvent.addDomListener(tundraButton, "click", function() {
        if (tundraLayer.visible) {
          map.removeOverlay(tundraLayer);
          tundraLayer.visible = false;
        }
        else {
          map.addOverlay(tundraLayer);
          tundraLayer.visible = true;
        }
        me.toggleButton_HBr(tundraButton, tundraLayer.visible);
      });  
      // Listener fuer Swamp Button
      GEvent.addDomListener(swampButton, "click", function() {
        if (swampLayer.visible) {
          map.removeOverlay(swampLayer);
          swampLayer.visible = false;
        }
        else {
          map.addOverlay(swampLayer);
          swampLayer.visible = true;
        }
        me.toggleButton_HBr(swampButton, swampLayer.visible);
      });  
      // Listener fuer Grass Button
      GEvent.addDomListener(grassButton, "click", function() {
        if (grassLayer.visible) {
          map.removeOverlay(grassLayer);
          grassLayer.visible = false;
        }
        else {
          map.addOverlay(grassLayer);
          grassLayer.visible = true;
        }
        me.toggleButton_HBr(grassButton, grassLayer.visible);
      });
      // Listener fuer Crop Button
      GEvent.addDomListener(cropButton, "click", function() {
        if (cropLayer.visible) {
          map.removeOverlay(cropLayer);
          cropLayer.visible = false;
        }
        else {
          map.addOverlay(cropLayer);
          cropLayer.visible = true;
        }
        me.toggleButton_HBr(cropButton, cropLayer.visible);
      });
      // Listener fuer Sand Button
      GEvent.addDomListener(sandButton, "click", function() {
        if (sandLayer.visible) {
          map.removeOverlay(sandLayer);
          sandLayer.visible = false;
        }
        else {
          map.addOverlay(sandLayer);
          sandLayer.visible = true;
        }
        me.toggleButton_HBr(sandButton, sandLayer.visible);
      });
      // Listener fuer Ice Button
      GEvent.addDomListener(iceButton, "click", function() {
        if (iceLayer.visible) {
          map.removeOverlay(iceLayer);
          iceLayer.visible = false;
        }
        else {
          map.addOverlay(iceLayer);
          iceLayer.visible = true;
        }
        me.toggleButton_HBr(iceButton, iceLayer.visible);
      });
      // Listener fuer Tile Button
      GEvent.addDomListener(tileButton, "click", function() {
        if (tileLayer.visible) {
          map.removeOverlay(tileLayer);
          tileLayer.visible = false;
        }
        else {
          map.addOverlay(tileLayer);
          tileLayer.visible = true;
        }
        me.toggleButton_HBr(tileButton, tileLayer.visible);
      });
      // Listener fuer Country Button
      GEvent.addDomListener(countryButton, "click", function() {
        if (countryLayer.visible) {
          map.removeOverlay(countryLayer);
          countryLayer.visible = false;
        }
        else {
          map.addOverlay(countryLayer);
          countryLayer.visible = true;
        }
        me.toggleButton_HBr(countryButton, countryLayer.visible);
      });
      // Listener fuer City Button
      GEvent.addDomListener(cityButton, "click", function() {
        if (cityLayer.visible) {
          map.removeOverlay(cityLayer);
          cityLayer.visible = false;
        }
        else {
          map.addOverlay(cityLayer);
          cityLayer.visible = true;
        }
        me.toggleButton_HBr(cityButton, cityLayer.visible);
      });
      // Listener fuer Contour Button
      GEvent.addDomListener(contourButton, "click", function() {
        if (contourLayer.visible) {
          map.removeOverlay(contourLayer);
          contourLayer.visible = false;
        }
        else {
          map.addOverlay(contourLayer);
          contourLayer.visible = true;
        }
        me.toggleButton_HBr(contourButton, contourLayer.visible);
      });
      // Listener fuer UMP Button
      GEvent.addDomListener(umpButton, "click", function() {
        if (umpLayer.visible) {
          map.removeOverlay(umpLayer);
          umpLayer.visible = false;
        }
        else {
          map.addOverlay(umpLayer);
          umpLayer.visible = true;
        }
        me.toggleButton_HBr(umpButton, umpLayer.visible);
      });

      // Koordinaten
      GEvent.addListener(map, "mousemove", function(point) { 
        var msLoc = point.toUrlValue().split(","); 
        var msLat = Math.round(msLoc[0] * 1000) / 1000;
        var msLon = Math.round(msLoc[1] * 1000) / 1000;
        document.getElementById("coords").innerHTML = msLat + "/" + msLon; } 
      );
      
      expandedDiv.appendChild(labelButton);
      expandedDiv.appendChild(brElement);    

      expandedDiv.appendChild(waterButton);    
      expandedDiv.appendChild(brElement);    
      expandedDiv.appendChild(adminButton); 
      expandedDiv.appendChild(brElement);            
      expandedDiv.appendChild(streetButton); 
      expandedDiv.appendChild(brElement);       
      expandedDiv.appendChild(seismapButton);
      expandedDiv.appendChild(brElement);       
      expandedDiv.appendChild(forestButton);
      expandedDiv.appendChild(brElement);    
      expandedDiv.appendChild(tundraButton);    
      expandedDiv.appendChild(brElement);    
      expandedDiv.appendChild(swampButton); 
      expandedDiv.appendChild(brElement);            
      expandedDiv.appendChild(grassButton);    
      expandedDiv.appendChild(brElement);    
      expandedDiv.appendChild(cropButton); 
      expandedDiv.appendChild(brElement); 
      expandedDiv.appendChild(sandButton); 
      expandedDiv.appendChild(brElement);    
      expandedDiv.appendChild(iceButton); 
      expandedDiv.appendChild(brElement);    
      expandedDiv.appendChild(tileButton); 
      expandedDiv.appendChild(brElement);
      expandedDiv.appendChild(countryButton);
      expandedDiv.appendChild(brElement);
      expandedDiv.appendChild(cityButton); 
      expandedDiv.appendChild(brElement);     
      expandedDiv.appendChild(contourButton);
      expandedDiv.appendChild(brElement);
      expandedDiv.appendChild(umpButton);    
      expandedDiv.appendChild(brElement);
      expandedDiv.appendChild(coords);
   
      layerButton.appendChild(plusButton);
      layerButton.appendChild(separatorDiv);
      layerButton.appendChild(expandedDiv);
      me.toggleButton_(layerButton.firstChild, false);
      
      container.appendChild(layerButton);
      container.appendChild(mapDiv);
      container.appendChild(satDiv);
      container.appendChild(terDiv);
      container.appendChild(relDiv);
      container.appendChild(osmDiv);

      map.getContainer().appendChild(container);
      return container;
    }
    
// ------- Layer Control Functions -------

    // Erzeugt die Buttons 
    LayerControl.prototype.createButton_ = function(text) {
      var buttonDiv = document.createElement("div");
      buttonDiv.style.color = "#000000";
      buttonDiv.style.backgroundColor = "white";
      buttonDiv.style.font = "small Arial";
      buttonDiv.style.border = "1px solid black";
      buttonDiv.style.padding = "0px";
      buttonDiv.style.margin= "0px";
      buttonDiv.style.textAlign = "center";
      buttonDiv.style.fontSize = "12px"; 
      buttonDiv.style.cursor = "pointer";
      buttonDiv.style.cssFloat = "left";
      buttonDiv.style.styleFloat = "left";
      var textDiv = document.createElement("div");
      textDiv.appendChild(document.createTextNode(text));
      textDiv.style.width = "6em";
      buttonDiv.appendChild(textDiv);
      return buttonDiv;
    }    
    
    // Click Map Buttons
    LayerControl.prototype.assignButtonEvent_ = function(div, map, mapType, otherDivs) {
      var me = this;
      GEvent.addDomListener(div, "click", function() {
        for (var i = 0; i < otherDivs.length; i++) {
          me.toggleButton_(otherDivs[i].firstChild, false);
        }
        me.toggleButton_(div.firstChild, true);
        map.setMapType(mapType);
      });
    }
    
    // Definiert den Style fuer das Expandend Div beim Anklicken    
    LayerControl.prototype.toggleButton_ = function(div, boolCheck) {
       div.style.fontWeight = boolCheck ? "bold" : "";
       div.style.border = "1px solid white";
       var shadows = boolCheck ? ["Top", "Left"] : ["Bottom", "Right"];
       for (var j = 0; j < shadows.length; j++) {
         div.style["border" + shadows[j]] = "1px solid #b0b0b0";
       } 
    }

    // Definiert den HBr-Style fuer die Layer Buttons beim Anklicken    
    LayerControl.prototype.toggleButton_HBr = function(div, boolCheck) {
       div.style.fontWeight = boolCheck ? "bold" : "";
    }

// ------- Add Layer Control to Map -------

    map = new GMap2(document.getElementById("map"));
    map.setCenter(new GLatLng(lat, lon), zoom);

    // UMP Layer
    umpLayer = new GTileLayerOverlay(getUMPLayer());
    umpLayer.visible = false;
    // Water Layer
    waterLayer = new GTileLayerOverlay(getWaterLayer());
    map.addOverlay(waterLayer);
    waterLayer.visible = true;
    // Label Layer
    labelLayer = new GTileLayerOverlay(G_HYBRID_MAP.getTileLayers()[1]);
    map.addOverlay(labelLayer);
    labelLayer.visible = true;
    // Admin Layer
    adminLayer = new GTileLayerOverlay(getAdminLayer());
    adminLayer.visible = false;
    // Street Layer
    streetLayer = new GTileLayerOverlay(getStreetLayer());
    streetLayer.visible = false;
    // Tile Layer
    tileLayer = new GTileLayerOverlay(getTileLayer());
    tileLayer.visible = false;
    // Seismap Layer
    seismapLayer = new GTileLayerOverlay(getSeismapLayer());
    seismapLayer.visible = false;
    // Forest Layer
    forestLayer = new GTileLayerOverlay(getForestLayer());
    forestLayer.visible = false;
    // Tundra Layer
    tundraLayer = new GTileLayerOverlay(getTundraLayer());
    tundraLayer.visible = false;
    // Swamp Layer
    swampLayer = new GTileLayerOverlay(getSwampLayer());
    swampLayer.visible = false;
    // Grass Layer
    grassLayer = new GTileLayerOverlay(getGrassLayer());
    grassLayer.visible = false;
    // Sand Layer
    sandLayer = new GTileLayerOverlay(getSandLayer());
    sandLayer.visible = false;
    // Crop Layer
    cropLayer = new GTileLayerOverlay(getCropLayer());
    cropLayer.visible = false;
    // Ice Layer
    iceLayer = new GTileLayerOverlay(getIceLayer());
    iceLayer.visible = false;
    // Country Layer
    countryLayer = new GTileLayerOverlay(getCountryLayer());
    countryLayer.visible = false;
    // City Layer
    cityLayer = new GTileLayerOverlay(getCityLayer());
    cityLayer.visible = true;
    // Contour Layer
    contourLayer = new GTileLayerOverlay(getContourLayer());
    contourLayer.visible = false;
    // UMP Layer
    umpLayer = new GTileLayerOverlay(getUMPLayer());
    umpLayer.visible = false;

    map.addMapType(reliefLayer);
    map.addMapType(osmLayer);
    map.addControl(new LayerControl()); 
    map.addControl(new GLargeMapControl());
    map.addControl(new GOverviewMapControl());
    map.addControl(new GScaleControl());
	//map.addControl(new GZoomControl());
	//map.addControl(new GZoomControl(),new GControlPosition(G_ANCHOR_TOP_LEFT,new GSize(10,10)));

        /* first set of options is for the visual overlay.*/
        var boxStyleOpts = {
          opacity: .2,
          border: "2px solid red"
        }

        /* second set of options is for everything else */
        var otherOpts = {
          buttonHTML:"<img src='../iconies/sn_off.gif' />",
          buttonZoomingHTML:"<img src='../iconies/sn_on.gif' />",
          buttonStartingStyle:{width:'0px',height:'0px',border:'1px none #ff0000'}

          //buttonHTML: "<img src='images/zoom-button.gif' />",
          //buttonZoomingHTML: "<img src='images/zoom-button-activated.gif' />",
          //buttonStartingStyle: {width: '24px', height: '24px'}
        };

        /* third set of options specifies callbacks */
        var callbacks = {
          //buttonclick: function(){GLog.write("Looks like you activated DragZoom!")},
          //dragstart: function(){GLog.write("Started to Drag . . .")},
          //dragging: function(x1,y1,x2,y2){GLog.write("Dragging, currently x="+x2+",y="+y2)},
          //dragend: function(nw,ne,se,sw,nwpx,nepx,sepx,swpx){GLog.write("Zoom! NE="+ne+";SW="+sw)}
        };
  
        map.addControl(new DragZoomControl(boxStyleOpts, otherOpts, callbacks),new GControlPosition(G_ANCHOR_TOP_LEFT,new GSize(50,50)));

    map.setMapType(reliefLayer);

    map.enableDoubleClickZoom();
    map.enableContinuousZoom();
    map.enableScrollWheelZoom();

    if (label != "" && label != "default") {
      var html1 = "<div style='font-family:arial,verdana,tahoma,helvetica; font-size:12px;'>"
      var html2 = "<b>" + label + "</b> <p> (" + lat + " / " + lon + ")" + "</div>";
      var marker = new GMarker(new GLatLng(lat, lon));   
      map.addOverlay(marker);
      marker.openInfoWindowHtml(html1 + html2);
    }
    else if (label == "default") {
      var icon = new GIcon();
      icon.image = "../iconies/poi.png";
      icon.iconSize = new GSize(9, 9);
      icon.shadowSize = new GSize(0, 0);
      icon.iconAnchor = new GPoint(0, 0);
      icon.infoWindowAnchor = new GPoint(10, 0);
      var marker = new GMarker(new GLatLng(lat, lon), icon);
      map.addOverlay(marker);
      var html = "<div style='font-size:12px; width:200px; color:#000000;'><b>Breithorn Nordgipfel (4112m)</b></div>";
      GEvent.addListener(marker, 'mouseover', function() {
        map.closeInfoWindow();
        marker.openInfoWindow(html);
      });
      GEvent.addListener(marker, 'mouseout', function() {
        map.closeInfoWindow();
      });
      GEvent.addListener(marker, 'click', function() {
        window.open("http://srtm.in-ulm.de/pages/geolabels/index.html"); // neues Fenster oder besser
        //location.href = "http://srtm.in-ulm.de/pages/geolabels/index.html"; // mit back-button!?
      });
    }
  }
}
