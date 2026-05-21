package io.github.magiccsacademy.csa_project;

public class GameThing {


    public Level l1 = new Level(1,1);
    public Level l3 = new Level(1,1);

    public GameThing(){
       /* l3.addTurn(new Ghostturn(3,2,1,false));
        l3.addTurn(new Ghostturn(3,2,1,false));
        l3.addTurn(new Ghostturn(2,6,1,false));*/
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
