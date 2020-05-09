package helpers;

import clickSystem.domain.Campaign;

public class CampaignBuilder {
    private int iD;
    private double budget;

    public static CampaignBuilder aCampaign(){
        return new CampaignBuilder();
    }

    public CampaignBuilder withID(int iD){
        this.iD = iD;
        return this;
    }

    public CampaignBuilder withBudget(double budget){
        this.budget = budget;
        return this;
    }

    public Campaign build(){
        return new Campaign(iD, budget);
    }
}
