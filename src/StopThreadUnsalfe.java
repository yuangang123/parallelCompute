import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by 袁刚 on 2017/7/31.
 */
public class StopThreadUnsalfe {
    public static User u  =new User();

    public static class User{
        private int id;
        private String name;
        public User(){
            id=0;
            name = "0";
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String toString(){
            return "User [id="+id+",name="+name+"]";
        }
    }

    public static class ChangeOjectThread extends Thread{
        @Override
        public void run() {
//            super.run();
            while (true){
                synchronized (u){
                    int v = (int)(System.currentTimeMillis()/1000);
                    u.setId(v);
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    u.setName(String.valueOf(v));
                }
                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread{
        @Override
        public void run() {
//            super.run();
            while (true){
                synchronized (u){
                    if(u.getId()!=Integer.parseInt(u.getName())){
                        System.out.println(u.toString());
                    }
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[]args)throws InterruptedException{
        new ReadObjectThread().start();
        while (true){
            Thread t = new ChangeOjectThread();
            t.start();
            Thread.sleep(150);
            t.stop();
        }
    }
}
