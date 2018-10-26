
class TableDashboard extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: {},
	  showError: false,
	  showSuccess: false,
      stationSummaryData: null,
      stockSummaryData: null,
      gasPricesSummaryData: null
    };
  }
  
  _toggleError = () => {
	  this.setState((prevState, props) => {
		  return {showError: !prevState.showError}
	  })
  };
  
  _fetchStationData(timeframe){
			
	  	var search = {};
		search["dateEnd"] = timeframe.dateEnd;
		search["dateBeg"] = timeframe.dateBeg;
		
		jQuery.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/getStationSummaryData",
			data: JSON.stringify(search),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				
				var stationSummaryData = data.result;
				var tableData = [];
				
				var count = 0;
				for (var i = 0; i < stationSummaryData.length - 1; i++) {
					
					var expenseOrCredit = {};
					var visaCredits = 0.0;
					var expensesOnly = 0.0;
					var expensesAndCredits = 0.0;
					for(var j = 0; j < stationSummaryData[i].expensesAndCredits.length; j++) {
						expenseOrCredit = stationSummaryData[i].expensesAndCredits[j];
						if (expenseOrCredit.item.toLowerCase().includes("visa")) {
							visaCredits += expenseOrCredit.amt;
						}
						expensesAndCredits += expenseOrCredit.amt;
					}
					expensesOnly = expensesAndCredits - visaCredits;
					
					count++;
					var row = [
						count,
						moment(stationSummaryData[i].date).tz('America/Lima').format('DD/MM/YYYY hh:mm A'),
						stationSummaryData[i].pumpAttendantNames,
						stationSummaryData[i].shift,
						stationSummaryData[i].totalDay.totalDayUnits.d2.stockGals,
						stationSummaryData[i].totalDay.totalDayUnits.g90.stockGals,
						stationSummaryData[i].totalDay.totalDayUnits.g95.stockGals,
						stationSummaryData[i].totalDay.totalDayUnits.d2.totalGalsSoldDay,
						stationSummaryData[i].totalDay.totalDayUnits.g90.totalGalsSoldDay,
						stationSummaryData[i].totalDay.totalDayUnits.g95.totalGalsSoldDay,
						stationSummaryData[i].totalDay.totalDayUnits.d2.totalSolesRevenueDay,
						stationSummaryData[i].totalDay.totalDayUnits.g90.totalSolesRevenueDay,
						stationSummaryData[i].totalDay.totalDayUnits.g95.totalSolesRevenueDay,
						stationSummaryData[i].totalDay.totalDayUnits.d2.totalProfitDay,
						stationSummaryData[i].totalDay.totalDayUnits.g90.totalProfitDay,
						stationSummaryData[i].totalDay.totalDayUnits.g95.totalProfitDay,
						stationSummaryData[i].totalDay.totalSolesRevenueDay,
						stationSummaryData[i].totalCash,
						expensesOnly,
						visaCredits,
						expensesAndCredits,
						(stationSummaryData[i].totalDay.totalSolesRevenueDay - stationSummaryData[i].totalCash - expensesAndCredits).toFixed(2),
						stationSummaryData[i].totalDay.totalProfitDay
						];
					
					tableData[i] = row;
				}
				
				
				this.setState({stationSummaryData: tableData});
			},
			error: function(e){

			}	
		});
  }
  
  _fetchStockData(timeframe){
		
	  	var search = {};
		search["dateEnd"] = timeframe.dateEnd;
		search["dateBeg"] = timeframe.dateBeg;
		
		jQuery.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/getLatestStock",
			data: JSON.stringify(search),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				var stockData = data.result;
				var tableData = [];
				
				var count = 0;
				for (var i = 0; i < stockData.length-1; i++) {
					
					var delivery = [];
					for(var j = 0; j < stockData[i].tanks.length; j++) {
						if (stockData[i].delivery) {
							delivery[j] = stockData[i].tanks[j].gals - stockData[i+1].tanks[j].gals;
						} else {
							delivery[j] = 0;
						}
					}

					count++;
					var row = [
						count,
						moment(stockData[i].date).tz('America/Lima').format('DD/MM/YYYY hh:mm A'),
						stockData[i].pumpAttendantNames,
						stockData[i].delivery,
						stockData[i].tanks[0].fuelType,
						stockData[i].tanks[0].gals,
						delivery[0],
						stockData[i].tanks[0].cost,
						stockData[i].tanks[1].fuelType,
						stockData[i].tanks[1].gals,
						delivery[1],
						stockData[i].tanks[1].cost,
						stockData[i].tanks[2].fuelType,
						stockData[i].tanks[2].gals,
						delivery[2],
						stockData[i].tanks[2].cost,
						stockData[i].supplierRUC,
						stockData[i].truckDriverName,
						stockData[i].truckPlateNumber
						];
					
					tableData[i] = row;
				}
				
				
				this.setState({stockSummaryData: tableData});
			},
			error: function(e){

			}	
		});
  }
  
  _fetchGasPricesData(timeframe){
		
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
				var gasPricesData = data.result;
				var tableData = [];
				
				var count = 0;
				for (var i = 0; i < gasPricesData.length; i++) {
					count++;
					var row = [
						count, 
						moment(gasPricesData[i].date).tz('America/Lima').format('DD/MM/YYYY hh:mm A'),
						gasPricesData[i].pumpAttendantNames,
						gasPricesData[i].gasPrices[0].fuelType,
						gasPricesData[i].gasPrices[0].cost,
						gasPricesData[i].gasPrices[0].price,
						gasPricesData[i].gasPrices[1].fuelType,
						gasPricesData[i].gasPrices[1].cost,
						gasPricesData[i].gasPrices[1].price,
						gasPricesData[i].gasPrices[2].fuelType,
						gasPricesData[i].gasPrices[2].cost,
						gasPricesData[i].gasPrices[2].price
					];
					
					tableData[i] = row;
				}
				
				
				this.setState({gasPricesSummaryData: tableData});
			},
			error: function(e){

			}	
		});
  }
  
  componentWillMount(){
	  this._fetchStationData({dateEnd: "latest", dateBeg: "-30"});
	  this._fetchStockData({dateEnd: "latest", dateBeg: "-30"});
	  this._fetchGasPricesData({dateEnd: "latest", dateBeg: "-30"});
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
	                  <label className="control-label">Today</label>
	                  <input type="text" id="lastName" className="form-control" placeholder="Fecha" value={`${moment().tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}  readOnly/>
	              </div>
	          </div>
	      </div>
	      
	      {this.state && this.state.stationSummaryData &&
	    	  <StationTbl data={this.state.stationSummaryData}></StationTbl>
		  } 
	      
	      {this.state && this.state.stockSummaryData &&
	    	  <StockTbl data={this.state.stockSummaryData}></StockTbl>
		  } 
	      {this.state && this.state.gasPricesSummaryData &&
	    	  <GasPricesTbl data={this.state.gasPricesSummaryData}></GasPricesTbl>
		  } 
	      
      </form>
    )
  }
}

class GasPricesTbl extends React.Component {
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
					{ title: "Nro" },
					{ title: "Fecha" },
		            { title: "Autor" },
		            { title: "Prod" },
		            { title: "Costo" },
		            { title: "Precio" },
		            { title: "Prod" },
		            { title: "Costo" },
		            { title: "Precio" },
		            { title: "Prod" },
		            { title: "Costo" },
		            { title: "Precio" }
			]
		});
	}
	
	componentWillUnmount() {
		
	}
	
	render() {
		return <div>
					<div className="row">
						<div className="col-md-9">
							<div className="portlet light bordered">
								<div className="portlet-title">
									<div className="caption font-green">
										<i className="icon-settings font-green"></i>
										<span className="caption-subject bold uppercase">Tabla de Precios</span>
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
  
class StockTbl extends React.Component {
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
					{ title: "Nro" },
					{ title: "Fecha" },
		            { title: "Autor" },
		            { title: "De Pedido?" },
		            { title: "Prod" },
		            { title: "Stock" },
		            { title: "Descarga" },
		            { title: "Costo" },
		            { title: "Prod" },
		            { title: "Stock" },
		            { title: "Descarga" },
		            { title: "Costo" },
		            { title: "Prod" },
		            { title: "Stock" },
		            { title: "Descarga" },
		            { title: "Costo" },
		            { title: "RUC" },
		            { title: "Conductor" },
		            { title: "Placa de Camion" }
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
										<span className="caption-subject bold uppercase">Tabla de Stocks</span>
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

class StationTbl extends React.Component {
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
					{ title: "Nro" },
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
										<span className="caption-subject bold uppercase">Tabla de Ventas/Ganancias/Pérdidas</span>
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

ReactDOM.render(<TableDashboard />, target);