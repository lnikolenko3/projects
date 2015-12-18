var data = require("sdk/self").data;
// Construct a panel, loading its content from the "text-entry.html"
// file in the "data" directory, and loading the "get-text.js" script
// into it.
var text_entry = require("sdk/panel").Panel({
	width: 287,
	height: 260,
  contentURL: data.url("text-entry.html"),
  contentScriptFile: data.url("get-text.js"),
  onHide: handleHide
});

// Create a button
var button = require("sdk/ui/button/toggle").ToggleButton({
  id: "show-panel",
  label: "PassFree",
  icon: {
    "16": "./passfree-16.png",
    "32": "./passfree-32.png",
    "64": "./passfree-64.png"
  },
  onClick: handleClick
});

// Show the panel when the user clicks the button.
function handleClick(state) {
  if (state.checked) {
    text_entry.show({
      position: button
    });
  }
}

// When the panel is displayed it generated an event called
// "show": we will listen for that event and when it happens,
// send our own "show" event to the panel's script, so the
// script can prepare the panel for display.
text_entry.on("show", function() {
  text_entry.port.emit("show");
});

// Listen for messages called "text-entered" coming from
// the content script. The message payload is the text the user
// entered.
// In this implementation we'll just log the text to the console.
text_entry.port.on("text-entered", function (text) {
  //console.log(text);
  //text_entry.hide();
  /*
  var tabs = require("sdk/tabs");
	var contentScriptString = 'document.body.innerHTML = "<h1>'+text+'</h1>";';

	tabs.activeTab.attach({
  	contentScript: contentScriptString
	});
	*/
});

text_entry.port.on("submitPass", function (text) {
	
	content1= 'var x="my name is nikki";var inputs=document.getElementsByTagName("input");for(var i=0;i<inputs.length;i++){{var input=inputs[i];if(input.type=="password"&&(input.name.toLowerCase().indexOf("auth")==-1)){{input.value = x}}}};document.getElementById("demo").innerHTML = x;';
  
  /*'document.body.innerHTML = "<h1>'+text+'</h1>";'
  */
				var tabs = require("sdk/tabs");
var contentScriptString = 'var x="my name is nikki";var inputs=document.getElementsByTagName("input");for(var i=0;i<inputs.length;i++){{var input=inputs[i];if(input.type=="password"&&(input.name.toLowerCase().indexOf("auth")==-1)){{input.value = x}}}};document.getElementById("demo").innerHTML = x;';

tabs.activeTab.attach({
  contentScript: contentScriptString
});
			
                    
            
       
});
/*
text_entry.port.on("yoo", function (text) {
	var inputs=document.getElementsByTagName("input");    

      for(var i=0;i<inputs.length;i++){{    

            var input=inputs[i];     

            if(input.type=="password"&&(input.name.toLowerCase().indexOf("auth")==-1)){
				{input.value = text}
				require("sdk/tabs").activeTab.attach({
      			contentScript: 'input.value = text'
    			});
				
                    
            }
       }};
    
	
 
});
*/

function handleHide() {
  button.state('window', {checked: false});
}

/*
var toggle = require('sdk/ui/button/toggle');
var panels = require("sdk/panel");
var self = require("sdk/self");

if(typeof sum === 'undefined')
{
sum = 0;
}
sum++;

var button = toggle.ToggleButton({
  id: "my-button",
  label: "my button",
  icon: {
    "16": "./icon-16.png",
    "32": "./icon-32.png",
    "64": "./icon-64.png"
  },
  onChange: handleChange
});

var panel = panels.Panel({
  contentURL: self.data.url("text-entry.html"),
  contentScriptFile: self.data.url("get-text.js"),
  onHide: handleHide
});

function handleChange(state) {
  if (state.checked) {
    panel.show({
      position: button
    });
  }
}

function handleHide() {
  button.state('window', {checked: false});
}

panel.on("show", function() {
  panel.port.emit("show");
  panel.port.emit(sum);
});


panel.port.on("username", function (text) {
  //display[0].innerHTML = '';
  //display[0].innerHTML = 'Your username is ', text, ',';
  console.log(text);
  //panel.hide();
  
  	//This puts the data onto the webpage. Gotta figure out how to put it on the panel.
	var tabs = require("sdk/tabs");
	var contentScriptString = 'document.body.innerHTML = "<h1>'+text+'</h1>";';

	tabs.activeTab.attach({
  	contentScript: contentScriptString
	});
	
});

panel.port.on("password", function (text) {
  //var display = panel.getElementsByClassName("display");
  //display[0].innerHTML = display[0].innerHTML, ' and your password is ', text, '.';
  console.log(text);
  //panel.hide();
  
   	//This puts the data onto the webpage. Gotta figure out how to put it on the panel.
	var tabs = require("sdk/tabs");
	var contentScriptString = 'document.body.innerHTML = document.body.innerHTML+ "<br>"+ "<h1>'+text+'</h1>";';

	tabs.activeTab.attach({
  	contentScript: contentScriptString
	});
	
});

//var iframe = document.getElementsByTagName( "iframe" )[ 0 ];
*/