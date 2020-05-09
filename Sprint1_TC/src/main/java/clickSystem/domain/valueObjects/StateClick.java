package clickSystem.domain.valueObjects;

public enum StateClick {
    PREMIUM,
    STANDARD;

    public static StateClick CheckStateClick(String stateClick){
        if ( stateClick.equals("Premium") )
            return PREMIUM;
        return STANDARD;
    }
}
