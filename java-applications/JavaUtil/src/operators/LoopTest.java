package operators;

public class LoopTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        breakEmbededLoops();
    }

    public void loopTest() {
        for (int i = 10, max = 11; i <= max; i++) {

        }
    }

    public static void breakEmbededLoops() {
        outerloop:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i * j > 6) {
                    System.out.println("Breaking: i * j ["+(i * j)+"]> 6: ");
                    break outerloop;
                }
                System.out.println(i + " " + j);
            }
        }
        System.out.println("Done");
}

public void doWhiletest() {
        // TODO Auto-generated method stub
        int count = 1;
        boolean moretask = true;
        do {
            count++;
            System.out.println("count=" + count);
            if (count >= 100) {
                moretask = false;
            }
        } while (moretask);
    }

}
