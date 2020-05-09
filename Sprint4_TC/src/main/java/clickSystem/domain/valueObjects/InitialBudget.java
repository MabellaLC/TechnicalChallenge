package clickSystem.domain.valueObjects;

public class InitialBudget {
    private double budgetCampaign;

    public InitialBudget(double budgetCampaign) {
        this.budgetCampaign = budgetCampaign;
    }

    public double calculateFivePercentOfInitialBudget(){
        return 5 * budgetCampaign / 100;
    }

    public double initial() {
        return budgetCampaign;
    }
}
