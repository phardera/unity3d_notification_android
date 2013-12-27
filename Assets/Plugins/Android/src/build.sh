#!/bin/sh

ANDROID_JAR=/Users/macaronics/Desktop/applications/adt-bundle-mac-x86_64-20130917/sdk/platforms/android-13/android.jar
UNITY_JAR=/Applications/Unity4.2.1/Unity.app/Contents/PlaybackEngines/AndroidPlayer/bin/classes.jar

javac ./*.java -cp $ANDROID_JAR:$UNITY_JAR -d .
jar cvfM ../AlarmReceiver.jar com/
rm -rf ./com