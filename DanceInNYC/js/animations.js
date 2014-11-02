$(document).ready(function() {	
	// get all section elements
	$navbar = $('#navbar');
	$navbarlogo = $('#logo');
	$zeroth = $('#zeroth');
	$first = $('#first');
	$second = $('#second');
	$third = $('#third');
	$fourth = $('#fourth');
	$footer = $('#footer');
	
	// set variables
	SCROLL_TIME = 1000;
	FADE_TIME = 750;
	navHeight = $navbar.outerHeight();
	
	// set up all scrolls
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

	// hide first section and then fade back in
	$navbarlogo.css('opacity', 0);
	$zeroth.css('opacity', 0);
	$footer.css('opacity', 0);
	$navbarlogo.animate({
		opacity: 1
	  }, 
	  // time
	  FADE_TIME, 
	  //callback - fade top section in
	  function() {
		$zeroth.animate({
			opacity: 1
		}, 
		//time
		FADE_TIME,
		// callback - fade footer in
		function (){
			$footer.animate({
				opacity: 1
			},
			FADE_TIME)
		})
	  });
	
});