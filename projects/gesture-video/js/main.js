/**
Main javascript for gesture video player
Includes lots of original $1 code as well as additional code
for gesture collection and video playing
**/
$(function() {
// ==================================================================
// Dollar code
// ==================================================================
//
// Startup
//
var _isDown, _points, _r, _g, _rc;
_points = new Array();
_r = new DollarRecognizer();

var canvas = document.getElementById('myCanvas');
_g = canvas.getContext('2d');
//_g.fillStyle = "rgb(0,0,225)";
//_g.strokeStyle = "rgb(0,0,225)";
_g.fillStyle = "rgb(64,140,153)";
_g.strokeStyle = "rgb(64,140,153)";
_g.lineWidth = 3;
_g.font = "16px A";
_rc = getCanvasRect(canvas); // canvas rect on page
//_g.fillStyle = "rgb(255,255,136)";
//_g.fillRect(0, 0, _rc.width, 20);

_isDown = false;

function getCanvasRect(canvas)
{
	var w = canvas.width;
	var h = canvas.height;

	var cx = canvas.offsetLeft;
	var cy = canvas.offsetTop;
	while (canvas.offsetParent != null)
	{
		canvas = canvas.offsetParent;
		cx += canvas.offsetLeft;
		cy += canvas.offsetTop;
	}
	return {x: cx, y: cy, width: w, height: h};
}
function getScrollY()
{
	var scrollY = 0;
	if (typeof(document.body.parentElement) != 'undefined')
	{
		scrollY = document.body.parentElement.scrollTop; // IE
	}
	else if (typeof(window.pageYOffset) != 'undefined')
	{
		scrollY = window.pageYOffset; // FF
	}
	return scrollY;
}
//
// Mouse Events
//
$("#myCanvas").mousedown(function(e){ mouseDownEvent(e.clientX, e.clientY);});
function mouseDownEvent(x, y)
{
	document.onselectstart = function() { return false; } // disable drag-select
	document.onmousedown = function() { return false; } // disable drag-select
	_isDown = true;
	x -= _rc.x;
	y -= _rc.y - getScrollY();
	if (_points.length > 0)
		_g.clearRect(0, 0, _rc.width, _rc.height);
	_points.length = 1; // clear
	_points[0] = new Point(x, y);
	//drawText("Recording unistroke...");
	_g.fillRect(x - 4, y - 3, 9, 9);
}
$("#myCanvas").mousemove(function(e){ mouseMoveEvent(e.clientX, e.clientY);});
function mouseMoveEvent(x, y)
{
	if (_isDown)
	{
		x -= _rc.x;
		y -= _rc.y - getScrollY();
		_points[_points.length] = new Point(x, y); // append
		drawConnectedPoint(_points.length - 2, _points.length - 1);
	}
}
$("#myCanvas").mouseup(function(e){ mouseUpEvent(e.clientX, e.clientY);});
function mouseUpEvent(x, y)
{
	document.onselectstart = function() { return true; } // enable drag-select
	document.onmousedown = function() { return true; } // enable drag-select

	if (_isDown)
	{
		_isDown = false;
		videoRecognize(_points);
	}
}

function drawText(str)
{
	_g.fillStyle = "rgb(255,255,136)";
	_g.fillRect(0, 0, _rc.width, 20);
	_g.fillStyle = "rgb(0,0,255)";
	_g.fillText(str, 1, 14);
}
function drawConnectedPoint(from, to)
{
	_g.beginPath();
	_g.moveTo(_points[from].X, _points[from].Y);
	_g.lineTo(_points[to].X, _points[to].Y);
	_g.closePath();
	_g.stroke();
}
function round(n, d) // round 'n' to 'd' decimals
{
	d = Math.pow(10, d);
	return Math.round(n * d) / d
}

// -------------- END DOLLAR CODE -----------------------------------

// ==================================================================
// Code for getting new unistrokes
// ==================================================================
function printUnistroke(unistroke) {
	var uniString = 'new Unistroke("';	
	uniString += unistroke["Name"] + '", new Array(';
	for(var i = 0; i < unistroke["Points"].length; i++) {
		uniString += 'new Point(';
		uniString += unistroke["Points"][i]["X"];
		uniString += ',';
		uniString += unistroke["Points"][i]["Y"];
		uniString += ')';
		if(i < unistroke["Points"].length - 1) {
			uniString += ',';
		}
	}
	uniString += '));';
	
	return uniString;
}

$("#printVars").click(function(){
	for(var i = 0; i < UnistrokesHolder.length; i++){
		var unistroke = UnistrokesHolder[i];
		var info = printUnistroke(unistroke);
		console.log(unistroke);
		//$("#holder").append(info + "<br><br>");
	}
});
// ------------- END NEW UNISTROKES CODE-----------------------------


// ==================================================================
// Code for mapping gestures to video changes
// ==================================================================
function videoRecognize(points){
	if (points.length >= 10)
	{
		var result = _r.Recognize(points);
		//drawText("Result: " + result.Name + " (" + round(result.Score,2) + ").");
		//$("#video-holder").append("Result: " + result.Name + " (" + round(result.Score,2) + ").<br>");
		changeVideo(result, points);
		_rc = getCanvasRect(canvas);
	}
	else // fewer than 10 points were inputted
	{
		playPause();
	}
	_rc = getCanvasRect(canvas);
}
// ------------- END NEW UNISTROKES CODE-----------------------------


// ==================================================================
// Code for beginning info pages
// ==================================================================
$("#yes").click(function(){
	$("#starting-page").css("display", "none");
	$("#directions").css("display", "none");
	$("#content").css("visibility", "visible");
	// get canvas again now that starting page is hidden
	_rc = getCanvasRect(canvas);
});

$("#no").click(function(){
	$("#starting-page").css("display", "none");
	$("#directions").css("display", "block");
	// get canvas again now that starting page is hidden
	_rc = getCanvasRect(canvas);
});

$("#goToGestures").click(function(){
	$("#directions").css("display", "none");
	$("#gesture-directions").css("display", "block");
	// get canvas again now that starting page is hidden
	_rc = getCanvasRect(canvas);
});

$("#go").click(function(){
	$("#gesture-directions").css("display", "none");
	$("#content").css("visibility", "visible");
	// get canvas again now that starting page is hidden
	_rc = getCanvasRect(canvas);
});

$("#help").click(function(){
	$("#content").css("visibility", "hidden");
	$("#directions").css("display", "block");
	// get canvas again now that starting page is hidden
	_rc = getCanvasRect(canvas);
	//pause video
	var video = document.getElementById("video");
	pauseVideo(video);
});

$(window).scroll(function(){
	_rc = getCanvasRect(canvas);
});

$(".button").button();
$("#gesture-dialog").dialog({
	autoOpen: false,
	minWidth: 500,
	minHeight: 350,
	modal: true
});
$("#goToGestureCheatsheet").click(function(){
	$("#gesture-dialog").dialog("open");
	var video = document.getElementById("video");
	pauseVideo(video);
});

});