package clickSystem.domain.valueObjects;

import java.util.Objects;

public class IDUser {
    private int iDUser;

    public IDUser(int iDUser) {
        this.iDUser = iDUser;
    }

    @Override
    public String toString() {
        return "" + iDUser;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        IDUser idUser = (IDUser) o;
        return iDUser == idUser.iDUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iDUser);
    }
}
