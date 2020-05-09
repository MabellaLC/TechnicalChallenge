package clickSystem.domain.valueObjects;

import java.util.Objects;

public class InstantClick {
    private String instantClick;

    public InstantClick(String instantClick) {
        this.instantClick = instantClick;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        InstantClick that = (InstantClick) o;
        return Objects.equals(instantClick, that.instantClick);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instantClick);
    }
}
