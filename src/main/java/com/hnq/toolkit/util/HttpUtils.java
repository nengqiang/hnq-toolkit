package com.hnq.toolkit.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hnq.toolkit.util.http.HttpService;
import com.hnq.toolkit.util.http.MoreHttpRequest;
import com.hnq.toolkit.util.http.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author henengqiang
 * @date 2018/10/15
 */
@Slf4j
public class HttpUtils {
    
    private static final String UNKNOWN = "unknown";

    public static String getProxyStr() {
        String result = null;
        try {
            String url = "http://wiseproxy.saas.treefinance.com.cn/wiseproxy/service/getProxy?site=123";
            String resultStr = HttpService.get(url);
            log.debug("proxyStr=" + resultStr);
            JSONObject obj = JSONObject.parseObject(resultStr);
            result = obj.getString("proxy");
        } catch (Exception e) {
            log.error("error to get proxy.", e);
        }
        return result;
    }

    /**
     * 发送请求重试机制
     *
     * @param request 请求
     * @param clazz   返回对象类型
     * @param retries 重试次数
     * @return 响应数据
     */
    public static <T> T sendRequest(MoreHttpRequest request, Class<T> clazz, int retries) {
        int count = Math.max(0, retries) + 1;
        for (int i = 0; i < count; i++) {
            try {
                return HttpService.send(request, clazz);
            } catch (HttpException e) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    log.warn("请求重试等待异常：", ex);
                }
                log.warn("第[{}]次请求出现异常, url={}, exception >> {}", (i + 1), request.getUrl(), e.getMessage());
            }
        }
        throw new IllegalStateException("重试3次请求失败！");
    }

    /**
     * 获取 HttpClient 对象
     * 默认 CookieStore，API自动创建
     */
    public static CloseableHttpClient getHttpClient() {
        return getHttpClient(null);
    }

    /**
     * 获取 HttpClient 对象
     * 指定 CookieStore，后面会对这个CookieStore操作的情况
     */
    public static CloseableHttpClient getHttpClient(CookieStore cookieStore) {
        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(50000)
                .setConnectTimeout(50000)
                .setSocketTimeout(50000).build();
        HttpClientBuilder builder = HttpClients.custom()
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(100)
                .setDefaultRequestConfig(requestConfig);
        if (cookieStore != null) {
            builder.setDefaultCookieStore(cookieStore);
        }
        return builder.build();
    }

    // -------------------------------------------------------------------------------- //
    // 某些代码可以先不急着删，可以做个代码参考
    // -------------------------------------------------------------------------------- //

    /**
     * 执行不带参数的GET请求，并返回响应String
     */
    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, null);
    }

    public static String doGet(String url, CookieStore cookieStore) {
        return doGet(url, null, cookieStore);
    }

    /**
     * 执行带参数的GET请求
     * @param url       请求路径
     * @param params    GET请求参数
     * @return          返回响应String数据
     */
    public static String doGet(String url, Map<String, String> params, CookieStore cookieStore)  {
        HttpGet httpGet = null;
        try {
            CloseableHttpClient httpClient = getHttpClient(cookieStore);
            URIBuilder builder = new URIBuilder(url);
            if (MapUtils.isNotEmpty(params)) {
                params.forEach(builder::setParameter);
            }
            URI uri = builder.build();
            httpGet = new HttpGet(uri);
            HttpResponse response = httpClient.execute(httpGet);
            return getResponseString(response);
        } catch (Exception e) {
            log.error("execute doGet ERROR:", e);
        } finally {
            if (httpGet != null) {
                // 关闭连接,释放资源
                httpGet.releaseConnection();
            }
        }
        return "";
    }

    /**
     * 执行不带参数的POST请求，并返回响应String
     */
    public static String doPost(String url) {
        return doPost(url, null, null);
    }

    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, null);
    }

    public static String doPost(String url, CookieStore cookieStore) {
        return doPost(url, null, cookieStore);
    }

    /**
     * 执行带参数的POST请求，并设置Header来伪装浏览器请求
     * @param url       请求路径
     * @param params    POST请求参数
     * @return          返回响应String数据
     */
    public static String doPost(String url, Map<String, String> params, CookieStore cookieStore) {
        try {
            CloseableHttpClient httpClient = getHttpClient(cookieStore);
            RequestBuilder requestBuilder = RequestBuilder.post(url);
            List<NameValuePair> paramList = Lists.newArrayList();
            if (MapUtils.isNotEmpty(params)) {
                params.forEach((k, v) -> paramList.add(new BasicNameValuePair(k, v)));
            }
            // 使用URL实体转换工具
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8);
            HttpUriRequest uriRequest = requestBuilder
                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                    .setEntity(entity)
                    .build();
            HttpResponse response = httpClient.execute(uriRequest);
            return getResponseString(response);
        } catch (Exception e) {
            log.error("execute doPost ERROR:", e);
        }
        return "";
    }

    /**
     * 构造消息头(post)
     */
    @Deprecated
    public static void constructHeader(HttpPost post, String sessionId) {
        post.setHeader("Content-type", "application/json; charset=utf-8");
        post.setHeader("Connection", "Close");
        post.setHeader("SessionId", sessionId);
    }

    /**
     * 构造消息头(get)
     */
    @Deprecated
    public static void constructHeader(HttpGet get, String sessionId) {
        get.setHeader("Content-type", "application/json; charset=utf-8");
        get.setHeader("Connection", "Close");
        get.setHeader("SessionId", sessionId);
    }

    /**
     * 为POST请求添加参数
     */
    public static void constructEntity(HttpPost post, Map<String, String> params) {
        List<NameValuePair> paramList = Lists.newArrayList();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            BasicNameValuePair param = new BasicNameValuePair(entry.getKey(), entry.getValue());
            paramList.add(param);
        }
        // 使用URL实体转换工具
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8);
        post.setEntity(entity);
    }

    /**
     * 处理响应数据
     * also use {@link EntityUtils#toString(org.apache.http.HttpEntity, Charset)} is ok
     */
    public static String getResponseString(HttpResponse res) throws IOException {
        return getResponseString(res, "UTF-8");
    }

    /**
     * 处理响应数据
     */
    public static String getResponseString(HttpResponse res, String charset) throws IOException {
        return EntityUtils.toString(res.getEntity(), charset);
    }

    /**
     * 处理获取到的原始数据流
     */
    public static ByteArrayOutputStream getByteArrayOutputStream(HttpResponse res) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int bufSize = 1024;
        byte[] buffer = new byte[bufSize];
        int len;
        while (-1 != (len = res.getEntity().getContent().read(buffer, 0, bufSize))) {
            bos.write(buffer, 0, len);
        }
        return bos;
    }

    /**
     * 用UUID构建唯一会话Id
     */
    public static String getSessionId() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.substring(0, 8) 
                + str.substring(9, 13) 
                + str.substring(14, 18) 
                + str.substring(19, 23) 
                + str.substring(24);
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 
     *  可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * 
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
     * 
     * 用户真实IP为： 192.168.1.110
     * 
     * @param request {@link HttpServletRequest}
     * @return        same as 192.168.5.11
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
