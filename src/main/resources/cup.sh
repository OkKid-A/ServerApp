#! /bin/bash
echo "STARTING JCUP COMPILING Proyecto"
java -jar "/home/okkid-a/Documents/Compi 1/ServerApp/src/main/resources/Libs/java-cup-11b.jar" -parser CreadorWebParser "//home/okkid-a/Documents/Compi 1/ServerApp/src/main/resources/creadorWebParser.cup"


echo "MOVING"
mv CreadorWebParser.java "/home/okkid-a/Documents/Compi 1/ServerApp/src/main/java/edu/cunoc/Nucleo"
mv sym.java "/home/okkid-a/Documents/Compi 1/ServerApp/src/main/java/edu/cunoc/Nucleo"
