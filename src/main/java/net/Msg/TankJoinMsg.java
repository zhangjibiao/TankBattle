package net.Msg;

import TankBattle.Dir;
import TankBattle.GameModel;
import TankBattle.Group;
import TankBattle.Tank;
import net.Client;

import java.io.*;
import java.util.UUID;

public class TankJoinMsg extends Msg {
    public int x, y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID id;
    public boolean live;

    public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id, boolean live) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.id = id;
        this.live = live;
        super.msgType = MsgType.TankJoinMsg;
    }

    public TankJoinMsg() {
        super.msgType = MsgType.TankJoinMsg;
    }

    public TankJoinMsg(Tank tank) {
        x = tank.x;
        y = tank.y;
        dir = tank.getDir();
        moving = tank.getMoving();
        group = tank.getGroup();
        live = tank.live;
        id = tank.id;
        super.msgType = MsgType.TankJoinMsg;
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

            dir = Dir.values()[dis.readInt()];
            moving = dis.readBoolean();
            group = Group.values()[dis.readInt()];
            id = new UUID(dis.readLong(), dis.readLong());
            live = dis.readBoolean();

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
        builder.append(this.getClass().getName())
                .append(":[")
                .append("UUiD=" + this.id + "   ")
                .append("x=" + this.x + " y=" + this.y + "  dir=" + this.dir)
                .append("   group=" + this.group)
                .append("  live=" + this.live)
                .append("]");
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
            dos.writeInt(34);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeBoolean(live);
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
        if (GameModel.getInstance().findByUUID(id) != null) return;
        else {
            System.out.println(this);
            GameModel.getInstance().newTankJoin(this);

            //让后加入的坦克能够看到自己
            Client.INSTANCE.channel.writeAndFlush(new TankJoinMsg(GameModel.getInstance().mytank));
        }
    }


}
