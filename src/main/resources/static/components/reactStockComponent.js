
class StockForm extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: {},
	  showError: false,
	  showSuccess: false,
      pumpAttendantNames: '',
      date: '',
      tanks: []
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
  
  handleNewGalsChange = (idx) => (evt) => {
	  const newTanks = this.state.tanks.map((tank, sidx) => {
	      if (idx !== sidx) {
	    	  return tank;
	      }
	      
	      return { ...tank, newGals: evt.target.value  == '' ? '': (Math.floor(evt.target.value * 100) / 100)};
	    });
	    
	    this.setState({ tanks: newTanks });
	    
  }
  
  handleSubmit = (evt) => {
	
	evt.preventDefault();
    
	const { pumpAttendantNames, date, tanks} = this.state;
    var self = this;
    var errors = {
    		submit: '',
    		pumpAttendantNames: '',
    		newGals: ''
    };
    var formIsValid = true;
    this.setState({ showError: false, showSuccess: false });
    
    var tanksVo = {
    	pumpAttendantNames: pumpAttendantNames,
    	date: date,
    	tanks: []
    };

    // Validation: Pump Attendant Names
    if (pumpAttendantNames && pumpAttendantNames.trim().length >= 0) {
    
    } else {
    	errors["pumpAttendantNames"] = "Falta nombre(s) de grifer@(s)";
		formIsValid = false;
    }
    
    var tank = {};
	for(var i = 0; i < tanks.length; i++) {
		tank = tanks[i];
		if (!isNaN(tank.newGals) && tank.newGals) {
			var tankVo = {};
			tankVo["tankId"] = tank.tankId;
			tankVo["fuelType"] = tank.fuelType;
			tankVo["gals"] = tank.newGals;
			tanksVo.tanks.push(tankVo);
		} else {
			// Validation Group 1 and 2
			errors["newGals"] = "Galonaje de " + tank.tankId + "-" + tank.fuelType + " esta incompleto";
			formIsValid = false;
		}
	}
    
	this.setState({errors: errors}); 
	
	if (formIsValid) {
		
		jQuery.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/submitTanksVo",
			data: JSON.stringify(tanksVo),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				var tanksVoUpd = data.result[0];
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
  
  _fetchData(){
			
		var search = {};
		search["dateEnd"] = "today";
		search["dateBeg"] = "yesterday";
		
		jQuery.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/getLatestStock",
			data: JSON.stringify(search),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				var tanksVo = data.result[0];
				var currentDate = new Date();
				
				var tank = {};
				for(var i = 0; i < tanksVo.tanks.length; i++) {
					tank = tanksVo.tanks[i];
					tank["newGals"] = '';
				}
				
				this.setState({tanks: tanksVo.tanks});
				this.setState({date: currentDate});
			},
			error: function(e){

			}	
		});
	}
  
  componentWillMount(){
	  this._fetchData();
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
	      <strong>¡Error!</strong>{" " + this.state.errors.pumpAttendantNames + " - " + this.state.errors.newGals + " - " + this.state.errors.submit}  
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
		                      <span className="caption-subject font-dark sbold uppercase">Reinicializar Stock</span>
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
			    	          <div className="col-md-4">
			    	              <div className="form-group">
			    	                  <label className="control-label">Nombres de Grifero(s)</label>
			    	                  <input type="text" className="form-control" placeholder="Nombre1, Nombre2, ..." onKeyPress={this.onKeyPress} value={this.state.pumpAttendantNames} onChange={this.handlePumpAttendantNamesChange}/>
			    	              </div>
			    	          </div>
			    	          <div className="col-md-4">
			    	              <div className="form-group">
			    	                  <label className="control-label">Fecha</label>
			    	                  <input type="text" id="lastName" className="form-control" placeholder="Fecha" value={`${moment().tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}  readOnly/>
			    	              </div>
			    	          </div>
			    	      </div>	
			          	  
			          	  <div className="row">
			    		      
			    		      <div className="col-md-3">
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
				  					            				Id
				  					            			</th>
				  					            			<th>
				  					            				Prod
				  					            			</th>
				  					            			<th>
				  					            				Actual
				  					            			</th>
				  					            			<th>
				  					            				Reactualizado
				  					            			</th>
				  					            		</tr>
				  					            		{this.state.tanks.map((tank, idx) => (	
					  					            		<tr key={`trItem${idx}`}>
						  					            		<td>
					  						            			<input style={{width: '20px'}} type="text" key={`tankId${idx}`} onKeyPress={this.onKeyPress} value={tank.tankId} readOnly/>
				  						            			</td>
					  					            			<td>
					  						            			<input style={{width: '30px'}} type="text" key={`fueType${idx}`} onKeyPress={this.onKeyPress} value={tank.fuelType} readOnly/>
				  						            			</td>
				  						            			<td>
					  						            			<input style={{width: '85px'}} type="text" key={`gals${idx}`} onKeyPress={this.onKeyPress} value={tank.gals} readOnly/>
				  						            			</td>
				  						            			<td>
					  						            			<input type="number" style={{size:10, width:'100px', textAlign: 'right'}} pattern="[0-9]*" onKeyPress={this.onKeyPress} key={`newGals${idx}`} placeholder={`Nuevo Stock`} inputMode="numeric" value={tank.newGals}  onChange={this.handleNewGalsChange(idx)}/>
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

let target = document.getElementById('stock-form-page');

ReactDOM.render(<StockForm />, target);