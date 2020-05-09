package clickSystem.infraestructure;

import clickSystem.domain.Campaign;
import clickSystem.domain.Click;

import java.util.Date;

public interface ClicksRepository {
    void addClicks(Click click, Campaign campaign);
    void chargeClicks(Campaign campaign);
    void compareListOfClicksWithClicksByBoots(Date date, Campaign campaign);
}
