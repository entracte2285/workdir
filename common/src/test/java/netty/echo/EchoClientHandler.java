/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Handler implementation for the echo client.  It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler() {
        firstMessage = Unpooled.buffer(EchoClient.SIZE);
        for (int i = 0; i < firstMessage.capacity(); i ++) {
            firstMessage.writeByte((byte) i);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(firstMessage);
        System.out.println("Active");


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.print(((ByteBuf)msg).toString(Charset.defaultCharset()));
        //ctx.write(msg);
        try{
            System.out.println("请输入");
            String str = readUserInput();
           // System.out.println(Unpooled.copiedBuffer(str.getBytes()));
            ctx.writeAndFlush(Unpooled.copiedBuffer(str.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
       ctx.flush();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();System.out.println();
        System.out.println();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        /* Close the connection when an exception is raised. */
        cause.printStackTrace();
        ctx.close();
    }

    private static String readUserInput() throws IOException {
        InputStreamReader is_reader = new InputStreamReader(System.in);
        return new BufferedReader(is_reader).readLine();
    }
}
