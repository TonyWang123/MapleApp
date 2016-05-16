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
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snlab.maple.mapleclient.api.Link;
import com.snlab.maple.mapleclient.api.MapleApp;
import com.snlab.maple.mapleclient.api.Packet;
import com.snlab.maple.mapleclient.api.Port;
import com.snlab.maple.mapleclient.api.Topology;
import com.snlab.maple.mapleclient.core.odl.ODLPacket;
import com.snlab.maple.mapleclient.core.odl.ODLPort;
import com.snlab.maple.mapleclient.core.odl.ODLTopology;
import com.snlab.maple.mapleclinet.core.packet.Ethernet;
import com.snlab.maple.mapleclinet.core.tracetree.Action;
import com.snlab.maple.mapleclinet.core.tracetree.MaplePacket;
import com.snlab.maple.mapleclinet.core.tracetree.Match;
import com.snlab.maple.mapleclinet.core.tracetree.Rule;
import com.snlab.maple.mapleclinet.core.tracetree.TraceTree;
import com.snlab.mapleserver.message.ActionType;
import com.snlab.mapleserver.message.KeyValueObject;
import com.snlab.mapleserver.message.Message;
import com.snlab.mapleserver.message.MessageType;

import edu.columbia.cs.psl.phosphor.runtime.MultiTainter;
import edu.columbia.cs.psl.phosphor.runtime.Tainter;

public class MapleClient implements MapleDataPathAdaptor, MapleDataStoreAdaptor{/////should implement maple datastore path interface
	
	String controllerAddress;
	
	MapleApp app;
	
    IoConnector connector;
	
	IoSession session;
	
	MapleCore mc;
	
	static ObjectMapper mapper = new ObjectMapper();

	public void setup(MapleConfig conf){
		controllerAddress = conf.getControllerAddress();
		mc = new MapleCore(this, this);////also setup the interface
	}
	
	public void addMapleApp(MapleApp app){//////synchronize maple app and maple core
		this.app = app;///maybe we don't use
		app.ms = new MapleSystem(mc);
		mc.setMapleApp(app);
	}
	
	private void sendRegMsg(){
		Message msg = new Message();
		msg.setType(MessageType.REG);
		try {
			session.write(mapper.writeValueAsString(msg));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void test(){
		String data = "////////gg8OA1hWCAYAAQgABgQAAYIPDgNYVgoAAAEAAAAAAAAKAAAC";
		Port ingress = new ODLPort();
		mc.onPacket(data.getBytes(), ingress);
		System.out.println("data change");
		Topology topo = new ODLTopology();
		mc.onDataChanged(topo);
		mc.onDataChanged(topo);
	}
	
	public void register(){
		connect();
		sendRegMsg();
	}
	
	public void close(){
		
	}
	
	private void setupConnector(){

		connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(10 * 1000);
		
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        
        //connector.getFilterChain().addLast("logger", new LoggingFilter());  
        //connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        
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
				ObjectMapper mapper = new ObjectMapper();
				Message msg = null;
				try{
					msg = mapper.readValue(message.toString(), Message.class);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				if(msg.getType() == MessageType.PKT_IN){
					System.out.println("packet in");
					Message outMsg = new Message();
					com.snlab.mapleserver.message.Action action = new com.snlab.mapleserver.message.Action();
					action.setType(ActionType.FORWARD);
					outMsg.setAction(action);
					outMsg.setData(msg.getData());
					outMsg.setType(MessageType.PKT_OUT);
					session.write(mapper.writeValueAsString(outMsg));
				}else if(msg.getType() == MessageType.OBJECT){
					KeyValueObject object = msg.getKeyValueObject();
					String type = object.getType();
					if(type.equals("topology")){
						System.out.println("deal with topology");
					}
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

	@Override
	public Object readData(String xpath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeData(String xpath, Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeTraceTree(TraceTree traceTree) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendPacket(byte[] payload, Port ingress, Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void installPath(Action action, Match match, Integer priority) {
		// TODO Auto-generated method stub
		System.out.println("install path");
		System.out.println(action.toString());
		System.out.println(match.toString());
	}

	@Override
	public void deletePath(Action action, Match match, Integer priority) {
		// TODO Auto-generated method stub
		System.out.println("delete path");
		System.out.println(action.toString());
		System.out.println(match.toString());
	}

	@Override
	public void installRule(Rule r, Port sw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRule(Rule r, Port sw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMapleCore(MapleCore mapleCore) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args){
		MapleClient mc = new MapleClient();
		MapleConfig conf = new MapleConfig();
		conf.setControllerAddress("localhost");
		mc.setup(conf);
		mc.register();
	}
}
