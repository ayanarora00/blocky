# Blocky Game

_(Gamewerks corporation internal code—do not share!)_

## Credits

Primary developers: Prof. Osera

Debuggers : Ayan Arora, Isaac Alexander

Acknowledgments :

Prof Osera, mentor David Rhoades and Grinnell CS department (evening tutor Amelia - with printing the board correctly)

Isaac had installation help from Ben and Charlotte

Visual Studio code IDE

Java version 17


### Resources Used

+ Java documentation (link from Strings lab)
+ Grinnell CS evening tutors (Amelia)
+ Java version 17
+ Visual Studio code IDE
+ Wiki page for Fischer-Yates algorithm as linked through project prompt

## Changelog

For the file BlockyGame.java :

2 new functions were introduced - FY_shuffle and RandomBlockGenerator

FY_Shuffle shuffles the array of pieces using the Fischer-Yates algorithm

The generator function returns a block/piece from the shuffled array

TrySpawnBlock function was changed to use the randomblockgenerator instead of always giving Piece I

Added a break in case right, otherwise exception is thrown in function processmovement

- 1 was changed to 1 to make it go downwards instead of upwards in gravity

processMovement(); was added in step function for mouse clicks to function properly

For Board.java :

pos.row - row was changed to pos.row + row in both collides and AddToWell function to work downwards through the frame instead of upwards (Upwards working possibly goes into negative rows and the visualization doesn't get displayed)

The outer for loop in deleterow was changed to go from n to 0 instead of 0 to n (working from the lower row to upper)
This is done because we start checking from the lowermost row because that is the one that gets cleared

We changed row + 1 to row - 1 within the deleterow for the new assignment (The new row is now the row on top of the existing one that goes which is consistent with the deletion logic/goal i.e. the row above is accessed and displayed once the current one is
cleared)

For Loader.java :

Changed the condition to col < 4 to remove the error of "Index 4 out of bounds for length 4" in Loader function

For BlockyPanel.java :

Changed (Constants.BOARD_HEIGHT - 2) to Constants.BOARD_HEIGHT in function BlockyPanel to make it fit the actual screen/
dimensions of the well. Earlier it was going below the visible area.

In the paintComponent function, Changed from (activePos.row - row + 1) to (activePos.row + row)

                                The earlier code was making the blocks go below the actual frame and 

                                The falling visualization and the collision visualization irregular

In 2nd part of paintComponent function, Changed (row + 1) to (row)

                                        The earlier code was making the blocks go below the actual frame and 
                                        
                                        The collision visualization irregular



~~~console
We had git problems so there isn't a log. Discussed with Professor Osera, though - project good for submission
without the log/commits. All changes made are reported in the changelog.
~~~
