package clickSystem.domain;

import clickSystem.domain.valueObjects.StateCampaign;
import java.util.Objects;

public class Campaign {
    private static final double ZEROBUDGET = 0.00;
    private int iDCampaign;
    private double budgetCampaign;
    private StateCampaign stateCampaign;

    public Campaign(int iDCampaign, double budgetCampaign) {
        this.iDCampaign = iDCampaign;
        this.budgetCampaign = Double.parseDouble(String.format("%.2f", budgetCampaign));
        this.stateCampaign = checkStatusOfCampaign();
    }
    private StateCampaign checkStatusOfCampaign(){
        if (budgetCampaign == ZEROBUDGET){
            return stateCampaign = StateCampaign.FINISHED;
        }
        return pause();
    }

    public StateCampaign activate() {
        if (stateCampaign.equals(StateCampaign.FINISHED )) {
            return stateCampaign =  StateCampaign.FINISHED;
        }
        return stateCampaign = StateCampaign.ACTIVATED;
    }

    public StateCampaign pause() {
        return stateCampaign = StateCampaign.PAUSED;
    }

    public Campaign chargeForClick(Click click){
        if (stateCampaign.equals(StateCampaign.PAUSED) ){
            return new Campaign(iDCampaign, budgetCampaign);
        }
        if (budgetCampaign > ZEROBUDGET ) {
            budgetCampaign -= click.chargeForClick();
            if (budgetCampaign <= ZEROBUDGET ){
                budgetCampaign = ZEROBUDGET;
                this.stateCampaign = StateCampaign.FINISHED;
            }
            return new Campaign(iDCampaign, budgetCampaign);
        }

        return new Campaign(iDCampaign, budgetCampaign);
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
