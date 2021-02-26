package net;

import TankBattle.Dir;
import TankBattle.Group;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class TankMsg {
    public int x, y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID id;

    public TankMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.id = id;
    }

    public TankMsg() {
    }

    ;


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append(":[")
                .append("UUiD=" + this.id + "   ")
                .append(this.x + "," + this.y + "   " + this.dir)
                .append("   " + this.group)
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
