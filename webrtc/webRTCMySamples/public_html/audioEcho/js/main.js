/* Copyright 2015 Salim Rahal
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

window.AudioContext = window.AudioContext || window.webkitAudioContext;

var audioContext = new AudioContext();
var audioInput = null,
        realAudioInput = null,
        inputPoint = null;

/*
 * it should echo back the stream
 */

function gotStream(stream) {

    // Create an AudioNode from the stream.
    realAudioInput = audioContext.createMediaStreamSource(stream);
    audioInput = realAudioInput;
    /*
     * without filter:
     
     // Connect it to the destination to hear yourself (or any other node for processing!)
     audioInput.connect(audioContext.destination);
     */

    /* Using a filter
     */
    // microphone -> filter -> destination.
    var filter = audioContext.createBiquadFilter();
    audioInput.connect(filter);
    console.log('gotStream: before connecting to the destination');
    // Connect it to the destination to hear yourself (or any other node for processing!)
    filter.connect(audioContext.destination);
    /**/
    console.log('finish getStream');
}

function initAudio() {
    if (!navigator.getUserMedia)
        navigator.getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
    if (!navigator.cancelAnimationFrame)
        navigator.cancelAnimationFrame = navigator.webkitCancelAnimationFrame || navigator.mozCancelAnimationFrame;
    if (!navigator.requestAnimationFrame)
        navigator.requestAnimationFrame = navigator.webkitRequestAnimationFrame || navigator.mozRequestAnimationFrame;

    navigator.getUserMedia(
            {
                "audio": {
                    "mandatory": {
                        "googEchoCancellation": "false",
                        "googAutoGainControl": "false",
                        "googNoiseSuppression": "false",
                        "googHighpassFilter": "false"
                    },
                    "optional": []
                },
            }, gotStream, function (e) {
        alert('Error getting audio');
        console.log(e);
    });
}

window.addEventListener('load', initAudio);