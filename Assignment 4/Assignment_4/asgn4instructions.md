

# COMP2001 F2022 Assignment 4 Instructions

## Objectives

Swing programming.

The problem concerns creating a control panel for a rudimentary simulation.

## Background

* Videos of the relevant lecture material and demo of the completed assignment will be posted.
* You will need to use **JButtons**, **javax.swing.Timer** and **JTextField** objects which were not specifically covered in lecture, but they use action performed events and **addActionListener()** in the same manner as the swing Components covered in lecture.
* There are code examples from the book that might help
  * There are **JButtons** and **JTextFields** in the AddressBook GUI interface from Chapter 14, and also included in Lab 8. It also has panels, panes, and layout elements that you don't need for this assignment, so skip it if it just confuses you.
  * The MusicPlayer project from chapter 13 has a lot of components you don't need, but it has a few **JButtons** with actionListeners attached that might help you with syntax.
* You will need some more information on the **Timer** object:
  * [The Timer API](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/Timer.html) is the only one you   need to research additional methods (stop, start, restart, change the delay) for this assignment.
* But here are some (unnecessary) links for the other components if you want to explore further:
  * JTextField abstract class javadoc shows it uses [set/getText](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/text/JTextComponent.html#setText(java.lang.String)) the same way a JLabel does. If you really need more methods, you can also look in the [concrete class javadoc](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/JTextField.html) if you want more information.
  * many JButton methods are in the [AbstractButton class](https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/javax/swing/AbstractButton.html)

## Activity

* Checkout comp2001-assign4 repository from github classroom for the following credit work
* Most of the JUNIT tests are provided, but some are held back, including tests of the file picker, the menu items, and the speed/interval number label.
  * Some JUNIT tests for the **JTextField** contents and Menu Items are withheld from distribution, but will be used in evaluation.
* Create a control panel **JFrame** window with the title "Sim Control" for the operation of the simulator which will pass the unit tests. The required functionality and controls are demonstrated on an accompanying demonstration video and listed here:
  * A _File_ menu
    * A _Load Settings_ menu item which opens a file picker. Picking a file causes a JOptionPane to open displaying a not implemented message.
    * A _Save Settings_ menu item which opens a file picker. Picking a file causes a JOptionPane to open displaying a not implemented message.
    * A _Quit_ menu item that shuts down the simulation and the control panel.
  * A **JLabel** message bar with the words "Sim Running/Not Running" as appropriate.
  * A _Run_ JButton that starts a javax.swing.Timer object causing the simulator to run using ongoing action events.
  * A _Stop_ JButton that stops the Timer
  * A _Step_ JButton that move the simulation forward one step.
  * A _Slower_ JButton that increases the delay between timer events 2x
  * A JTextField that displays the **Timer** delay in millisecs
  * A _Faster_ JButton that reduces the **Timer** delay 0.5x
  * A _Run To:_ JButton that advances the simulation to a specific step.
  * A **JTextField** that displays the run to step number
  * A _Reset_ JButton that resets the simulation
  * A **JLabel** status bar that shows the step number and Fox/Rabbit population. It may show the words "Status Bar" before the simulation is initialized.
* The layout of the control panel components is not important in the assignment evaluation. Do not worry about duplicating the layout in the demo video.

* To get the unit tests to work:
  * The buttons, menus and menu items must have the same names as listed here and on the demo video in order for the unit tests to locate those swing components. The control panel frame must have the title "Sim Control" in order for the junits to locate it.
  * Your control panel object Class must be called **SimController** in order for the junit tests to construct an instance of it.
    * Creating an instance of SimController should create the control panel object and the simulator object in a state ready for use using the buttons on the control panel.
    * The junit tests expect to call a `SimController.quit()` method to shut down the swing components and the simulation. The quit method should:
      * turn off the **Timer** object
      * shut down the simulation
      * set any control panel `JFrame.visibility()` to false and then call the frame's `dispose()` method
    * When the simulation is shut down, it should also set any **JFrame**(s) it is using to `visibility(false)` and then `dispose()` the **JFrames**.
    * If you leave simulations, control panels or **JFrames** active after quit(), then the junits will not terminate correctly.