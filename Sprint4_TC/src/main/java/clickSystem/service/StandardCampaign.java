package clickSystem.service;

import clickSystem.domain.Click;
import clickSystem.domain.valueObjects.StateClick;

public class StandardCampaign implements CampaignType {
    @Override
    public double chargeForClick(Click click) {
        if ( click.stateClick().equals(StateClick.PREMIUM) ) {
            return Double.parseDouble(String.format("%.2f", 0.05));
        }
        return Double.parseDouble(String.format("%.2f", 0.01));
    }
}
