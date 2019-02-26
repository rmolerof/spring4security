var Modal = ReactBootstrap.Modal;
var Button = ReactBootstrap.Button;

class TableDashboard extends React.Component {
  constructor() {
    super();
    this.state = {
      errors: {},
      id: '',
      user: {
    	  name: '',
    	  password: '',
    	  email: '',
    	  roles: {
    		  ROLE_USER: false,
    		  ROLE_ADMIN: false
    	  }
      },
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
      invoiceDateEditorDisabled: true,
      invoiceDateDisp: '',
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
      invoiceNumberEditorDisabled: true,
      invoiceNumberEditorButtonDisabled: false,
      notaDeCreditoDisabled: false,
      submitDisabled: false,
      printDisabled: true,
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
	  bonusNumber: '',
	  bonusNumberDisp: '',
	  sunatStatus: 'PENDIENTE',
	  sunatValidated: false
    };
    
    this.CONSTANTS = {
	    FACTURA: '01',
	    BOLETA: '03',
	    NOTADECREDITO: '07',
	    DNI: '1',
	    RUC: '6',
	    BLANK_BOLETA_NUMBER: 'B001-XXXXXXXX',
	    ZERO_BOLETA_NUMBER: 'B001-00000000',
	    BLANK_FACTURA_NUMBER: 'F001-XXXXXXXX',
	    ZERO_FACTURA_NUMBER: 'F001-00000000',
	    BONUS_NUMBER_PREFIX: '7027661000'
    }
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
  
  invoiceNumberChange = (evt) => {
	  this.setState({ invoiceNumber: evt.target.value.toUpperCase() });
  }
  
  invoiceDateDispChange = (evt) => {
	  this.setState({ invoiceDateDisp: evt.target.value.toUpperCase() });
  }
  
  editInvoiceDate() {
	  this.setState({invoiceDateEditorDisabled: !this.state.invoiceDateEditorDisabled});
	  
	  this.setState({invoiceDateDisp: moment(this.state.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A').toString()});
  }
  
  _validateInvoiceNumber(invoiceNumber) {
	  
	  var re = /^([BF])?001-[0-9]{8}$/;
	  
	  if (invoiceNumber != '' && !re.test(invoiceNumber)) {
		  return false
	  } else {
		  var invoiceNumberInString = invoiceNumber.substring(5, invoiceNumber.length);
		  if (parseInt(invoiceNumberInString) <= 0){
			  return false;
		  } else {
			  return true;
		  }
	  } 
  }
  
  _validateBonusNumberDisp(bonusNumberDisp){
	  var re = /^[0-9]{9}$/;
	  
	  if (bonusNumberDisp != '' && !re.test(bonusNumberDisp)) {
		  return false
	  } else {
		  return true;
	  } 
  }
  
  _validateDatetime(datetime) {
	    
	  	var datetimeSplit = datetime.trim().split(' ');
	  	var re;
	  	
	  	var date = datetimeSplit[0];
	    if(date != '' && !this._validateDate(date)) {
	      return 'Invalid Date';
	    }

	    var time = datetimeSplit[1] + ' ' + datetimeSplit[2];
	    re = /^((0[1-9])|(1[0-2])):([0-5][0-9])\s([AP]M)?$/;
	    if(time != '' && !re.test(time)) {
	      return 'Invalid Date';
	    }
	    
	    var validatedDate = moment.tz(date + " " + time, 'DD/MM/YYYY hh:mm A', 'America/Lima').toDate();
	    
	    return validatedDate;
  }
  
  _validateDate(date)
  {
      // First check for the pattern
      if(!/^\d{1,2}\/\d{1,2}\/\d{4}$/.test(date))
          return false;

      // Parse the date parts to integers
      var parts = date.split("/");
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
  
  clientDocNumberChange = (evt) => {
    this.setState({ clientDocNumber: evt.target.value.trim() });
    
    if (evt.target.value == '0' && this.state.selectedOption == 'boleta') {
    	this.setState({ clientName: 'CLIENTES VARIOS' });
    }
  }
  
  bonusNumberDispChange = (evt) => {
    this.setState({bonusNumberDisp: evt.target.value.trim()});
    this.setState({bonusNumber: this.CONSTANTS.BONUS_NUMBER_PREFIX + evt.target.value.trim()});
  }
  
  invoiceNumberModifiedDispChange = (evt) => {
	    this.setState({ invoiceNumberModifiedDisp: evt.target.value });
	    
	    var pad = "00000000";
	    var n = evt.target.value;
	    var valueWithZeros = (pad+n).slice(-pad.length);
	    
	    if (this.state.invoiceTypeModifiedToggle) {
	    	this.setState({ invoiceNumberModified: "F001-" + valueWithZeros });
	    	this.setState({ invoiceNumber: this.CONSTANTS.BLANK_FACTURA_NUMBER });
	    } else {
	    	this.setState({ invoiceNumberModified: "B001-" + valueWithZeros });
	    	this.setState({ invoiceNumber: this.CONSTANTS.BLANK_BOLETA_NUMBER });
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
		this.setState({clientDocType:  this.CONSTANTS.DNI});
		this.setState({invoiceType: this.CONSTANTS.BOLETA});
		this.setState({invoiceNumber: this.CONSTANTS.BLANK_BOLETA_NUMBER});
		this.setState({invoiceTypeModified: ''});
		this.setState({invoiceNumberModified: ''});
		this.setState({invoiceNumberModifiedDisp: ''});
		this.setState({dateOfInvoiceModified: new Date()});
		this.setState({igvModified: ''});
		this.setState({totalModified: ''});
		this.setState({clientDocNumber: '', clientName: '', clientAddress: '', clientEmailAddress: '', truckPlateNumber: '', bonusNumber: '', bonusNumberDisp: ''});

	  } else if (changeEvent.target.value == 'factura') {
		  var docLabelObj = {
	    	  clientDocType: 'RUC',
	    	  clientDocTypePH: 'Ingrese RUC',
	    	  clientName: 'Razón Social',
	    	  clientNamePH: 'Nombre(s)',
	      }
	  	this.setState({docLabelObj: docLabelObj});
		this.setState({clientDocType: this.CONSTANTS.RUC});
		this.setState({invoiceType: this.CONSTANTS.FACTURA});
		this.setState({invoiceNumber: this.CONSTANTS.BLANK_FACTURA_NUMBER});
		this.setState({invoiceTypeModified: ''});
		this.setState({invoiceNumberModified: ''});
		this.setState({invoiceNumberModifiedDisp: ''});
		this.setState({dateOfInvoiceModified: new Date()});
		this.setState({igvModified: ''});
		this.setState({totalModified: ''});
		this.setState({clientDocNumber: '', clientName: '', clientAddress: '', clientEmailAddress: '', truckPlateNumber: '', bonusNumber: '', bonusNumberDisp: ''});

	  } else if (changeEvent.target.value == 'nota de credito') {
		  var docLabelObj = {
	    	  clientDocType: 'RUC',
	    	  clientDocTypePH: 'Ingrese Doc',
	    	  clientName: 'Señor(es)',
	    	  clientNamePH: 'Nombre(s)',
	      }
	  	this.setState({docLabelObj: docLabelObj});
		this.setState({clientDocType: this.CONSTANTS.RUC});
		this.setState({invoiceType: this.CONSTANTS.NOTADECREDITO});
		this.setState({invoiceNumber: this.CONSTANTS.BLANK_FACTURA_NUMBER});
		this.setState({invoiceTypeModified: this.CONSTANTS.FACTURA});
		this.setState({invoiceNumberModified: ''});
		this.setState({invoiceNumberModifiedDisp: ''});
		this.setState({dateOfInvoiceModified: new Date()});
		this.setState({igvModified: ''});
		this.setState({totalModified: ''});
		this.setState({clientDocNumber: '', clientName: '', clientAddress: '', clientEmailAddress: '', truckPlateNumber: '', bonusNumber: '', bonusNumberDisp: ''});
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
  
  _getQueryVariable(variable)
  {
         var query = window.location.search.substring(1);
         var vars = query.split("&");
         for (var i=0;i<vars.length;i++) {
                 var pair = vars[i].split("=");
                 if(pair[0] == variable){return pair[1];}
         }
         return(false);
  }
  
  _getUser() {

	  var self = this;
	  jQuery.ajax({
			type: "GET",
			contentType: "application/json", 
			url:"/getUser",
			datatype: 'json',
			cache: false,
			timeout: 600000,
			success: (data) => {						
				self.setState({user: data});
			},
			error: function(e){

			}	
		});
  }
  
  componentWillMount(){
	  // Get current User
	  this._getUser();
	  
	  var invoiceNumber = this._getQueryVariable('id');
	  if (invoiceNumber) {
		  this._invoiceSearchAjax(invoiceNumber);
		  this.setState({printDisabled: false});
	  } else {
		  this._fetchGasPrices({dateEnd: "latest", dateBeg: ""});  
	  }
	  
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
		this.setState({invoiceType: this.CONSTANTS.NOTADECREDITO});
		this.setState({invoiceTypeModified: this.CONSTANTS.FACTURA});
		this.setState({ invoiceNumber: this.CONSTANTS.BLANK_FACTURA_NUMBER });
		var docLabelObj = {
		    	  clientDocType: 'RUC',
		    	  clientDocTypePH: 'Ingrese Doc',
		    	  clientName: 'Señor(es)',
		    	  clientNamePH: 'Nombre(s)',
        }
		this.setState({docLabelObj: docLabelObj});
	} else {
		this.setState({invoiceType: this.CONSTANTS.NOTADECREDITO});
		this.setState({invoiceTypeModified: this.CONSTANTS.BOLETA});
		this.setState({ invoiceNumber: this.CONSTANTS.BLANK_BOLETA_NUMBER });
		var docLabelObj = {
		    	  clientDocType: 'DNI',
		    	  clientDocTypePH: 'Ingrese DNI',
		    	  clientName: 'Señor(es)',
		    	  clientNamePH: 'Nombre(s)',
	    }
	  	this.setState({docLabelObj: docLabelObj});
	}
  }
  
  editInvoiceNumber() {
	  this.setState({invoiceNumberEditorDisabled: !this.state.invoiceNumberEditorDisabled, invoiceNumber: this.state.invoiceNumber.replace(/X/gi, '0')});
  }
  
  motiveCdHandleChange(event) {
	  this.setState({motiveCd: event.target.value});

	  var index = event.nativeEvent.target.selectedIndex;
	  this.setState({motiveCdDescription: event.nativeEvent.target[index].text.toUpperCase()});
  }
  
  searchButton(event) {
	  this.onTabPress(event);
  }
  
  onTabPress(event) {
	
	this.setState({ showError: false});
	
	var self = this; 
	var search = {};
	if (event.target.innerText && event.target.innerText == "Buscar") {
		search["docId"] = this.state.clientDocNumber;
	} else {
		search["docId"] = event.target.value;
	}
	
	if (search["docId"]) {
		if (this.state.selectedOption != 'boleta') {
			
			if (search["docId"] && search["docId"] >= 0 &&  search["docId"].length == 11) {
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
							self.setState({bonusNumber: data.result.bonusNumber});
							self.setState({bonusNumberDisp: data.result.bonusNumber.substring(10)});
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
			if (search["docId"] && search["docId"] > 0 &&  search["docId"].length == 8) {
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
							self.setState({bonusNumber: data.result.bonusNumber});
							self.setState({bonusNumberDisp: data.result.bonusNumber.substring(10)});
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
	
  }
  
  _invoiceSearchAjax(invoiceNumber) {
	    var self = this;
	  	var search = {};
	  	self.setState({ showError: false, clientDocNumberDisabled: false, clientNameDisabled: false, clientAddressDisabled: false});
	    
	    search["invoiceNumber"] = invoiceNumber;
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
					  id: data.result[0].id,
					  invoiceNumber: data.result[0].invoiceNumber,
					  date: data.result[0].date,
					  clientDocNumber: data.result[0].clientDocNumber,
					  clientName: data.result[0].clientName,
					  invoiceType: data.result[0].invoiceType,
					  clientDocType: data.result[0].clientDocType,
					  clientAddress: data.result[0].clientAddress,
					  truckPlateNumber: data.result[0].truckPlateNumber,
					  dateOfInvoiceModified: data.result[0].date,
					  galsD2: data.result[0].galsD2,
					  galsG90: data.result[0].galsG90,
					  galsG95: data.result[0].galsG95,
					  priceD2: data.result[0].priceD2,
					  priceG90: data.result[0].priceG90,
					  priceG95: data.result[0].priceG95,
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
					  invoiceTypeModified: data.result[0].invoiceTypeModified,
					  invoiceNumberModified: data.result[0].invoiceNumberModified,
					  motiveCd: data.result[0].motiveCd,
					  motiveCdDescription: data.result[0].motiveCdDescription,
					  dateOfInvoiceModified: data.result[0].dateOfInvoiceModified,
					  igvModified: data.result[0].totalIGV,
					  totalModified: data.result[0].total,
					  bonusNumber: data.result[0].bonusNumber,
					  bonusNumberDisp: data.result[0].bonusNumber.substring(10),
					  sunatStatus: data.result[0].sunatStatus,
					  clientDocNumberDisabled: false,
					  clientNameDisabled: false,
					  clientAddressDisabled: false,
					  saveOrUpdate: 'update',
					  invoiceHash: data.result[0].invoiceHash,
					  clientEmailAddress: data.result[0].clientEmailAddress
				  });
				  
				  // Display No of modified invoice
				  if (data.result[0].invoiceTypeModified == self.CONSTANTS.FACTURA) {
					  self.setState({invoiceTypeModifiedToggle: true});
				  } else {
					  self.setState({invoiceTypeModifiedToggle: false});
				  }
				  self.setState({invoiceNumberModifiedDisp: data.result[0].invoiceNumberModified.substring(5)});
					
				  // Select typo of invoice
				  if (data.result[0].invoiceType == self.CONSTANTS.FACTURA) {
					  self.setState({selectedOption: "factura", facturaDisabled: false, boletaDisabled: true, notaDeCreditoDisabled: true });
				  } else if (data.result[0].invoiceType == self.CONSTANTS.BOLETA) {
					  self.setState({selectedOption: "boleta", facturaDisabled: true, boletaDisabled: false, notaDeCreditoDisabled: true });
				  } else if (data.result[0].invoiceType == self.CONSTANTS.NOTADECREDITO)  {
					  self.setState({selectedOption: "nota de credito", facturaDisabled: true, boletaDisabled: true, notaDeCreditoDisabled: false });
				  }
				  
				  // Select wording based on client document type
				  var docLabelObj = {};
				  if (data.result[0].clientDocType == self.CONSTANTS.DNI) {
					  docLabelObj = {
				    	  clientDocType: 'DNI',
				    	  clientDocTypePH: 'Ingrese DNI',
				    	  clientName: 'Señor(es)',
				    	  clientNamePH: 'Nombre(s)',
				      }

				  } else if (data.result[0].clientDocType == self.CONSTANTS.RUC) {
					  docLabelObj = {
				    	  clientDocType: 'RUC',
				    	  clientDocTypePH: 'Ingrese RUC',
				    	  clientName: 'Razón Social',
				    	  clientNamePH: 'Nombre(s)',
				      }
				  }
				  self.setState({docLabelObj: docLabelObj});

				  // Select button behind radio button 
				  var cssB = (data.result[0].invoiceType == self.CONSTANTS.BOLETA) ? "btn blue active btn-sm" : "btn btn-default btn-sm";
				  self.setState({showBoletaRadioButton: cssB});
				  var cssF = (data.result[0].invoiceType == self.CONSTANTS.FACTURA) ? "btn blue active btn-sm" : "btn btn-default btn-sm";
				  self.setState({showFacturaRadioButton: cssF});
				  var cssNV = (data.result[0].invoiceType == self.CONSTANTS.NOTADECREDITO) ? "btn blue active btn-sm" : "btn btn-default btn-sm";
				  self.setState({showNotaDeCreditoRadioButton: cssNV});
				  
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
  
  invoiceSearchForCreditNote(event) {
	  	
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
					  priceG95: data.result[0].priceG95,
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
					  bonusNumber: data.result[0].bonusNumber,
					  bonusNumberDisp: data.result[0].bonusNumber.substring(10),
					  sunatStatus: 'PENDIENTE',
					  sunatValidated: false,
					  clientEmailAddress: data.result[0].clientEmailAddress
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
  
  handleInvoiceSubmitResultModalHide() {
	  this.setState({ showError: false, showSuccess: false });  
  }
  
  handleInvoiceSubmitResultModalClose() {
	  this.setState({ showError: false, showSuccess: false });
	  
	  if (this.state.status) {
		  $('#printInvoiceButton')[0].click();  
	  }
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
					
				  /*if (data.result == '1') {	
					  self.setState({ sunatErrorStr: data.msg });
					  self.setState({ showSuccess: true });
				  } else {
				  	  var errors = {
			  			  submit: data.msg
				  	  };
				  	  self.setState({errors: errors}); 
				  	  self._toggleError();
				  }*/
				  
				  self.setState({emailingGif: false});
			
				}, error: function(e){
					
					if (e.statusText == "timeout") {
						//self.setState({ sunatErrorStr: "Correo electrónico sera enviado en unos minutos" });
						//self.setState({ showSuccess: true });
						self.setState({ emailingGif: false});
						
						/*setTimeout(function() {
						    ReactDOM.findDOMNode(self.refs['refResultModal']).focus();
				        }.bind(this), 0); */
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
		  invoiceNumber: this.CONSTANTS.BLANK_BOLETA_NUMBER,
		  invoiceNumberEditorDisabled: true,
		  // customer
	      clientDocNumber: '',
	      clientName: '',
	      clientDocType: this.CONSTANTS.DNI,
	      clientAddress: '',
	      clientEmailAddress: '',
	      truckPlateNumber: '',
	      clientDocNumberDisabled: false,
	      clientNameDisabled: false,
	      clientAddressDisabled: false,
		  // invoice breakdown
	      date: new Date(),
	      invoiceType: this.CONSTANTS.BOLETA,
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
	      printDisabled: true,
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
	      bonusNumber: '',
	      bonusNumberDisp: '',
	      sunatStatus: 'PENDIENTE'
	  });
  }
  
  _mongoObjectId() {
	    var timestamp = (new Date().getTime() / 1000 | 0).toString(16);
	    return timestamp + 'xxxxxxxxxxxxxxxx'.replace(/[x]/g, function() {
	        return (Math.random() * 16 | 0).toString(16);
	    }).toLowerCase();
	};
  
  handleSubmit = (evt) => {
		
		evt.preventDefault();
		
		const {id,
			user,
			subTotal, 
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
			clientEmailAddress,
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
			saveOrUpdate,
			invoiceHash,
			bonusNumber,
			bonusNumberDisp,
			sunatStatus,
			invoiceDateDisp} = this.state;
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
	    	id: id ? id: this._mongoObjectId(),
	    	user: user,
	    	invoiceNumber: invoiceNumber,  
		    clientDocNumber: clientDocNumber,
		    clientName: clientName,
		    clientDocType: clientDocType, // RUC
		    clientAddress: clientAddress,
		    clientEmailAddress: clientEmailAddress,
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
	    	saveOrUpdate: saveOrUpdate,
	    	bonusNumber: bonusNumber,
	    	invoiceHash: invoiceHash,
	    	sunatStatus: sunatStatus
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
		    // Invoice Number validation
	    	if (!invoiceNumber.includes("XXXXXXXX")) {
	    		var invoiceNumberInString = invoiceNumber.substring(5, invoiceNumber.length);
	    		if (!self._validateInvoiceNumber(invoiceNumber)) {
	    			errors["submit"] = "Número de Comprobante es incorrecto";
	    			formIsValid = false;
	    		}
	    	}
	    	
	    	// Bonus validation
	    	if (bonusNumber && bonusNumber >= 0) {
	    		if (!self._validateBonusNumberDisp(bonusNumberDisp)) {
	    			errors["bonusNumberDisp"] = "Número Bonus debe tener 9 dígitos";
	    			formIsValid = false;
	    		}
		    }
	    	
	    	// Date validation
	    	if (!this.state.invoiceDateEditorDisabled){
		    	var validatedDatetime = this._validateDatetime(invoiceDateDisp);
		    	if(validatedDatetime && validatedDatetime != 'Invalid Date') {
		    		invoiceVo.date = validatedDatetime;
		    	} else {
		    		errors["submit"] = "Fecha Inválida.";
	    			formIsValid = false;
		    	}
	    	}
	    	
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
						self.setState({emailingGif: false, showSuccess: true, submitDisabled: true,  printDisabled: false});
						
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
	      
	      
	      <div className="row">
      		<div className="form-inline" style={{marginLeft: '15px'}}>
	            <div className="form-group" style={{marginRight: "7px"}}>
	                <h1 className="page-title uppercase bold" style={{fontSize: "20px", marginBottom: "-1px"}}> {this.state.selectedOption} ELECTRÓNICA</h1>
	            </div>
	            <div className="form-group" style={{marginRight: "7px"}}>
	                {this.state.invoiceNumberEditorDisabled && <div><h1 className="page-title uppercase bold" style={{fontSize: "20px", marginBottom: "-1px"}}> {this.state.invoiceNumber}</h1></div>}
		      		{!this.state.invoiceNumberEditorDisabled && <div><input type="text" className="form-control" style={{borderColor: '#26344b', width: "130px", marginBottom: "-1px", height: 25}} placeholder={"[B,F]001-00000000"} onKeyPress={this.onKeyPress} value={this.state.invoiceNumber} onChange={this.invoiceNumberChange}/></div>}
	      		</div>
	      		<div className="form-group">
	            	{!this.state.invoiceNumberEditorButtonDisabled && this.state.user.roles.ROLE_ADMIN &&
		      			<a type="button" onClick={this.editInvoiceNumber.bind(this)} > <i className="fa fa-edit"></i></a>
		            }
	      		</div>
	        </div>
          </div>
	      
	      <div className="invoice margin-bottom-20 margin-top-10">
              <div className="portlet-body form">
        		
	              <div className="row">
	              	  <div style={{marginLeft: '15px'}}>
			              <div className="form-group">
			                  <label className="control-label">Tipo de Comprobante</label>
		    	              <div className="clearfix">
					    	      <div className="btn-group btn-group-xs">
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
		            	  	<label className="control-label">Bonus</label><br></br>  
		            	  	<input name="bonusNumberDisp" type="text" pattern="[0-9]*" className="form-control" style={{borderColor: '#26344b'}} disabled={this.state.bonusNbrDisabled} placeholder={'Nro Bonus'} onKeyPress={this.onKeyPress.bind(this)} value={this.state.bonusNumberDisp} onChange={this.bonusNumberDispChange}/>
		                  </div>
		              </div>
		              <div className="col-md-2">
			              <div className="form-group">
		            	  	<label className="control-label">Ir a</label><br></br>  
		            	  	<div style={{textAlign: 'left'}}>
					      		  <a href="/invoice-table-page" className="btn btn-sm yellow margin-bottom-5">
							          <i className="fa fa-table"></i>&nbsp;Tabla Comprobantes
								  </a>
				      	      </div>
		                  </div>
		              </div>
		              <div className="col-md-2">
			              <div className="form-group">
		            	  	<label className="control-label">Crear</label><br></br>  
		            	  	<div style={{textAlign: 'left'}}>
		            	  		<a type="submit" onClick={this.newInvoice} className="btn btn-sm purple hidden-print margin-bottom-5" > <i className="fa fa-edit"></i> Nuevo</a>
				      	    </div>
		                  </div>
		              </div>
	              </div>
	              {this.state.selectedOption == 'nota de credito' &&
	              <div className="row">
		              <div className="col-md-2">
				          <div className="form-group">
		            	  	<label className="control-label">{invoicePrefix}</label><br></br>  
		            	  	<a onClick={this._invoiceTypeHandleClick.bind(this)} className="btn btn-sm green-meadow">{this.state.invoiceTypeModifiedToggle} {!this.state.invoiceTypeModifiedToggle} {buttonText}</a>
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
	    	                  <input type="number" pattern="[0-9]*" className="form-control" style={{borderColor: '#26344b'}} placeholder={"Nro Comprobante"} onBlur={this.invoiceSearchForCreditNote.bind(this)} onKeyPress={this.onKeyPress} inputMode="numeric"  value={this.state.invoiceNumberModifiedDisp} onChange={this.invoiceNumberModifiedDispChange}/>
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
	    	                  <select className="ticket-assign form-control input-medium selectHeight" style={{borderColor: '#26344b'}} value={this.state.motiveCd} onChange={this.motiveCdHandleChange.bind(this)}>
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
			                  <label className="control-label">Fecha&nbsp; {this.state.user.roles.ROLE_ADMIN && <a type="button" onClick={this.editInvoiceDate.bind(this)} > <i className="fa fa-edit"></i></a>}</label>
			                  {this.state.invoiceDateEditorDisabled && <input type="text" id="lastName" className="form-control" value={`${moment(this.state.date).tz('America/Lima').format('DD/MM/YYYY hh:mm A')}`}  readOnly/>}
			                  
			            	  {!this.state.invoiceDateEditorDisabled && <input type="text" className="form-control" style={{borderColor: '#26344b'}} placeholder="DD/MM/AAAA hh:mm [A,P]M" onKeyPress={this.onKeyPress} value={this.state.invoiceDateDisp} onChange={this.invoiceDateDispChange}/>}
			              </div>
			          </div>
	    	      	  <div className="col-md-2">
	    	              <div className="form-group">
	    	                  <table style={{width:'50%'}}>
	    	                  	<tbody>
	    	                  	  <tr>
		    	                    <td><label className="control-label">{this.state.docLabelObj.clientDocType}&nbsp;<a type="button" onClick={this.searchButton.bind(this)} > <i className="fa fa-edit">Buscar</i></a></label></td>
		    	                    <td>{this.state.loadingGif &&
		  	    	                  <img src="../assets/global/plugins/plupload/js/jquery.ui.plupload/img/loading.gif" className="img-responsive" alt="" />}</td> 
		    	                  </tr>
		    	                </tbody>
		    	              </table>
	    	                  <input name="clientDocNumber" type="text" pattern="[0-9]*" className="form-control" style={{borderColor: '#26344b'}} disabled={this.state.clientDocNumberDisabled} placeholder={this.state.docLabelObj.clientDocTypePH} onKeyPress={this.onKeyPress.bind(this)} value={this.state.clientDocNumber} onChange={this.clientDocNumberChange}/>
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
				                      		<strong>Tarjeta Crédito/Débito <i className="fa fa-cc-visa"></i>:</strong>
					                      </td>
					                      <td>
					                      	S/ 
					                      </td>
					                      <td className="text-right sbold"><input type="number" step="0.01" style={{width: '100px', textAlign: 'right', borderColor: '#26344b'}} pattern="[0-9]*" className="form-control" placeholder="VISA" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.electronicPmt} onChange={this.electronicPmtChange}/></td>
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
				                      		<strong>Efectivo Entregado <i className="fa fa-money"></i>:</strong>
					                      </td>
					                      <td>
					                      	S/ 
					                      </td>
					                      <td className="text-right sbold"><input type="number" step="0.01" style={{width: '100px', textAlign: 'right', borderColor: '#26344b', borderWidth: '2px'}} pattern="[0-9]*" className="form-control" placeholder="Efectivo" onKeyPress={this.onKeyPress} inputMode="numeric" value={this.state.cashGiven} onChange={this.cashGivenChange}/></td>
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
			          
	                  {this.state.status && <ReactToPrint trigger={() => <a id="printInvoiceButton" type="submit" className="btn blue hidden-print margin-bottom-5" > <i className="fa fa-print"></i> Imprimir</a>} content={() => this.componentRef}></ReactToPrint>}&nbsp;
	                  {!this.state.status && <a type="submit" className="btn blue hidden-print margin-bottom-5" disabled={!this.state.status} > <i className="fa fa-print"></i> Imprimir</a>}&nbsp;
	                  <button type="submit" disabled={((this.state.submitDisabled && !((new Date() - this.state.date) > 300000)) || ((new Date() - this.state.date) > 300000)) && !this.state.user.roles.ROLE_ADMIN} className="btn green hidden-print margin-bottom-5">
	    	          	<i className="fa fa-check"></i> Enviar
	    	          </button>&nbsp;
	    	          <button type="button" onClick={this.emailInvoice} disabled={this.state.printDisabled} className="btn green-meadow hidden-print margin-bottom-5">
	    	          	<i className="fa fa-envelope"></i> Email 
	    	          </button>&nbsp;
	    	          {/*<a type="submit" onClick={this.emailInvoice.bind(this)} disabled={!this.state.submitDisabled} className="btn green-meadow margin-bottom-5"><i className="fa fa-envelope"></i> Email</a>&nbsp;*/}
	    	          {/*<a type="submit" onClick={this.newInvoice} className="btn purple hidden-print margin-bottom-5" > <i className="fa fa-edit"></i> Nuevo</a>*/}
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
	                      </div>}
	                      {this.state.bonusNumberDisp && <div><strong>Nro Bonus: </strong> {this.state.bonusNumberDisp}</div>}
	                      <strong>Atendido por:</strong> {this.state.user.name} 
	                  </address>
	                  <address>
	                      <strong>Consulte su documento en:</strong>
	                      <a> www.grifoslajoya.com </a>
	                      <strong>AUTORIZADO MEDIANTE RESOLUCIÓN DE INTENDENCIA - N° 034-005-0005294/SUNAT</strong>
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
			        Correo electrónico sera enviado en unos minutos!
	          </Modal.Body>
	          
	          <Modal.Footer>
	          	<Button onClick={this.handleEmailModalCancel.bind(this)}>Cancelar</Button>
	          	<Button bsStyle="primary" onClick={this.handleEmailModalClose.bind(this)}>Enviar</Button>
	          </Modal.Footer>
          </Modal>
          
          <Modal show={this.state.showError || this.state.showSuccess} onHide={this.handleInvoiceSubmitResultModalHide.bind(this)}>
	          <Modal.Header closeButton>
	            <Modal.Title>Status</Modal.Title>
	          </Modal.Header>
	          
	          <Modal.Body>
			      {this.state.showError && 
				        <div className="alert alert-danger">
			      <strong>¡Error!</strong>{" " + this.state.errors.submit + " - " + this.state.errors.clientName + " - " + this.state.errors.clientDocNumber + " - " + this.state.errors.bonusNumberDisp + " - " + this.state.errors.clientAddress + " - " + this.state.errors.truckPlateNumber}  
				      	</div>
			      }
	
			      {this.state.showSuccess && 
			      	<div className="alert alert-success">
			      		<strong>Éxito!</strong> {this.state.sunatErrorStr}
			      	</div>
			      }
	          </Modal.Body>
	          
	          <Modal.Footer>
	          	{this.state.showSuccess && <Button bsStyle="primary" name="resultModal" ref={'refResultModal'} onClick={this.handleInvoiceSubmitResultModalClose.bind(this)}>OK</Button>}
	          	{this.state.showError &&  <Button bsStyle="primary" onClick={this.handleInvoiceSubmitResultModalHide.bind(this)}>OK</Button>}
	          </Modal.Footer>
	      </Modal>
	      
      </form>
    )
  }
}

let target = document.getElementById('react-invoice-id');

ReactDOM.render(<TableDashboard />, target);