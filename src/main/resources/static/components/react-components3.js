
class IncorporationForm extends React.Component {
  constructor() {
    super();
    this.state = {
      name: '',
      pumpAttendantNames: '',
      date: '',
      shift: '',
      totalRevenue: '',
      totalCash: '', 
      shareholders1: [],
      shareholders2: [],
      expensesAndCredits: [],
      totalExpensesAndCredits: ''
    };
  }
  
  _initExpensesAndCredits() {
	  var temp = [];
	  var i = 0;
	  while (i < 7) {
		  temp.push({ item: '', amt: ''});
		  i++;
	  };
	  
	  this.setState({expensesAndCredits: this.state.expensesAndCredits.concat(temp)});
  }
  
  handlePumpAttendantNamesChange = (evt) => {
    this.setState({ pumpAttendantNames: evt.target.value });
  }
  
  handleTotalCashChange = (evt) => {
	  this.setState({ totalCash: evt.target.value == '' ? '': (Math.floor(evt.target.value * 100) / 100) }); 
  }
  
  handleItemChange = (idx) => (evt) => {
	  const newExpenseOrCredits = this.state.expensesAndCredits.map((expenseOrCredit, sidx) => {
	      if (idx !== sidx) {
	    	  return expenseOrCredit;
	      }
	      
	      return { ...expenseOrCredit, item: evt.target.value };
	    });
	    
	    this.setState({ expensesAndCredits: newExpenseOrCredits });
  }
  
  handleItemAmtChange = (idx) => (evt) => {
	  const newExpenseOrCredits = this.state.expensesAndCredits.map((expenseOrCredit, sidx) => {
	      if (idx !== sidx) {
	    	  return expenseOrCredit;
	      }
	      
	      return { ...expenseOrCredit, amt: evt.target.value  == '' ? '': (Math.floor(evt.target.value * 100) / 100)};
	    });
	    
	    this.setState({ expensesAndCredits: newExpenseOrCredits });
	    
	    this._getTotalExpensesAndCredits(newExpenseOrCredits);
  }
  
  _getTotalExpensesAndCredits(expensesAndCredits) {
	  var totalExpsAndCreds = 0.0;
	  var expenseOrCredit = {};
	  for(var i = 0; i < expensesAndCredits.length; i++) {
		  expenseOrCredit = expensesAndCredits[i];
			if (!isNaN(expenseOrCredit.amt) && expenseOrCredit.amt) {
				totalExpsAndCreds = totalExpsAndCreds + Math.floor(expenseOrCredit.amt * 100) / 100 ; 
			}
	  }
	  
	  this.setState({totalExpensesAndCredits: totalExpsAndCreds.toFixed(2)});
  }
  
  handleNumEndChange1 = (idx) => (evt) => {
    const newShareholders = this.state.shareholders1.map((shareholder, sidx) => {
      if (idx !== sidx) return shareholder;
      return { ...shareholder, numEnd: evt.target.value };
    });
    
    this.setState({ shareholders1: newShareholders });
    
    this._getTotalRevenue(newShareholders, this.state.shareholders2);
  }

  handleNumEndChange2 = (idx) => (evt) => {
    const newShareholders = this.state.shareholders2.map((shareholder, sidx) => {
      if (idx !== sidx) return shareholder;
      return { ...shareholder, numEnd: evt.target.value };
    });
    
    this.setState({ shareholders2: newShareholders });
    
    this._getTotalRevenue(this.state.shareholders1, newShareholders);
  }
  
