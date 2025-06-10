@echo off
echo Starting Airport Management System...

set PATH_TO_FX="lib"

rem Compile the Java files
javac --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -d bin src/com/airport/app/*.java src/com/airport/model/*.java src/com/airport/repository/*.java

rem Run the application
java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -cp "bin;lib/*" com.airport.app.FlightApp

pause 