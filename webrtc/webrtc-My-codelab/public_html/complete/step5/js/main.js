var isInitiator;
//original example is commented
//room = prompt("Enter room name:");

//added by Salim: getting room froim URl: /domain/?room_name
// grab the room from the URL
console.log('main.js:location.search=', location.search);//print out = ?room1
var room = location.search && location.search.split('?')[1];
console.log('main.js: room=', room);//example: room1

var socket = io.connect();

if (room !== "") {
    console.log('Joining room ' + room);
    socket.emit('create or join', room);
}

socket.on('full', function(room) {
    console.log('Room ' + room + ' is full');
});

socket.on('empty', function(room) {
    isInitiator = true;
    console.log('Room ' + room + ' is empty');
});

/*
 * join called by the initiator
 */
socket.on('join', function(room) {
    console.log('event: on JOIN: Making request to join room ' + room);
    console.log('You are the initiator!');
});

/*
 * joined called by the next client
 */
socket.on('joined', function(room) {
    console.log('event:on JOINED: Making request to join room ' + room);
});

socket.on('log', function(array) {
    console.log.apply(console, array);
});
