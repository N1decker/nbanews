package ru.nidecker.nbanews;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.nidecker.nbanews.TimingRules;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractTest {

    @ClassRule
    public static ExternalResource summary = TimingRules.SUMMARY;

    @Rule
    public Stopwatch stopwatch = TimingRules.STOPWATCH;
}
