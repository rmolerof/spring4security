
class IncorporationForm extends React.Component {
  constructor() {
    super();
    this.state = {
      name: '',
      pumpAttendantNames: '',
      date: '',
      shift: '',
      shareholders1: [],
      shareholders2: []
    };
  }
  
  handlePumpAttendantNamesChange = (evt) => {
    this.setState({ pumpAttendantNames: evt.target.value });
  }
  
  handleNumEndChange1 = (idx) => (evt) => {
    const newShareholders = this.state.shareholders1.map((shareholder, sidx) => {
      if (idx !== sidx) return shareholder;
      return { ...shareholder, numEnd: evt.target.value };
    });
    
    this.setState({ shareholders1: newShareholders });
  }

  handleNumBegChange1 = (idx) => (evt) => {
    const newShareholders = this.state.shareholders1.map((shareholder, sidx) => {
      if (idx !== sidx) return shareholder;
      return { ...shareholder, numBeg: evt.target.value };
    });
    
    this.setState({ shareholders1: newShareholders });
  }
  
  handleNumEndChange2 = (idx) => (evt) => {
    const newShareholders = this.state.shareholders2.map((shareholder, sidx) => {
      if (idx !== sidx) return shareholder;
      return { ...shareholder, numEnd: evt.target.value };
    });
    
    this.setState({ shareholders2: newShareholders });
  }

  handleNumBegChange2 = (idx) => (evt) => {
    const newShareholders = this.state.shareholders2.map((shareholder, sidx) => {
      if (idx !== sidx) return shareholder;
      return { ...shareholder, numBeg: evt.target.value };
    });
    
    this.setState({ shareholders2: newShareholders });
  }
  
