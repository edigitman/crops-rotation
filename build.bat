del app.jar
javac model/*.java *.java
jar cvfm app.jar manifest.txt model/*class *.class
del model\*.class *.class