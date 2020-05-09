package unitTest;

import clickSystem.domain.Campaign;
import clickSystem.domain.Click;
import clickSystem.domain.valueObjects.IDCampaign;
import clickSystem.domain.valueObjects.IDClick;
import clickSystem.domain.valueObjects.IDUser;
import clickSystem.domain.valueObjects.StateClick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static helpers.CampaignBuilder.aCampaign;
import static helpers.ClickBuilder.aClick;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CampaignShould {
    SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp(){
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    }

    @Test
    public void create_a_campaign(){
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(25).typeOf("friendly").build();

        assertEquals(new Campaign(new IDCampaign(1), 25, "friendly"), campaign);
    }

    @Test
    public void be_finished_state_in_campaign_when_budget_is_zero(){
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(0).typeOf("standard").build();

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.00" + " €" + "\n" +
                "StateCampaign: FINISHED", campaign.toString());
    }

    @Test
    public void be_paused_state_in_campaign_when_is_paused(){
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(1).typeOf("standard").build();
        campaign.pause();
        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 1.00" + " €" + "\n" +
                "StateCampaign: PAUSED", campaign.toString());
    }

    @Test
    public void be_activated_state_in_campaign_when_is_paused(){
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(1).typeOf("standard").build();
        campaign.activate();
        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 1.00" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }

    @Test
    public void charge_for_click_in_a_friendly_campaign() throws ParseException {
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(0.10).typeOf("friendly").build();
        campaign.activate();
        Click click = aClick().withID(new IDClick(1)).withIDUser(new IDUser(10)).at(dateFormat.parse("07/05/2020 09:26:15")).isType(StateClick.PREMIUM).build();
        campaign.chargeForClick(click);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.08" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }

    @Test
    public void charge_for_click_in_a_standard_campaign() throws ParseException {
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(0.10).typeOf("standard").build();
        campaign.activate();
        Click click = aClick().withID(new IDClick(1)).withIDUser(new IDUser(10)).at(dateFormat.parse("07/05/2020 09:26:15")).isType(StateClick.PREMIUM).build();
        campaign.chargeForClick(click);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.05" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }

    @Test
    public void charge_for_click_in_a_demo_campaign() throws ParseException {
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(0.10).typeOf("demo").build();
        campaign.activate();
        Click click = aClick().withID(new IDClick(1)).withIDUser(new IDUser(10)).at(dateFormat.parse("07/05/2020 09:26:15")).isType(StateClick.PREMIUM).build();
        campaign.chargeForClick(click);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.10" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }
}
