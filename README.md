# Advanced Programming Project
# Stick-Hero (Harry Potter Theme)

## Authors
- Ananya Sachdev (Roll No. 2022069)
- Maulik Mahey (Roll No. 2022282)

# Overview
We have created a Harry Potter-themed stick hero game and added similar and existing features to the stick hero game. Coming to the creativity part, we have added music to the game. 
As per the programming practices, we have added 3 design patterns and 1 JUnit tests in the following classes:-

1. Design Patterns
   -> `PillarFactory.java`: Factory & Flyweight
   -> `Character.java`: Singleton

2. JUnit Tests
   -> `testScore.java` - Here, we check whether the score starts from zero.

# Implementation Details

The game starts with the **Start Screen**. You have been given two options first **Start Game** and second **How to Play**. The second option provides you with instructions to play the game and follow all the game rules according to it. When you start with the game, two button keys are provided for the gameplay; first is the **Grow Button Key**, and  second is the **Invert Button Key**. The keys' Instructions are in the **How To Play Screen**; please follow them accordingly. You can **Pause Game** in between the gameplay; there are options for **Repeat**,**Resume** & **Exit** the game.

# How to Run the Project
Follow these steps to run the file:
1. Add JavaFX Library in the project structures.
2. Edit the running configuration by adding the **Main Class**, which is the `StickHero.java` and add **VM method** with the JavaFX library folder path. The **VM Path** for **Windows** is `--module-path "\path\to\javafx-sdk-21.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.media`; for **Mac & Linux** it is `--module-path /path/to/javafx-sdk-21.0.1/lib --add-modules javafx.controls,javafx.fxml,javfx.media`.

