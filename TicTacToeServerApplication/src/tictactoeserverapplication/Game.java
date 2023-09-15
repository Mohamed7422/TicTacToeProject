/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserverapplication;

/**
 *
 * @author muham
 */
import java.util.Date;

public class Game {
    private int id;
    private String playerName;
    private String opponentName;
    private Date dateTime;
    private String gameResult;
    private boolean isRecorded;

    public Game(int id, String playerName, String opponentName, Date dateTime, String gameResult, boolean isRecorded) {
        this.id = id;
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.dateTime = dateTime;
        this.gameResult = gameResult;
        this.isRecorded = isRecorded;
    }

    public int getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getGameResult() {
        return gameResult;
    }

    public boolean isRecorded() {
        return isRecorded;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", opponentName='" + opponentName + '\'' +
                ", dateTime=" + dateTime +
                ", gameResult='" + gameResult + '\'' +
                ", isRecorded=" + isRecorded +
                '}';
    }
}

