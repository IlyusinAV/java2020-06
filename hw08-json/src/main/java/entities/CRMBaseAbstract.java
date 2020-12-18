package entities;

import java.util.Set;

public abstract class CRMBaseAbstract extends AuthBaseAbstract {

    private Integer num;
    private Integer userID;
    private Set<Integer> marks;

    public CRMBaseAbstract() {
        super();
    }
}