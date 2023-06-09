import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.security.spec.DSAGenParameterSpec;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    @Timeout(value = 22)
    @Disabled
    void main() {
        Date before = new Date();
        try {
            Main.main(new String[0]);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Date after = new Date();
        System.out.printf("%.3f", ((double) after.getTime() - before.getTime()) / 1000);
    }
}