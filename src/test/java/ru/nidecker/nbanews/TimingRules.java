package ru.nidecker.nbanews;

import lombok.extern.slf4j.Slf4j;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TimingRules {

    private static final StringBuilder results = new StringBuilder();

    public static final Stopwatch STOPWATCH = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-95s %7d", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            log.info(result + " ms\n");
        }
    };

    private static final String DELIM = "-".repeat(103);

    public static final ExternalResource SUMMARY = new ExternalResource() {

        @Override
        protected void before() throws Throwable {
            results.setLength(0);
        }

        @Override
        protected void after() {
            log.info("\n" + DELIM +
                    "\nTest                                                                                       Duration, ms" +
                    "\n" + DELIM + "\n" + results + DELIM + "\n");
        }
    };
}