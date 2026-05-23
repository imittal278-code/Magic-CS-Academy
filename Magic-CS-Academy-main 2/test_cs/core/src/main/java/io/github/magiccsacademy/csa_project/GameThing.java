package io.github.magiccsacademy.csa_project;

public class GameThing {

    public Level l3 = new Level(3, 1);
    public Level l4 = new Level(4, 1);
    public Level l1 = new Level(1,1);
    public Level l2 = new Level(2,2);

    public GameThing() {

        l3.addTurn(new Ghostturn(3, 2, 1, false,false,false));
        l3.addTurn(new Ghostturn(3, 2, 1, false,false,false));
        l3.addTurn(new Ghostturn(2, 6, 1, false,false,false));
        l3.addTurn(new Ghostturn(4, 1, 1, false, true,false));
        l3.addTurn(new Ghostturn(15, 1, 1, false,false,false));
        l3.addTurn(new Ghostturn(1, 1, 1, false,false,false));
        l3.addTurn(new Ghostturn(1, 1, 1, false,false,false));
        l3.addTurn(new Ghostturn(1, 1, 1, false,false,false));
        l3.addTurn(new Ghostturn(new Ghost("1111111111",true)));
        l3.addTurn(new Ghostturn(1, 1, 1, false,false,false));
        l3.addTurn(new Ghostturn(1, 1, 1, false,false,false));
        l3.addTurn(new Ghostturn(new Ghost("0000000000",true)));


        // ONE TURN USING NEW CONSTRUCTOR NEEDS TO BE WRITTEN
        l4.addTurn(new Ghostturn(6, 3, 1, false,false,false));
        l4.addTurn(new Ghostturn(1, 5, 1, false,false,false));
        l4.addTurn(new Ghostturn(20, 1, 1, false,false,false));// lightning
        l4.addTurn(new Ghostturn(new Ghost("121212"))); // Lightbulb
        l4.addTurn(new Ghostturn(new Ghost("13131313")));// anglerfish boss
        l4.addTurn(new Ghostturn(new Ghost("12321")));// lightbulb
        l4.addTurn(new Ghostturn(new Ghost("0112323110")));// anglerfish boss
        l4.addTurn(new Ghostturn(1, 5, 1, false,false,false));// lightbulb
        l4.addTurn(new Ghostturn(new Ghost("213020132")));// anglerfish boss last




        l1.addTurn(new Ghostturn(3,1,1,false,false,false));
        l1.addTurn(new Ghostturn(2,2,1,false,false,false));
        l1.addTurn(new Ghostturn(3,2,1,false,false,false));//SPIRAL make it true when spiral works
        l1.addTurn(new Ghostturn(4,4,1,false,true,false));//SHIELD remove comment when shield works
        l1.addTurn(new Ghostturn(8,1,1,false,false,false));//SPIRAL make it true when spiral works
        l1.addTurn(new Ghostturn(new Ghost("003003",true)));//when ready make the very last number(add it) the spiral number
        l1.addTurn(new Ghostturn(new Ghost("212121",true)));//when ready make the very last number(add it) the spiral number
        l1.addTurn(new Ghostturn(new Ghost("01213",true)));//when ready make the very last number(add it) the spiral number

        l2.addTurn(new Ghostturn(9,3,1,false,false,false));//Note this level is really hard, in the actual game fish come from the right and the cat is on the left
        l2.addTurn(new Ghostturn(1,7,1,false,false,false));
        l2.addTurn(new Ghostturn(7,3,1,false,true,false)); //SHIELD remove comment when shield worksNote this level is really hard, in the actual game fish come from the right and the cat is on the left
        l2.addTurn(new Ghostturn(1,8,1,false,false,false));
        l2.addTurn(new Ghostturn(4,3,1,false,false,false));
        l2.addTurn(new Ghostturn(1,5,1,false,false,false));//add lightning if we get there


        

    }

}