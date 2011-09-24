var seismap = {
  init : function(baseUrl, page, user) {
    seismap.baseUrl = baseUrl;
    seismap.user = user;
    var minLengthValidator = Form.Validator.getValidator('minLength');
    var emailValidator = Form.Validator.getValidator('validate-email');
    var integerValidator = Form.Validator.getValidator('validate-integer');
    Form.Validator.add('validate-email-or-integer', {
      errorMsg : function(element, props) {
        return emailValidator.getError(element, props) + ' or '
            + integerValidator.getError(element, props);
      },
      test : function(element, props) {
        return emailValidator.test(element, props)
            || integerValidator.test(element, props);
      }
    });
    Form.Validator
        .add(
            'emptyOrMinLength',
            {
              errorMsg : function(element, props) {
                if (typeOf(props.emptyOrMinLength) != 'null')
                  return Form.Validator.getMsg('minLength').substitute({
                    minLength : props.emptyOrMinLength,
                    length : element.get('value').length
                  });
                else
                  return '';
              },
              test : function(element, props) {
                if (element.get('value').length > 0
                    && typeOf(props.emptyOrMinLength) != 'null')
                  return (element.get('value').length >= (props.emptyOrMinLength || 0));
                else
                  return true;
              }
            });
    if (page) {
      if (page.init) {
        window.addEvent('domready', page.init.bind(page));
      }
    }
  },
  validatorOptions : {
    scrollToErrorsOnSubmit : false,
    evaluateOnSubmit : false,
    evaluateFieldsOnBlur : false,
    evaluateFieldsOnChange : false
  },
  showAlert : function(message, callback, title, ok) {
    var dialog = new MooDialog.Alert(message, {
      title : title == undefined ? 'Seismap' : title,
      okText : ok == undefined ? 'Aceptar' : ok
    });
    if (callback) {
      dialog.addEvent('close', callback);
    }
    dialog.open();
  },
  showAlertAndFocus : function(message, element, title, ok) {
    var dialog = new MooDialog.Alert(message, {
      title : title == undefined ? 'Seismap' : title,
      okText : ok == undefined ? 'Aceptar' : ok
    });
    dialog.addEvent('close', function() {
      element.focus();
    });
    dialog.open();
  },
  showPrompt : function(message, okCallback, cancelCallback, defaultValue,
      title, ok) {
    var accepted = false;
    var dialog = new MooDialog.Prompt(message, function(text) {
      accepted = true;
      okCallback(text);
    }, {
      title : title == undefined ? 'Seismap' : title,
      okText : ok == undefined ? 'Aceptar' : ok,
      defaultValue : defaultValue
    });
    if (cancelCallback) {
      dialog.addEvent('close', function(event) {
        if (!accepted) {
          cancelCallback(event);
        }
      });
    }
    dialog.open();
  },
  showConfirm : function(message, yesCallback, noCallback, title, ok, cancel) {
    var dialog = new MooDialog.Confirm(message, yesCallback, noCallback, {
      title : title == undefined ? 'Seismap' : title,
      okText : ok == undefined ? 'Aceptar' : ok,
      cancelText : cancel == undefined ? 'Cancelar' : cancel
    });
    dialog.open();
  },
  showError : function(message, callback, title, ok) {
    var dialog = new MooDialog.Error(message, {
      title : title == undefined ? 'Seismap' : title,
      okText : ok == undefined ? 'Aceptar' : ok
    });
    if (callback) {
      dialog.addEvent('close', callback);
    }
    dialog.open();
  },
  showGenericError : function() {
    seismap.showError('Ups! Hubo un error. Perdón!');
  },
  request : function(action, data, callback, errorCallback) {
    if (!errorCallback) {
      errorCallback = seismap.showGenericError;
    }
    var request = new Request.JSON({
      headers : {
        'Content-Type' : 'application/json'
      },
      urlEncoded : false,
      url : seismap.baseUrl + '/action/' + action,
      method : 'post',
      onSuccess : callback,
      onError : errorCallback,
      onFailure : errorCallback
    });
    request.post(JSON.encode(data));
  },
  requestAndGo : function(action, data, target) {
    seismap.request(action, data, function(response) {
      if (response.exception) {
        seismap.showError(response.exception.message);
      } else {
        if (typeof target == 'function') {
          destination = target(response);
        } else {
          destination = target;
        }
        window.location = seismap.baseUrl + '/' + destination;
      }
    });
  },
  hide : function(element, callback) {
    var originalHeight = element.style.height;
    var originalOverflow = element.getStyle('overflow');
    element.setStyle('overflow', 'hidden');
    var fx = new Fx.Tween(element, {
      property : 'height',
      transition : Fx.Transitions.Quart.easeOut
    });
    fx.addEvent('complete', function() {
      element.setStyle('display', 'none');
      element.setStyle('height', originalHeight);
      element.setStyle('overflow', originalOverflow);
      element.setStyle('display', 'none');
      if (callback) {
        callback();
      }
    });
    fx.start(0);
  },
  show : function(element, callback) {
    element.setStyle('visibility', 'hidden');
    element.setStyle('display', 'block');
    var realHeight = element.getSize().y;
    var originalHeight = element.style.height;
    var originalOverflow = element.getStyle('overflow');
    element.setStyle('overflow', 'hidden');
    element.setStyle('height', 0);
    element.setStyle('visibility', 'visible');
    var fx = new Fx.Tween(element, {
      property : 'height',
      transition : Fx.Transitions.Quart.easeOut
    });
    fx.addEvent('complete', function() {
      element.setStyle('display', 'none');
      element.setStyle('height', originalHeight);
      element.setStyle('overflow', originalOverflow);
      element.setStyle('display', 'block');
      if (callback) {
        callback();
      }
    });
    fx.start(realHeight);
  },
  twoDigitsNumber : function(number) {
    if (number < 10) {
      return '0' + number;
    } else {
      return '' + number;
    }
  },
  FLOAT_REG_EXP : /^ *[+-]? *\d*\.?\d* *$/,
  parseFloat : function(text) {
    if (text == null || text == undefined || text.length == 0
        || !seismap.FLOAT_REG_EXP.test(text)) {
      return null;
    }
    return parseFloat(text);
  },
  formatFloat : function(number) {
    if (number == null || number == undefined || isNaN(number)) {
      return '';
    }
    var text = number.toString();
    if (text.indexOf('.') == -1) {
      text += '.0';
    }
    return text;
  },
  INT_REG_EXP : /^ *[+-]? *\d* *$/,
  parseInt : function(text) {
    if (text == null || text == undefined || text.length == 0
        || !seismap.INT_REG_EXP.test(text)) {
      return null;
    }
    return parseInt(text, 10);
  },
  formatInt : function(number) {
    if (number == null || number == undefined || isNaN(number)) {
      return '';
    }
    var text = number.toString();
    return text;
  },
  DATE_REG_EXP : /^ *(\d\d\d\d)-(\d?\d)-(\d?\d) *$/,
  TIME_REG_EXP : /^ *(\d?\d):(\d?\d):(\d?\d) *$/,
  parseDateTime : function(dateText, timeText) {
    var dateMatches;
    var timeMatches;
    if (dateText == null || timeText == null || dateText == undefined
        || timeText == undefined || dateText.length == 0
        || timeText.length == 0
        || !(dateMatches = seismap.DATE_REG_EXP.exec(dateText))
        || !(timeMatches = seismap.TIME_REG_EXP.exec(timeText))) {
      return null;
    }
    var year = parseInt(dateMatches[1], 10);
    var month = parseInt(dateMatches[2], 10) - 1;
    var date = parseInt(dateMatches[3], 10);
    var hours = parseInt(timeMatches[1], 10);
    var minutes = parseInt(timeMatches[2], 10);
    var seconds = parseInt(timeMatches[3], 10);
    var milliseconds = 0;
    var date = new Date(year, month, date, hours, minutes, seconds,
        milliseconds);
    return date;
  },
  formatDate : function(date) {
    if (date == null || date == undefined) {
      return '';
    }
    var text = '' + date.getFullYear() + '-'
        + seismap.twoDigitsNumber(date.getMonth() + 1) + '-'
        + seismap.twoDigitsNumber(date.getDate());
    return text;
  },
  formatTime : function(date) {
    if (date == null || date == undefined) {
      return '';
    }
    var text = '' + seismap.twoDigitsNumber(date.getHours()) + ':'
        + seismap.twoDigitsNumber(date.getMinutes()) + ':'
        + seismap.twoDigitsNumber(date.getSeconds());
    return text;
  }
};

