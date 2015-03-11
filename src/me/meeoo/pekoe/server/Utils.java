/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.meeoo.pekoe.server;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author duncan.berenguier
 */
public class Utils {

    public static void shuffle(List l) {
        Random random = new Random();
        for (int i = l.size() - 1 ; i > 0 ; i--) {
            int j = random.nextInt(i+1);
            Object swap = l.get(i);
            l.set(i, l.get(j));
            l.set(j, swap);
        }
    }

    public static ArrayList<Long> readListLong(DataInput in) throws IOException {
        int size = in.readInt();
        ArrayList<Long> l = new ArrayList<>(size);
        for (int i = 0 ; i < size ; i++) {
            l.add(in.readLong());
        }
        return l;
    }

    public static ArrayList<Integer> readListInt(DataInput in) throws IOException {
        int size = in.readInt();
        ArrayList<Integer> l = new ArrayList<>(size);
        for (int i = 0 ; i < size ; i++) {
            l.add(in.readInt());
        }
        return l;
    }

    public static ArrayList<String> readListString(DataInput in) throws IOException {
        int size = in.readInt();
        ArrayList<String> l = new ArrayList<>(size);
        for (int i = 0 ; i < size ; i++) {
            l.add(in.readUTF());
        }
        return l;
    }

}
