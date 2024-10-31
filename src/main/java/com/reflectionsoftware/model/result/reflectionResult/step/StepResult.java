package com.reflectionsoftware.model.result.reflectionResult.step;

import java.util.ArrayList;
import java.util.List;

import com.reflectionsoftware.model.result.reflectionResult.step.clazz.ClazzResult;

public class StepResult {
    
    private final int step;
    private List<ClazzResult> clazzResults;

    public StepResult(int step) {
        this.step = step;
        this.clazzResults = new ArrayList<>();
    }

    public int getStep(){
        return step;
    }
    
    public void addClazzResult(ClazzResult clazzResult){
        this.clazzResults.add(clazzResult);
    }

    public List<ClazzResult> getClazzResults(){
        return clazzResults;
    }

    @Override
    public String toString() {
        return "StepResult{" +
                "step=" + step +
                ", clazzResults=" + clazzResults +
                '}';
    }
}
