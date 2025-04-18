package com.codered.ecomerce.model;

public class BirthdayCake {
    private static String song;
    private static String name;
    
    BirthdayCake(String name, String song){
        this.song = song;
        this.name = name;
    }

    public static void main(String args[]){
        System.out.println("Happy birthday " + name);
        System.out.println(song);
    }
}
