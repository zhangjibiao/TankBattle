package net.Msg;

import TankBattle.Dir;
import TankBattle.GameModel;
import TankBattle.Tank;
import net.Client;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class TankStartMoveMsg extends Msg {
    public int x, y;
    public Dir dir;
    public UUID id;

    public TankStartMoveMsg(int x, int y, Dir dir, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.id = id;
    }

    public TankStartMoveMsg() {
    }

    public TankStartMoveMsg(Tank tank) {
        x = tank.x;
        y = tank.y;
        dir = tank.getDir();
        id = tank.id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName())
                .append(":[")
                .append("UUiD=" + this.id + "   ")
                .append("x=" + this.x + " y=" + this.y + "  dir=" + this.dir)
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
        if (GameModel.getInstance().findByUUID(id) != null) return;
        else {
            System.out.println(this);
//            GameModel.getInstance().newTankJoin(this);

            //让后加入的坦克能够看到自己
            Client.INSTANCE.channel.writeAndFlush(new TankStartMoveMsg(GameModel.getInstance().mytank));
        }
    }

    @Override
    public void parse(byte[] bytes) {

    }


}
