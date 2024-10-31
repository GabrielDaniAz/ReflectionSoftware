package com.reflectionsoftware.model.criteria.step;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.reflectionsoftware.model.criteria.step.clazz.CriteriaClazz;

public class CriteriaStep {
    @SerializedName("passo")
    private int step;

    @SerializedName("classes")
    private List<CriteriaClazz> classes; 

    public CriteriaStep() {
    }

    public int getStep() {
        return step;
    }

    public List<CriteriaClazz> getClazzes() {
        return classes;
    }

    @Override
    public String toString() {
        return "{ \"passo\": " + step + ", " +
            "\"classes\": " + ((classes != null) ? classes.toString() : "[]") + " }";
    }
}
