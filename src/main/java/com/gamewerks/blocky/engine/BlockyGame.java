package com.gamewerks.blocky.engine;

import com.gamewerks.blocky.util.Constants;
import com.gamewerks.blocky.util.Position;

import java.util.Random;
import java.util.random.*;

public class BlockyGame {
    private static final int LOCK_DELAY_LIMIT = 30;
    
    private Board board;
    private Piece activePiece;
    private Direction movement;
    
    private int lockCounter;

    PieceKind[] arr = {PieceKind.I, PieceKind.J, PieceKind.L, PieceKind.O, PieceKind.S, PieceKind.T, PieceKind.Z};
    public int currindex = 0;

    // Initializing random onject to create a random integer later on
    Random rand = new Random();
    
    public BlockyGame() {
        board = new Board();
        movement = Direction.NONE;
        lockCounter = 0;
        trySpawnBlock();
    }

    /**This function is used to shuffle the array of the kinds of pieces using the Fischer-Yates algorithm
     * 
     * @param The array which holds all the piecekinds of type PieceKind[]
     * @return returns nothing
     */
     
    public void FY_shuffle(PieceKind[] arr){

        // Traversing the array in backward direction (as according to the wiki page for the algorithm)
        for (int i = arr.length - 1; i >= 0; i--){

            // Declaring j as a random integer between 0 and 6
            int j = rand.nextInt(6);

            // Temporary object stores the object at current index (during traversal)
            PieceKind temp = arr[i];

            // The element at that position is then assigned to the element at the index of the randomly generated number 
            arr[i] = arr[j];

            // The element at the randomly generated number index is then assigned to the original value of the element
            // at index i
            arr[j] = temp;
        }


    }

    /**This function replies upon the FY_shuffle function and generates random blocks using that and the algorithm
     * provided in the project prompt
     * 
     * @Param doesn't take anything as its parameter
     * @return It returns a piecekind
     * 
    */
    public PieceKind randomblockgenerator(){

        // Shuffling the array
        FY_shuffle(arr);

        // Declaring piecekind object
        PieceKind rp;

        // If the index is less than 6, Set the element at the index of the shuffled array as the piecekind to return and 
        // increment index tracker

        if (currindex < 6){
            rp = arr[currindex];
            currindex+=1;

        // Else, assign the last element from the array to the piecekind object to be returned.
        // Then, shuffle once again and set index back to 0

        } else {
            rp = arr[currindex];
            FY_shuffle(arr);
            currindex = 0;
        }
    
        // Returning the piecekind
        return rp;
    
    }



    private void trySpawnBlock() {

        if (activePiece == null) {

            // This was changed to use the randomblockgenerator instead of always giving Piece I
            activePiece = ((new Piece(randomblockgenerator(),
                            new Position(1, Constants.BOARD_WIDTH / 2 - 2))));

            if (board.collides(activePiece)) {
                System.exit(0);
            }
        }
    }
    
    private void processMovement() {
        Position nextPos;
        switch(movement) {
        case NONE:
            nextPos = activePiece.getPosition();
            break;
        case LEFT:
            nextPos = activePiece.getPosition().add(0, -1);
            break;
        case RIGHT:
            nextPos = activePiece.getPosition().add(0, 1);
            break; //Added a break in case right, otherwise exception is thrown
        default:
            throw new IllegalStateException("Unrecognized direction: " + movement.name());
        }
        if (!board.collides(activePiece.getLayout(), nextPos)) {
            activePiece.moveTo(nextPos);
        }
    }
    
    private void processGravity() {

        // - 1 was changed to 1 to make it go downwards instead of upwards
        Position nextPos = activePiece.getPosition().add(1, 0);
        if (!board.collides(activePiece.getLayout(), nextPos)) {
            lockCounter = 0;
            activePiece.moveTo(nextPos);
        } else {
            if (lockCounter < LOCK_DELAY_LIMIT) {
                lockCounter += 1;
            } else {
                board.addToWell(activePiece);
                lockCounter = 0;
                activePiece = null;
            }
        }
    }
    
    private void processClearedLines() {
        board.deleteRows(board.getCompletedRows());
    }
    
    public void step() {

        trySpawnBlock();
        processMovement(); // added for mouse clicks to function properly
        processGravity();
        processClearedLines();

    }
    
    public boolean[][] getWell() {
        return board.getWell();
    }
    
    public Piece getActivePiece() { return activePiece; }
    public void setDirection(Direction movement) { this.movement = movement; }
    public void rotatePiece(boolean dir) { activePiece.rotate(dir); }
}
