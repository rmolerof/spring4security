
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
    const { pumpAttendantNames, date, shift, shareholders1, shareholders2 } = this.state;
    //alert(`Incorporated: ${name} with ${shareholders1.length} shareholders1 and ${shareholders2.length} shareholders2`);
    
    var shareholders = shareholders1.concat(shareholders2);
    var dayDataVO = {
    	pumpAttendantNames: pumpAttendantNames,
    	date: date,
    	shift: shift,
    	dayData: {}	
    };

    var shareholder = {};
	for(var i = 0; i < shareholders.length; i++) {
		shareholder = shareholders[i];
		dayDataVO.dayData[shareholder.name] = shareholder.numEnd;
	}
	
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
			
		},
		error: function(e){
			var json = "<h4>Submit Error </h4><pre>" + e.responseText + "</pre>";
			console.log("ERROR: ", e);
		}	
	});
  }
  
  handleAddShareholder = () => {
    this.setState({ shareholders: this.state.shareholders.concat([{ name: '' }]) });
  }
  
  handleRemoveShareholder = (idx) => () => {
    this.setState({ shareholders: this.state.shareholders.filter((s, sidx) => idx !== sidx) });
  }
  
  _fetchData(){
			
		var search = {};
		search["dateEnd"] = "today";
		search["dateBeg"] = "yesterday";
		
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
				console.log("getStationStatusByDates -> SUCCESS: ", data);
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
	  this._fetchData();
  }
  
  render() {    
    return (
    
      <form onSubmit={this.handleSubmit}>
      	
	    <div className="form-body">
	      <h3 className="form-section">Grifo {this.state.name}</h3>
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
	                  <input type="text" id="lastName" className="form-control" placeholder="Fecha" value={this.state.date}  readOnly/>
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
					      						{this.state.shareholders1.map((shareholder, idx) => (
					      					          <div key={`d${idx}`} className="shareholder">
					      					            
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
					      					            		<tr>
					      						            		<td>
					      						            			<input  style={{border: 'none', width: '40px'}} type="text" key={`prod${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.name} readOnly/>
					      						            		</td>
					      						            		<td>
					      						            			<MaskedInput style={{size:10, width:'80px', textAlign: 'right'}} mask={shareholder.pattern} key={`end${idx}`} placeholder={`Num ${shareholder.name}`} value={`${shareholder.numEnd}`} onChange={this.handleNumEndChange1(idx)}/>
					      						                    </td>
					      						            	</tr>
					      					            	</tbody>
					      					            </table>
					      					          </div>
						      				        ))}
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
					      					{this.state.shareholders2.map((shareholder, idx) => (
					  					          <div key={`d${idx}`} className="shareholder">
					  					            
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
					  					            		<tr>
					  						            		<td>
					  						            			<input style={{border: 'none', width: '40px'}} type="text" key={`prod${idx}`} value={shareholder.name} readOnly/>
					  						            		</td>
					  						            		<td>
					  						            			<MaskedInput style={{size:10, width:'80px', textAlign: 'right'}} mask={shareholder.pattern} key={`end${idx}`} placeholder={`Num ${shareholder.name}`} value={`${shareholder.numEnd}`} onChange={this.handleNumEndChange2(idx)}/>
					  						                    </td>
					  						            	</tr>
					  					            	</tbody>
					  					            </table>
					  					          </div>
					  				        ))}
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
  
	      <button type="button" key={"addShareholder"} onClick={this.handleAddShareholder} className="small">Add Shareholder</button>
		  <button>Incorporate</button>
		</div>
      </form>
    )
  }
}

let target = document.getElementById('story-app');

ReactDOM.render(<IncorporationForm />, target);