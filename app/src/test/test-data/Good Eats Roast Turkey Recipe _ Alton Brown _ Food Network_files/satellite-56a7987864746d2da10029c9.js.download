_satellite.pushBlockingScript(function(event, target, $variables){
  window.SNI = window.SNI || {};
SNI.EngageSweepsManager = SNI.EngageSweepsManager || {};
SNI.EngageSweepsManager.initConfig = {};
SNI.EngageSweepsManager.activate = function(config) {
  config = config || {};
	if ( typeof SNI.EngageSweepsManager.init  === "function" ) {
		// the EngageSweepsManager init method is available, call it. 
		SNI.EngageSweepsManager.init(config);
	} else {
		if ( config.src ) {
			// the config has a src attribute,
			// load that file and pass it back into this method
			$.getJSON(config.src,function(data) {
				SNI.EngageSweepsManager.activate(data);
			})
		} else {
			// load config into the initConfig object and fire the direct call rule
			SNI.EngageSweepsManager.initConfig = config || {};
			_satellite.track('esmInit');
		}
	}
}

});
