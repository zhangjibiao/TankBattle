package net;

import TankBattle.Dir;
import TankBattle.GameModel;
import TankBattle.Group;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;


public class Client {
    Channel channel;

    public Client() {
    }

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup(1); //线程池
        //Even网络事件：有没有accept，有没有阻塞，loop一直看，Group线程池，参数是线程数，default是cpu*2

        // 靴子，辅助启动类
        Bootstrap b = new Bootstrap();

        try {
            ChannelFuture f =
                    b.group(group) //指定线程池
                            .channel(NioSocketChannel.class) //指定aio，bio，nio
                            .handler(new ClientChannelInitializer())
                            .connect("localhost", 8888); //指定服务器，这是一个异步方法，会直接往下运行，不管

            f.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        System.out.println("<Client not connect to Server!>");
                    } else {
                        System.out.println("<Client connect to Server!>");
                        channel = future.channel();
                    }
                }
            });


            f.sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void send(TankMsg msg) {
        ByteBuf buf = Unpooled.copiedBuffer(msg.toBytes());
        channel.writeAndFlush(buf);//写出去并且释放buf的引用,实际上可以传object？
    }

    public void closeConnect() {
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    //往责任链添加自己的责任环
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new TankMsgDecoder())
                .addLast(new TankMsgEncoder())
                .addLast(new ClientHandler());
    }
}

class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //第一次连上可用，Netty最终使用ByteBuf来发送数据，ByteBuf效率高，Direct Memory
        // 因为直接使用win中的内存，而不是从win中接收的数据再
        //拷贝一份
//        ByteBuf buf = Unpooled.copiedBuffer("Hello".getBytes());
//        ctx.writeAndFlush(buf);//写出去并且释放buf的引用
        ctx.writeAndFlush(new TankMsg(10, 20, Dir.UP, false, Group.Bad, UUID.randomUUID(), false));
    }

    @Override
    //ctx是上下文
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = null;
//        try{
//            buf = (ByteBuf) msg;//
//            byte[] bytes = new byte[buf.readableBytes()];//可读的字节数
//            buf.getBytes(buf.readerIndex(),bytes);//第一个参数是从buf的哪里开始读，第二个是放到哪里
////            ClientFrame.getINSTANCE().showMsg(new String(bytes)); 不需要向这个窗口发送数据了
//            ServerFrame.INSTANCE.updateClientMsg(new String(bytes));
//
//
//        }finally {
//            if(buf != null) ReferenceCountUtil.release(buf);//释放buf
//        }
        System.out.println(msg);
        GameModel.getInstance().update((TankMsg) msg);


    }

}

