// HBr...

function openwindow (url, width, height) {
  // Achtung: Scrollbars werden sowieso nicht angezeigt, da diese zusaetzlich im CSS-Style von snahpshot.php abgeschaltet sind...
  fenster=window.open(url, "_blank", "width=" + width + ",height=" + height + ",toolbar=no,locationbar=no,personalbar=no,directories=no,status=no,statusbar=no,menubar=no,scrollbars=no,resizable=no");
  ww=(screen.availWidth/2) - width/2;
  wh=(screen.availHeight/2) - height/2;
  //ww = document.body.clientWidth/2 - width/2;
  //wh = document.body.clientHight/2;
  fenster.moveTo(ww, wh);
  fenster.focus();
  return false;
}



/*
* DragZoomControl Class v1.2 
*  Copyright (c) 2005-2007, Andre Lewis, andre@earthcode.com
*
* Back Button functionality
*  Copyright (c)  2007, Richard Garland, papabear.newyork@gmail.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*       http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* This class lets you add a control to the map which will let the user
*  zoom by dragging a rectangle.
*  More info on original GZoom at http://earthcode.com
*
* Back Button functionality provides the user with a one click means to return the map state 
*  to its state prior to the DragZoom.  Sequential DragZooms are backed out in reverse order.
*/

/**
 * Constructor for DragZoomControl, which takes 3 option hashes and
 *  uses them to customize the control.
 * @param {opts_boxStyle} Named optional arguments:
 *   opts_boxStyle.opacity {Number} Opacity from 0-1
 *   opts_boxStyle.fillColor {String} Hex value of fill color
 *   opts_boxStyle.border {String} CSS-style declaration of border
 * @param {opts_other} Named optional arguments:
 *   opts_other.buttonHTML {String} The zoom button HTML in non-activated state
 *   opts_other.buttonStartingStyle {Object} A hash of css styles for the 
 *     zoom button which are common to both un-activated and activated state
 *   opts_other.buttonStyle {Object} A hash of css styles for the zoom button 
 *     which will be applied when the button is in un-activated state.
 *   opts_other.buttonZoomingHTML {String} HTML which is placed in the 
 *     zoom button when the button is activated. 
 *   opts_other.buttonZoomingStyle {Object} A hash of css styles for the 
 *    zoom button which will be applied when the button is activated.
 *   opts_other.overlayRemoveTime {Number} The number of milliseconds to wait before
 *     removing the rectangle indicating the zoomed-in area after the zoom has happened.
 *   opts_other.stickyZoomEnabled {Boolean} Whether or not the control stays in 
 *     "zoom mode" until turned off. When true, the user can zoom repeatedly, 
 *     until clicking on the zoom button again to turn zoom mode off.
 *   opts_other.backButtonEnabled {Boolean} enables Back Button functionality
 *   opts_other.backButtonHTML {String} The back button HTML
 *   opts_other.backButtonStyle {Object} A hash of css styles for the back button
 *     which will be applied when the button is created.	
 * @param {opts_callbacks} Named optional arguments:
 *   opts_callbacks.buttonclick {Function} Called when the DragZoom is activated 
 *     by clicking on the "zoom" button. 
 *   opts_callbacks.dragstart {Function} Called when user starts to drag a rectangle.
 *     Callback args are x,y -- the PIXEL values, relative to the upper-left-hand 
 *     corner of the map, where the user began dragging.
 *   opts_callbacks.dragging {Function} Called repeatedly while the user is dragging.
 *     Callback args are startX,startY, currentX,currentY -- the PIXEL values of the 
 *     start of the drag, and the current drag point, respectively.
 *   opts_callbacks.dragend {Function} Called when the user releases the mouse button 
 *     after dragging the rectangle. Callback args are: NW {GLatLng}, NE {GLatLng}, 
 *     SE {GLatLng}, SW {GLatLng}, NW {GPoint}, NE {GPoint}, SE {GPoint}, SW {GPoint}.
 *     The first 4 are the latitudes/longitudes; the last 4 are the pixel coords on the map.
 *   opts_callbacks.backbuttonclick {Function} Called when the back button is activated 
 *     after the map context is restored. Callback args: methodCall (boolean) set true if
 *     this backbuttonclick was to restore context set by the mathod call, else false.
 * Method
 *    this.saveMapContext(text) Call to push map context onto the backStack and set the button text 
 *    this.initiateZoom() Call to simulate clicking the dragZoom button
 *    this.initiateZoomBack() Call to simulate clicking the dragZoom back button
 **/

