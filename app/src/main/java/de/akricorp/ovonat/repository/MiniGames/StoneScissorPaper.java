package de.akricorp.ovonat.repository.MiniGames;

import java.util.Random;

/**
 * Created by Hannes on 25.08.2015.
 */
public class StoneScissorPaper extends Object {

    int ovoWinCount=0;
    int playerWinCount=0;
    int drawCount=0;

    private static final int STONE = 0;
    private static final int SCISSOR = 1;
    private static final int PAPER = 2;

    Random r = new Random();

    private void stoneUsed(){
        int ovonatsChoice = ovoRandom();

        if(ovonatsChoice==STONE){
            updateDrawCount();
        }
        if(ovonatsChoice==SCISSOR){
            updatePlayerCount();
        }
        if(ovonatsChoice== PAPER){
            ovoWinUpdate();
        }
    }

    private void scissorUsed(){
        int ovonatsChoice = ovoRandom();

        if(ovonatsChoice==STONE){
            ovoWinUpdate();
        }
        if(ovonatsChoice==SCISSOR){
            updateDrawCount();
        }
        if(ovonatsChoice== PAPER){
            updatePlayerCount();
        }
    }

    private void paperUsed(){

        int ovonatsChoice = ovoRandom();

        if(ovonatsChoice==STONE){
            updatePlayerCount();
        }
        if(ovonatsChoice==SCISSOR){
            ovoWinUpdate();
        }
        if(ovonatsChoice== PAPER){
            updateDrawCount();
        }
    }

    private int ovoRandom() {
        int ovoChoice = r.nextInt(2);
        return ovoChoice;
    }

    private void ovoWinUpdate() {
        ovoWinCount++;
    }

    private void updatePlayerCount() {
        playerWinCount++;
    }

    private void updateDrawCount() {
        drawCount++;
    }

}