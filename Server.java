import java.net.*;
import java.io.*;
public class Server{
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server(){
        try{
            server=new ServerSocket(7777);
            System.out.println("Server is Ready to Connect:");
            System.out.println("Waiting..");
            socket=server.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void startReading(){
        //Thread will read Data concurrently.
        System.out.println("reader Started...");
        Runnable r1=()->{
            try{
                while(true){
                    String msg=br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Client Ended Chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("Client:"+msg);
                }
            }catch(Exception e){
               // e.printStackTrace();
               System.out.println("Connection is closed");
            }   
        };
        new Thread(r1).start();
    }
    

    public void startWriting(){
        //Thread will take input from user and send it to client cconcurrently.
        System.out.println("Writer Started");
        Runnable r2=()->{
            try {
                while(!socket.isClosed()){
                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
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
    

    public static void main(String[] args){
        System.out.println("Server program going to start.");
        new Server();
    }
}