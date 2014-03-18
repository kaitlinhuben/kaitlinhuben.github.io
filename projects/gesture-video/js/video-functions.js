/**
Main javascript for gesture video player
Includes lots of original $1 code as well as additional code
for gesture collection and video playing
**/
var _arrow = "arrow";
var _arrowB = "arrow block";
var _triangle = "triangle";
var _triUp = "triangle up";
var _triDown = "triangle down";
var _line = "line";
var _m = "M";
var _circle = "circle";
var _circleUp = "circle up";
var _circleDown = "circle down";
var playbackChange = 0.5;  //how much to change playback rate
var volumeChange = 0.2;    //how much to change volume
var sizeChange = 100;      //how much to change size
var playing = false;

/* Changes the video based on input */
function changeVideo(result, points) {
	var gesture = result.Name;
	if(gesture == _arrow) {
		seek(10, points);
	}
	else if(gesture == _arrowB) {
		seek(30, points);
	}
	else if(gesture == _triangle) {
		resetPlaybackRate();
	}
	else if(gesture == _triUp) {
		increasePlaybackRate();
	}
	else if(gesture == _triDown) {
		decreasePlaybackRate();
	}
	else if(gesture == _line) {
		changeVolume(points);
	}
	else if(gesture == _m) {
		mute();
	}
	else if(gesture == _circleUp) {
		increaseSize();
	}
	else if(gesture == _circleDown) {
		decreaseSize();
	}
	else if(gesture == _circle) {
		restart();
	}
	else {
		noMatch();
	}
}

/* Pauses or plays video based on whether previously playing 
Disappearing video fix adapted from:
http://stackoverflow.com/questions/13492198/video-disappears-when-poster-attribute-and-preload-is-defined
*/
function playPause(){
	var video = document.getElementById("video");
	if(playing) {
		video.pause();
		// to fix disappearing video bug (Chrome)
		if (video.getAttribute('controls') !== 'true') {
            video.setAttribute('controls', 'true');                    
        }
        video.removeAttribute('controls');
		playing = false;
		$("#gesture-name").html("Pause");
	} else {
		video.play();
		// to fix disappearing video bug (Chrome)
		if (video.getAttribute('controls') !== 'true') {
            video.setAttribute('controls', 'true');                    
        }
        video.removeAttribute('controls');
		playing = true;
		$("#gesture-name").html("Play");
	}
}

function pauseVideo(video) {
	video.pause();
	playing = false;
}

/* Seeks time amount forward or back based on location of first and last points */
function seek(time, points) {
	var video = document.getElementById("video");
	
	var firstPointX = points[0]["X"];
	var lastPointX = points[points.length-1]["X"];
	
	//figure out whether seeking forward or backward
	if( (lastPointX - firstPointX) > 0 ) { 
		video.currentTime += time;
		$("#gesture-name").html("+" + time + " seconds");
	} else { 
		video.currentTime -= time;
		$("#gesture-name").html("-" + time + " seconds");
	}
}

/* Sets video time back to start */
function restart() {
	var video = document.getElementById("video");
	
	video.currentTime = 0;
	$("#gesture-name").html("Restart");
}

/* Increases playback rate */
function increasePlaybackRate() {
	var video = document.getElementById("video");
	
	video.playbackRate = video.playbackRate + playbackChange;
	$("#gesture-name").html("Increase playback rate");
}

/* Decreases playback rate */
function decreasePlaybackRate() {
	var video = document.getElementById("video");
	
	// make sure playback rate doesn't go below zero 
	// (no bad effects, just annoying for user to go back up later)
	if(video.playbackRate > 0) {
		video.playbackRate = video.playbackRate - playbackChange;
		$("#gesture-name").html("Decrease playback rate");
	} else {
		$("#gesture-name").html("(Playback cannot decrease)");
	}
}

/* Resets playback rate to normal */
function resetPlaybackRate() {
	var video = document.getElementById("video");
	
	video.playbackRate = 1;	//default is 1
	$("#gesture-name").html("Reset playback rate");
}

/* Increases or decreases volume based on location of first and last points */
function changeVolume(points) {
	var video = document.getElementById("video");
	
	var firstPointY = points[0]["Y"];
	console.log("first y: " + firstPointY);
	var lastPointY = points[points.length-1]["Y"];
	console.log("last y: " + lastPointY);
	
	//figure out whether seeking forward or backward, 
	//and keep volume in [0,1]
	if( (lastPointY - firstPointY) > 0) { 
		if(video.volume > 0.1) {
			video.volume -= volumeChange; 
			$("#gesture-name").html("Volume down");
		} else {
			$("#gesture-name").html("(Volume cannot go down more)");
		}
	} else {
		if(video.volume < 1){ 
			video.volume += volumeChange;
			$("#gesture-name").html("Volume up");
		} else {
			$("#gesture-name").html("(Volume cannot go up more)");
		}
	}
}

/* Mutes the volume */
function mute() {
	var video = document.getElementById("video");
	
	if(video.muted) {
		video.muted = false;
		$("#gesture-name").html("Unmute");
	} else {
		video.muted = true;
		$("#gesture-name").html("Mute");
	}
}

/* Increase size of video frame */
function increaseSize() {
	var videoHeight = $("video").height();
	$("video").height(videoHeight + sizeChange);
	$("#gesture-name").html("Increase video size");
}

/* Decrease size of video frame */
function decreaseSize() {
	var videoHeight = $("video").height();
	if(videoHeight > sizeChange) {
		$("video").height(videoHeight - sizeChange);
		$("#gesture-name").html("Decrease video size");
	} else {
		$("#gesture-name").html("(Video size cannot decrease)");
	}
}

/* Informs user there was no gesture match */
function noMatch() {
	$("#gesture-name").html("No gesture match");
}