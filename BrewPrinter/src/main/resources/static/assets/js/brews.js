$(document).ready(function() {
	$.get( "brews", function(data) {
		  $.each(data, function(index, element) {
	    	var myStuff = "\
            <div class='col-lg-4 col-md-4 col-sm-6 beer'>\
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
			alert("clicked me. " + $(this).find("h3").html());
		  });
		})
		  .fail(function(xhr, status, message) {
		    alert( "error" );
		  });
	
	
});