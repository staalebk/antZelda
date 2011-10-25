@echo off
cd src
rem clean
del /q *.class
del /q MyBot.jar
rem compile
javac MyBot.java
rem package
jar cvfm MyBot.jar Manifest.txt *.class 
rem clean
del /q *.class
move MyBot.jar ../
cd..