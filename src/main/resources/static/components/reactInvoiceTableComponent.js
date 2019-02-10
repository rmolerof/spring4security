
class InvoiceTableSummary extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: {},
	  showError: false,
	  showSuccess: false,
      invoicesSummaryData: null,
      invoicesSummaryConcarData: null,
      processingTypeButtonToggle: true,
      processingType: 'NORMAL'
    };
    
    this.CONSTANTS = {
	    FACTURA: '01',
	    BOLETA: '03',
	    NOTADECREDITO: '07',
	    SUNAT_PENDING_STATUS: 'PENDIENTE',
	    SUNAT_SENT_STATUS: 'ENVIADO',
	    NORMAL_PROCESSING_TYPE: 'NORMAL',
	    FORCED_PROCESSING_TYPE: 'FORCED'
    }
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
						invoicesSummaryData[i].invoiceHash,
						invoicesSummaryData[i].bonusNbr,
						invoicesSummaryData[i].sunatStatus,
						"<a class='view' href='/invoice-page?id=" + invoicesSummaryData[i].invoiceNumber + "'>Edit</a>",
						'<a class="delete" href="">Delete</a>'
						];
					
					tableData[i] = row;
				}
				
				this.setState({invoicesSummaryData: tableData});
			},
			error: function(e){

			}	
		});
  }
  
  _fetchInvoiceConcarData(timeframe){
		
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
					
					var dateOfInvoiceModified = '';
					var igvModified = '';
					var totalModified = '';
					if (invoicesSummaryData[i].invoiceType == "07") {
						dateOfInvoiceModified = moment(invoicesSummaryData[i].dateOfInvoiceModified).tz('America/Lima').format('DD/MM/YYYY');
						igvModified = invoicesSummaryData[i].igvModified; 
						totalModified = invoicesSummaryData[i].totalModified;
					} 
					
					var clientDocNumber = '';
					if (invoicesSummaryData[i].clientDocNumber == '0') {
						clientDocNumber = '0000';
					} else {
						clientDocNumber = invoicesSummaryData[i].clientDocNumber; 
					}
					
					// Assuming only one product is purchased
					var ctacble = '';
					var glosa = '';
					if (invoicesSummaryData[i].galsD2 > 0) {
						ctacble = '701112';
						glosa = 'DIESEL';
					} else if (invoicesSummaryData[i].galsG90 > 0) {
						ctacble = '701114';
						glosa = 'GASOHOL-90';
					} else if (invoicesSummaryData[i].galsG95 > 0) {
						ctacble = '701115';
						glosa = 'GASOHOL-95';
					} else {
						ctacble = '700000';
						glosa = 'OTRO';
					}
					
					
					
					count++;
					var row = [
						count,
						moment(invoicesSummaryData[i].date).tz('America/Lima').format('DD/MM/YYYY'),
						invoicesSummaryData[i].invoiceType,
						invoicesSummaryData[i].invoiceNumber,
						"S/.",
						clientDocNumber,
						invoicesSummaryData[i].clientName,
						invoicesSummaryData[i].subTotal,
						"",
						invoicesSummaryData[i].totalIGV,
						"",
						invoicesSummaryData[i].total,
						"5",
						"",
						ctacble,
						glosa,
						invoicesSummaryData[i].invoiceTypeModified,
						invoicesSummaryData[i].invoiceNumberModified,
						dateOfInvoiceModified,
						igvModified,
						totalModified
						];
					
					tableData[i] = row;
				}
				
				this.setState({invoicesSummaryConcarData: tableData});
			},
			error: function(e){

			}	
		});
}
  
  componentWillMount(){
	  this._fetchInvoiceData({dateEnd: "latest", dateBeg: "-31"});
	  this._fetchInvoiceConcarData({dateEnd: "latest", dateBeg: "-31"});
  }
  
  onKeyPress(event) {
	if (event.which === 13 ) {
		event.preventDefault();
	}
  }
  
