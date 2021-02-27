package net.Msg;

public abstract class Msg {
    static MsgType msgType;

    public abstract byte[] toBytes();

    public abstract String toString();

    public abstract void handle();

    public abstract void parse(byte[] bytes);
}
