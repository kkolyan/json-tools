package net.kkolyan.json2.evaluation;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nplekhanov
 */
public class SimpleEvaluationContext implements EvaluationContext {
    private EvaluationContext parent;
    private Map<String,Object> map;

    public SimpleEvaluationContext(Map<String, Object> map, EvaluationContext parent) {
        this.map = map;
        this.parent = parent;
    }

    public SimpleEvaluationContext() {
        this(new HashMap<String, Object>(), null);
    }

    public SimpleEvaluationContext(EvaluationContext parent) {
        this(new HashMap<String, Object>(), parent);
    }

    @Override
    public Object getValue(String name) {
        Object value = map.get(name);
        if (value == null && parent != null) {
            value = parent.getValue(name);
        }
        return value;
    }

    @Override
    public void setValue(String name, Object value) {
        map.put(name, value);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
