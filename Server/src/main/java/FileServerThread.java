import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.*;
import java.net.*;
import java.util.*;

public class FileServerThread extends Thread {
    protected Socket socket;
    protected PrintWriter out = null;
    protected BufferedReader in = null;
    protected Vector messages;

    public FileServerThread(Socket socket, Vector messages) {
        super();
        this.socket = socket;
        this.messages = messages;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("IOException while opening a read/write connection");
        }
    }

    public void run() {
        // initialize interaction
        out.println("Connected to Main Server");
        out.println("200 Ready For File Sharing");

        try {
            processCommand();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean processCommand() {
        String message = null;
        try {
            message = in.readLine();
        } catch (IOException e) {
            System.err.println("Error reading command from socket.");
            return true;
        }
        if (message == null) {
            return true;
        }
        StringTokenizer st = new StringTokenizer(message);
        String command = st.nextToken();
        String args = null;
        if (st.hasMoreTokens()) {
            args = message.substring(command.length() + 1, message.length());
        }
        return processCommand(command, args);
    }

    protected boolean processCommand(String command, String arguments) {
        // Commands based on client request
        if (command.equalsIgnoreCase("DIRECTORY")) {
            //Display all files in the shared folder


        } else if (command.equalsIgnoreCase("UPLOAD")) {
            //Save text from file as a new filename and send to the server's shared folder from the local folder


        } else if (command.equalsIgnoreCase("DOWNLOAD")) {
            //Send the file from the server's shared folder to the local folder


        }
        return true;
    }
}
