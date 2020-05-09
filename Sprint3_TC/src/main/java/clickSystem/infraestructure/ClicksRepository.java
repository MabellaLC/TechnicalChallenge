package clickSystem.infraestructure;

import clickSystem.domain.Campaign;
import clickSystem.domain.Click;

public interface ClicksRepository {
    void addClicks(Click click, Campaign campaign);
    void chargeClicks(Campaign campaign);
}
