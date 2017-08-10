import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 袁刚 on 2017/8/7.
 */
public class ParallelSearch {

    static int arr[];
    static ExecutorService pool = Executors.newCachedThreadPool();
    static final int Thread_Num = 2;
    static AtomicInteger result = new AtomicInteger(-1);

    public static int search(int searchValue,int beginPos,int endPos ){
        int i =0 ;
        for (i=beginPos;i<endPos;i++){
            if (result.get()>0){
                return result.get();
            }

            if (arr[i]==searchValue){
                if (!result.compareAndSet(-1,i)){
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }

    public static class SearchTask implements Callable<Integer>{
        int begin,end,searchValue;

        public SearchTask(int begin, int end, int searchValue) {
            this.begin = begin;
            this.end = end;
            this.searchValue = searchValue;
        }

        @Override
        public Integer call() throws Exception {

            int re  = search(searchValue,begin,end);
            return  re;
        }
    }

    public static int pSearch(int searchValue) throws Exception{
        int subArrsize  = arr.length/Thread_Num;
        List<Future<Integer>> re = new ArrayList<>();
        for (int i = 0; i < arr.length; i+=subArrsize) {
            int end = i+subArrsize;
            if (end>=arr.length){
                end = arr.length;
            }
            re.add(pool.submit(new SearchTask(searchValue,i,end)));

        }
        for (Future<Integer> fu:re){
            if(fu.get()>=0){
                return fu.get();
            }
        }
        return -1;
    }
}
