package helpers;

import clickSystem.domain.Click;
import clickSystem.domain.valueObjects.IDClick;
import clickSystem.domain.valueObjects.IDUser;
import clickSystem.domain.valueObjects.StateClick;

import java.util.Date;

public class ClickBuilder {
    private IDClick iD;
    private IDUser iDUser;
    private Date instantDate;
    private StateClick typeOfClick;

    public static ClickBuilder aClick(){
        return new ClickBuilder();
    }

    public ClickBuilder withID(IDClick iD){
        this.iD = iD;
        return this;
    }

    public ClickBuilder withIDUser(IDUser iDUser){
        this.iDUser = iDUser;
        return this;
    }

    public ClickBuilder at(Date instantDate){
        this.instantDate = instantDate;
        return this;
    }

    public ClickBuilder isType(StateClick typeOfClick){
        this.typeOfClick = typeOfClick;
        return this;
    }

    public Click build(){
        return new Click(iD, iDUser, instantDate, typeOfClick);
    }
}
