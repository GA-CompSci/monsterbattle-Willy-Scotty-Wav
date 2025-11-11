package game;

import java.util.ArrayList;

import gui.MonsterBattleGUI;

/**
 * Game - YOUR monster battle game!
 * 
 * Build your game here. Look at GameDemo.java for examples.
 * 
 * Steps:
 * 1. Fill in setupGame() - create monsters, items, set health
 * 2. Fill in the action methods - what happens when player acts?
 * 3. Customize the game loop if you want
 * 4. Add your own helper methods
 * 
 * Run this file to play YOUR game
 */
public class Game {

    // The GUI (I had AI build most of this)
    private MonsterBattleGUI gui;

    // Game state - YOU manage these
    private ArrayList<Monster> monsters;
    private Monster lastAttacked; // store last attacked monster so it can respond
    private int shieldPower = 0;
    private ArrayList<Item> inventory;
    private int playerHealth;
    private int playerSpeed;
    private int playerDamage;
    private int playerHeal;
    private int playerShield;

    /**
     * Main method - start YOUR game!
     */
    public static void main(String[] args) {
        Game game = new Game(); // it instantiates a copy of this file. We're not running static
        game.play(); // this extra step is unnecessary AI stuff
    }

    /**
     * Play the game!
     */
    public void play() {
        setupGame();
        gameLoop();
    }

    /**
     * Setup - create the GUI and initial game state
     * 
     * TODO: Customize this! How many monsters? What items? How much health?
     */
    private void setupGame() {
        // Create the GUI
        gui = new MonsterBattleGUI("Monster Battle - Mr A");

        // CHOOSE DIFFICULTY (number of monsters to face)
        int numMonsters = chooseDifficulty();
        monsters = new ArrayList<>();
        // should we add special abilitys
        for (int k = 0; k < numMonsters; k++) {
            if (k == 0) {
                // add a special monster
                monsters.add(new Monster("Vampire"));
            } else {
                monsters.add(new Monster());
            }

        }
        gui.updateMonsters(monsters);
        // PICK YOUR CHARACTER BUILD (using the 4 action buttons!)
        pickCharacterBuild();

        // TODO: Create starting items
        inventory = new ArrayList<>();
        // Add items here! Look at GameDemo.java for examples
        gui.updateInventory(inventory);

        // TODO: Customize button labels
        String[] buttons = { "Attack [" + playerDamage + "]",
                "Defend [" + playerShield + "]",
                "Heal [" + playerHeal + "]",
                "Use Item" };
        gui.setActionButtons(buttons);

        // Welcome message
        gui.displayMessage("Battle Start! Make Your Move.");
    }

    /**
     * Main game loop
     * 
     * This controls the flow: player turn → monster turn → check game over
     * You can modify this if you want!
     */
    private void gameLoop() {
        // Keep playing while monsters alive and player alive
        while (countLivingMonsters() > 0 && playerHealth > 0) {
            shieldPower = 0;

            // PLAYER'S TURN
            gui.displayMessage("Your turn! HP: " + playerHealth);
            int action = gui.waitForAction(); // Wait for button click (0-3)
            handlePlayerAction(action);
            gui.updateMonsters(monsters);
            gui.pause(500);

            // MONSTER'S TURN (if any alive and player alive)
            if (countLivingMonsters() > 0 && playerHealth > 0) {
                monsterAttack();
                gui.updateMonsters(monsters);
                gui.pause(500);
            }
        }

        // Game over!
        if (playerHealth <= 0) {
            gui.displayMessage("💀 DEFEAT! You have been defeated...");
        } else {
            gui.displayMessage("🎉 VICTORY! You defeated all monsters!");
        }
    }

    /**
     * Let player choose difficulty (number of monsters) using the 4 buttons
     * This demonstrates using the GUI for menu choices!
     */
    private int chooseDifficulty() {
        // Set button labels to difficulty levels
        String[] difficulties = { "Easy (3-4)", "Medium (4-5)", "Hard (6-8)", "Extreme (10-15)" };
        gui.setActionButtons(difficulties);

        // Display choice prompt
        gui.displayMessage("---- CHOOSE DIFFICULTY ----");

        // Wait for player to click a button (0-3)
        int choice = gui.waitForAction();
        int numMonsters = 0;
        switch (choice) {
            case 0:
                numMonsters = (int) (Math.random() * (4 - 2 + 1)) + 2;
                break;
            case 1:
                numMonsters = (int) (Math.random() * (5 - 4 + 1)) + 4;
                break;
            case 2:
                numMonsters = (int) (Math.random() * (8 - 6 + 1)) + 6;
                break;
            case 3:
                numMonsters = (int) (Math.random() * (15 - 10 + 1)) + 10;
                break;
        }

        gui.displayMessage("You Will Face: " + numMonsters + " Monsters! I Wish You Luck.");
        gui.pause(1500);

        return numMonsters;
    }

