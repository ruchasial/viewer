var type = 1;
document.addEventListener('DOMContentLoaded', webViewerLoad, true);

function webViewerLoad() {
	var filePath = getFilePath();
	if (type == 1)
		viewImage(filePath);
	else
		viewDocument(filePath);

}

function getFilePath() {
	var queryString = document.location.search.substring(1);
	var url = window.location.toString();
	var anchor = url.indexOf('#');
	var query = url.indexOf('?');
	var end = Math.min(anchor > 0 ? anchor : url.length, query > 0 ? query
			: url.length);
	var start = queryString.indexOf('=') + 1;
	type=url.substring(anchor+2,anchor+1);
	return queryString.substring(start, end);
}

/*document.onload = function(){
 document.getElementById('Frame').src=getFileName();
 };
 */

var zoomLevel = 100;
var maxZoomLevel = 110;
var minZoomLevel = 90;


function zoomInOut(zm) {
	if (zm > 1) {
		if (zoomLevel < maxZoomLevel) {
			zoomLevel++;
		} else {
			return;
		}
	} else if (zm < 1) {
		if (zoomLevel > minZoomLevel) {
			zoomLevel--;
		} else {
			return;
		}
	}

	if (type == 1) {
		var img = document.getElementById("Frame");

		wid = img.width;
		ht = img.height;
		if (zm == 0.9) {
			zm = 10 / 11;
		}
		img.style.width = (wid * zm) + "px";
		img.style.height = (ht * zm) + "px";
		var fit = document.querySelector(".fit");
		fit.style.maxHeight = "none";
	} else {
		
		var frame = document.getElementById("DocFrame");
		var content = (frame.contentDocument || frame.contentWindow);
		var magnifier=Math.abs((zoomLevel-100))
		content.body.style.fontSize = 100+(magnifier*(zm-1) * 100) + "%";
	}//img.style.marginLeft =-(img.width/2) + "px";
	//img.style.marginTop =-(img.height/2) + "px";
	//fit.style.maxWidth= (zm*100+100) + "%";
	
}

function zoom(zm) {
	
	if (type == 1) {
		var fit = document.querySelector(".fit");
		fit.style.maxHeight = "100%";
		var img = document.getElementById("Frame");
		img.style.width = "initial";
		img.style.height = "initial";
		wid = img.width;
		ht = img.height;
		img.style.width = (wid * zm) + "px";
		img.style.height = (ht * zm) + "px";
		fit.style.maxHeight = "none";

	}
	else {
		var frame = document.getElementById("DocFrame");
		var content = (frame.contentDocument || frame.contentWindow);
		content.body.style.fontSize = (zm * 100) + "%";
		}
	//img.style.marginLeft =-(img.width/2) + "px";
	//img.style.marginTop =-(img.height/2) + "px";
	//fit.style.maxWidth= (zm*100+100) + "%";
	}

function set_body_height() {
	var wh = $(window).height() - 2;
	$('body').attr('style', 'height:' + wh + 'px;');
}
$(document).ready(function() {
	set_body_height();
	$(window).bind('resize', function() {
		set_body_height();
	});
});

window.onload = function() {
	if (window.addEventListener)
		window.addEventListener('DOMMouseScroll', wheel, false);
	window.onmousewheel = document.onmousewheel = wheel;
}

function wheel(event) {
	var delta = 0;
	if (!event)
		event = window.event;

	if (event.wheelDelta) {
		delta = event.wheelDelta / 60;
	} else if (event.detail) {
		delta = -event.detail / 2;
	}

	if (delta)
		zoomOnScroll(delta);

	if (event.preventDefault)
		event.preventDefault();
	event.returnValue = false;
}

function zoomOnScroll(delta) {
	if (delta < 0)
		zoomInOut(0.9);
	else
		zoomInOut(1.1);
}

/*<div id="viewerContainer" style="display:flex;justify-content:center;align-items:center;overflow:auto;">
 * <img id="Frame" class="center fit" onmousewheel="wheel" src="output/sample_jpg.jpg" onload="this.onload=null; this.src=getFilePath();"  >
 */

function viewImage(filePath) {
	var image = document.createElement("img");
	image.id = 'Frame';
	image.className = "center fit";
	image.src = filePath;
	image.setAttribute("onmousewheel", "wheel");
	var viewer = document.getElementById('viewerContainer');
	viewer.style.display = "flex";
	viewer.style.justifyContent = "center";
	viewer.style.alignItems = "center";
	viewer.style.overflow = "auto";
	viewer.appendChild(image);

}

/*<div id=viewerContainer style=" overflow: hidden;box-sizing: border-box;width=100%;height=100%;padding-left:5%">
 <iframe src="output/sample_html.html" width=94% height=100% style="background-color: white;"></iframe>*/

function viewDocument(filePath) {
	var frame = document.createElement("iframe");
	frame.id = 'DocFrame';
	frame.src = filePath;
	frame.width = "94%";
	frame.height = "100%";
	frame.style.backgroundColor = "white";
	var viewer = document.getElementById('viewerContainer');
	viewer.style.overflow = "hidden";
	viewer.style.boxSizing = "border-box";
	viewer.style.paddingLeft = "5%";
	viewer.appendChild(frame);
	var rotateCw=document.getElementById('pageRotateCw');
	rotateCw.setAttribute("disabled","disabled");
	var rotateCcw=document.getElementById('pageRotateCcw');
	rotateCcw.setAttribute("disabled","disabled");
	/*var ssc=document.getElementById('scaleSelectContainer');
	ssc.className="hidden";
*/
}

function fullscreen() {
	zoom(1.5);

	var elem = document.getElementById("viewerContainer");
	if (elem.requestFullscreen) {
		elem.requestFullscreen();
	} else if (elem.msRequestFullscreen) {
		elem.msRequestFullscreen();
	} else if (elem.mozRequestFullScreen) {
		elem.mozRequestFullScreen();
	} else if (elem.webkitRequestFullscreen) {
		elem.webkitRequestFullscreen();
	}
	
}

/*<input type="file" id="fileLoader" name="files" title="Load File" />
 */

function openFile() {
	/*var fileInput = document.createElement('input');
	fileInput.id = 'fileLoader';
	//fileInput.className = 'fileInput';
	fileInput.multiple = "multiple";
	fileInput.setAttribute('type', 'file');
	//fileInput.onChange="getUploadedFile();";
	//fileInput.oncontextmenu = noContextMenuHandler;
	document.body.appendChild(fileInput);
	$("#fileLoader").click();
    $("#fileLoader").change(getUploadedFile());
*/
	window.open("http://localhost:8080/Viewer");
	
	}

/*function getUploadedFile() {
	  var input = document.getElementById ("fileLoader");
	  alert (input.value);
	  window.open("http://localhost:8080/WebApp/web/NewFile1.html?file=output/"+input.value);
	var files = document.getElementById("fileLoader").files;
	for (var i = 0; i < files.length; i++) {
		
		window.open("http://localhost:8080/Viewer/web/iviewer.html?file=output/"+ files[i].name);
	}
	
}
*/
var angle = 0;
function rotateImg(degree) {
	angle += degree;
	var img = document.getElementById("Frame");
	img.style.transform = "rotate(" + angle + "deg)";

}
