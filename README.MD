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