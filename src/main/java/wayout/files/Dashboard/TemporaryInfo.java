package wayout.files.Dashboard;

import java.io.Serializable;

public class TemporaryInfo implements Serializable,Cloneable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name="";


}
