$(document).ready(function() {	
	// get all section elements
	$navbar = $('#navbar');
	$zeroth = $('#zeroth');
	$first = $('#first');
	$second = $('#second');
	
	// set variables
	SCROLL_TIME = 1000;
	navHeight = $navbar.outerHeight();
	
	// set up all scrolls
	$('#go-to-1').click(function() {
		$('body').animate({
			scrollTop: $first.position().top - navHeight
		  }, SCROLL_TIME);
	});
	
	//TODO fade in first section?
});