  _getTotalRevenue(shareholders1, shareholders2) {

	  var shareholders = shareholders1.concat(shareholders2);
	  var totalRev = 0.0;
	  var shareholder = {};
	  for(var i = 0; i < shareholders.length; i++) {
			shareholder = shareholders[i];
			if (!isNaN(shareholder.numEnd) && shareholder.numEnd) {
				totalRev = totalRev + Math.floor((shareholder.numEnd - shareholder.numBeg) * shareholder.price * 100) / 100 ; // add Math.floor((shareholder.numEnd - shareholder.numBeg) * shareholder.price * 100) / 100) to get cash
			}
	  }
	  
	  this.setState({totalRevenue: totalRev.toFixed(2)});
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
		url:"/api/submitDayData",
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
  
  handleAddItem = () => {
	    this.setState({ expensesAndCredits: this.state.expensesAndCredits.concat([{ item: '', amt: ''}]) });
  }
  
  handleRemoveItem = (idx) => () => {
	    
	  const newExpensesOrCredits = this.state.expensesAndCredits.filter((s, sidx) => idx !== sidx);
	  this.setState({ expensesAndCredits:  newExpensesOrCredits});
	  
	  this._getTotalExpensesAndCredits(newExpensesOrCredits);
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
								numBeg: galsWithZeros,
								numEnd: '',
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
  
  componentWillMount(){
	  this._fetchData();
	  this._initExpensesAndCredits();
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
					      						
					      					          <div  className="shareholder">
					      					            
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
					      					            		{this.state.shareholders1.map((shareholder, idx) => (
					      					            		<tr key={`d1${idx}`}>
					      						            		<td>
					      						            			<input  style={{border: 'none', width: '40px'}} type="text" key={`prod${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.name} readOnly/>
					      						            		</td>
					      						            		<td>
					      						            			<input style={{border: 'none', width: '80px'}} type="text" key={`price${idx}`} placeholder={`Numero ${idx + 1}`} value={`${(Math.floor((shareholder.numEnd - shareholder.numBeg) * shareholder.price * 100) / 100).toFixed(2)}`} readOnly/>
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
					      						            							<input style={{border: 'none', width: '80px', textAlign: 'right'}} type="text" key={`beg${idx}`} placeholder={`Numero ${idx + 1}`} value={shareholder.numBeg} readOnly/>
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
		      
		      <div className="col-md-3">
		          <div className="portlet box purple">
		              <div className="portlet-title">
		                  <div className="caption">
		                      <i className="fa fa-gift"></i>Grupo 2 de Maquinas</div>
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
			  					            				Soles
			  					            			</th>
			  					            			<th>
			  					            				Venta
			  					            			</th>
			  					            		</tr>
			  					            		{this.state.shareholders2.map((shareholder, idx) => (
				  					            		<tr key={`d2${idx}`}>
				  						            		<td>
				  						            			<input style={{border: 'none', width: '40px'}} type="text" key={`prod${idx}`} value={shareholder.name} readOnly/>
				  						            		</td>
				  						            		<td>
				  						            			<input style={{border: 'none', width: '80px'}} type="text" key={`price${idx}`} value={`${(Math.floor((shareholder.numEnd - shareholder.numBeg) * shareholder.price * 100) / 100).toFixed(2)}`} readOnly/>
				  						            		</td>
				  						            		<td>
				  						            			<table>
				  						            				<tbody>
				  						            					<tr>
				  						            						<td>
				  						            							<MaskedInput style={{size:10, width:'80px', textAlign: 'right'}} mask={shareholder.pattern} key={`end${idx}`} placeholder={`Num ${shareholder.name}`} value={''} onChange={this.handleNumEndChange2(idx)}/>
				  					            							</td>
				  						            					</tr>
				  						            					<tr>
				  						            						<td>
				  						            							<input style={{border: 'none', width: '80px', textAlign: 'right'}} type="text" key={`beg${idx}`}  value={shareholder.numBeg} readOnly/>
				  						            						</td>
				  						            					</tr>
				  						            					<tr>
				  					            						<td>
				  					            							<input style={{borderBottomWidth: '4px', width: '80px', textAlign: 'right'}} type="text" key={`dif${idx}`} placeholder={`Gals Vendidos ${idx + 1}`} value={`${(shareholder.numEnd - shareholder.numBeg).toFixed(2)}`} readOnly/>
				  					            						</td>
				  					            					</tr>
				  						            				</tbody>
				  						            			</table>
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
		          <div className="portlet box green">
		              <div className="portlet-title">
		                  <div className="caption">
		                      <i className="fa fa-gift"></i>Resultados</div>
		              </div>
		              
		              <div className="portlet-body form">
		              	<div className="portlet-body">
			              	<div className="table-responsive">
			              	
			              	
			              	  <table className="table table-hover table-light">
				      	        <tbody>
						      	    <tr>
			      	        			<td>
			      	        				<label className="control-label" key="revenueLabel">Venta Total:</label>&nbsp;&nbsp;
			      	        				<input style={{width: '80px', textAlign: 'right'}} key="totalRevenue" type="text" value={this.state.totalRevenue} readOnly/>
			      	        			</td>
			      	        		</tr>
				      	        	<tr>
			      	        			<td>
			      	        				<label className="control-label" key="cashLabel">Effectivo:</label>&nbsp;&nbsp;
			      	        				{/*<MaskedInput style={{width:'80px', textAlign: 'right'}} mask="11111.11" key="totalCash" placeholder={`Effectivo`} value={''} onChange={this.handleTotalCashChange}/>*/}
			      	        				<input type="number" style={{width:'80px', textAlign: 'right'}} pattern="[0-9]*" inputMode="numeric" placeholder={`Effectivo`} value={this.state.totalCash} onChange={this.handleTotalCashChange}/>
			      	        			</td>
			      	        		</tr>
			      	        		<tr>
		      	        				<td>
			      	        				<label className="control-label" key="gastosOrCreditsLabel">Total Gastos/Créditos:</label>&nbsp;&nbsp;
			      	        				<input style={{width: '80px', textAlign: 'right'}} key="gastosOrCredits" type="text" value={this.state.totalExpensesAndCredits} readOnly/>
			      	        			</td>
			      	        		</tr>
			      	        		
			      	        		<tr>
		      	        				<td>
			      	        				<label className="control-label" key="excessOrMissingLabel">Falta/Sobra:</label>&nbsp;&nbsp;
			      	        				<input style={{width: '80px', textAlign: 'right'}} key="excessOrMissing" type="text" value={`${(Math.floor((this.state.totalRevenue - this.state.totalCash - this.state.totalExpensesAndCredits) * 100) / 100).toFixed(2)}`} readOnly/>
			      	        			</td>
			      	        		</tr>
			      	        		
			      	        		<tr>
				      					<td>
			  					            <table className="table table-bordered">
			  					            	<tbody>
			  					            		<tr>
			  					            			<th>
			  					            				Item
			  					            			</th>
			  					            			<th>
			  					            				Monto
			  					            			</th>
			  					            			<th>
			  					            				
			  					            			</th>
			  					            		</tr>
			  					            		{this.state.expensesAndCredits.map((expenseOrCredit, idx) => (	
			  					            		<tr key={`trItem${idx}`}>
			  						            		<td>
			  						            			<input style={{width: '160px'}} type="text" key={`item${idx}`} placeholder={`Crédito o gasto`} value={expenseOrCredit.item}  onChange={this.handleItemChange(idx)}/>
		  						            			</td>
			  						            		<td>
			  						            			<input type="number" style={{size:10, width:'80px', textAlign: 'right'}} pattern="[0-9]*"  key={`amt${idx}`} placeholder={`Monto`} inputMode="numeric" value={expenseOrCredit.amt}  onChange={this.handleItemAmtChange(idx)}/>
			  						            			{/* <MaskedInput style={{size:10, width:'80px', textAlign: 'right'}} mask="1111.11" key={`amt${idx}`} placeholder={`Monto`} value={`${expenseOrCredit.amt}`} onChange={this.handleItemAmtChange(idx)}/>*/}
			  						            		</td>
			  						            		<td>
			  						            			
			  						            			<a key={`del${idx}`} onClick={this.handleRemoveItem(idx)} className="btn btn-outline btn-circle dark btn-sm black">
			  						            				<i className="fa fa-trash-o"></i>
			  						            			</a>
			  						            		</td>
			  						            	</tr>
			  					            		))}
			  					            	</tbody>
			  					            </table>
					      				</td>
					      			</tr>
			      	        		
					      		</tbody>
				              </table>
				              
				              <a key={"addItem"} onClick={this.handleAddItem} className="btn btn-outline btn-circle btn-sm purple">
				              	<i className="fa fa-plus"></i> Añadir ítem 
                              </a>
                              
			                </div>
		                </div>
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
		</div>
      </form>
    )
  }
}

let target = document.getElementById('story-app');

ReactDOM.render(<IncorporationForm />, target);