/*  validatePendingInvoices() {
	  	var search = {};
		search["dateEnd"] = "latest";
		search["dateBeg"] = "-31";
		
		jQuery.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/validatePendingInvoices",
			data: JSON.stringify(search),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				
				
			},
			error: function(e){

			}	
		});
  }*/
  
  _toggleError = () => {
	  this.setState((prevState, props) => {
		  return {showError: !prevState.showError}
	  })
  }
  
  submitPendingInvoiceGroup = (evt) => {
		evt.preventDefault();
		
		var self = this;
		self.setState({ showError: false});
		var sunatSubmitCriteria = {};
		sunatSubmitCriteria["processingType"] = this.state.processingType;
		
		jQuery.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/submitInvoicesToSunat",
			data: JSON.stringify(sunatSubmitCriteria),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {
				
				if (data.result.length > 0) {
					var submittedInvoices = data.result;
					var errors = [];
					var validationError = "Comprobantes Invalidos: ";
					var sunatErrorStr = "";
					var sunatErrorCount = 0;
					
					for (var i = 0; i < submittedInvoices.length; i++) {
						if (submittedInvoices[i].sunatStatus == self.CONSTANTS.SUNAT_PENDING_STATUS) {
							validationError += submittedInvoices[i].invoiceNumber + " ";
							sunatErrorStr += submittedInvoices[i].sunatErrorStr + " "
							sunatErrorCount++;
						}
					}
					
					if (sunatErrorCount > 0) {
						errors["sunatValidationMsg"] = validationError + sunatErrorStr;
						this.setState({errors: errors, showError: true});
					} else {
						this.setState({showSuccess: true});
					}
				} else {
					var errors = {
			    		submit: data.msg
				    };
					self.setState({errors: errors}); 
					self._toggleError();
				}
				
			},
			error: function(e){

			}	
		});
  }
  
  _processingTypeButtonHandleClick(){
		this.setState({processingTypeButtonToggle: !this.state.processingTypeButtonToggle});
		
		if (this.state.processingTypeButtonToggle) {
			this.setState({processingType: this.CONSTANTS.FORCED_PROCESSING_TYPE});
		} else {
			this.setState({processingType: this.CONSTANTS.NORMAL_PROCESSING_TYPE});
		}
  }
  
  render() {    
	  
	let processingTypeButtonText = this.CONSTANTS.NORMAL_PROCESSING_TYPE;
	if (!this.state.processingTypeButtonToggle) {
		processingTypeButtonText = this.CONSTANTS.FORCED_PROCESSING_TYPE;
	}
	  
    return (
    
      <form onSubmit={this.submitPendingInvoiceGroup}>
      	  
	      {this.state.showError && 
		        <div className="alert alert-danger">
	      <strong>¡Error!</strong>{" " + this.state.errors.sunatValidationMsg + " " + this.state.errors.submit}  
		      	</div>
	      }
	      
	      {this.state.showSuccess && 
	      	<div className="alert alert-success">
	      		<strong>Success!</strong> Los comprobantes fueron enviados con éxito. 
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
	      
	      {/*<button id="sample_editable_1_new" className="btn green"> Nuevo Comprobante
	          <i className="fa fa-plus"></i>
	      </button>*/}
	      <div style={{textAlign: 'right'}}>
		      {/*<button type="submit" onClick={this.validatePendingInvoices.bind(this)} className="btn blue hidden-print margin-bottom-5">
	        	<i className="fa fa-check"></i>Validar Comprobantes
	          </button>&nbsp;*/}
		      {/*<button className="btn blue hidden-print margin-bottom-5">
	        	<a onClick={this._processingTypeButtonHandleClick.bind(this)} className="btn green-meadow">{processingTypeButtonText}&nbsp;<i className="fa fa-check"></i></a>
	          </button>&nbsp;*/}
	          <a type="submit" onClick={this._processingTypeButtonHandleClick.bind(this)} className="btn purple hidden-print margin-bottom-5" > {processingTypeButtonText}&nbsp;<i className="fa fa-check"></i></a>&nbsp;
		      <button type="submit" className="btn blue hidden-print margin-bottom-5">
	          	<i className="fa fa-check"></i>Procesar Pendientes
	          </button>&nbsp;
	          <button className="btn green margin-bottom-5"> 
		      	<a className='view' href='/invoice-page'>Nuevo Comprobante</a>&nbsp;<i className="fa fa-plus"></i>
		      </button>
	      </div>
	      {/*<div style={{textAlign: 'right'}}>
		      
	      </div>*/}
	      
	      {this.state && this.state.invoicesSummaryData &&
	    	  <InvoicesTbl data={this.state.invoicesSummaryData}></InvoicesTbl>
		  }
	      
	      {this.state && this.state.invoicesSummaryConcarData &&
	    	  <InvoicesConcarTbl data={this.state.invoicesSummaryConcarData}></InvoicesConcarTbl>
		  } 
	      
      </form>
    )
  }
}


