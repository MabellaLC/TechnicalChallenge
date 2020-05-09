package clickSystem.infraestructure;

import clickSystem.domain.valueObjects.IDUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserBoots {
    List<IDUser> userList;

    public UserBoots() {
        this.userList = new ArrayList<>();
        listOfUserBoots();
    }

    private void listOfUserBoots(){
        userList.add(new IDUser(15));
        userList.add(new IDUser(16));
        userList.add(new IDUser(21));
    }

    public List<IDUser> getBootsList(){
        return new ArrayList<>(userList);
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        UserBoots userBoots = (UserBoots) o;
        return Objects.equals(userList, userBoots.userList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userList);
    }

    @Override
    public String toString() {
        return "" + userList;
    }
}
