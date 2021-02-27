package net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.Msg.TankJoinMsg;
import net.Msg.TankMsgDecoder;
import net.Msg.TankMsgEncoder;

import java.util.Iterator;

public class Server {
    //用channel组处理所有的channel上的事件，用默认的线程
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void serverStart() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);//作accept作用
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);


        ServerBootstrap b = new ServerBootstrap();
        try {
            ChannelFuture f = b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        //每个客户端连接，这个方法会被调用
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pl = ch.pipeline();
                            //给处理的责任链加上自己的handler
                            pl.addLast(new TankMsgDecoder())  //服务端先decode再处理
                                    .addLast(new TankMsgEncoder())
                                    .addLast(new ServerChildHandler());


                        }
                    })
                    .bind(8888)
                    .sync();

            ServerFrame.INSTANCE.updateServerMsg("<Server start!>");
            f.channel().closeFuture().sync();//关门的服务员
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

class ServerChildHandler extends ChannelInboundHandlerAdapter {
    @Override
    //通道连接上时就把其加入通道组
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clients.add(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Server.clients.remove(ctx.channel()); //移除报错的channel
        ctx.close();//关闭上下文
    }

    @Override
    //每一个客户端传过来的是msg
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf;
//        buf = (ByteBuf) msg;//
//        byte[] bytes = new byte[buf.readableBytes()];//可读的字节数
//        buf.getBytes(buf.readerIndex(),bytes);//第一个参数是从buf的哪里开始读，第二个是放到哪里
//
//        String s = new String(bytes);
//        if(s.equals("_bye_")){
//            ServerFrame.INSTANCE.updateServerMsg("<客户端退出！>");
//            Server.clients.remove(ctx.channel());
//            ctx.close();
//        }else{
//            Server.clients.writeAndFlush(msg);
//            ServerFrame.INSTANCE.updateServerMsg(s);
//        }
//        Server.clients.writeAndFlush(msg);
        Iterator<Channel> it = Server.clients.iterator();
        Channel c;
        while (it.hasNext()) {
            c = it.next();
            if (c != ctx.channel())
                c.writeAndFlush(msg);
        }
        ServerFrame.INSTANCE.updateServerMsg(
                ((TankJoinMsg) msg)
                        .toString());
        System.out.println(((TankJoinMsg) msg)
                .toString());

    }
}
