import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Solution {

    public List<String> findRepeatedDnaSequences(String s) {

        // RepeatedDna length : 10, even find repeated dna several times only return once

        // Hash - high up the matching speed
        HashSet<String> seen = new HashSet<>();
        // remove the duplicated matched key
        HashSet<String> res = new HashSet<>();
        // i : start of the intended matched dna, we suppose each string + 10 will be the target
        for (int i = 0; i + 10 <= s.length(); i++) {
            String key = s.substring(i, i+10);
            if (seen.contains(key))
                res.add(key);
            else
                seen.add(key);
        }

        return new ArrayList<String>(res);
    }
}
