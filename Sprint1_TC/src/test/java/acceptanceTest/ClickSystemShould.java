package acceptanceTest;

import clickSystem.CampaignManager;
import clickSystem.domain.Campaign;
import clickSystem.domain.Click;
import org.junit.jupiter.api.Test;

import static helpers.CampaignBuilder.aCampaign;
import static helpers.ClickBuilder.aClick;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClickSystemShould {
    @Test
    public void charge_for_clicks_from_website(){
        CampaignManager campaignManager = new CampaignManager();

        Campaign campaign = aCampaign().withID(1).withBudget(0.20).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(1).withIDUser(1).at("07/05/2020 09:17:15").isType("Premium").build();
        Click click2 = aClick().withID(2).withIDUser(2).at("07/05/2020 09:18:15").isType("Standard").build();
        Click click3 = aClick().withID(3).withIDUser(3).at("07/05/2020 09:19:15").isType("Premium").build();
        campaignManager.chargeForClick(campaign, click);
        campaignManager.chargeForClick(campaign, click2);
        campaignManager.chargeForClick(campaign, click3);

        campaignManager.pause(campaign);
        Click click4 = aClick().withID(4).withIDUser(4).at("07/05/2020 09:20:15").isType("Premium").build();
        Click click5 = aClick().withID(5).withIDUser(5).at("07/05/2020 09:21:15").isType("Premium").build();
        campaignManager.chargeForClick(campaign, click4);
        campaignManager.chargeForClick(campaign, click5);

        campaignManager.activate(campaign);
        Click click6 = aClick().withID(6).withIDUser(6).at("07/05/2020 09:22:15").isType("Premium").build();
        Click click7 = aClick().withID(7).withIDUser(8).at("07/05/2020 09:24:15").isType("Standard").build();
        Click click8 = aClick().withID(8).withIDUser(2).at("07/05/2020 09:18:15").isType("Standard").build();
        Click click9 = aClick().withID(9).withIDUser(9).at("07/05/2020 09:25:15").isType("Standard").build();
        Click click10 = aClick().withID(10).withIDUser(10).at("07/05/2020 09:26:15").isType("Premium").build();
        campaignManager.chargeForClick(campaign, click6);
        campaignManager.chargeForClick(campaign, click7);
        campaignManager.chargeForClick(campaign, click8);
        campaignManager.chargeForClick(campaign, click9);
        campaignManager.chargeForClick(campaign, click10);

        Campaign campaignExpected = aCampaign().withID(1).withBudget(0).build();

        assertEquals(campaignExpected, campaign);
    }
}
