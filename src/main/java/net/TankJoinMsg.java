package net;

import TankBattle.Dir;
import TankBattle.Group;
import TankBattle.Tank;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class TankJoinMsg {
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
    }

    public TankJoinMsg() {
    }

    ;

    public TankJoinMsg(Tank tank) {
        x = tank.x;
        y = tank.y;
        dir = tank.getDir();
        moving = tank.getMoving();
        group = tank.getGroup();
        live = tank.live;
        id = tank.id;
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append(":[")
                .append("UUiD=" + this.id + "   ")
                .append("x=" + this.x + " y=" + this.y + "  dir=" + this.dir)
                .append("   group=" + this.group)
                .append("live=" + this.live)
                .append("]");
        return builder.toString();
    }

    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
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


}
