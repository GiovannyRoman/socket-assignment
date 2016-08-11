package com.cooksys.socket.assignment;

import com.cooksys.socket.assignment.model.Config;
import com.cooksys.socket.assignment.model.Student;

import java.io.File;
import java.io.IOException;

import java.io.OutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Server {

	/**
	 * Reads a {@link Student} object from the given file path
	 *
	 * @param studentFilePath
	 *            the
	 * @param jaxb
	 * @return
	 */
	public static Student loadStudent(String studentFilePath, JAXBContext jaxb) throws JAXBException {
		File f = new File(studentFilePath);
		Unmarshaller jaxbUnmarshaller = jaxb.createUnmarshaller();
		Student s = (Student) jaxbUnmarshaller.unmarshal(f);
		return s;

	}

	public static void main(String[] args) {

		try {
			JAXBContext jax = Utils.createJAXBContext();
			Config con = Utils.loadConfig("config/config.xml", jax);

			try (ServerSocket ss = new ServerSocket(con.getLocal().getPort());
					Socket s = ss.accept();
					OutputStream out = s.getOutputStream();) {
				Marshaller marsh = Utils.createJAXBContext().createMarshaller();
				marsh.marshal(loadStudent(con.getStudentFilePath(), jax), out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
	}
}
