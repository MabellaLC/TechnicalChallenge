package clickSystem;

import clickSystem.domain.Campaign;
import clickSystem.domain.Click;

public class CampaignManager {
    public void activate(Campaign campaign) {
        campaign.activate();
    }

    public void pause(Campaign campaign) {
        campaign.pause();
    }

    public void chargeForClick(Campaign campaign, Click click) {
        campaign.chargeForClick(click);

    }
}
