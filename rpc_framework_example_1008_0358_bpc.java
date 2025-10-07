// 代码生成时间: 2025-10-08 03:58:31
package com.example.rpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * RPC 框架配置类
 */
@Configuration
public class RpcFrameworkConfig {

    private static final Map<String, Object> serviceMap = new HashMap<>();
    private static final Map<String, Class<?>> serviceClassMap = new HashMap<>();

    @Bean
    public RpcServiceExporter rpcServiceExporter() {
        return new RpcServiceExporter(serviceMap, serviceClassMap);
    }

    @Bean
    public RpcClientProxy rpcClientProxy() {
        return new RpcClientProxy(serviceMap, serviceClassMap);
    }
}

/**
 * 服务导出器
 */
class RpcServiceExporter {
    private final Map<String, Object> serviceMap;
    private final Map<String, Class<?>> serviceClassMap;

    public RpcServiceExporter(Map<String, Object> serviceMap, Map<String, Class<?>> serviceClassMap) {
        this.serviceMap = serviceMap;
        this.serviceClassMap = serviceClassMap;
    }

    public <T> void exportService(T service, Class<T> serviceClass) {
        String serviceName = serviceClass.getName();
        serviceMap.put(serviceName, service);
        serviceClassMap.put(serviceName, serviceClass);
    }
}

/**
 * RPC 客户端代理
 */
class RpcClientProxy {
    private final Map<String, Object> serviceMap;
    private final Map<String, Class<?>> serviceClassMap;

    public RpcClientProxy(Map<String, Object> serviceMap, Map<String, Class<?>> serviceClassMap) {
        this.serviceMap = serviceMap;
        this.serviceClassMap = serviceClassMap;
    }

    public <T> T getProxy(Class<T> serviceClass) {
        Object serviceBean = serviceMap.get(serviceClass.getName());
        if (serviceBean == null) {
            throw new RuntimeException("Service not found");
        }
        return serviceClass.cast(Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass},
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 远程调用逻辑
                        return "invoke method: " + method.getName();
                    }
                }));
    }
}

/**
 * 服务接口
 */
interface HelloService {
    String hello(String name);
}

/**
 * 服务实现
 */
class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "Hello " + name;
    }
}

/**
 * RPC 服务器
 */
class RpcServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 1234;

    public void start() throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server started on port: " + PORT);
    }

    public void stop() throws Exception {
        if (selector != null) {
            selector.close();
        }
        if (serverSocketChannel != null) {
            serverSocketChannel.close();
        }
    }
}

/**
 * RPC 客户端
 */
class RpcClient {
    private SocketChannel socketChannel;
    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    public void connect() throws Exception {
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
    }

    public void close() throws Exception {
        if (socketChannel != null) {
            socketChannel.close();
        }
    }

    public String invoke(String methodName, String[] parameterTypes, Object[] args) throws Exception {
        // 调用远程方法
        ByteBuffer request = ByteBuffer.allocate(1024);
        // 构建请求数据
        request.put(methodName.getBytes());
        request.put("
".getBytes());
        for (int i = 0; i < parameterTypes.length; i++) {
            request.put(parameterTypes[i].getBytes());
            request.put("
".getBytes());
            request.put(args[i].toString().getBytes());
            request.put("
".getBytes());
        }
        request.flip();
        socketChannel.write(request);
        // 读取响应数据
        ByteBuffer response = ByteBuffer.allocate(1024);
        socketChannel.read(response);
        response.flip();
        byte[] bytes = new byte[response.remaining()];
        response.get(bytes);
        return new String(bytes);
    }
}