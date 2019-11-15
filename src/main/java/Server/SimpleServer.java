package Server;

import DTO.ResponseDto;
import Processor.Processor;
import Processor.ProcessorFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SimpleServer {

  public static void main(String[] args) throws IOException {
    ServerSocket ding;
    Socket dong = null;
    ResponseDto res = null;
    try {
      ding = new ServerSocket(1299);
      System.out.println("Opened socket " + 1299);
      while (true) {
        // keeps listening for new clients, one at a time
        try {
          dong = ding.accept(); // waits for client here
        } catch (IOException e) {
          System.out.println("Error opening socket");
          System.exit(1);
        }

        InputStream stream = dong.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        try {
          // read the first line to get the request method, URI and HTTP version
          String line = in.readLine();
          // getting second parameter which will be /posts, /users or unknown request from URI
          String request = line.split(" ")[1].split("[?]")[0];
          HashMap<String, String> myArgs = getQueryHashMap(line);
          System.out.println("----------REQUEST START---------");
          // getting ResponseDto based on request type
          Processor processorRes = ProcessorFactory.makeProcessor(request);
          res = processorRes.process(myArgs);
          System.out.println(line);
          // read only headers
          line = in.readLine();
          while (line != null && line.trim().length() > 0) {
            int index = line.indexOf(": ");
            if (index > 0) {
              System.out.println(line);
            } else {
              break;
            }
            line = in.readLine();
          }
          System.out.println("----------REQUEST END---------\n\n");
        } catch (IOException e) {
          System.out.println("Error reading");
          System.exit(1);
        }

        BufferedOutputStream out = new BufferedOutputStream(dong.getOutputStream());
        PrintWriter writer = new PrintWriter(out, true);  // char output to the client

        // every response will always have the status-line, date, and server name
        writer.println("HTTP/1.1 200 OK");
        writer.println("Server: TEST");
        writer.println("Connection: close");
        writer.println("Content-type: application/json");
        writer.println("");

        // Body of our response
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        writer.println(gson.toJson(res));

        dong.close();
      }
    } catch (IOException e) {
      System.out.println("Error opening socket");
      System.exit(1);
    }
  }

  public static HashMap<String, String> getQueryHashMap(String query)
  {
    String[] lineArr = query.split(" ");
    String uri = lineArr[1];
    lineArr = uri.split("[?]");
    HashMap<String, String> map = new HashMap<String, String>();

    if (lineArr.length == 1 && !(uri.contains("?"))) {
      return map;
    } else if (lineArr.length == 1 && uri.contains("?")) {
      map.put("?", null);
      return map;
  }

    String[] params = lineArr[1].split("&");
    for (String param : params)
    {
      if (param.split("=").length == 2) {
        String key = param.split("=")[0];
        String value = param.split("=")[1];
        map.put(key, value);
      } else {
        String key = param.split("=")[0];
        map.put(key, null);
      }
    }
    return map;
  }
}
