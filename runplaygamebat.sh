#!/bin/bash
#
# This script tries to parse Windows bat files for gameplay and run them in a
# UN*X environment

if [ -z $1 ]
then
	echo "Usage: $0 batfilename"
	exit 0
fi

if [ ! -f $1 ]
then
	echo "$0: $1: no such file"
	exit 0
fi

# Use env variable if set 
if [ -z $ANTZELDALOGDIR ]
then
	ANTZELDALOGDIR='/tmp/ants'
fi


# Make sure we got the newest MyBot.jar
make clean all

# This may do the trick ..
grep "^python tools/playgame.py" $1 | sed \
 -e "s@\(--log_dir\)[[:space:]]*[^[:space:]]*@\1 $ANTZELDALOGDIR@" \
 -e "s///g" | \
 bash 
