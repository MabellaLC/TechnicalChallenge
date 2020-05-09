package acceptanceTest;

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

public class ClickSystemShould {
    ClicksRepository clicksRepository;
    CampaignManager campaignManager;
    SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp(){
        clicksRepository = new ClicksInMemory();
        campaignManager = new CampaignManager(clicksRepository);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    }

    @Test
    public void charge_for_clicks_from_website() throws ParseException {

        Campaign campaign = aCampaign().withID(1).withBudget(0.20).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(1).withIDUser(1).at(dateFormat.parse("07/05/2020 09:17:15")).isType("Premium").build();
        Click click2 = aClick().withID(2).withIDUser(2).at(dateFormat.parse("07/05/2020 09:18:15")).isType("Standard").build();
        Click click3 = aClick().withID(3).withIDUser(3).at(dateFormat.parse("07/05/2020 09:19:15")).isType("Premium").build();
        campaignManager.addClickToAList(click, campaign);
        campaignManager.addClickToAList(click2, campaign);
        campaignManager.addClickToAList(click3, campaign);

        campaignManager.pause(campaign);
        Click click4 = aClick().withID(4).withIDUser(4).at(dateFormat.parse("07/05/2020 09:20:15")).isType("Premium").build();
        Click click5 = aClick().withID(5).withIDUser(5).at(dateFormat.parse("07/05/2020 09:21:15")).isType("Premium").build();
        campaignManager.addClickToAList(click4, campaign);
        campaignManager.addClickToAList(click5, campaign);

        campaignManager.activate(campaign);
        Click click6 = aClick().withID(6).withIDUser(6).at(dateFormat.parse("07/05/2020 09:22:15")).isType("Premium").build();
        Click click7 = aClick().withID(7).withIDUser(8).at(dateFormat.parse("07/05/2020 09:23:15")).isType("Standard").build();
        Click click8 = aClick().withID(8).withIDUser(2).at(dateFormat.parse("07/05/2020 09:24:15")).isType("Standard").build();
        Click click9 = aClick().withID(9).withIDUser(9).at(dateFormat.parse("07/05/2020 09:25:15")).isType("Standard").build();
        Click click10 = aClick().withID(10).withIDUser(10).at(dateFormat.parse("07/05/2020 09:26:15")).isType("Premium").build();
        campaignManager.addClickToAList(click6, campaign);
        campaignManager.addClickToAList(click7, campaign);
        campaignManager.addClickToAList(click8, campaign);
        campaignManager.addClickToAList(click9, campaign);
        campaignManager.addClickToAList(click10, campaign);

        campaignManager.chargeClicks(campaign);
        Campaign campaignExpected = aCampaign().withID(1).withBudget(0).build();

        assertEquals(campaignExpected, campaign);
    }

    @Test
    public void charge_duplicated_clicks() throws ParseException {
        Campaign campaign = aCampaign().withID(1).withBudget(0.20).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(1).withIDUser(1).at(dateFormat.parse("07/05/2020 09:16:15")).isType("Premium").build();
        Click click2 = aClick().withID(2).withIDUser(1).at(dateFormat.parse("07/05/2020 09:16:25")).isType("Premium").build();
        Click click3 = aClick().withID(2).withIDUser(1).at(dateFormat.parse("07/05/2020 09:16:55")).isType("Premium").build();
        campaignManager.addClickToAList(click, campaign);
        campaignManager.addClickToAList(click2, campaign);
        campaignManager.addClickToAList(click3, campaign);

        campaignManager.chargeClicks(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.10" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }

}
