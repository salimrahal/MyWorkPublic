#! /bin/bash
echo "transfer starts.."$1
scp $1 todeploy@t8.cofares.net:/home/todeploy
echo "transfer ends." 
