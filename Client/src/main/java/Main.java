import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Main extends Application {
    @FXML private Button download;
    @FXML private Button upload;

    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter networkOut = null;
    private BufferedReader networkIn = null;
    public static String SERVER_ADDRESS = "Local Host";
    public  static int   SERVER_PORT = 2020;

    public Main() {

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: "+SERVER_ADDRESS);
        } catch (IOException e) {
            System.err.println("IOException while connecting to server: "+SERVER_ADDRESS);
        }
        if (socket == null) {
            System.err.println("socket is null");
        }
        try {
            networkOut = new PrintWriter(socket.getOutputStream(), true);
            networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("IOException while opening a read/write connection");
        }

        in = new BufferedReader(new InputStreamReader(System.in));

        try {
            processUserInput();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // Display Menu  of actions
    // Alternatively you can always be in "reading mode" whatever is typed gets send to the server/other clients without they having to "List all messages"
    // -- This would work 100x better and easier if you make at least the client a JavaFX application, the user can type in a textbox, when pressed <enter> you send the message
    // --- Every time the server gets a message they send to all the other clients who get their UI refreshed with the most recent messages, etc.
    protected boolean processUserInput() {
        String input = null;

        // print the menu
        System.out.println("Commands: ");
        System.out.println("DIR - List Of Shared Folder Content");
        System.out.println("UPLOAD - Upload File To Server Shared Folder");
        System.out.println("DOWNLOAD - Download File To Client Shared Folder");
        System.out.print("Command> ");

        try {
            input = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (input.equalsIgnoreCase("DIR")) {
            dir();
        } else if (input.equalsIgnoreCase("UPLOAD")) {
            upload();
        } else if (input.equalsIgnoreCase("DOWNLOAD")) {
            download();
        }
        return true;
    }

    // menu option 1
    public void dir() {
        String message = null;
        networkOut.println("DIRECTORY");

        //Read response from server
        try {
            message = networkIn.readLine();
        } catch (IOException e) {
            System.err.println("Error reading from socket.");
        }
    }

    // menu option 2
    public void upload() {
        String message = null;
        networkOut.println("UPLOAD");

        //Read response from server
        try {
            message = networkIn.readLine();
        } catch (IOException e) {
            System.err.println("Error reading from socket.");
        }
    }

    //menu option 3
    public void download() {
        String message = null;
        networkOut.println("DOWNLOAD");

        //Read response from server
        try {
            message = networkIn.readLine();
        } catch (IOException e) {
            System.err.println("Error reading from socket.");
        }
    }

    @FXML
    public void press(ActionEvent event){
        download();
    }

    @FXML
    public void press2(ActionEvent event){
        upload();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Image icon = new Image("https://image.flaticon.com/icons/png/128/2450/2450869.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("File Sharer v1.0");
        primaryStage.setScene(new Scene(root, 700, 700));
        primaryStage.show();

    }


    public void main(String[] args) {
        //Trying to pass the computer name and selected folder as CLA (BUG)
        /*
        if (args.length != 2) {
            System.err.println("Computer Name, Folder path");
            return;
        }

        String computerName = args[0];
        File FOLDER_PATH = new File(args[1]);
        */

        Main client = new Main();
    }
}
