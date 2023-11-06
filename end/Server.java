import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            // 创建ServerSocket对象，监听8000端口
            ServerSocket serverSocket = new ServerSocket(8000);

            System.out.println("Server is running and listening on port 8000...");

            // 无限循环，等待客户端连接
            while (true) {
                // 当有客户端连接时，返回一个新的Socket对象
                Socket clientSocket = serverSocket.accept();
                System.out.println("A client has connected!");

                // 创建输入流和输出流
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // 读取客户端发送的请求
                String request = in.readLine();
                System.out.println("Received request: " + request);

                // 处理请求
                if (request.startsWith("POST /api/askme")) {
                    // 解析请求体数据
                    String requestBody = "";
                    while (in.ready()) {
                        requestBody += (char) in.read();
                    }
                    System.out.println("Request body: " + requestBody);

                    // 响应客户端请求
                    String response = "Hello from server!";
                    String jsonResponse = "{\"message\": \"Hello from server!\", \"status\": 200}";
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/plain");

                    // 添加跨域头信息
                    out.println("Access-Control-Allow-Origin: *");
                    out.println("Access-Control-Allow-Methods: POST");
                    out.println("Access-Control-Allow-Headers: Content-Type");

                    out.println();
                    out.println(jsonResponse);
                    System.out.println("Sent response: " + response);
                } else {
                    // 如果请求路径不匹配，返回404 Not Found
                    out.println("HTTP/1.1 404 Not Found");
                    out.println();
                }

                // 关闭客户端连接
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}