package com.example.chat;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server extends Application  {
    private Button send; TextArea ta;
    private TextField tf;
    private DataInputStream di;
    private DataOutputStream do1;
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage stage)throws Exception{
        send = new Button("Send");send.setStyle("-fx-background-color:green;");
        ta=new TextArea();
        tf = new TextField();
        HBox h1 = new HBox(20,tf,send);
        h1.setAlignment(Pos.CENTER);

        VBox v1 = new VBox(20,ta,h1);
        Scene scene = new Scene(v1);
        stage.setScene(scene);
        stage.show();connecttoserver();
        stage.setTitle("Shiv's Personal Chat box");
        send.setOnAction(e->{

            String msg = tf.getText();
            if(!msg.isEmpty()){
                ta.appendText("Shiv:"+msg+"\n");
                try {
                    do1.writeUTF(msg);
                    do1.flush();
                }catch (Exception qe){}

                tf.clear();}
        });



    }
    public void  connecttoserver()throws Exception{
        Socket sc = new Socket();
        ServerSocket socket= new ServerSocket(500);
        sc=socket.accept();
        di= new DataInputStream(sc.getInputStream());
        do1 = new DataOutputStream(sc.getOutputStream());
        Thread t1 = new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        String msg = di.readUTF();
                        Platform.runLater(()->ta.appendText("Logidth:" + msg+"\n"));

                    } catch (Exception e){}
                }
            }
        };
        t1.setDaemon(true);
        t1.start();


    }
}
