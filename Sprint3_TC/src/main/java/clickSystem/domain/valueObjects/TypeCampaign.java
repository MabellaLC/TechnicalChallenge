package clickSystem.domain.valueObjects;

import clickSystem.service.Commands;
import clickSystem.service.DemoCampaign;
import clickSystem.service.PremiumCampaign;
import clickSystem.service.StandardCampaign;

public enum TypeCampaign {
    DEMO,
    STANDARD,
    FRIENDLY;

    public static TypeCampaign isType(String typeOf) {
        if ( typeOf == "friendly" )
            return FRIENDLY;
        if ( typeOf == "demo" )
            return DEMO;
        return STANDARD;
    }

    public Commands returnCommands(TypeCampaign type) {
        if (type.equals(FRIENDLY)) {
            return new PremiumCampaign();
        } else if (type.equals(STANDARD)) {
            return new StandardCampaign();
        } else if (type.equals(TypeCampaign.DEMO)) {
            return new DemoCampaign();
        }
        throw new UnknownError("error message");
    }
}
