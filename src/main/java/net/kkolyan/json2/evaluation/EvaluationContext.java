package net.kkolyan.json2.evaluation;

/**
 * @author nplekhanov
 */
public interface EvaluationContext {
    Object getValue(String name);
    void setValue(String name, Object value);
}
