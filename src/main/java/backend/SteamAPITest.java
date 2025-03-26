package backend;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SteamAPITest {
    @Test
    public void testSteamAPI() throws IOException {
        SteamAPIFetcher fetcher = new SteamAPIFetcher();
        // Test a couple of games to validate it is not a unique case
        assertTrue(fetcher.validateAPI(440));
        assertTrue(fetcher.validateAPI(730));
        assertTrue(fetcher.validateAPI(413150));
    }
}
