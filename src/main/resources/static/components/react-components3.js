
class IncorporationForm extends React.Component {
  constructor() {
    super();
    this.state = {
      name: '',
      shareholders1: [],
      shareholders2: []
    };
  }
  
  handleNameChange = (evt) => {
    this.setState({ name: evt.target.value });
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
    const { name, shareholders1, shareholders2 } = this.state;
    //alert(`Incorporated: ${name} with ${shareholders1.length} shareholders1 and ${shareholders2.length} shareholders2`);
    
    var shareholders = shareholders1.concat(shareholders2);
    var dayDataVO = {
    	date: new Date(),
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
		url:"/api/submitDayData",
		data: JSON.stringify(dayDataVO),
		datatype: 'json',
		cache: false,
		timeout: 600000,
		success: (data) => {
			console.log("SUCCESS : ", data);
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
				console.log("SUCCESS : ", data);
				var json = "<h4>Ajax Response</h4><pre>" + JSON.stringify(data, null, 4) + "</pre>";
				var station = data.result[0];
				
				
				// Input Data
				var dayData = {};
				dayData["d2_1"] = 289041.95;
				dayData["d2_2"] = 144382.63;
				dayData["d2_3"] = 73242.59;
				dayData["d2_4"] = 211990.12;
				dayData["d2_5"] = 724116.58;
				dayData["d2_6"] = 83397.64;
				dayData["g90_1"] = 39187.64;
				dayData["g90_2"] = 32222.86;
				dayData["g90_3"] = 64773.44;
				dayData["g90_4"] = 174827.79;
				dayData["g95_1"] = 96795.94;
				dayData["g95_2"] = 99017.05;
				
				
				// Iterate through Dispensers
				var shareholdersResponse1 = [];
				var shareholdersResponse2 = [];
				var i = 0;
				for(var property in station.dispensers){
					if (station.dispensers.hasOwnProperty(property)) {
						var dispenser = station.dispensers[property];
						const shareholder = {
								name: dispenser.name + "_" + dispenser.id,
								price: dispenser.price,
								numBeg: dispenser.gallons,
								numEnd: dayData[property],
								pattern: dispenser.gallons.toString().length == 8 ?  "11111.11": "111111.11"};
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
				
			},
			error: function(e){
				var json = "<h4>Ajax Response</h4><pre>" + e.responseText + "</pre>";
				console.log("ERROR: ", e);
			}	
		});
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
      
      {/*<MaskedInput mask="111111.11" name="card" size="20" onChange={this._onChange}/>*/}
      
      <div className="row">
	      <div className="col-md-3">
	          <div className="portlet box red">
	              <div className="portlet-title">
	                  <div className="caption">
	                      <i className="fa fa-gift"></i>Grupo 1 de Maquinas</div>
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
				      					            
				      					            {/*<button type="button" key={`b${idx}`} onClick={this.handleRemoveShareholder(idx)} className="small">-</button>*/}
				      					            <table className="table table-bordered">
				      					            	<tbody>
				      					            		<tr>
				      					            			<th>
				      					            				Prod
				      					            			</th>
				      					            			<th>
				      					            				Soles
				      					            			</th>
				      					            			<th>
				      					            				Venta
				      					            			</th>
				      					            		</tr>
				      					            		<tr>
				      						            		<td>
				      						            			<input  style={{border: 'none', width: '40px'}} type="text" key={`prod${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.name} readOnly/>
				      						            		</td>
				      						            		<td>
				      						            			<input style={{border: 'none', width: '80px'}} type="text" key={`price${idx}`} placeholder={`Numero ${idx + 1}`} value={`${((shareholder.numEnd - shareholder.numBeg) * shareholder.price).toFixed(2)}`} readOnly/>
				      						            		</td>
					      						            		<td>
					      						            			<table>
					      						            				<tbody>
					      						            					<tr>
					      						            						<td>
					      						            							<MaskedInput style={{size:10, width:'80px', textAlign: 'right'}} mask={shareholder.pattern} key={`end${idx}`} placeholder={`Num ${shareholder.name}`} value={''} onChange={this.handleNumEndChange1(idx)}/>
					      						            						</td>
					      						            					</tr>
					      						            					<tr>
					      						            						<td>
					      						            							<input style={{border: 'none', width: '80px', textAlign: 'right'}} type="text" key={`beg${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.numBeg} onChange={this.handleNumBegChange1(idx)}/>
					      						            						</td>
					      						            					</tr>
					      						            					<tr>
					      					            						<td>
					      					            							<input style={{borderBottomWidth: '4px', width: '80px', textAlign: 'right'}} type="text" key={`dif${idx}`} placeholder={`Numero ${idx + 1}`} value={`${(shareholder.numEnd - shareholder.numBeg).toFixed(2)}`} readOnly/>
					      					            						</td>
					      					            					</tr>
					      						            				</tbody>
					      						            			</table>
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
	                      <i className="fa fa-gift"></i>Grupo 2 de Maquinas</div>
	              </div>
	              
	              <div className="portlet-body form">
	                  <table>
		      	        <tbody>
		      				<tr>
		      					<td>
			      					{this.state.shareholders2.map((shareholder, idx) => (
			  					          <div key={`d${idx}`} className="shareholder">
			  					            
			  					            {/*<button type="button" key={`b${idx}`} onClick={this.handleRemoveShareholder(idx)} className="small">-</button>*/}
			  					            <table>
			  					            	<tbody>
			  					            		<tr>
			  					            			<th>
			  					            				Product
			  					            			</th>
			  					            			<th>
			  					            				Soles
			  					            			</th>
			  					            			<th>
			  					            				Venta
			  					            			</th>
			  					            		</tr>
			  					            		<tr>
			  						            		<td>
			  						            			<input style={{border: 'none', width: '40px'}} type="text" key={`prod${idx}`} value={shareholder.name} readOnly/>
			  						            		</td>
			  						            		<td>
			  						            			<input style={{border: 'none', width: '80px'}} type="text" key={`price${idx}`} value={`${((shareholder.numEnd - shareholder.numBeg) * shareholder.price).toFixed(2)}`} readOnly/>
			  						            		</td>
			  						            		<td>
			  						            			<table>
			  						            				<tbody>
			  						            					<tr>
			  						            						<td>
			  						            							<input style={{width: '80px'}} type="text" key={`end${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.numEnd} onChange={this.handleNumEndChange2(idx)}/>
			  					            							</td>
			  						            					</tr>
			  						            					<tr>
			  						            						<td>
			  						            							<input style={{border: 'none', width: '80px'}} type="text" key={`beg${idx}`}  value={shareholder.numBeg} onChange={this.handleNumBegChange1(idx)}/>
			  						            						</td>
			  						            					</tr>
			  						            					<tr>
			  					            						<td>
			  					            							<input style={{border: 'none', width: '80px'}} type="text" key={`dif${idx}`} placeholder={`Gals Vendidos ${idx + 1}`} value={`${(shareholder.numEnd - shareholder.numBeg).toFixed(2)}`} readOnly/>
			  					            						</td>
			  					            					</tr>
			  						            				</tbody>
			  						            			</table>
			  						                    </td>
			  						            	</tr>
			  					            	</tbody>
			  					            </table>
			  					            <br />
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
	  
	  <div className="form-actions">
	      <button type="submit" className="btn blue">
          	<i className="fa fa-check"></i> Submit
          </button>
	      <button type="button" className="btn default">Cancel</button>
	  </div>
  
  
        <input type="text" placeholder="Date" value={this.state.name} onChange={this.handleNameChange} />
      
        <h4>Nuevos número de máquina</h4>
      
        <table>
	        <tbody>
				<tr>
					<td>
						{this.state.shareholders1.map((shareholder, idx) => (
					          <div key={`d${idx}`} className="shareholder">
					            
					            {/*<button type="button" key={`b${idx}`} onClick={this.handleRemoveShareholder(idx)} className="small">-</button>*/}
					            <table>
					            	<tbody>
					            		<tr>
					            			<th>
					            				Product
					            			</th>
					            			<th>
					            				Soles
					            			</th>
					            			<th>
					            				Venta
					            			</th>
					            		</tr>
					            		<tr>
						            		<td>
						            			<input type="text" key={`prod${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.name} readOnly/>
						            		</td>
						            		<td>
						            			<input type="text" key={`price${idx}`} placeholder={`Numero ${idx + 1}`} value={`${(shareholder.numEnd - shareholder.numBeg) * shareholder.price}`} readOnly/>
						            		</td>
						            		<td>
						            			<table>
						            				<tbody>
						            					<tr>
						            						<td>
						            							<input type="text" key={`end${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.numEnd} onChange={this.handleNumEndChange1(idx)}/>
					            							</td>
						            					</tr>
						            					<tr>
						            						<td>
						            							<input type="text" key={`beg${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.numBeg} onChange={this.handleNumBegChange1(idx)}/>
						            						</td>
						            					</tr>
						            					<tr>
					            						<td>
					            							<input type="text" key={`dif${idx}`} placeholder={`Numero ${idx + 1}`} value={`${shareholder.numEnd - shareholder.numBeg}`} readOnly/>
					            						</td>
					            					</tr>
						            				</tbody>
						            			</table>
						                    </td>
						            	</tr>
					            	</tbody>
					            </table>
					            <br />
					          </div>
				        ))}
					</td>
					<td>
					{this.state.shareholders2.map((shareholder, idx) => (
					          <div key={`d${idx}`} className="shareholder">
					            
					            {/*<button type="button" key={`b${idx}`} onClick={this.handleRemoveShareholder(idx)} className="small">-</button>*/}
					            <table>
					            	<tbody>
					            		<tr>
					            			<th>
					            				Product
					            			</th>
					            			<th>
					            				Soles
					            			</th>
					            			<th>
					            				Venta
					            			</th>
					            		</tr>
					            		<tr>
						            		<td>
						            			<input type="text" key={`prod${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.name} readOnly/>
						            		</td>
						            		<td>
						            			<input type="text" key={`price${idx}`} placeholder={`Numero ${idx + 1}`} value={`${(shareholder.numEnd - shareholder.numBeg) * shareholder.price}`} readOnly/>
						            		</td>
						            		<td>
						            			<table>
						            				<tbody>
						            					<tr>
						            						<td>
						            							<input type="text" key={`end${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.numEnd} onChange={this.handleNumEndChange2(idx)}/>
					            							</td>
						            					</tr>
						            					<tr>
						            						<td>
						            							<input type="text" key={`beg${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.numBeg} onChange={this.handleNumBegChange1(idx)}/>
						            						</td>
						            					</tr>
						            					<tr>
					            						<td>
					            							<input type="text" key={`dif${idx}`} placeholder={`Numero ${idx + 1}`} value={`${shareholder.numEnd - shareholder.numBeg}`} readOnly/>
					            						</td>
					            					</tr>
						            				</tbody>
						            			</table>
						                    </td>
						            	</tr>
					            	</tbody>
					            </table>
					            <br />
					          </div>
				        ))}
					</td>
				</tr>
			</tbody>
        </table>
        
        <button type="button" key={"addShareholder"} onClick={this.handleAddShareholder} className="small">Add Shareholder</button>
        <button>Incorporate</button>
      </form>
    )
  }
}

let target = document.getElementById('story-app');

ReactDOM.render(<IncorporationForm />, target);