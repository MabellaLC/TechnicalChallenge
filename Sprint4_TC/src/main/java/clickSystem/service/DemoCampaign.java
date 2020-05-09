package clickSystem.service;

import clickSystem.domain.Click;

public class DemoCampaign implements CampaignType {
    @Override
    public double chargeForClick(Click click) {
        return 0.00;
    }
}
