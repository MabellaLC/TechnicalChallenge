package clickSystem.domain.valueObjects;

import java.util.Objects;

public class IDClick {
    private int iDClick;

    public IDClick(int iDClick) {

        this.iDClick = iDClick;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        IDClick idClick = (IDClick) o;
        return iDClick == idClick.iDClick;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iDClick);
    }
}
