package io.github.magiccsacademy.csa_project;

public class GameThing {


    public Level l1 = new Level(1,1);
    public Level l3 = new Level(3,1);
    public Level l2 = new Level(2,1);

    public GameThing(){
        l1.addTurn(new Ghostturn(3,1,1,false));
        l1.addTurn(new Ghostturn(2,2,1,false));
        l1.addTurn(new Ghostturn(3,2,1,false));//SPIRAL make it true when spiral works
        //l1.addTurn(new Ghostturn(4,4,1,false,true));//SHIELD remove comment when shield works
        l1.addTurn(new Ghostturn(8,1,1,false));//SPIRAL make it true when spiral works
        l1.addTurn(new Ghostturn(new Ghost("003003")));//when ready make the very last number(add it) the spiral number
        l1.addTurn(new Ghostturn(new Ghost("212121")));//when ready make the very last number(add it) the spiral number
        l1.addTurn(new Ghostturn(new Ghost("01213")));//when ready make the very last number(add it) the spiral number

        l2.addTurn(new Ghostturn(9,3,1,false));//Note this level is really hard, in the actual game fish come from the right and the cat is on the left
        l2.addTurn(new Ghostturn(1,7,1,false));
        //l2.addTurn(new Ghostturn(7,3,1,false,true)); //SHIELD remove comment when shield worksNote this level is really hard, in the actual game fish come from the right and the cat is on the left
        l2.addTurn(new Ghostturn(1,8,1,false));
        l2.addTurn(new Ghostturn(4,3,1,false));
        l2.addTurn(new Ghostturn(1,5,1,false));//add lightning if we get there






        l3.addTurn(new Ghostturn(3,2,1,false));
        l3.addTurn(new Ghostturn(3,2,1,false));
        l3.addTurn(new Ghostturn(2,6,1,false));
        l3.addTurn(new Ghostturn(4,1,1,false, true));
        l3.addTurn(new Ghostturn(15,1,1,false));
        l3.addTurn(new Ghostturn(1,1,1,false));
        l3.addTurn(new Ghostturn(1,1,1,false));
        l3.addTurn(new Ghostturn(1,1,1,false));
        l3.addTurn(new Ghostturn(new Ghost("1111111111")));
        l3.addTurn(new Ghostturn(1,1,1,false));
        l3.addTurn(new Ghostturn(1,1,1,false));
        l3.addTurn(new Ghostturn(new Ghost("0000000000")));
        
    }

}
