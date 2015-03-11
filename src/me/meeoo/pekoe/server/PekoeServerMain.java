/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.meeoo.pekoe.server;

/**
 *
 * @author duncan.berenguier
 */
public class PekoeServerMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PekoeGame pekoeGame = new PekoeGame("6716424286428");
        System.err.println(pekoeGame.getVisualHash());
    }

}
