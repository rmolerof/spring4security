
class TableDashboard extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: {},
	  showError: false,
	  showSuccess: false,
	  invoiceNumber: 'B001-XXXXXXXX',
	  // customer
      clientDocNumber: '',
      clientName: '',
      clientDocType: '1',
      clientAddress: '',
      truckPlateNumber: '',
	  // invoice breakdown
      date: '',
      invoiceType: '03',
	  // Break-down
      galsD2: '',
      galsG90: '',
      galsG95: '',
      priceD2: '',
      priceG90: '',
      priceG95: '',
      solesD2: '',
      solesG90: '',
      solesG95: '',
	  // Save or update in DB
      gasPrices: [],
      totalVerbiage: '',
      invoiceHash: '',
      saveOrUpdate: 'save',
      sunatErrorStr: '',
      status: '',
      selectedOption: 'boleta',
      boletaDisabled: false,
      facturaDisabled: false,
      notaDeCreditoDisabled: false,
      submitDisabled: false,
      showBoletaRadioButton: 'btn blue active',
      showFacturaRadioButton: 'btn btn-default',
      showNotaDeCreditoRadioButton: 'btn btn-default',
      docLabelObj: {
    	  clientDocType: 'DNI',
    	  clientDocTypePH: 'Ingrese DNI',
    	  clientName: 'Señor(es)',
    	  clientNamePH: 'Nombre(s)',
      }	  
    };
  }
  
  galsD2Change = (evt) => {
	  	this.setState({ galsD2: evt.target.value == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	  	this.setState({ solesD2: evt.target.value == '' ? '': ((evt.target.value * this.state.priceD2 * 100).toFixed() / 100) });
  }
  galsG90Change = (evt) => {
	    this.setState({ galsG90: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	    this.setState({ solesG90: evt.target.value == '' ? '': ((evt.target.value * this.state.priceG90 * 100).toFixed() / 100) });
  }
  galsG95Change = (evt) => {
	    this.setState({ galsG95: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	    this.setState({ solesG95: evt.target.value == '' ? '': ((evt.target.value * this.state.priceG95 * 100).toFixed() / 100) });
  }
  solesD2Change = (evt) => {
	    this.setState({ solesD2: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	    this.setState({ galsD2: evt.target.value == '' ? '': ((evt.target.value / this.state.priceD2 * 100).toFixed() / 100) });
  }
  solesG90Change = (evt) => {
	  this.setState({ solesG90: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	  this.setState({ galsG90: evt.target.value == '' ? '': ((evt.target.value / this.state.priceG90 * 100).toFixed() / 100) });
  }
  solesG95Change = (evt) => {
	  this.setState({ solesG95: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	  this.setState({ galsG95: evt.target.value == '' ? '': ((evt.target.value / this.state.priceG95 * 100).toFixed() / 100) });
  }
  clientAddressChange = (evt) => {
	    this.setState({ clientAddress: evt.target.value });
  }
  
  clientNameChange = (evt) => {
	    this.setState({ clientName: evt.target.value });
  }
  
  clientDocNumberChange = (evt) => {
    this.setState({ clientDocNumber: evt.target.value });
    this.setState({ clientDocNumber: evt.target.value == '' ? '': Math.floor(evt.target.value) });
    
    if (evt.target.value == '0' && this.state.selectedOption == 'boleta') {
    	this.setState({ clientName: 'CLIENTES VARIOS' });
    }
  }
  
  truckPlateNumberChange = (evt) => {
    this.setState({ truckPlateNumber: evt.target.value });
  }
  
  _toggleError = () => {
	  this.setState((prevState, props) => {
		  return {showError: !prevState.showError}
	  })
  }
  
  handleOptionChange = (changeEvent) => {
	  this.setState({selectedOption: changeEvent.target.value});
	  
	  var cssB = (this.state.showBoletaRadioButton === "btn btn-default" && changeEvent.target.value == 'boleta') ? "btn blue active" : "btn btn-default";
	  this.setState({showBoletaRadioButton: cssB});
	  var cssF = (this.state.showFacturaRadioButton === "btn btn-default" && changeEvent.target.value == 'factura') ? "btn blue active" : "btn btn-default";
	  this.setState({showFacturaRadioButton: cssF});
	  var cssNV = (this.state.showNotaDeCreditoRadioButton === "btn btn-default" && changeEvent.target.value == 'nota de credito') ? "btn blue active" : "btn btn-default";
	  this.setState({showNotaDeCreditoRadioButton: cssNV});
	  
	  if (changeEvent.target.value == 'boleta') {
		  var docLabelObj = {
	    	  clientDocType: 'DNI',
	    	  clientDocTypePH: 'Ingrese DNI',
	    	  clientName: 'Señor(es)',
	    	  clientNamePH: 'Nombre(s)',
	      }
	  	this.setState({docLabelObj: docLabelObj});
		this.setState({clientDocType: '1'});
		this.setState({invoiceType: '03'});
		this.setState({invoiceNumber: 'B001-XXXXXXXX'});
	  } else if (changeEvent.target.value == 'factura') {
		  var docLabelObj = {
	    	  clientDocType: 'RUC',
	    	  clientDocTypePH: 'Ingrese RUC',
	    	  clientName: 'Razón Social',
	    	  clientNamePH: 'Nombre(s)',
	      }
	  	this.setState({docLabelObj: docLabelObj});
		this.setState({clientDocType: '6'});
		this.setState({invoiceType: '01'});
		this.setState({invoiceNumber: 'F001-XXXXXXXX'});
	  } else if (changeEvent.target.value == 'nota de credito') {
		  var docLabelObj = {
	    	  clientDocType: 'RUC',
	    	  clientDocTypePH: 'Ingrese Doc',
	    	  clientName: 'Señor(es)',
	    	  clientNamePH: 'Nombre(s)',
	      }
	  	this.setState({docLabelObj: docLabelObj});
		this.setState({clientDocType: '6'});
		this.setState({invoiceType: '07'});
		this.setState({invoiceNumber: 'F001-XXXXXXXX'});
	  }
  }
  
  _fetchGasPrices(timeframe){
		
	  	var search = {};
		search["dateEnd"] = timeframe.dateEnd;
		search["dateBeg"] = timeframe.dateBeg;
		
		jQuery.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/getLatestPrices",
			data: JSON.stringify(search),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				if (data.result.length == 1) {
					var gasPricesVo = data.result[0];
					var currentDate = new Date();
					
					this.setState({priceD2: gasPricesVo.gasPrices[0].price});
					this.setState({priceG90: gasPricesVo.gasPrices[1].price});
					this.setState({priceG95: gasPricesVo.gasPrices[2].price});
					this.setState({gasPrices: gasPricesVo.gasPrices});
					this.setState({date: currentDate});
				} 
			},
			error: function(e){

			}	
		});
	}
  
  componentWillMount(){
	  this._fetchGasPrices({dateEnd: "latest", dateBeg: ""});
  }
  
  onKeyPress(event) {
	if (event.which === 13 ) {
		event.preventDefault();
	}
  }
  
  onTabPress(event) {
	
	// Search RUC only for facturas
	if (this.state.selectedOption != 'boleta') {
		var self = this;  
		var search = "https://api.sunat.cloud/ruc/" + event.target.value;
		
		jQuery.ajax({
			type: "GET",
			contentType: "application/json", 
			url:search,
			cache: false,
			timeout: 600000,
			success: (data) => {
			    self.setState({clientAddress: data.domicilio_fiscal});
				self.setState({clientName: data.razon_social});
			      
			},
			error: function(e){
	
			}	
		});
	}
	
  }
  
  handleSubmit = (evt) => {
		
		evt.preventDefault();
		
		const {invoiceNumber, invoiceType, clientDocType, clientAddress, clientDocNumber, clientName, truckPlateNumber, galsD2, galsG90, galsG95, priceD2, priceG90, priceG95, solesD2, solesG90,solesG95,gasPrices, date} = this.state;
	    var self = this;
	    var errors = {
	    		submit: '',
	    		clientDocNumber: '',
	    		clientName: '',
	    		clientAddress: '',
	    		truckPlateNumber: ''
	    };
	    var formIsValid = true;
	    this.setState({ showError: false, showSuccess: false });
	    
	    var invoiceVo = {
	    	invoiceNumber: invoiceNumber,  
		    clientDocNumber: clientDocNumber,
		    clientName: clientName,
		    clientDocType: clientDocType, // RUC
		    clientAddress: clientAddress,
		    truckPlateNumber: truckPlateNumber,
		    date: date,
	    	invoiceType: invoiceType, // factura
		    galsD2: galsD2,
		    galsG90: galsG90,
		    galsG95: galsG95,
		    priceD2: priceD2,
		    priceG90: priceG90,
		    priceG95: priceG95,
		    solesD2: solesD2,
		    solesG90: solesG90,
		    solesG95: solesG95,
		    
	    	saveOrUpdate: 'save'
	    };

	    if (solesD2 || solesG90 || solesG95) {
	    	
    	} else {
	    	errors["submit"] = "Cantidades no pueden ser nulas";
			formIsValid = false;
	    }
	    
	    if (formIsValid){
		    // RUC Validation
		    if (clientDocType == '6') {
			    if (clientDocNumber && clientDocNumber >= 0) {
		    		if (clientDocNumber.toString().length  != 11) {
		    			errors["clientDocNumber"] = "RUC debe tener 11 digitos";
		    			formIsValid = false;
		    		}
			    } else {
			    	errors["clientDocNumber"] = "Falta RUC o DNI";
					formIsValid = false;
			    }
		    }
		    
		    // DNI Validation
		    if (clientDocType == '1') {
			    if (clientDocNumber == '0') {
			    	if (((solesD2 || 0) + (solesG90 || 0) + (solesG95 || 0)) >= 700.00) {
		    			errors["clientDocNumber"] = "DNI no puede ser 0, proveer DNI para compras mayores a S/ 700.00 ";
		    			formIsValid = false;
		    		} 
			    } else {
			    	if (clientDocNumber && clientDocNumber >= 0) {
			    		if (clientDocNumber.toString().length  != 8) {
			    			errors["clientDocNumber"] = "DNI debe tener 8 digitos";
			    			formIsValid = false;
			    		} 
				    } else {
				    	errors["clientDocNumber"] = "Numero de DNI incompleto";
						formIsValid = false;
				    }
			    }
		    }
		    
		    // Validation of Razon Social, Direccion, Nro de Placa 
		    if (clientDocType == '6') {
		    	if (clientName && clientName.trim().length >= 0) {
		    		
		    	} else {
		    		errors["clientName"] = "Falta razon social asociado al RUC " + clientDocNumber;
					formIsValid = false;
		    	}
	
		    	if (clientAddress && clientAddress.trim().length >= 0) {
		    		
		    	} else {
		    		errors["clientAddress"] = "Falta direccion asociado al RUC " + clientDocNumber;
					formIsValid = false;
		    	}
		    	if (truckPlateNumber && truckPlateNumber.trim().length >= 0) {
		    		
		    	} else {
		    		errors["truckPlateNumber"] = "Falta placa de vehículo asociado al RUC " + clientDocNumber;
					formIsValid = false;
		    	}
		    } 
		    
		    // Validation of product list
		    if (!galsD2) {
		    	invoiceVo.galsD2 = 0;
		    	invoiceVo.solesD2 = 0;
		    }
		    if (!galsG90) {
		    	invoiceVo.galsG90 = 0;
		    	invoiceVo.solesG90 = 0;
		    }
		    if (!galsG95) {
		    	invoiceVo.galsG95 = 0;
		    	invoiceVo.solesG95 = 0;
		    }
	    }
		this.setState({errors: errors}); 
		
		if (formIsValid) {
			
			jQuery.ajax({
				type: "POST",
				contentType: "application/json", 
				url:"/api/submitInvoiceVo",
				data: JSON.stringify(invoiceVo),
				datatype: 'json',
				cache: false,
				timeout: 600000,
				success: (data) => {
					var invoiceVoResp = data.result[0];
					
					self.setState({ totalVerbiage: invoiceVoResp.totalVerbiage });
					self.setState({ invoiceHash: invoiceVoResp.invoiceHash });
					self.setState({ sunatErrorStr: invoiceVoResp.sunatErrorStr });
					self.setState({ status: invoiceVoResp.status });
					self.setState({ invoiceNumber: invoiceVoResp.invoiceNumber });
					
					// Prevent user from changing invoice type after submission
					if (this.state.selectedOption == 'boleta') {
						self.setState({ notaDeCreditoDisabled: true });
						self.setState({ facturaDisabled: true });
					} else if (this.state.selectedOption == 'factura') {
						self.setState({ notaDeCreditoDisabled: true });
						self.setState({ boletaDisabled: true });
					} else if (this.state.selectedOption == 'nota de credito') {
						self.setState({ facturaDisabled: true });
						self.setState({ boletaDisabled: true });
					}
					
					if (invoiceVoResp.sunatErrorStr.charAt(0) == "1" && invoiceVoResp.status == '1') {
						self.setState({ showSuccess: true });
						self.setState({submitDisabled: true});
						var qrcode1 = new QRCode("qrcode1");
						qrcode1.clear();
						qrcode1.makeCode("www.grifoslajoya.com/verRecibo/" + invoiceNumber);
						
						var qrcode2 = new QRCode("qrcode2");
						qrcode2.clear();
						qrcode2.makeCode("www.grifoslajoya.com/verRecibo/" + invoiceNumber);
					} else {
						errors["submit"] = "Recibo rechazado por Sunat. " + invoiceVoResp.sunatErrorStr;
						self._toggleError();
					}
				},
				error: function(e){
					errors["submit"] = "Recibo no aceptada. Intente otra vez"  + e.responseText;
					self._toggleError();
				}	
			});
		} else {
			
			this._toggleError();
		}

	  }
  
  render() {    
    return (
    
      <form onSubmit={this.handleSubmit}>
      	  
	      {this.state.showError && 
		        <div className="alert alert-danger">
	      <strong>¡Error!</strong>{" " + this.state.errors.submit + " - " + this.state.errors.clientName + " - " + this.state.errors.clientDocNumber + " - " + this.state.errors.clientAddress + " - " + this.state.errors.truckPlateNumber}  
		      	</div>
	      }

	      {this.state.showSuccess && 
	      	<div className="alert alert-success">
	      		<strong>Success!</strong> Tu recibo has sido remitido. {this.state.sunatErrorStr}
	      	</div>
	      }
	      
	      <div className="invoice">
	          <div className="row invoice-logo">
	              <div className="col-xs-6 invoice-logo-space">
	                  <img src="../assets/pages/media/invoice/lajoya.png" className="img-responsive" alt="" /> </div>
	              <div className="col-xs-6">
	                  <p> <strong className="uppercase ">{this.state.selectedOption} ELECTRÓNICA</strong> <br/>
	                      <span className="muted"> RUC: 20501568776  </span><br/>
	                      <span className="muted bold"> {this.state.invoiceNumber}  </span>
	                  </p>
	              </div>
	          </div>
	          <div className="row">
	              <div className="col-md-12">
	                  <ul className="list-unstyled">
	                      <li><strong>  La Joya de Santa Isabel EIRL </strong></li>
	                      <li> Av. Miguel Grau Mza B Lote 1-2 </li>
	                      <li> Lima - Lima - Ate </li>
	                      <li> +51 356 0345 </li>
	                  </ul>
	              </div>
              </div>
              <div className="portlet-body form">
        		
	    	      <div className="row">
		    	      <div className="col-md-2">
			              <div className="form-group">
			                  <label className="control-label">Fecha</label>
			                  <input type="text" id="lastName" className="form-control" placeholder="Fecha y Hora" value={`${moment(this.state.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}  readOnly/>
			              </div>
			          </div>
	    	      	  <div className="col-md-2">
	    	              <div className="form-group">
	    	                  <label className="control-label">{this.state.docLabelObj.clientDocType}</label>
	    	                  <input type="number" pattern="[0-9]*" className="form-control" placeholder={this.state.docLabelObj.clientDocTypePH} onBlur={this.onTabPress.bind(this)} onKeyPress={this.onKeyPress} inputMode="numeric"  value={this.state.clientDocNumber} onChange={this.clientDocNumberChange}/>
	    	              </div>
	    	          </div>
	    	          <div className="col-md-2">
	    	              <div className="form-group">
	    	                  <label className="control-label">{this.state.docLabelObj.clientName}</label>
	    	                  <input type="text" className="form-control" placeholder={this.state.docLabelObj.clientNamePH} onKeyPress={this.onKeyPress} value={this.state.clientName} onChange={this.clientNameChange}/>
	    	              </div>
	    	          </div>
	    	          <div className="col-md-4">
	    	              <div className="form-group">
	    	                  <label className="control-label">Tipo de Recibo</label>
		    	              <div className="clearfix">
					    	      <div className="btn-group">
			                      <label className={this.state.showBoletaRadioButton}>
			                          <input type="radio" value="boleta" disabled={this.state.boletaDisabled} className="toggle" checked={this.state.selectedOption === 'boleta'} onChange={this.handleOptionChange}/> BOLETA </label>
			                      <label className={this.state.showFacturaRadioButton}>
			                          <input type="radio" value="factura" disabled={this.state.facturaDisabled} className="toggle" checked={this.state.selectedOption === 'factura'} onChange={this.handleOptionChange}/> FACTURA </label>
			                      <label className={this.state.showNotaDeCreditoRadioButton}>
			                          <input type="radio" value="nota de credito" disabled={this.state.notaDeCreditoDisabled} className="toggle" checked={this.state.selectedOption === 'nota de credito'} onChange={this.handleOptionChange}/> NOTA DE CRÉDITO </label>
			                      </div>
		                      </div>
	                      </div>
        	          </div>
	              </div>
	              <div className="row">
	    	          <div className="col-md-4">
	    	              <div className="form-group">
	    	                  <label className="control-label">Dirección</label>
	    	                  <input type="text" className="form-control" placeholder="Dirección" onKeyPress={this.onKeyPress} value={this.state.clientAddress} onChange={this.clientAddressChange}/>
	    	              </div>
	    	          </div>
	    	          {this.state.selectedOption != 'boleta' &&
	    	          <div className="col-md-2">
	    	              <div className="form-group">
	    	                  <label className="control-label">Nro de Placa: </label>
	    	                  <input type="text" className="form-control" placeholder="Ingrese placa" onKeyPress={this.onKeyPress} value={this.state.truckPlateNumber} onChange={this.truckPlateNumberChange}/>
	    	              </div>
	    	          </div>
	    	          }
		          </div>
              </div>
             {/* <div className="row">
	              <div className="col-xs-4">
	                  <h3>About:</h3>
	                  <ul className="list-unstyled">
	                      <li> Drem psum dolor sit amet </li>
	                      <li> Laoreet dolore magna </li>
	                      <li> Consectetuer adipiscing elit </li>
	                      <li> Magna aliquam tincidunt erat volutpat </li>
	                      <li> Olor sit amet adipiscing eli </li>
	                      <li> Laoreet dolore magna </li>
	                  </ul>
	              </div>
	              <div className="col-xs-4 invoice-payment">
	                  <h3>Payment Details:</h3>
	                  <ul className="list-unstyled">
	                      <li>
	                          <strong>V.A.T Reg #:</strong> 542554(DEMO)78 </li>
	                      <li>
	                          <strong>Account Name:</strong> FoodMaster Ltd </li>
	                      <li>
	                          <strong>SWIFT code:</strong> 45454DEMO545DEMO </li>
	                      <li>
	                          <strong>Account Name:</strong> FoodMaster Ltd </li>
	                      <li>
	                          <strong>SWIFT code:</strong> 45454DEMO545DEMO </li>
	                  </ul>
	              </div>
	          </div>*/}
	          <div className="row">
	              <div className="col-xs-12">
	                  <table className="table table-striped table-hover">
	                      <thead>
	                          <tr>
	                              <th className="hidden-xs"> Prod </th>
	                              <th className="hidden-sm-up"> Descr </th>
	                              <th className="hidden-sm-up"> Cantidad </th>
	                              <th> Valor Unitario </th>
	                              <th className="hidden-sm-up"> Importe </th>
	                          </tr>
	                      </thead>
	                      <tbody>
	                      	  <tr>
	                              <td className="hidden-xs"> 01-Diesel </td>
	                              <td className="hidden-sm-up"> D2 </td>
	                              <td className="hidden-sm-up"> <input type="number" style={{width: '100px', textAlign: 'right'}} pattern="[0-9]*" className="form-control" placeholder="Galones" onKeyPress={this.onKeyPress} inputMode="numeric"  value={this.state.galsD2} onChange={this.galsD2Change}/> </td>
	                              {this.state.gasPrices && <td>S/ {this.state.priceD2} </td>} 
	                              <td className="hidden-sm-up"> <input type="number" style={{width: '100px', textAlign: 'right'}} pattern="[0-9]*" className="form-control" placeholder="Soles" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.solesD2} onChange={this.solesD2Change}/> </td>
	                          </tr>
	                          <tr>
	                              <td className="hidden-xs"> 02-Gas 90 </td>
	                              <td className="hidden-sm-up"> G90 </td>
	                              <td className="hidden-sm-up"> <input type="number" style={{width: '100px', textAlign: 'right'}} pattern="[0-9]*" className="form-control" placeholder="Galones" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.galsG90} onChange={this.galsG90Change}/> </td>
	                              {this.state.gasPrices && <td>S/ {this.state.priceG90} </td>}
	                              <td className="hidden-sm-up"> <input type="number" style={{width: '100px', textAlign: 'right'}} pattern="[0-9]*" className="form-control" placeholder="Soles" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.solesG90} onChange={this.solesG90Change}/> </td>
	                          </tr>
	                          <tr>
	                              <td className="hidden-xs"> 03-Gas 95 </td>
	                              <td className="hidden-sm-up"> G95 </td>
	                              <td className="hidden-sm-up"> <input type="number" style={{width: '100px', textAlign: 'right'}} pattern="[0-9]*" className="form-control" placeholder="Galones" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.galsG95} onChange={this.galsG95Change}/> </td> 
	                              {this.state.gasPrices && <td>S/ {this.state.priceG95} </td>}
	                              <td className="hidden-sm-up"> <input type="number" style={{width: '100px', textAlign: 'right'}} pattern="[0-9]*" className="form-control" placeholder="Soles" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.solesG95} onChange={this.solesG95Change}/> </td>
	                          </tr>
	                      </tbody>
	                  </table>
	              </div>
	          </div>
	          <div className="row">
	              <div className="col-xs-3">
	                  <div className="well">
	                      <address>
	                          <strong>Código Hash:</strong>
	                          <br/> {this.state.invoiceHash}
                        	  <br/>
	                          {/*<strong>Nro de <span className="uppercase" >{this.state.selectedOption}</span>:</strong>
	                          <br/> {this.state.invoiceNumber}
	                          <br/>*/}
	                          <strong>SON:</strong>
		                      <br/> {this.state.totalVerbiage}
		                      <br/>
                          </address>
	                      <address>
	                          <strong>Consulte su documento en:</strong>
	                          <a> www.grifoslajoya.com </a>
	                      </address>
	                  </div>
	              </div>
		          <div className="col-xs-3">
	                  <div className="well">
		                  <address>
		                      <strong>Código QR:</strong>
		                      <br/>  
		                  </address>
		                  <div style={{width: '300px', height: '300px', display: 'block', margin: 'auto', zoom: 0.5}} >
				          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<div id="qrcode1" className="col-xs-12 col-md-offset-3" ></div>
				          </div>
	                  </div>
	              </div>
	              <div className="col-xs-6 invoice-block">
	                  <div className="row invoice-subtotal">
				          <div className="col-xs-12">
					          <table className="table table-hover">
					              <tbody>
					                  <tr>
					                      <td>
					                      	<strong>Sub-Total ventas:</strong>
					                      </td>
					                      <td className="text-center sbold">S/ {((((this.state.solesD2 || 0) + (this.state.solesG90 || 0) + (this.state.solesG95 || 0)) * 100).toFixed() / 100 - (((this.state.solesD2  || 0) + (this.state.solesG90  || 0) + (this.state.solesG95 || 0)) * 18 / 1.18).toFixed() / 100 ).toFixed(2)}</td>
					                  </tr>
					                  <tr>
					                      <td>
					                      	<strong>IGV (18%):</strong>
					                      </td>
					                      <td className="text-center sbold">S/ {((((this.state.solesD2 || 0) + (this.state.solesG90 || 0) + (this.state.solesG95 || 0)) * 18 / 1.18).toFixed() / 100).toFixed(2)} </td>
					                  </tr>
					                  <tr>
					                      <td>
				                      		<strong>Importe Total:</strong>
					                      </td>
					                      <td className="text-center sbold">S/ {(((((this.state.solesD2 || 0) + (this.state.solesG90 || 0) + (this.state.solesG95 || 0)) * 100).toFixed() / 100)).toFixed(2)}</td>
					                  </tr>
					              </tbody>
					          </table>	
		          		  </div>
			          </div>
	                  <br/>
	                  {this.state.invoiceHash && <ReactToPrint trigger={() => <a type="submit" className="btn btn-lg blue hidden-print margin-bottom-5" > Imprimir&nbsp;<i className="fa fa-print"></i></a>} content={() => this.componentRef}></ReactToPrint>}&nbsp;
	                  {!this.state.invoiceHash && <a type="submit" className="btn btn-lg blue hidden-print margin-bottom-5" disabled={!this.state.invoiceHash} > Imprimir&nbsp;<i className="fa fa-print"></i></a>}&nbsp;
	                  <button type="submit" disabled={this.state.submitDisabled} className="btn btn-lg green hidden-print margin-bottom-5">
	    	          	<i className="fa fa-check"></i> Enviar Recibo
	    	          </button>
	              </div>
	          </div>
	      </div>
	      
	      <div className="invoice-content-2 " ref={el => (this.componentRef = el)} style={{fontFamily:"sans-serif", fontSize: 11}}>
	          <div className="row invoice-head">
	              <div className="col-md-12 col-xs-12 text-center">
	                  <div className="invoice-logo">
	                      <img src="../assets/pages/media/invoice/lajoya.png" className="img-responsive" style={{display: 'block', margin: 'auto'}} alt="" />
	                      <span className="uppercase" >{this.state.selectedOption} Electrónica</span><br/>
	                      <span className="uppercase" >{this.state.invoiceNumber}</span>
	                    	  
	                  </div>
	              </div>
	              <div className="col-md-12 col-xs-12">
	                  <div className="company-address text-center">
	                      <span className="bold uppercase" style={{fontSize: 13}}>La Joya de Santa Isabel EIRL</span>
	                      <br/> Av. Miguel Grau Mza B Lote 1-2 
	                      <br/> Lima - Lima - Ate 
	                      <br/> Teléfono: +51 356 0345
	                      <br/> www.grifoslajoya.com 
                      </div>
	              </div>
	          </div>
	          <div className="row invoice-cust-add">
	              <div className="col-xs-12">
		              <table className="table table-hover table-borderless" >
			              <tbody>
				              <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      {this.state.docLabelObj.clientDocType}
			                      </td>
			                      <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{this.state.clientDocNumber}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      {this.state.docLabelObj.clientName}:
			                      </td>
			                      <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{this.state.clientName}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      Fecha:
			                      </td>
			                      <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{`${moment(this.state.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      Dirección:
			                      </td>
			                      <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{this.state.clientAddress}</td>
			                  </tr>
			                  {this.state.selectedOption != 'boleta' && 
			                  <tr>
		                      	  <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
		                      	  Placa de Vehículo:
			                      </td>
			                      <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{this.state.truckPlateNumber}</td>
			                  </tr>
			                  }
			              </tbody>
			          </table>
	              </div>
	          	  
	          </div>
	          <div className="row invoice-body" >
	              <div className="col-xs-12 table-responsive">
	                  <table className="table table-hover">
	                      <thead>
	                          <tr>
                      			  <th className="invoice-title" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>Producto</th>
	                              <th className="invoice-title text-center" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>Cantidad</th>
	                              <th className="invoice-title text-center" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>Precio</th>
	                              <th className="invoice-title text-center" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>Importe</th>
	                          </tr>
	                      </thead>
	                      <tbody>
	                      	  {this.state.galsD2 > 0 && 
	                      	  <tr>
	                              <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
	                                  01-Diesel 2
	                              </td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{parseFloat(this.state.galsD2 || '0').toFixed(2)}</td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>S/ {parseFloat(this.state.priceD2 || '0').toFixed(2)}</td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>S/ {parseFloat(this.state.solesD2 || '0').toFixed(2)}</td>
	                          </tr>
	                      	  }
	                      	  {this.state.galsG90 > 0 && 
	                      	  <tr>
	                          	  <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
	                                  02-Gas 90
	                              </td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{parseFloat(this.state.galsG90 || '0').toFixed(2)}</td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>S/ {parseFloat(this.state.priceG90 || '0').toFixed(2)}</td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>S/ {parseFloat(this.state.solesG90 || '0').toFixed(2)}</td>
	                          </tr>
	                      	  }
	                      	  {this.state.galsG95 > 0 && 
	                      	  <tr>
	                          	  <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
	                                  03-Gas 95
	                              </td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{parseFloat(this.state.galsG95 || '0').toFixed(2)}</td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>S/ {parseFloat(this.state.priceG95 || '0').toFixed(2)}</td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>S/ {parseFloat(this.state.solesG95 || '0').toFixed(2)}</td>
	                          </tr>
	                      	  }
	                      </tbody>
	                  </table>
	              </div>
	          </div>
	          <div className="row invoice-subtotal">
		          <div className="col-xs-12">
			          <table className="table table-hover">
			              <tbody>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                          Subtotal
			                      </td>
			                      <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>S/ {((((this.state.solesD2 || 0) + (this.state.solesG90 || 0) + (this.state.solesG95 || 0)) * 100).toFixed() / 100 - (((this.state.solesD2 || 0) + (this.state.solesG90 || 0) + (this.state.solesG95 || 0)) * 18 / 1.18).toFixed() / 100 ).toFixed(2)}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      IGV (18%)
			                      </td>
			                      <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>S/ {((((this.state.solesD2 || 0) + (this.state.solesG90 || 0) + (this.state.solesG95 || 0)) * 18 / 1.18).toFixed() / 100).toFixed(2)}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                          Total
			                      </td>
			                      <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>S/ {((((this.state.solesD2 || 0) + (this.state.solesG90 || 0) + (this.state.solesG95 || 0)) * 100).toFixed(2) / 100).toFixed(2)}</td>
			                  </tr>
			              </tbody>
			          </table>	
          		  </div>
	          </div>
	          <div className="row col-xs-12">
	                  <address>
		                  <strong>SON:</strong>
	                      <br/> {this.state.totalVerbiage}
	                      <br/>
	                  	  <strong>Código Hash:</strong>
	                      <br/> {this.state.invoiceHash}
	                	  <br/>
	                  </address>
	                  <address>
	                      <strong>Consulte su documento en:</strong>
	                      <a> www.grifoslajoya.com </a>
	                  </address>
	          </div>
	          
	          <div style={{width: '300px', height: '500px', display: 'block', margin: 'auto', zoom: 0.5}} >
	          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<div id="qrcode2" className="col-xs-12 col-md-offset-3" ></div>
	          </div>
	      </div>
	      
      </form>
    )
  }
}

let target = document.getElementById('react-invoicing-id');

ReactDOM.render(<TableDashboard />, target);