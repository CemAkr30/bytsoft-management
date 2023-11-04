#!/bin/bash
echo "$1" " belirtlen konfigürasyona göre proje ayağa kaldırılıyor..."



cd /c/Users/Cem/.m2/repository/com/bytsoft/"$2"/0.0.1-SNAPSHOT
java -jar "$2"-0.0.1-SNAPSHOT.jar --spring.profiles.active="$1"
