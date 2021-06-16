# Maestri del Rinascimento Board Game - AM59
Final examination for "Computer Science and Engineering" held at Politecnico di Milano (2020/2021)
<img src="MoR.png" width=300px height=400px align="right">

## Authors
| Personal Data | Username |
|:-----------------------|:------------------------------------:|
| Anozie Hanson Obinna | [@hansonobi9](https://github.com/hansonobi9) |
| Chen Peizhou | [@cheeeeenais](https://github.com/cheeeeenais) |
| Cicellini Raffaele | [@raffaelecicellini](https://github.com/raffaelecicellini) |

## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/raffaelecicellini/ingswAM2021-Anozie-Chen-Cicellini/tree/master/src/main/java/it/polimi/ingsw/model) |
| Complete rules | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/raffaelecicellini/ingswAM2021-Anozie-Chen-Cicellini/tree/master/src/main/java/it/polimi/ingsw/model) |
| Socket |[![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/raffaelecicellini/ingswAM2021-Anozie-Chen-Cicellini/tree/master/src/main/java/it/polimi/ingsw/server) |
| GUI | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/raffaelecicellini/ingswAM2021-Anozie-Chen-Cicellini/tree/master/src/main/java/it/polimi/ingsw/client/gui) |
| CLI |[![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/raffaelecicellini/ingswAM2021-Anozie-Chen-Cicellini/tree/master/src/main/java/it/polimi/ingsw/client/cli) |
| Multiple games | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/raffaelecicellini/ingswAM2021-Anozie-Chen-Cicellini/tree/master/src/main/java/it/polimi/ingsw/server)|
| Local game | [![GREEN](http://placehold.it/15/44bb44/44bb44)](https://github.com/raffaelecicellini/ingswAM2021-Anozie-Chen-Cicellini/tree/master/src/main/java/it/polimi/ingsw/client) |
| Editor | [![RED](http://placehold.it/15/f03c15/f03c15)](https://github.com/raffaelecicellini/ingswAM2021-Anozie-Chen-Cicellini) |
| Persistency | [![RED](http://placehold.it/15/f03c15/f03c15)](https://github.com/raffaelecicellini/ingswAM2021-Anozie-Chen-Cicellini) |
| Resilience to disconnections | [![RED](http://placehold.it/15/f03c15/f03c15)](https://github.com/raffaelecicellini/ingswAM2021-Anozie-Chen-Cicellini) |

#### Legend
[![RED](http://placehold.it/15/f03c15/f03c15)]() Not Implemented &nbsp;&nbsp;&nbsp;&nbsp;[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)]() Implementing&nbsp;&nbsp;&nbsp;&nbsp;[![GREEN](http://placehold.it/15/44bb44/44bb44)]() Implemented

<!--
[![RED](http://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](http://placehold.it/15/44bb44/44bb44)](#)
-->

## Instructions for build and execution:

The jar is the same for both server and client. You can build it under your preferred OS and launch it following the instructions below.
The project has been developed using JAVA 15.0.2 and JavaFX 14.0.1. 
For the CLI it is recommended to have the terminal in fullscreen, to use a monospace font with size 12 (we tested with Unifont and Windows Terminal). If these requirements are not met, we cannot guarantee the correct functioning of the CLI.

### Build instructions:

The jar is built using "Maven Assembly Plugin": inside the IntelliJ IDE, clone the repository and launch "package" under "lifecycle" section in the Maven toolbar.
The generated jar will be placed in the "target" folder of your project.

If you want to use Maven in the terminal, you can execute:
```
mvn clean package
```
In the project's root folder (same folder of "pom.xml").
The generated jar will be placed in the "target" folder of your project.

### Execution instructions:

#### Server

In terminal execute:

```
java -jar Maestri-AM59.jar server
```
The server will launch and you can choose the port on which the server will listen.

#### Client

To launch the game on the command line interface (CLI)  make sure your terminal is in fullscreen mode,
then execute:

```
java -jar Maestri-AM59.jar cli
```

To launch the program with the graphical user interface (GUI), double-click on the jar.
You can also execute in the terminal:

```
java -jar Maestri-AM59.jar gui
```
or simply:
```
java -jar Maestri-AM59.jar
```

## Test cases
All tests in model and controller has a classes' coverage at 100%.

**Coverage criteria: methods.**

| Package |Tested Class | Coverage |
|:-----------------------|:------------------|:------------------------------------:|
| Controller | Global Package | 10/12 (83%)
| Model | Global Package | 250/270 (92%)

## Deliveries
In the "deliveries" folder you will find:
* Initial UML diagram (done with StarUML)
* Final UML diagram (generated with Intellij IDEA)
* Javadoc
* Final jar (the same for both the client and the server)
* A pdf file describing the Communication Protocol
