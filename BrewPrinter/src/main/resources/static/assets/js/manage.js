$(document).ready(function() {
	getBrews();
});

//var dialog, form;

//var dialog = $("#dialog-form").dialog({
//	autoOpen : false,
//	height : 400,
//	width : 350,
//	modal : true,
//	buttons : {
//		"Update Brew Label" : updateBrew,
//		Cancel : function() {
//			dialog.dialog("close");
//		}
//	},
//	close : function() {
//		form[0].reset();
//		allFields.removeClass("ui-state-error");
//	}
//});
//
//var form = dialog.find("form").on("submit", function(event) {
//	event.preventDefault();
//	updateBrew();
//});

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
			
			//alert("clicked me. " + $(this).attr("data-brewid") + " " + $(this).attr("data-brewname") + " " + $(this).attr("data-abv"));
		});
	}).fail(function(xhr, status, message) {
		alert("error");
	});
}

function updateBrew() {
	//alert("clicked me. " + $("#brewnumber").val() + " " + $("#brewname").val() + " " + $("#abv").val());
//	$.post("brews", {
//		id: $("#brewnumber").val(),
//		name: $("#brewname").val(),
//		abv: $("#abv").val()
//		}, 
//		function(result){
//			getBrews();
//			$('#brewModal').modal('close');
//		});
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
		  }
		});
}



//$("#update-brew").button().on("click", function() {
//	dialog.dialog("open");
//});