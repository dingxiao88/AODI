package AODI_GPRS;

import java.nio.ByteBuffer;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Test
{
    public static void main(String[] args)
    {
        byte[] messageByte = new byte[1000];
        boolean end = false;
        String messageString = "";

        try 
        {
            Socket clientSocket;
            ServerSocket server;

            server = new ServerSocket(30501, 100);
            clientSocket = server.accept();

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            int bytesRead = 0;

            messageByte[0] = in.readByte();
            messageByte[1] = in.readByte();
            ByteBuffer byteBuffer = ByteBuffer.wrap(messageByte, 0, 2);

            int bytesToRead = byteBuffer.getShort();
            System.out.println("About to read " + bytesToRead + " octets");

            //The following code shows in detail how to read from a TCP socket

            while(!end)
            {
                bytesRead = in.read(messageByte);
                messageString += new String(messageByte, 0, bytesRead);
                if (messageString.length() == bytesToRead )
                {
                    end = true;
                }
            }

            //All the code in the loop can be replaced by these two lines
            //in.readFully(messageByte, 0, bytesToRead);
            //messageString = new String(messageByte, 0, bytesToRead);

            System.out.println("MESSAGE: " + messageString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

