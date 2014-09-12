package operators;

public class LoopTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
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

    public void loopTest() {
        for (int i = 10, max = 11; i <= max; i++) {

        }
    }

}
