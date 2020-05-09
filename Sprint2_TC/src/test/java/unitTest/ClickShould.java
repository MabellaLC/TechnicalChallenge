package unitTest;

import clickSystem.domain.Click;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static helpers.ClickBuilder.aClick;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClickShould {
    Date firstDate;
    Date secondDate;
    SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp() {
        firstDate = new Date();
        secondDate = new Date();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    }

    @Test
    public void create_a_click() throws ParseException {
        firstDate = dateFormat.parse("07/05/2020 09:26:15");

        Click click = aClick().withID(10).withIDUser(10).at(firstDate).isType("Standard").build();
        Click expectedClick = aClick().withID(10).withIDUser(10).at(firstDate).isType("Standard").build();
        assertEquals(expectedClick, click);
    }

    @Test
    public void charge_five_cents_if_click_is_premium() throws ParseException {
        firstDate = dateFormat.parse("07/05/2020 09:26:15");
        Click click = aClick().withID(10).withIDUser(10).at(firstDate).isType("Premium").build();

        assertEquals(0.05, click.chargeForClick());
    }

    @Test
    public void charge_five_cents_if_click_is_standard() throws ParseException {
        firstDate = dateFormat.parse("07/05/2020 09:26:15");
        Click click = aClick().withID(10).withIDUser(10).at(firstDate).isType("Standard").build();

        assertEquals(0.01, click.chargeForClick());
    }

    @Test
    public void be_fifteen_seconds_bigger_than_click_before() throws ParseException {

        firstDate = dateFormat.parse("07/05/2020 09:26:15");
        secondDate = dateFormat.parse("07/05/2020 09:26:15");

        Click click = aClick().withID(1).withIDUser(10).at(firstDate).isType("Standard").build();
        Click click2 = aClick().withID(2).withIDUser(10).at(secondDate).isType("Standard").build();

        assertEquals(false, click.differenceBiggerThanFifteenSeconds(click2));
    }
}
