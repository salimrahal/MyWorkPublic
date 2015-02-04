/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var io = require('socket.io')();
io.on('connection', function(socket){});
io.listen(3000);