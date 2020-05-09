package clickSystem.domain;

import clickSystem.domain.valueObjects.StateCampaign;

import java.util.Objects;

import static clickSystem.domain.valueObjects.StateCampaign.FINISHED;
import static clickSystem.domain.valueObjects.StateCampaign.PAUSED;

public class Campaign {
    private static final double ZERO_BUDGET = 0.00;
    private int iDCampaign;
    private double budgetCampaign;
    private StateCampaign stateCampaign;

    public Campaign(int iDCampaign, double budgetCampaign) {
        this.iDCampaign = iDCampaign;
        this.budgetCampaign = Double.parseDouble(String.format("%.2f", budgetCampaign));
        this.stateCampaign = checkStatusOfCampaign();
    }

    private StateCampaign checkStatusOfCampaign(){
        if (budgetCampaign == ZERO_BUDGET){
            return this.stateCampaign = FINISHED;
        }
        return this.stateCampaign = StateCampaign.ACTIVATED;
    }

    public void activate() {
        if (stateCampaign == FINISHED) {
            return;
        }
        this.stateCampaign = StateCampaign.ACTIVATED;
    }

    public void pause() {
        if (stateCampaign == FINISHED ) {
            return;
        }
        stateCampaign = PAUSED;
    }

    public void chargeForClick(Click click){
        if (budgetCampaign > ZERO_BUDGET ) {
            budgetCampaign -= click.chargeForClick();
            if (budgetCampaign <= ZERO_BUDGET ){
                budgetCampaign = ZERO_BUDGET;
                this.stateCampaign = FINISHED;
            }
        }
    }

    public StateCampaign stateCampaign() {
        return stateCampaign;
    }

    public void updateCampaign(){
        new Campaign(iDCampaign, budgetCampaign);
    }

    @Override
    public String toString() {
        return "iDCampaign: " + iDCampaign + "\n" +
                "BudgetCampaign: " + String.format("%.2f", budgetCampaign) + " €" + "\n" +
                "StateCampaign: " + stateCampaign ;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Campaign campaign = (Campaign) o;
        return iDCampaign == campaign.iDCampaign &&
                Double.compare(campaign.budgetCampaign, budgetCampaign) == 0 &&
                stateCampaign == campaign.stateCampaign;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iDCampaign, budgetCampaign, stateCampaign);
    }
}