var Modal = ReactBootstrap.Modal;
var Button = ReactBootstrap.Button;

class InvoicesTbl extends React.Component {
	
	constructor() {
	    super();
	    this.state = {
		  showModal: false,
		  loadingGif: false
	    }
	}
	    
	handleResultModalCancel() {
		this.setState({ showModal: false });
	}
	
	componentDidMount() {
		var self = this;
		this.$el = $(this.el);
		var table = this.$el;
		var oTable = table.dataTable({
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
		            { title: "Bonus" },
		            { title: "Sunat Status" },
		            { title: "Edit" },
		            { title: "Cancel" }
			]
		});
		
		var nEditing = null;
        var nNew = false;

        $('#sample_editable_1_new').click(function (e) {
            e.preventDefault();

            if (nNew && nEditing) {
                if (confirm("Previose row not saved. Do you want to save it ?")) {
                    saveRow(oTable, nEditing); // save
                    $(nEditing).find("td:first").html("Untitled");
                    nEditing = null;
                    nNew = false;

                } else {
                    oTable.fnDeleteRow(nEditing); // cancel
                    nEditing = null;
                    nNew = false;
                    
                    return;
                }
            }

            var aiNew = oTable.fnAddData(['', '', '', '', '', '','', '', '', '', '', '','', '', '', '', '', '', '', '', '']);
            var nRow = oTable.fnGetNodes(aiNew[0]);
            editRow(oTable, nRow);
            nEditing = nRow;
            nNew = true;
        });
        