    /**
     * Let player pick their character build using the 4 buttons
     * This demonstrates using the GUI for menu choices!
     */
    private void pickCharacterBuild() {
        // Set button labels to character classes
        String[] characterClasses = { "Fighter", "Tank", "Healer", "Ninja" };
        gui.setActionButtons(characterClasses);

        // Display choice prompt
        gui.displayMessage("---- PICK YOUR BUILD ----");

        // Wait for player to click a button (0-3)
        int choice = gui.waitForAction();

        // Initialize default stats
        playerDamage = 50;
        playerShield = 50;
        playerHeal = 30;
        playerSpeed = 10;
        playerHealth = 100;

        // Customize stats based on character choice
        if (choice == 0) {
            // Fighter: high damage, low healing and shield
            gui.displayMessage("You chose Fighter! High damage, but weak defense.");
            playerShield -= (int) (Math.random() * 25 + 1) + 5; // Reduce shield by 6-30
            playerHeal -= (int) (Math.random() * 20) + 5; // Reduce heal by 5-30
            playerSpeed = (int) (Math.random() * 3) + 5; // calc speed by 1-10
        } else if (choice == 1) {
            // Tank: high shield, low damage and speed
            gui.displayMessage("You chose Tank! Tough defense, but slow attacks.");
            playerSpeed = (int) (Math.random() * 7) + 1; // calc speed by 1-10
            playerDamage -= (int) (Math.random() * 21) + 1; // Reduce damage by 100-199
        } else if (choice == 2) {
            // Healer: high healing, low damage and shield
            gui.displayMessage("You chose Healer! Great recovery, but fragile.");
            playerDamage -= (int) (Math.random() * 21) + 5; // Reduce damage by 5-30
            playerShield -= (int) (Math.random() * 21) + 5; // Reduce shield by 5-50
            playerSpeed = (int) (Math.random() * 5) + 4; // calc speed by 4-8
        } else {
            // Ninja: high speed, low healing and health
            gui.displayMessage("You chose Ninja! Fast and deadly, but risky.");
            playerHeal -= (int) (Math.random() * 20) + 5; // Reduce heal by 5-50
            playerHealth -= (int) (Math.random() * 21) + 5; // Reduce max health by 5-25
            playerSpeed = (int) (Math.random() * 5) + 7; // calc speed by 6-11
        }
        if (playerHeal < 0)
            playerHeal = 0; // dont let heal go neg

        gui.setPlayerMaxHealth(playerHealth);
        gui.updatePlayerHealth(playerHealth);
        // Pause to let player see their choice
        gui.pause(1500);
    }

    /**
     * Handle player's action choice
     * 
     * TODO: What happens for each action?
     */
    private void handlePlayerAction(int action) {
        switch (action) {
            case 0: // Attack button
                attackMonster();
                break;
            case 1: // Defend button
                defend();
                break;
            case 2: // Heal button
                heal();
                break;
            case 3: // Use Item button
                useItem();
                break;
        }
    }

    /**
     * Attack a monster
     * 
     * TODO: How does attacking work in your game?
     * - How much damage?
     * - Which monster gets hit?
     * - Special effects?
     */
    private void attackMonster() {
        // work on better targeting
        Monster target = getRandomLivingMonster();
        lastAttacked = target;
        int damage = (int) (Math.random() * playerDamage + 1);
        if (damage == 0) {
            // hurt self
            playerHealth -= 5;
            gui.displayMessage("Critical Fail! You Hit Yourself for 5 Damage");
            gui.updatePlayerHealth(playerHealth);
        } else if (damage == playerDamage) {
            gui.displayMessage("Critical Hit! You Slayed The Monster");
            target.takeDamage(target.health());

        } else {
            target.takeDamage(damage);
            gui.displayMessage("You Hit The Monster For " + damage + "Damage");
        }
        // Show which one we hit
        int index = monsters.indexOf(target);
        gui.highlightMonster(index);
        gui.pause(300);
        gui.highlightMonster(-1);
        // update list
        gui.updateMonsters(monsters);
    }

