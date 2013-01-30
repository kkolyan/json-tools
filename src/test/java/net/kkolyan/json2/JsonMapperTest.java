package net.kkolyan.json2;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.annotation.ElementType;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author CooperNout
 */
public class JsonMapperTest extends JsonParserTest {
    @Override
    protected void assert_test1(final Reader reader, final CharSequence data, final int bufferSize) {
        ExampleObject o;
        if (reader == null) {
            o = (ExampleObject) Json.getEvaluator().evaluate(reader, ExampleObject.class, null);
        } else {
            o = (ExampleObject) Json.getEvaluator().evaluate(new StringReader(data.toString()), ExampleObject.class, null);
        }
        /*
        {
           "stringKey":"stringValue",
           "integerKey":123,
           "floatKey":123.456,
           "trueKey":true,
           "falseKey":false,
           "nullKey":null,
           "emptyArrayKey":[],
           "arrayKey":[
               {"subKey":"subValue1","a":["a","b","c"]},
               {"subKey":"subValue2","a":["d","e","f"]}

           ],
           "emptyObjectKey":{},
           "objectKey":{
               "aKey":"aValue",
               "bKey":"bValue"
           },
           "stringKey2":"This is a good day to test something",
           "mapKey":{
               "aKey":"aValue",
               "bKey":"bValue"
           },
           "elementType":"FIELD"
        }
         */
        assertEquals("stringValue",o.getStringKey());
        assertEquals(123,o.getIntegerKey());
        assertEquals(123.456,o.getFloatKey(), 0.00001);
        assertEquals(true, o.isTrueKey());
        assertEquals(false, o.isFalseKey());
        assertEquals(null, o.getNullKey());
        assertEquals(new ArrayList(),o.getEmptyArrayKey());
        assertEquals("subValue1", o.getArrayKey().get(0).getSubKey());
        assertEquals(list("a", "b", "c"), o.getArrayKey().get(0).getA());
        assertEquals("subValue2", o.getArrayKey().get(1).getSubKey());
        assertEquals(list("d", "e", "f"), o.getArrayKey().get(1).getA());
        assertNotNull(o.getEmptyObjectKey());
        assertEquals("aValue", o.getObjectKey().getAKey());
        assertEquals("bValue", o.getObjectKey().getBKey());
        assertEquals("This is a good day to test something",o.getStringKey2());
        assertEquals(map(
               "aKey","aValue",
               "bKey","bValue"
        ), o.getMapKey());
        assertEquals(ElementType.FIELD, o.getElementType());
    }

    @Override
    protected Reader getReader() {
        return new InputStreamReader(getClass().getClassLoader().getResourceAsStream("JsonMapperTest.json"));
    }
}