/**
 *  Versions
 *  1.0 original version (v 189) 5/24/2007
 *  1.1 backbutton functionality added  (v 211) 7/30/2007
 *  1.2 bug fixes and 2 new methods 9/6/2007
 *    fix text selection conflict in IE
 *    fix align-text:center inheritance problem in IE
 *    create methods initiateZoom and initiateZoomBack which call buttonclick_() and backbuttonclick_()
 **/

function DragZoomControl(opts_boxStyle, opts_other, opts_callbacks) {
  // Holds all information needed globally
  // Not all globals are initialized here
  this.globals = {
    draggingOn: false,
    cornerTopDiv: null,
    cornerRightDiv: null,
    cornerBottomDiv: null,
    cornerLeftDiv: null,
    mapPosition: null,
    outlineDiv: null,
    mapWidth: 0,
    mapHeight: 0,
    mapRatio: 0,
    startX: 0,
    startY: 0,
    borderCorrection: 0
  };

  //box style options
  this.globals.style = {
    opacity: .2,
    fillColor: "#000",
    border: "2px solid blue"
  };

  var style = this.globals.style;
  for (var s in opts_boxStyle) {
    style[s]=opts_boxStyle[s];
  }

  var borderStyleArray = style.border.split(' ');
  style.outlineWidth = parseInt(borderStyleArray[0].replace(/\D/g,''));
  style.outlineColor = borderStyleArray[2];
  style.alphaIE = 'alpha(opacity=' + (style.opacity * 100) + ')';
 
  // map context stack for back button
  this.globals.backStack = [];

  // Other options
  this.globals.options={
    buttonHTML: 'zoom ...',
    buttonStartingStyle: 
      {width: '52px', border: '1px solid black', padding: '2px'},
    buttonStyle: {background: '#FFF'},
    backButtonHTML: 'zoom back',
    backButtonStyle: {background: '#FFF', display: 'none'},
    buttonZoomingHTML: 'Drag a region on the map',
    buttonZoomingStyle: {background: '#FF0'},
    overlayRemoveTime: 6000,
    backButtonEnabled: false,
    stickyZoomEnabled: false
  };
	
  for (var s in opts_other) {
    this.globals.options[s] = opts_other[s]
  }

  // callbacks: buttonclick, dragstart, dragging, dragend, backbuttonclick 
  if (opts_callbacks == null) {
    opts_callbacks = {}
  }
  this.globals.callbacks = opts_callbacks;
}

DragZoomControl.prototype = new GControl();

/**
 * Methods
 */

/**
 * Method called to save the map context before the zoom.
 * Back Button functionality:	
 * @param {text} text string for the back button
 */
DragZoomControl.prototype.saveMapContext = function(text) {
  if (this.globals.options.backButtonEnabled) {
    this.saveBackContext_(text,true);
    this.globals.backButtonDiv.style.display = 'block';
  }	
};

/**
 * Method called to initiate a dragZoom as if the user had clicked the dragZoom button.
 */
DragZoomControl.prototype.initiateZoom = function() {this.buttonclick_()};

/**
 * Method called to initiate a dragZoom back operation as if the user had clicked the dragZoom back button.
 * Back Button functionality:	
 */
DragZoomControl.prototype.initiateZoomBack = function() {if (this.globals.options.backButtonEnabled) this.backbuttonclick_()};	

/**
 * Creates a new button to control gzoom and appends to the button container div.
 * @param {DOM Node} buttonContainerDiv created in main .initialize code
 */
