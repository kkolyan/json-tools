package net.kkolyan.json2;

import org.junit.Test;
import org.unitils.thirdparty.org.apache.commons.io.IOUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ParseListTest {

    @Test
    public void test() throws IOException {
        String s = IOUtils.toString(
                getClass().getClassLoader().getResourceAsStream("ParseListTest.json"));

        @SuppressWarnings("unchecked")
        List<SubObject> list = (List) Json.getEvaluator().evaluate(new StringReader(s),
                ParameterizedTypeImpl.make(List.class, new Type[]{SubObject.class}, null), null);

        assertEquals("subValue1", list.get(0).getSubKey());
        assertEquals(list("a", "b", "c"), list.get(0).getA());
        assertEquals("subValue2", list.get(1).getSubKey());
        assertEquals(list("d", "e", "f"), list.get(1).getA());
    }

    @SuppressWarnings({"unchecked"})
    protected ArrayList list(Object... data) {
        return new ArrayList(Arrays.asList(data));
    }
}
