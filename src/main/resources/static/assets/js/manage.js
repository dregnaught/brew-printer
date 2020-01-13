$(document).ready(function() {
	getBrews();
});

$('#brewModal').on('shown.bs.modal', function () {
  $('#brewname').trigger('focus')
})

$('#saveBrew').on('click', function () {
	updateBrew();
})

function getBrews() {
	$.get("brews",function(data) {
		$(".content .container-fluid .row").html("");
		$.each(data,function(index, element) {
			var myStuff = "\
		        <div class='col-lg-4 col-md-4 col-sm-6 beer' data-brewid='" + element.id + "' data-brewname='" + element.name + "' data-abv='" + element.abv + "'>\
		          <div class='card card-stats'>\
		            <div class='card-header card-header-primary'>\
		              <h1>"+ element.id + "</h1>\
		        	  <h3 class='card-title'>"+ element.name + "</h3>\
		              <h6>" + element.abv + "%</h6>\
		            </div>\
		          </div>\
		        </div>";

			$(".content .container-fluid .row").append(myStuff);
		});
		$(".beer").click(function() {
			$("#brewnumber").val($(this).attr("data-brewid"));
			$("#brewname").val($(this).attr("data-brewname"));
			$("#abv").val($(this).attr("data-abv"));

			$('#brewModal').modal('show')			
		});
	}).fail(function(xhr, status, message) {
		alert("error");
	});
}

function updateBrew() {

	var brew = {
			id: $("#brewnumber").val(),
			name: $("#brewname").val(),
			abv: $("#abv").val()
			};
	
	$.ajax({
		  url:"brews",
		  type:"PUT",
		  data: JSON.stringify(brew), 
		  contentType:"application/json; charset=utf-8",
		  dataType:"json",
		  success: function(result){
			  getBrews();
			  $('#brewModal').modal('hide');
			  $.notify({
					message: 'Update successful. ' + result.name
				},{
					type: 'success'
				});
		  }
		});

		  

}