DragZoomControl.prototype.initButton_ = function(buttonContainerDiv) {
  var G = this.globals;
  var buttonDiv = document.createElement('div');
  buttonDiv.innerHTML = G.options.buttonHTML;
  buttonDiv.id = 'gzoom-control';
  DragZoomUtil.style([buttonDiv], {cursor: 'pointer', zIndex:200});
  DragZoomUtil.style([buttonDiv], G.options.buttonStartingStyle);
  DragZoomUtil.style([buttonDiv], G.options.buttonStyle);
  buttonContainerDiv.appendChild(buttonDiv);
  return buttonDiv;
};

/**												
 * Creates a second new button to control backup zoom and appends to the button container div.
 * @param {DOM Node} buttonContainerDiv created in main .initialize code
 */
DragZoomControl.prototype.initBackButton_ = function(buttonContainerDiv) {
  var G = this.globals;
  var backButtonDiv = document.createElement('div');
  backButtonDiv.innerHTML = G.options.backButtonHTML;
  backButtonDiv.id = 'gzoom-back';
  DragZoomUtil.style([backButtonDiv], {cursor: 'pointer', zIndex:200});
  DragZoomUtil.style([backButtonDiv], G.options.buttonStartingStyle);
  DragZoomUtil.style([backButtonDiv], G.options.backButtonStyle);
  buttonContainerDiv.appendChild(backButtonDiv);
  return backButtonDiv;
};

/**
 * Sets button mode to zooming or otherwise, changes CSS & HTML.
 * @param {String} mode Either "zooming" or not.
 */
DragZoomControl.prototype.setButtonMode_ = function(mode){
  var G = this.globals;
  if (mode == 'zooming') {
    G.buttonDiv.innerHTML = G.options.buttonZoomingHTML;
    DragZoomUtil.style([G.buttonDiv], G.options.buttonStartingStyle);
    DragZoomUtil.style([G.buttonDiv], G.options.buttonZoomingStyle);
  } else {
    G.buttonDiv.innerHTML = G.options.buttonHTML;
    DragZoomUtil.style([G.buttonDiv], G.options.buttonStartingStyle);
    DragZoomUtil.style([G.buttonDiv], G.options.buttonStyle);
  }
};

/**
 * Is called by GMap2's addOverlay method. Creates the zoom control
 * divs and appends to the map div.
 * @param {GMap2} map The map that has had this DragZoomControl added to it.
 * @return {DOM Object} Div that holds the gzoomcontrol button
 */ 
DragZoomControl.prototype.initialize = function(map) {
  var G = this.globals;
  var me = this;
  var mapDiv = map.getContainer();
 
  // Create div for both buttons	
    var buttonContainerDiv = document.createElement("div");	
    DragZoomUtil.style([buttonContainerDiv], {cursor: 'pointer', zIndex: 150});

  // create and init the zoom button
    //DOM:button
    var buttonDiv = this.initButton_(buttonContainerDiv);

  // create and init the back button				
    //DOM:button
    var backButtonDiv = this.initBackButton_(buttonContainerDiv);
  
  // Add the two buttons to the map 					
    mapDiv.appendChild(buttonContainerDiv);
 
  //DOM:map covers
    var zoomDiv = document.createElement("div");
    zoomDiv.id ='gzoom-map-cover';
    zoomDiv.innerHTML ='<div id="gzoom-outline" style="position:absolute;display:none;"></div><div id="gzoom-cornerTopDiv" style="position:absolute;display:none;"></div><div id="gzoom-cornerLeftDiv" style="position:absolute;display:none;"></div><div id="gzoom-cornerRightDiv" style="position:absolute;display:none;"></div><div id="gzoom-cornerBottomDiv" style="position:absolute;display:none;"></div>';
    DragZoomUtil.style([zoomDiv], {position: 'absolute', display: 'none', overflow: 'hidden', cursor: 'crosshair', zIndex: 101});
    mapDiv.appendChild(zoomDiv);
  
  // add event listeners
    GEvent.addDomListener(buttonDiv, 'click', function(e) {
      me.buttonclick_(e);
    });
    GEvent.addDomListener(backButtonDiv, 'click', function(e) {
      me.backbuttonclick_(e);
    });
    GEvent.addDomListener(zoomDiv, 'mousedown', function(e) {
      me.coverMousedown_(e);
    });
    GEvent.addDomListener(document, 'mousemove', function(e) {
      me.drag_(e);
    });
    GEvent.addDomListener(document, 'mouseup', function(e) {
      me.mouseup_(e);
    });
  
  // get globals
    G.mapPosition = DragZoomUtil.getElementPosition(mapDiv);
    G.outlineDiv = DragZoomUtil.gE("gzoom-outline");	
    G.buttonDiv = DragZoomUtil.gE("gzoom-control");
    G.backButtonDiv = DragZoomUtil.gE("gzoom-back");
    G.mapCover = DragZoomUtil.gE("gzoom-map-cover");
    G.cornerTopDiv = DragZoomUtil.gE("gzoom-cornerTopDiv");
    G.cornerRightDiv = DragZoomUtil.gE("gzoom-cornerRightDiv");
    G.cornerBottomDiv = DragZoomUtil.gE("gzoom-cornerBottomDiv");
    G.cornerLeftDiv = DragZoomUtil.gE("gzoom-cornerLeftDiv");
    G.map = map;
  
    G.borderCorrection = G.style.outlineWidth * 2;	
    this.setDimensions_();
  
  //styles
    this.initStyles_();

  // disable text selection on map cover
    G.mapCover.onselectstart = function() {return false}; 
    
  return buttonContainerDiv;
};

