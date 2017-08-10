import sun.misc.Cleaner;

import javax.xml.crypto.Data;

/**
 * Created by 袁刚 on 2017/8/7.
 */
public class FutureDemo {

    public static class FutureData implements Data{
        protected RealData realData = null;
        protected boolean isReady = false;
        public synchronized void setRealData(RealData realData){
            if (isReady){
                return;
            }
            this.realData = realData;
            isReady = true;
            notifyAll();
        }

        public synchronized String getResult(){
            while (!isReady){
                try{
                    wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return realData.result;
        }
    }

    public static  class RealData implements Data{
        protected final String result;
        public RealData(String para){
            StringBuffer sb =new StringBuffer();
            for (int i = 0; i < 10; i++) {
                sb.append(para);
            }
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            result= sb.toString();
        }

        public String getResult(){
            return result;
        }
    }

    public static class Client{

        public FutureData request(final String queryStr){
            final FutureData futureData  =new FutureData();
            new Thread(){
                @Override
                public void run() {
                    RealData realData  =new RealData(queryStr);
                    futureData.setRealData(realData);
                }
            }.start();
            return futureData;
        }

    }

    public static void main(String[] args) throws Exception{
        Client client = new Client();
        FutureData data = client.request("name");
        System.out.println("请求完毕");
        try{
            System.out.println("他么的，我在干别的是");
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("数据="+data.getResult());

    }
}
