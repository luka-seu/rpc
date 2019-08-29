package cn.plasticlove.client;

import cn.plasticlove.server.HelloServer;

import java.net.InetSocketAddress;

/**
 * @author luka-seu
 * @description 客户端测试类
 * @create 2019-08 29-23:56
 **/

public class ClientTest {
    public static void main(String[] args) throws ClassNotFoundException {
        HelloServer hs = Client.getRemoteMethod(Class.forName("cn.plasticlove.server.HelloServer"), new InetSocketAddress("127.0.0.1", 8888));
        hs.hello();
    }
}
