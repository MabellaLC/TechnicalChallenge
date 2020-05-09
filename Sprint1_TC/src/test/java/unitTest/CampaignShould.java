package unitTest;

import clickSystem.domain.Campaign;
import org.junit.jupiter.api.Test;

import static helpers.CampaignBuilder.aCampaign;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CampaignShould {
    @Test
    public void create_a_campaign(){
        Campaign campaign = aCampaign().withID(1).withBudget(25).build();

        assertEquals(new Campaign(1, 25), campaign);
    }

    @Test
    public void be_finished_state_in_campaign_when_budget_is_zero(){
        Campaign campaign = aCampaign().withID(1).withBudget(0).build();

        assertEquals("iDCampaign: 1" + "\n" +
                "BudgetCampaign: 0.00" + " €" + "\n" +
                "StateCampaign: FINISHED", campaign.toString());
    }
}
