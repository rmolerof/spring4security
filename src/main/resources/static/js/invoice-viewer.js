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
			
			if (data.result[0].clientDocNumber) {
				$('#errorAlert').html("OK");
			} else {
				$('#errorAlert').html("Recibo no encontrado. Intente otra vez");
			}
			
            if(data.result[0].invoiceType == "01") {
            	$('#invoiceType').html("factura");
            } else if(data.result[0].invoiceType == "03") {
            	$('#invoiceType').html("boleta");
            } else if(data.result[0].invoiceType == "07") {
            	$('#invoiceType').html("nota de credito");
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
            
            $('#galsD2').html(data.result[0].galsD2);
            $('#priceD2').html(data.result[0].priceD2);
            $('#solesD2').html(data.result[0].solesD2);
            $('#galsG90').html(data.result[0].galsG90);
            $('#priceG90').html(data.result[0].priceG90);
            $('#solesG90').html(data.result[0].solesG90);
            $('#galsG95').html(data.result[0].galsG95);
            $('#priceG95').html(data.result[0].priceG95);
            $('#solesG95').html(data.result[0].solesG95);
            
            $('#subTotal').html(data.result[0].subTotal);
            $('#totalIGV').html(data.result[0].totalIGV);
            $('#total').html(data.result[0].total);
            
            $('#totalVerbiage').html(data.result[0].totalVerbiage);
            $('#invoiceHash').html(data.result[0].invoiceHash);
            
            $('canvas').remove();
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
            
            console.log("SUCCESS : ", data);
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