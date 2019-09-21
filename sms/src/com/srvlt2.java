package com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class srvlt2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection con=null;
		try{
			response.setContentType("text/html");
			Context iniCtx=new InitialContext();
			Queue que=(Queue) iniCtx.lookup("java:/zensarqueue");
			Destination dest=(Destination) que;
			QueueConnectionFactory qcf=(QueueConnectionFactory) iniCtx.lookup("java:/ConnectionFactory");
			con=qcf.createConnection();
			Session session=con.createSession(false,Session.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer=session.createConsumer(dest);
			PrintWriter out=response.getWriter();
			out.println("<body><html>");
			while(true){
				Message m=consumer.receive(1);
			if(m !=null){
				if(m instanceof TextMessage ){
					TextMessage message=(TextMessage) m;
				}else{
					break;
				}
			}
		}
			out.println("to send message  please < a href=msg.html > click here </a> ");
					out.println("</body></html>");
					
				}catch(Exception e)
		{
					e.printStackTrace();
		}
			}
	}
