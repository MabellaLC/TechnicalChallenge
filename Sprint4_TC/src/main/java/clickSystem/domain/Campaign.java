package clickSystem.domain;

import clickSystem.domain.valueObjects.IDCampaign;
import clickSystem.domain.valueObjects.InitialBudget;
import clickSystem.domain.valueObjects.StateCampaign;
import clickSystem.domain.valueObjects.TypeCampaign;
import clickSystem.service.CampaignType;

import java.util.Objects;

import static clickSystem.domain.valueObjects.StateCampaign.FINISHED;
import static clickSystem.domain.valueObjects.StateCampaign.PAUSED;
import static clickSystem.domain.valueObjects.TypeCampaign.*;

public class Campaign {
    private static final double ZERO_BUDGET = 0.00;

    private IDCampaign iDCampaign;
    private double budgetCampaign;
    private String typeOf;
    private StateCampaign stateCampaign;
    private TypeCampaign typeCampaign;
    private InitialBudget initialBudget;
    double amount = 0.00;

    public Campaign(IDCampaign iDCampaign, double budgetCampaign, String typeOf) {
        this.iDCampaign = iDCampaign;
        this.budgetCampaign = Double.parseDouble(String.format("%.2f", budgetCampaign));
        this.typeOf = typeOf;
        this.stateCampaign = checkStatusOfCampaign();
        this.typeCampaign = TypeCampaign.isType(typeOf);
        this.initialBudget = new InitialBudget(budgetCampaign);
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
        if (stateCampaign == FINISHED) {
            return;
        }
        stateCampaign = PAUSED;
    }

    public void chargeForClick(Click click) {
        CampaignType commands = typeCampaign.returnCommands(typeCampaign);
        if ( budgetCampaign > ZERO_BUDGET ) {
            checkTypeOfCampaignAndChargeClick(typeCampaign, click, commands);
        }
        if ( budgetCampaign <= ZERO_BUDGET ) {
            budgetCampaign = ZERO_BUDGET;
            this.stateCampaign = FINISHED;
        }
    }

    private void checkTypeOfCampaignAndChargeClick(TypeCampaign typeCampaign, Click click, CampaignType commands) {
        if ( typeCampaign.equals(PREMIUM) ) {
            budgetCampaign -= commands.chargeForClick(click);
        }
        if(typeCampaign.equals(STANDARD) ){
            budgetCampaign -= commands.chargeForClick(click);
        }
        if( typeCampaign.equals(DEMO) ) {
            budgetCampaign -= commands.chargeForClick(click);
        }
    }

    public void refundClickMadeByBots(Click click){
        CampaignType commands = typeCampaign.returnCommands(typeCampaign);
        checkTypeOfCampaignAndRefundClick(typeCampaign, click, commands);
    }

    private void checkTypeOfCampaignAndRefundClick(TypeCampaign typeCampaign, Click click, CampaignType commands) {
        if(typeCampaign.equals(STANDARD) ){
            amount = commands.chargeForClick(click);
            budgetCampaign -= amount;
        }
        if( typeCampaign.equals(DEMO) ) {
            budgetCampaign += commands.chargeForClick(click);
        }
        if (typeCampaign.equals(PREMIUM) ) {
            amount += commands.chargeForClick(click);
            checkAmountOfTotalBotsClick();
        }
    }

    private void checkAmountOfTotalBotsClick() {
        if ( amount > initialBudget.calculateFivePercentOfInitialBudget()){
            budgetCampaign -= amount;
        }else {
            budgetCampaign = initialBudget.initial();
        }
    }

    public StateCampaign stateCampaign() {
        return stateCampaign;
    }

    public void updateCampaign(){
        new Campaign(iDCampaign, budgetCampaign, typeOf);
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
        return Double.compare(campaign.budgetCampaign, budgetCampaign) == 0 &&
                Objects.equals(iDCampaign, campaign.iDCampaign) &&
                Objects.equals(typeOf, campaign.typeOf) &&
                stateCampaign == campaign.stateCampaign;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iDCampaign, budgetCampaign, typeOf, stateCampaign);
    }
}
