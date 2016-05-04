package com.snlab.maple.mapleclinet.core;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.snlab.maple.mapleclient.api.Link;
import com.snlab.maple.mapleclient.api.MapleApp;
import com.snlab.maple.mapleclient.api.Packet;
import com.snlab.maple.mapleclient.core.odl.ODLPacket;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Tainter;

public class MapleClient {/////should implement maple datastore path interface
	
	String controllerAddress;
	
	MapleApp app;
	
    IoConnector connector;
	
	IoSession session;
	
	MapleCore mc;

	public void setup(MapleConfig conf){
		controllerAddress = conf.getControllerAddress();
		mc = new MapleCore();////also setup the interface
	}
	
	public void addMapleApp(MapleApp app){//////synchronize maple app and maple core
		this.app = app;///maybe we don't use
		app.ms = new MapleSystem(mc);
		mc.setMapleApp(app);
	}
	
	public void register(){
		connect();
		session.write("register");
	}
	
	public void close(){
		
	}
	
	private void setupConnector(){

		connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(10 * 1000);
		
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		
        connector.setHandler(new IoHandlerAdapter() {
			
			@Override
			public void sessionCreated(IoSession session) throws Exception {
				System.out.println("session created");
			}

			@Override
			public void sessionOpened(IoSession session) throws Exception {
				System.out.println("session opened");
			}

			@Override
			public void sessionClosed(IoSession session) throws Exception {
			}

			@Override
			public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
			}

			@Override
			public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
				session.close(true);
			}

			@Override
			public void messageReceived(IoSession session, Object message) throws Exception {
				System.out.println("Received message " + message);
				String receivedMessage = message.toString();
				if(receivedMessage.equals("ok")){
					System.out.println("handling packet");
					ODLPacket p = new ODLPacket();////////connect to maple core
					p.setSrcMac("m1");
					p.setDstMac("m3");
					Action action = app.onPacket(p);
					if(action.path.isEmpty())System.out.println("path is empty");
					for(Link link: action.path){
						System.out.println("link taint: " + MultiTainter.getTaint(link));
					}
				}else{
					System.out.println("sth wrong");
				}
			}

			@Override
			public void messageSent(IoSession session, Object message) throws Exception {
				System.out.println("Sent message " + message);
			}
		});
	}
	
	private void connect(){
		setupConnector();
		try {
			ConnectFuture future = connector.connect(new InetSocketAddress("localhost", 10000));
			future.awaitUninterruptibly();
			session = future.getSession();
			session.getConfig().setUseReadOperation(true);
		} catch (RuntimeIoException e) {
			System.out.println(e.getMessage());
		}
	}
}
