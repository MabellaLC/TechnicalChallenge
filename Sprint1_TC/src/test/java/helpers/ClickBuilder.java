package helpers;

import clickSystem.domain.Click;
import clickSystem.domain.valueObjects.IDClick;
import clickSystem.domain.valueObjects.IDUser;
import clickSystem.domain.valueObjects.StateClick;

import java.util.Date;

public class ClickBuilder {
    private int iD;
    private int iDUser;
    private String instantDate;
    private String typeOfClick;

    public static ClickBuilder aClick(){
        return new ClickBuilder();
    }

    public ClickBuilder withID(int iD){
        this.iD = iD;
        return this;
    }

    public ClickBuilder withIDUser(int iDUser){
        this.iDUser = iDUser;
        return this;
    }

    public ClickBuilder at(String instantDate){
        this.instantDate = instantDate;
        return this;
    }

    public ClickBuilder isType(String typeOfClick){
        this.typeOfClick = typeOfClick;
        return this;
    }

    public Click build(){
        return new Click(iD, iDUser, instantDate, typeOfClick);
    }
}
