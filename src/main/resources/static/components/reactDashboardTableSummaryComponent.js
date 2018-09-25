
class StockForm extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: {},
	  showError: false,
	  showSuccess: false,
      pumpAttendantNames: '',
      date: '',
      tanks: [],
      summaryData: null
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
			url:"/api/getTableSummaryData",
			data: JSON.stringify(search),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				
				var summaryData = data.result;
				var tableData = [];
				
				for (var i = 0; i < summaryData.length - 1; i++) {
					
					var expenseOrCredit = {};
					var visaCredits = 0.0;
					var expensesOnly = 0.0;
					var expensesAndCredits = 0.0;
					for(var j = 0; j < summaryData[i].expensesAndCredits.length; j++) {
						expenseOrCredit = summaryData[i].expensesAndCredits[j];
						if (expenseOrCredit.item.toLowerCase().includes("visa")) {
							visaCredits += expenseOrCredit.amt;
						}
						expensesAndCredits += expenseOrCredit.amt;
					}
					expensesOnly = expensesAndCredits - visaCredits;
					
					var row = [
						moment(summaryData[i].date).tz('America/Lima').format('DD/MM/YYYY hh:mm A'),
						summaryData[i].pumpAttendantNames,
						summaryData[i].shift,
						summaryData[i].totalDay.totalDayUnits.d2.stockGals,
						summaryData[i].totalDay.totalDayUnits.g90.stockGals,
						summaryData[i].totalDay.totalDayUnits.g95.stockGals,
						summaryData[i].totalDay.totalDayUnits.d2.totalGalsSoldDay,
						summaryData[i].totalDay.totalDayUnits.g90.totalGalsSoldDay,
						summaryData[i].totalDay.totalDayUnits.g95.totalGalsSoldDay,
						summaryData[i].totalDay.totalDayUnits.d2.totalSolesRevenueDay,
						summaryData[i].totalDay.totalDayUnits.g90.totalSolesRevenueDay,
						summaryData[i].totalDay.totalDayUnits.g95.totalSolesRevenueDay,
						summaryData[i].totalDay.totalDayUnits.d2.totalProfitDay,
						summaryData[i].totalDay.totalDayUnits.g90.totalProfitDay,
						summaryData[i].totalDay.totalDayUnits.g95.totalProfitDay,
						summaryData[i].totalDay.totalSolesRevenueDay,
						summaryData[i].totalCash,
						expensesOnly,
						visaCredits,
						expensesAndCredits,
						(summaryData[i].totalDay.totalSolesRevenueDay - summaryData[i].totalCash - expensesAndCredits).toFixed(2),
						summaryData[i].totalDay.totalProfitDay
						];
					
					tableData[i] = row;
				}
				
				
				this.setState({summaryData: tableData});
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
	          <div className="col-md-4">
	              <div className="form-group">
	                  <label className="control-label">Fecha</label>
	                  <input type="text" id="lastName" className="form-control" placeholder="Fecha" value={`${moment().tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}  readOnly/>
	              </div>
	          </div>
	      </div>
	      
	      {this.state && this.state.summaryData &&
	    	  <Tbl data={this.state.summaryData}></Tbl>
		  } 
	      
      </form>
    )
  }
}

class Tbl extends React.Component {
	componentDidMount() {
		this.$el = $(this.el);
		this.$el.DataTable({
			data: this.props.data,
			pageLength: 5,
			scrollY: 300,
			deferRender: true,
			scroller: true,
			deferRender: true,
			scrollX: true,
			scrollCollapse: true,
			
			dom: 'Brftip',
			buttons: [
				{extend: 'print', className: 'btn dark btn-outline'},
				{extend: 'pdf', className: 'btn green btn-outline'},
				{extend: 'csv', className: 'btn purple btn-outline'}
			],
			columns: [
				 	{ title: "Fecha" },
		            { title: "Autor" },
		            { title: "Trno" },
		            { title: "Stk D2" },
		            { title: "Stk G90" },
		            { title: "Stk G95" },
		            { title: "Gals D2" },
		            { title: "Gals G90" },
		            { title: "Gals G95" },
		            { title: "Vnts D2" },
		            { title: "Vnts G90" },
		            { title: "Vnts G95" },
		            { title: "Util D2" },
		            { title: "Util G90" },
		            { title: "Util G95" },
		            { title: "Ventas" },
		            { title: "Efectivo" },
		            { title: "Solo Gasto" },
		            { title: "Visas" },
		            { title: "Gsts y Crdts" },
		            { title: "Falta" },
		            { title: "Util Bruta" },
			]
		});
	}
	
	componentWillUnmount() {
		
	}
	
	render() {
		return <div>
					<div className="row">
						<div className="col-md-12">
							<div className="portlet light bordered">
								<div className="portlet-title">
									<div className="caption font-green">
										<i className="icon-settings font-green"></i>
										<span className="caption-subject bold uppercase">Grifo La Joya</span>
									</div>
									<div className="tools"></div>
								</div>
								<div className="portlet-body table-both-scroll">
									<table className="display table table-striped table-bordered table-hover order-column" ref={el => this.el = el}>
									</table>
								</div>
							</div>
						</div>
					</div>
			   </div>
	}
}

let target = document.getElementById('stock-form-page');

ReactDOM.render(<StockForm />, target);