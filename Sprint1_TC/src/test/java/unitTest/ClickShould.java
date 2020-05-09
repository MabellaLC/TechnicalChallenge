package unitTest;

import clickSystem.domain.Click;
import clickSystem.domain.valueObjects.IDClick;
import clickSystem.domain.valueObjects.IDUser;
import clickSystem.domain.valueObjects.StateClick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static helpers.ClickBuilder.aClick;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClickShould {
    @Test
    public void create_a_click(){
        Click click = new Click(10, 10, "07/05/2020 09:26:15", "Standard");
        Click expectedClick = new Click(10, 10, "07/05/2020 09:26:15", "Standard");

        assertEquals(expectedClick, click);
    }

    @Test
    public void charge_five_cents_if_click_is_premium(){
        Click click = new Click(10, 10, "07/05/2020 09:26:15", "Premium");

        assertEquals(0.05, click.chargeForClick());
    }

    @Test
    public void charge_five_cents_if_click_is_standard(){
        Click click = new Click(10, 10, "07/05/2020 09:26:15", "Standard");

        assertEquals(0.01, click.chargeForClick());
    }
}
