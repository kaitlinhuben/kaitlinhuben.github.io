$(function () {
	$(".navbar-brand").hover(function() { 
		$(".navbar-brand").html('alt-z');
	}, 
	function() {
		$(".navbar-brand").html('<span class="blue">a</span><span class="green">l</span><span class="orange">t</span>-<span class="teal">z</span>');
	});
} );