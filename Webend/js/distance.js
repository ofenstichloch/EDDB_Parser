$("#distform").submit(function(event){
	$("#calc").button("loading");
	$.ajax({
		type: "POST",
  		url: "php/getDistance.php",
  		data: { system1: $("#system1").val(), system2: $("#system2").val() }
	}).done(function(data) {
		$("#calc").button("reset");
		$("#result").html(data);
		
	});
return false;
});

