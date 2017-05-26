package okhttp_tools;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2017/5/24.
 */
public class okHttpTools {

    public ArrayList response;
    ThreadPoolExecutor execute = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    //return JSON
    public void  postTools(final String URL, final String jsonData,String Header,int operation) throws ExecutionException, InterruptedException {

        handler obj=new handler(URL,jsonData,Header,operation);
        Future<ArrayList> F1 = execute.submit(obj);

//        Thread Thread1 = new Thread(F1);
//        Thread1.start();
        this.response=F1.get();
        Log.i("CODE", (String) response.get(0));
        Log.i("content","1"+ response.get(1));
        execute.shutdown();

    }

    public ArrayList getResponse() {
        return response;
    }
}



