package clickSystem.domain;

import clickSystem.domain.valueObjects.IDClick;
import clickSystem.domain.valueObjects.IDUser;
import clickSystem.domain.valueObjects.StateClick;

import java.util.Date;
import java.util.Objects;

public class Click {
    private IDClick iDClick;
    private IDUser iDUser;
    private Date instantClick;
    private StateClick stateClick;

    public Click(IDClick iDClick, IDUser iDUser, Date instantClick, StateClick stateClick) {
        this.iDClick = iDClick;
        this.iDUser = iDUser;
        this.instantClick = instantClick;
        this.stateClick = stateClick;
    }

    public boolean differenceBiggerThanFifteenSeconds(Click click) {
        long secondsOfDifference = 0;
        long difference = instantClick.getTime() - click.instantClick.getTime();
        if ( difference != 0 ) {
            secondsOfDifference = difference / 1000;
        }
        return Math.abs(secondsOfDifference) > 15;
    }

    public Date getInstantClick() {
        return instantClick;
    }

    @Override
    public String toString() {
        return "Click{" +
                "iDClick: " + iDClick +
                ", idUser: " + iDUser +
                ", instantClick: " + instantClick +
                ", stateClick: " + stateClick +
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

    public StateClick stateClick() {
        return stateClick;
    }

    public IDUser withIDUser() {
        return iDUser;
    }
}
