package com.camsys.carmonic.pojo;

import java.io.Serializable;
import java.util.Map;

public class Bill implements Serializable {

    public boolean changeTyre;
    public boolean fixEngine;
    public boolean changeWindshield;
    public Map<String, String> other;
}
