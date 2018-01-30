package sco.flatMap.paumard;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 *
 * @author José
 */
public class FlatMapExamples {

    public static void main(String[] args) throws IOException, URISyntaxException {
        // http://introcs.cs.princeton.edu/java/data/TomSawyer.txt
        
        Stream<String> stream1 = Files.lines(Paths.get(ClassLoader.getSystemResource("m2/TomSawyer_01.txt").toURI()));
        Stream<String> stream2 = Files.lines(Paths.get(ClassLoader.getSystemResource("m2/TomSawyer_02.txt").toURI()));
        Stream<String> stream3 = Files.lines(Paths.get(ClassLoader.getSystemResource("m2/TomSawyer_03.txt").toURI()));
        Stream<String> stream4 = Files.lines(Paths.get(ClassLoader.getSystemResource("m2/TomSawyer_04.txt").toURI()));

//        System.out.println("Stream 1 : " + stream1.count());
//        System.out.println("Stream 2 : " + stream2.count());
//        System.out.println("Stream 3 : " + stream3.count());
//        System.out.println("Stream 4 : " + stream4.count());
        
        //Stream.concat two stream keep the order with low performance
        //Stream<String> streamOfStreams0 = Stream.concat(stream1, stream2);
        
        //
        Stream<Stream<String>> streamOfStreams = 
            Stream.of(stream1, stream2, stream3, stream4);
        
//        System.out.println("# streams: " + streamOfStreams.count());
        Stream<String> streamOfLines = 
            streamOfStreams.flatMap(Function.identity()); //s -> s
        
//        System.out.println("# lines " + streamOfLines.count());
        
        //Function<String, Stream<String>> f0 = line -> Stream.of(line.split(" "));
        Function<String, Stream<String>> lineSplitter = 
                line -> Pattern.compile(" ").splitAsStream(line);
        
        Stream<String> streamOfWords = 
            streamOfLines.flatMap(lineSplitter)
                .map(word -> word.toLowerCase())
                .filter(word -> word.length() == 4)
                .distinct();
        
        System.out.println("# words :" + streamOfWords.count());
    }
}
