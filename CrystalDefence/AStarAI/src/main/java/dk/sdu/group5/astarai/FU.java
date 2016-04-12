package dk.sdu.group5.astarai;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// TODO: 12/04/16 What does FU mean? Function utility?
public class FU {
    public static <T> boolean any(Predicate<T> predicate, List<T> elements) {
        return elements.stream().anyMatch(predicate);
    }

    // TODO: 12/04/16 Find a better name ;P
    public static <A, B, C> BiFunction<List<A>, List<B>, List<C>> lift2(BiFunction<A, B, C> biFunction) {
        return (as, bs) -> {
            List<C> rs = new ArrayList<>();
            for (int i = 0; i < as.size(); i++) {
                for (int j = i+1; j < bs.size(); j++) {
                    rs.add(biFunction.apply(as.get(i), bs.get(j)));
                }
            }
            return rs;
        };
    }

    public static <A, B> List<B> map(Function<A, B> function, List<A> elements) {
        return elements.stream().map(function).collect(Collectors.toList());
    }

    public static <A, B> List<B> flatMap(Function<A, List<B>> function, List<A> elements) {
        return elements.stream().flatMap(x -> function.apply(x).stream()).collect(Collectors.toList());
    }
}
