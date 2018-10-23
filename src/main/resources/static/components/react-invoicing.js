
class TableDashboard extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: {},
	  showError: false,
	  showSuccess: false,
      
	  clientAddress: '',
      rucNumber: '',
      clientName: '',
      truckPlateNumber: '',
      galsD2: '',
      galsG90: '',
      galsG95: '',
      priceD2: '',
      priceG90: '',
      priceG95: '',
      solesD2: '',
      solesG90: '',
      solesG95: '',
      gasPrices: [],
      date: '',
      
	  // customer
      clientDocNumber: '',
      clientName: '',
      clientDocType;
      clientAddress: '',
      truckPlateNumber: '',
	  // invoice breakdown
      date: '',
      invoiceType: '',
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
      // Totals
      /*total: '',
      subTotal: '',
      totalIGV: '',
      totalVerbiage: '',*/
	  // Save or update in DB
      gasPrices: [],
      saveOrUpdate: 'save'
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
  
  rucNumberChange = (evt) => {
    this.setState({ rucNumber: evt.target.value });
    this.setState({ rucNumber: evt.target.value == '' ? '': Math.floor(evt.target.value) });
  }
  
  truckPlateNumberChange = (evt) => {
    this.setState({ truckPlateNumber: evt.target.value });
  }
  
  _toggleError = () => {
	  this.setState((prevState, props) => {
		  return {showError: !prevState.showError}
	  })
  };
  
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
				var qrcode = new QRCode("qrcode");
				qrcode.makeCode("www.google.com");
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
	// validation
	
	var self = this;  
	var search = "https://api.sunat.cloud/ruc/" + event.target.value;
	
	jQuery.ajax({
		type: "GET",
		contentType: "application/json", 
		url:search,
		cache: false,
		timeout: 600000,
		success: (data) => {
			console.log(data);
		    self.setState({clientAddress: data.domicilio_fiscal});
			self.setState({clientName: data.razon_social});
		      
		},
		error: function(e){

		}	
	});
	
  }
  
  handleSubmit = (evt) => {
		
		evt.preventDefault();
		
		const {clientAddress, rucNumber, clientName, truckPlateNumber, galsD2, galsG90, galsG95, priceD2, priceG90, priceG95, solesD2, solesG90,solesG95,gasPrices, date} = this.state;
	    var self = this;
	    var errors = {
	    		submit: '',
	    		pumpAttendantNames: '',
	    		price: '',
	    };
	    var formIsValid = true;
	    this.setState({ showError: false, showSuccess: false });
	    
	    var invoiceVo = {
			   
	    	clientAddress: clientAddress,
		    rucNumber: rucNumber,
		    clientName: clientName,
		    truckPlateNumber: truckPlateNumber,
		    galsD2: galsD2,
		    galsG90: galsG90,
		    galsG95: galsG95,
		    priceD2: priceD2,
		    priceG90: priceG90,
		    priceG95: priceG95,
		    solesD2: solesD2,
		    solesG90: solesG90,
		    solesG95: solesG95,
		    date: date,
	    	billType: 'factura',
	    	saveOrUpdate: 'save'
	    };

	    // Validation: Pump Attendant Names
	    /*if (pumpAttendantNames && pumpAttendantNames.trim().length >= 0) {
	    
	    } else {
	    	errors["pumpAttendantNames"] = "Falta nombre(s) de grifer@(s)";
			formIsValid = false;
	    }
	    
	    var gasPrice = {};
	    var validCostCount = 0;
	    var validPriceCount = 0;
		for(var i = 0; i < gasPrices.length; i++) {
			gasPrice = gasPrices[i];
			if (!isNaN(gasPrice.newCost) && gasPrice.newCost) {
				validCostCount++;
			}
			if (!isNaN(gasPrice.newPrice) && gasPrice.newPrice) {
				validPriceCount++
			}
		}
		
		if ((0 < validCostCount && validCostCount < gasPrices.length) || (0 < validPriceCount && validPriceCount < gasPrices.length) || (validCostCount == 0 && validPriceCount == 0)) {
			errors["price"] = "List precios o costos está incompleto";
			formIsValid = false;
		} else {
			
			var gasPrice = {};
			for(var i = 0; i < gasPrices.length; i++) {
				gasPrice = gasPrices[i];
				var gasPriceVo = {};
				gasPriceVo["fuelType"] = gasPrice.fuelType;
				
				if (validCostCount == 0 && validPriceCount == gasPrices.length) {
					gasPriceVo["price"] = gasPrice.newPrice;
					gasPriceVo["cost"] = gasPrice.cost;
				} else if (validPriceCount == 0 && validCostCount == gasPrices.length) {
					gasPriceVo["price"] = gasPrice.price;
					gasPriceVo["cost"] = gasPrice.newCost;
				} else if (validCostCount == gasPrices.length && validPriceCount == gasPrices.length) {
					gasPriceVo["price"] = gasPrice.newPrice;
					gasPriceVo["cost"] = gasPrice.newCost;
				}		
				
				gasPricesVo.gasPrices.push(gasPriceVo);
			}
		
		}
		
		this.setState({errors: errors}); */
		
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
					var InvoiceVoResp = data.result[0];
					self.setState({ showSuccess: true });
					
					var qrcode = new QRCode("qrcode");
					qrcode.makeCode("www.google.com");
				},
				error: function(e){
					var json = "<h4>Submit Error </h4><pre>" + e.responseText + "</pre>";
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
	      <strong>¡Error!</strong>{" " + this.state.errors.pumpAttendantNames + " - " + this.state.errors.newGals + " - " + this.state.errors.submit}  
		      	</div>
	      }
	      
	      {this.state.showSuccess && 
	      	<div className="alert alert-success">
	      		<strong>Success!</strong> Tu forma has sido remitida. 
	      	</div>
	      }
	      
	      <div className="invoice">
	          <div className="row invoice-logo">
	              <div className="col-xs-6 invoice-logo-space">
	                  <img src="../assets/pages/media/invoice/lajoya.png" className="img-responsive" alt="" /> </div>
	              <div className="col-xs-6">
	                  <p> <strong>FACTURA ELECTRÓNICA</strong> <br/>
	                      <span className="muted"> RUC: 20501568776  </span><br/>
	                      <span className="muted"> F001-338  </span>
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
	    	                  <label className="control-label">RUC</label>
	    	                  <input type="number" pattern="[0-9]*" className="form-control" placeholder="RUC" onBlur={this.onTabPress.bind(this)} onKeyPress={this.onKeyPress} inputMode="numeric"  value={this.state.rucNumber} onChange={this.rucNumberChange}/>
	    	              </div>
	    	          </div>
	    	          <div className="col-md-2">
	    	              <div className="form-group">
	    	                  <label className="control-label">Razón Social</label>
	    	                  <input type="text" className="form-control" placeholder="Nombre(s)" onKeyPress={this.onKeyPress} value={this.state.clientName} onChange={this.clientNameChange}/>
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
	    	          <div className="col-md-2">
	    	              <div className="form-group">
	    	                  <label className="control-label">Nro de Placa: </label>
	    	                  <input type="text" className="form-control" placeholder="Ingrese placa" onKeyPress={this.onKeyPress} value={this.state.truckPlateNumber} onChange={this.truckPlateNumberChange}/>
	    	              </div>
	    	          </div>
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
	                              <th className="hidden-sm-up"> Total </th>
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
	              <div className="col-xs-6">
	                  <div className="well">
	                      <address>
	                          <strong>Código Hash:</strong>
	                          <br/> HFXKvckrdfhzmrou%yntb7
                        	  <br/>
	                          <strong>Nro de Ticket:</strong>
	                          <br/> 503-9024410180828
	                          <br/> <strong>Código QR:</strong>
	                          <br/>  
                          </address>
	                      <address>
	                          <strong>Consulte su documento en:</strong>
	                          <a> www.grifolajoya.com </a>
	                      </address>
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
					                      <td className="text-center sbold">S/ {(((this.state.solesD2 + this.state.solesG90 + this.state.solesG95) * 100).toFixed() / 100 - ((this.state.solesD2 + this.state.solesG90 + this.state.solesG95) * 18).toFixed() / 100 ).toFixed(2)}</td>
					                  </tr>
					                  <tr>
					                      <td>
					                      	<strong>IGV (18%):</strong>
					                      </td>
					                      <td className="text-center sbold">S/ {(((this.state.solesD2 + this.state.solesG90 + this.state.solesG95) * 18).toFixed() / 100).toFixed(2)} </td>
					                  </tr>
					                  <tr>
					                      <td>
				                      		<strong>Importe Total:</strong>
					                      </td>
					                      <td className="text-center sbold">S/ {((((this.state.solesD2 + this.state.solesG90 + this.state.solesG95) * 100).toFixed() / 100)).toFixed(2)}</td>
					                  </tr>
					              </tbody>
					          </table>	
		          		  </div>
			          </div>
	                  <br/>
	                  <ReactToPrint trigger={() => <a type="submit" className="btn btn-lg blue hidden-print margin-bottom-5" > Imprimir&nbsp;<i className="fa fa-print"></i></a>} content={() => this.componentRef}></ReactToPrint>&nbsp;
	                  <button type="submit" className="btn btn-lg green hidden-print margin-bottom-5">
	    	          	<i className="fa fa-check"></i> Enviar Recibo
	    	          </button>
	              </div>
	          </div>
	      </div>
	      
	      <div className="invoice-content-2 " ref={el => (this.componentRef = el)}>
	          <div className="row invoice-head">
	              <div className="col-md-12 col-xs-12">
	                  <div className="invoice-logo">
	                      <img src="../assets/pages/media/invoice/lajoya.png" className="img-responsive" alt="" />
	                      <h1 className="uppercase">Factura</h1>
	                  </div>
	              </div>
	              <div className="col-md-12 col-xs-12">
	                  <div className="company-address">
	                      <span className="bold uppercase">La Joya de Santa Isabel EIRL</span>
	                      <br/> Av. Miguel Grau Mza B Lote 1-2 
	                      <br/> Lima - Lima - Ate 
	                      <br/>
	                      <span className="bold">T</span> +51 356 0345
	                      <br/>
	                      <span className="bold">W</span> www.grifolajoya.com </div>
	              </div>
	          </div>
	          <div className="row invoice-cust-add">
	              <div className="col-xs-12">
		              <table className="table table-hover">
			              <tbody>
				              <tr>
			                      <td>
			                      RUC
			                      </td>
			                      <td className="text-center sbold">{this.state.rucNumber}</td>
			                  </tr>
			              	  <tr>
			                      <td>
			                      Razon Social
			                      </td>
			                      <td className="text-center sbold">{this.state.clientName}</td>
			                  </tr>
			                  <tr>
			                      <td>
			                      Fecha
			                      </td>
			                      <td className="text-center sbold">{`${moment(this.state.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}</td>
			                  </tr>
			                  <tr>
			                      <td>
			                      Direccion
			                      </td>
			                      <td className="text-center sbold">{this.state.clientAddress}</td>
			                  </tr>
			                  <tr>
		                      <td>
			                      Placa de Vehiculo
			                      </td>
			                      <td className="text-center sbold">{this.state.truckPlateNumber}</td>
			                  </tr>
			              </tbody>
			          </table>
	              </div>
	          	  
	          </div>
	          <div className="row invoice-body">
	              <div className="col-xs-12 table-responsive">
	                  <table className="table table-hover">
	                      <thead>
	                          <tr>
                      			  <th className="invoice-title">Prod</th>
	                              <th className="invoice-title text-center">Cantidad</th>
	                              <th className="invoice-title text-center">Precio</th>
	                              <th className="invoice-title text-center">Importe</th>
	                          </tr>
	                      </thead>
	                      <tbody>
	                          <tr>
	                              <td>
	                                  01-Diesel 2
	                              </td>
	                              <td className="text-center sbold">{parseFloat(this.state.galsD2 || '0').toFixed(2)}</td>
	                              <td className="text-center sbold">{parseFloat(this.state.priceD2 || '0').toFixed(2)}</td>
	                              <td className="text-center sbold">{parseFloat(this.state.solesD2 || '0').toFixed(2)}</td>
	                          </tr>
	                          <tr>
	                          	  <td>
	                                  02-Gas 90
	                              </td>
	                              <td className="text-center sbold">{parseFloat(this.state.galsG90 || '0').toFixed(2)}</td>
	                              <td className="text-center sbold">{parseFloat(this.state.priceG90 || '0').toFixed(2)}</td>
	                              <td className="text-center sbold">{parseFloat(this.state.solesG90 || '0').toFixed(2)}</td>
	                          </tr>
	                          <tr>
	                          	  <td>
	                                  03-Gas 95
	                              </td>
	                              <td className="text-center sbold">{parseFloat(this.state.galsG95 || '0').toFixed(2)}</td>
	                              <td className="text-center sbold">{parseFloat(this.state.priceG95 || '0').toFixed(2)}</td>
	                              <td className="text-center sbold">{parseFloat(this.state.solesG95 || '0').toFixed(2)}</td>
	                          </tr>
	                      </tbody>
	                  </table>
	              </div>
	          </div>
	          <div className="row invoice-subtotal">
		          <div className="col-xs-12">
			          <table className="table table-hover">
			              <tbody>
			                  <tr>
			                      <td>
			                          Subtotal
			                      </td>
			                      <td className="text-center sbold">S/ {(((this.state.solesD2 + this.state.solesG90 + this.state.solesG95) * 100).toFixed() / 100 - ((this.state.solesD2 + this.state.solesG90 + this.state.solesG95) * 18).toFixed() / 100 ).toFixed(2)}</td>
			                  </tr>
			                  <tr>
			                      <td>
			                      IGV (18%)
			                      </td>
			                      <td className="text-center sbold">S/ {(((this.state.solesD2 + this.state.solesG90 + this.state.solesG95) * 18).toFixed() / 100).toFixed(2)}</td>
			                  </tr>
			                  <tr>
			                      <td>
			                          Total
			                      </td>
			                      <td className="text-center sbold">S/ {(((this.state.solesD2 + this.state.solesG90 + this.state.solesG95) * 100).toFixed(2) / 100).toFixed(2)}</td>
			                  </tr>
			              </tbody>
			          </table>	
          		  </div>
	          </div>
	          <div style={{width: '300px', height: '300px', display: 'block', margin: 'auto', zoom: 0.5}} >
	          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<div id="qrcode" className="col-xs-12 col-md-offset-3" ></div>
	          </div>
	      </div>
	      
      </form>
    )
  }
}

let target = document.getElementById('react-invoicing-id');

ReactDOM.render(<TableDashboard />, target);