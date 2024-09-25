import java.net.*;
import java.io.*;
public class Client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public Client(){
        try {
            socket=new Socket("127.0.0.1",7777);
            System.out.println("Connection Established");

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading(){
        System.out.println("Reader Started...");
        Runnable r1=()->{
            try{
                while(true){
                    String msg=br.readLine();
                        if(msg.equals("exit")){
                            System.out.println("Server terminated chat");
                            socket.close();
                            break;
                        }
                        System.out.println("Server:"+msg);
                }
            }catch(Exception e){
               // e.printStackTrace();
               System.out.println("Connection is closed");
            }
        };
        new Thread(r1).start();
    }

    public void startWriting(){
        System.out.println("Writer Started...");
        Runnable r2=()->{
            try {
                while(!socket.isClosed()){
                    BufferedReader br2=new BufferedReader(new InputStreamReader(System.in));
                    String content=br2.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Connection is closed");
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("Client side program running");
        new Client();
    }
}
