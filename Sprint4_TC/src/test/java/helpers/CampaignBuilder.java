package helpers;

import clickSystem.domain.Campaign;
import clickSystem.domain.valueObjects.IDCampaign;

public class CampaignBuilder {
    private double budget;
    private String typeOf;
    private IDCampaign iD;

    public static CampaignBuilder aCampaign(){
        return new CampaignBuilder();
    }

    public CampaignBuilder withID(IDCampaign iD){
        this.iD = iD;
        return this;
    }

    public CampaignBuilder withBudget(double budget){
        this.budget = budget;
        return this;
    }

    public CampaignBuilder typeOf(String typeOf){
        this.typeOf = typeOf;
        return this;
    }

    public Campaign build(){
        return new Campaign(iD, budget, typeOf);
    }
}
