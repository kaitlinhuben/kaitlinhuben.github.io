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
	var $skillsList	 	= $('#skills-list');
	var $skillsFilter 		= $('#skills-filter');
		
	// Run Isotope  
	$skillsList.isotope({
		filter				: '*',
		layoutMode   		: 'masonry',
		animationOptions	: {
		duration			: 750,
		easing				: 'linear'
	   }
	});	
	
	// Isotope Filter 
	$skillsFilter.find('a').click(function(){
	  var selector = $(this).attr('data-filter');
		$skillsList.isotope({ 
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