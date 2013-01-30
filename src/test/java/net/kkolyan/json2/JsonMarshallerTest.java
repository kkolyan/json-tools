package net.kkolyan.json2;

import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JsonMarshallerTest {
    @SuppressWarnings("unchecked")
    private <T> T createFromStream(Reader reader, Class<T> tClass) {
        return (T) Json.getEvaluator().evaluate(reader, tClass, null);
    }
    
    @Test
    public void test() throws IOException {
        Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("JsonMapperTest.json"));
        CharBuffer buffer = CharBuffer.allocate(1024);
        reader.read(buffer);
        reader.close();
        buffer.flip();
        String in = buffer.toString();

        ExampleObject object = createFromStream(new StringReader(in), ExampleObject.class);

        StringBuilder sb = new StringBuilder();
        Json.getSerializer().serialize(object, sb);

        in = in.replace("    ","");
        in = in.replace("   ","");
        in = in.replace("\n","");
        in = in.replace("\r","");

        ExampleObject actual = createFromStream(new StringReader(sb.toString()), ExampleObject.class);

        ReflectionAssert.assertReflectionEquals(object, actual);

        System.out.println(in);
        System.out.println(sb);
        assertEquals(in.length(), sb.toString().length());
    }

    private static String sortString(String s) {
        char[] cs = s.toCharArray();
        Arrays.sort(cs);
        return String.copyValueOf(cs);
    }

    @Test
    public void test2() throws IOException {
        Object2 object = new Object2();
        object.name = "Simple Name";

        assertEquals("{\"name\":\"Simple Name\",\"nameLength\":11}", Json.getSerializer().serialize(object));
    }
}
