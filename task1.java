import java.util.Scanner;

public class GameApplication {

    public static class GameState {
        private static GameState instance;
        private int currentLevel;
        private String difficulty;

        private GameState() {
            currentLevel = 1;
            difficulty = "Easy";
        }

        public static GameState getInstance() {
            if (instance == null) {
                instance = new GameState();
            }
            return instance;
        }

        public int getCurrentLevel() {
            return currentLevel;
        }

        public void setCurrentLevel(int level) {
            currentLevel = level;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }
    }

    // Factory Method Pattern for creating enemies
    public static abstract class Enemy {
        public abstract void attack();
        public abstract String getDescription();
    }

    public static class Goblin extends Enemy {
        @Override
        public void attack() {
            System.out.println("Goblin attacks with a club!");
        }

        @Override
        public String getDescription() {
            return "A mischievous Goblin armed with a club.";
        }
    }

    public static class Dragon extends Enemy {
        @Override
        public void attack() {
            System.out.println("Dragon breathes fire!");
        }

        @Override
        public String getDescription() {
            return "A fearsome Dragon that scorches everything in its path.";
        }
    }

    public static class Orc extends Enemy {
        @Override
        public void attack() {
            System.out.println("Orc swings a massive axe!");
        }

        @Override
        public String getDescription() {
            return "A brutish Orc wielding a massive axe.";
        }
    }

    public static class EnemyFactory {
        public static Enemy createEnemy(String type) {
            return switch (type) {
                case "Goblin" -> new Goblin();
                case "Dragon" -> new Dragon();
                case "Orc" -> new Orc();
                default -> throw new IllegalArgumentException("Unknown enemy type");
            };
        }
    }

    public interface ItemFactory {
        Weapon createWeapon();
        PowerUp createPowerUp();
    }

    public interface Weapon {
        void use();
    }

    public interface PowerUp {
        void apply();
    }

    public static class Sword implements Weapon {
        @Override
        public void use() {
            System.out.println("Swinging a sword!");
        }
    }

    public static class Shield implements Weapon {
        @Override
        public void use() {
            System.out.println("Blocking with a shield!");
        }
    }

    public static class HealthPotion implements PowerUp {
        @Override
        public void apply() {
            System.out.println("Restoring health with a potion!");
        }
    }

    public static class ManaPotion implements PowerUp {
        @Override
        public void apply() {
            System.out.println("Replenishing mana with a potion!");
        }
    }

    public static class AdvancedItemFactory implements ItemFactory {
        @Override
        public Weapon createWeapon() {
            return new Sword();
        }

        @Override
        public PowerUp createPowerUp() {
            return new HealthPotion();
        }
    }

    public static class DefensiveItemFactory implements ItemFactory {
        @Override
        public Weapon createWeapon() {
            return new Shield();
        }

        @Override
        public PowerUp createPowerUp() {
            return new ManaPotion();
        }
    }

    public static void main(String[] args) {
        GameState gameState = GameState.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the level you want to play (1-3): ");
        int level = scanner.nextInt();

        if (level < 1 || level > 3) {
            System.out.println("Invalid level! Please restart and choose a level between 1 and 3.");
            return;
        }

        gameState.setCurrentLevel(level);

        switch (level) {
            case 1 -> gameState.setDifficulty("Easy");
            case 2 -> gameState.setDifficulty("Medium");
            case 3 -> gameState.setDifficulty("Hard");
        }

        System.out.println("=== Level " + level + " ===");
        System.out.println("Current Level: " + gameState.getCurrentLevel());
        System.out.println("Difficulty: " + gameState.getDifficulty());

        String enemyType = switch (gameState.getDifficulty()) {
            case "Easy" -> "Goblin";
            case "Medium" -> "Orc";
            case "Hard" -> "Dragon";
            default -> "Goblin";
        };

        Enemy enemy = EnemyFactory.createEnemy(enemyType);
        System.out.println(">> A wild " + enemyType + " appears!");
        System.out.println(enemy.getDescription());
        enemy.attack();

        ItemFactory itemFactory = switch (gameState.getDifficulty()) {
            case "Easy" -> new AdvancedItemFactory();
            case "Medium", "Hard" -> new DefensiveItemFactory();
            default -> new AdvancedItemFactory();
        };

        Weapon weapon = itemFactory.createWeapon();
        PowerUp powerUp = itemFactory.createPowerUp();

        System.out.println(">> Found a weapon!");
        weapon.use();

        System.out.println(">> Found a power-up!");
        powerUp.apply();

        System.out.println(">> Level " + level + " Complete!\n");

        System.out.println("=== Congratulations! You completed the chosen level! ===");
    }
}
