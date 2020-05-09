package unitTest;

import clickSystem.CampaignManager;
import clickSystem.domain.Campaign;
import clickSystem.domain.Click;
import clickSystem.infraestructure.ClicksInMemory;
import clickSystem.infraestructure.ClicksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static helpers.CampaignBuilder.aCampaign;
import static helpers.ClickBuilder.aClick;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CampaignManagerShould {

    ClicksRepository clicksRepository;
    CampaignManager campaignManager;
    SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp(){
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    }

    @Test
    public void activate_a_campaign(){
        clicksRepository = new ClicksInMemory();
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = aCampaign().withID(1).withBudget(0.50).build();

        campaignManager.activate(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.50" + " €" +  "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }

    @Test
    public void pause_a_campaign(){
        clicksRepository = new ClicksInMemory();
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = new Campaign(1, 0.50);

        campaignManager.activate(campaign);
        campaignManager.pause(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.50" + " €" + "\n" +
                "StateCampaign: PAUSED", campaign.toString());

    }

    @Test
    public void charge_for_click_in_a_active_campaign() throws ParseException {
        clicksRepository = new ClicksInMemory();
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = aCampaign().withID(1).withBudget(0.50).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(1).withIDUser(1).at(dateFormat.parse("07/05/2020 09:26:15")).isType("Premium").build();
        campaignManager.addClickToAList(click, campaign);

        campaignManager.chargeClicks(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.45" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }

    @Test
    public void no_charge_for_click_in_finished_campaign() throws ParseException {
        clicksRepository = new ClicksInMemory();
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = aCampaign().withID(1).withBudget(0.10).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(2).withIDUser(2).at(dateFormat.parse("07/05/2020 09:26:15")).isType("Premium").build();
        Click click2 = aClick().withID(3).withIDUser(3).at(dateFormat.parse("07/05/2020 09:27:15")).isType("Premium").build();
        campaignManager.addClickToAList(click, campaign);
        campaignManager.addClickToAList(click2, campaign);

        campaignManager.chargeClicks(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.00" + " €" + "\n" +
                "StateCampaign: FINISHED", campaign.toString());
    }

    @Test
    public void charge_for_click_in_a_paused_campaign() throws ParseException {
        clicksRepository = new ClicksInMemory();
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = aCampaign().withID(1).withBudget(0.50).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(1).withIDUser(1).at(dateFormat.parse("07/05/2020 09:18:15")).isType("Premium").build();
        campaignManager.addClickToAList(click, campaign);

        campaignManager.pause(campaign);
        Click click2 = aClick().withID(2).withIDUser(2).at(dateFormat.parse("07/05/2020 09:28:15")).isType("Premium").build();
        campaignManager.addClickToAList(click2, campaign);

        campaignManager.chargeClicks(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.45" + " €" + "\n" +
                "StateCampaign: PAUSED", campaign.toString());
    }

    @Test
    public void no_activate_campaign_if_this_is_finalised() throws ParseException {
        clicksRepository = new ClicksInMemory();
        campaignManager = new CampaignManager(clicksRepository);

        Campaign campaign = aCampaign().withID(1).withBudget(0.10).build();
        campaignManager.activate(campaign);

        Click click = aClick().withID(1).withIDUser(10).at(dateFormat.parse("07/05/2020 09:26:15")).isType("Premium").build();
        Click click2 = aClick().withID(2).withIDUser(1).at(dateFormat.parse("07/05/2020 09:27:15")).isType("Premium").build();

        campaignManager.addClickToAList(click, campaign);
        campaignManager.addClickToAList(click2, campaign);

        campaignManager.chargeClicks(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.00" + " €" + "\n" +
                "StateCampaign: FINISHED", campaign.toString());
    }

    @Test
    public void adding_click_in_a_clicks_repository() throws ParseException {
        clicksRepository = mock(ClicksRepository.class);
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = aCampaign().withID(1).withBudget(0.10).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(1).withIDUser(10).at(dateFormat.parse("07/05/2020 09:26:15")).isType("Premium").build();
        campaignManager.addClickToAList(click, campaign);
        campaignManager.chargeClicks(campaign);
        verify(clicksRepository).addClicks(click, campaign);
    }

    @Test
    public void not_be_the_same_user_at_same_instant() throws ParseException {
        clicksRepository = new ClicksInMemory();
        campaignManager = new CampaignManager(clicksRepository);
        Campaign campaign = aCampaign().withID(1).withBudget(0.10).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(1).withIDUser(10).at(dateFormat.parse("07/05/2020 09:26:15")).isType("Premium").build();
        Click click2 = aClick().withID(2).withIDUser(10).at(dateFormat.parse("07/05/2020 09:26:25")).isType("Premium").build();

        campaignManager.addClickToAList(click, campaign);
        campaignManager.addClickToAList(click2, campaign);

        campaignManager.chargeClicks(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.05" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }
}
