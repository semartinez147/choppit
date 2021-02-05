_satellite.pushAsyncScript(function(event, target, $variables){
  SniAds.ready(function() {
  	var dfpSlots;
    SniAds.Event.subscribe("slotRenderComplete", function(object) {
	  if ( object.lineItemId ) {
      try {
        dfpSlots = localStorage.getItem('analyticsdfpslots');
        dfpSlots = dfpSlots ? dfpSlots + "," + object.lineItemId : object.lineItemId;
        localStorage.setItem('analyticsdfppage', mdManager.getUniqueId());
        localStorage.setItem('analyticsdfpslots', dfpSlots);
      }
      catch(e) {
      }
      }
     });
});
});
