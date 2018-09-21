
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
			url:"/api/getTableSummaryData",
			data: JSON.stringify(search),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (summaryData) => {
				var tanksVo = summaryData.result[0];
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
  
  tableData = [["airi"],["angelica"]];
  
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
	      
	      <Tbl data={this.tableData}></Tbl>
	      
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
				{title: "test"}
			]
		});
	}
	
	componentWillUnmount() {
		
	}
	
	render() {
		return <div>
					<div className="row">
						<div className="col-md-6">
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