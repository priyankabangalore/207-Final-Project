# Tech Conference Platform

## Table of Contents
* Entities
* Use Cases
* Controllers
* Presenters
* Gateways
* Exceptions
* Design Patterns Listing
* Phase 2 Features Description

## General Information
This project is Phase 2 of a tech-conference system which allows users to view events, sign-up for events and message
other users.
In this Phase 2 version, there are multiple event types, messaging enhancements, accessibility/dietary
accommodations, room equipment constraints, multi-day conferences, and various user-friendly upgrades.

## Setup
Please make sure your jdk version is 1.8. You can find this by executing "java -version" in your system terminal.

It is recommended that you run this program in JetBrains IntelliJ or build it in to a jar file.

1. Clone this repo.
2. Open IntelliJ IDEA and create a new project from existing sources.
* The root of the project should be the phase2 folder i.e. where this readme file is located.
3. Create a configuration.
* Main class should be "Controllers.Main".
* Working directory must be the root of the project
* If you want to build it in to a jar file,
* create a project artifact in the Project Structure menu with the provided MANIFEST.MF in META-INF
* TOGGLE "Include in project build"
4. Build and run this configuration.
* If you want to run of the single jar file,
* Open up your system's terminal and navigate to where the jar file is.
* Then run it with "java -jar ./phase2.jar"

If you don't want to use an ide, you need to build the program yourself.

It is highly recommended that you build the program in to a jar file, and run it with instructions above.

1. Clone this repo and build the project with packages in phase2/src.
2. Run the binary .class file with the following options.
* The working directory of your console/terminal should be the root of the phase2 folder.
* The classpath is where the compiled files are located.

## Using the Program
After the previous setup steps, the program should be running.
Options will be given in the console for you to choose.
Please enter the number option to select an option.

When there is no admin in the system at start up, a default administrator will be created with:
ID: "0"
Password: “adminDefaultPW"

#### Authors
* Benjamin Matthew Tudor Price
* Priyanka Michelle Bangalore
* Daniel Michael Oleksiyenko-Stech
* Stephanie Julia Cristea
* Tian Du
* Fizzah Mansoor
* Syeda Areej Faisal Naqvi
* Tianhe Zhang