/**
 * Required by GMaps API for controls. 
 * @return {GControlPosition} Default location for control
 */
DragZoomControl.prototype.getDefaultPosition = function() {
  return new GControlPosition(G_ANCHOR_TOP_LEFT, new GSize(3, 120));
};

/**
 * Function called when mousedown event is captured.
 * @param {Object} e 
 */
DragZoomControl.prototype.coverMousedown_ = function(e){
  var G = this.globals;
  var pos = this.getRelPos_(e);
  G.startX = pos.left;
  G.startY = pos.top;
  
  DragZoomUtil.style([G.mapCover], {background: 'transparent', opacity: 1, filter: 'alpha(opacity=100)'});
  DragZoomUtil.style([G.outlineDiv], {left: G.startX + 'px', top: G.startY + 'px', display: 'block', width: '1px', height: '1px'});
  G.draggingOn = true;

  G.cornerTopDiv.style.top = (G.startY - G.mapHeight) + 'px';
  G.cornerTopDiv.style.display ='block';
  G.cornerLeftDiv.style.left = (G.startX - G.mapWidth) +'px';
  G.cornerLeftDiv.style.top = G.startY + 'px';
  G.cornerLeftDiv.style.display = 'block';

  G.cornerRightDiv.style.left = G.startX + 'px';
  G.cornerRightDiv.style.top = G.startY + 'px';
  G.cornerRightDiv.style.display = 'block';
  G.cornerBottomDiv.style.left = G.startX + 'px';
  G.cornerBottomDiv.style.top = G.startY + 'px';
  G.cornerBottomDiv.style.width = '0px';
  G.cornerBottomDiv.style.display = 'block';

  // invoke the callback if provided
  if (G.callbacks.dragstart != null) {
    G.callbacks.dragstart(G.startX, G.startY);
  }

  return false;
};

/**
 * Function called when drag event is captured
 * @param {Object} e 
 */
