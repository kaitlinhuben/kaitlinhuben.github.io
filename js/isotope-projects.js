(function ($, window, document, undefined) {
/* ---------------------------------------------------------------------- */
	/*	Cats Filter
	/* ---------------------------------------------------------------------- */ 
	
	var $catsfilter 		= $('.categories-filter');

	// Copy categories to item classes
	$catsfilter.find('a').click(function() {
		var currentOption = $(this).attr('data-filter');
		$(this).parent().parent().find('a').removeClass('current');
		$(this).addClass('current');
	});	

	/* ---------------------------------------------------------------------- */
	/*	Portfolio
	/* ---------------------------------------------------------------------- */ 
	
	// Needed variables
	var $plist	 	= $('#projects-list');
	var $pfilter 		= $('#projects-filter');
		
	// Run Isotope  
	$plist.isotope({
		filter				: '*',
		layoutMode   		: 'masonry',
		animationOptions	: {
		duration			: 750,
		easing				: 'linear'
	   }
	});	
	
	// Isotope Filter 
	$pfilter.find('a').click(function(){
	  var selector = $(this).attr('data-filter');
		$plist.isotope({ 
		filter				: selector,
		animationOptions	: {
		duration			: 750,
		easing				: 'linear',
		queue				: false,
	   }
	  });
	  return false;
	});	
	

})(window.jQuery, this, document);