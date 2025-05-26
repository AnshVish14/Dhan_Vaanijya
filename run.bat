@echo off
title DHAN VAANIJYA - Java Build & Run Script

:: ========================
:: Clean Previous Build
:: ========================
echo [🔁] Cleaning previous builds...
if exist bin (
    rmdir /s /q bin
)
mkdir bin

:: ========================
:: Compile Java Files
:: ========================
echo [⚙️] Compiling Java source files...

:: Check if 'lib' folder exists
if exist lib (
    set CLASSPATH=lib\*;.
) else (
    set CLASSPATH=.
)

javac -d bin -cp "%CLASSPATH%" src\com\dhanvaanijya\**\*.java

if %ERRORLEVEL% NEQ 0 (
    echo [❌] Compilation failed!
    pause
    exit /b
)

:: ========================
:: Run the Application
:: ========================
echo [🚀] Running DHAN VAANIJYA...
java -cp "bin;%CLASSPATH%" com.dhanvaanijya.ui.Main

:: ========================
:: End
:: ========================
echo [✅] Program terminated.
pause
