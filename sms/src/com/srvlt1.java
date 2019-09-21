package com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
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

public class srvlt1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con=null;
		try{
			String s=request.getParameter("m");
			response.setContentType("text/html");
			Context iniCtx=new InitialContext();
			Queue que=(Queue) iniCtx.lookup("java:/zensarqueue");
			Destination dest=(Destination) que;
			QueueConnectionFactory qcf=(QueueConnectionFactory) iniCtx.lookup("java:/ConnectionFactory");
			con=qcf.createConnection();
			Session session=con.createSession(false,Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer=session.createProducer(dest);
			TextMessage message=session.createTextMessage(s);
			PrintWriter out=response.getWriter();
			out.println("<body><html>");
			out.println("send message"+message.getText());
			producer.send(message);
			out.println(" message "+ message.getText() +" sent  successfully");
			out.println(" to receive the message please <a href=srvlt2 > click here </a>");
			out.println("</body></html>");
			}catch(Exception e){
				System.err.println("exception occured"+e.toString());
			}
	finally{
		try{
			con.close();
		}catch(JMSException e){
			e.printStackTrace();
		}
	}

}
}
