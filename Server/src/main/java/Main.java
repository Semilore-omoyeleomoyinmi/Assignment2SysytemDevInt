import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.net.URL;
import java.util.Vector;

public class Main extends Application {
    protected Socket clientSocket           = null;
    protected ServerSocket serverSocket     = null;
    protected FileServerThread[] threads    = null;
    protected int numClients                = 0;
    protected Vector messages               = new Vector();
    public static int SERVER_PORT = 2020;

    public Main(){
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("---------------------------");
            System.out.println("File Sharing Application is running");
            System.out.println("---------------------------");
            System.out.println("Listening to port: "+SERVER_PORT);

            //Need to make this array below dynamic since there isn't a fixed number of clients connecting (BUG)
            threads = new FileServerThread[numClients];
            while(true) {
                clientSocket = serverSocket.accept();
                System.out.println("Client #"+(numClients+1)+" is connected.");
                threads[numClients] = new FileServerThread(clientSocket, messages);
                threads[numClients].start();
                numClients++;
            }
        } catch (IOException e) {
            System.err.println("IOException while creating server connection");
        }
    }

    //UI not really needed for the server

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Image icon = new Image("https://image.flaticon.com/icons/png/128/2450/2450847.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("File Sharer v1.0");
        primaryStage.setScene(new Scene(root, 700, 700));
        primaryStage.show();
        */
    }




    public static void main(String[] args) {
        Main app = new Main();
    }
}
