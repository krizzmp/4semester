package dk.sdu.group5.astarai;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class FU {
    public static <T> boolean any(Predicate<T> ps, List<T> xs) {
        return xs.stream().anyMatch(ps);
    }

    public static <A, B, C> BiFunction<List<A>, List<B>, List<C>> lift2(BiFunction<A, B, C> fn) {
        return (as, bs) -> {
            List<C> rs = new ArrayList<>();
            for (int i = 0; i < as.size(); i++) {
                for (int j = i+1; j < bs.size(); j++) {
                    rs.add(fn.apply(as.get(i), bs.get(j)));
                }
            }
            return rs;
        };
    }
    public static <A,B> List<B> map(Function<A,B> fn, List<A> xs){
        return xs.stream().map(fn).collect(Collectors.toList());
    }
    public static <A,B> List<B> flatMap(Function<A,List<B>> fn, List<A> xs){
        return xs.stream().flatMap(x->fn.apply(x).stream()).collect(Collectors.toList());
    }
}
