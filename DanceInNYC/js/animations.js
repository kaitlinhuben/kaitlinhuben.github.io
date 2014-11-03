$(document).ready(function() {	
	// ----------------------------------------------------
	// get all section elements
	// ----------------------------------------------------
	$navbar = $('#navbar');
	$navbarlogo = $('#logo');
	$zeroth = $('#zeroth');
	$photobanner = $('#photo-banner');
	$first = $('#first');
	$second = $('#second');
	$third = $('#third');
	$fourth = $('#fourth');
	$footer = $('#footer');
	
	// ----------------------------------------------------
	// set variables
	// ----------------------------------------------------
	SCROLL_TIME = 1000;
	FADE_TIME = 750;
	navHeight = $navbar.outerHeight();
	
	// ----------------------------------------------------
	// set up all scroll handlers
	// ----------------------------------------------------
	$('.go-to-top').click(function() {
		$('body').animate({
			scrollTop: $zeroth.position().top - navHeight
		  }, SCROLL_TIME);
	});
	$('#go-to-1').click(function() {
		$('body').animate({
			scrollTop: $first.position().top - navHeight
		  }, SCROLL_TIME);
	});
	$('#go-to-2').click(function() {
		$('body').animate({
			scrollTop: $second.position().top - navHeight
		  }, SCROLL_TIME);
	});
	$('#go-to-3').click(function() {
		$('body').animate({
			scrollTop: $third.position().top - navHeight
		  }, SCROLL_TIME);
	});
	$('#go-to-4').click(function() {
		$('body').animate({
			scrollTop: $fourth.position().top - navHeight
		  }, SCROLL_TIME);
	});

	// ----------------------------------------------------
	// hide top sections
	// ----------------------------------------------------
	$navbarlogo.css('opacity', 0);
	$zeroth.css('opacity', 0);
	$footer.css('opacity', 0);
	$photobanner.css('opacity', 0);
	// ----------------------------------------------------
	// fade top sections back in
	// ----------------------------------------------------
	$navbarlogo.animate(
		{
			opacity: 1
		}, 
		// time
		FADE_TIME, 
		//callback - fade zeroth intro section in
		function() {
			animateZerothSection();
		}
	);
	
	// helper function - navbar's callback
	function animateZerothSection() {
		$zeroth.animate(
			{
				opacity: 1
			},
			// time
			FADE_TIME,
			// callback - fade footer and photo in
			function (){
				animateFooterAndPhoto();
			}
		)
	}
	  
	// helper function - zeroth section's callback
	function animateFooterAndPhoto() {
		// fade in footer
		$footer.animate(
			{
				opacity: 1
			}, 
			FADE_TIME);
		// fade in photo
		$photobanner.animate(
			{
				opacity: 1
			}, 
			FADE_TIME)
	  }
	
});