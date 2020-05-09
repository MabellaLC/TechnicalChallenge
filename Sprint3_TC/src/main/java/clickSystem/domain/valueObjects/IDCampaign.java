package clickSystem.domain.valueObjects;

import java.util.Objects;

public class IDCampaign {
    private int idCampaign;

    public IDCampaign(int idCampaign) {

        this.idCampaign = idCampaign;
    }

    @Override
    public String toString() {
        return "" + idCampaign ;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        IDCampaign that = (IDCampaign) o;
        return idCampaign == that.idCampaign;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCampaign);
    }
}
