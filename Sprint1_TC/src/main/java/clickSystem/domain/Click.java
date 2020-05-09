package clickSystem.domain;

import clickSystem.domain.valueObjects.IDClick;
import clickSystem.domain.valueObjects.IDUser;
import clickSystem.domain.valueObjects.InstantClick;
import clickSystem.domain.valueObjects.StateClick;

import java.util.Date;
import java.util.Objects;

public class Click {
    private IDClick iDClick;
    private IDUser iDUser;
    private InstantClick instantClick;
    private StateClick stateClick;

    public Click(int iDClick, int iDUser, String instantClick, String stateClick) {

        this.iDClick = new IDClick(iDClick);
        this.iDUser = new IDUser(iDUser);
        this.instantClick = new InstantClick(instantClick);
        this.stateClick = StateClick.CheckStateClick(stateClick);
    }

    public double chargeForClick() {
        if ( stateClick.equals(StateClick.PREMIUM) ) {
            return Double.parseDouble(String.format("%.2f", 0.05));
        }
        return Double.parseDouble(String.format("%.2f", 0.01));
    }


    @Override
    public String toString() {
        return "Click{" +
                "iDClick=" + iDClick +
                ", iDUser=" + iDUser +
                ", instantClick=" + instantClick +
                ", stateClick=" + stateClick +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Click click = (Click) o;
        return Objects.equals(iDClick, click.iDClick) &&
                Objects.equals(iDUser, click.iDUser) &&
                Objects.equals(instantClick, click.instantClick) &&
                stateClick == click.stateClick;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iDClick, iDUser, instantClick, stateClick);
    }
}