DragZoomControl.prototype.drag_ = function(e){
  var G = this.globals;
  if(G.draggingOn) {
    var pos = this.getRelPos_(e);
    rect = this.getRectangle_(G.startX, G.startY, pos, G.mapRatio);

    if (rect.left) {
      addX = -rect.width;			
    } else { 
      addX = 0;
    }

    if (rect.top) {
      addY = -rect.height;
    } else {
      addY = 0;
    }

    DragZoomUtil.style([G.outlineDiv], {left: G.startX + addX + 'px', top: G.startY + addY + 'px', display: 'block', width: '1px', height: '1px'});	
	
    G.outlineDiv.style.width = rect.width + "px";
    G.outlineDiv.style.height = rect.height + "px";

    G.cornerTopDiv.style.height = ((G.startY + addY) - (G.startY - G.mapHeight)) + 'px';
    G.cornerLeftDiv.style.top = (G.startY + addY) + 'px';
    G.cornerLeftDiv.style.width = ((G.startX + addX) - (G.startX - G.mapWidth)) + 'px';
    G.cornerRightDiv.style.top = G.cornerLeftDiv.style.top;
    G.cornerRightDiv.style.left = (G.startX + addX + rect.width + G.borderCorrection) + 'px';
    G.cornerBottomDiv.style.top = (G.startY + addY + rect.height + G.borderCorrection) + 'px';
    G.cornerBottomDiv.style.left = (G.startX - G.mapWidth + ((G.startX + addX) - (G.startX - G.mapWidth))) + 'px';
    G.cornerBottomDiv.style.width = (rect.width + G.borderCorrection) + 'px';
		
    // invoke callback if provided
    if (G.callbacks.dragging != null) {
      G.callbacks.dragging(G.startX, G.startY, rect.endX, rect.endY)
    }
		
    return false;
  }  
};

/** 
 * Function called when mouseup event is captured
 * @param {Event} e
 */
