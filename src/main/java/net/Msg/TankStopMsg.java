package net.Msg;

import TankBattle.GameModel;
import TankBattle.Tank;

import java.io.*;
import java.util.UUID;

public class TankStopMsg extends Msg {
    public int x, y;
    UUID id;

    public TankStopMsg(int x, int y, UUID id) {
        this.x = x;
        this.y = y;
        this.id = id;
        super.msgType = MsgType.TankStopMsg;
    }

    public TankStopMsg() {
        super.msgType = MsgType.TankStopMsg;
    }

    public TankStopMsg(Tank tank) {
        x = tank.x;
        y = tank.y;
        this.id = tank.id;
        super.msgType = MsgType.TankStopMsg;
    }

    //通过bytes给自己的属性赋值
    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream bais = null;
        DataInputStream dis = null;

        try {
            bais = new ByteArrayInputStream(bytes);
            dis = new DataInputStream(bais);

            x = dis.readInt();
            y = dis.readInt();
            id = new UUID(dis.readLong(), dis.readLong());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dis != null)
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            if (bais != null)
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n")
                .append("<" + msgType + ">")
                .append("\n")
                .append("UUiD=" + this.id.getLeastSignificantBits())
                .append("\tx=" + this.x)
                .append("\ty=" + this.y);
        return builder.toString();
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(msgType.ordinal());
            dos.writeInt(24);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (dos != null) dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return bytes;
    }

    @Override
    public void handle() {
        Tank t = (Tank) GameModel.getInstance().findByUUID(id);
        if (t != null) {
            t.setMoving(false);
            t.x = x;
            t.y = y;
        }
    }


}
