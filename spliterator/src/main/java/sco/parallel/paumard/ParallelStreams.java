package sco.parallel.paumard;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author José
 */
public class ParallelStreams {

	//parallel:
	//1. do not use stateful stream operation (limit, skip,...)
	//2. do not modify source
	//3. do not use reduce
	//4. do not use non concurrent operations (java collection, instead of collectors).
    public static void main(String[] args) {
    	//it extends collection which implemented iterator. It show the list in order. to use parallel using unordered operation
    	List<String> ls = new ArrayList<>();
        
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2") ;
        
        List<String> strings = new CopyOnWriteArrayList<>();
        
        List<String> collect = 
                Stream.iterate("+", s -> s + "+")
                .parallel()
                .limit(1000)
                // .peek(s -> System.out.println(s + " processed in the thread " + Thread.currentThread().getName()))
                // .forEach(s -> strings.add(s)); //ArrayList strings is not concurrent will have race condition causing exception or wrong results 
                .collect(Collectors.toList());
        
        System.out.println("# " + collect.size());
        
        //Stream<Long> stream = ThreadLocalRandom.current().longs(1000)
    }
}
