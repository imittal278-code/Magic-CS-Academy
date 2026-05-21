package io.github.magiccsacademy.csa_project;

public class GameThing {

    public Level l3 = new Level(1, 1);

    public Level l4 = new Level(4, 1);

    public GameThing() {

        l3.addTurn(new Ghostturn(3, 2, 1, false));
        l3.addTurn(new Ghostturn(3, 2, 1, false));
        l3.addTurn(new Ghostturn(2, 6, 1, false));
        l3.addTurn(new Ghostturn(4, 1, 1, false, true));
        l3.addTurn(new Ghostturn(15, 1, 1, false));
        l3.addTurn(new Ghostturn(1, 1, 1, false));
        l3.addTurn(new Ghostturn(1, 1, 1, false));
        l3.addTurn(new Ghostturn(1, 1, 1, false));
        l3.addTurn(new Ghostturn(new Ghost("1111111111")));
        l3.addTurn(new Ghostturn(1, 1, 1, false));
        l3.addTurn(new Ghostturn(1, 1, 1, false));
        l3.addTurn(new Ghostturn(new Ghost("0000000000")));
        l4.addTurn(new Ghostturn(6, 3, 1, false));
        l4.addTurn(new Ghostturn(1, 5, 1, false));

        // ONE TURN USING NEW CONSTRUCTOR NEEDS TO BE WRITTEN

        l4.addTurn(new Ghostturn(20, 1, 1, false));// lightning
        l4.addTurn(new Ghostturn(new Ghost("121212"))); // Lightbulb
        l4.addTurn(new Ghostturn(new Ghost("13131313")));// anglerfish boss
        l4.addTurn(new Ghostturn(new Ghost("12321")));// lightbulb
        l4.addTurn(new Ghostturn(new Ghost("0112323110")));// anglerfish boss
        l4.addTurn(new Ghostturn(1, 5, 1, false));// lightbulb
        l4.addTurn(new Ghostturn(new Ghost("213020132")));// anglerfish boss last

    }

}