$(document).ready(function() {
	$.get( "brews", function(data) {
		  $.each(data, function(index, element) {
	    	var myStuff = "\
            <div class='col-lg-4 col-md-4 col-sm-6 beer' data-brewid='" + element.id + "' data-brewname='" + element.name + "' data-abv='" + element.abv + "'>\
              <div class='card card-stats'>\
                <div class='card-header card-header-primary'>\
                  <h3 class='card-title'>" + element.name + "</h3>\
                  <h6>" + element.abv + "%</h6>\
                </div>\
              </div>\
            </div>";
	    	
	    	$(".content .container-fluid .row").append(myStuff);
		  });
		  $(".beer").click(function() {
			console.log("clicked me. " + $(this).attr("data-brewid"));
			$.ajax({
				  url:"brews/" + $(this).attr("data-brewid"),
				  type:"POST",
				  contentType:"application/json; charset=utf-8",
				  dataType:"json",
				  success: function(result){
					  $.notify({
							message: 'Printing successful.' 
						},{
							type: 'success'
						});
				  }
				});
		  });
		})
		  .fail(function(xhr, status, message) {
		    $.notify({
				message: 'Printing unsuccessful. ' + message 
			},{
				type: 'error'
			});
		  });
	
	
});