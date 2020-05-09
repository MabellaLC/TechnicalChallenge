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

        Click click = aClick().withID(new IDClick(10)).withIDUser(new IDUser(10)).at(firstDate).isType(StateClick.STANDARD).build();
        Click expectedClick = aClick().withID(new IDClick(10)).withIDUser(new IDUser(10)).at(firstDate).isType(StateClick.STANDARD).build();

        assertEquals(expectedClick, click);
    }

    @Test
    public void be_fifteen_seconds_bigger_than_click_before() throws ParseException {
        firstDate = dateFormat.parse("07/05/2020 09:26:15");
        secondDate = dateFormat.parse("07/05/2020 09:26:15");
        Click click = aClick().withID(new IDClick(1)).withIDUser(new IDUser(10)).at(firstDate).isType(StateClick.STANDARD).build();
        Click click2 = aClick().withID(new IDClick(2)).withIDUser(new IDUser(10)).at(secondDate).isType(StateClick.STANDARD).build();

        assertEquals(false, click.differenceBiggerThanFifteenSeconds(click2));
    }

    @Test
    public void incorrect_time_between_clicks() throws ParseException {
        firstDate = dateFormat.parse("07/05/2020 09:26:15");
        secondDate = dateFormat.parse("07/05/2020 09:27:15");

        Click click = aClick().withID(new IDClick(1)).withIDUser(new IDUser(10)).at(firstDate).isType(StateClick.STANDARD).build();
        Click click2 = aClick().withID(new IDClick(2)).withIDUser(new IDUser(10)).at(secondDate).isType(StateClick.STANDARD).build();

        assertEquals(true, click.differenceBiggerThanFifteenSeconds(click2));
    }
}
