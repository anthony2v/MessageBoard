
const CommonUtil = {
    /*
     * converts a form to a json object with key-value pairs being [name]:[value] for every input within the form
     * if an input element is to be excluded, simply do not provide the "name" attribute
     */
    formToJson: (formElement, stringify) => {
		stringify = (stringify === undefined) ? true : stringify;

		let output = {};
		new FormData(formElement).forEach((value, key) => {
			// Check if property already exist
			if ( Object.prototype.hasOwnProperty.call( output, key ) ) {
				let current = output[ key ];
				if ( !Array.isArray( current ) ) {
          			// If it's not an array, convert it to an array.
					current = output[ key ] = [ current ];
				}
				current.push( value ); // Add the new value to the array.
			} 
			else {
				output[ key ] = value;
			}
		});
		
		return stringify ? JSON.stringify(output) : output;
	},
	
	includeStylesheet: (href) => {
		let linkElement = this.document.createElement('link');
		linkElement.setAttribute('rel', 'stylesheet');
		linkElement.setAttribute('type', 'text/css');
		let cacheBuster = "r=" + new Date().getTime();
		linkElement.setAttribute('href', href + (href.indexOf("?") > -1 ? "&" + cacheBuster : "?" + cacheBuster));

		document.head.appendChild(linkElement);
	 }
};