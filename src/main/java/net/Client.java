package net;

import TankBattle.GameModel;
import TankBattle.TankBattle;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.Msg.Msg;
import net.Msg.TankJoinMsg;
import net.Msg.TankMsgDecoder;
import net.Msg.TankMsgEncoder;


public class Client {
    public static final Client INSTANCE = new Client();
    public Channel channel;

    public static void main(String[] args) {
        Client.INSTANCE.connect();
    }

    private Client() {
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
                        new Thread(() -> {
                            TankBattle.start();
                        }).start();
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

    public void send(Msg msg) {
//        ByteBuf buf = Unpooled.copiedBuffer(msg.toBytes());
        channel.writeAndFlush(msg);//写出去并且释放buf的引用,实际上可以传object？
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
        ctx.writeAndFlush(new TankJoinMsg(GameModel.getInstance().mytank));
    }

    @Override
    //ctx是上下文
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ((Msg) msg).handle();
    }
}

