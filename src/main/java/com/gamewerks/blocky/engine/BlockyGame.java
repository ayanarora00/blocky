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

    /**Write documentation */
    public void FY_shuffle(PieceKind[] arr){

        for (int i = arr.length - 1; i >= 0; i--){
            int j = rand.nextInt(6);
            PieceKind temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }


    }

    /**Write documentation */
    public PieceKind randomblockgenerator(){

        FY_shuffle(arr);
        PieceKind rp;

        if (currindex < 6){
            rp = arr[currindex];
            currindex+=1;
        } else {
            rp = arr[currindex];
            FY_shuffle(arr);
            currindex = 0;
        }
    
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
