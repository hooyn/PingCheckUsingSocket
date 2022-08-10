package twim.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientB {
    public static void main(String[] args) {
        try{
            Socket socket = null;

            socket = new Socket("192.168.0.236", 3030);
            System.out.println("---Hello Client Ping Checking---");

            //받아온 소켓에서 InputStream을 받습니다.
            InputStream in = socket.getInputStream();
            //InputStream에서 BufferedReader를 통해 메세지를 읽습니다.
            BufferedReader msg = new BufferedReader(new InputStreamReader(in));

            while(true){
                //BufferedReader에 저장된 메세지를 출력합니다.
                System.out.println(msg.readLine());
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
