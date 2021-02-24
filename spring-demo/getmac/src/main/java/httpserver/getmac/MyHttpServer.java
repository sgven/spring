//package httpserver.getmac;
//
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//import com.sun.net.httpserver.HttpServer;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//
//public class MyHttpServer {
//    public static void main(String[] args) throws Exception {
//        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
//        byte[] respContents = MacHelper.getMacAddr().getBytes("UTF-8");
//        httpServer.createContext("/", new HttpHandler() {
//            @Override
//            public void handle(HttpExchange httpExchange) throws IOException {
//                // 响应内容
////                byte[] respContents = "Hello World".getBytes("UTF-8");
//
//                // 设置响应头
//                httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
//                // 设置响应code和内容长度
//                httpExchange.sendResponseHeaders(200, respContents.length);
//
//                // 设置响应内容
//                httpExchange.getResponseBody().write(respContents);
//
//                // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
//                httpExchange.close();
//            }
//        });
//        //启动服务
//        httpServer.start();
//    }
//
//}
