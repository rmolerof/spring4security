
class InvoiceTableSummary extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: {},
	  showError: false,
	  showSuccess: false,
      invoicesSummaryData: null,
    };
  }
  
  _toggleError = () => {
	  this.setState((prevState, props) => {
		  return {showError: !prevState.showError}
	  })
  };
  
  _fetchInvoiceData(timeframe){
			
	  	var search = {};
		search["dateEnd"] = timeframe.dateEnd;
		search["dateBeg"] = timeframe.dateBeg;
		
		jQuery.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/getInvoicesSummaryData",
			data: JSON.stringify(search),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				
				var invoicesSummaryData = data.result;
				var tableData = [];
				
				var count = 0;
				for (var i = 0; i < invoicesSummaryData.length; i++) {
					
					var invoiceType = '';
					if (invoicesSummaryData[i].invoiceType == '01') {
						invoiceType = 'FACTURA'
					} else if (invoicesSummaryData[i].invoiceType == '03') {
						invoiceType = 'BOLETA'
					} else if (invoicesSummaryData[i].invoiceType == '07') {
						invoiceType = 'N. CREDITO'
					}
					var clientDocType = '';
					if (invoicesSummaryData[i].clientDocType == '1') {
						clientDocType = 'DNI'
					} else {
						clientDocType = 'RUC'
					}
					count++;
					var row = [
						count,
						/*moment(invoicesSummaryData[i].date).tz('America/Lima').format('DD/MM/YYYY hh:mm A'),*/
						moment(invoicesSummaryData[i].date).tz('America/Lima').format('DD/MM/YYYY'),
						moment(invoicesSummaryData[i].date).tz('America/Lima').format('hh:mm:ss A'),
						invoiceType,
						invoicesSummaryData[i].invoiceNumber,
						clientDocType,
						invoicesSummaryData[i].clientDocNumber,
						invoicesSummaryData[i].clientName,
						invoicesSummaryData[i].priceD2,
						invoicesSummaryData[i].priceG90,
						invoicesSummaryData[i].priceG95,
						invoicesSummaryData[i].galsD2,
						invoicesSummaryData[i].galsG90,
						invoicesSummaryData[i].galsG95,
						invoicesSummaryData[i].subTotal,
						invoicesSummaryData[i].totalIGV,
						invoicesSummaryData[i].total,
						invoicesSummaryData[i].invoiceHash
						];
					
					tableData[i] = row;
				}
				
				this.setState({invoicesSummaryData: tableData});
			},
			error: function(e){

			}	
		});
  }
  
  componentWillMount(){
	  this._fetchInvoiceData({dateEnd: "latest", dateBeg: "-30"});
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
	                  <label className="control-label">Hoy es</label>
	                  <input type="text" id="lastName" className="form-control" placeholder="Fecha" value={`${moment().tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}  readOnly/>
	              </div>
	          </div>
	      </div>
	      
	      {this.state && this.state.invoicesSummaryData &&
	    	  <InvoicesTbl data={this.state.invoicesSummaryData}></InvoicesTbl>
		  } 
	      
      </form>
    )
  }
}

class InvoicesTbl extends React.Component {
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
				 	{ title: "Hora" },
				 	{ title: "Recibo Tipo" },
				 	{ title: "Nro" },
		            { title: "Tipo Doc" },
		            { title: "Nro" },
		            { title: "A Nombre" },
		            { title: "Precio D2" },
		            { title: "Precio G90" },
		            { title: "Precio G95" },
		            { title: "Gals D2" },
		            { title: "Gals G90" },
		            { title: "Gals G95" },
		            { title: "Subtotal" },
		            { title: "IGV" },
		            { title: "Total" },
		            { title: "Nro hash" },
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
										<span className="caption-subject bold uppercase">Resumen de Boletas/Facturas/Notas de Crédito</span>
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

let target = document.getElementById('invoice-table-summary');

ReactDOM.render(<InvoiceTableSummary />, target);