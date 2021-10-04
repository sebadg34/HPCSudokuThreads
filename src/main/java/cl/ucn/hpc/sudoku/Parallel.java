package cl.ucn.hpc.sudoku;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;




@Slf4j
public class Parallel {
    /**
     * The Main
     */

    private static  AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        final int cores = 4;
        final int ini = 2;




        // Finding the numbers of cores
        final int maxCores = Runtime.getRuntime().availableProcessors();
        log.debug ("The max cores: {}.",maxCores);

        // The Executor of Threads
        final ExecutorService executor = Executors.newFixedThreadPool(maxCores);
        final StopWatch sw = StopWatch.createStarted();


            executor.submit(() -> {

            });




        executor.shutdown();



        int maxTime = 5;
        if(executor.awaitTermination(maxTime, TimeUnit.MINUTES)){
            log.info("Executor ok. {} {}", counter, sw.getTime(TimeUnit.MILLISECONDS));
        } else {
            log.warn("The Executor didn't finish in {} minutes", maxTime);
        }
    }
}
