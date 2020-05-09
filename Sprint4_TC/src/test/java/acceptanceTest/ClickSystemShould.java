package acceptanceTest;

import clickSystem.CampaignManager;
import clickSystem.domain.Campaign;
import clickSystem.domain.Click;
import clickSystem.domain.valueObjects.IDCampaign;
import clickSystem.domain.valueObjects.IDClick;
import clickSystem.domain.valueObjects.IDUser;
import clickSystem.domain.valueObjects.StateClick;
import clickSystem.infraestructure.ClicksInMemory;
import clickSystem.infraestructure.ClicksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static helpers.CampaignBuilder.aCampaign;
import static helpers.ClickBuilder.aClick;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClickSystemShould {
    ClicksRepository clicksRepository;
    CampaignManager campaignManager;
    SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp(){
        clicksRepository = new ClicksInMemory();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    }

    @Test
    public void charge_for_clicks_from_website() throws ParseException {
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(0.20).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(new IDClick(1)).withIDUser(new IDUser(1)).at(dateFormat.parse("07/05/2020 09:17:15")).isType(StateClick.PREMIUM).build();
        Click click2 = aClick().withID(new IDClick(2)).withIDUser(new IDUser(2)).at(dateFormat.parse("07/05/2020 09:18:15")).isType(StateClick.STANDARD).build();
        Click click3 = aClick().withID(new IDClick(3)).withIDUser(new IDUser(3)).at(dateFormat.parse("07/05/2020 09:19:15")).isType(StateClick.PREMIUM).build();
        campaignManager.addClickToAList(click, campaign);
        campaignManager.addClickToAList(click2, campaign);
        campaignManager.addClickToAList(click3, campaign);

        campaignManager.pause(campaign);
        Click click4 = aClick().withID(new IDClick(4)).withIDUser(new IDUser(4)).at(dateFormat.parse("07/05/2020 09:20:15")).isType(StateClick.PREMIUM).build();
        Click click5 = aClick().withID(new IDClick(5)).withIDUser(new IDUser(5)).at(dateFormat.parse("07/05/2020 09:21:15")).isType(StateClick.PREMIUM).build();
        campaignManager.addClickToAList(click4, campaign);
        campaignManager.addClickToAList(click5, campaign);

        campaignManager.activate(campaign);
        Click click6 = aClick().withID(new IDClick(6)).withIDUser(new IDUser(6)).at(dateFormat.parse("07/05/2020 09:22:15")).isType(StateClick.PREMIUM).build();
        Click click7 = aClick().withID(new IDClick(7)).withIDUser(new IDUser(8)).at(dateFormat.parse("07/05/2020 09:23:15")).isType(StateClick.STANDARD).build();
        Click click8 = aClick().withID(new IDClick(8)).withIDUser(new IDUser(2)).at(dateFormat.parse("07/05/2020 09:24:15")).isType(StateClick.STANDARD).build();
        Click click9 = aClick().withID(new IDClick(9)).withIDUser(new IDUser(9)).at(dateFormat.parse("07/05/2020 09:25:15")).isType(StateClick.STANDARD).build();
        Click click10 = aClick().withID(new IDClick(10)).withIDUser(new IDUser(10)).at(dateFormat.parse("07/05/2020 09:26:15")).isType(StateClick.PREMIUM).build();
        campaignManager.addClickToAList(click6, campaign);
        campaignManager.addClickToAList(click7, campaign);
        campaignManager.addClickToAList(click8, campaign);
        campaignManager.addClickToAList(click9, campaign);
        campaignManager.addClickToAList(click10, campaign);

        campaignManager.chargeClicks(campaign);
        Campaign campaignExpected = aCampaign().withID(new IDCampaign(1)).withBudget(0).build();

        assertEquals(campaignExpected, campaign);
    }

    @Test
    public void no_charge_clicks_when_user_is_the_same_until_fifteen_seconds_after_of_the_click_before() throws ParseException {
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(0.20).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(new IDClick(1)).withIDUser(new IDUser(1)).at(dateFormat.parse("07/05/2020 09:16:15")).isType(StateClick.PREMIUM).build();
        Click click2 = aClick().withID(new IDClick(2)).withIDUser(new IDUser(1)).at(dateFormat.parse("07/05/2020 09:16:25")).isType(StateClick.PREMIUM).build();
        Click click3 = aClick().withID(new IDClick(2)).withIDUser(new IDUser(1)).at(dateFormat.parse("07/05/2020 09:16:55")).isType(StateClick.PREMIUM).build();
        campaignManager.addClickToAList(click, campaign);
        campaignManager.addClickToAList(click2, campaign);
        campaignManager.addClickToAList(click3, campaign);

        campaignManager.chargeClicks(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.10" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }

    @Test
    public void charge_clicks_as_a_Friendly_campaign() throws ParseException {
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(0.20).typeOf("premium").build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(new IDClick(1)).withIDUser(new IDUser(1)).at(dateFormat.parse("07/05/2020 09:16:15")).isType(StateClick.PREMIUM).build();
        Click click2 = aClick().withID(new IDClick(2)).withIDUser(new IDUser(1)).at(dateFormat.parse("07/05/2020 09:16:25")).isType(StateClick.PREMIUM).build();
        Click click3 = aClick().withID(new IDClick(2)).withIDUser(new IDUser(1)).at(dateFormat.parse("07/05/2020 09:16:55")).isType(StateClick.PREMIUM).build();
        campaignManager.addClickToAList(click, campaign);
        campaignManager.addClickToAList(click2, campaign);
        campaignManager.addClickToAList(click3, campaign);

        campaignManager.chargeClicks(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.16" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }

    @Test
    public void find_boots_as_a_user_and_refund_click_when_campaign_is_standard() throws ParseException {
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(0.20).typeOf("standard").build();

        Click click = aClick().withID(new IDClick(1)).withIDUser(new IDUser(1)).at(dateFormat.parse("07/05/2020 09:16:15")).isType(StateClick.PREMIUM).build();
        Click click2 = aClick().withID(new IDClick(2)).withIDUser(new IDUser(15)).at(dateFormat.parse("07/05/2020 09:16:55")).isType(StateClick.PREMIUM).build();
        campaignManager.addClickToAList(click, campaign);
        campaignManager.addClickToAList(click2, campaign);


        campaignManager.findBotsFrom(dateFormat.parse("07/05/2020 09:15:15"), campaign);


        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.15" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }

    @Test
    public void find_boots_as_a_user_and_refund_click_when_campaign_is_premium() throws ParseException {
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = aCampaign().withID(new IDCampaign(1)).withBudget(0.20).typeOf("premium").build();

        Click click = aClick().withID(new IDClick(1)).withIDUser(new IDUser(1)).at(dateFormat.parse("07/05/2020 09:16:15")).isType(StateClick.PREMIUM).build();
        Click click2 = aClick().withID(new IDClick(2)).withIDUser(new IDUser(15)).at(dateFormat.parse("07/05/2020 09:16:55")).isType(StateClick.PREMIUM).build();
        campaignManager.addClickToAList(click, campaign);
        campaignManager.addClickToAList(click2, campaign);

        campaignManager.findBotsFrom(dateFormat.parse("07/05/2020 09:16:15"), campaign);


        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.18" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }
}
