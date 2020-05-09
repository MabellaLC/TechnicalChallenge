package clickSystem.infraestructure;

import clickSystem.domain.Campaign;
import clickSystem.domain.Click;
import clickSystem.domain.valueObjects.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClicksInMemory implements ClicksRepository {
    List<Click> clicksList;
    UserBoots userBoots = new UserBoots();

    public ClicksInMemory() {
        clicksList = new ArrayList<>();
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
            checkIfClickCanBeAddedToAList(click);
        }
    }

    private void checkIfClickCanBeAddedToAList(Click click) {
        if ( !clicksList.get(clicksList.size() - 1).withIDUser().equals(click.withIDUser()) ) {
            clicksList.add(click);
        }
        if ( (clicksList.get(clicksList.size() - 1).differenceBiggerThanFifteenSeconds(click)) ) {
            clicksList.add(click);
        }
    }

    @Override
    public void chargeClicks(Campaign campaign) {
        for ( Click clickActual : clicksList ) {
            campaign.chargeForClick(clickActual);
        }
        campaign.updateCampaign();
    }


    @Override
    public void compareListOfClicksWithClicksByBoots(Date date, Campaign campaign) {
        List<Click> clicksNoValids = new ArrayList<>();
        ifLisOfClicksIsEmpty(date, clicksNoValids);

        for (Click click : clicksNoValids) {
            campaign.refundClickMadeByBots(click);
        }
        campaign.updateCampaign();
    }

    private void ifLisOfClicksIsEmpty(Date date, List<Click> clicksNoValids) {
        if ( !clicksList.isEmpty() ) {
            forEveryClickInAList(date, clicksNoValids);
        }
    }

    private void forEveryClickInAList(Date date, List<Click> clicksNoValids) {
        for ( Click clickActual : clicksList ) {
            ifDateIsGreaterThanDateStablished(date, clicksNoValids, clickActual);
        }
    }

    private void ifDateIsGreaterThanDateStablished(Date date, List<Click> clicksNoValids, Click clickActual) {
        if ( clickActual.getInstantClick().getTime() > date.getTime() ) {
            checkIfUserAreEquals(clicksNoValids, clickActual);
        }
    }

    private void checkIfUserAreEquals(List<Click> clicksNoValids, Click clickActual) {
        for ( IDUser user : userBoots.getBootsList() ) {
            checkClick(clicksNoValids, clickActual, user);
        }
    }

    private void checkClick(List<Click> clicksNoValids, Click clickActual, IDUser user) {
        if ( clickActual.withIDUser().equals(user) ) {
            clicksNoValids.add(clickActual);
        }
    }
}
