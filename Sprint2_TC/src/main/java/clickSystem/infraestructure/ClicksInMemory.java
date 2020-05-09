package clickSystem.infraestructure;

import clickSystem.domain.Campaign;
import clickSystem.domain.Click;
import clickSystem.domain.valueObjects.StateCampaign;

import java.util.ArrayList;
import java.util.List;

public class ClicksInMemory implements ClicksRepository {
    List<Click> clicksList;

    public ClicksInMemory() {
        clicksList = new ArrayList<>();
        ;
    }

    @Override
    public void addClicks(Click click, Campaign campaign) {
        if ( campaign.stateCampaign() == StateCampaign.PAUSED ) {
            clicksList.remove(clicksList.size() - 1);
        }
        if ( clicksList.isEmpty() ) {
            clicksList.add(click);
        }
        if ( clicksList.size() != 0 ) {
            if ( clicksList.get(clicksList.size() - 1).differenceBiggerThanFifteenSeconds(click) ) {
                clicksList.add(click);
            }
        }
    }

    @Override
    public void chargeClicks(Campaign campaign) {
        for ( Click clickActual : clicksList ) {
            campaign.chargeForClick(clickActual);
        }
        campaign.updateCampaign();
    }
}
