$(document).ready(function(){
	
	$('input').each(function(){
	    $(this).attr("readonly","readonly");
	});
	$('textarea').each(function(){
	    $(this).attr("readonly","readonly");
	});
});