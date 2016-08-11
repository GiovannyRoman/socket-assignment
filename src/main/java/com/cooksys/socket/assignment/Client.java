package com.cooksys.socket.assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.socket.assignment.model.Config;
import com.cooksys.socket.assignment.model.Student;

public class Client {

    public static void main(String[] args) throws JAXBException {
    	try
    	{
    		Config con = Utils.loadConfig("config\\config.xml", Utils.createJAXBContext());
        	
        	try (
        			Socket s = new Socket(con.getRemote().getHost(),con.getLocal().getPort());//switch get local
        			InputStream in = s.getInputStream();
        			InputStreamReader read = new InputStreamReader(in);
    				BufferedReader reader = new BufferedReader(read);
    				)	
        	
        	
    		{
        		Unmarshaller unmarsh = Utils.createJAXBContext().createUnmarshaller();
        		Student stu = (Student) unmarsh.unmarshal(reader);
        		
        		System.out.println(stu);
    		}
    		catch (IOException e)
    		{
    			e.printStackTrace();
    		}
    	}
    	catch(JAXBException e1)
    	{
    		e1.printStackTrace();
    	}
    	
    	
    	
    }
}
