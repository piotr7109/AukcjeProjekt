$(document).ready(function()
{
	tinymce.init({ selector:'textarea' });
	$( ".datepicker" ).datepicker({ dateFormat: 'yy-mm-dd',
		showAnim:"slide", firstDay:1 });
});
