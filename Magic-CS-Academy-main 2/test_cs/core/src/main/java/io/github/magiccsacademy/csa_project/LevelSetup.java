package io.github.magiccsacademy.csa_project;


/**
 * Sets up the levels with all their ghostTurns
 */
public class LevelSetup {

    /**
     * Level 3 object
     */
    private final Level l3;

    /**
     * Level 4 object
     */
    private final Level l4;

    /**
     * Level 1 object
     */
    private final Level l1;

    /**
     * Level 2 object
     */
    private final Level l2;

    /**
     * Level 5 object
     */
    private final Level l5;


    /**
     * Initializes the 5 level fields, and adds their ghostTurns to them
     * @param game The shared game class (used for accessing some assets)
     */
    public LevelSetup(Main game) {
        l3 = new Level(3, 1,game);
        l4 = new Level(4, 1,game);
        l1 = new Level(1,1,game);
        l2 = new Level(2,8,game);
        l5 = new Level(5, 1,game);
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


        l4.addTurn(new Ghostturn(6, 3, 1, false,false,false));
        l4.addTurn(new Ghostturn(1, 5, 1, false,false,false));
        int[] arr2 = {1,1,1,2,2,2,3,3,3,4};
        l4.addTurn(new Ghostturn(1, arr2));
        l4.addTurn(new Ghostturn(20, 1, 1, false,false,false));
        l4.addTurn(new Ghostturn(new Ghost("121212")));
        l4.addTurn(new Ghostturn(new Ghost("13131313")));
        l4.addTurn(new Ghostturn(1,5,1,false,false,false));
        l4.addTurn(new Ghostturn(1, 10, 1, false, false, false));
        l4.addTurn(new Ghostturn(1, 5, 1, false,false,false));
        l4.addTurn(new Ghostturn(new Ghost("213020132")));



        l1.addTurn(new Ghostturn(3,1,1,false,false,false));
        l1.addTurn(new Ghostturn(2,2,1,false,false,false));
        l1.addTurn(new Ghostturn(3,2,1,false,false,false));
        l1.addTurn(new Ghostturn(4,4,1,false,true,false));
        l1.addTurn(new Ghostturn(8,1,1,false,false,false));
        l1.addTurn(new Ghostturn(1, 6, 1, false, false, false));
        l1.addTurn(new Ghostturn(new Ghost("212121")));
        l1.addTurn(new Ghostturn(1, 5, 1, false, false, false));


        l2.addTurn(new Ghostturn(9,3,1,true,false,false));
        l2.addTurn(new Ghostturn(1,7,1,true,false,false));
        l2.addTurn(new Ghostturn(7,3,1,true,true,false));
        l2.addTurn(new Ghostturn(1,8,1,true,false,false));
        l2.addTurn(new Ghostturn(4,3,1,true,false,false));
        l2.addTurn(new Ghostturn(1,5,1,true,false,false));

        l5.addTurn(new Ghostturn(8,2,1,false,false,false));
        l5.addTurn(new Ghostturn(new Ghost("101303101",true)));
        l5.addTurn(new Ghostturn(12, 1, 1, false, false, false));
        l5.addTurn(new Ghostturn(new Ghost("221303122",true)));
        l5.addTurn(new Ghostturn(7, 4, 1));
        l5.addTurn(new Ghostturn(new Ghost("3012301203",true)));


        int[] arr = {2,2,3,3,3,3,3,4,4,4,4,4,4,4,4,4,4,5};
        l5.addTurn(new Ghostturn(1, arr));
        l5.addTurn(new Ghostturn(new Ghost("0102301023010",true)));

        

    }


    /**
    *  Returns the level 1 object
    *  @return the level 1 object
    */
    public Level getLevel1(){
        return l1;
    }

    /**
    *  Returns the level 2 object
    *  @return the level 2 object
    */
    public Level getLevel2(){
        return l2;
    }

    /**
    *  Returns the level 3 object
    *  @return the level 3 object
    */
    public Level getLevel3(){
        return l3;
    }

    /**
    *  Returns the level 4 object
    *  @return the level 4 object
    */
    public Level getLevel4(){
        return l4;
    }

    /**
    *  Returns the level 5 object
    *  @return the level 5 object
    */
    public Level getLevel5(){
        return l5;
    }

}
