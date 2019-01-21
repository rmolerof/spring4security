
class IncorporationForm extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: {},
	  showError: false,
	  showSuccess: false,
      name: '',
      pumpAttendantNames: '',
      date: '',
      shiftDate: '',
      shift: '',
      totalRevenue: '',
      totalCash: '', 
      shareholders1: [],
      shareholders2: [],
      expensesAndCredits: [],
      totalExpensesAndCredits: '',
      tanks: [],
      gasPrices: [],
      saveOrUpdate: 'save',
      totalCredits: '',
      totalDeposits: '',
      totalVisas: '',
      totalExpenses: '',
      inputStyle: {color: 'black'},
      excessOrMissingStyle: {width: '80px', textAlign: 'right', color: 'blue'},
      fetchBackDataCount: 0
    };
  }
  
  _toggleError = () => {
	  this.setState((prevState, props) => {
		  return {showError: !prevState.showError}
	  })
  };
  
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
  
  handleShiftDateChange = evt => {
	  this.setState({shiftDate: evt.target.value.trim()});
	  
	  if (this._isValidDate(evt.target.value.trim())) {
		  this.setState({inputStyle: {color: 'black'}});
	  } else {
		  this.setState({inputStyle: {color: 'red'}});
	  }
  }
  
  _isValidDate(dateString)
  {
      // First check for the pattern
      if(!/^\d{1,2}\/\d{1,2}\/\d{4}$/.test(dateString))
          return false;

      // Parse the date parts to integers
      var parts = dateString.split("/");
      var day = parseInt(parts[0], 10);
      var month = parseInt(parts[1], 10);
      var year = parseInt(parts[2], 10);

      // Check the ranges of month and year
      if(year < 1000 || year > 3000 || month == 0 || month > 12)
          return false;

      var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

      // Adjust for leap years
      if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
          monthLength[1] = 29;

      // Check the range of the day
      return day > 0 && day <= monthLength[month - 1];
  };
  
  handleTotalCashChange = (evt) => {
	  this.setState({ totalCash: evt.target.value == '' ? '': ((evt.target.value * 100).toFixed() / 100) }); 
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
	      
	      return { ...expenseOrCredit, amt: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100)};
	    });
	    
	    this.setState({ expensesAndCredits: newExpenseOrCredits });
	    
	    this._getTotalExpensesAndCredits(newExpenseOrCredits);
  }
  
  _getTotalExpensesAndCredits(expensesAndCredits) {
	  var totalExpsAndCreds = 0.0;
	  var totalExpenses = 0.0;
	  var totalVisas = 0.0;
	  var totalDeposits = 0.0;
	  var totalCredits = 0.0;
	  var expenseOrCredit = {};
	  for(var i = 0; i < expensesAndCredits.length; i++) {
		  expenseOrCredit = expensesAndCredits[i];
			if (!isNaN(expenseOrCredit.amt) && expenseOrCredit.amt) {
				//totalExpsAndCreds = totalExpsAndCreds + Math.floor(expenseOrCredit.amt * 100) / 100 ;
				totalExpsAndCreds = totalExpsAndCreds + expenseOrCredit.amt;
			}
			
			if(!isNaN(expenseOrCredit.amt) && expenseOrCredit.amt && (expenseOrCredit.item.toLowerCase().includes("visa"))) {
				totalVisas = totalVisas + expenseOrCredit.amt
			}
			
			if(!isNaN(expenseOrCredit.amt) && expenseOrCredit.amt && (expenseOrCredit.item.toLowerCase().includes("deposito") || expenseOrCredit.item.toLowerCase().includes("depósito"))) {
				totalDeposits = totalDeposits + expenseOrCredit.amt
			}
			
			if(!isNaN(expenseOrCredit.amt) && expenseOrCredit.amt && (expenseOrCredit.item.toLowerCase().includes("credito") || expenseOrCredit.item.toLowerCase().includes("crédito"))) {
				totalCredits = totalCredits + expenseOrCredit.amt
			}
	  }
	  
	  totalExpenses = totalExpsAndCreds - totalVisas - totalDeposits - totalCredits;
	  var exessOrMissing = this.state.totalRevenue - this.state.totalCash - totalExpsAndCreds;
	  this.setState({totalExpensesAndCredits: totalExpsAndCreds.toFixed(2)});
	  this.setState({totalVisas: totalVisas.toFixed(2)});
	  this.setState({totalDeposits: totalDeposits.toFixed(2)});
	  this.setState({totalCredits: totalCredits.toFixed(2)});
	  this.setState({totalExpenses: totalExpenses.toFixed(2)});
	  
	  if (exessOrMissing && exessOrMissing.toFixed(2) <= 0) {
		  this.setState({excessOrMissingStyle: {width: '80px', textAlign: 'right', color: 'blue'}});
	  } else {
		  this.setState({excessOrMissingStyle: {width: '80px', textAlign: 'right', color: 'red'}});
	  }
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
				totalRev = totalRev + Math.floor((shareholder.numEnd - shareholder.numBeg) * shareholder.price * 100) / 100 ;
			}
	  }
	  
	  this.setState({totalRevenue: totalRev.toFixed(2)});
  }

  handleSubmit = (evt) => {
	
	evt.preventDefault();
    
	const { pumpAttendantNames, date, shift, shiftDate, shareholders1, shareholders2, totalCash, expensesAndCredits, saveOrUpdate} = this.state;
    var self = this;
    var errors = {
    		numEnd: '',
    		totalCash: '', 
    		expenseOrCredit: '',
    		submit: '',
    		pumpAttendantNames: ''
    };
    var formIsValid = true;
    this.setState({ showError: false, showSuccess: false });
    //alert(`Incorporated: ${name} with ${shareholders1.length} shareholders1 and ${shareholders2.length} shareholders2`);
    
    var shareholders = shareholders1.concat(shareholders2);
    var dayDataVO = {
    	pumpAttendantNames: pumpAttendantNames,
    	date: date,
    	shift: shift,
    	shiftDate: shiftDate,
    	dayData: {},
    	totalCash: totalCash,
    	expensesAndCredits: expensesAndCredits,
    	saveOrUpdate: saveOrUpdate
    };

    // Validation: Pump Attendant Names
    if (pumpAttendantNames && pumpAttendantNames.trim().length >= 0) {
    
    } else {
    	errors["pumpAttendantNames"] = "Falta nombre(s) de grifer@(s)";
		formIsValid = false;
    }
    
    // Validation shift date
    if (shiftDate && shiftDate.trim().length >= 0 && this.state.inputStyle.color == 'black') {
    
    } else {
    	errors["submit"] = "Falta o corregir fecha";
		formIsValid = false;
    }
    
    var shareholder = {};
	for(var i = 0; i < shareholders.length; i++) {
		shareholder = shareholders[i];
		if (!isNaN(shareholder.numEnd) && shareholder.numEnd) {
			dayDataVO.dayData[shareholder.name] = shareholder.numEnd;
		} else {
			// Validation Group 1 and 2
			errors["numEnd"] = "número de máquina " + shareholder.name + " esta incompleto";
			formIsValid = false;
		}
	}
	
	// Validation: total cash
	if (!isNaN(totalCash) && totalCash) {
		
	} else {
		errors["totalCash"] = "Efectivo está incompleto";
		formIsValid = false;
	}
	
	// Validation: item and amount
	var expenseOrCredit = {};
	var expensesAndCreditsCleaned = [];
	for(var i = 0; i < expensesAndCredits.length; i++) {
		expenseOrCredit = expensesAndCredits[i];
		if (!isNaN(expenseOrCredit.amt) && expenseOrCredit.amt) {
			if (expenseOrCredit.item && expenseOrCredit.item.trim().length >= 0) {
				expensesAndCreditsCleaned.push(expenseOrCredit);
			} else {
				errors["expenseOrCredit"] = "Gasto o credito " + expenseOrCredit.item + " - " + expenseOrCredit.amt + " esta incompleto";
				formIsValid = false;
			}
		} else {
			if (expenseOrCredit.item && expenseOrCredit.item.trim().length >= 0) {
				errors["expenseOrCredit"] = "Gasto o credito " + expenseOrCredit.item + " - " + expenseOrCredit.amt + " esta incompleto";
				formIsValid = false;
			} else {
				
			}
		}
	}
	dayDataVO.expensesAndCredits = expensesAndCreditsCleaned;
	
	
	this.setState({errors: errors}); 
	
	if (formIsValid) {
		
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
  
  handleAddItem = () => {
	    this.setState({ expensesAndCredits: this.state.expensesAndCredits.concat([{ item: '', amt: ''}]) });
  }
  
  handleRemoveItem = (idx) => () => {
	    
	  const newExpensesOrCredits = this.state.expensesAndCredits.filter((s, sidx) => idx !== sidx);
	  this.setState({ expensesAndCredits:  newExpensesOrCredits});
	  
	  this._getTotalExpensesAndCredits(newExpensesOrCredits);
  }
  
  loadPrevious = () => {
	  this.setState({saveOrUpdate:  'update'});
	  this._fetchData({dateEnd: "latest", dateBeg: "previous", backDataCount: this.state.fetchBackDataCount});
	  this.setState({fetchBackDataCount: this.state.fetchBackDataCount + 1});
  }
  
  _fetchData(timeframe){
			
		var search = {};
		search["dateEnd"] = timeframe.dateEnd;
		search["dateBeg"] = timeframe.dateBeg;
		search["backDataCount"] = timeframe.backDataCount;
		
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
				
				if (data.result.length == 1) {
					var station = data.result[0];
					var currentDate = new Date();
					if (station.totalCash != 0) {
						var currentShift = station.shift == "1" ? "2": "1";
					} else {
						var currentShift = station.shift;
					}
					
					// Iterate through Dispensers
					var shareholdersResponse1 = [];
					var shareholdersResponse2 = [];
					var prices = {};
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
							prices[dispenser.name] = {"price": dispenser.price, "cost": dispenser.cost, "name": dispenser.name};
							if (i < 6) {
								shareholdersResponse1.push(shareholder);
							} else {
								shareholdersResponse2.push(shareholder);
							}
							i++;
						} 
					}
					
					var gasPrices = [];
					for(var property in prices) {
						gasPrices.push({"price": prices[property].price, "cost": prices[property].cost, "name": prices[property].name});
					}
					var tanks = [];
					for(var property in station.tanks) {
						tanks.push(station.tanks[property]);
					}
					this.setState({shareholders1: this.state.shareholders1.concat(shareholdersResponse1)});
					this.setState({shareholders2: this.state.shareholders2.concat(shareholdersResponse2)});
					this.setState({name: station.name});
					this.setState({date: currentDate});
					this.setState({shiftDate: ''});
					this.setState({shift: currentShift});
					this.setState({tanks: tanks});
					this.setState({gasPrices: gasPrices});
					
				} else {
					var stationLatest = data.result[0];
					var stationPrevious = data.result[1];
					
					// Iterate through Dispensers
					var shareholdersResponse1 = [];
					var shareholdersResponse2 = [];
					var prices = {};
					var i = 0;
					
					for(var property in stationLatest.dispensers){
						if (stationLatest.dispensers.hasOwnProperty(property)) {
							var dispenserLatest = stationLatest.dispensers[property];
							var dispenserPrevious = stationPrevious.dispensers[property]; 
							
							var galsWithZerosLatest = dispenserLatest.gallons.toFixed(2) + "";
							while (galsWithZerosLatest.length < dispenserLatest.pattern) galsWithZerosLatest = "0" + galsWithZerosLatest;
							
							var galsWithZerosPrevious = dispenserPrevious.gallons.toFixed(2) + "";
							while (galsWithZerosPrevious.length < dispenserPrevious.pattern) galsWithZerosPrevious = "0" + galsWithZerosPrevious;
							
							const shareholder = {
									name: dispenserLatest.name + "_" + dispenserLatest.id,
									price: dispenserLatest.price,
									numBeg: galsWithZerosPrevious,
									numEnd: galsWithZerosLatest,
									pattern: dispenserLatest.pattern == 8 ?  "11111.11": "111111.11"};
							prices[dispenserLatest.name] = {"price": dispenserLatest.price, "cost": dispenserLatest.cost, "name": dispenserLatest.name};
							if (i < 6) {
								shareholdersResponse1.push(shareholder);
							} else {
								shareholdersResponse2.push(shareholder);
							}
							i++;
						} 
					}
					
					var gasPrices = [];
					for(var property in prices) {
						gasPrices.push({"price": prices[property].price, "cost": prices[property].cost, "name": prices[property].name});
					}
					var tanks = [];
					for(var property in stationLatest.tanks) {
						tanks.push(stationLatest.tanks[property]);
					}
					this.setState({shareholders1: []});
					this.setState({shareholders2: []});
					this.setState({shareholders1: this.state.shareholders1.concat(shareholdersResponse1)});
					this.setState({shareholders2: this.state.shareholders2.concat(shareholdersResponse2)});
					this.setState({name: stationLatest.name});
					this.setState({pumpAttendantNames: stationLatest.pumpAttendantNames});
					this.setState({date: stationLatest.date});
					this.setState({shiftDate: stationLatest.shiftDate});
					this.setState({shift: stationLatest.shift});
					this.setState({tanks: tanks});
					this.setState({gasPrices: gasPrices});
					this.setState({expensesAndCredits: stationLatest.expensesAndCredits});
					this.setState({totalCash: stationLatest.totalCash});
					this.setState({totalRevenue: stationLatest.totalDay.totalSolesRevenueDay});
					
					// Total Expenses, visas, deposits, credits
					var totalExpsAndCreds = 0.0;
					var totalExpenses = 0.0;
					var totalVisas = 0.0;
					var totalDeposits = 0.0;
					var totalCredits = 0.0;
					var expenseOrCredit = {};
					for(var i = 0; i < stationLatest.expensesAndCredits.length; i++) {
						  expenseOrCredit = stationLatest.expensesAndCredits[i];
							if (!isNaN(expenseOrCredit.amt) && expenseOrCredit.amt) {
								//totalExpsAndCreds = totalExpsAndCreds + Math.floor(expenseOrCredit.amt * 100) / 100 ;
								totalExpsAndCreds = totalExpsAndCreds + expenseOrCredit.amt;
							}
							
							if(!isNaN(expenseOrCredit.amt) && expenseOrCredit.amt && (expenseOrCredit.item.toLowerCase().includes("visa"))) {
								totalVisas = totalVisas + expenseOrCredit.amt
							}
							
							if(!isNaN(expenseOrCredit.amt) && expenseOrCredit.amt && (expenseOrCredit.item.toLowerCase().includes("deposito") || expenseOrCredit.item.toLowerCase().includes("depósito"))) {
								totalDeposits = totalDeposits + expenseOrCredit.amt
							}
							
							if(!isNaN(expenseOrCredit.amt) && expenseOrCredit.amt && (expenseOrCredit.item.toLowerCase().includes("credito") || expenseOrCredit.item.toLowerCase().includes("crédito"))) {
								totalCredits = totalCredits + expenseOrCredit.amt
							}
					}
					  
					totalExpenses = totalExpsAndCreds - totalVisas - totalDeposits - totalCredits;
					var exessOrMissing = stationLatest.totalDay.totalSolesRevenueDay - stationLatest.totalCash - totalExpsAndCreds;
					this.setState({totalExpensesAndCredits: totalExpsAndCreds.toFixed(2)});
					this.setState({totalVisas: totalVisas.toFixed(2)});
					this.setState({totalDeposits: totalDeposits.toFixed(2)});
					this.setState({totalCredits: totalCredits.toFixed(2)});
					this.setState({totalExpenses: totalExpenses.toFixed(2)});
					
					if (exessOrMissing && exessOrMissing.toFixed(2) <= 0) {
						  this.setState({excessOrMissingStyle: {width: '80px', textAlign: 'right', color: 'blue'}});
					  } else {
						  this.setState({excessOrMissingStyle: {width: '80px', textAlign: 'right', color: 'red'}});
					  }
					
				}
				
				/*var station = data.result[0];
				var currentDate = new Date();
				if (station.totalCash != 0) {
					var currentShift = station.shift == "1" ? "2": "1";
				} else {
					var currentShift = station.shift;
				}
				
				// Iterate through Dispensers
				var shareholdersResponse1 = [];
				var shareholdersResponse2 = [];
				var prices = {};
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
						prices[dispenser.name] = {"price": dispenser.price, "cost": dispenser.cost, "name": dispenser.name};
						if (i < 6) {
							shareholdersResponse1.push(shareholder);
						} else {
							shareholdersResponse2.push(shareholder);
						}
						i++;
					} 
				}
				
				var gasPrices = [];
				for(var property in prices) {
					gasPrices.push({"price": prices[property].price, "cost": prices[property].cost, "name": prices[property].name});
				}
				var tanks = [];
				for(var property in station.tanks) {
					tanks.push(station.tanks[property]);
				}
				this.setState({shareholders1: this.state.shareholders1.concat(shareholdersResponse1)});
				this.setState({shareholders2: this.state.shareholders2.concat(shareholdersResponse2)});
				this.setState({name: station.name});
				this.setState({date: currentDate});
				this.setState({shift: currentShift});
				this.setState({tanks: tanks});
				this.setState({gasPrices: gasPrices});*/
			},
			error: function(e){
				var json = "<h4>Ajax Response</h4><pre>" + e.responseText + "</pre>";
				console.log("ERROR: ", e);
			}	
		});
	}
  
  componentWillMount(){
	  this._fetchData({dateEnd: "latest", dateBeg: ""});
	  this._initExpensesAndCredits();
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
	      		<strong>¡Error!</strong>{" " + this.state.errors.pumpAttendantNames + " - " + this.state.errors.numEnd + " - " + this.state.errors.totalCash + " - " + this.state.errors.expenseOrCredit + " - " + this.state.errors.submit}  
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
	                        <span className="caption-subject font-green bold uppercase">Grifo {this.state.name}</span>
	                    </div>
	                </div>
				    <div className="form-body">
				      <div className="row">
				          <div className="col-md-3">
				              <div className="form-group">
				                  <label className="control-label">Nombres de Grifero(s)</label>
				                  <input type="text" className="form-control" placeholder="Nombre1, Nombre2, ..." onKeyPress={this.onKeyPress} value={this.state.pumpAttendantNames} onChange={this.handlePumpAttendantNamesChange}/>
				              </div>
				          </div>
				          <div className="col-md-2">
				              <div className="form-group">
				                  <label className="control-label">Marca Horaria</label>
				                  <input type="text" id="date" className="form-control" placeholder="Fecha" value={`${moment(this.state.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`} readOnly/>
				              </div>
				          </div>
				          <div className="col-md-1">
				              <div className="form-group">
				                  <label className="control-label">Fecha Turno</label>
				                  <input type="text" id="shiftDate" style={this.state.inputStyle} className="form-control" placeholder="Fecha de Turno" value={this.state.shiftDate} onChange={this.handleShiftDateChange}/>
				              </div>
				          </div>
				          <div className="col-md-1">
				              <div className="form-group">
				                  <label className="control-label">Turno</label>
				                  <input type="text" id="shift" className="form-control" placeholder="Turno" value={this.state.shift} readOnly/>
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
					  	<div className="col-md-3" >
				              <div className="form-group">
				              <table className="table table-hover table-light">
				            	<tbody>
			          			<tr>
			          				{this.state.tanks.map((tank, idx) => (
				            			<td key={`col${idx}`}>
				            				<label className="control-label" key={`name${idx}`}>Inicio {tank.fuelType} (gal)</label>
				            				<input type="text" className="form-control"  key={`cost${idx}`} onKeyPress={this.onKeyPress} value={tank.gals.toFixed(2)} readOnly/>
			          				</td>
			          				))}
				                    </tr>
			                  </tbody>
				              </table>
				              </div>
				        </div>
				      
			  		  	<div className="col-md-3" >
				              <div className="form-group">
				              <table className="table table-hover table-light">
				            	<tbody>
			            			<tr>
			            				{this.state.gasPrices.map((gasPrice, idx) => (
				            			<td key={`col${idx}`}>
				            				<label className="control-label" key={`name${idx}`}>Precio {gasPrice.name}</label>
				            				<input type="text" className="form-control"  key={`cost${idx}`} onKeyPress={this.onKeyPress} value={`S/. ` + gasPrice.price.toFixed(2)} readOnly/>
			            				</td>
			            				))}
				                    </tr>
			                    </tbody>
				              </table>
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
								      						            			<input style={{border: 'none', width: '80px'}} type="text" key={`price${idx}`} value={`${(((shareholder.numEnd - shareholder.numBeg) * shareholder.price * 100).toFixed() / 100)}`} readOnly/>
								      						            		</td>
								      						            		<td>
								      						            			<table>
								      						            				<tbody>
								      						            					<tr>
								      						            						<td>
								      						            							<MaskedInput style={{size:10, width:'80px', textAlign: 'right'}} mask={shareholder.pattern} key={`end${idx}`} placeholder={`Num ${shareholder.name}`} value={`${shareholder.numEnd}`} onChange={this.handleNumEndChange1(idx)}/>
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
							  						            			<input style={{border: 'none', width: '80px'}} type="text" key={`price${idx}`} value={`${(((shareholder.numEnd - shareholder.numBeg) * shareholder.price * 100).toFixed() / 100)}`} readOnly/>
							  						            		</td>
							  						            		<td>
							  						            			<table>
							  						            				<tbody>
							  						            					<tr>
							  						            						<td>
							  						            							<MaskedInput style={{size:10, width:'80px', textAlign: 'right'}} mask={shareholder.pattern} key={`end${idx}`} placeholder={`Num ${shareholder.name}`} value={`${shareholder.numEnd}`} onChange={this.handleNumEndChange2(idx)}/>
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
					      
					      <div className="col-md-4">
					          <div className="portlet box green">
					              <div className="portlet-title">
					                  <div className="caption">
					                      <i className="fa fa-gift"></i>Resultados</div>
					              </div>
					              
					              <div className="portlet-body form">
					              	<div className="portlet-body">
						              	<div className="table-responsive">
						              	
						              	
						              	  <table className="table table-light">
							      	        <tbody>
									      	    <tr>
						      	        			<td>
							      	        			<table className="table table-hover table-borderless">
						  					            	<tbody>
						  					            		<tr>
						  					            			<td>
						  					            				<label className="control-label" key="revenueLabel">Venta Total:</label>&nbsp;&nbsp;
						  					            			</td>
						  					            			<td>
						  					            				<input style={{width: '80px', textAlign: 'right'}} key="totalRevenue" type="text" value={this.state.totalRevenue} readOnly/>
						  					            			</td>
						  					            		</tr>
						  					            		<tr>
										      	        			<td>
										      	        				<label className="control-label" key="cashLabel">Effectivo:</label>&nbsp;&nbsp;
									      	        				</td>
									      	        				<td>
										      	        				<input type="number" style={{width:'80px', textAlign: 'right'}} pattern="[0-9]*" onKeyPress={this.onKeyPress} inputMode="numeric" placeholder={`Effectivo`} value={this.state.totalCash} onChange={this.handleTotalCashChange}/>
										      	        			</td>
										      	        		</tr>
										      	        		<tr>
									      	        				<td>
										      	        				<label className="control-label" key="gastosOrCreditsLabel">VDCG:</label>&nbsp;&nbsp;
									      	        				</td>
									      	        				<td>
										      	        				<input style={{width: '80px', textAlign: 'right'}} key="gastosOrCredits" type="text" value={this.state.totalExpensesAndCredits} readOnly/>
										      	        			</td>
										      	        		</tr>
										      	        		<tr>
									      	        				<td>
									      	        					&nbsp;&nbsp;&nbsp;&nbsp;<label className="control-label" key="totalVisasLabel">Solo Visas: S/.</label>&nbsp;&nbsp;
									      	        				</td>
									      	        				<td>
										      	        				<input style={{width: '80px', textAlign: 'right'}} key="totalVisas" type="text" value={this.state.totalVisas} readOnly/>
										      	        			</td>
										      	        		</tr>
										      	        		<tr>
									      	        				<td>
									      	        					&nbsp;&nbsp;&nbsp;&nbsp;<label className="control-label" key="totalDepositsLabel">Solo Depósitos: S/.</label>&nbsp;&nbsp;
									      	        				</td>
									      	        				<td>
										      	        				<input style={{width: '80px', textAlign: 'right'}} key="totalDeposits" type="text" value={this.state.totalDeposits} readOnly/>
										      	        			</td>
										      	        		</tr>
										      	        		<tr>
									      	        				<td>
									      	        					&nbsp;&nbsp;&nbsp;&nbsp;<label className="control-label" key="totalCreditsLabel">Solo Créditos: S/.</label>&nbsp;&nbsp;
									      	        				</td>
									      	        				<td>
										      	        				<input style={{width: '80px', textAlign: 'right'}} key="totalCredits" type="text" value={this.state.totalCredits} readOnly/>
										      	        			</td>
										      	        		</tr>
										      	        		<tr>
									      	        				<td>
									      	        					&nbsp;&nbsp;&nbsp;&nbsp;<label className="control-label" key="totalExpensesLabel">Solo Gastos: S/.</label>&nbsp;&nbsp;
									      	        				</td>
									      	        				<td>
										      	        				<input style={{width: '80px', textAlign: 'right'}} key="totalExpenses" type="text" value={this.state.totalExpenses} readOnly/>
										      	        			</td>
										      	        		</tr>
										      	        		<tr>
									      	        				<td>
										      	        				<label className="control-label" key="excessOrMissingLabel">Falta/Sobra:</label>&nbsp;&nbsp;
									      	        				</td>
									      	        				<td>
										      	        				<input style={this.state.excessOrMissingStyle} key="excessOrMissing" type="text" value={`${(((this.state.totalRevenue - this.state.totalCash - this.state.totalExpensesAndCredits) * 100).toFixed() / 100)}`} readOnly/>
										      	        			</td>
										      	        		</tr>
					  					            		</tbody>
				  					            		</table>
						      	        			</td>
						      	        		</tr>
						      	        		
						      	        		<tr>
							      					<td>
						  					            <table className="table table-hover table-bordered">
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
						  						            			<input style={{width: '160px'}} type="text" key={`item${idx}`} onKeyPress={this.onKeyPress} placeholder={`Crédito o gasto`} value={expenseOrCredit.item}  onChange={this.handleItemChange(idx)}/>
					  						            			</td>
						  						            		<td>
						  						            			<input type="number" style={{size:10, width:'80px', textAlign: 'right'}} pattern="[0-9]*" onKeyPress={this.onKeyPress} key={`amt${idx}`} placeholder={`Monto`} inputMode="numeric" value={expenseOrCredit.amt}  onChange={this.handleItemAmtChange(idx)}/>
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
				          	<i className="fa fa-check"></i> Enviar
				          </button>
					      <button type="button" className="btn default">Cancelar</button>
					  </div>
					  
					  <br></br>
					  
					  <ReactToPrint trigger={() => <button type="button" className="btn btn-default mt-ladda-btn ladda-button" data-style="expand-left" data-spinner-color="#333"><a href="#"><i className="fa fa-print"></i> Print this out</a></button>} content={() => this.componentRef}></ReactToPrint>
					  <div className="row" ref={el => (this.componentRef = el)}>
					      <div className="col-md-7">
					          <div className="portlet box red">
					              <div className="portlet-title">
					                  <div className="caption">
					                      <i className="fa fa-gift"></i>Resumen de Forma para Impresión</div>
					              </div>
					              
					              <div className="portlet-body form">
						              <div className="portlet-body">
						              
							              <table>
								              <tbody>
						            			  <tr>
											          <td>
											              <div className="form-group">
											                  <label className="control-label">Nombres de Grifero(s)</label>
											                  <input type="text" className="form-control" placeholder="Nombre1, Nombre2, ..." value={this.state.pumpAttendantNames} readOnly/>
											              </div>
											          </td>
											         {/* <td>
											              <div className="form-group">
											                  <label className="control-label">Marca Horaria</label>
											                  <input type="text" id="lastName" className="form-control" placeholder="Fecha" value={`${moment(this.state.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}  readOnly/>
											              </div>
											          </td>*/}
											          <td>
											              <div className="form-group">
											                  <label className="control-label">Fecha Turno</label>
											                  <input type="text" id="shiftDate" style={this.state.inputStyle} className="form-control" placeholder="Fecha de Turno" value={this.state.shiftDate} readOnly/>
											              </div>
											          </td>
											          <td>
											              <div className="form-group">
											                  <label className="control-label">Turno</label>
											                  <input type="text" id="lastName" className="form-control" placeholder="Turno" value={this.state.shift} readOnly/>
											              </div>
											          </td>
											      </tr>
										      
											      <tr>
												  	<td>
											              <div className="form-group">
											              <table className="table table-hover table-light">
											            	<tbody>
										          			<tr>
										          				{this.state.tanks.map((tank, idx) => (
											            			<td key={`col${idx}`}>
											            				<label className="control-label" key={`name${idx}`}>Inic {tank.fuelType.toUpperCase()} (gal)</label>
											            				<input type="text" className="form-control"  key={`cost${idx}`} onKeyPress={this.onKeyPress} value={tank.gals.toFixed(2)} readOnly/>
										          				</td>
										          				))}
											                    </tr>
										                  </tbody>
											              </table>
											              </div>
											        </td>
											      
										  		  	<td>
											              <div className="form-group">
											              <table className="table table-hover table-light">
											            	<tbody>
										            			<tr>
										            				{this.state.gasPrices.map((gasPrice, idx) => (
											            			<td key={`col${idx}`}>
											            				<label className="control-label" key={`name${idx}`}>Precio {gasPrice.name}</label>
											            				<input type="text" className="form-control"  key={`cost${idx}`} onKeyPress={this.onKeyPress} value={`S/. ` + gasPrice.price.toFixed(2)} readOnly/>
										            				</td>
										            				))}
											                    </tr>
										                    </tbody>
											              </table>
											              </div>
											        </td>
											      </tr>
										      </tbody>
									      </table>
						              		
						                  <div className="table-responsive">  
							              	  <table>
								              	<tbody>
									              	<tr>
										              	<td style={{verticalAlign: 'top'}}>
										                  	<table>
												      	        <tbody>
												      				<tr>
												      					<td>
											      					          <div  className="shareholder">
											      					            
											      					            <table className="table table-bordered">
											      					            	<tbody>
											      					            		<tr>
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
												      					            			<table>
													      					            			<tbody>
														      					            			<tr>
															      						            		<td>
															      						            			<input style={{border: 'none', width: '80px'}} type="text" key={`price${idx}`} value={`S/. ${(((shareholder.numEnd - shareholder.numBeg) * shareholder.price * 100).toFixed() / 100)}`} readOnly/>
															      						            		</td>
														      						            		</tr>
														      					            			<tr>
															      					            			<td>
															      					            				<i className="fa"></i>
															                                                    <b><span className="ladda-label">{shareholder.name.toUpperCase()}</span></b>
															      					            			</td>
														      						            		</tr>
													      						            		</tbody>
												      						            		</table>
											      						            		</td>
											      						            		<td>
											      						            			<table>
											      						            				<tbody>
											      						            					<tr>
											      						            						<td>
											      						            							<input style={{border: 'none', width: '80px', textAlign: 'right'}} type="text" key={`end${idx}`}  value={shareholder.numEnd} readOnly/>
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
											            </td>
											            
											            <td style={{verticalAlign: 'top'}}>
										                  	<table>
												      	        <tbody>
												      				<tr>
												      					<td>
											      					          <div  className="shareholder">
											      					            
											      					            <table className="table table-bordered">
											      					            	<tbody>
											      					            		<tr>
											      					            			<th>
											      					            				Soles
											      					            			</th>
											      					            			<th>
											      					            				Venta
											      					            			</th>
											      					            		</tr>
											      					            		{this.state.shareholders2.map((shareholder, idx) => (
											      					            		<tr key={`d1${idx}`}>
											      						            		<td>
												      					            			<table>
													      					            			<tbody>
														      					            			<tr>
															      						            		<td>
															      						            			<input style={{border: 'none', width: '80px'}} type="text" key={`price${idx}`} value={`S/. ${(((shareholder.numEnd - shareholder.numBeg) * shareholder.price * 100).toFixed() / 100)}`} readOnly/>
															      						            		</td>
														      						            		</tr>
														      					            			<tr>
															      					            			<td>
															      					            				<i className="fa"></i>
															                                                    <b><span className="ladda-label">{shareholder.name.toUpperCase()}</span></b>
															      					            			</td>
														      						            		</tr>
													      						            		</tbody>
												      						            		</table>
											      						            		</td>
											      						            		<td>
											      						            			<table>
											      						            				<tbody>
											      						            					<tr>
											      						            						<td>
											      						            							<input style={{border: 'none', width: '80px', textAlign: 'right'}} type="text" key={`end${idx}`}  value={shareholder.numEnd} readOnly/>
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
											            </td>
								              
											            <td>
												              <table className="table table-hover table-light">
												      	        <tbody>
														      	    <tr>
											      	        			<td>
												      	        			<table className="table table-hover table-borderless">
											  					            	<tbody>
											  					            		<tr>
											  					            			<td>
											  					            				<label className="control-label" key="revenueLabel">Venta Total: S/.</label>&nbsp;&nbsp;
											  					            			</td>
											  					            			<td>
											  					            				<input style={{width: '80px', textAlign: 'right'}} key="totalRevenue" type="text" value={this.state.totalRevenue} readOnly/>
											  					            			</td>
										  					            			</tr>
										  					            			<tr>
															      	        			<td>
															      	        				<label className="control-label" key="cashLabel">Effectivo: S/.</label>&nbsp;&nbsp;
														      	        				</td>
											  					            			<td>
															      	        				<input type="number" style={{width:'80px', textAlign: 'right'}} pattern="[0-9]*" onKeyPress={this.onKeyPress} inputMode="numeric" placeholder={`Effectivo`} value={this.state.totalCash} readOnly/>
															      	        			</td>
															      	        		</tr>
															      	        		<tr>
														      	        				<td>
															      	        				<label className="control-label" key="gastosOrCreditsLabel">VDCG: S/.</label>&nbsp;&nbsp;
														      	        				</td>
											  					            			<td>
															      	        				<input style={{width: '80px', textAlign: 'right'}} key="gastosOrCredits" type="text" value={this.state.totalExpensesAndCredits} readOnly/>
															      	        			</td>
															      	        		</tr>
															      	        		<tr>
														      	        				<td>
														      	        					&nbsp;&nbsp;&nbsp;&nbsp;<label className="control-label" key="totalVisasLabel">Solo Visas: S/.</label>&nbsp;&nbsp;
														      	        				</td>
											  					            			<td>
															      	        				<input style={{width: '80px', textAlign: 'right'}} key="totalVisas" type="text" value={this.state.totalVisas} readOnly/>
															      	        			</td>
															      	        		</tr>
															      	        		<tr>
														      	        				<td>
														      	        					&nbsp;&nbsp;&nbsp;&nbsp;<label className="control-label" key="totalDepositsLabel">Solo Depósitos: S/.</label>&nbsp;&nbsp;
														      	        				</td>
											  					            			<td>
															      	        				<input style={{width: '80px', textAlign: 'right'}} key="totalDeposits" type="text" value={this.state.totalDeposits} readOnly/>
															      	        			</td>
															      	        		</tr>
															      	        		<tr>
														      	        				<td>
														      	        					&nbsp;&nbsp;&nbsp;&nbsp;<label className="control-label" key="totalCreditsLabel">Solo Créditos: S/.</label>&nbsp;&nbsp;
														      	        				</td>
											  					            			<td>
															      	        				<input style={{width: '80px', textAlign: 'right'}} key="totalCredits" type="text" value={this.state.totalCredits} readOnly/>
															      	        			</td>
															      	        		</tr>
															      	        		<tr>
														      	        				<td>
														      	        					&nbsp;&nbsp;&nbsp;&nbsp;<label className="control-label" key="totalExpensesLabel">Solo Gastos: S/.</label>&nbsp;&nbsp;
														      	        				</td>
											  					            			<td>
															      	        				<input style={{width: '80px', textAlign: 'right'}} key="totalExpenses" type="text" value={this.state.totalExpenses} readOnly/>
															      	        			</td>
															      	        		</tr>
															      	        		<tr>
														      	        				<td>
															      	        				<label className="control-label" key="excessOrMissingLabel">Falta/Sobra: S/.</label>&nbsp;&nbsp;
														      	        				</td>
											  					            			<td>
															      	        				<input style={{width: '80px', textAlign: 'right'}} key="excessOrMissing" type="text" value={`${(((this.state.totalRevenue - this.state.totalCash - this.state.totalExpensesAndCredits) * 100).toFixed() / 100)}`} readOnly/>
															      	        			</td>
															      	        		</tr>
										  					            		</tbody>
									  					            		</table>
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
											  					            		</tr>
											  					            		{this.state.expensesAndCredits.map((expenseOrCredit, idx) => (	
											  					            		<tr key={`trItem${idx}`}>
											  						            		<td>
											  						            			<input style={{width: '160px'}} type="text" key={`item${idx}`} onKeyPress={this.onKeyPress} placeholder={`Crédito o gasto`} value={expenseOrCredit.item} readOnly/>
										  						            			</td>
											  						            		<td>
											  						            			<input type="number" style={{size:10, width:'80px', textAlign: 'right'}} pattern="[0-9]*" onKeyPress={this.onKeyPress} key={`amt${idx}`} placeholder={`Monto`} inputMode="numeric" value={expenseOrCredit.amt} readOnly/>
											  						            		</td>
											  						            	</tr>
											  					            		))}
											  					            	</tbody>
											  					            </table>
													      				</td>
													      			</tr>
											      	        		
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