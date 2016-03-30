package dk.sdu.group5.astarai;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class FU {
    public static <T> boolean any(Predicate<T> ps, List<T> xs) {
        return xs.stream().anyMatch(ps);
    }

    public static <A, B, C> BiFunction<List<A>, List<B>, List<C>> lift2(BiFunction<A, B, C> fn) {
        BiFunction<List<A>, List<B>, List<C>> lfn = (as, bs) -> {
            List<C> rs = new ArrayList<>();
            for (int i = 0; i < as.size(); i++) {
                for (int j = i; j < bs.size(); j++) {
                    rs.add(fn.apply(as.get(i), bs.get(j)));
                }
            }
            return rs;
        };
        return lfn;
    }
}
