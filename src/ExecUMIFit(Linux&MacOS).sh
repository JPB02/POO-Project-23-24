#!/bin/bash
echo "Compiling Main.java..."
javac Main.java
if [ $? -ne 0 ]; then
    echo "Compilation failed, please check your Java code."
    exit 1
fi
echo "Running Main..."
java Main