    /**
     * Defend
     * 
     * TODO: What does defending do?
     * - Reduce damage?
     * - Block next attack?
     * - Something else?
     */
    private void defend() {
        shieldPower = playerShield;
        gui.displayMessage("Your shield is up!");
    }

    private void heal() {
        playerHeal += playerHeal;
        gui.updatePlayerHealth(playerHealth);
        gui.displayMessage("You Healed For " + playerHeal + " HP");

        gui.displayMessage("TODO: Implement heal!");
    }

    /**
     * Use an item from inventory
     */
    private void useItem() {
        if (inventory.isEmpty()) {
            gui.displayMessage("No items in inventory!");
            return;
        }

        // Use first item
        Item item = inventory.remove(0);
        gui.updateInventory(inventory);
        item.use(); // The item knows what to do!
    }

    /**
     * Monster attacks player
     * 
     * TODO: Customize how monsters attack!
     * - How much damage?
     * - Which monster attacks?
     * - Special abilities?
     */
    private void monsterAttack() {
        // BUILD A LIST OF MONSTERS THAT WILL ATTACK US
        ArrayList<Monster> attackers = new ArrayList<>();
        if (lastAttacked != null && lastAttacked.health() > 0 && !attackers.contains(lastAttacked))
            ;
        attackers.add(lastAttacked);

        for (Monster m : attackers) {
            double incomingDamage = m.damage(); // Maybe use Math.random() to vary damage

            // CHECK FOR SPECIALS
            if (!m.special().isEmpty()) {
                if (m.special().equals("Vampire")) {
                    m.heal((int) incomingDamage);
                    gui.displayMessage("Your health has been Taken! The monster healed for: " + incomingDamage + "!");
                } else if (m.special().equals("Poison")) {
                    // TODO: Add poison effect that damages over time
                } else if (m.special().equals("Equalizer")) {
                    if (m.health() < 10)
                        incomingDamage += 20;
                    else if (m.health() < 25)
                        incomingDamage += 10;
                    else if (m.health() < 50)
                        incomingDamage += 5;
                    else
                        incomingDamage += 0;
                }
            }
            // todo fin logic for reapeated shield hits
            if (shieldPower > 0) {
                shieldPower = Math.min((int)incomingDamage, shieldPower);
                gui.displayMessage("You Blocked For " + incomingDamage + "Damage");
            }
            if (incomingDamage > 0) {
                playerHealth -= incomingDamage;
                gui.displayMessage("The Monster Hit You For " + incomingDamage + " Damage!");
                gui.updatePlayerHealth(playerHealth);
            }
            // FLASH THE MONSTER SO WE SEE WHO HIT US
            // todo check for shield
            int index = monsters.indexOf(m);
            gui.highlightMonster(index);
            gui.pause(300);
            gui.highlightMonster(-1);

        }

    }

    // ==================== HELPER METHODS ====================
    // Add your own helper methods here!

    /**
     * Count how many monsters are still alive
     */
    private int countLivingMonsters() {
        int count = 0;
        for (Monster m : monsters) {
            if (m.health() > 0)
                count++;
        }
        return count;
    }

    private ArrayList<Monster> getSpecialMonsters() {
        ArrayList<Monster> result = new ArrayList<>();
        for (Monster m : monsters) {
            if (m.special() != null && !m.special().equals("") && m.health() > 0) {
                result.add(m);
            }
        }
        return result;
    }

    private ArrayList<Monster> getSpeedyMonsters() {
        ArrayList<Monster> result = new ArrayList<>();
        for (Monster m : monsters) {
            if (m.speed() < playerSpeed && m.health() > 0) {
                result.add(m);
            }
        }
        return result;
    }

    /**
     * Get a random living monster
     */
    private Monster getRandomLivingMonster() {
        ArrayList<Monster> alive = new ArrayList<>();
        for (Monster m : monsters) {
            if (m.health() > 0) alive.add(m);
        }
        if (alive.isEmpty()) return null;
        return alive.get((int)(Math.random() * alive.size()));
    }
}
