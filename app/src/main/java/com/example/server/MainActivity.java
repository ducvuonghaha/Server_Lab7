package com.example.server;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {


    private TextView textview;
    private String line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = findViewById(R.id.textview);

//        AsyncTask asyncTask = new AsyncTask() {
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                // khoi tao 1 server voi port = 5555
//                ServerSocket serverSocket = null;
//
//                try {
//                    serverSocket = new ServerSocket(5554);
//                    Log.e("WAIT", "WAITING TO CONNECT....");
//                    // tao vong lap vo han de lang nghe su kien ket noi
//                    while (true) {
//                        Log.e("ANC", "New Client connect!");
//                        Socket socket = serverSocket.accept();
//                        try {
//                            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//                            while (true) {
//                                String line = is.readLine();
//
//                                os.write(">>" + line);
//                                os.newLine();
//                                os.flush();
//
//                                // in gia tri gui toi
//                                Log.e("line",line);
//                            }
//
//                        } catch (Exception e) {
//                            Log.e("ABC",e.toString());
//                        }
//                    }
//
//                } catch (IOException e) {
//                    Log.e("LOI", e.getMessage());
//                }
//
//                return null;
//            }
//        };
//
//        asyncTask.execute();

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                // khoi tao 1 server voi port = 2710
                ServerSocket serverSocket = null;
                try {
                    //tao sever
                    serverSocket = new ServerSocket(); // <-- create an unbound socket first
                    serverSocket.setReuseAddress(true);
                    serverSocket.bind(new InetSocketAddress(5555));
                    Log.e("STATUS_SERVER", "Waiting to connect....");
                    // tao vong lap vo han de lang nghe su kien ket noi
                    while (true) {
                        Socket socket = serverSocket.accept();
                        //gui va nhan du lieu
                        try {
                            Log.e("STATUS_SERVER", "New client connect!");
                            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                            line = is.readLine();
                                os.write(">>" + line);
                                os.newLine();
                                os.flush();
                                is.close();
                                os.close();
                                socket.close();

                                // in gia tri gui toi
                                Log.e("RESPONSE", line);
//                                textview.setText(line);

                        } catch (Exception e) {
//                            Log.e("ERROR IN GET RESPONSE", e.toString());
                        }
                    }

                } catch (Exception e) {
                    Log.e("STATUS_SERVER", "New client connect!");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Log.e("RESPONE", "From Simulator, connection time(milliseconds): " + System.currentTimeMillis());
                textview.setText("From Simulator, connection time(milliseconds): " + System.currentTimeMillis());
            }
        };

        asyncTask.execute();

    }
}