package game.data;

import fileio.CardInput;
import game.card.Hero;

import java.util.ArrayList;

/**
 * Contains the information for a card
 */
public class CardInputData {

    public static final int HEALTH_HERO = 30;
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors =  new ArrayList<>();
    private String name;

    private int frozen; /* 1 if the card is frozen, 0 else */
    private int attack; /* 1 if the card already attacked on turn, 0 else */

    public CardInputData() {

    }

    /**
     * Copy constructor
     * @param card information for a card
     */
    public CardInputData(final CardInput card) {
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();

        Hero hero = new Hero();
        if (hero.getHeroCards().contains(card.getName())) {
            this.health = HEALTH_HERO;
        } else {
            this.health = card.getHealth();
        }
        this.description = new String(card.getDescription());
        this.colors.addAll(card.getColors());
        this.name = new String(card.getName());
        frozen = 0;
        attack = 0;
    }

    /**
     * @return mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * @param mana new value
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * @return attackDamage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * @param attackDamage new value
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health new value
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description new value
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return colors
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * @param colors new value
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new value
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return frozen
     */
    public int getFrozen() {
        return frozen;
    }

    /**
     * @param frozen new value
     */
    public void setFrozen(final int frozen) {
        this.frozen = frozen;
    }

    /**
     * @return attack
     */
    public int getAttack() {
        return attack;
    }

    /**
     * @param attack new value
     */
    public void setAttack(final int attack) {
        this.attack = attack;
    }
}
