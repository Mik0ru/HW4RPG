import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {290, 270, 250,200,220,400 , 270,210};
    public static int[] heroesDamage = {20, 15, 10,10, 15, 3 , 0, 15};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medical", "LuckyShot", "GolemHit", "Witcher", "Thor"};
    public static int roundNumber = 0;
    public static boolean Stun = false;
    public static boolean StunCooldown = false;

    public static void main(String[] args) {
        printRound();
        Statictics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        printRound();
        medicHeal();
        witcherSacrifice();
        Statictics();
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (Stun) {
                StunCooldown = true;
                System.out.println("Boss is stunned");
                break;
            }
            if (heroesAttackType[i].equals("LuckyShot")) {
                Random random = new Random();
                boolean dodge = random.nextBoolean();
                if (dodge) {
                    System.out.println("Lucky dodges next hit!!");
                    continue;
                }
            }
            if (heroesHealth[i] > 0) {
                golemDamageReduction();
                if (heroesHealth[i] - (bossDamage - golemDamageReduction()) < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - (bossDamage - golemDamageReduction());
                }
            }
        }
        StunCooldown = false;
    }
    public static void medicHeal () {
        if (heroesHealth[3] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesAttackType[i].equals("Medical")) {
                    continue;
                }
                if (heroesHealth[i] <= 100 && heroesHealth[i] > 0) {
                    Random random = new Random();
                    int healing = random.nextInt(100 ) + 50;
                    System.out.println("Medical Healed " + heroesAttackType[i] + " for " + healing);
                    break;

                }
            }
        }
    }
    public static int golemDamageReduction() {
        if (heroesHealth[5] > 0 ) {
            if (heroesHealth[5] - bossDamage/5 > 0 ) {
                heroesHealth[5] = heroesHealth[5] - bossDamage / 5;
            }
            if (heroesHealth[5] - bossDamage/5 < 0 ) {
                heroesHealth[5] = 0;
            }
            return bossDamage/5;
        }
        else return 0;

    }
    public static void witcherSacrifice () {
        if (heroesHealth[6] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesAttackType[i].equals("Witcher")) {
                    continue;
                }
                if (heroesHealth[i] == 0) {
                    System.out.println("Witcher sacrificed himself to revive: " + heroesAttackType[i]+ "!!!");
                    heroesHealth[i] = heroesHealth[6];
                    heroesHealth[6] = 0;
                    break;
                }


            }
        }

    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i].equals("Thor") && !StunCooldown) {
                    Random random = new Random();
                    Stun = random.nextBoolean();
                    if (Stun) {
                        System.out.println("Boss was stunned by Thor!!!");
                    }
                }
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void printRound() {
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("ROUND " + roundNumber + " -----------------");
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
    }

    public static void Statictics (){
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);

        }
    }
}