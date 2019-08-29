package cn.plasticlove.server;

/**
 * @author luka-seu
 * @description 服务端提供的方法
 * @create 2019-08 27-22:25
 **/

public class HelloServerImpl implements HelloServer{
    @Override
    public void helloRpc() {
        System.out.println("Hello RPC!");
    }

    @Override
    public void hello() {
        System.out.println("Hello World");
    }
}
