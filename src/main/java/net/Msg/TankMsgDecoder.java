package net.Msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TankMsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 8) return;

        in.markReaderIndex();
        MsgType type = MsgType.values()[in.readInt()];
        int length = in.readInt();

        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        } else {
            byte[] bytes = new byte[length];
            in.readBytes(bytes);

            Msg msg = null;
            switch (type) {
                case TankJoinMsg:
                    msg = new TankJoinMsg();
                    break;
                case TankStartMoveMsg:
                    msg = new TankStartMoveMsg();
                    break;
                case TankStopMsg:
                    msg = new TankStopMsg();
                    break;
                default:
                    break;
            }

            //调用msg自身的parse将msg解析，并输出到管道
            msg.parse(bytes);
            out.add(msg);
        }
    }
}
