package unitTest;

import clickSystem.CampaignManager;
import clickSystem.domain.Campaign;
import clickSystem.domain.Click;
import org.junit.jupiter.api.Test;

import static helpers.CampaignBuilder.aCampaign;
import static helpers.ClickBuilder.aClick;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CampaignManagerShould {

    @Test
    public void activate_a_campaign(){
        CampaignManager campaignManager = new CampaignManager();
        Campaign campaign = aCampaign().withID(1).withBudget(0.50).build();

        campaignManager.activate(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.50" + " €" +  "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }

    @Test
    public void pause_a_campaign(){
        CampaignManager campaignManager = new CampaignManager();
        Campaign campaign = new Campaign(1, 0.50);

        campaignManager.activate(campaign);
        campaignManager.pause(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.50" + " €" + "\n" +
                "StateCampaign: PAUSED", campaign.toString());

    }

    @Test
    public void charge_for_click_in_a_active_campaign(){
        CampaignManager campaignManager = new CampaignManager();
        Campaign campaign = aCampaign().withID(1).withBudget(0.50).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(1).withIDUser(1).at("07/05/2020 09:18:15").isType("Premium").build();
        campaignManager.chargeForClick(campaign, click);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.45" + " €" + "\n" +
                "StateCampaign: ACTIVATED", campaign.toString());
    }

    @Test
    public void no_charge_for_click_in_finished_campaign(){
        CampaignManager campaignManager = new CampaignManager();
        Campaign campaign = aCampaign().withID(1).withBudget(0.10).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(2).withIDUser(2).at("07/05/2020 09:18:15").isType("Premium").build();
        Click click2 = aClick().withID(3).withIDUser(3).at("07/05/2020 09:18:15").isType("Premium").build();
        campaignManager.chargeForClick(campaign, click);
        campaignManager.chargeForClick(campaign, click2);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.00" + " €" + "\n" +
                "StateCampaign: FINISHED", campaign.toString());
    }

    @Test
    public void charge_for_click_in_a_paused_campaign(){
        CampaignManager campaignManager = new CampaignManager();
        Campaign campaign = aCampaign().withID(1).withBudget(0.50).build();

        campaignManager.activate(campaign);
        Click click = aClick().withID(1).withIDUser(1).at("07/05/2020 09:18:15").isType("Premium").build();
        campaignManager.chargeForClick(campaign, click);

        campaignManager.pause(campaign);
        Click click2 = aClick().withID(2).withIDUser(2).at("07/05/2020 09:28:15").isType("Premium").build();
        campaignManager.chargeForClick(campaign, click2);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.45" + " €" + "\n" +
                "StateCampaign: PAUSED", campaign.toString());
    }

    @Test
    public void no_activate_campaign_if_this_is_finalised(){
        CampaignManager campaignManager = new CampaignManager();

        Campaign campaign = aCampaign().withID(1).withBudget(0.10).build();
        campaignManager.activate(campaign);

        Click click = aClick().withID(1).withIDUser(10).at("07/05/2020 09:18:15").isType("Premium").build();
        Click click2 = aClick().withID(2).withIDUser(1).at("07/05/2020 09:18:15").isType("Premium").build();

        campaignManager.chargeForClick(campaign, click);
        campaignManager.chargeForClick(campaign, click2);

        campaignManager.activate(campaign);

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.00" + " €" + "\n" +
                "StateCampaign: FINISHED", campaign.toString());
    }
}
