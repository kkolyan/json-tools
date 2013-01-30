package net.kkolyan.json2;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.*;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unchecked"})
@Ignore
public class JsonParserTest {

    protected Reader getReader() {
        return new InputStreamReader(getClass().getClassLoader().getResourceAsStream("JsonParserTest.json"));
    }

    protected LinkedHashMap<?,?> map(Object... data) {
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(Arrays.toString(data));
        }
        LinkedHashMap map = new LinkedHashMap();
        for (int i = 0; i + 1 < data.length; i += 2) {
            map.put(data[i], data[i + 1]);
        }
        return map;
    }

    protected ArrayList list(Object... data) {
        return new ArrayList(Arrays.asList(data));
    }

    protected void assert_test1(final Reader reader, final CharSequence data, final int bufferSize) {
//        Tokenizer tokenizer;
//        if (reader == null) {
//            tokenizer = new Tokenizer(data);
//        } else if (bufferSize > 0) {
//            tokenizer = new Tokenizer(reader, bufferSize);
//        } else {
//            tokenizer = new Tokenizer(reader);
//        }
//        JsonParser parser = new JsonParser(tokenizer);
//        LinkedHashMap o = (LinkedHashMap) parser.parse();
//        assertEquals(11, o.size());
//        assertEquals("stringValue",o.get("stringKey"));
//        assertEquals(new BigDecimal(123),o.get("integerKey"));
//        assertEquals(new BigDecimal("123.456"),o.get("floatKey"));
//        assertEquals(true, o.get("trueKey"));
//        assertEquals(false, o.get("falseKey"));
//        assertEquals(null, o.get("nullKey"));
//        assertEquals(list(),o.get("emptyArrayKey"));
//        assertEquals(list(
//                new BigDecimal(234),
//                true,
//                map("subKey","subValue"),
//                list("a","b","c")
//        ),o.get("arrayKey"));
//        assertEquals(map(),o.get("emptyObjectKey"));
//        assertEquals(map(
//            "aKey","aValue",
//            "bKey","bValue"
//        ), o.get("objectKey"));
//        assertEquals("This is a good day to test something",o.get("stringKey2"));
    }

    protected void assert_test1(Reader source) {
    }

    protected void assert_test1(Reader source, int bufferSize) {}

    protected void assert_test1(CharSequence source) {}

    @Test
    public void test1() {
        Reader reader = getReader();
        assert_test1(reader);
    }

    @Test
    public void test2() throws IOException {
        Reader reader = getReader();
        CharBuffer buffer = CharBuffer.allocate(1024);
        reader.read(buffer);
        buffer.flip();
        assert_test1(buffer);
    }

    @Test
    public void test3() throws IOException {
        Reader reader = getReader();
        CharBuffer buffer = CharBuffer.allocate(1024);
        reader.read(buffer);
        buffer.flip();
        String s = buffer.toString();
        s = s.replace("\n","");
        s = s.replace("\r", "");
        s = s.replace("    ", "");
        buffer = CharBuffer.wrap(s.toCharArray());
        assert_test1(buffer);
    }

    @Test
    public void test4_1() throws IOException {
        test4(32);
    }

    @Test
    public void test4_2() throws IOException {
        test4(1024);
    }

    @Test
    public void test4_3() throws IOException {
        test4(16);
    }

    public void test4(int bufferSize) throws IOException {
        Reader reader = getReader();
        final CharBuffer buffer = CharBuffer.allocate(1024);
        reader.read(buffer);
        buffer.flip();


        reader = new Reader() {
            public int read(char[] cbuf, int off, int len) throws IOException {
                if (buffer.remaining() == 0) {
                    return -1;
                }
                int n = Math.min(len, Math.min(20, buffer.remaining()));
                buffer.get(cbuf, off, n);
                return n;
            }

            public void close() throws IOException {}
        };

        assert_test1(reader, bufferSize);
    }

    private List<String> split(String in, int partsCount) {
        List<String> list = new ArrayList<String>();
        int step = in.length() / partsCount;
        for (int i = 0; i < in.length(); i += step) {
            String s = in.substring(i, Math.min(i + step, in.length()));
            list.add(s);
        }
        return list;
    }
}
