package cn.plasticlove.register;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luka-seu
 * @description 注册中心
 * @create 2019-08 27-22:30
 **/

public class RegisterCenter implements Register {
    //存放注册的服务集合
    private static HashMap<String,Class> methodMap = new HashMap<>();
    //暴露的端口号
    private static int PORT = 0;

    //线程池执行任务
    private static ExecutorService servicePool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public RegisterCenter(){

    }
    public RegisterCenter(int PORT){
        this.PORT = PORT;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void register() {

    }
}
