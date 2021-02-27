package net;

public abstract class Msg {
    public abstract byte[] toBytes();

    public abstract void handle();
}
