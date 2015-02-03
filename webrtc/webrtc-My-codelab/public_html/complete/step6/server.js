var static = require('node-static');
var http = require('http');
var file = new (static.Server)();
var app = http.createServer(function (req, res) {
    file.serve(req, res);
}).listen(2014);


var io = require('socket.io').listen(app);

io.sockets.on('connection', function (socket) {

    // convenience function to log server messages on the client
    function log() {
        var array = [">>> Message from server: "];
        for (var i = 0; i < arguments.length; i++) {
            array.push(arguments[i]);
        }
        socket.emit('log', array);
    }

    socket.on('message', function (message) {
        log('Got message:', message);
        // for a real app, would be room only (not broadcast)
        socket.broadcast.emit('message', message);
    });

    socket.on('create or join', function (room) {
        //original code of getting clients number socket.io 0.9
        //var numClients = io.sockets.clients(room).length;

        /* socket.io.1.0+
         * added by Salim: https://github.com/Automattic/socket.io/issues/1544
         * 
         * 
         */
        //var clients = io.sockets.adapter.rooms[room];
        //var numClients = (typeof clients !== 'undefined') ? Object.keys(clients).length : 0;
        // console.log("server.js:numClients=" + numClients);
/******GEt numclient: begin********/
        console.log("server.js:room=" + room);
        log("server.js:room=" + room);
        var clients = io.sockets.adapter.rooms[room];
        var numClients = 0;
        if (typeof clients === 'undefined') {
            console.log("server.js:clients is type=" + clients + "/numClients=" + numClients);
            log("server.js:clients is type=" + clients + "/numClients=" + numClients);
        } else {
            numClients = Object.keys(clients).length;
            console.log("server.js:clients is defined , numClients=" + numClients);
            log("server.js:clients is defined , numClients=" + numClients);
        }

        /*
         * end of my edition
         */
        log('Room ' + room + ' has ' + numClients + ' client(s)');
        log('Request to create or join room ' + room);
/******GEt numclient: End********/

        if (numClients === 0) {
            socket.join(room);
            socket.emit('created', room);
        } else if (numClients === 1) {
            io.sockets.in(room).emit('join', room);
            socket.join(room);
            socket.emit('joined', room);
        } else { // max two clients
            socket.emit('full', room);
        }
        socket.emit('emit(): client ' + socket.id + ' joined room ' + room);
        socket.broadcast.emit('broadcast(): client ' + socket.id + ' joined room ' + room);

    });

});

