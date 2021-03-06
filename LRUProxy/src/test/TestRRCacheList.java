import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by hensleyl4 on 11/30/2016.
 */
@RunWith(value=Parameterized.class)
public class TestRRCacheList {
    private static RRCacheList cacheList;
    private static final String directory = "./";
    private static final int maxSize = 2;
    private static int total = 0;
    private static String url;
    private static LinkedList<String> list = new LinkedList<String>();
    /**
     * returns sets of parameters to run the test cases with
     * */
    @Parameterized.Parameters
    public static Collection<Object[]> getTestParameters(){
        return Arrays.asList(new String[][]{
                {"google.com"},
                {"weather.com/weather/today"},
                {"yahoo.com"},
                {"https://www.google.com/#q=american+sign+language"}
        });
    }

    /**
     * constructor accepts url, filename, and the info at that url
     * */
    public TestRRCacheList(String url){
        this.url = url;
    }

    @BeforeClass
    public static void initialize(){
        cacheList = new RRCacheList(directory, maxSize);
    }

    /**tests the addNewObject method, which should
     * return an empty string if cache hasn't reached max size,
     * otherwise return the url of the object removed*/
    @Test
    public void testAddNewObject() throws Exception {
        if(total < maxSize) {
            assertEquals(cacheList.addNewObject(url, true), "");
            list.add(url);
            total++;
        }
        else{
            String removedURL = cacheList.addNewObject(url, true);
            assertNotNull(removedURL);
            int i = list.indexOf(removedURL);
            list.remove(removedURL);
            list.add(i, url);
        }

    }

    /**
     * tests the getCacheSize() method, which should return
     * the total number of elements in cache
     */
    @Test
    public void testGetCacheSize() throws Exception {
        assertEquals(cacheList.getCacheSize(), total);
    }

    /**
     * tests the getHead() method which should return
     * the element that is stored at start of list
     */
    @Test
    public void testGetHead() throws Exception {
        if(total != 0) {
            assertEquals(list.get(0), cacheList.getHead());
            assertEquals(cacheList.getHead(), cacheList.get(0));
        }
        else
            assertEquals(cacheList.getHead(), "");
    }

    /**
     * tests get(i) method, which should return
     * the object at index i
     */
    @Test
    public void testGet() throws Exception {
        if(total != 0)
            assertEquals(cacheList.get(0), list.get(0));
        assertEquals(cacheList.get(maxSize), "");
        assertEquals(cacheList.get(-1), "");
    }

    /**
     * tests if multiple addNewObject() methods
     * can be completed within 400 ms
     */
    @Test(timeout=400)
    public void testMultipleAdds(){
        RRCacheList temp = new RRCacheList(directory, maxSize);
        Random rand = new Random();
        for(int i = 0; i < 100; i++){
            temp.addNewObject(Integer.toString(rand.nextInt(20)), true);
        }
    }

}