package com.mygdx.game;

/**
 * Created by Immortan on 1/23/2016.
 */
public class Player {

    //The default ant used for testing purposes
    //Will not make it into the final product
    public static final int ANT_DEFAULT = 0;

    //+Fire ant workers have powerful poison and attacks
    //+Reproduce quickly
    //-Completely lacking in forager ants
    //-Soldier ant is weaker vs the same class
    public static final int ANT_FIRE = 1;

    //+Can harvest wood sources
    //+Excellent negotiations
    //-Relatively slow reproduction
    //-Expensive hive construction
    public static final int ANT_CARPENTER = 2;

    //+Extremely powerful poison
    //+Massive hit point and armor bonus
    //-Prohibitively expensive
    //-Much more difficult negotiations
    //
    public static final int ANT_BULLET = 3;

    //+Very powerful worker ants
    //+Immune to weather effects
    //-Cannot construct hive and queen always exposed
    //-Queen cannot lay eggs in transit
    public static final int ANT_ARMY = 4;

    //+Very fast
    //+High chance to dodge attacks
    //-Limited colony size
    //-No flyers
    public static final int ANT_CRAZY = 5;

    //+Extremely fast reproduction
    //+Advanced forager ants
    //-Extremely weak in combat
    public static final int ANT_SUGAR = 6;

    //+Extreme bonus from wood resources
    //+Very defensive hives
    //+Higher reproduction
    //-Very slow
    //-Can only use plant resources
    public static final int TERMITE_WHITE = 7;

    //+Extreme bonus from wood resources
    //+Very defensive hives
    //+Higher health
    //-Very slow
    //-Can only use plant resources
    public static final int TERMITE_BROWN = 8;

    //+Does not use up resources
    //+High agility
    //+High movement speed
    //+Suicide attack
    //-Can only feed on honey
    //Immobilized during weather
    public static final int BEE_HONEY = 9;

    //+Very powerful, master of the skies
    //Powerful poison, health, armor, armor penetration, mobility
    //-Low agility
    //-Slow and expensive reproduction rate
    //Immobilized during weather
    public static final int BEE_WASP = 10;

    //+High agility
    //+Fast reproduction
    //-No poison
    //Immobilized during weather
    public static final int BEE_HORNET = 11;


    public static final String[] factionString={"Ant_Default","Ant_Fire","Ant_Carpenter",
            "Ant_Bullet","Ant_Army","Ant_Crazy","Ant_Sugar","Termite_White","Termite_Brown"
            ,"Bee_Honey","Bee_Wasp","Bee_Hornet"};

    public static final int HUMAN = 1;
    public static final int NONE = 2;
    public static final int COMPUTER = 3;

    private int control;
    private int biomass=0;
    private int team;
    private int faction;

    /*
    //Constructor for advanced AI types
    //Unsure if will be implemented
    public Player(int team, int faction, int control, AI ai)
    {

    }
     */

    public Player(int team, int faction, int control)
    {
        this.team=team;
        this.control=control;
        this.faction=faction;
    }

    public void setBiomass(int biomass)
    {
        this.biomass = biomass;
    }

    public int getBiomass() {return biomass;}

    public int getControl() {
        return control;
    }

    public void setControl(int control) {
        this.control = control;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getFaction() {
        return faction;
    }

    public void setFaction(int faction) {
        this.faction = faction;
    }
}