DragZoomControl.prototype.mouseup_ = function(e){
  var G = this.globals;
  if (G.draggingOn) {
    var pos = this.getRelPos_(e);
    G.draggingOn = false;
    
    var rect = this.getRectangle_(G.startX, G.startY, pos, G.mapRatio);

    if (rect.left) rect.endX = rect.startX - rect.width;
    if (rect.top) rect.endY = rect.startY - rect.height;
	
    this.resetDragZoom_();

    var nwpx = new GPoint(rect.startX, rect.startY);
    var nepx = new GPoint(rect.endX, rect.startY);
    var sepx = new GPoint(rect.endX, rect.endY);
    var swpx = new GPoint(rect.startX, rect.endY);
    var nw = G.map.fromContainerPixelToLatLng(nwpx); 
    var ne = G.map.fromContainerPixelToLatLng(nepx); 
    var se = G.map.fromContainerPixelToLatLng(sepx); 
    var sw = G.map.fromContainerPixelToLatLng(swpx); 

    var n = nw.lat();
    var s = se.lat();
    var w = nw.lng();
    var e = se.lng();

    if (n > 85 || s < -85) alert("Latitude must not be outside of the map.");
    else if (w > e ) alert("Longitude wrap around (west coord > east coord)\n is not yet supported... Just try another region...");
    else {

        // map
        var zoom = G.map.getZoom();
        var tiles = Math.pow(2, zoom);
        var size = 256;
        var width = tiles * size;
        var radius = width / 2;

        // viewport
        var x0 = (w + 180) * (width / 360);
            x0 = Math.round(x0);
        var x1 = (e + 180) * (width / 360);
            x1 = Math.round(x1);

        var y0 = 0.5 * Math.log(Math.tan((n * Math.PI) / (360) + (Math.PI / 4)));
            y0 = (y0 * 180) / Math.PI; // toDegrees
            y0 = radius - y0 * (radius / 90);
            y0 = Math.round(y0);

        var y1 = 0.5 * Math.log(Math.tan((s * Math.PI) / (360) + (Math.PI / 4)));
            y1 = (y1 * 180) / Math.PI; // toDegrees
            y1 = radius - y1 * (radius / 90);
            y1 = Math.round(y1);

        var coords = x0 + "," + y0 + "," + x1 + "," + y1;
		// + 14 fuer das padding, wenn Snapshot im Minifenster angezeigt wird...
        var width = x1 - x0 + 14;
        var height = y1 - y0 + 15;

		if (width < 30 || height < 30) alert("The selected area was too small or your click was too fast.\n To take a snapshot click in the map but hold the mouse\n button down. Then drag the mouse and select a region.");
        else {

          // tiles
          var tiles = "";
          var numberx = 0;
          var numbery = 0;
          for (var y = 256 * Math.floor(y0 / 256); y <= y1; y += 256) {
            numbery++;
            var ty = Math.floor(y / 256);
            for (var x = 256 * Math.floor(x0 / 256); x <= x1; x += 256) {
              if (numbery == 1) numberx++;
              var tx = Math.floor(x / 256);
              tiles = tiles + zoom + "_" + tx + "_" + ty + ",";
            }
          }
          tiles = tiles.substr(0, tiles.length-1); // abschliessendes Komma entfernen
          var number = numberx + "," + numbery;

          // layer
		  var layer = "";
		  if (forestLayer.visible) layer = layer + "forest,";
		  if (grassLayer.visible) layer = layer + "grass,";
		  if (cropLayer.visible) layer = layer + "crop,";
		  if (tundraLayer.visible) layer = layer + "tundra,";
		  if (swampLayer.visible) layer = layer + "swamp,";
		  if (sandLayer.visible) layer = layer + "sand,";
		  if (iceLayer.visible) layer = layer + "ice,";
		  if (waterLayer.visible) layer = layer + "water,";
		  if (adminLayer.visible) layer = layer + "admin,";
		  if (streetLayer.visible) layer = layer + "streets,";
		  if (tileLayer.visible) layer = layer + "tiles,";
		  if (countryLayer.visible) layer = layer + "country,";
		  if (layer != "") layer = layer.substr(0, layer.length-1);		

          openwindow("http://www.maps-for-free.com/php/snapshot.php?coords=" + coords + "&number=" + number + "&tiles=" + tiles + "&layer=" + layer, width, height);
		}
    }

// HBr...
/*
    var zoomAreaPoly = new GPolyline([nw, ne, se, sw, nw], G.style.outlineColor, G.style.outlineWidth + 1,.4);

    try{
      G.map.addOverlay(zoomAreaPoly);
      setTimeout (function() {G.map.removeOverlay(zoomAreaPoly)}, G.options.overlayRemoveTime);  
    }catch(e) {}

    polyBounds = zoomAreaPoly.getBounds();
    var ne = polyBounds.getNorthEast();
    var sw = polyBounds.getSouthWest();
    var se = new GLatLng(sw.lat(), ne.lng());
    var nw = new GLatLng(ne.lat(), sw.lng());
    zoomLevel = G.map.getBoundsZoomLevel(polyBounds);
    center = polyBounds.getCenter();
    G.map.setCenter(center, zoomLevel);

    // invoke callback if provided
    if (G.callbacks.dragend != null) {
      G.callbacks.dragend(nw, ne, se, sw, nwpx, nepx, sepx, swpx);
    }

    //re-init if sticky
    if (G.options.stickyZoomEnabled) {
      //GLog.write("stickyZoomEnabled, re-initting");
      this.initCover_();
      if (G.options.backButtonEnabled) this.saveBackContext_(G.options.backButtonHTML,false); // save the map context for back button
      G.backButtonDiv.style.display='none';
    }
*/
  }
};

/**
 * Set the cover sizes according to the size of the map
 */
DragZoomControl.prototype.setDimensions_ = function() {
  var G = this.globals;
  var mapSize = G.map.getSize();
  G.mapWidth  = mapSize.width;
  G.mapHeight = mapSize.height;
  G.mapRatio  = G.mapHeight / G.mapWidth;
  // set left:0px in next <div>s in case we inherit text-align:center from map <div> in IE.
  DragZoomUtil.style([G.mapCover, G.cornerTopDiv, G.cornerRightDiv, G.cornerBottomDiv, G.cornerLeftDiv], 
    {left: '0px',width: G.mapWidth + 'px', height: G.mapHeight +'px'});
};

/**
 * Initializes styles based on global parameters
 */
DragZoomControl.prototype.initStyles_ = function(){
  var G = this.globals;
  DragZoomUtil.style([G.mapCover, G.cornerTopDiv, G.cornerRightDiv, G.cornerBottomDiv, G.cornerLeftDiv], 
    {filter: G.style.alphaIE, opacity: G.style.opacity, background:G.style.fillColor});
  G.outlineDiv.style.border = G.style.border;  
};

