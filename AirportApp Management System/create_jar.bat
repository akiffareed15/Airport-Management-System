@echo off
REM Set JavaFX path
set "JAVAFX_PATH=E:\javafx-sdk-24.0.1\lib"

REM Clean old build
if exist bin rmdir /s /q bin
if exist AirportApp.jar del AirportApp.jar

REM Check Java installation
java -version
if %ERRORLEVEL% neq 0 (
    echo Java is not properly installed or not in PATH.
    pause
    exit /b 1
)

REM Create bin directory
mkdir bin

REM Compile Java files with JavaFX modules
javac --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -d bin -cp "lib/*" src/module-info.java src/com/airport/app/*.java src/com/airport/model/*.java src/com/airport/repository/*.java
if %ERRORLEVEL% neq 0 (
    echo.
    echo Compilation failed. Please check the errors above.
    pause
    exit /b 1
)

REM Copy FXML and CSS resources
xcopy /Y /S src\com\airport\app\*.fxml bin\com\airport\app\
xcopy /Y /S src\com\airport\app\*.css bin\com\airport\app\
REM Copy style.css to bin root for resource loading
copy /Y src\style.css bin\style.css

REM Create JAR file with correct main class
jar --create --file AirportApp.jar --main-class com.airport.app.FlightApp -C bin .
if %ERRORLEVEL% neq 0 (
    echo.
    echo JAR creation failed. Please check the errors above.
    pause
    exit /b 1
)

echo.
echo Success! Created AirportApp.jar
echo You can now use Launch4j to create the EXE file.
pause 