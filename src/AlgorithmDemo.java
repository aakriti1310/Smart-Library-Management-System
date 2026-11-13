import java.util.*;

/** Small demonstrations of greedy and dynamic-programming algorithms. */
public class AlgorithmDemo {
    private static class Activity {
        int start, finish;
        Activity(int s, int f) { start = s; finish = f; }
    }

    public static void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Algorithm Demonstrations ---");
        System.out.println("1. Activity Selection (Greedy)");
        System.out.println("2. 0/1 Knapsack (Dynamic Programming)");
        System.out.println("3. Fractional Knapsack (Greedy)");
        System.out.print("Choose: ");
        int choice = readInt(sc);
        switch (choice) {
            case 1 -> activitySelection();
            case 2 -> zeroOneKnapsack();
            case 3 -> fractionalKnapsack();
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void activitySelection() {
        Activity[] a = { new Activity(1, 3), new Activity(2, 5), new Activity(4, 6),
                new Activity(6, 7), new Activity(5, 8), new Activity(8, 9) };
        Arrays.sort(a, Comparator.comparingInt(x -> x.finish));
        int lastFinish = -1, count = 0;
        System.out.println("Selected activity time slots:");
        for (Activity x : a) {
            if (x.start >= lastFinish) {
                System.out.println("(" + x.start + ", " + x.finish + ")");
                lastFinish = x.finish; count++;
            }
        }
        System.out.println("Maximum compatible activities: " + count);
    }

    private static void zeroOneKnapsack() {
        int[] weight = {2, 3, 4, 5}, value = {3, 4, 5, 6};
        int capacity = 5;
        int[][] dp = new int[weight.length + 1][capacity + 1];
        for (int i = 1; i <= weight.length; i++)
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w];
                if (weight[i - 1] <= w)
                    dp[i][w] = Math.max(dp[i][w], value[i - 1] + dp[i - 1][w - weight[i - 1]]);
            }
        System.out.println("Weights: " + Arrays.toString(weight));
        System.out.println("Values : " + Arrays.toString(value));
        System.out.println("Capacity: " + capacity);
        System.out.println("Maximum value (0/1 Knapsack): " + dp[weight.length][capacity]);
    }

    private static void fractionalKnapsack() {
        double[] weight = {10, 20, 30}, value = {60, 100, 120};
        int capacity = 50;
        Integer[] order = {0, 1, 2};
        Arrays.sort(order, (a, b) -> Double.compare(value[b] / weight[b], value[a] / weight[a]));
        double total = 0; int remaining = capacity;
        for (int i : order) {
            if (remaining == 0) break;
            int take = Math.min(remaining, (int) weight[i]);
            total += take * (value[i] / weight[i]);
            remaining -= take;
        }
        System.out.printf("Capacity: %d%nMaximum value (Fractional Knapsack): %.2f%n", capacity, total);
    }

    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) { sc.next(); System.out.print("Enter a number: "); }
        return sc.nextInt();
    }
}
