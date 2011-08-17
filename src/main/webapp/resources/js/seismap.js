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
    var mapOptions = {
      zoom : this.mapData.zoom,
      center : new google.maps.LatLng(this.mapData.centerLatitude,
          this.mapData.centerLongitude),
      mapTypeId : google.maps.MapTypeId.ROADMAP
    };
    this.map = new google.maps.Map(document.id('map'), mapOptions);
    var cqlFilters = this.buildCqlFilters(this.mapData, new Date());
    this.uriCqlFilters = [];
    this.layers = [];
    for ( var i = 0; i < cqlFilters.length; i++) {
      var cqlFilter = cqlFilters[i];
      document.id('mapUris').value += cqlFilter + '\n';
      var uriCqlFilter;
      if (cqlFilter == null) {
        uriCqlFilter = '';
      } else {
        uriCqlFilter = '&CQL_FILTER=' + escape(cqlFilter);
      }
      this.uriCqlFilters[i] = uriCqlFilter;
      this.layers[i] = this.getLayer(i);
    }
    this.animationIndex = -1;
    this.cycle();
    if (this.uriCqlFilters.length > 1) {
      this.animationHandle = setInterval(this.cycle.bind(this), 3000);
    }
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
  },
  getLayer : function(index) {
    var layerUri = this.getLayerUri(index);
    return new google.maps.KmlLayer(layerUri, {
      preserveViewport : true
    });
  },
  getLayerUri : function(index) {
    var uriCqlFilter = this.uriCqlFilters[index];
    var layerUri = this.layerServerUri
        + '/wms?service=WMS&version=1.1.0&request=GetMap&layers=cite:eventandml&styles=&bbox=-2.003750834E7,-2.003750834E7,2.003750834E7,2.003750834E7&width=512&height=512&srs=EPSG:900913&format=application/vnd.google-earth.kml+xml'
        + uriCqlFilter;
    return layerUri;
  },
  cycle : function() {
    var previousIndex = this.animationIndex % this.uriCqlFilters.length;
    var index = (++this.animationIndex) % this.uriCqlFilters.length;
    if (previousIndex != -1) {
      this.layers[previousIndex].setMap(null);
    }
    this.layers[index].setMap(this.map);
  },
  twoDigitsNumber : function(number) {
    if (number < 10) {
      return '0' + number;
    } else {
      return '' + number;
    }
  },
  getEffectiveDate : function(map, currentDate, min) {
    var fixedDate;
    var relativeUnits;
    var relativeAmount;
    if (min) {
      fixedDate = map.minDate;
      relativeUnits = map.minDateRelativeUnits;
      relativeAmount = map.minDateRelativeAmount;
    } else {
      fixedDate = map.maxDate;
      relativeUnits = map.maxDateRelativeUnits;
      relativeAmount = map.maxDateRelativeAmount;
    }
    if (fixedDate) {
      return fixedDate;
    }
    if (relativeAmount) {
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
    } else {
      return null;
    }
  },
  buildCqlDate : function(date) {
    return '' + date.getUTCFullYear() + '-'
        + this.twoDigitsNumber(date.getUTCMonth() + 1) + '-'
        + this.twoDigitsNumber(date.getUTCDate()) + 'T'
        + this.twoDigitsNumber(date.getUTCHours()) + ':'
        + this.twoDigitsNumber(date.getUTCMinutes()) + ':'
        + this.twoDigitsNumber(date.getUTCSeconds()) + 'Z';
  },
  buildCqlDateFilter : function(map, currentDate, effectiveMinDate,
      effectiveMaxDate, min, windowLimit) {
    var operator;
    var limit;
    if (min) {
      operator = 'AFTER';
      limit = effectiveMinDate;
    } else {
      operator = 'BEFORE';
      limit = effectiveMaxDate;
    }
    if (limit) {
      if (map.animationType == 'DATE' && effectiveMinDate && effectiveMaxDate) {
        var limitTimestamp = windowLimit
            * (effectiveMaxDate.getTime() - effectiveMinDate.getTime())
            + effectiveMinDate.getTime();
        limit = new Date(limitTimestamp);
      }
      var filter = 'date ' + operator + ' ';
      filter += this.buildCqlDate(limit);
      return filter;
    } else {
      // no filter
      return null;
    }
  },
  buildCqlDepthFilter : function(map, min, windowLimit) {
    var limit;
    var operator;
    if (min) {
      limit = map.minDepth;
      operator = '>=';
    } else {
      limit = map.maxDepth;
      operator = '<=';
    }
    if (limit) {
      if (map.animationType == 'DEPTH' && map.minDepth && map.maxDepth) {
        limit = windowLimit * (map.maxDepth - map.minDepth) + map.minDepth;
      }
      var filter = 'depth ' + operator + ' ' + limit;
      return filter;
    } else {
      // no filter
      return null;
    }
  },
  buildCqlMagnitudeFilter : function(map, min, windowLimit) {
    var limit;
    var operator;
    if (min) {
      limit = map.minMagnitude;
      operator = '>=';
    } else {
      limit = map.maxMagnitude;
      operator = '<=';
    }
    if (limit) {
      if (map.animationType == 'MAGNITUDE' && map.minMagnitude
          && map.maxMagnitude) {
        limit = windowLimit * (map.maxMagnitude - map.minMagnitude)
            + map.minMagnitude;
      }
      var filter = 'magnitudevalue ' + operator + ' ' + limit;
      if (map.listUnmeasured) {
        filter = '(magnitudevalue IS NULL OR ' + magnitudevalue + ')';
      }
      return filter;
    } else {
      // no filter
      return null;
    }
  },
  concatenateCqlFilter : function(soFar, added) {
    if (soFar == null) {
      return added;
    } else if (added == null) {
      return soFar;
    } else {
      return soFar + ' AND ' + added;
    }
  },
  buildCqlFilter : function(map, currentDate, effectiveMinDate,
      effectiveMaxDate, windowStart, windowEnd) {
    var filter = null;
    var minDateFilter = this.buildCqlDateFilter(map, currentDate,
        effectiveMinDate, effectiveMaxDate, true, windowStart);
    filter = this.concatenateCqlFilter(filter, minDateFilter);
    var maxDateFilter = this.buildCqlDateFilter(map, currentDate,
        effectiveMinDate, effectiveMaxDate, false, windowEnd);
    filter = this.concatenateCqlFilter(filter, maxDateFilter);
    var minDepthFilter = this.buildCqlDepthFilter(map, true, windowStart);
    filter = this.concatenateCqlFilter(filter, minDepthFilter);
    var maxDepthFilter = this.buildCqlDepthFilter(map, false, windowEnd);
    filter = this.concatenateCqlFilter(filter, maxDepthFilter);
    var minMagnitudeFilter = this.buildCqlMagnitudeFilter(map, true,
        windowStart);
    filter = this.concatenateCqlFilter(filter, minMagnitudeFilter);
    var maxMagnitudeFilter = this
        .buildCqlMagnitudeFilter(map, false, windowEnd);
    filter = this.concatenateCqlFilter(filter, maxMagnitudeFilter);
    return filter;
  },
  buildStaticCqlFilter : function(map, currentDate) {
    var effectiveMinDate = this.getEffectiveDate(map, currentDate, true);
    var effectiveMaxDate = this.getEffectiveDate(map, currentDate, false);
    var filter = this.buildCqlFilter(map, currentDate, effectiveMinDate,
        effectiveMaxDate, 0, 1);
    return filter;
  },
  buildCqlFilters : function(map, currentDate) {
    if (map.animationType == 'NONE') {
      var filter = this.buildStaticCqlFilter(map, currentDate);
      return [ filter ];
    }
    var effectiveMinDate = this.getEffectiveDate(map, currentDate, true);
    var effectiveMaxDate = this.getEffectiveDate(map, currentDate, false);
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
      var filter = this.buildCqlFilter(map, currentDate, effectiveMinDate,
          effectiveMaxDate, Math.max(0, windowStart), Math.min(1, windowEnd));
      filters[i] = filter;
      windowStart += delta;
      windowEnd += delta;
    }
    return filters;
  },
  setMapData : function(mapData) {
    this.mapData = mapData;
    if (this.mapData.minDate) {
      this.mapData.minDate = new Date(this.mapData.minDate);
      this.mapData.minDate.setMinutes(this.mapData.minDate.getMinutes()
          - this.mapData.minDate.getTimezoneOffset());
    }
    if (this.mapData.maxDate) {
      this.mapData.maxDate = new Date(this.mapData.maxDate);
      this.mapData.maxDate.setMinutes(this.mapData.maxDate.getMinutes()
          - this.mapData.maxDate.getTimezoneOffset());
    }
  },
  setLayerServerUri : function(layerServerUri) {
    this.layerServerUri = layerServerUri;
  }
};