  handleSubmit = (evt) => {
	evt.preventDefault();
	  
    const { pumpAttendantNames, date, shift, shareholders1, shareholders2 } = this.state;
    var self = this;
    var formIsValid = true;
    var shareholders = shareholders1.concat(shareholders2);
    var dayDataVO = {
    	pumpAttendantNames: pumpAttendantNames,
    	date: date,
    	shift: shift,
    	dayData: {}	
    };
    var errors = {
    		numEnd: '',
    		submit: '',
    		pumpAttendantNames: ''
    };
    this.setState({ showError: false, showSuccess: false });
    
    // Validation: Pump Attendant Names
    if (pumpAttendantNames && pumpAttendantNames.trim().length >= 0) {
    
    } else {
    	errors["pumpAttendantNames"] = "Falta nombre(s) de grifer@(s)";
		formIsValid = false;
    }
    
    var shareholder = {};
	for(var i = 0; i < shareholders.length; i++) {
		shareholder = shareholders[i];
		if (!isNaN(shareholder.numEnd) && shareholder.numEnd) {
			dayDataVO.dayData[shareholder.name] = shareholder.numEnd;
		} else {
			// Validation Group 1 and 2
			errors["numEnd"] = "Nuevo número de máquina " + shareholder.name + " esta incompleto";
			formIsValid = false;
		}
	}
	
	this.setState({errors: errors});
	
	if (formIsValid) {
	    jQuery.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/resetStatus",
			data: JSON.stringify(dayDataVO),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				console.log("submitDayData -> SUCCESS: ", data);
				var json = "<h4>submitDayData Response</h4><pre>" + JSON.stringify(data, null, 4) + "</pre>";
				var station = data.result[0];
				self.setState({ showSuccess: true });
			},
			error: function(e){
				var json = "<h4>Submit Error </h4><pre>" + e.responseText + "</pre>";
				console.log("ERROR: ", e);
			}	
		});
	} else {
		
		this._toggleError();
	}
  }
  
  _toggleError = () => {
	  this.setState((prevState, props) => {
		  return {showError: !prevState.showError}
	  })
  };
  
  handleAddShareholder = () => {
    this.setState({ shareholders: this.state.shareholders.concat([{ name: '' }]) });
  }
  
  handleRemoveShareholder = (idx) => () => {
    this.setState({ shareholders: this.state.shareholders.filter((s, sidx) => idx !== sidx) });
  }
  
  _fetchData(timeframe){
			
		var search = {};
		search["dateEnd"] = timeframe.dateEnd;
		search["dateBeg"] = timeframe.dateBeg;
		
		//jQuery("#btn-search").prop("disabled", true);
		
		jQuery.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/getStationStatusByDates",
			data: JSON.stringify(search),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				var json = "<h4>Ajax Response</h4><pre>" + JSON.stringify(data, null, 4) + "</pre>";
				var station = data.result[0];
				var currentDate = new Date();
				var currentShift = station.shift == "1" ? "2": "1";
				
				// Iterate through Dispensers
				var shareholdersResponse1 = [];
				var shareholdersResponse2 = [];
				var i = 0;
				for(var property in station.dispensers){
					if (station.dispensers.hasOwnProperty(property)) {
						var dispenser = station.dispensers[property];
						
						var galsWithZeros = dispenser.gallons.toFixed(2) + "";
						while (galsWithZeros.length < dispenser.pattern) galsWithZeros = "0" + galsWithZeros;
						
						const shareholder = {
								name: dispenser.name + "_" + dispenser.id,
								price: dispenser.price,
								numBeg: '',
								numEnd: galsWithZeros,
								pattern: dispenser.pattern == 8 ?  "11111.11": "111111.11"};
						if (i < 6) {
							shareholdersResponse1.push(shareholder);
						} else {
							shareholdersResponse2.push(shareholder);
						}
						i++;
					} 
				}
				this.setState({shareholders1: this.state.shareholders1.concat(shareholdersResponse1)});
				this.setState({shareholders2: this.state.shareholders2.concat(shareholdersResponse2)});
				this.setState({name: station.name});
				this.setState({date: currentDate});
				this.setState({shift: currentShift});
			},
			error: function(e){
				var json = "<h4>Ajax Response</h4><pre>" + e.responseText + "</pre>";
				console.log("ERROR: ", e);
			}	
		});
	}
  
  _getNextShift(shift) {
		
	  	
	}
  
  _fetchComments(){
		var shareholdersResponse = [];
		for(var i = 0; i < 6; i++){
			const shareholder = {
					name: 'D' + `${i + 1}`,
					price: `${i/10}`,
					numBeg: `${1000 * i}`,
					numEnd: `${2000 * i}` };
			shareholdersResponse.push(shareholder);
		}
		this.setState({shareholders1: this.state.shareholders1.concat(shareholdersResponse)});
		
		shareholdersResponse = [];
		for(var i = 6; i < 12; i++){
			const shareholder = {
					name: 'D' + `${i + 1}`,
					price: `${i/10}`,
					numBeg: `${1000 * i}`,
					numEnd: `${2000 * i}` };
			shareholdersResponse.push(shareholder);
		}
		this.setState({shareholders2: this.state.shareholders2.concat(shareholdersResponse)});
  }
  
  componentWillMount(){
	  //this._fetchComments();
	  this._fetchData({dateEnd: "latest", dateBeg: ""});
  }
  
  render() {    
    return (
    
      <form onSubmit={this.handleSubmit}>
	      {this.state.showError && 
		        <div className="alert alert-danger">
		      		<strong>¡Error!</strong>{" " + this.state.errors.pumpAttendantNames + " - " + this.state.errors.numEnd + " - " + this.state.errors.submit}  
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
		                      <i className="icon-social-dribbble font-green"></i>
		                      <span className="caption-subject font-green bold uppercase">Reinicializar los números de máquinas</span>
		                  </div>
		              </div>
		              
					    <div className="form-body">
					      {/*<h3 className="form-section">Grifo {this.state.name}</h3>*/}
					      <div className="row">
					          <div className="col-md-4">
					              <div className="form-group">
					                  <label className="control-label">Nombres de Grifero(s)</label>
					                  <input type="text" className="form-control" placeholder="Nombre1, Nombre2, ..." value={this.state.pumpAttendantNames} onChange={this.handlePumpAttendantNamesChange}/>
					              </div>
					          </div>
					          <div className="col-md-4">
					              <div className="form-group">
					                  <label className="control-label">Fecha</label>
					                  <input type="text" id="lastName" className="form-control" placeholder="Fecha" value={`${moment().tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}  readOnly/>
					              </div>
					          </div>
					          <div className="col-md-4">
					              <div className="form-group">
					                  <label className="control-label">Turno</label>
					                  <input type="text" id="lastName" className="form-control" placeholder="Turno" value={this.state.shift} readOnly/>
					              </div>
					          </div>
					      </div>	
				      	  
				      	  <div className="row">
						      <div className="col-md-3">
						          <div className="portlet box red">
						              <div className="portlet-title">
						                  <div className="caption">
						                      <i className="fa fa-gift"></i>Grupo 1 de Máquinas</div>
						              </div>
						              
						              <div className="portlet-body form">
							              <div className="portlet-body">
							                  <div className="table-responsive">  
								              	<table>
									      	        <tbody>
									      				<tr>
									      					<td>
							      					            <table className="table table-bordered">
							      					            	<tbody>
							      					            		<tr>
							      					            			<th>
							      					            				Prod
							      					            			</th>
							      					            			<th>
							      					            				Nuevo Número
							      					            			</th>
							      					            		</tr>
							      					            		{this.state.shareholders1.map((shareholder, idx) => (
								      					            		<tr key={`d1${idx}`}>
								      						            		<td>
								      						            			<input  style={{border: 'none', width: '40px'}} type="text" key={`prod${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.name} readOnly/>
								      						            		</td>
								      						            		<td>
								      						            			<MaskedInput style={{size:10, width:'80px', textAlign: 'right'}} mask={shareholder.pattern} key={`end${idx}`} placeholder={`Num ${shareholder.name}`} value={`${shareholder.numEnd}`} onChange={this.handleNumEndChange1(idx)}/>
								      						                    </td>
								      						            	</tr>
							      					            		))}
							      					            	</tbody>
							      					            </table>
									      					</td>
									      				</tr>
										      		</tbody>
									              </table>
								              </div>
					                      </div>
						              </div>
						              
						          </div>
						      </div>
						      
						      <div className="col-md-3">
						          <div className="portlet box purple">
						              <div className="portlet-title">
						                  <div className="caption">
						                      <i className="fa fa-gift"></i>Grupo 2 de Máquinas</div>
						              </div>
						              
						              <div className="portlet-body form">
						              	<div className="portlet-body">
						                  <div className="table-responsive"> 
							                  <table>
								      	        <tbody>
								      				<tr>
								      					<td>
								  					          <div className="shareholder">
								  					            <table className="table table-bordered">
								  					            	<tbody>
								  					            		<tr>
								  					            			<th>
								  					            				Prod
								  					            			</th>
								  					            			<th>
								  					            				Nuevo Número
								  					            			</th>
								  					            		</tr>
								  					            		{this.state.shareholders2.map((shareholder, idx) => (
									  					            		<tr key={`d2${idx}`}>
									  						            		<td>
									  						            			<input style={{border: 'none', width: '40px'}} type="text" key={`prod${idx}`} value={shareholder.name} readOnly/>
									  						            		</td>
									  						            		<td>
									  						            			<MaskedInput style={{size:10, width:'80px', textAlign: 'right'}} mask={shareholder.pattern} key={`end${idx}`} placeholder={`Num ${shareholder.name}`} value={`${shareholder.numEnd}`} onChange={this.handleNumEndChange2(idx)}/>
									  						                    </td>
									  						            	</tr>
								  					            		))}
								  					            	</tbody>
								  					            </table>
								  					          </div>
									      				</td>
									      			</tr>
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

let target = document.getElementById('story-app');

ReactDOM.render(<IncorporationForm />, target);