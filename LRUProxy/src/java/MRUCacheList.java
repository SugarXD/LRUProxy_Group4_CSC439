/**
 * Created by Kirby Owens on 11/13/16.
 */
import java.util.LinkedList;
import java.util.List;
public class MRUCacheList implements CacheList {
    private CacheLog log; // not used yet
    private LinkedList<String> linkedList;
    private int maxSize;

    /**
     * Constructor.  The minimum cache size is 1.
     * @param directory - cache log directory for logging
     *                    objects removed from cache
     * @param maxsize - maximum number of objects to cache
     */
    public MRUCacheList(String directory, int maxsize)
    {
        log = new CacheLog(directory);
        linkedList=new LinkedList<String>();
        if (maxsize<1)
        {
            this.maxSize=1;
        }
        else
        {
            this.maxSize=maxsize;
        }
    }

    public String addNewObject(String URL, boolean hit)
    {
        String removedURL = "";

        if (hit)
        {
            linkedList.remove(URL);
        }


        // If size is MAXSIZE, remove first link
        if (linkedList.size() == maxSize)
        {
            removedURL = (String)linkedList.getFirst();
            log.logRemoval(removedURL);
            linkedList.removeFirst();
        }

        // Newest is always the first.
        linkedList.addFirst(URL);

        return removedURL;
    }

    public int getCacheSize()
    {
        return linkedList.size();
    }

    public String getHead()
    {
        String returnedURL="";
        if (linkedList.size()>0)
        {
            returnedURL=linkedList.getFirst().toString();
        }
        return returnedURL;
    }

    public String get(int i)
    {
        String returnedURL="";
        if (i<linkedList.size() && i >= 0)
        {
            returnedURL=linkedList.get(i).toString();
        }
        return returnedURL;
    }
}
