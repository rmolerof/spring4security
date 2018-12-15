var Modal = ReactBootstrap.Modal;
var Button = ReactBootstrap.Button;

class TableDashboard extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: {},
	  showError: false,
	  showSuccess: false,
	  showEmailModal: false, 
	  invoiceNumber: 'B001-XXXXXXXX',
	  // customer
      clientDocNumber: '',
      clientDocNumberDisabled: false,
      clientName: '',
      clientNameDisabled: false,
      clientDocType: '1',
      clientAddress: '',
      clientAddressDisabled: false,
      truckPlateNumber: '',
      clientEmailAddress: '',
	  // invoice breakdown
      date: '',
      invoiceType: '03',
	  // Break-down
      galsD2: '',
      galsG90: '',
      galsG95: '',
      priceD2: '',
      priceG90: '',
      priceG95: '',
      solesD2: '',
      solesG90: '',
      solesG95: '',
      // Payment
  	  subTotal: '',
	  discount: '',
	  totalIGV: '',
	  total: '',
	  electronicPmt: '',
	  cashPmt: '',
	  cashGiven: '',
	  change: '',
      // Save or update in DB
      gasPrices: [],
      totalVerbiage: '',
      invoiceHash: '',
      saveOrUpdate: 'save',
      sunatErrorStr: '',
      status: '',
      selectedOption: 'boleta',
      boletaDisabled: false,
      facturaDisabled: false,
      notaDeCreditoDisabled: false,
      submitDisabled: false,
      showBoletaRadioButton: 'btn blue active btn-sm',
      showFacturaRadioButton: 'btn btn-default btn-sm',
      showNotaDeCreditoRadioButton: 'btn btn-default btn-sm',
      docLabelObj: {
    	  clientDocType: 'DNI',
    	  clientDocTypePH: 'Ingrese DNI',
    	  clientName: 'Señor(es)',
    	  clientNamePH: 'Nombre(s)',
      },
      loadingGif: false,
      emailingGif: false,
      invoiceTypeModified: '',
      invoiceNumberModified: '',
      invoiceNumberModifiedDisp: '',
      invoiceTypeModifiedToggle: true,
      dateOfInvoiceModified: new Date(),
      motiveCd: '',
      motiveCdDescription: '',
	  igvModified: '',
	  totalModified: '',
	  hasBonus: false,
	  cssBonusButton: 'btn btn-default'
    };
  }
  
  galsD2Change = (evt) => {
	  	this.setState({ galsD2: evt.target.value == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	  	this.setState({ solesD2: evt.target.value == '' ? '': ((evt.target.value * this.state.priceD2 * 100).toFixed() / 100) });
	  	
	  	var totalSum = ((parseFloat(((evt.target.value * this.state.priceD2 * 100).toFixed() / 100) || 0) + parseFloat(this.state.solesG90 || 0) + parseFloat(this.state.solesG95 || 0)) * 100).toFixed() / 100;
	    var subTotal = parseFloat((totalSum - (totalSum * 18 / 1.18).toFixed() / 100).toFixed(2));
	    var discount = this.state.discount || 0;
	    var totalIGV = parseFloat(((((totalSum - totalSum * 0.18 / 1.18) - discount) * 18).toFixed() / 100).toFixed(2));
	    var total = (subTotal - discount + totalIGV).toFixed(2);
	    var cashPmt = (total - (this.state.electronicPmt  || 0)).toFixed(2);
	    var change = ((this.state.cashGiven || 0) - cashPmt).toFixed(2);
	    this.setState({ subTotal: subTotal});
	    this.setState({ totalIGV: totalIGV});
	    this.setState({ total: total});
	    this.setState({ cashPmt: cashPmt});
	    this.setState({ change: change});
  }
  galsG90Change = (evt) => {
	    this.setState({ galsG90: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	    this.setState({ solesG90: evt.target.value == '' ? '': ((evt.target.value * this.state.priceG90 * 100).toFixed() / 100) });
	    
	    var totalSum = ((parseFloat(this.state.solesD2 || 0) + parseFloat(((evt.target.value * this.state.priceG90 * 100).toFixed() / 100) || 0) + parseFloat(this.state.solesG95 || 0)) * 100).toFixed() / 100;
	    var subTotal = parseFloat((totalSum - (totalSum * 18 / 1.18).toFixed() / 100).toFixed(2));
	    var discount = (this.state.discount || 0);
	    var totalIGV = parseFloat(((((totalSum - totalSum * 0.18 / 1.18) - discount) * 18).toFixed() / 100).toFixed(2));
	    var total = (subTotal - discount + totalIGV).toFixed(2);
	    var cashPmt = total - (this.state.electronicPmt || 0);
	    var change = ((this.state.cashGiven || 0) - cashPmt).toFixed(2);
	    this.setState({ subTotal: subTotal});
	    this.setState({ totalIGV: totalIGV});
	    this.setState({ total: total});
	    this.setState({ cashPmt: cashPmt});
	    this.setState({ change: change});
  }
  galsG95Change = (evt) => {
	    this.setState({ galsG95: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	    this.setState({ solesG95: evt.target.value == '' ? '': ((evt.target.value * this.state.priceG95 * 100).toFixed() / 100) });
	    
	    var totalSum = ((parseFloat(this.state.solesD2 || 0) + parseFloat(this.state.solesG90 || 0) + parseFloat(((evt.target.value * this.state.priceG95 * 100).toFixed() / 100) || 0)) * 100).toFixed() / 100;
	    var subTotal = parseFloat((totalSum - (totalSum * 18 / 1.18).toFixed() / 100).toFixed(2));
	    var discount = this.state.discount || 0;
	    var totalIGV = parseFloat(((((totalSum - totalSum * 0.18 / 1.18) - discount) * 18).toFixed() / 100).toFixed(2));
	    var total = (subTotal - discount + totalIGV).toFixed(2);
	    var cashPmt = (total - (this.state.electronicPmt  || 0)).toFixed(2);
	    var change = ((this.state.cashGiven || 0) - cashPmt).toFixed(2);
	    this.setState({ subTotal: subTotal});
	    this.setState({ totalIGV: totalIGV});
	    this.setState({ total: total});
	    this.setState({ cashPmt: cashPmt});
	    this.setState({ change: change});
  }
  solesD2Change = (evt) => {
	    this.setState({ solesD2: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	    this.setState({ galsD2: evt.target.value == '' ? '': ((evt.target.value / this.state.priceD2 * 100).toFixed() / 100) });
	    
	    var totalSum = ((parseFloat(evt.target.value || 0) + parseFloat(this.state.solesG90 || 0) + parseFloat(this.state.solesG95 || 0)) * 100).toFixed() / 100;
	    var subTotal = parseFloat((totalSum - (totalSum * 18 / 1.18).toFixed() / 100).toFixed(2));
	    var discount = this.state.discount || 0;
	    var totalIGV = parseFloat(((((totalSum - totalSum * 0.18 / 1.18) - discount) * 18).toFixed() / 100).toFixed(2));
	    var total = (subTotal - discount + totalIGV).toFixed(2);
	    var cashPmt = (total - (this.state.electronicPmt  || 0)).toFixed(2);
	    var change = ((this.state.cashGiven || 0) - cashPmt).toFixed(2);
	    this.setState({ subTotal: subTotal});
	    this.setState({ totalIGV: totalIGV});
	    this.setState({ total: total});
	    this.setState({ cashPmt: cashPmt});
	    this.setState({ change: change});
  }
  solesG90Change = (evt) => {
	  this.setState({ solesG90: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	  this.setState({ galsG90: evt.target.value == '' ? '': ((evt.target.value / this.state.priceG90 * 100).toFixed() / 100) });
	  
	  var totalSum = ((parseFloat(this.state.solesD2 || 0) + parseFloat(evt.target.value || 0) + parseFloat(this.state.solesG95 || 0)) * 100).toFixed() / 100;
	  var subTotal = parseFloat((totalSum - (totalSum * 18 / 1.18).toFixed() / 100).toFixed(2));
	  var discount = this.state.discount || 0;
	  var totalIGV = parseFloat(((((totalSum - totalSum * 0.18 / 1.18) - discount) * 18).toFixed() / 100).toFixed(2));
	  var total = (subTotal - discount + totalIGV).toFixed(2);
	  var cashPmt = (total - (this.state.electronicPmt  || 0)).toFixed(2);
	  var change = ((this.state.cashGiven || 0) - cashPmt).toFixed(2);
	  this.setState({ subTotal: subTotal});
	  this.setState({ totalIGV: totalIGV});
	  this.setState({ total: total});
	  this.setState({ cashPmt: cashPmt});
	  this.setState({ change: change});
  }
  solesG95Change = (evt) => {
	  this.setState({ solesG95: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	  this.setState({ galsG95: evt.target.value == '' ? '': ((evt.target.value / this.state.priceG95 * 100).toFixed() / 100) });
	  
	  var totalSum = ((parseFloat(this.state.solesD2 || 0) + parseFloat(this.state.solesG90 || 0) + parseFloat(evt.target.value || 0)) * 100).toFixed() / 100;
	  var subTotal = parseFloat((totalSum - (totalSum * 18 / 1.18).toFixed() / 100).toFixed(2));
	  var discount = this.state.discount || 0;
	  var totalIGV = parseFloat(((((totalSum - totalSum * 0.18 / 1.18) - discount) * 18).toFixed() / 100).toFixed(2));
	  var total = (subTotal - discount + totalIGV).toFixed(2);
	  var cashPmt = (total - (this.state.electronicPmt  || 0)).toFixed(2);
	  var change = ((this.state.cashGiven || 0) - cashPmt).toFixed(2);
	  this.setState({ subTotal: subTotal});
	  this.setState({ totalIGV: totalIGV});
	  this.setState({ total: total});
	  this.setState({ cashPmt: cashPmt});
	  this.setState({ change: change});
  }
  discountChange = (evt) => {
	  this.setState({ discount: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	  
	  var totalSum = ((parseFloat(this.state.solesD2 || 0) + parseFloat(this.state.solesG90 || 0) + parseFloat(this.state.solesG95 || 0)) * 100).toFixed() / 100;
	  var subTotal = parseFloat((totalSum - (totalSum * 18 / 1.18).toFixed() / 100).toFixed(2));
	  var discount = evt.target.value || 0;
	  var totalIGV = parseFloat(((((totalSum - totalSum * 0.18 / 1.18) - discount) * 18).toFixed() / 100).toFixed(2));
	  var total = (subTotal - discount + totalIGV).toFixed(2);
	  var cashPmt = (total - (this.state.electronicPmt  || 0)).toFixed(2);
	  var change = ((this.state.cashGiven || 0) - cashPmt).toFixed(2);
	  this.setState({ subTotal: subTotal});
	  this.setState({ totalIGV: totalIGV});
	  this.setState({ total: total});
	  this.setState({ cashPmt: cashPmt});
	  this.setState({ change: change});
  }
  electronicPmtChange = (evt) => {
	  this.setState({ electronicPmt: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	  
	  var totalSum = ((parseFloat(this.state.solesD2 || 0) + parseFloat(this.state.solesG90 || 0) + parseFloat(this.state.solesG95 || 0)) * 100).toFixed() / 100;
	  var subTotal = parseFloat((totalSum - (totalSum * 18 / 1.18).toFixed() / 100).toFixed(2));
	  var discount = this.state.discount || 0;
	  var totalIGV = parseFloat(((((totalSum - totalSum * 0.18 / 1.18) - discount) * 18).toFixed() / 100).toFixed(2));
	  var total = (subTotal - discount + totalIGV).toFixed(2);
	  var cashPmt = (total - (evt.target.value  || 0)).toFixed(2);
	  var change = ((this.state.cashGiven || 0) - cashPmt).toFixed(2);
	  this.setState({ subTotal: subTotal});
	  this.setState({ totalIGV: totalIGV});
	  this.setState({ total: total});
	  this.setState({ cashPmt: cashPmt});
	  this.setState({ change: change});
  }
  cashGivenChange = (evt) => {
	  this.setState({ cashGiven: evt.target.value  == '' ? '': ((evt.target.value * 100).toFixed() / 100) });
	  
	  var totalSum = ((parseFloat(this.state.solesD2 || 0) + parseFloat(this.state.solesG90 || 0) + parseFloat(this.state.solesG95 || 0)) * 100).toFixed() / 100;
	  var subTotal = parseFloat((totalSum - (totalSum * 18 / 1.18).toFixed() / 100).toFixed(2));
	  var discount = this.state.discount || 0;
	  var totalIGV = parseFloat(((((totalSum - totalSum * 0.18 / 1.18) - discount) * 18).toFixed() / 100).toFixed(2));
	  var total = (subTotal - discount + totalIGV).toFixed(2);
	  var cashPmt = (total - (this.state.electronicPmt  || 0)).toFixed(2);
	  var change = ((evt.target.value  || 0) - cashPmt).toFixed(2);
	  this.setState({ subTotal: subTotal});
	  this.setState({ totalIGV: totalIGV});
	  this.setState({ total: total});
	  this.setState({ cashPmt: cashPmt});
	  this.setState({ change: change});
  }
  
  clientAddressChange = (evt) => {
	    this.setState({ clientAddress: evt.target.value.toUpperCase() });
  }
  
  clientEmailAddressChange = (evt) => {
	    this.setState({ clientEmailAddress: evt.target.value.toUpperCase() });
  }
  
  clientNameChange = (evt) => {
	    this.setState({ clientName: evt.target.value.toUpperCase() });
  }
  
  clientDocNumberChange = (evt) => {
    this.setState({ clientDocNumber: evt.target.value.trim() });
    
    if (evt.target.value == '0' && this.state.selectedOption == 'boleta') {
    	this.setState({ clientName: 'CLIENTES VARIOS' });
    }
  }
  
  invoiceNumberModifiedDispChange = (evt) => {
	    this.setState({ invoiceNumberModifiedDisp: evt.target.value });
	    
	    var pad = "00000000";
	    var n = evt.target.value;
	    var valueWithZeros = (pad+n).slice(-pad.length);
	    
	    if (this.state.invoiceTypeModifiedToggle) {
	    	this.setState({ invoiceNumberModified: "F001-" + valueWithZeros });
	    	this.setState({ invoiceNumber: "F001-XXXXXXXX" });
	    } else {
	    	this.setState({ invoiceNumberModified: "B001-" + valueWithZeros });
	    	this.setState({ invoiceNumber: "B001-XXXXXXXX" });
	    }
  }
  
  truckPlateNumberChange = (evt) => {
    this.setState({ truckPlateNumber: evt.target.value.toUpperCase()});
  }
  
  _toggleError = () => {
	  this.setState((prevState, props) => {
		  return {showError: !prevState.showError}
	  })
  }
  
  handleOptionChange = (changeEvent) => {
	  this.setState({selectedOption: changeEvent.target.value});
	  
	  var cssB = (this.state.showBoletaRadioButton === "btn btn-default btn-sm" && changeEvent.target.value == 'boleta') ? "btn blue active btn-sm" : "btn btn-default btn-sm";
	  this.setState({showBoletaRadioButton: cssB});
	  var cssF = (this.state.showFacturaRadioButton === "btn btn-default btn-sm" && changeEvent.target.value == 'factura') ? "btn blue active btn-sm" : "btn btn-default btn-sm";
	  this.setState({showFacturaRadioButton: cssF});
	  var cssNV = (this.state.showNotaDeCreditoRadioButton === "btn btn-default btn-sm" && changeEvent.target.value == 'nota de credito') ? "btn blue active btn-sm" : "btn btn-default btn-sm";
	  this.setState({showNotaDeCreditoRadioButton: cssNV});
	  
	  if (changeEvent.target.value == 'boleta') {
		  var docLabelObj = {
	    	  clientDocType: 'DNI',
	    	  clientDocTypePH: 'Ingrese DNI',
	    	  clientName: 'Señor(es)',
	    	  clientNamePH: 'Nombre(s)',
	      }
	  	this.setState({docLabelObj: docLabelObj});
		this.setState({clientDocType: '1'});
		this.setState({invoiceType: '03'});
		this.setState({invoiceNumber: 'B001-XXXXXXXX'});
		this.setState({invoiceTypeModified: ''});
		this.setState({invoiceNumberModified: ''});
		this.setState({invoiceNumberModifiedDisp: ''});
		this.setState({dateOfInvoiceModified: new Date()});
		this.setState({igvModified: ''});
		this.setState({totalModified: ''});
		this.setState({clientDocNumber: '', clientName: '', clientAddress: '', clientEmailAddress: '', truckPlateNumber: ''});

	  } else if (changeEvent.target.value == 'factura') {
		  var docLabelObj = {
	    	  clientDocType: 'RUC',
	    	  clientDocTypePH: 'Ingrese RUC',
	    	  clientName: 'Razón Social',
	    	  clientNamePH: 'Nombre(s)',
	      }
	  	this.setState({docLabelObj: docLabelObj});
		this.setState({clientDocType: '6'});
		this.setState({invoiceType: '01'});
		this.setState({invoiceNumber: 'F001-XXXXXXXX'});
		this.setState({invoiceTypeModified: ''});
		this.setState({invoiceNumberModified: ''});
		this.setState({invoiceNumberModifiedDisp: ''});
		this.setState({dateOfInvoiceModified: new Date()});
		this.setState({igvModified: ''});
		this.setState({totalModified: ''});
		this.setState({clientDocNumber: '', clientName: '', clientAddress: '', clientEmailAddress: '', truckPlateNumber: ''});

	  } else if (changeEvent.target.value == 'nota de credito') {
		  var docLabelObj = {
	    	  clientDocType: 'RUC',
	    	  clientDocTypePH: 'Ingrese Doc',
	    	  clientName: 'Señor(es)',
	    	  clientNamePH: 'Nombre(s)',
	      }
	  	this.setState({docLabelObj: docLabelObj});
		this.setState({clientDocType: '6'});
		this.setState({invoiceType: '07'});
		this.setState({invoiceNumber: 'F001-XXXXXXXX'});
		this.setState({invoiceTypeModified: '01'});
		this.setState({invoiceNumberModified: ''});
		this.setState({invoiceNumberModifiedDisp: ''});
		this.setState({dateOfInvoiceModified: new Date()});
		this.setState({igvModified: ''});
		this.setState({totalModified: ''});
		this.setState({clientDocNumber: '', clientName: '', clientAddress: '', clientEmailAddress: '', truckPlateNumber: ''});
	  }
  }
  
  _fetchGasPrices(timeframe){
		
	  	var search = {};
	  	var self = this;
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
				if (data.result.length == 1) {
					var gasPricesVo = data.result[0];
					var currentDate = new Date();
					
					this.setState({priceD2: gasPricesVo.gasPrices[0].price});
					this.setState({priceG90: gasPricesVo.gasPrices[1].price});
					this.setState({priceG95: gasPricesVo.gasPrices[2].price});
					this.setState({gasPrices: gasPricesVo.gasPrices});
					this.setState({date: currentDate});
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
  
  componentWillMount(){
	  this._fetchGasPrices({dateEnd: "latest", dateBeg: ""});
  }
  
  onKeyPress(event) {
	if (event.which === 13 ) {
		
		if (event.target.name == "clientDocNumber") {
			this.onTabPress(event);
			if (this.state.selectedOption != 'boleta') {
				const form = event.target.form;
				const index = Array.prototype.indexOf.call(form, event.target);
				form.elements[index+3].focus();
				event.preventDefault();
			} else {
				const form = event.target.form;
				const index = Array.prototype.indexOf.call(form, event.target);
				form.elements[index+2].focus();
				event.preventDefault();
			}
		} else {
			if (event.target.form) {
				const form = event.target.form;
				const index = Array.prototype.indexOf.call(form, event.target);
				form.elements[index+1].focus();
				event.preventDefault();
			}
		}

	}
  }
  
  _invoiceTypeHandleClick(){
	this.setState({invoiceTypeModifiedToggle: !this.state.invoiceTypeModifiedToggle});
	
	if (!this.state.invoiceTypeModifiedToggle) {
		this.setState({invoiceType: "07"});
		this.setState({invoiceTypeModified: "01"});
		this.setState({ invoiceNumber: "F001-XXXXXXXX" });
	} else {
		this.setState({invoiceType: "07"});
		this.setState({invoiceTypeModified: "03"});
		this.setState({ invoiceNumber: "B001-XXXXXXXX" });
	}
  }
  
  _bonusButtonHandleClick(){
		this.setState({hasBonus: !this.state.hasBonus});
		
		if (!this.state.hasBonus) {
			this.setState({cssBonusButton: 'btn green-meadow'});
		} else {
			this.setState({cssBonusButton: 'btn btn-default'});
		}
  }
  
  motiveCdHandleChange(event) {
	  this.setState({motiveCd: event.target.value});

	  var index = event.nativeEvent.target.selectedIndex;
	  this.setState({motiveCdDescription: event.nativeEvent.target[index].text.toUpperCase()});
  }
  
  onTabPress(event) {
	
	this.setState({ showError: false});
	
	var self = this; 
	var search = {};
	search["docId"] = event.target.value;
	
	if (this.state.selectedOption != 'boleta') {
		
		if (event.target.value && event.target.value >=0 &&  event.target.value.length == 11) {
			// display RUC search loading
			self.setState({loadingGif: true});
			self.setState({clientNameDisabled: false});
			self.setState({clientAddressDisabled: false});
			
			jQuery.ajax({
				type: "POST",
				contentType: "application/json", 
				url:"/api/findRuc",
				data: JSON.stringify(search),
				datatype: 'json',
				cache: false,
				timeout: 600000,
				success: (data) => {
					
					// When no RUC found
					var isDataEmpty = true;
					for(var prop in data) {
				        if(data.hasOwnProperty(prop))
				        	isDataEmpty = false;
				    }
					
					if (isDataEmpty || !data.result.status) {
						var errors = {
				    		submit: 'Numero de RUC no econtrado.'
					    };
						this.setState({errors: errors, clientName: '', clientAddress: ''}); 
						self._toggleError();
						
						setTimeout(function() {
						    ReactDOM.findDOMNode(self.refs['refResultModal']).focus();
				        }.bind(this), 0);
					} else {
						self.setState({clientAddress: data.result.direccionS});
						self.setState({clientName: data.result.razonSocial});
						self.setState({clientEmailAddress: data.result.correoElectronico});
						self.setState({clientNameDisabled: true});
						if (data.result.direccionS.trim() == "" || data.result.direccionS.trim() == "-") {
							self.setState({clientAddressDisabled: false});
						} else {
							self.setState({clientAddressDisabled: true});
						}
						
					}
					
					// hide delay delay
					self.setState({loadingGif: false});
				},
				error: function(e){
					self.setState({loadingGif: false});
				}	
			});
		} else {
			var errors = {
	    		submit: 'Numero de RUC es incorrecto.'
		    };
			this.setState({errors: errors, clientName: '', clientAddress: '', clientNameDisabled: false, clientAddressDisabled: false}); 
			self._toggleError();
			
			setTimeout(function() {
			    ReactDOM.findDOMNode(self.refs['refResultModal']).focus();
	        }.bind(this), 0);
		}
		
	} else {
		if (event.target.value && event.target.value > 0 &&  event.target.value.length == 8) {
			// display RUC search loading
			self.setState({loadingGif: true});
			self.setState({clientNameDisabled: false});
			self.setState({clientAddressDisabled: false});
			
			jQuery.ajax({
				type: "POST",
				contentType: "application/json", 
				url:"/api/findDni",
				data: JSON.stringify(search),
				datatype: 'json',
				cache: false,
				timeout: 600000,
				success: (data) => {
					
					// When no RUC found
					var isDataEmpty = true;
					for(var prop in data) {
				        if(data.hasOwnProperty(prop))
				        	isDataEmpty = false;
				    }
					
					if (isDataEmpty || !data.result.status) {
						var errors = {
				    		submit: 'Numero de DNI no econtrado.'
					    };
						this.setState({errors: errors, clientName: '', clientAddress: ''}); 
						self._toggleError();
						
						setTimeout(function() {
							ReactDOM.findDOMNode(self.refs['refResultModal']).focus();
					    }.bind(this), 0);
					} else {
						self.setState({clientName: data.result.paterno + " " + data.result.materno + " " + data.result.nombre});
						self.setState({clientEmailAddress: data.result.correoElectronico});
						self.setState({clientNameDisabled: true});
						self.setState({clientAddressDisabled: false});
					}
					
					// hide delay delay
					self.setState({loadingGif: false});
				},
				error: function(e){
					self.setState({loadingGif: false});
				}	
			});
		} else if (event.target.value == "0") { 
			self.setState({clientNameDisabled: true});
			self.setState({clientAddressDisabled: false});
			return;
		} else {
			var errors = {
	    		submit: 'Numero de DNI es incorrecto.'
		    };
			this.setState({errors: errors, clientName: '', clientAddress: '', clientNameDisabled: false, clientAddressDisabled: false}); 
			self._toggleError();
			
			setTimeout(function() {
				ReactDOM.findDOMNode(self.refs['refResultModal']).focus();
		    }.bind(this), 0);
		}
	}
	
  }
  
  invoiceSearch(event) {
	  	
	  	var self = this;
	  	var search = {};
	  	self.setState({ showError: false, clientDocNumberDisabled: false, clientNameDisabled: false, clientAddressDisabled: false});
	    
	    search["invoiceNumber"] = self.state.invoiceNumberModified;
	    self.setState({loadingGif: true});
	    
		$.ajax({
			type: "POST",
			contentType: "application/json", 
			url:"/api/findInvoice",
			data: JSON.stringify(search),
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: function(data) {
				
			  if (data.result[0].clientDocNumber) {	
				  self.setState({
					  clientDocNumber: data.result[0].clientDocNumber,
					  clientName: data.result[0].clientName,
					  clientDocType: data.result[0].clientDocType,
					  clientAddress: data.result[0].clientAddress,
					  truckPlateNumber: data.result[0].truckPlateNumber,
					  dateOfInvoiceModified: data.result[0].date,
					  galsD2: data.result[0].galsD2,
					  galsG90: data.result[0].galsG90,
					  galsG95: data.result[0].galsG95,
					  priceD2: data.result[0].priceD2,
					  priceG90: data.result[0].priceG90,
					  solesD2: data.result[0].solesD2,
					  solesG90: data.result[0].solesG90,
					  solesG95: data.result[0].solesG95,
					  subTotal: data.result[0].subTotal,
					  discount: data.result[0].discount,
					  totalIGV: data.result[0].totalIGV,
					  total: data.result[0].total,
					  electronicPmt: data.result[0].electronicPmt,
					  cashPmt: data.result[0].cashPmt,
					  cashGiven: data.result[0].cashGiven,
					  change: data.result[0].change,
					  clientDocNumberDisabled: true,
					  clientNameDisabled: true, 
					  clientAddressDisabled: true,
					  invoiceTypeModified: data.result[0].invoiceType,
					  igvModified: data.result[0].totalIGV,
					  totalModified: data.result[0].total,
					  hasBonus: data.result[0].hasBonus,
					  cssBonusButton: data.result[0].hasBonus == true ? 'btn green-meadow': 'btn btn-default'
				  });
				  
			  } else {
			  	  var errors = {
		  			  submit: 'Numero de Comprobante no fue encontrado o es incorrecto.'
			  	  };
			  	  self.setState({errors: errors, clientDocNumber: '', clientName: '', clientAddress: '', clientDocNumberDisabled: false, clientNameDisabled: false, clientAddressDisabled: false}); 
			  	  self._toggleError();
			  	  
				  setTimeout(function() {
					  ReactDOM.findDOMNode(self.refs['refResultModal']).focus();
			      }.bind(this), 0);
			  }
			  
			  self.setState({loadingGif: false});
		
			}, error: function(e){
				console.log("ERROR: ", e);
				self.setState({loadingGif: false});
			}	
		});
	  
  }
  
  handleEmailModalCancel() {
	  this.setState({ showEmailModal: false });
  }
  
  handleResultModalCancel() {
	  this.setState({ showError: false, showSuccess: false });
  }
  
  handleEmailModalClose() {
	    var self = this;
		var search = {};
		var formIsValid = true;
		
		var errors = {
	    		submit: '',
	    };
	    
  
		self.setState({ showEmailModal: false });
		
		if (!self.state.clientEmailAddress.trim()) {
	    	errors["submit"] = "Email no puede estar vacio";
			formIsValid = false;
			self.setState({errors: errors, showError: true, showSuccess: false}); 
	    }
	    
		if (formIsValid) {
		    search["invoiceNumber"] = self.state.invoiceNumber;
			search["selectedOption"] = self.state.selectedOption;
			search["clientEmailAddress"] = self.state.clientEmailAddress;
			search["clientDocNumber"] = self.state.clientDocNumber;
			search["clientDocType"] = self.state.clientDocType;
			
			self.setState({emailingGif: true});
			
			$.ajax({
				type: "POST",
				contentType: "application/json", 
				url:"/api/emailInvoice",
				data: JSON.stringify(search),
				datatype: 'json',
				cache: false,
				timeout: 500,
				success: function(data) {
					
				  if (data.result == '1') {	
					  self.setState({ sunatErrorStr: data.msg });
					  self.setState({ showSuccess: true });
				  } else {
				  	  var errors = {
			  			  submit: data.msg
				  	  };
				  	  self.setState({errors: errors}); 
				  	  self._toggleError();
				  }
				  
				  self.setState({emailingGif: false});
			
				}, error: function(e){
					
					if (e.statusText == "timeout") {
						self.setState({ sunatErrorStr: "Correo electrónico sera enviado en unos minutos" });
						self.setState({ showSuccess: true });
						self.setState({ emailingGif: false});
						
						setTimeout(function() {
						    ReactDOM.findDOMNode(self.refs['refResultModal']).focus();
				        }.bind(this), 0); 
					} else {
						console.log("ERROR: ", e);
					}
					
				}	
			});
		}
  }
  
  emailInvoice = () => {
	var self = this;
	self.setState({ showError: false, showSuccess: false, showEmailModal: true });
	
  }
  
  newInvoice = () => {
	  
	  $('canvas').remove();
      $("img[alt$='Scan me!']").remove();
  
	  this.setState({
		  errors: {},
		  showError: false,
		  showSuccess: false,
		  invoiceNumber: 'B001-XXXXXXXX',
		  // customer
	      clientDocNumber: '',
	      clientName: '',
	      clientDocType: '1',
	      clientAddress: '',
	      clientEmailAddress: '',
	      truckPlateNumber: '',
	      clientDocNumberDisabled: false,
	      clientNameDisabled: false,
	      clientAddressDisabled: false,
		  // invoice breakdown
	      date: new Date(),
	      invoiceType: '03',
		  // Break-down
	      galsD2: '',
	      galsG90: '',
	      galsG95: '',
//	      priceD2: '',
//	      priceG90: '',
//	      priceG95: '',
	      solesD2: '',
	      solesG90: '',
	      solesG95: '',
	      // Payment
	  	  subTotal: '',
		  discount: '',
		  totalIGV: '',
		  total: '',
		  electronicPmt: '',
		  cashPmt: '',
		  cashGiven: '',
		  change: '',
	      // Save or update in DB
	      gasPrices: [],
	      totalVerbiage: '',
	      invoiceHash: '',
	      saveOrUpdate: 'save',
	      sunatErrorStr: '',
	      status: '',
	      selectedOption: 'boleta',
	      boletaDisabled: false,
	      facturaDisabled: false,
	      notaDeCreditoDisabled: false,
	      submitDisabled: false,
	      showBoletaRadioButton: 'btn blue active btn-sm',
	      showFacturaRadioButton: 'btn btn-default btn-sm',
	      showNotaDeCreditoRadioButton: 'btn btn-default btn-sm',
	      docLabelObj: {
	    	  clientDocType: 'DNI',
	    	  clientDocTypePH: 'Ingrese DNI',
	    	  clientName: 'Señor(es)',
	    	  clientNamePH: 'Nombre(s)',
	      },
	      loadingGif: false,
	      invoiceTypeModified: '',
	      invoiceNumberModified: '',
	      invoiceNumberModifiedDisp: '',
	      invoiceTypeModifiedToggle: true,
	      dateOfInvoiceModified: new Date(),
	      motiveCd: '',
	      motiveCdDescription: '',
	      igvModified: '',
	      totalModified: '',
	      hasBonus: false
	  });
  }
  
  handleSubmit = (evt) => {
		
		evt.preventDefault();
		
		const {subTotal, 
			discount, 
			totalIGV, 
			total, 
			electronicPmt, 
			cashPmt, 
			cashGiven, 
			change, 
			invoiceTypeModified, 
			invoiceNumberModified, 
			motiveCd, 
			motiveCdDescription,
			dateOfInvoiceModified,
			igvModified,
			totalModified,
			invoiceNumber, 
			invoiceType, 
			clientDocType, 
			clientAddress, 
			clientDocNumber, 
			clientName, 
			truckPlateNumber, 
			galsD2, 
			galsG90, 
			galsG95, 
			priceD2, 
			priceG90, 
			priceG95, 
			solesD2, 
			solesG90,
			solesG95,
			gasPrices,
			date,
			hasBonus} = this.state;
		var self = this;
	    var errors = {
	    		submit: '',
	    		clientDocNumber: '',
	    		clientName: '',
	    		clientAddress: '',
	    		truckPlateNumber: ''
	    };
	    var formIsValid = true;
	    this.setState({ showError: false, showSuccess: false });
	    
	    var invoiceVo = {
	    	invoiceNumber: invoiceNumber,  
		    clientDocNumber: clientDocNumber,
		    clientName: clientName,
		    clientDocType: clientDocType, // RUC
		    clientAddress: clientAddress,
		    truckPlateNumber: truckPlateNumber,
		    date: date,
	    	invoiceType: invoiceType, // factura
		    galsD2: galsD2,
		    galsG90: galsG90,
		    galsG95: galsG95,
		    priceD2: priceD2,
		    priceG90: priceG90,
		    priceG95: priceG95,
		    solesD2: solesD2,
		    solesG90: solesG90,
		    solesG95: solesG95,
		    subTotal: subTotal,
		    discount: (discount || 0),
		    totalIGV: totalIGV,
		    total: total,
		    electronicPmt: (electronicPmt || 0),
		    cashPmt: cashPmt,
		    cashGiven: (cashGiven || 0),
		    change: change ,
		    invoiceTypeModified: invoiceTypeModified,
		    invoiceNumberModified: invoiceNumberModified,
		    motiveCd: motiveCd,
		    motiveCdDescription: motiveCdDescription,
		    dateOfInvoiceModified: dateOfInvoiceModified,
		    igvModified: igvModified,
			totalModified: totalModified,
	    	saveOrUpdate: 'save',
	    	hasBonus: hasBonus
	    };

	    if (solesD2 || solesG90 || solesG95) {
	    	
    	} else {
	    	errors["submit"] = "Cantidades no pueden ser nulas";
			formIsValid = false;
	    }
	    
	    if (((electronicPmt || 0) +  (cashGiven || 0)) >= total) {
	    	
    	} else {
	    	errors["submit"] = "Pagos por Tarjeta Crédito/Débito y Efectivo son menor al Importe Total";
			formIsValid = false;
	    }
	    
	    if (formIsValid){
		    // RUC Validation
		    if (clientDocType == '6') {
			    if (clientDocNumber && clientDocNumber >= 0) {
		    		if (clientDocNumber.toString().length  != 11) {
		    			errors["clientDocNumber"] = "RUC debe tener 11 digitos";
		    			formIsValid = false;
		    		}
			    } else {
			    	errors["clientDocNumber"] = "Falta RUC o DNI";
					formIsValid = false;
			    }
		    }
		    
		    // DNI Validation
		    if (clientDocType == '1') {
			    if (clientDocNumber == '0') {
			    	if (((solesD2 || 0) + (solesG90 || 0) + (solesG95 || 0)) >= 700.00) {
		    			errors["clientDocNumber"] = "DNI no puede ser 0, proveer DNI para compras mayores a S/ 700.00 ";
		    			formIsValid = false;
		    		} 
			    } else {
			    	if (clientDocNumber && clientDocNumber >= 0) {
			    		if (clientDocNumber.toString().length  != 8) {
			    			errors["clientDocNumber"] = "DNI debe tener 8 digitos";
			    			formIsValid = false;
			    		} 
				    } else {
				    	errors["clientDocNumber"] = "Numero de DNI incompleto";
						formIsValid = false;
				    }
			    }
		    }
		    
		    // Validation of Razon Social, Direccion, Nro de Placa 
		    if (clientDocType == '6') {
		    	if (clientName && clientName.trim().length >= 0) {
		    		
		    	} else {
		    		errors["clientName"] = "Falta razon social asociado al RUC " + clientDocNumber;
					formIsValid = false;
		    	}
	
		    	if (clientAddress && clientAddress.trim().length >= 0) {
		    		
		    	} else {
		    		errors["clientAddress"] = "Falta direccion asociado al RUC " + clientDocNumber;
					formIsValid = false;
		    	}
		    	if (truckPlateNumber && truckPlateNumber.trim().length >= 0) {
		    		
		    	} else {
		    		errors["truckPlateNumber"] = "Falta placa de vehículo asociado al RUC " + clientDocNumber;
					formIsValid = false;
		    	}
		    } 
		    
		    // Validation of product list
		    if (!galsD2) {
		    	invoiceVo.galsD2 = 0;
		    	invoiceVo.solesD2 = 0;
		    }
		    if (!galsG90) {
		    	invoiceVo.galsG90 = 0;
		    	invoiceVo.solesG90 = 0;
		    }
		    if (!galsG95) {
		    	invoiceVo.galsG95 = 0;
		    	invoiceVo.solesG95 = 0;
		    }
		    if (!igvModified) {
		    	invoiceVo.igvModified = 0;
		    }
		    if (!totalModified) {
		    	invoiceVo.totalModified = 0;
		    }
	    }
		this.setState({errors: errors}); 
		
		if (formIsValid) {
			
			self.setState({emailingGif: true});
			
			jQuery.ajax({
				type: "POST",
				contentType: "application/json", 
				url:"/api/submitInvoiceVo",
				data: JSON.stringify(invoiceVo),
				datatype: 'json',
				cache: false,
				timeout: 600000,
				success: (data) => {
					var invoiceVoResp = data.result[0];
					
					if (invoiceVoResp.sunatErrorStr.charAt(0) == "1" && invoiceVoResp.status == '1') {
						self.setState({ totalVerbiage: invoiceVoResp.totalVerbiage });
						self.setState({ invoiceHash: invoiceVoResp.invoiceHash });
						/*self.setState({ sunatErrorStr: invoiceVoResp.sunatErrorStr });*/
						self.setState({ sunatErrorStr: data.msg });
						self.setState({ status: invoiceVoResp.status });
						self.setState({ invoiceNumber: invoiceVoResp.invoiceNumber });
						
						// Prevent user from changing invoice type after submission
						if (this.state.selectedOption == 'boleta') {
							self.setState({ notaDeCreditoDisabled: true });
							self.setState({ facturaDisabled: true });
						} else if (this.state.selectedOption == 'factura') {
							self.setState({ notaDeCreditoDisabled: true });
							self.setState({ boletaDisabled: true });
						} else if (this.state.selectedOption == 'nota de credito') {
							self.setState({ facturaDisabled: true });
							self.setState({ boletaDisabled: true });
						}
					
						var qrcode1 = new QRCode("qrcode1");
						qrcode1.clear();
						qrcode1.makeCode("20501568776|" + 
								invoiceVoResp.invoiceType + "|" + 
								invoiceVoResp.invoiceNumber.substring(0, 4) + "|" + 
								invoiceVoResp.invoiceNumber.substring(5, invoiceVoResp.invoiceNumber.length) + "|" +
								invoiceVoResp.totalIGV + "|" +
								invoiceVoResp.total + "|" +
								moment(invoiceVoResp.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A') + "|" + 
								invoiceVoResp.clientDocType + "|" +
								invoiceVoResp.clientDocNumber
								);
						
						var qrcode2 = new QRCode("qrcode2");
						qrcode2.clear();
						qrcode2.makeCode("20501568776|" + 
								invoiceVoResp.invoiceType + "|" + 
								invoiceVoResp.invoiceNumber.substring(0, 4) + "|" + 
								invoiceVoResp.invoiceNumber.substring(5, invoiceVoResp.invoiceNumber.length) + "|" +
								invoiceVoResp.totalIGV + "|" +
								invoiceVoResp.total + "|" +
								moment(invoiceVoResp.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A') + "|" + 
								invoiceVoResp.clientDocType + "|" +
								invoiceVoResp.clientDocNumber
								);
						var qrcode3 = new QRCode("qrcode3");
						qrcode3.clear();
						qrcode3.makeCode("20501568776|" + 
								invoiceVoResp.invoiceType + "|" + 
								invoiceVoResp.invoiceNumber.substring(0, 4) + "|" + 
								invoiceVoResp.invoiceNumber.substring(5, invoiceVoResp.invoiceNumber.length) + "|" +
								invoiceVoResp.totalIGV + "|" +
								invoiceVoResp.total + "|" +
								moment(invoiceVoResp.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A') + "|" + 
								invoiceVoResp.clientDocType + "|" +
								invoiceVoResp.clientDocNumber
								);
						self.setState({emailingGif: false});
						self.setState({showSuccess: true });
						self.setState({submitDisabled: true});
						
						setTimeout(function() {
						    ReactDOM.findDOMNode(self.refs['refResultModal']).focus();
				        }.bind(this), 0); 
					} else {
						errors["submit"] = "Recibo rechazado por Sunat. " + invoiceVoResp.sunatErrorStr;
						self._toggleError();
						self.setState({emailingGif: false});
						
						setTimeout(function() {
						    ReactDOM.findDOMNode(self.refs['refResultModal']).focus();
				        }.bind(this), 0); 
					}
				},
				error: function(e){
					errors["submit"] = "Recibo no aceptado. Intente otra vez. "  + e.responseText;
					self._toggleError();
					self.setState({emailingGif: false});
					
					setTimeout(function() {
					    ReactDOM.findDOMNode(self.refs['refResultModal']).focus();
			        }.bind(this), 0); 
				}	
			});
		} else {
			
			self._toggleError();
			
		    setTimeout(function() {
			    ReactDOM.findDOMNode(self.refs['refResultModal']).focus();
	        }.bind(this), 0);  
			
		}

	  }
  
  render() {    
	  
	  let buttonText = 'FACTURA';
	  let invoicePrefix = 'F001-';
	  if (!this.state.invoiceTypeModifiedToggle) {
			buttonText = 'BOLETA';
			invoicePrefix = 'B001-';
	  }
	  
	  let bonusButtonText = 'Sin Bonus';
	  if (this.state.hasBonus) {
		  bonusButtonText = 'Con Bonus';
	  }
	  
	  return (
			  
      <form onSubmit={this.handleSubmit}>
      	  
	      {/*{this.state.showError && 
		        <div className="alert alert-danger">
	      <strong>¡Error!</strong>{" " + this.state.errors.submit + " - " + this.state.errors.clientName + " - " + this.state.errors.clientDocNumber + " - " + this.state.errors.clientAddress + " - " + this.state.errors.truckPlateNumber}  
		      	</div>
	      }

	      {this.state.showSuccess && 
	      	<div className="alert alert-success">
	      		<strong>Éxito!</strong> {this.state.sunatErrorStr}
	      	</div>
	      }*/}
	      
	      <h1 className="page-title col-xs-12 col-md-12 uppercase bold margin-bottom-10" style={{fontSize: "20px"}}> {this.state.selectedOption} ELECTRÓNICA {this.state.invoiceNumber}</h1>
	      
	      <div className="invoice margin-bottom-20">
	          {/*<div className="row invoice-logo">
	          	  {<div className="col-md-4 invoice-logo-space hidden-xs">
	          	   <img src="../assets/pages/media/invoice/lajoya.png" className="img-responsive" alt="" /> 
	          	  </div>}
	          	  {<div className="col-xs-6 col-md-4 invoice-logo-space">
			          <ul className="list-unstyled">
		                  <li><strong>  La Joya de Santa Isabel EIRL </strong></li>
		                  <li> Av. Miguel Grau Mza B Lote 1-2 </li>
		                  <li> Lima - Lima - Ate </li>
		                  <li> +51 356 0345 </li>
		              </ul>
		          </div>}
	              <div className="col-xs-6 col-md-4">
	                  <p> <strong className="uppercase ">{this.state.selectedOption} ELECTRÓNICA</strong> <br/>
	                      <span className="muted"> RUC: 20501568776  </span><br/>
	                      <span className="muted bold"> {this.state.invoiceNumber}  </span>
	                  </p>
	              </div>
	          </div>*/}
	          {/*<div className="row">
	              <div className="col-md-12">
	                  <ul className="list-unstyled">
	                      <li><strong>  La Joya de Santa Isabel EIRL </strong></li>
	                      <li> Av. Miguel Grau Mza B Lote 1-2 </li>
	                      <li> Lima - Lima - Ate </li>
	                      <li> +51 356 0345 </li>
	                  </ul>
	              </div>
              </div>*/}
              <div className="portlet-body form">
        		
	              <div className="row">
		              <div className="col-md-6 col-xs-12">
			              <div className="form-group">
			                  <label className="control-label">Tipo de Comprobante</label>
		    	              <div className="clearfix">
					    	      <div className="btn-group">
			                      <label className={this.state.showBoletaRadioButton}>
			                          <input type="radio" value="boleta" disabled={this.state.boletaDisabled} className="toggle" checked={this.state.selectedOption === 'boleta'} onChange={this.handleOptionChange}/> BOLETA </label>
			                      <label className={this.state.showFacturaRadioButton}>
			                          <input type="radio" value="factura" disabled={this.state.facturaDisabled} className="toggle" checked={this.state.selectedOption === 'factura'} onChange={this.handleOptionChange}/> FACTURA </label>
			                      <label className={this.state.showNotaDeCreditoRadioButton}>
			                          <input type="radio" value="nota de credito" disabled={this.state.notaDeCreditoDisabled} className="toggle" checked={this.state.selectedOption === 'nota de credito'} onChange={this.handleOptionChange}/> NOTA DE CRÉDITO </label>
			                      </div>
		                      </div>
		                  </div>
			          </div>
			          <div className="col-md-2">
				          <div className="form-group">
		            	  	<label className="control-label">Seleccionar</label><br></br>  
		            	  	<a onClick={this._bonusButtonHandleClick.bind(this)} className={this.state.cssBonusButton} >{bonusButtonText}</a>
		                  </div>
		              </div>
	              </div>
	              {this.state.selectedOption == 'nota de credito' &&
	              <div className="row">
		              <div className="col-md-2">
				          <div className="form-group">
		            	  	<label className="control-label">{invoicePrefix}</label><br></br>  
		            	  	<a onClick={this._invoiceTypeHandleClick.bind(this)} className="btn green-meadow">{this.state.invoiceTypeModifiedToggle} {!this.state.invoiceTypeModifiedToggle} {buttonText}</a>
		                  </div>
		              </div>
		              <div className="col-md-2">
	    	              <div className="form-group">
	    	              	  <table style={{width:'30%'}}>
		  	                  	<tbody>
		  	                  	  <tr>
		    	                    <td><label className="control-label">Nro</label></td>
		    	                    <td>{this.state.loadingGif &&
		  	    	                  <img src="../assets/global/plugins/plupload/js/jquery.ui.plupload/img/loading.gif" className="img-responsive" alt="" />}</td> 
		    	                  </tr>
		    	                </tbody>
		    	              </table>
	    	                  <input type="number" pattern="[0-9]*" className="form-control" style={{borderColor: '#26344b'}} placeholder={"Nro Comprobante"} onBlur={this.invoiceSearch.bind(this)} onKeyPress={this.onKeyPress} inputMode="numeric"  value={this.state.invoiceNumberModifiedDisp} onChange={this.invoiceNumberModifiedDispChange}/>
		                  </div>
	    	          </div>
		    	      <div className="col-md-2">
			              <div className="form-group">
			                  <label className="control-label">Fecha Comprobante Referencia</label>
			                  <input type="text" id="lastName" className="form-control" placeholder="Fecha y Hora" value={`${moment(this.state.dateOfInvoiceModified).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}  readOnly/>
			              </div>
			          </div>
	    	          <div className="col-md-2">
	    	              <div className="form-group">
	    	                  <label className="control-label">Seleccione Motivo</label>
	    	                  <select className="ticket-assign form-control input-medium" style={{borderColor: '#26344b'}} value={this.state.motiveCd} onChange={this.motiveCdHandleChange.bind(this)}>
	    	                  	  <option value=""></option>
	    	                  	  <option value="01">Anulacion de la Operacion</option>
	    	                  	  <option value="02">Anulacion por error en el RUC</option>
	    	                  	  <option value="03">Correccion por error en la descripcion</option>
	    	                  	  <option value="04">Descuento global</option>
	    	                  	  <option value="05">Descuento por item</option>
	    	                  	  <option value="06">Devolucion total</option>
	    	                  	  <option value="07">Devolucion por item</option>
	    	                  	  <option value="08">Bonificacion</option>
	    	                  	  <option value="09">Disminucion en el valor</option>
	    	                  	  <option value="10">Otros conceptos</option>
			                  </select>
	    	              </div>
	    	          </div>
	              </div>
	              }
              	  <div className="row">
		    	      <div className="col-md-2">
			              <div className="form-group">
			                  <label className="control-label">Fecha</label>
			                  <input type="text" id="lastName" className="form-control" placeholder="Fecha y Hora" value={`${moment(this.state.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}  readOnly/>
			              </div>
			          </div>
	    	      	  <div className="col-md-2">
	    	              <div className="form-group">
	    	                  <table style={{width:'30%'}}>
	    	                  	<tbody>
	    	                  	  <tr>
		    	                    <td><label className="control-label">{this.state.docLabelObj.clientDocType}</label></td>
		    	                    <td>{this.state.loadingGif &&
		  	    	                  <img src="../assets/global/plugins/plupload/js/jquery.ui.plupload/img/loading.gif" className="img-responsive" alt="" />}</td> 
		    	                  </tr>
		    	                </tbody>
		    	              </table>
	    	                  <input name="clientDocNumber" type="text" pattern="[0-9]*" className="form-control" style={{borderColor: '#26344b'}} disabled={this.state.clientDocNumberDisabled} placeholder={this.state.docLabelObj.clientDocTypePH} onBlur={this.onTabPress.bind(this)} onKeyPress={this.onKeyPress.bind(this)} value={this.state.clientDocNumber} onChange={this.clientDocNumberChange}/>
	                	  </div>
	    	          </div>
	    	          <div className="col-md-2">
	    	              <div className="form-group">
	    	                  <label className="control-label">{this.state.docLabelObj.clientName}</label>
	    	                  <input type="text" className="form-control" style={{borderColor: '#26344b'}} disabled={this.state.clientNameDisabled} placeholder={this.state.docLabelObj.clientNamePH} onKeyPress={this.onKeyPress} value={this.state.clientName} onChange={this.clientNameChange}/>
	    	              </div>
	    	          </div>
	              
	              
	    	          <div className="col-md-4">
	    	              <div className="form-group">
	    	                  <label className="control-label">Dirección</label>
	    	                  <input type="text" className="form-control" style={{borderColor: '#26344b'}} disabled={this.state.clientAddressDisabled} placeholder="Dirección" onKeyPress={this.onKeyPress} value={this.state.clientAddress} onChange={this.clientAddressChange}/>
	    	              </div>
	    	          </div>
	    	          <div className="col-md-2">
	    	              <div className="form-group">
	    	                  <label className="control-label">Nro de Placa: </label>
	    	                  <input type="text" className="form-control" style={{borderColor: '#26344b'}} placeholder="Ingrese placa" onKeyPress={this.onKeyPress} value={this.state.truckPlateNumber} onChange={this.truckPlateNumberChange}/>
	    	              </div>
	    	          </div>
		          </div>
              </div>
             {/* <div className="row">
	              <div className="col-xs-4">
	                  <h3>About:</h3>
	                  <ul className="list-unstyled">
	                      <li> Drem psum dolor sit amet </li>
	                      <li> Laoreet dolore magna </li>
	                      <li> Consectetuer adipiscing elit </li>
	                      <li> Magna aliquam tincidunt erat volutpat </li>
	                      <li> Olor sit amet adipiscing eli </li>
	                      <li> Laoreet dolore magna </li>
	                  </ul>
	              </div>
	              <div className="col-xs-4 invoice-payment">
	                  <h3>Payment Details:</h3>
	                  <ul className="list-unstyled">
	                      <li>
	                          <strong>V.A.T Reg #:</strong> 542554(DEMO)78 </li>
	                      <li>
	                          <strong>Account Name:</strong> FoodMaster Ltd </li>
	                      <li>
	                          <strong>SWIFT code:</strong> 45454DEMO545DEMO </li>
	                      <li>
	                          <strong>Account Name:</strong> FoodMaster Ltd </li>
	                      <li>
	                          <strong>SWIFT code:</strong> 45454DEMO545DEMO </li>
	                  </ul>
	              </div>
	          </div>*/}
	          <div className="row">
	              <div className="col-xs-12">
	                  <table className="table table-condensed table-hover">
	                      <thead>
	                          <tr>
	                              <th className="hidden-xs"> Prod </th>
	                              <th className="hidden-sm-up"> Descr </th>
	                              <th className="hidden-sm-up"> Cantidad </th>
	                              <th> Valor Unitario </th>
	                              <th className="hidden-sm-up"> Importe </th>
	                          </tr>
	                      </thead>
	                      <tbody>
	                      	  <tr>
	                              <td className="hidden-xs"> 01-MAX-D BIODIESEL B.A (UV) </td>
	                              <td className="hidden-sm-up"> D2 </td>
	                              <td className="hidden-sm-up"> <input type="number" step="0.01" style={{width: '100px', textAlign: 'right', borderColor: 'black'}} pattern="[0-9]*" className="form-control" placeholder="Galones" onKeyPress={this.onKeyPress} inputMode="numeric"  value={this.state.galsD2} onChange={this.galsD2Change}/> </td>
	                              {this.state.gasPrices && <td>S/ {this.state.priceD2} </td>} 
	                              <td className="hidden-sm-up"> <input type="number" step="0.01" style={{width: '100px', textAlign: 'right', borderColor: 'black'}} pattern="[0-9]*" className="form-control" placeholder="Soles" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.solesD2} onChange={this.solesD2Change}/> </td>
	                          </tr>
	                          <tr>
	                              <td className="hidden-xs"> 02-GASOHOL PRIMAX 90 </td>
	                              <td className="hidden-sm-up"> G90 </td>
	                              <td className="hidden-sm-up"> <input type="number" step="0.01" style={{width: '100px', textAlign: 'right', borderColor: 'black'}} pattern="[0-9]*" className="form-control" placeholder="Galones" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.galsG90} onChange={this.galsG90Change}/> </td>
	                              {this.state.gasPrices && <td>S/ {this.state.priceG90} </td>}
	                              <td className="hidden-sm-up"> <input type="number" step="0.01" style={{width: '100px', textAlign: 'right', borderColor: 'black'}} pattern="[0-9]*" className="form-control" placeholder="Soles" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.solesG90} onChange={this.solesG90Change}/> </td>
	                          </tr>
	                          <tr>
	                              <td className="hidden-xs"> 03-GASOHOL PRIMAX 95 </td>
	                              <td className="hidden-sm-up"> G95 </td>
	                              <td className="hidden-sm-up"> <input type="number" step="0.01" style={{width: '100px', textAlign: 'right', borderColor: 'black'}} pattern="[0-9]*" className="form-control" placeholder="Galones" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.galsG95} onChange={this.galsG95Change}/> </td> 
	                              {this.state.gasPrices && <td>S/ {this.state.priceG95} </td>}
	                              <td className="hidden-sm-up"> <input type="number" step="0.01" style={{width: '100px', textAlign: 'right', borderColor: 'black'}} pattern="[0-9]*" className="form-control" placeholder="Soles" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.solesG95} onChange={this.solesG95Change}/> </td>
	                          </tr>
	                      </tbody>
	                  </table>
	              </div>
	          </div>
	          <div className="row">
	              <div className="hidden-xs col-sm-3">
	                  <div className="well">
	                      <address>
	                          <strong>Código Hash: </strong> {this.state.invoiceHash}
	                          <br/> <strong>Son: </strong> {this.state.totalVerbiage}
	                          <br/> {this.state.selectedOption == 'nota de credito' && <div> <strong>Nro Documento Referencia: </strong> {this.state.invoiceNumberModified}
		                      <br/> <strong>Fecha Documento Referencia: </strong> {`${moment(this.state.dateOfInvoiceModified).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}</div>
		                      }
                          </address>
	                      <address>
	                          <strong>Consulte su documento en:</strong>
	                          <a> www.grifoslajoya.com </a>
	                      </address>
	                  </div>
	              </div>
		          <div className="hidden-xs col-sm-3">
	                  <div className="well">
		                  <address>
		                      <strong>Código QR:</strong>
		                      <br/>  
		                  </address>
		                  <div style={{width: '300px', height: '300px', display: 'block', margin: 'auto', zoom: 0.5}} >
				          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<div id="qrcode1" className="col-xs-12 col-md-offset-3" ></div>
				          </div>
	                  </div>
	              </div>
	              <div className="col-xs-12 col-sm-6 invoice-block">
	                  <div className="row invoice-subtotal">
				          <div className="col-xs-12">
					          <table className="table table-condensed table-hover">
					              <tbody>
					                  <tr>
					                      <td>
					                      	<strong>Sub-Total Ventas:</strong>
					                      </td>
					                      <td>
					                      	S/ 
					                      </td>
					                      <td className="text-right sbold">{this.state.subTotal}</td>
					                  </tr>
					                  <tr>
					                      <td>
					                      	<strong>Descuento:</strong>
					                      </td>
					                      <td>
					                      	S/ 
					                      </td>
					                      <td className="text-right sbold"><input type="number" step="0.01" style={{width: '100px', textAlign: 'right', borderColor: '#26344b'}} pattern="[0-9]*" className="form-control" placeholder="" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.discount} onChange={this.discountChange}/></td>
					                  </tr>
					                  <tr>
					                      <td>
					                      	<strong>IGV (18%):</strong>
					                      </td>
					                      <td>
					                      	S/ 
					                      </td>
					                      <td className="text-right sbold">{this.state.totalIGV} </td>
					                  </tr>
					                  <tr>
					                      <td>
				                      		<strong>Importe Total:</strong>
					                      </td>
					                      <td>
					                      	S/ 
					                      </td>
					                      <td className="text-right sbold">{this.state.total}</td>
					                  </tr>
					                  <tr>
					                      <td>
				                      		<strong><u>Forma de Pago:</u></strong>
					                      </td>
					                  </tr>
					                  <tr>
					                      <td>
				                      		<strong>Tarjeta Crédito/Débito:</strong>
					                      </td>
					                      <td>
					                      	S/ 
					                      </td>
					                      <td className="text-right sbold"><input type="number" step="0.01" style={{width: '100px', textAlign: 'right', borderColor: '#26344b'}} pattern="[0-9]*" className="form-control" placeholder="Visa" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.electronicPmt} onChange={this.electronicPmtChange}/></td>
					                  </tr>
					                  <tr>
					                      <td>
				                      		<strong>Efectivo:</strong>
					                      </td>
					                      <td>
					                      	S/ 
					                      </td>
					                      <td className="text-right sbold">{this.state.cashPmt}</td>
					                  </tr>
					                  <tr>
					                      <td>
				                      		<strong>Efectivo Entregado:</strong>
					                      </td>
					                      <td>
					                      	S/ 
					                      </td>
					                      <td className="text-right sbold"><input type="number" step="0.01" style={{width: '100px', textAlign: 'right', borderColor: '#26344b'}} pattern="[0-9]*" className="form-control" placeholder="" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.cashGiven} onChange={this.cashGivenChange}/></td>
					                  </tr>
					                  <tr>
					                      <td>
				                      		<strong>Vuelto:</strong>
					                      </td>
					                      <td>
					                      	S/ 
					                      </td>
					                      <td className="text-right sbold">{this.state.change}</td>
					                  </tr>
					              </tbody>
					          </table>	
		          		  </div>
			          </div>
			          
	                  {this.state.invoiceHash && <ReactToPrint trigger={() => <a type="submit" className="btn blue hidden-print margin-bottom-5" > <i className="fa fa-print"></i> Imprimir</a>} content={() => this.componentRef}></ReactToPrint>}&nbsp;
	                  {!this.state.invoiceHash && <a type="submit" className="btn blue hidden-print margin-bottom-5" disabled={!this.state.invoiceHash} > <i className="fa fa-print"></i> Imprimir</a>}&nbsp;
	                  <button type="submit" disabled={this.state.submitDisabled} className="btn green hidden-print margin-bottom-5">
	    	          	<i className="fa fa-check"></i> Enviar
	    	          </button>&nbsp;
	    	          <button type="button" onClick={this.emailInvoice} disabled={!this.state.submitDisabled} className="btn green-meadow hidden-print margin-bottom-5">
	    	          	<i className="fa fa-envelope"></i> Email 
	    	          </button>&nbsp;
	    	          {/*<a type="submit" onClick={this.emailInvoice.bind(this)} disabled={!this.state.submitDisabled} className="btn green-meadow margin-bottom-5"><i className="fa fa-envelope"></i> Email</a>&nbsp;*/}
	    	          <a type="submit" onClick={this.newInvoice} className="btn purple hidden-print margin-bottom-5" > <i className="fa fa-edit"></i> Nuevo</a>
	    	          {this.state.emailingGif &&
    	                  <div className="inline-block"><img src="../assets/global/plugins/plupload/js/jquery.ui.plupload/img/loading.gif" className="img-responsive" alt="" /></div>}
	              </div>
	          </div>
	          <div className="row">
	              <div className="hidden-sm hidden-md hidden-lg">
	                  <div className="well">
	                      <address>
		                      <strong>Código Hash: </strong> {this.state.invoiceHash}
	                          <br/> <strong>Son: </strong> {this.state.totalVerbiage}
	                          <br/> {this.state.selectedOption == 'nota de credito' && <div> <strong>Nro Documento Referencia: </strong> {this.state.invoiceNumberModified}
		                      <br/> <strong>Fecha Documento Referencia: </strong> {`${moment(this.state.dateOfInvoiceModified).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}</div>
		                      }
	                      </address>
	                      <address>
	                          <strong>Consulte su documento en:</strong>
	                          <a> www.grifoslajoya.com </a>
	                      </address>
	                  </div>
	              </div>
	          </div>
	          <div className="row">
		          <div className="hidden-sm hidden-md hidden-lg">
	                  <div className="well">
		                  <address>
		                      <strong>Código QR:</strong>
		                  </address>
		                  <div style={{width: '300px', height: '300px', display: 'block', margin: 'auto', zoom: 0.5}} >
				          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<div id="qrcode3" className="col-xs-12 col-md-offset-3" ></div>
				          </div>
	                  </div>
	              </div>
	          </div>
	      </div>
	      
	      <div className="invoice-content-2" ref={el => (this.componentRef = el)} style={{fontFamily:"sans-serif", fontSize: 11}}>
	          <div className="row invoice-head">
	              <div className="col-md-12 col-xs-12 text-center">
	                  <div className="invoice-logo">
	                  	  {/*<img src="../assets/pages/media/invoice/lajoya.png" className="img-responsive" style={{display: 'block', margin: 'auto'}} alt="" />*/}
	                      <span className="uppercase" >{this.state.selectedOption} Electrónica</span><br/>
	                      <span className="uppercase" >{this.state.invoiceNumber}</span>
	                    	  
	                  </div>
	              </div>
	              <div className="col-md-12 col-xs-12">
	                  <div className="company-address text-center">
	                      <span className="bold uppercase" style={{fontSize: 13}}>La Joya de Santa Isabel EIRL</span>
	                      <br/> Av. Miguel Grau Mza B Lote 1-2 
	                      <br/> Lima - Lima - Ate 
	                      <br/> Teléfono: +51 356 0345
	                      <br/> www.grifoslajoya.com 
	                      <br/> <span className="muted"> RUC: 20501568776  </span>
                      </div>
	              </div>
	          </div>
	          <div className="row invoice-cust-add">
	              <div className="col-xs-12">
		              <table className="table table-hover table-borderless" >
			              <tbody>
				              <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      {this.state.docLabelObj.clientDocType}:
			                      </td>
			                      <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{this.state.clientDocNumber}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      {this.state.docLabelObj.clientName}:
			                      </td>
			                      <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{this.state.clientName}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      Dirección:
			                      </td>
			                      <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{this.state.clientAddress}</td>
			                  </tr>
			                  {this.state.selectedOption != 'boleta' && 
			                  <tr>
		                      	  <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
		                      	  Placa de Vehículo:
			                      </td>
			                      <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{this.state.truckPlateNumber}</td>
			                  </tr>
			                  }
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      Fecha:
			                      </td>
			                      <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{`${moment(this.state.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}</td>
			                  </tr>
			              </tbody>
			          </table>
	              </div>
	          	  
	          </div>
	          <div className="row invoice-body" >
	              <div className="col-xs-12 table-responsive">
	                  <table className="table table-hover">
	                      <thead>
	                          <tr>
                      			  <th className="invoice-title text-center" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>Producto</th>
	                              <th className="invoice-title text-center" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>Cantidad</th>
	                              <th className="invoice-title text-center" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>Precio</th>
	                              <th className="invoice-title text-center" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>Importe</th>
	                          </tr>
	                      </thead>
	                      <tbody>
	                      	  {this.state.galsD2 > 0 && 
	                      	  <tr>
	                              <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
	                                  01-MAX-D BIODIESEL
	                              </td>
	                              <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{parseFloat(this.state.galsD2 || '0').toFixed(2)}</td>
	                              <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{parseFloat(this.state.priceD2 || '0').toFixed(2)}</td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>{parseFloat(this.state.solesD2 || '0').toFixed(2)}</td>
	                          </tr>
	                      	  }
	                      	  {this.state.galsG90 > 0 && 
	                      	  <tr>
	                          	  <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
	                                  02-GASOHOL PRIMAX 90
	                              </td>
	                              <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{parseFloat(this.state.galsG90 || '0').toFixed(2)}</td>
	                              <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{parseFloat(this.state.priceG90 || '0').toFixed(2)}</td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>{parseFloat(this.state.solesG90 || '0').toFixed(2)}</td>
	                          </tr>
	                      	  }
	                      	  {this.state.galsG95 > 0 && 
	                      	  <tr>
	                          	  <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
	                                  03-GASOHOL PRIMAX 95
	                              </td>
	                              <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{parseFloat(this.state.galsG95 || '0').toFixed(2)}</td>
	                              <td className="text-center sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>{parseFloat(this.state.priceG95 || '0').toFixed(2)}</td>
	                              <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>{parseFloat(this.state.solesG95 || '0').toFixed(2)}</td>
	                          </tr>
	                      	  }
	                      </tbody>
	                  </table>
	              </div>
	          </div>
	          <div className="row invoice-subtotal">
		          <div className="col-xs-12">
			          <table className="table table-hover">
			              <tbody>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                          Sub-total Ventas:
			                      </td>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>S/</td>
			                      <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>{this.state.subTotal}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      Descuento:
			                      </td>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>S/</td>
			                      <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>{parseFloat(this.state.discount || '0').toFixed(2)}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      IGV (18%):
			                      </td>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>S/</td>
			                      <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>{this.state.totalIGV}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      Importe Total:
			                      </td>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>S/</td>
			                      <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>{this.state.total}</td>
			                  </tr>
			                  <tr>
		                      	  <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
		                      	  	<strong><u>Forma de Pago:</u></strong>
			                      </td>
			                      
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      Tarjeta Crédito/Débito:
			                      </td>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>S/</td>
			                      <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>{parseFloat(this.state.electronicPmt || '0').toFixed(2)}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      Efectivo:
			                      </td>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>S/</td>
			                      <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>{this.state.cashPmt}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      Efectivo Entregado:
			                      </td>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>S/</td>
			                      <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>{parseFloat(this.state.cashGiven || '0').toFixed(2)}</td>
			                  </tr>
			                  <tr>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>
			                      Vuelto:
			                      </td>
			                      <td style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px"}}>S/</td>
			                      <td className="text-right sbold" style={{fontFamily:"sans-serif", fontSize: 11, padding: "2px", paddingRight: "12px"}}>{this.state.change}</td>
			                  </tr>
			              </tbody>
			          </table>	
          		  </div>
	          </div>
	          <div className="row col-xs-12">
	                  <address>
		                  <strong>Código Hash: </strong> {this.state.invoiceHash}
	                      <br/> <strong>Son: </strong> {this.state.totalVerbiage}
	                      <br/> {this.state.selectedOption == 'nota de credito' && <div> <strong>Motivo: </strong> {this.state.motiveCdDescription} 
	                      <br/> <strong>Nro Documento Referencia: </strong> {this.state.invoiceNumberModified}
	                      <br/> <strong>Fecha Documento Referencia: </strong> {`${moment(this.state.dateOfInvoiceModified).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}
	                      </div>
	                      }
	                  </address>
	                  <address>
	                      <strong>Consulte su documento en:</strong>
	                      <a> www.grifoslajoya.com </a>
	                  </address>
	          </div>
	          
	          <div style={{width: '300px', height: '500px', display: 'block', margin: 'auto', zoom: 0.5}} >
	          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<div id="qrcode2" className="col-xs-12 col-md-offset-3" ></div>
	          </div>
	      </div>
	      
	      <Modal show={this.state.showEmailModal} onHide={this.handleEmailModalCancel.bind(this)}>
	          <Modal.Header closeButton>
	            <Modal.Title>Ingrese o actualize email</Modal.Title>
	          </Modal.Header>
	          
	          <Modal.Body>
		            <div className="input-group">
		                <span className="input-group-addon">
		                    <i className="fa fa-envelope"></i>
		                </span>
		                <input type="email" className="form-control" placeholder="Correo electrónico"  onKeyPress={this.onKeyPress} value={this.state.clientEmailAddress} onChange={this.clientEmailAddressChange}></input>
			        </div>
	          </Modal.Body>
	          
	          <Modal.Footer>
	          	<Button onClick={this.handleEmailModalCancel.bind(this)}>Cancelar</Button>
	          	<Button bsStyle="primary" onClick={this.handleEmailModalClose.bind(this)}>Enviar</Button>
	          </Modal.Footer>
          </Modal>
          
          <Modal show={this.state.showError || this.state.showSuccess} onHide={this.handleResultModalCancel.bind(this)}>
          <Modal.Header closeButton>
            <Modal.Title>Status</Modal.Title>
          </Modal.Header>
          
          <Modal.Body>
		      {this.state.showError && 
			        <div className="alert alert-danger">
		      <strong>¡Error!</strong>{" " + this.state.errors.submit + " - " + this.state.errors.clientName + " - " + this.state.errors.clientDocNumber + " - " + this.state.errors.clientAddress + " - " + this.state.errors.truckPlateNumber}  
			      	</div>
		      }

		      {this.state.showSuccess && 
		      	<div className="alert alert-success">
		      		<strong>Éxito!</strong> {this.state.sunatErrorStr}
		      	</div>
		      }
          </Modal.Body>
          
          <Modal.Footer>
          	<Button bsStyle="primary" name="resultModal" ref={'refResultModal'} onClick={this.handleResultModalCancel.bind(this)}>OK</Button>
          </Modal.Footer>
      </Modal>
	      
      </form>
    )
  }
}

let target = document.getElementById('react-invoice-id');

ReactDOM.render(<TableDashboard />, target);