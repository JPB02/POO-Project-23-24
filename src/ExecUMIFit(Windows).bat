@echo off
echo Compiling Main.java...
javac Main.java
if %ERRORLEVEL% neq 0 goto error
echo Running Main...
java Main
goto end

:error
echo Compilation failed, please check your Java code.

:end
pause