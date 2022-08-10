package twim.socket.server;

import twim.socket.util.PrintDate;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
    //클라이언트를 식별하기 위한 no
    int no = -1;

    //모든 유저를 저장하기 위한 전역 리스트
    static ArrayList<Socket> users = new ArrayList<Socket>();

    //ServerSocket에서 생성하고 넘겨준 소켓을 저장
    static Socket socket = null;

    //시간 출력을 위한 객체 인스턴스 생성
    static PrintDate now = new PrintDate();

    public Server(Socket socket, int no) {
        //클라이언트 식별 번호 초기화
        this.no = no;

        //클라이언트 소켓 저장
        this.socket = socket;

        //리스트에 모든 유저 저장
        users.add(socket);
    }

    public void run(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("connect to server from client" + socket.getInetAddress());

            //서버에서 보낼 메세지 저장
            OutputStream out = socket.getOutputStream();
            PrintWriter server = new PrintWriter(out, true);

            server.println("server connection successful");

            String input = null;

            //클라이언트에서 입력이 있다면
            while (true) {
                //현재 서버와 연결되어 있는 사용자를 찾아서 ping 출력
                out = users.get(no).getOutputStream();
                server = new PrintWriter(out,true);

                //여기서 이제 필요한 데이터를 서버에 보낼 수 있습니다.
                server.println("ping check : [" + no + "] " + now.getDate());

                //2초 마다 출력하기
                sleep(2000);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try{
            //포트 설정
            int port = 3030;
            //서버 포트 설정
            ServerSocket serverSocket = new ServerSocket(port);

            int cnt = 0;

            while (true) {
                //서버 포트로는 IP주소와 Port값이 맞는지 확인 후 검증이 되면 소켓을 생성한다.
                //ServerSocket의 기능은 여기까지
                Socket socketUser = serverSocket.accept();

                //위에서 생성된 Socket으로 Thread를 만들어서 실행시킨다.
                Thread thread = new Server(socketUser, cnt++);
                thread.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
