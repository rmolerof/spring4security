class ReactToPrint extends React.Component {

	constructor(props) {
		super(props);
		self = this;
	}

  triggerPrint(target) {
    const { onBeforePrint, onAfterPrint } = self.props;

    if (onBeforePrint) {
      onBeforePrint;
    }

    setTimeout(() => {
      target.contentWindow.focus();
      target.contentWindow.print();
      this.removeWindow(target);

      if (onAfterPrint) {
        onAfterPrint;
      }

    }, 500);
  }

  removeWindow(target) { 
    setTimeout(() => {
      target.parentNode.removeChild(target);
    }, 500);
  }

  handlePrint(){
  
    const {
      bodyClass,
      content,
      copyStyles,
      pageStyle,
      onAfterPrint
    } = self.props;

    const contentEl = content();

    if (contentEl === undefined) {
      console.error("Refs are not available stateless components. For 'react-to-print' to work only Class based components can be printed");
      return false;
    }

    let printWindow = document.createElement('iframe');
    printWindow.style.position = 'absolute';
    printWindow.style.top = '-1000px';
    printWindow.style.left = '-1000px';

    const contentNodes = ReactDOM.findDOMNode(contentEl);
    const linkNodes = document.querySelectorAll('link[rel="stylesheet"]');

    self.linkTotal = linkNodes.length || 0;
    self.linkLoaded = 0;

    const markLoaded = (type) => {

      self.linkLoaded++;

      if (self.linkLoaded === self.linkTotal) {       
        self.triggerPrint(printWindow);
      }

    };

    printWindow.onload = () => {

      let domDoc = printWindow.contentDocument || printWindow.contentWindow.document;
      const srcCanvasEls = [...contentNodes.querySelectorAll('canvas')];

      domDoc.open();
      domDoc.write(contentNodes.outerHTML);
      domDoc.close();

      /* remove date/time from top */
      const defaultPageStyle = pageStyle === undefined
        ? "@page { size: auto;  margin: 0mm; } @media print { body { -webkit-print-color-adjust: exact; } }"
        : pageStyle;

      let styleEl = domDoc.createElement('style');
      styleEl.appendChild(domDoc.createTextNode(defaultPageStyle));
      domDoc.head.appendChild(styleEl);

      if (self.props.bodyClass.length) {
        domDoc.body.classList.add(bodyClass);
      }

      const canvasEls = domDoc.querySelectorAll('canvas');
      [...canvasEls].forEach((node, index) => {     
        node.getContext('2d').drawImage(srcCanvasEls[index], 0, 0);
      });

      if (copyStyles !== false) {

        const headEls = document.querySelectorAll('style, link[rel="stylesheet"]');

        [...headEls].forEach((node, index) => { 
        
          let newHeadEl = domDoc.createElement(node.tagName);
          let styleCSS = "";

          if (node.tagName === 'STYLE') {

            if (node.sheet) {
              for (let i = 0; i < node.sheet.cssRules.length; i++) {
                styleCSS += node.sheet.cssRules[i].cssText + "\r\n";
              }

              newHeadEl.setAttribute('id', `react-to-print-${index}`);
              newHeadEl.appendChild(domDoc.createTextNode(styleCSS));

            }

          } else {

            let attributes = [...node.attributes];
            attributes.forEach(attr => {
              newHeadEl.setAttribute(attr.nodeName, attr.nodeValue);
            });

            newHeadEl.onload = markLoaded.bind(null, 'link');
            newHeadEl.onerror = markLoaded.bind(null, 'link');          

          }

          domDoc.head.appendChild(newHeadEl);

        });


      }

      if (self.linkTotal === 0 || copyStyles === false) {
        self.triggerPrint(printWindow);
      }

    };

    document.body.appendChild(printWindow);

  }

  render() {

    return React.cloneElement(this.props.trigger(), {
     ref: (el) => this.triggerRef = el,
     onClick: this.handlePrint
    });

  }

}

ReactToPrint.defaultProps = {
		copyStyles: PropTypes.bool,
		trigger: PropTypes.func.isRequired,
		content: PropTypes.func.isRequired,
		onBeforePrint: PropTypes.func,
		onAfterPrint: PropTypes.func,
		pageStyle: PropTypes.string,
		bodyClass: PropTypes.string,
		copyStyles: true,
		closeAfterPrint: true,
		bodyClass: ''
}