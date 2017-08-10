import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by 袁刚 on 2017/8/5.
 */
public class AtomicReferenceDemo {
    static AtomicReference<Integer> money = new AtomicReference<Integer>(19);
    //设置账户的初始值小于20，显然这是一个需要再充值的账户

    public static void main(String[] args) {

        System.out.println(money.get());


        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {

                    while (true) {
                        while (true) {
                            Integer m = money.get();
                            if (m < 20) {
                                if (money.compareAndSet(m, m + 20)) {
                                    System.out.println("余额小于20元，充值成功，余额：" + money.get() + "元");
                                    break;
                                }
                            } else {
//                                System.out.println("余额大于20，无需充值");
                                break;
                            }
                        }
                    }
                }
            }.start();
        }

            new Thread(){
                @Override
                public void run() {

                    for (int j = 0; j < 100; j++) {
                        while (true){
                            Integer m=money.get();
                            if(m>10){
                                System.out.println("大于10元");
                                if (money.compareAndSet(m,m-10)){
                                    System.out.println("成功消费10元，余额："+money.get());
                                    break;
                                }
                            }else {
                                System.out.println("没有足够的金额");
                                break;
                            }
                            try{
                                Thread.sleep(100);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }.start();
        }
    }

