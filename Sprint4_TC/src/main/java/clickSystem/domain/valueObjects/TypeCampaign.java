package clickSystem.domain.valueObjects;

import clickSystem.service.*;

public enum TypeCampaign {
    DEMO,
    STANDARD,
    PREMIUM;

    public static TypeCampaign isType(String typeOf) {
        if ( typeOf == ("premium") )
            return PREMIUM;
        if ( typeOf == ("demo") )
            return DEMO;
        return STANDARD;
    }

    public CampaignType returnCommands(TypeCampaign type) {
        if (type.equals(PREMIUM)) {
            return new PremiumCampaign();
        } else if (type.equals(STANDARD)) {
            return new StandardCampaign();
        } else if (type.equals(TypeCampaign.DEMO)) {
            return new DemoCampaign();
        }
        throw new UnknownCampaign("Type campaign is not valid");
    }
}