var basePage = {
  init : function(options) {
    options = options == undefined ? {} : options;
    options.logged = options.logged == undefined ? true : options.logged;
    document.id('newCategory').addEvent('click',
        basePage.newCategory.bind(basePage));
    document.id('newMap').addEvent('click', basePage.newMap.bind(basePage));
  },
  newCategory : function() {
    seismap.showPrompt('Ingrese el nombre para la nueva categoría:',
        basePage.createCategory.bind(basePage), null, '', 'Nueva Categoría',
        'Crear');
    return false;
  },
  createCategory : function(text) {
    var data = {
      categoryName : text
    };
    seismap.requestAndGo('category/create', data, '');
  },
  newMap : function() {
    seismap.showPrompt('Ingrese el nombre para el nuevo mapa:',
        basePage.createMap.bind(basePage), null, '', 'Nueva Mapa', 'Crear');
    return false;
  },
  createMap : function(text) {
    var data = {
      userId : seismap.user.id,
      mapName : text
    };
    seismap.requestAndGo('map/create', data, function(response) {
      return 'map/' + response.value.id;
    });
  }
};
var homePage = {
  init : function() {
    basePage.init({
      logged : false
    });
  }
};

var mapPage = {
  init : function() {
    basePage.init({
      logged : false
    });
    this.minWait = -1;

    OpenLayers.ImgPath = seismap.baseUrl + "/resources/css/lib/openlayers/img/"

    var bounds = new OpenLayers.Bounds(-11233695.257, -7183045.364,
        19951346.826, -1055981.386);
    var options = {
      controls : [],
      maxExtent : bounds,
      maxResolution : 121816.57063671875,
      projection : "EPSG:900913",
      units : 'm',
      theme : seismap.baseUrl
          + "/resources/css/lib/openlayers/theme/default/style.css"
    };

    this.map = new OpenLayers.Map('map', options);

    var gphy = new OpenLayers.Layer.Google("Google Physical", {
      type : google.maps.MapTypeId.TERRAIN
    });
    var gmap = new OpenLayers.Layer.Google("Google Streets", // the default
    {
      numZoomLevels : 20
    });
    var ghyb = new OpenLayers.Layer.Google("Google Hybrid", {
      type : google.maps.MapTypeId.HYBRID,
      numZoomLevels : 20
    });
    var gsat = new OpenLayers.Layer.Google("Google Satellite", {
      type : google.maps.MapTypeId.SATELLITE,
      numZoomLevels : 22
    });

    this.map.addLayers([ gphy, gmap, ghyb, gsat ]);

    // build up all controls
    this.map.addControl(new OpenLayers.Control.PanZoomBar({
      position : new OpenLayers.Pixel(2, 15)
    }));
    this.map.addControl(new OpenLayers.Control.Navigation());
    this.map.addControl(new OpenLayers.Control.Scale(document.id('scale')));
    this.map.addControl(new OpenLayers.Control.MousePosition({
      element : document.id('location')
    }));
    this.map.zoomToExtent(bounds);
    this.registerControlHandles();
    this.currentFrame = -1
    this.changed(true);
  },
  // var layer = new
  // google.maps.KmlLayer('http://gmaps-samples.googlecode.com/svn/trunk/ggeoxml/cta.kml');

  // this.map.overlayMapTypes.push(null);
  // this.map.overlayMapTypes.setAt("0", this.layer);

  /*
   * this.layer = new google.maps.ImageMapType({ getTileUrl : function(tile,
   * zoom) { return seismap.baseUrl + '/map/' + mapPage.mapData.id + '/tile/' +
   * zoom + '-' + tile.x + '-' + tile.y + '.png'; }, tileSize : new
   * google.maps.Size(256, 256), opacity : 0.60, isPng : true });
   * this.map.overlayMapTypes.push(null); this.map.overlayMapTypes.setAt("0",
   * this.layer);
   */
  registerControlHandles : function() {
    var form = document.id(document.forms.mapConfiguration);
    var nameControl = document.id(form.name);
    var descriptionControl = document.id(form.description);
    var centerLatitudeControl = document.id(form.centerLatitude);
    var centerLongitudeControl = document.id(form.centerLongitude);
    var zoomControl = document.id(form.zoom);
    var styleControl = document.id(form.style);
    var minDateTypeNoneControl = form
        .getElement('input[name="minDateType"][value="NONE"]');
    var minDateTypeRelativeControl = form
        .getElement('input[name="minDateType"][value="RELATIVE"]');
    var minDateTypeAbsoluteControl = form
        .getElement('input[name="minDateType"][value="ABSOLUTE"]');
    var minDateRelativeAmountControl = document.id(form.minDateRelativeAmount);
    var minDateRelativeUnitsControl = document.id(form.minDateRelativeUnits);
    var minDateControl = document.id(form.minDate);
    var minTimeControl = document.id(form.minTime);
    var maxDateTypeNoneControl = form
        .getElement('input[name="maxDateType"][value="NONE"]');
    var maxDateTypeRelativeControl = form
        .getElement('input[name="maxDateType"][value="RELATIVE"]');
    var maxDateTypeAbsoluteControl = form
        .getElement('input[name="maxDateType"][value="ABSOLUTE"]');
    var maxDateRelativeAmountControl = document.id(form.maxDateRelativeAmount);
    var maxDateRelativeUnitsControl = document.id(form.maxDateRelativeUnits);
    var maxDateControl = document.id(form.maxDate);
    var maxTimeControl = document.id(form.maxTime);
    var maxDepthTypeNoneControl = form
        .getElement('input[name="maxDepthType"][value="NONE"]');
    var maxDepthTypeAbsoluteControl = form
        .getElement('input[name="maxDepthType"][value="ABSOLUTE"]');
    var maxDepthControl = document.id(form.maxDepth);
    var minDepthTypeNoneControl = form
        .getElement('input[name="minDepthType"][value="NONE"]');
    var minDepthTypeAbsoluteControl = form
        .getElement('input[name="minDepthType"][value="ABSOLUTE"]');
    var minDepthControl = document.id(form.minDepth);
    var magnitudeTypeControl = document.id(form.magnitudeType);
    var listUnmeasuredControl = document.id(form.listUnmeasured);
    var animationTypeControl = document.id(form.animationType);
    var animationStepsControl = document.id(form.animationSteps);
    var animationStepDurationControl = document.id(form.animationStepDuration);
    var animationStepKeepControl = document.id(form.animationStepKeep);
    var reverseAnimationControl = document.id(form.reverseAnimation);
    var showInitialViewLink = document.id('showInitialViewLink');
    var useCurrentViewLink = document.id('useCurrentViewLink');
    // Name
    nameControl.addEvent('blur', function() {
      var value = nameControl.value;
      if (value == 0) {
        seismap.showAlertAndFocus('El nombre no puede estar vacío.',
            nameControl);
      } else {
        if (mapPage.mapData.name != value) {
          mapPage.mapData.name = value;
          mapPage.changed(true);
        }
      }
    });
    // Description
    descriptionControl.addEvent('blur', function() {
      var value = descriptionControl.value;
      if (mapPage.mapData.description != value) {
        mapPage.mapData.description = value;
        mapPage.changed(true);
      }
    });
    // Center Latitude
    centerLatitudeControl.addEvent('blur', function() {
      var value = seismap.parseFloat(centerLatitudeControl.value);
      if (value == null || value < -90 || value > 90) {
        seismap.showAlertAndFocus('La latitude debe estar entre -90º y 90º',
            centerLatitudeControl);
      } else {
        centerLatitudeControl.value = seismap.formatFloat(value);
        if (mapPage.mapData.centerLatitude != value) {
          mapPage.mapData.centerLatitude = value;
          mapPage.changed(false);
        }
      }
    });
    // Center Longitude
    centerLongitudeControl.addEvent('blur', function() {
      var value = seismap.parseFloat(centerLongitudeControl.value);
      if (value == null || value < -180 || value > 180) {
        seismap.showAlertAndFocus('La longitud debe estar entre -180º y 180º',
            centerLongitudeControl);
      } else {
        centerLongitudeControl.value = seismap.formatFloat(value);
        if (mapPage.mapData.centerLongitude != value) {
          mapPage.mapData.centerLongitude = value;
          mapPage.changed(false);
        }
      }
    });
    // Zoom
    zoomControl.addEvent('blur', function() {
      var value = seismap.parseInt(zoomControl.value);
      if (value == null || value < 0 || value > 21) {
        seismap.showAlertAndFocus('El zoom debe estar entre 0 y 21',
            zoomControl);
      } else {
        zoomControl.value = seismap.formatInt(value);
        if (mapPage.mapData.zoom != value) {
          mapPage.mapData.zoom = value;
          mapPage.changed(false);
        }
      }
    });
    // Use Current View
    useCurrentViewLink.addEvent('click', function() {
      var center = mapPage.map.getCenter();
      var latitudeValue = center.lat();
      var longitudeValue = center.lng();
      var zoomValue = mapPage.map.getZoom();
      longitudeValue = ((((longitudeValue + 180) % 360) + 360) % 360) - 180;
      centerLatitudeControl.value = seismap.formatFloat(latitudeValue);
      centerLongitudeControl.value = seismap.formatFloat(longitudeValue);
      zoomControl.value = seismap.formatInt(zoomValue);
      if (mapPage.mapData.centerLatitude != latitudeValue
          || mapPage.mapData.centerLongitude != longitudeValue
          || mapPage.mapData.zoom != zoomValue) {
        mapPage.mapData.centerLatitude = latitudeValue;
        mapPage.mapData.centerLongitude = longitudeValue;
        mapPage.mapData.zoom = zoomValue;
        mapPage.changed(false);
      }
      return false;
    });
    // Show Initial View
    showInitialViewLink.addEvent('click', function() {
      mapPage.map.setCenter(new google.maps.LatLng(
          mapPage.mapData.centerLatitude, mapPage.mapData.centerLongitude));
      mapPage.map.setZoom(mapPage.mapData.zoom);
      return false;
    });
    // Style
    styleControl.addEvent('change', function() {
      var value = styleControl.value;
      if (mapPage.mapData.style.id != value) {
        mapPage.mapData.style = mapPage.styles[value];
        mapPage.changed(true);
      }
    });
    // Min Date - None
    minDateTypeNoneControl.addEvent('click', function() {
      minDateRelativeAmountControl.disabled = true;
      minDateRelativeUnitsControl.disabled = true;
      minDateControl.disabled = true;
      minTimeControl.disabled = true;
      if (mapPage.mapData.minDateType != 'NONE') {
        mapPage.mapData.minDateType = 'NONE';
        mapPage.changed(true);
      }
    });
    // Min Date - Relative
    minDateTypeRelativeControl.addEvent('click', function() {
      minDateRelativeAmountControl.disabled = false;
      minDateRelativeUnitsControl.disabled = false;
      minDateControl.disabled = true;
      minTimeControl.disabled = true;
      if (mapPage.mapData.minDateType != 'RELATIVE') {
        mapPage.mapData.minDateType = 'RELATIVE';
        mapPage.changed(true);
      }
    });
    minDateRelativeAmountControl.addEvent('blur', function() {
      var value = seismap.parseFloat(minDateRelativeAmountControl.value);
      if (value == null || value < 0) {
        seismap.showAlertAndFocus(
            'La fecha relativa mínima debe ser mayor a cero',
            minDateRelativeAmountControl);
      } else {
        minDateRelativeAmountControl.value = seismap.formatFloat(value);
        if (mapPage.mapData.minDateRelativeAmount != value) {
          mapPage.mapData.minDateRelativeAmount = value;
          mapPage.changed(true);
        }
      }
    });
    minDateRelativeUnitsControl.addEvent('change', function() {
      var value = minDateRelativeUnitsControl.value;
      if (mapPage.mapData.minDateRelativeUnits != value) {
        mapPage.mapData.minDateRelativeUnits = value;
        mapPage.changed(true);
      }
    });
    // Min Date - Absolute
    minDateTypeAbsoluteControl.addEvent('click', function() {
      minDateRelativeAmountControl.disabled = true;
      minDateRelativeUnitsControl.disabled = true;
      minDateControl.disabled = false;
      minTimeControl.disabled = false;
      if (mapPage.mapData.minDateType != 'ABSOLUTE') {
        mapPage.mapData.minDateType = 'ABSOLUTE';
        mapPage.changed(true);
      }
    });
    minDateControl.addEvent('blur', function() {
      var value = seismap.parseDateTime(minDateControl.value,
          minTimeControl.value);
      if (value == null) {
        seismap.showAlertAndFocus('La fecha mínima debe ser válida',
            minDateControl);
      } else {
        minDateControl.value = seismap.formatDate(value);
        minTimeControl.value = seismap.formatTime(value);
        if (mapPage.mapData.minDate != value) {
          mapPage.mapData.minDate = value;
          mapPage.changed(true);
        }
      }
    });
    minTimeControl.addEvent('blur', function() {
      var value = seismap.parseDateTime(minDateControl.value,
          minTimeControl.value);
      if (value == null) {
        seismap.showAlertAndFocus('La hora mínima debe ser válida',
            minTimeControl);
      } else {
        minDateControl.value = seismap.formatDate(value);
        minTimeControl.value = seismap.formatTime(value);
        if (mapPage.mapData.minDate != value) {
          mapPage.mapData.minDate = value;
          mapPage.changed(true);
        }
      }
    });
    // Max Date - None
    maxDateTypeNoneControl.addEvent('click', function() {
      maxDateRelativeAmountControl.disabled = true;
      maxDateRelativeUnitsControl.disabled = true;
      maxDateControl.disabled = true;
      maxTimeControl.disabled = true;
      if (mapPage.mapData.maxDateType != 'NONE') {
        mapPage.mapData.maxDateType = 'NONE';
        mapPage.changed(true);
      }
    });
    // Max Date - Relative
    maxDateTypeRelativeControl.addEvent('click', function() {
      maxDateRelativeAmountControl.disabled = false;
      maxDateRelativeUnitsControl.disabled = false;
      maxDateControl.disabled = true;
      maxTimeControl.disabled = true;
      if (mapPage.mapData.maxDateType != 'RELATIVE') {
        mapPage.mapData.maxDateType = 'RELATIVE';
        mapPage.changed(true);
      }
    });
    maxDateRelativeAmountControl.addEvent('blur', function() {
      var value = seismap.parseFloat(maxDateRelativeAmountControl.value);
      if (value == null || value < 0) {
        seismap.showAlertAndFocus(
            'La fecha relativa máxima debe ser mayor a cero',
            maxDateRelativeAmountControl);
      } else {
        maxDateRelativeAmountControl.value = seismap.formatFloat(value);
        if (mapPage.mapData.maxDateRelativeAmount != value) {
          mapPage.mapData.maxDateRelativeAmount = value;
          mapPage.changed(true);
        }
      }
    });
    maxDateRelativeUnitsControl.addEvent('change', function() {
      var value = maxDateRelativeUnitsControl.value;
      if (mapPage.mapData.maxDateRelativeUnits != value) {
        mapPage.mapData.maxDateRelativeUnits = value;
        mapPage.changed(true);
      }
    });
    // Max Date - Absolute
    maxDateTypeAbsoluteControl.addEvent('click', function() {
      maxDateRelativeAmountControl.disabled = true;
      maxDateRelativeUnitsControl.disabled = true;
      maxDateControl.disabled = false;
      maxTimeControl.disabled = false;
      if (mapPage.mapData.maxDateType != 'ABSOLUTE') {
        mapPage.mapData.maxDateType = 'ABSOLUTE';
        mapPage.changed(true);
      }
    });
    maxDateControl.addEvent('blur', function() {
      var value = seismap.parseDateTime(maxDateControl.value,
          maxTimeControl.value);
      if (value == null || value < 0) {
        seismap.showAlertAndFocus('La fecha máxima debe ser válida',
            maxDateControl);
      } else {
        maxDateControl.value = seismap.formatDate(value);
        maxTimeControl.value = seismap.formatTime(value);
        if (mapPage.mapData.maxDate != value) {
          mapPage.mapData.maxDate = value;
          mapPage.changed(true);
        }
      }
    });
    maxTimeControl.addEvent('blur', function() {
      var value = seismap.parseDateTime(maxDateControl.value,
          maxTimeControl.value);
      if (value == null || value < 0) {
        seismap.showAlertAndFocus('La hora máxima debe ser válida',
            maxTimeControl);
      } else {
        maxDateControl.value = seismap.formatDate(value);
        maxTimeControl.value = seismap.formatTime(value);
        if (mapPage.mapData.maxDate != value) {
          mapPage.mapData.maxDate = value;
          mapPage.changed(true);
        }
      }
    });
    // Max Depth - None
    maxDepthTypeNoneControl.addEvent('click', function() {
      maxDepthControl.disabled = true;
      if (mapPage.mapData.maxDepthType != 'NONE') {
        mapPage.mapData.maxDepthType = 'NONE';
        mapPage.changed(true);
      }
    });
    // Max Depth - Absolute
    maxDepthTypeAbsoluteControl.addEvent('click', function() {
      maxDepthControl.disabled = false;
      if (mapPage.mapData.maxDepthType != 'ABSOLUTE') {
        mapPage.mapData.maxDepthType = 'ABSOLUTE';
        mapPage.changed(true);
      }
    });
    maxDepthControl.addEvent('blur', function() {
      var value = seismap.parseFloat(maxDepthControl.value);
      if (value == null || value < 0) {
        seismap.showAlertAndFocus(
            'La profundidad máxima debe ser mayor a cero', maxDepthControl);
      } else {
        maxDepthControl.value = seismap.formatFloat(value);
        if (mapPage.mapData.maxDepth != value) {
          mapPage.mapData.maxDepth = value;
          mapPage.changed(true);
        }
      }
    });
    // Min Depth - None
    minDepthTypeNoneControl.addEvent('click', function() {
      minDepthControl.disabled = true;
      if (mapPage.mapData.minDepthType != 'NONE') {
        mapPage.mapData.minDepthType = 'NONE';
        mapPage.changed(true);
      }
    });
    // Min Depth - Absolute
    minDepthTypeAbsoluteControl.addEvent('click', function() {
      minDepthControl.disabled = false;
      if (mapPage.mapData.minDepthType != 'ABSOLUTE') {
        mapPage.mapData.minDepthType = 'ABSOLUTE';
        mapPage.changed(true);
      }
    });
    minDepthControl.addEvent('blur', function() {
      var value = seismap.parseFloat(minDepthControl.value);
      if (value == null || value < 0) {
        seismap.showAlertAndFocus(
            'La profundidad mínima debe ser mayor a cero', minDepthControl);
      } else {
        minDepthControl.value = seismap.formatFloat(value);
        if (mapPage.mapData.minDepth != value) {
          mapPage.mapData.minDepth = value;
          mapPage.changed(true);
        }
      }
    });
    // Magnitude Type
    magnitudeTypeControl.addEvent('change', function() {
      var value = magnitudeTypeControl.value;
      if (mapPage.mapData.magnitudeType != value) {
        mapPage.mapData.magnitudeType = value;
        mapPage.changed(true);
      }
    });
    // List Unmeasured
    listUnmeasuredControl.addEvent('click', function() {
      var value = listUnmeasuredControl.checked;
      if (mapPage.mapData.listUnmeasured != value) {
        mapPage.mapData.listUnmeasured = value;
        mapPage.changed(true);
      }
    });
    // Animation Type
    animationTypeControl.addEvent('change', function() {
      var value = animationTypeControl.value;
      var disabled = value == 'NONE';
      animationStepsControl.disabled = disabled;
      animationStepDurationControl.disabled = disabled;
      animationStepKeepControl.disabled = disabled;
      reverseAnimationControl.disabled = disabled;
      if (mapPage.mapData.animationType != value) {
        mapPage.mapData.animationType = value;
        mapPage.changed(true);
      }
    });
    // Animation Steps
    animationStepsControl.addEvent('blur', function() {
      var value = seismap.parseInt(animationStepsControl.value);
      if (value == null || value < 2) {
        seismap.showAlertAndFocus(
            'Los cantidad de cuadros de la animación ser mayor o igual a 2',
            animationStepsControl);
      } else {
        animationStepsControl.value = seismap.formatInt(value);
        if (mapPage.mapData.animationSteps != value) {
          mapPage.mapData.animationSteps = value;
          mapPage.changed(true);
        }
      }
    });
    // Animation Step Duration
    animationStepDurationControl
        .addEvent(
            'blur',
            function() {
              var value = seismap
                  .parseFloat(animationStepDurationControl.value);
              if (value == null || value < 1) {
                seismap
                    .showAlertAndFocus(
                        'Los duración de los cuadros de la animación ser mayor o igual a 1',
                        animationStepDurationControl);
              } else {
                animationStepDurationControl.value = seismap.formatInt(value);
                if (mapPage.mapData.animationStepDuration != value) {
                  mapPage.mapData.animationStepDuration = value;
                  mapPage.changed(true);
                }
              }
            });
    // Animation Step Keep
    animationStepKeepControl
        .addEvent(
            'blur',
            function() {
              var value = seismap.parseFloat(animationStepKeepControl.value);
              if (value == null || value < 0 || value > 100) {
                seismap
                    .showAlertAndFocus(
                        'Los preservación de los cuadros de la animación ser estar entre 0% y 100%',
                        animationStepKeepControl);
              } else {
                animationStepKeepControl.value = seismap.formatFloat(value);
                var realValue = value / 100;
                if (mapPage.mapData.animationStepKeep != realValue) {
                  mapPage.mapData.animationStepKeep = realValue;
                  mapPage.changed(true);
                }
              }
            });
    // Reverse Animation
    reverseAnimationControl.addEvent('click', function() {
      var value = reverseAnimationControl.checked;
      if (mapPage.mapData.reverseAnimation != value) {
        mapPage.mapData.reverseAnimation = value;
        mapPage.changed(true);
      }
    });

  },
  changed : function(reload) {
    document.title = (isNaN(parseInt(document.title)) ? 0
        : parseInt(document.title)) + 1;
    if (!reload) {
      return;
    }
    this.stop();
    if (this.currentFrame != -1) {
      this.removeLayer(this.layers[this.currentFrame]);
      for ( var i = 0; i < this.layers.length; i++) {
        var layer = this.layers[i];
        this.map.removeLayer(layer);
      }
    }
    this.cqlFilters = this.buildCqlFilters(this.mapData, this.dataBounds,
        new Date());
    this.layers = [];
    for ( var i = 0; i < this.cqlFilters.length; i++) {
      var cqlFilter = this.cqlFilters[i];
      document.id('mapUris').value += cqlFilter + '\n';
      this.layers[i] = this.createLayer(cqlFilter);
    }
    this.currentFrame = -1;
    this.start();

  },
  start : function() {
    this.cycle();
    if (this.layers.length > 1) {
      this.animationHandle = setInterval(this.cycle.bind(this),
          this.mapData.animationStepDuration * 1000);
    }
  },
  stop : function() {
    if (this.animationHandle) {
      clearInterval(this.animationHandle);
      this.animationHandle = null;
    }
  },
  nextFrame : function() {
    this.cycle();
    if (this.animationHandle) {
      clearInterval(this.animationHandle);
      this.animationHandle = setInterval(this.cycle.bind(this),
          this.mapData.animationStepDuration * 1000);
    }
  },
  previousFrame : function() {
    this.cycle(true);
    if (this.animationHandle) {
      clearInterval(this.animationHandle);
      this.animationHandle = setInterval(this.cycle.bind(this),
          this.mapData.animationStepDuration * 1000);
    }
  },
  cycle : function(backwards) {
    var current = this.currentFrame;
    if (current != -1) {
      this.removeLayer(this.layers[current]);
    }
    if (backwards == true) {
      current--;
      if (current == -1) {
        current = this.layers.length - 1;
      }
    } else {
      current++;
      if (current == this.layers.length) {
        current = 0;
      }

    }
    this.currentFrame = current;
    this.addLayer(this.layers[current]);
  },
  addLayer : function(layer) {
    layer.seismapAdded = new Date().getTime();
    var timeSinceRemoved = layer.seismapAdded - layer.seismapRemoved;
    var waitTime = this.minWait - timeSinceRemoved;
    if (waitTime <= 0) {
      this.doAddLayer(layer);
    } else {
      setTimeout(this.doAddLayer.bind(this, layer), waitTime);
    }
  },
  doAddLayer : function(layer) {
    if (layer.seismapAdded > layer.seismapRemoved) {
      layer.seismapAdded = new Date().getTime();
      if (!layer.getVisibility()) {
        layer.setVisibility(true);
      }
    }
  },
  removeLayer : function(layer) {
    layer.seismapRemoved = new Date().getTime();
    var timeSinceAdded = layer.seismapRemoved - layer.seismapAdded;
    var waitTime = this.minWait - timeSinceAdded;
    if (waitTime <= 0) {
      this.doRemoveLayer(layer);
    } else {
      setTimeout(this.doRemoveLayer.bind(this, layer), waitTime);
    }
  },
  doRemoveLayer : function(layer) {
    if (layer.seismapRemoved > layer.seismapAdded) {
      layer.seismapRemoved = new Date().getTime();
      if (layer.getVisibility()) {
        layer.setVisibility(false);
      }
    }
  },
  createLayer : function(cqlFilter) {
    var layerName = 'seismap:eventandaveragemagnitudes';
    var style = this.mapData.style;
    var sld = style.sld;
    var magnitudeLimits = this.magnitudeLimits[this.mapData.magnitudeType];

    var env = '?env=';
    env += 'map.magnitudeType:' + this.mapData.magnitudeType;
    for ( var name in style.variables) {
      var value = style.variables[name];
      env += ';';
      env += 'var.' + escape(name) + ':' + escape(value);
    }

    var rule1 = new OpenLayers.Rule({
      filter : new OpenLayers.Filter.Comparison({
        type : OpenLayers.Filter.Comparison.GREATER_THAN_OR_EQUAL_TO,
        property : "averagemagnitude",
        value : 3
      }),
      symbolizer : {
        Point : {
          pointRadius : 15
        }
      }
    });
    var rule2 = new OpenLayers.Rule({
      filter : new OpenLayers.Filter.Comparison({
        type : OpenLayers.Filter.Comparison.LESS_THAN,
        property : "averagemagnitude",
        value : 3
      }),
      symbolizer : {
        Point : {
          pointRadius : 4
        }
      }
    });
    var rule3 = new OpenLayers.Rule({
      symbolizer : {
        Point : {
          strokeColor : "#00FF00",
          graphicName : 'circle',
          fillColor : "#0000FF",
          fillOpacity : 1
        }
      }
    });
    var style = new OpenLayers.Style({}, {
      rules : [ rule3 ]
    });

    var sldBody = new OpenLayers.Format.SLD().write({
      namedLayers : [ {
        name : layerName,
        userStyles : [ style ]
      } ]
    });
    // if (cqlFilter) {
    // layerUri += "&CQL_FILTER=" + escape(cqlFilter);
    // }
    // layerUri += "&SLD_BODY=" + escape(sldBody);
    //    
    // var layer = new OpenLayers.Layer.Vector("KML", {
    // strategies : [ new OpenLayers.Strategy.Fixed() ],
    // protocol : new OpenLayers.Protocol.HTTP({
    // url : layerUri,
    // format : new OpenLayers.Format.KML({
    // extractStyles : true,
    // extractAttributes : true,
    // maxDepth : 100
    // })
    // })
    // });

    var layer = new OpenLayers.Layer.WMS("SeisMap", this.layerServerUri
        + '/wms' + env, {
      width : '1679',
      srs : 'EPSG:900913',
      layers : layerName,
      height : '330',
      styles : sld,
      format : 'image/png',
      cql_filter : cqlFilter,
      // sld_body : sldBody,
      tiled : 'false',
      feature_count : 1,
      tilesOrigin : this.map.maxExtent.left + ',' + this.map.maxExtent.bottom,
      transparent : true
    }, {
      buffer : 0,
      displayOutsideMaxExtent : true
    });
    layer.setVisibility(false);
    layer.seismapAdded = -1;
    layer.seismapRemoved = new Date().getTime() - 1;
    this.map.addLayer(layer);
    return layer;
  },
  buildCqlDate : function(date) {
    return '' + date.getFullYear() + '-'
        + seismap.twoDigitsNumber(date.getMonth() + 1) + '-'
        + seismap.twoDigitsNumber(date.getDate()) + 'T'
        + seismap.twoDigitsNumber(date.getHours()) + ':'
        + seismap.twoDigitsNumber(date.getMinutes()) + ':'
        + seismap.twoDigitsNumber(date.getSeconds()) + 'Z';
  },
  buildCqlDateFilter : function(map, limit, operator, limitType,
      effectiveLimits, windowLimit) {
    if (limitType == 'ABSOLUTE' || limitType == 'RELATIVE'
        || map.animationType == 'DATE') {
      if (map.animationType == 'DATE') {
        var limitTimestamp = windowLimit
            * (effectiveLimits.maxDate.getTime() - effectiveLimits.minDate
                .getTime()) + effectiveLimits.minDate.getTime();
        limit = new Date(limitTimestamp);
      }
      var filter = 'date ' + operator + ' ' + this.buildCqlDate(limit);
      return filter;
    } else {
      // no filter
      return null;
    }
  },
  buildCqlDepthFilter : function(map, limit, operator, limitType,
      effectiveLimits, windowLimit) {
    if (limitType == 'ABSOLUTE' || map.animationType == 'DEPTH') {
      if (map.animationType == 'DEPTH') {
        limit = windowLimit
            * (effectiveLimits.maxDepth - effectiveLimits.minDepth)
            + effectiveLimits.minDepth;
      }
      var filter = 'depth ' + operator + ' ' + limit;
      return filter;
    } else {
      // no filter
      return null;
    }
  },
  buildCqlMagnitudeFilter : function(map, limit, operator, limitType,
      effectiveLimits, windowLimit) {
    if (limitType == 'ABSOLUTE' || map.animationType == 'MAGNITUDE') {
      if (map.animationType == 'MAGNITUDE') {
        limit = windowLimit
            * (effectiveLimits.maxMagnitude - effectiveLimits.minMagnitude)
            + effectiveLimits.minMagnitude;
      }
      var column = 'magnitude';
      var filter = column + ' ' + operator + ' ' + limit;
      if (map.listUnmeasured) {
        filter = '(' + column + ' IS NULL OR ' + filter + ')';
      }
      return filter;
    } else {
      if (!map.listUnmeasured) {
        var filter = column + ' IS NOT NULL';
        return filter;
      } else {
        // no filter
        return null;
      }
    }
  },
  concatenateCqlFilter : function(soFar, added) {
    if (!soFar) {
      return added;
    } else if (!added) {
      return soFar;
    } else {
      return soFar + ' AND ' + added;
    }
  },
  buildCqlFilter : function(map, effectiveLimits, windowStart, windowEnd) {
    var filter = null;
    var minDateFilter = this.buildCqlDateFilter(map, effectiveLimits.minDate,
        'AFTER', map.minDateType, effectiveLimits, windowStart);
    filter = this.concatenateCqlFilter(filter, minDateFilter);
    var maxDateFilter = this.buildCqlDateFilter(map, effectiveLimits.maxDate,
        'BEFORE', map.maxDateType, effectiveLimits, windowEnd);
    filter = this.concatenateCqlFilter(filter, maxDateFilter);
    var minDepthFilter = this.buildCqlDepthFilter(map,
        effectiveLimits.minDepth, '>=', map.minDepthType, effectiveLimits,
        windowStart);
    filter = this.concatenateCqlFilter(filter, minDepthFilter);
    var maxDepthFilter = this.buildCqlDepthFilter(map,
        effectiveLimits.maxDepth, '<=', map.maxDepthType, effectiveLimits,
        windowEnd);
    filter = this.concatenateCqlFilter(filter, maxDepthFilter);
    var minMagnitudeFilter = this.buildCqlMagnitudeFilter(map,
        effectiveLimits.minMagnitude, '>=', map.minagnitudeType,
        effectiveLimits, windowStart);
    filter = this.concatenateCqlFilter(filter, minMagnitudeFilter);
    var maxMagnitudeFilter = this.buildCqlMagnitudeFilter(map,
        map.maxMagnitude, '<=', map.maxMagnitudeType, effectiveLimits,
        windowEnd);
    filter = this.concatenateCqlFilter(filter, maxMagnitudeFilter);
    return filter;
  },
  buildStaticCqlFilter : function(map, dataBounds, currentDate) {
    var effectiveLimits = this.getEffectiveLimits(map, dataBounds, currentDate);
    var filter = this.buildCqlFilter(map, effectiveLimits, 0, 1);
    return filter;
  },
  buildCqlFilters : function(map, dataBounds, currentDate) {
    if (map.animationType == 'NONE') {
      var filter = this.buildStaticCqlFilter(map, dataBounds, currentDate);
      return [ filter ];
    }
    var effectiveLimits = this.getEffectiveLimits(map, dataBounds, currentDate);
    var steps = map.animationSteps;
    var delta;
    var windowStart;
    var windowEnd;
    if (map.reverseAnimation) {
      delta = -1 / steps;
      windowStart = 1 + delta;
      windowEnd = 1 - delta * map.animationStepKeep;
    } else {
      delta = 1 / steps;
      windowStart = -delta * map.animationStepKeep;
      windowEnd = delta;
    }
    var filters = [];
    for ( var i = 0; i < steps; i++) {
      var filter = this.buildCqlFilter(map, effectiveLimits, Math.max(0,
          windowStart), Math.min(1, windowEnd));
      filters[i] = filter;
      windowStart += delta;
      windowEnd += delta;
    }
    return filters;
  },
  getEffectiveDate : function(boundDate, currentDate, limitType, fixedDate,
      relativeAmount, relativeUnits) {
    if (limitType == 'ABSOLUTE') {
      return fixedDate;
    } else if (limitType == 'RELATIVE') {
      var limit = new Date(currentDate.getFullYear(), currentDate.getMonth(),
          currentDate.getDate(), currentDate.getHours(), currentDate
              .getMinutes(), currentDate.getSeconds(), currentDate
              .getMilliseconds());
      if (relativeUnits == 'MINUTE') {
        limit.setMinutes(limit.getMinutes() - relativeAmount);
      } else if (relativeUnits == 'HOUR') {
        limit.setHours(limit.getHours() - relativeAmount);
      } else if (relativeUnits == 'DAY') {
        limit.setDate(limit.getDate() - relativeAmount);
      } else if (relativeUnits == 'WEEK') {
        limit.setDate(limit.getDate() - relativeAmount * 7);
      } else if (relativeUnits == 'MONTH') {
        limit.setMonth(limit.getMonth() - relativeAmount);
      } else if (relativeUnits == 'YEAR') {
        limit.setFullYear(limit.getFullYear() - relativeAmount);
      } else {
        return null;
      }
      return limit;
    } else /* if (limitType == 'NONE') */{
      return boundDate;
    }
  },
  getEffectiveDepth : function(boundDepth, limitType, fixedDepth) {
    if (limitType == 'ABSOLUTE') {
      return fixedDepth;
    } else /* if (limitType == 'NONE') */{
      return boundDepth;
    }
  },
  getEffectiveMagnitude : function(boundMagnitude, limitType, fixedMagnitude) {
    if (limitType == 'ABSOLUTE') {
      return fixedMagnitude;
    } else /* if (limitType == 'NONE') */{
      return boundMagnitude;
    }
  },
  getEffectiveLimits : function(map, dataBounds, currentDate) {
    var effectiveLimits = {};
    effectiveLimits.minDate = this.getEffectiveDate(dataBounds.minDate,
        currentDate, map.minDateType, map.minDate, map.minDateRelativeAmount,
        map.minDateRelativeUnits);
    effectiveLimits.maxDate = this.getEffectiveDate(dataBounds.maxDate,
        currentDate, map.maxDateType, map.maxDate, map.maxDateRelativeAmount,
        map.maxDateRelativeUnits);
    effectiveLimits.minDepth = this.getEffectiveDepth(dataBounds.minDepth,
        map.minDepthType, map.minDepth);
    effectiveLimits.maxDepth = this.getEffectiveDepth(dataBounds.maxDepth,
        map.maxDepthType, map.maxDepth);
    var magnitudeBounds = dataBounds.magnitudeBounds[map.magnitudeType];
    effectiveLimits.minMagnitude = this.getEffectiveMagnitude(
        magnitudeBounds.min, map.minMagnitudeType, map.minMagnitude);
    effectiveLimits.maxMagnitude = this.getEffectiveMagnitude(
        magnitudeBounds.max, map.maxMagnitudeType, map.maxMagnitude);
    return effectiveLimits;
  },
  setMapData : function(mapData) {
    this.mapData = mapData;
    if (this.mapData.minDate) {
      this.mapData.minDate = new Date(this.mapData.minDate);
    }
    if (this.mapData.maxDate) {
      this.mapData.maxDate = new Date(this.mapData.maxDate);
    }
  },
  setDataBounds : function(dataBounds) {
    this.dataBounds = dataBounds;
    if (this.dataBounds.minDate) {
      this.dataBounds.minDate = new Date(this.dataBounds.minDate);
    }
    if (this.dataBounds.maxDate) {
      this.dataBounds.maxDate = new Date(this.dataBounds.maxDate);
    }
  },
  setStyles : function(styles) {
    this.styles = {};
    for ( var i = 0; i < styles.length; i++) {
      var style = styles[i];
      this.styles[style.id] = style;
    }
  },
  setMagnitudeLimits : function(magnitudeLimits) {
    this.magnitudeLimits = {};
    for ( var i = 0; i < magnitudeLimits.length; i++) {
      var aMagnitudeLimits = magnitudeLimits[i];
      this.magnitudeLimits[aMagnitudeLimits.magnitudeType] = aMagnitudeLimits;
    }
  },
  setLayerServerUri : function(layerServerUri) {
    this.layerServerUri = layerServerUri;
  }
};
