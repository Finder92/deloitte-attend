package io.renren.modules.attend.service;

/**
 * @ClassName HttpClientService
 * @Description TODO
 * @Author Finder
 * @Date 2019/7/29 14:21
 **/
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class HttpClientService {


    @Autowired(required = false)
    private CloseableHttpClient httpClient;


    @Autowired(required = false)
    private RequestConfig requestConfig;

    /**
     * 编辑httpget请求(请求路径是拼接而成)
     * 1.使用map<k(id),v(值)>,保存传递的参数
     * 2.指定uri
     * 3.设置字符集编码
     */

    public String doGet(String uri, Map<String, String> params, String charset) {
        String result = null;
        //1.判断字符集是否为空,如空,则设置字符集
        if (StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }
        //2.判断是否有参数,有,则拼接参数路径
        try {
            if (params != null) {
                //使用URIBuilder进行请求路径拼接
                URIBuilder builder = new URIBuilder(uri);
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());

                }
                //动态拼接(build()生成新的请求路径)
                uri = builder.build().toString();
            }
            System.out.println("动态拼接的路径---->" + uri);
            //3.定义请求对象
            HttpGet httpGet = new HttpGet(uri);
            //4.定义请求的链接时长
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpGet);

            //5.判断请求是否正确
            if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(
                        closeableHttpResponse.getEntity(),
                        charset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String doGet(String uri, Map<String, String> params) {
        return doGet(uri, params, null);
    }

    public String doGet(String uri) {
        return doGet(uri, null);
    }


    /**
     * @Author Finder
     * @Description //TODO post请求  封装参数的方法
     * @Date 12:52 2019/7/30
     * @Param 
     * @return 
     */
    public String doPost(String uri, Map<String, String> params, String charset) {
        String result = null;
        //判断字符集是否为空,为空则赋编码集
        if (StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }
        //创建请求对象
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("User-Agent", "Koala Admin");
        //定义超时时长
        httpPost.setConfig(requestConfig);
        try {
            //判断参数是否为空
            if (params != null) {
                List<NameValuePair> parameters = new ArrayList<>();
                //遍历map集合,给list赋值
                for (Map.Entry<String, String> enyry : params.entrySet()) {
                    //NameValuePair的实现类
                    BasicNameValuePair basicNameValuePair =
                            new BasicNameValuePair(enyry.getKey(), enyry.getValue());
                    //放入list集合
                    parameters.add(basicNameValuePair);
                }
                //封装from表单
                UrlEncodedFormEntity formEntity =
                        new UrlEncodedFormEntity(parameters, charset);
                //将请求的实体对象,封装到POST对象中
                httpPost.setEntity(formEntity);
            }
            //发送请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            //获取响应结果
                result=EntityUtils.toString(
                        httpResponse.getEntity(),charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String doPost(String uri) {
        return doPost(uri, null);
    }

    public String doPost(String uri, Map<String, String> params) {
        return doPost(uri, params, null);
    }

}