/**
 * Function called when the zoom button's click event is captured.
 */
DragZoomControl.prototype.buttonclick_ = function(){
  var G = this.globals;	
  G.backButtonDiv.style.display='none';
  if (G.mapCover.style.display == 'block') { // reset if clicked before dragging 
    this.resetDragZoom_();
    if (G.options.backButtonEnabled) {  
      this.restoreBackContext_();  // pop the backStack on a button reset
      if (G.backStack.length==0) G.backButtonDiv.style.display='none';
    }
  } else {
    this.initCover_();
    if ( G.options.backButtonEnabled ) this.saveBackContext_(G.options.backButtonHTML,false); // save the map context for back button
  }
};

/**
 * Back Button functionality:	
 * Function called when the back button's click event is captured.
 * calls the function to set the map context back to where it was before the zoom.
 */
DragZoomControl.prototype.backbuttonclick_ = function(){
  var G = this.globals;	
  if (G.options.backButtonEnabled && G.backStack.length > 0) {
    this.restoreBackContext_();
    // invoke the callback if provided
    if (G.callbacks['backbuttonclick'] != null) {
      G.callbacks.backbuttonclick(G.methodCall);
    }
  }
};

/** 
 * Back Button functionality:	
 * Saves the map context and pushes it on the backStack for later use by the back button
 */
DragZoomControl.prototype.saveBackContext_ = function(text,methodCall) {
  var G = this.globals;
  var backFrame = {};
  backFrame["center"] = G.map.getCenter();
  backFrame["zoom"] = G.map.getZoom();
  backFrame["maptype"] = G.map.getCurrentMapType();
  backFrame["text"] = G.backButtonDiv.innerHTML; // this saves the previous button text
  backFrame["methodCall"] = methodCall; //This determines if it was an internal or method call
  G.backStack.push(backFrame);
  G.backButtonDiv.innerHTML = text;
  // Back Button is turned on in resetDragZoom_()
};

/** 
 * Back Button functionality:	
 * Pops the previous map context off of the backStack and restores the map to that context
 */
DragZoomControl.prototype.restoreBackContext_ = function() {
  var G = this.globals;
  var backFrame = G.backStack.pop();
  G.map.setCenter(backFrame["center"],backFrame["zoom"],backFrame["maptype"]);
  G.backButtonDiv.innerHTML = backFrame["text"];
  G.methodCall = backFrame["methodCall"];
  if (G.backStack.length==0) G.backButtonDiv.style.display = 'none'; // if we're at the top of the stack, hide the back botton
};

/**
 * Shows the cover over the map
 */
DragZoomControl.prototype.initCover_ = function(){
  var G = this.globals;
  G.mapPosition = DragZoomUtil.getElementPosition(G.map.getContainer());
  this.setDimensions_();
  this.setButtonMode_('zooming');
  DragZoomUtil.style([G.mapCover], {display: 'block', background: G.style.fillColor});
  DragZoomUtil.style([G.outlineDiv], {width: '0px', height: '0px'});

  //invoke callback if provided
  if(G.callbacks['buttonclick'] != null){
    G.callbacks.buttonclick();
  }
};

/**
 * Gets position of the mouse relative to the map
 * @param {Object} e
 */
DragZoomControl.prototype.getRelPos_ = function(e) {
  var pos = DragZoomUtil.getMousePosition(e);
  var G = this.globals;
  return {top: (pos.top - G.mapPosition.top), 
          left: (pos.left - G.mapPosition.left)};
};

/**
 * Figures out the rectangle the user's trying to draw
 * @param {Number} startX 
 * @param {Number} startY
 * @param {Object} pos
 * @param {Number} ratio
 * @return {Object} Describes the rectangle
 */
