
class PriceForm extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: {},
	  showError: false,
	  showSuccess: false,
      pumpAttendantNames: '',
      date: '',
      gasPrices: [],
      saveOrUpdate: 'save'
    };
  }
  
  _toggleError = () => {
	  this.setState((prevState, props) => {
		  return {showError: !prevState.showError}
	  })
  };
  
  handlePumpAttendantNamesChange = (evt) => {
    this.setState({ pumpAttendantNames: evt.target.value });
  }
  
  handleNewCostChange = (idx) => (evt) => {
	  const newGasPrices = this.state.gasPrices.map((gasPrice, sidx) => {
	      if (idx !== sidx) {
	    	  return gasPrice;
	      }
	      
	      return { ...gasPrice, newCost: evt.target.value  == '' ? '': (Math.floor(evt.target.value * 100) / 100)};
	    });
	    
	    this.setState({ gasPrices: newGasPrices });
  }
  
  handleNewPriceChange = (idx) => (evt) => {
	  const newGasPrices = this.state.gasPrices.map((gasPrice, sidx) => {
	      if (idx !== sidx) {
	    	  return gasPrice;
	      }
	      
	      return { ...gasPrice, newPrice: evt.target.value  == '' ? '': (Math.floor(evt.target.value * 100) / 100)};
	    });
	    
	    this.setState({ gasPrices: newGasPrices });
  }
  
  handleSubmit = (evt) => {
	
	evt.preventDefault();
    
	const { pumpAttendantNames, date, gasPrices, saveOrUpdate} = this.state;
    var self = this;
    var errors = {
    		submit: '',
    		pumpAttendantNames: '',
    		price: '',
    };
    var formIsValid = true;
    this.setState({ showError: false, showSuccess: false });
    
    var gasPricesVo = {
    	pumpAttendantNames: pumpAttendantNames,
    	date: date,
    	gasPrices: [],
    	saveOrUpdate: saveOrUpdate
    };

    // Validation: Pump Attendant Names
    if (pumpAttendantNames && pumpAttendantNames.trim().length >= 0) {
    
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
	
	this.setState({errors: errors}); 
	
	if (formIsValid) {
		
		jQuery.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/submitGasPricesVo",
			data: JSON.stringify(gasPricesVo),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				var gasPricesVoUpd = data.result[0];
				self.setState({ showSuccess: true });
				
			},
			error: function(e){
				var json = "<h4>Submit Error </h4><pre>" + e.responseText + "</pre>";
				console.log("ERROR: ", e);
				errors["submit"] = "Forma no aceptada. Intente otra vez";
				self._toggleError();
			}	
		});
	} else {
		
		this._toggleError();
	}

  }
  
  loadPrevious = () => {
	  this.setState({saveOrUpdate:  'update'});
	  this._fetchData({dateEnd: "latest", dateBeg: "previous"});
  }
  
  _fetchData(timeframe){
			
	  	var search = {};
	  	var self = this; 
	  	var errors = {
	    	submit: ''
	    };
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
				if (data.result.length == 0) {
					errors["submit"] = data.msg;
					self.setState({errors: errors});
					self._toggleError();
				} else if (data.result.length == 1) {
					var gasPricesVo = data.result[0];
					var currentDate = new Date();
					
					var gasPrice = {};
					for(var i = 0; i < gasPricesVo.gasPrices.length; i++) {
						gasPrice = gasPricesVo.gasPrices[i];
						gasPrice["newCost"] = '';
						gasPrice["newPrice"] = '';
					}
					
					this.setState({gasPrices: gasPricesVo.gasPrices});
					this.setState({date: currentDate});
				} else {
					var gasPricesVoLatest = data.result[0];
					var gasPricesVoPrevious = data.result[1];
					
					var gasPricesLatest = {};
					var gasPricesPrevious = {};
					for(var i = 0; i < gasPricesVoLatest.gasPrices.length; i++) {
						gasPricesLatest = gasPricesVoLatest.tanks[i];
						gasPricesPrevious = gasPricesVoPrevious.tanks[i];
						
						gasPricesLatest["newCost"] = gasPricesLatest["cost"];
						gasPricesLatest["newPrice"] = gasPricesLatest["price"];
						gasPricesLatest["cost"] = gasPricesPrevious["cost"];
						gasPricesLatest["price"] = gasPricesPrevious["price"];
					}
					
					this.setState({gasPrices: gasPricesVoLatest.gasPrices});
					this.setState({date: gasPricesVoLatest.date});
					this.setState({pumpAttendantNames: gasPricesVoLatest.pumpAttendantNames});
				}
			},
			error: function(e){

			}	
		});
	}
  
  componentWillMount(){
	  this._fetchData({dateEnd: "latest", dateBeg: ""});
  }
  
  onKeyPress(event) {
	if (event.which === 13 ) {
		event.preventDefault();
	}
  }
  
  render() {    
    return (
    
      <form onSubmit={this.handleSubmit}>
		  {this.state.showError && 
		        <div className="alert alert-danger">
		      		<strong>¡Error!</strong>{" " + this.state.errors.pumpAttendantNames + " - " + this.state.errors.price + " - " + this.state.errors.submit}  
		      	</div>
		  }
		    
		  {this.state.showSuccess && 
		    	<div className="alert alert-success">
		    		<strong>Success!</strong> Tu forma has sido remitida. 
		    	</div>
		  }
	      <div className="row">
		      <div className="col-md-12">
		          <div className="portlet light form-fit ">
		              <div className="portlet-title">
		                  <div className="caption">
		                      <i className="icon-settings font-dark"></i>
		                      <span className="caption-subject font-dark sbold uppercase">Reinicializar Precios</span>
		                  </div>
		                  <div className="actions">
		                      <div className="btn-group btn-group-devided" data-toggle="buttons">
		                          <label className="btn dark btn-outline btn-circle btn-sm active">
		                              <input type="radio" name="options" className="toggle" id="option1"/>Settings</label>
		                          <label className="btn 	dark btn-outline btn-circle btn-sm">
		                              <input type="radio" name="options" className="toggle" id="option2"/>Tools</label>
		                      </div>
		                  </div>
		              </div>
		              <div className="portlet-body form">
		              		
			    	      <div className="row">
			    	          <div className="col-md-2">
			    	              <div className="form-group">
			    	                  <label className="control-label">Nombres de Grifero(s)</label>
			    	                  <input type="text" className="form-control" placeholder="Nombre1, Nombre2, ..." onKeyPress={this.onKeyPress} value={this.state.pumpAttendantNames} onChange={this.handlePumpAttendantNamesChange}/>
			    	              </div>
			    	          </div>
			    	          <div className="col-md-2">
			    	              <div className="form-group">
			    	                  <label className="control-label">Fecha</label>
			    	                  <input type="text" id="lastName" className="form-control" placeholder="Fecha" value={`${moment().tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}  readOnly/>
			    	              </div>
			    	          </div>
			    	          <div className="col-md-2">
					              <div className="form-group">
					              	  <label className="control-label">&nbsp;</label><br></br>
							          <a onClick={this.loadPrevious} className="btn red">
	                                    <i className="fa fa-edit"></i> Editar Anterior
	                                </a>
					              </div>
					          </div>
			    	      </div>	
			          	  
			          	  <div className="row">
			    		      
					          <div className="col-md-4">
			    		          <div className="portlet box green">
			    		              <div className="portlet-title">
			    		                  <div className="caption">
			    		                      <i className="fa fa-gift"></i>Resultados</div>
			    		              </div>
			    		              
			    		              <div className="portlet-body form">
			    		              	<div className="portlet-body">
			    			              	<div className="table-responsive">
			    			              	
				  					            <table className="table table-hover table-light table-bordered">
				  					            	<tbody>
				  					            		<tr>
				  					            			<th>
				  					            				Prod
				  					            			</th>
				  					            			<th>
				  					            				Costo
				  					            			</th>
			  					            				<th>
				  					            				Nvo Costo
				  					            			</th>
			  					            				<th>
				  					            				Venta
				  					            			</th>
				  					            			<th>
				  					            			    Nvo Venta
				  					            			</th>	
				  					            		</tr>
				  					            		{this.state.gasPrices.map((gasPrice, idx) => (	
					  					            		<tr key={`trItem${idx}`}>
						  					            		<td>
					  						            			<input style={{width: '30px'}} type="text" key={`fuelType${idx}`} onKeyPress={this.onKeyPress} value={gasPrice.fuelType} readOnly/>
				  						            			</td>
					  					            			<td>
					  						            			<input style={{width: '60px'}} type="text" key={`cost${idx}`} onKeyPress={this.onKeyPress} value={gasPrice.cost} readOnly/>
				  						            			</td>
				  						            			<td>
					  						            			<input type="number" style={{size:10, width:'60px', textAlign: 'right'}} pattern="[0-9]*" onKeyPress={this.onKeyPress} key={`newCost${idx}`} placeholder={`Nvo Costo`} inputMode="numeric" value={gasPrice.newCost}  onChange={this.handleNewCostChange(idx)}/>
					  						            		</td>
				  						            			<td>
					  						            			<input style={{width: '60px'}} type="text" key={`price${idx}`} onKeyPress={this.onKeyPress} value={gasPrice.price} readOnly/>
				  						            			</td>
				  						            			<td>
					  						            			<input type="number" style={{size:10, width:'60px', textAlign: 'right'}} pattern="[0-9]*" onKeyPress={this.onKeyPress} key={`newPrice${idx}`} placeholder={`Nvo Venta`} inputMode="numeric" value={gasPrice.newPrice}  onChange={this.handleNewPriceChange(idx)}/>
					  						            		</td>	
					  						            	</tr>
				  					            		))}
				  					            	</tbody>
				  					            </table>
			    				              
			    			                </div>
			    		                </div>
			    		              </div>
			    		              
			    		          </div>
			    		      </div>
			    		      
			    		  </div>
			    		  
			    		  <div className="form-actions">
			    		      <button type="submit" className="btn blue">
			    	          	<i className="fa fa-check"></i> Enviar
			    	          </button>
			    		      <button type="button" className="btn default">Cancelar</button>
			    		  </div>
		              </div>
		          </div>
		      </div>
		  </div>
      </form>
    )
  }
}

let target = document.getElementById('price-form-page');

ReactDOM.render(<PriceForm />, target);