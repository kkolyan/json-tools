package net.kkolyan.json2;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

/**
 * @author nplekhanov
 */
public class EmptyObjectTest {

    @Test
    public void test() {
        Object o = Json.getEvaluator().evaluate(new StringReader("{}"), EmptyObject.class, null);
        Assert.assertEquals(EmptyObject.class, o.getClass());
    }
}
