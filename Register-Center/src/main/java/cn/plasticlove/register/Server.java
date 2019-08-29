package cn.plasticlove.register;

import cn.plasticlove.server.HelloServer;
import cn.plasticlove.server.HelloServerImpl;

/**
 * @author luka-seu
 * @description 服务启动类
 * @create 2019-08 29-23:53
 **/

public class Server {
    public static void main(String[] args) {
        Register server = new RegisterCenter(8888);
        server.register(HelloServer.class, HelloServerImpl.class);
        server.start();
    }
}
