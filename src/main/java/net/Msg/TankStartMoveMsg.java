package net.Msg;

import TankBattle.Dir;
import TankBattle.GameModel;
import TankBattle.PropertyMgr;
import TankBattle.Tank;

import java.io.*;
import java.util.UUID;

public class TankStartMoveMsg extends Msg {
    public int x, y;
    public Dir dir;
    public UUID id;
    private static final int SPEED = Integer.parseInt((String) PropertyMgr.getValue("tankSpeed"));


    public TankStartMoveMsg(int x, int y, Dir dir, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.id = id;
        super.msgType = MsgType.TankStartMoveMsg;
    }

    public TankStartMoveMsg() {
        super.msgType = MsgType.TankStartMoveMsg;
    }

    public TankStartMoveMsg(Tank tank) {
        x = tank.x;
        y = tank.y;
        dir = tank.getDir();
        id = tank.id;
        super.msgType = MsgType.TankStartMoveMsg;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n")
                .append("<" + msgType + ">")
                .append("\n")
                .append("UUiD=" + this.id.getLeastSignificantBits())
                .append("\tx=" + this.x)
                .append("\ty=" + this.y)
                .append("\tdir=" + this.dir);
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
            dos.writeInt(28);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
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
        updateTank();
    }

    private void updateTank() {
        Tank t = (Tank) GameModel.INSTANCE.findByUUID(id);
        t.x = x;
        t.y = y;
        t.setDir(dir);
        //t.setMoving(true);
    }

    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream bais = null;
        DataInputStream dis = null;

        try {
            bais = new ByteArrayInputStream(bytes);
            dis = new DataInputStream(bais);

            x = dis.readInt();
            y = dis.readInt();

            dir = Dir.values()[dis.readInt()];
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


}
