/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserverapplication;

import java.util.Date;

/**
 *
 * @author muham
 */
public class GameCountPerDay {
    private Date date;
    private int gameCount;

    public GameCountPerDay(Date date, int gameCount) {
        this.date = date;
        this.gameCount = gameCount;
    }

    public Date getDate() {
        return date;
    }

    public int getGameCount() {
        return gameCount;
    }
}