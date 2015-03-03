//running video peer to peer - step 6:
cd /home/salim/Development/webrtc/webrtc-My-codelab/public_html/complete/step6

//run nodejs with logs:
//forever -o out.log -e error.log server.js &
forever start -l forever.log -o out.log -e error.log server.js
forever start -o out.log -e error.log server.js
//restart the diamon
forever restart -o out.log -e error.log server.js

//run nodejs without logs:
//old command: forever server.js &
forever start server.js

//stop a process or a node js server
forever stop {PID}

/**************install node static*************/
sudo npm install node-static 
