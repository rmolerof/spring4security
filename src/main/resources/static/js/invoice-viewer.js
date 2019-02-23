$(document).ready(function(){
	$("#search-form").submit(function(event){
		event.preventDefault();
		fire_ajax_submit();
	});
});

function fire_ajax_submit() {
	var search = {};
	search["invoiceNumber"] = $("#invoiceNumber").val();
	
	$("#btn-search").prop("disabled", true);
	
	$("#d2Row").show();
	$("#g90Row").show();
	$("#g95Row").show();
	
	$.ajax({
		type: "POST",
		contentType: "application/json", 
		url:"/api/findInvoice",
		data: JSON.stringify(search),
		datatype: 'json',
		cache: false,
		timeout: 600000,
		success: function(data){
			/*var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);*/
			
			// Remove any previous image or canvas
            $('canvas').remove();
            $('img').remove();
			
			if (data.result[0].clientDocNumber) {
				$('#errorAlert').parent().parent().hide();
			
	            if(data.result[0].invoiceType == "01") {
	            	$('#invoiceType').html("factura");
	            	$('.creditNoteDetails').hide();
	            } else if(data.result[0].invoiceType == "03") {
	            	$('#invoiceType').html("boleta");
	            	$('.creditNoteDetails').hide();
	            } else if(data.result[0].invoiceType == "07") {
	            	$('#invoiceType').html("nota de credito");
	            	$('.creditNoteDetails').show();
	            }
	            
	            if (data.result[0].clientDocType == "1") {
	            	$('#clientDocType').html("DNI");
	            } else if (data.result[0].clientDocType == "6") {
	            	$('#clientDocType').html("RUC");
	            }
	            
	            if (data.result[0].clientDocType == "1") {
	            	$('#clientNameVrb').html("Señor(es)");
	            } else if (data.result[0].clientDocType == "6") {
	            	$('#clientNameVrb').html("Razón Social");
	            } 
	            
	            if (data.result[0].galsD2 == "0") {
	            	$("#d2Row").hide();
	            } 
	            if (data.result[0].galsG90 == "0") {
	            	$("#g90Row").hide();
	            } 
	            if (data.result[0].galsG95 == "0") {
	            	$("#g95Row").hide();
	            }
	            
	            $('#clientName').html(data.result[0].clientName);
	            $('#invoiceNbr').html(data.result[0].invoiceNumber);
	            $('#dateId').html(moment(data.result[0].date).tz('America/Lima').format('DD/MM/YYYY hh:mm A'));
	            $('#clientDocNumber').html(data.result[0].clientDocNumber);
	            $('#clientAddress').html(data.result[0].clientAddress);
	            $('#truckPlateNumber').html(data.result[0].truckPlateNumber);
			 	if(data.result[0].invoiceType == "03") {
	            	$('#truckPlateNumber').parent().parent().hide();
			 	} else {
			 		$('#truckPlateNumber').parent().parent().show();
			 	}
	            
	            $('#galsD2').html(data.result[0].galsD2.toFixed(2));
	            $('#priceD2').html(data.result[0].priceD2.toFixed(2));
	            $('#solesD2').html(data.result[0].solesD2.toFixed(2));
	            $('#galsG90').html(data.result[0].galsG90.toFixed(2));
	            $('#priceG90').html(data.result[0].priceG90.toFixed(2));
	            $('#solesG90').html(data.result[0].solesG90.toFixed(2));
	            $('#galsG95').html(data.result[0].galsG95.toFixed(2));
	            $('#priceG95').html(data.result[0].priceG95.toFixed(2));
	            $('#solesG95').html(data.result[0].solesG95.toFixed(2));
	            
	            $('#subTotal').html(data.result[0].subTotal.toFixed(2));
	            $('#discount').html(data.result[0].discount.toFixed(2));
	            $('#totalIGV').html(data.result[0].totalIGV.toFixed(2));
	            $('#total').html(data.result[0].total.toFixed(2));
	            $('#electronicPmt').html(data.result[0].electronicPmt.toFixed(2));
	            $('#cashPmt').html(data.result[0].cashPmt.toFixed(2));
	            $('#cashGiven').html(data.result[0].cashGiven.toFixed(2));
	            $('#change').html(data.result[0].change.toFixed(2));
	            
	            $('.totalVerbiage').html(data.result[0].totalVerbiage);
	            $('.invoiceHash').html(data.result[0].invoiceHash);
	            $('.motiveCdDescription').html(data.result[0].motiveCdDescription);
	            $('.invoiceNumberModified').html(data.result[0].invoiceNumberModified);
	            $('.dateOfInvoiceModified').html(moment(data.result[0].dateOfInvoiceModified).tz('America/Lima').format('DD/MM/YYYY hh:mm A'));
	            $('.bonusNumber').html(data.result[0].bonusNumber);
	            if (data.result[0].bonusNumber) {
	            	$('.toggleBonusNumber').show();
	            } else {
	            	$('.toggleBonusNumber').hide();
	            }
	            $('.userName').html(data.result[0].user.name);
	            
	            var qrcode1 = new QRCode("qrcode1");
				qrcode1.clear();
				qrcode1.makeCode("20501568776|" + 
						data.result[0].invoiceType + "|" + 
						data.result[0].invoiceNumber.substring(0, 4) + "|" + 
						data.result[0].invoiceNumber.substring(5, data.result[0].invoiceNumber.length) + "|" +
						data.result[0].totalIGV + "|" +
						data.result[0].total + "|" +
						moment(data.result[0].date).tz('America/Lima').format('DD/MM/YYYY hh:mm A') + "|" + 
						data.result[0].clientDocType + "|" +
						data.result[0].clientDocNumber
				);
	            
			} else {
				$('#errorAlert').parent().parent().show();
				$('#errorAlert').html("Recibo no encontrado. Intente otra vez");
			}
			
            $("#btn-search").prop("disabled", false);
            
		},
		error: function(e){
			var json = "<h4>Ajax Response</h4><pre>"
				+ e.responseText + "</pre>";
			$('#feedback').html(json);
			console.log("ERROR: ", e);
			$("#btn-search").prop("disabled", false);
		}	
	});
}