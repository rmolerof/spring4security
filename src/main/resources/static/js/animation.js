// Initial function for Login page jQuery animation
$(function() {
	$('.message a').click(function() {
		$('form').animate({height : "toggle", opacity : "toggle"}, "slow");
	});
});