        function restoreRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);

            for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                oTable.fnUpdate(aData[i], nRow, i, false);
            }

            oTable.fnDraw();
        }

        function editRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
            jqTds[0].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[0] + '">';
            jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[1] + '">';
            jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[2] + '">';
            jqTds[3].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[3] + '">';
            jqTds[4].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[4] + '">';
            jqTds[5].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[5] + '">';
            jqTds[6].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[6] + '">';
            jqTds[7].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[7] + '">';
            jqTds[8].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[8] + '">';
            jqTds[9].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[9] + '">';
            jqTds[10].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[10] + '">';
            jqTds[11].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[11] + '">';
            jqTds[12].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[12] + '">';
            jqTds[13].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[13] + '">';
            jqTds[14].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[14] + '">';
            jqTds[15].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[15] + '">';
            jqTds[16].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[16] + '">';
            jqTds[17].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[17] + '">';
            jqTds[18].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[18] + '">';
            jqTds[19].innerHTML = '<a class="edit" href="">Save</a>';
            jqTds[20].innerHTML = '<a class="cancel" href="">Cancel</a>';
        }

        function saveRow(oTable, nRow) {
            var jqInputs = $('input', nRow);
            oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
            oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
            oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
            oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
            oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
            oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 5, false);
            oTable.fnDraw();
        }

        function cancelEditRow(oTable, nRow) {
            var jqInputs = $('input', nRow);
            oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
            oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
            oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
            oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
            oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
            oTable.fnDraw();
        }
		
		/*var table = this.$el.DataTable({...
		table.column(3).visible(! table.column(3).visible());
		table.column(6).visible(! table.column(6).visible());*/
        
        table.on('click', '.delete', function (e) {
            e.preventDefault();
            
            var nRow = $(this).parents('tr')[0];
            var invoiceNumber = nRow.cells[4].innerText;
            
            if (confirm("¿Está seguro de borrar comprobante " + invoiceNumber + "?") == false) {
                return;
            }
            
            oTable.fnDeleteRow(nRow);
            //alert("Deleted! Do not forget to do some ajax to sync with backend :)");
            
            var search = {};
    	    search["invoiceNumber"] = invoiceNumber;
    	    self.setState({loadingGif: true});
    	    
    		$.ajax({
    			type: "POST",
    			contentType: "application/json", 
    			url:"/api/deleteInvoice",
    			data: JSON.stringify(search),
    			datatype: 'json',
    			cache: false,
    			timeout: 600000,
    			success: function(data) {
    				
    			   self.setState({loadingGif: false});	  
    			      		
    			}, error: function(e){
    				console.log("ERROR: ", e);
    				self.setState({loadingGif: false});
    			}	
    		});  
        });
        
        table.on('click', '.cancel', function (e) {
            e.preventDefault();
            if (nNew) {
                oTable.fnDeleteRow(nEditing);
                nEditing = null;
                nNew = false;
            } else {
                restoreRow(oTable, nEditing);
                nEditing = null;
            }
        });

        table.on('click', '.edit', function (e) {
            e.preventDefault();
            nNew = false;
            
            /* Get the row as a parent of the link that was clicked on */
            var nRow = $(this).parents('tr')[0];

            if (nEditing !== null && nEditing != nRow) {
                /* Currently editing - but not this row - restore the old before continuing to edit mode */
                restoreRow(oTable, nEditing);
                editRow(oTable, nRow);
                nEditing = nRow;
            } else if (nEditing == nRow && this.innerHTML == "Save") {
                /* Editing this row and want to save it */
                saveRow(oTable, nEditing);
                nEditing = null;
                alert("Updated! Do not forget to do some ajax to sync with backend :)");
            } else {
                /* No edit in progress - let's start one */
                editRow(oTable, nRow);
                nEditing = nRow;
            }
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
					
					<Modal show={this.state.showModal} onHide={this.handleResultModalCancel.bind(this)}>
			          <Modal.Header closeButton>
			            <Modal.Title>Status</Modal.Title>
			          </Modal.Header>
			          
			          <Modal.Body>
					      <div className="alert alert-danger">
					      	<strong>¿Estás seguro de borrar esta fila?</strong>  
						  </div>
			          </Modal.Body>
			          
			          <Modal.Footer>
			          	<Button bsStyle="primary" name="acceptModal" ref={'refResultModal'} onClick={this.handleResultModalCancel.bind(this)}>Aceptar</Button>
			          </Modal.Footer>
			      </Modal>
			   </div>
	}
}

class InvoicesConcarTbl extends React.Component {
	componentDidMount() {
		this.$el = $(this.el);
		var table = this.$el.DataTable({
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
					{ title: "ITEM" },
				 	{ title: "FECHA" },
				 	{ title: "TIPO" },
				 	{ title: "NUMERO" },
				 	{ title: "MONEDA" },
		            { title: "CODIGO|RUC|DNI" },
		            { title: "CLIENTE" },
		            { title: "VALOR" },
		            { title: "EXONERADO" },
		            { title: "IGV" },
		            { title: "PERCEPCION" },
		            { title: "TOTAL" },
		            { title: "SUB" },
		            { title: "COSTO" },
		            { title: "CTACBLE" },
		            { title: "GLOSA" },
		            { title: "TDOC REF" },
		            { title: "NUMERO REF" },
		            { title: "FECHA REF" },
		            { title: "IGV REF" },
		            { title: "BASE IMP REF" }
		            
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
										<span className="caption-subject bold uppercase">Provisión Ventas para Concar</span>
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