<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>SeisMap Server</title>

<script type="text/javascript"
	src="/js/mootools/mootools-core-1.3-full-nocompat.js"></script>
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script type="text/javascript">
      document.write('<script type="text/javascript" src="http://google-maps-utility-library-v3.googlecode.com/svn/tags/markermanager/1.0/src/markermanager' + (document.location.search.indexOf('packed') > -1 ? '_packed' : '') + '.js"><' + '/script>');
    </script>
<script type="text/javascript">
    //<![CDATA[

    function log(text) {
        var log=document.getElementById('log');
        log.value =text+'\n'+log.value;
    }

	var scaleLimits = {
	    ML: {
	        minimum:1, 
	        maximum:7,
	        cut:6.5
	    },
        MC: {
            minimum:1, 
            maximum:12,
            cut:10
        },
        MW: {
            minimum:1, 
            maximum:7,
            cut:6.5
        }
	};
	
	var googleZoomLevel = {
			minimum:0,
            maximum:20,
			cut:3
	};
    
    var IMAGES = [ 'sun', 'rain', 'snow', 'storm' ];
    var ICONS = [];
    var map = null;
    var mgr = null;

    
    function setupMap() {
      var myOptions = {
        zoom: 4,
        //center: new google.maps.LatLng(48.25, 11.00),
        //center: new google.maps.LatLng(-36.5, -64.5), ARGENTINA
        center: new google.maps.LatLng(-10, 24),
        mapTypeId: google.maps.MapTypeId.ROADMAP
      };
      map = new google.maps.Map(document.getElementById('map'), myOptions);

      var listener = google.maps.event.addListener(map, 'bounds_changed', function(){
          setupWeatherMarkers();
          google.maps.event.removeListener(listener);
      });
    }

    function getWeatherIcon() {
      var i = Math.floor(IMAGES.length*Math.random());
      if (!ICONS[i]) {          
        var iconImage = new google.maps.MarkerImage('images/' + IMAGES[i] + '.png',
            new google.maps.Size(32, 32),
            new google.maps.Point(0,0),
            new google.maps.Point(0, 32)
        );
        
        var iconShadow = new google.maps.MarkerImage('images/' + IMAGES[i] + '.png',
            new google.maps.Size(32, 59),
            new google.maps.Point(0,0),
            new google.maps.Point(0, 32)
        );
        
        var iconShape = {
            coord: [1, 1, 1, 32, 32, 32, 32, 1],
            type: 'poly'
        };

        ICONS[i] = { 
          icon : iconImage,
          shadow: iconShadow,
          shape : iconShape
        };
        
      }
      return ICONS[i];
    }

    function getRandomPoint() {
      var lat = 48.25 + (Math.random() - 0.5) * 14.5;
      var lng = 11.00 + (Math.random() - 0.5) * 36.0;
      return new google.maps.LatLng(Math.round(lat * 10) / 10, Math.round(lng * 10) / 10);
    }

	function getWeatherMarkers(n) {
      var batch = [];
      for (var i = 0; i < n; ++i) {
        var tmpIcon = getWeatherIcon();  
        
        batch.push(new google.maps.Marker({
            position: getRandomPoint(),
            //shadow: tmpIcon.shadow,
            //icon: tmpIcon.icon,
            //shape: tmpIcon.shape,
            title: 'Weather marker'
            })
        );        
      }
      return batch;
    }

    function getScaleVisibleMinimum(scale) {
        var effectiveZoom = map.getZoom();
        if (effectiveZoom < googleZoomLevel.cut) {
        	effectiveZoom = googleZoomLevel.cut;
        }
        var normalizedZoom = (googleZoomLevel.maximum - effectiveZoom) / (googleZoomLevel.maximum - googleZoomLevel.cut);
        var smin= (scaleLimits[scale].cut - scaleLimits[scale].minimum) * normalizedZoom + scaleLimits[scale].minimum;
        log('z=' + map.getZoom() + ' effectiveZoom=' + effectiveZoom + ' normalizedZoom=' + normalizedZoom + ' smin=' + smin);
        return smin;
    }

    function getScaleVisibleMaximum(scale) {
        return null;
    }

    function getMagnitudeVisibleZoom(scale, magnitude) {
        if (scaleLimits[scale] == undefined) {
            log ('define limits for ' + scale);
        }
        var visibleZoom = googleZoomLevel.maximum - (googleZoomLevel.maximum - googleZoomLevel.cut) * (magnitude - scaleLimits[scale].minimum) / (scaleLimits[scale].cut - scaleLimits[scale].minimum);
        if (visibleZoom <= googleZoomLevel.cut) {
            visibleZoom = googleZoomLevel.minimum;
        }
        return visibleZoom;
    }

    function getEventVisibleZoom(event) {
        var minimumVisibleZoom = googleZoomLevel.maximum;
        for (var i = 0; i < event.magnitudes.length; i++) {
            var magnitude = event.magnitudes[i];
            var magnitudeVisibleZoom = getMagnitudeVisibleZoom(magnitude.type, magnitude.value);
        	minimumVisibleZoom = Math.min(magnitudeVisibleZoom, minimumVisibleZoom);
        }
        //log(' visibleZoom=' + minimumVisibleZoom + ' ' + JSON.encode(event.magnitudes));
        return Math.round(minimumVisibleZoom);
    }
	
	var loadedEventsIds = {};
    function loadEvents() {
        var bounds = map.getBounds();
    	var filter =
           {
    	        
                dateRange: {minimum: null, maximum: null},
                latitudeRange: {minimum: bounds.getSouthWest().lat(), maximum: bounds.getNorthEast().lat()}, 
                longitudeRange: {minimum: bounds.getSouthWest().lng(), maximum: bounds.getNorthEast().lng()},
                depthRange: {minimum: null, maximum: null},
                magnitudeRanges: {
                    ML:{minimum: getScaleVisibleMinimum('ML'), maximum: getScaleVisibleMaximum('ML')},
                }
           };
        log('Retrieving ' + JSON.encode(filter));
        function addMarkers(events) {
			var markersByZoom = {};
			for (var i=0; i<events.length; i++) {
				var event = events[i];
				if(loadedEventsIds[event.id] == undefined) {
					loadedEventsIds[event.id] = true;
					var zoom = getEventVisibleZoom(event);
					if(markersByZoom[zoom] == undefined) {
						markersByZoom[zoom] = [];
					}
					var markers = markersByZoom[zoom];
					markers.push(new google.maps.Marker({
				            position: new google.maps.LatLng(event.latitude, event.longitude),
				            //shadow: tmpIcon.shadow,
				            //icon: tmpIcon.icon,
				            //shape: tmpIcon.shape,
				            title: 'Event' + event.id
				            })
				        );  
				}
			}
			for (var zoom in markersByZoom) {
			    var markers = markersByZoom[zoom];
				log("Markers added at zoom " + zoom + ":" + markers.length);
				if (markers.length > 0) {
	            	mgr.addMarkers(markers, zoom);
	            	mgr.refresh();
				}
			}
        }
    	var jsonRequest = new Request.JSON({
    		 headers: { 'Content-Type': 'application/json' }, urlEncoded: false,
        	url: '/view/rest/event/get', onSuccess: function(response){
        	
    		   log("Obtained " + response.events.length);
    		   addMarkers(response.events);
               
    	    }}).post(JSON.encode(filter));
    }
    

    var timerId = null;
    function setupWeatherMarkers() {
        mgr = new MarkerManager(map);
        google.maps.event.addListener(map, 'bounds_changed', function() {
            	if (timerId != null) {
                	clearTimeout(timerId);
            	}
            	function timer() {
            		timerId=null;
            		loadEvents();
            	}
            	timerId = setTimeout(timer, 2000);
        	});      
      }
    function clearText(){
    	var textArea = document.getElementById("log");
    	textArea.value="";
    }
    function getZoom(){
    	alert(map.getZoom());
    }
    function getCenter(){
    	if (map != null){
    		alert(map.getCenter() + " CENTRO");
    	}
    	
    	var latLng = {lat: map.getCenter().lat(), lng: map.getCenter().lng()};
    	latLng.lng = latLng.lng%360;
		alert(latLng.lat + " " + latLng.lng);
    	alert(map.getCenter() + " CENTRO");
    }
    function getModule(){
    	
    }
    //]]>
    </script>

</head>

<body onload="setupMap()">
<div id="map" style="margin: 5px auto; width: 500px; height: 400px"></div>
<div style="text-align: center; font-size: large;">Random Weather
Map</div>
<div>
<button onclick="getEvents()">getEvents</button>
<button onclick="getCenter()">getCenter</button>
<button onclick="clearText()">clearText</button>
<button onclick="getZoom()">getZoom</button>
<button onclick="getModule()">Modulo</button>
<br/>
</div>
<div>
<textarea id="log" rows="15" cols="200"></textarea>
</div>
</body>
</html>

