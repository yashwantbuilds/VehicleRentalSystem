import com.example.service.impl.RentalServiceImpl;
import com.example.utils.CommandProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JUnit5ExampleTest {

    @Test
    private void justAnExample() {
        CommandProcessor commandProcessor = new CommandProcessor(new RentalServiceImpl());
        Assertions.assertEquals(Boolean.TRUE, commandProcessor.processCommand("ADD_BRANCH B1 CAR,BIKE,VAN"));
        Assertions.assertEquals(Boolean.TRUE, commandProcessor.processCommand("ADD_VEHICLE B1 CAR V1 500"));
        Assertions.assertEquals(Boolean.TRUE, commandProcessor.processCommand("ADD_VEHICLE B1 CAR V2 1000"));
        Assertions.assertEquals(Boolean.TRUE, commandProcessor.processCommand("ADD_VEHICLE B1 BIKE V3 250"));
        Assertions.assertEquals(Boolean.TRUE, commandProcessor.processCommand("ADD_VEHICLE B1 BIKE V4 300"));
        Assertions.assertEquals(Boolean.FALSE, commandProcessor.processCommand("ADD_VEHICLE B1 BUS V5 2500"));
        Assertions.assertEquals(-1, commandProcessor.processCommand("BOOK B1 VAN 1 5"));
        Assertions.assertEquals(1000, commandProcessor.processCommand("BOOK B1 CAR 1 3"));
        Assertions.assertEquals(250, commandProcessor.processCommand("BOOK B1 BIKE 2 3"));
        Assertions.assertEquals(900, commandProcessor.processCommand("BOOK B1 BIKE 2 5"));
        Assertions.assertEquals("V2", commandProcessor.processCommand("DISPLAY_VEHICLES B1 1 5"));
    }

}
