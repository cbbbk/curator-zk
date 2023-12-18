package com.whh.curator;

public class LockTest {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        Thread app1 = new Thread(ticket, "app1");
        Thread app2 = new Thread(ticket, "app2");
        Thread app3 = new Thread(ticket, "app3");
        app1.start();
        app2.start();
        app3.start();
    }
}
