package net;

import TankBattle.Dir;
import TankBattle.Group;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.UUID;

public class TankMsgDecoder extends ByteToMessageDecoder {


    @Override
    //in是decoder的输入，out放输出的list
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 33) return; //判断信息有没有完整传完，TCP粘包

        TankMsg msg = new TankMsg();
        msg.x = in.readInt();
        msg.y = in.readInt();
        msg.dir = Dir.values()[in.readInt()];
        msg.moving = in.readBoolean();
        msg.group = Group.values()[in.readInt()];
        msg.id = new UUID(in.readLong(), in.readLong());
        out.add(msg);
    }
}
