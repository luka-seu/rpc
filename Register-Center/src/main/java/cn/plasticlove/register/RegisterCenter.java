package cn.plasticlove.register;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
        //首先起一个server
       ServerSocket server = null;
        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress(PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            //等待客户端连接
            Socket socket = null;
            try {
                System.out.println("等待客户端连接");
                //这里是一个阻塞的过程，只有server接收到请求才会进行下一步
                socket = server.accept();
                servicePool.execute(new ServerTask(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void stop() {
        servicePool.shutdown();
    }

    @Override
    public void register(Class service, Class serviceImpl) {
        //将方法放入map中
        methodMap.put(service.getName(),serviceImpl);
    }
    //放在线程池当中执行的任务
    private class ServerTask implements Runnable {
        private Socket socket;
        public ServerTask(Socket socket) {
            this.socket = socket;
        }
        public ServerTask(){

        }


        @Override
        public void run() {
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;
            try{
                System.out.println("客户端已连接");
                ois = new ObjectInputStream(socket.getInputStream());
                //读取客户端的接口名
                String interfaceName = ois.readUTF();
                //读取客户端的方法名
                String methodName = ois.readUTF();
                //读取客户端方法参数类型
                Class[] methodPara = (Class[]) ois.readObject();
                //读取客户端方法参数
                Object[] args = (Object[]) ois.readObject();

                //从注册中心中获取服务
                Class clazz = methodMap.get(interfaceName);
                //获取方法
                Method method = clazz.getMethod(methodName);
                //执行方法，获取结果
                Object returns = method.invoke(clazz.newInstance(), args);

                oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(returns);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }finally {
                try{
                    if (oos!=null){
                        oos.close();
                    }
                    if (ois!=null){
                        ois.close();
                    }
                    if (socket!=null){
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
