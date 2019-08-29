package cn.plasticlove.register;

import java.io.IOException;

public interface Register {
    public void start();
    public void stop();
    public void register(Class service, Class serviceImpl);
}