// HBr...
DragZoomControl.prototype.getRectangle_ = function(startX, startY, pos, ratio){
  var left = false;
  var top = false;
  var dX = pos.left - startX;
  var dY = pos.top - startY;	
  if (dX < 0) dX = 0;
  if (dY < 0) dY = 0;

  return {
    startX: startX,
    startY: startY,
    endX: startX+dX,
    endY: startY+dY,
    width: dX,
    height: dY,
    left:left,
    top:top
  }

/*
  var left = false;
  var top = false;
  var dX = pos.left - startX;
  var dY = pos.top - startY;	
  if (dX < 0) {
    dX = dX * -1;
    left = true;
  }
  if (dY < 0) {
    dY = dY * -1;
    top = true;
  }
  delta = dX > dY ? dX : dY;

  return {
    startX: startX,
    startY: startY,
    endX: startX + delta,
    endY: startY + parseInt(delta * ratio),
    width: delta,
    height: parseInt(delta * ratio),
    left:left,
    top:top
  }
*/
};

/** 
 * Resets CSS and button display when drag zoom done
 */
DragZoomControl.prototype.resetDragZoom_ = function() {
  var G = this.globals;
  DragZoomUtil.style([G.mapCover, G.cornerTopDiv, G.cornerRightDiv, G.cornerBottomDiv, G.cornerLeftDiv], 
    {display: 'none', opacity: G.style.opacity, filter: G.style.alphaIE});
  G.outlineDiv.style.display = 'none';	
  this.setButtonMode_('normal');
  if (G.options.backButtonEnabled  && (G.backStack.length > 0)) G.backButtonDiv.style.display = 'block'; // show the back button
};

/* utility functions in DragZoomUtil.namespace */
var DragZoomUtil={};

/**
 * Alias function for getting element by id
 * @param {String} sId
 * @return {Object} DOM object with sId id
 */
DragZoomUtil.gE = function(sId) {
  return document.getElementById(sId);
}

/**
 * A general-purpose function to get the absolute position
 * of the mouse.
 * @param {Object} e  Mouse event
 * @return {Object} Describes position
 */
DragZoomUtil.getMousePosition = function(e) {
  var posX = 0;
  var posY = 0;
  if (!e) var e = window.event;
  if (e.pageX || e.pageY) {
    posX = e.pageX;
    posY = e.pageY;
  } else if (e.clientX || e.clientY){
    posX = e.clientX + 
      (document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft);
    posY = e.clientY + 
      (document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop);
  }	
  return {left: posX, top: posY};  
};

/**
 * Gets position of element
 * @param {Object} element
 * @return {Object} Describes position
 */
DragZoomUtil.getElementPosition = function(element) {
  var leftPos = element.offsetLeft;          // initialize var to store calculations
  var topPos = element.offsetTop;            // initialize var to store calculations
  var parElement = element.offsetParent;     // identify first offset parent element  
  while (parElement != null ) {                // move up through element hierarchy
    leftPos += parElement.offsetLeft;      // appending left offset of each parent
    topPos += parElement.offsetTop;  
    parElement = parElement.offsetParent;  // until no more offset parents exist
  }
  return {left: leftPos, top: topPos};
};

/**
 * Applies styles to DOM objects 
 * @param {String/Object} elements Either comma-delimited list of ids 
 *   or an array of DOM objects
 * @param {Object} styles Hash of styles to be applied
 */
DragZoomUtil.style = function(elements, styles){
  if (typeof(elements) == 'string') {
    elements = DragZoomUtil.getManyElements(elements);
  }
  for (var i = 0; i < elements.length; i++){
    for (var s in styles) { 
      elements[i].style[s] = styles[s];
    }
  }
};

/**
 * Gets DOM elements array according to list of IDs
 * @param {String} elementsString Comma-delimited list of IDs
 * @return {Array} Array of DOM elements corresponding to s
 */
DragZoomUtil.getManyElements = function(idsString){		
  var idsArray = idsString.split(',');
  var elements = [];
  for (var i = 0; i < idsArray.length; i++){
    elements[elements.length] = DragZoomUtil.gE(idsArray[i])
  };
  return elements;
};
