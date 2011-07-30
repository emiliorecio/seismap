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
    document.id('newCategory').addEvent('click', basePage.newCategory.bind(basePage));
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
        basePage.createMap.bind(basePage), null, '', 'Nueva Mapa',
        'Crear');
    return false;
  },
  createMap : function(text) {
    var data = {
      userId : seismap.user.id,
      mapName : text
    };
    seismap.requestAndGo('map/create', data, function (response) {
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
      // center: new google.maps.LatLng(48.25, 11.00),
      // center: new google.maps.LatLng(-36.5, -64.5),
      center : new google.maps.LatLng(this.mapData.centerLatitude, this.mapData.centerLongitude),
      mapTypeId : google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.id('map'), mapOptions);
  },
  setMapData : function(mapData) {
    this.mapData = mapData;
  }
};
