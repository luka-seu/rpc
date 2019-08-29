package cn.plasticlove.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author luka-seu
 * @description RPC客户端
 * @create 2019-08 29-23:25
 **/

public class Client {
    @SuppressWarnings("unchecked")
    public static <T> T getRemoteMethod(final Class interfaceName, final InetSocketAddress addr){
        return (T) Proxy.newProxyInstance(interfaceName.getClassLoader(), new Class<?>[]{interfaceName}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = null;
                ObjectInputStream ois = null;
                ObjectOutputStream ous = null;
                Object result = null;
                try {
                    socket = new Socket();
                    socket.connect(addr);

                    ous = new ObjectOutputStream(socket.getOutputStream());
                    //写入接口名
                    ous.writeUTF(interfaceName.getName());
                    //写入方法名
                    ous.writeUTF(method.getName());
                    //写入参数类型
                    ous.writeObject(method.getParameterTypes());
                    //写入方法参数
                    ous.writeObject(args);
                    ois = new ObjectInputStream(socket.getInputStream());
                    result = ois.readObject();


                }finally {
                    try{
                        if (ous!=null){
                            ous.close();
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
                return result;
            }
        });
    }
}
