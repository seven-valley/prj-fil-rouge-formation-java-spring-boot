```java
import java.util.*;

public class Solution {

    public static int next(int n) {
        // Récupérer tous les chiffres de n
        String s = String.valueOf(n);
        Set<Character> forbidden = new HashSet<>();
        for (char c : s.toCharArray()) forbidden.add(c);

        int length = s.length();
        // Commencer à partir de n + 1
        for (long i = (long)n + 1; i < Math.pow(10, length + 1); i++) {
            String str = String.valueOf(i);
            boolean valid = true;
            for (char c : str.toCharArray()) {
                if (forbidden.contains(c)) {
                    valid = false;
                    break;
                }
            }
            if (valid) return (int)i;
        }

        return -1; // aucun entier valide
    }

    public static void main(String[] args) {
        System.out.println(next(654321)); // 700000
        System.out.println(next(123));    // 400
        System.out.println(next(987654321)); // -1 si impossible
    }
}
```

```java
import java.util.*;

public class TopKFrequent {

    public static List<Integer> topKFrequent(int[] nums, int k) {

        // 1. Compter les fréquences
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // 2. Transformer en liste
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(freqMap.entrySet());

        // 3. Trier par fréquence décroissante
        list.sort((a, b) -> b.getValue() - a.getValue());

        // 4. Récupérer les k premiers
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(list.get(i).getKey());
        }

        return result;
    }

    // Test rapide
    public static void main(String[] args) {
        int[] nums = {1,1,1,2,2,3};
        int k = 2;

        System.out.println(topKFrequent(nums, k)); // [1, 2]
    }
}
```