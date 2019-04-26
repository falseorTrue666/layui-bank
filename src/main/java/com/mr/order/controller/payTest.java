package com.mr.order.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.mr.bank.service.BankService;
import com.mr.order.mapper.OrderMapper;
import com.mr.order.pojo.Order;
import com.mr.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by DELL on 2019/4/11.
 */
@Controller
public class payTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BankService bankService;


    private final String APP_ID = "2016092700607248";
    private final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC3PlP4bjAvQhwTosHbUS2yFVn4MdBWWPGn0RTI1AkPpC+OrK4a27xIZsE/5XpWfPLqEROuRQSvzi6RO182N9/PlOD3z1PJhdqAsHuJcfxV8M7HvcEyNw7G/+szaBbhGX2vFyzjb4PiZj1bay667eC4cjCHnOcegLoY/B3nZ7/CpSwrf/IuWl5wL2XlKk2d561Li2rFSYtWQh0qOuoc8I1f0GRGG4JEnyD8U/nuQnLrNG7Fiahz1Xv41dJ7a6Z4sM76LQ8ueea8q9vNIBARlP2YndWH/je4ZiMR8Mv3GPtuUwx5kVLiCF0ivGxpu+c8ObxCLw0WI1yNwZJYWwujvxJzAgMBAAECggEALrMFt2Rthz2z5hUujnQrzxy95xDa5YaZX4lbYKsY/BF9xUCgxluFqnX1fhE/klcZmSls+JbCUQOcvoqBA1JOCZsJkl4WHl+shet0MF0KT+Sk7iaf2+pIK9MA4AMBAbD7ZOnlGLlMxSAFNw0jxaPmB1uino0QIafwxXlLLbQ1HKeJJYpBvk8fcnOD+NDkN1JjVbe3EQlTP0rYa88/k7qJYZge6dbtS3SDa8HFpFEsDoTMrHRdpuc1zxUrfw+tGg9qX+2oIAX5y66oSDxVfYV1lKzSNsnuH8qbJLnZwdvVdgMhjims3KBtulzWBXAnoGFrVdpJn1bkJrmyUhG7g4OMEQKBgQDc1Yq5+z3pfVrSkg9TUISAQlEY5HXomQnssqz6aTRMecu6jD7DLyIzs6CgUZlAmv+Za1HvXcAwnnaSCM/8v3p2GLAdoHLzM0+j95AQLu5MAbFbmdaqJVzmJ0GHwOXE6BlfaNNBlrKl4t2UFA8eiPblCB3K5WkXWgeTg5sNzwxs5wKBgQDUbGA3bLLao0QPr9qtqVfaqhbXCDZfA0PIA+3F9zSOsuyHm4wDrxprPcTk2HyuAtHsDG4SnC+GLO0lGZFkHCijQoqVkGLTwKepHxgsXjT6NY6oiCm/BnDmHKrvytJiXQQRKjKp3BFpOGmlIvgGlaHgKJmPDFUOJsrezNcUxbjQlQKBgGpaBQdwce6iyjyizvKm0kkapwe/7vDn0xnFKv0l8WPrPCxJImjJqEUR+wd6hL3SZ8oxHzKSCd7gUeYejFwCsy1w0lBSV0m8qlwi+6RvlZ/RKOMegX1LpSPKQArq4T0lMiL6ztLFip/NVDzeNDHWcGD4ubBfL29pkkB+nwvVbPx9AoGACO+qOIqtF3+IzHQkaevW008WeONSjjqv7kkkBpD4uAwU4TuM71a9cMd/cfUdgaCCvkBjnQrPAQEA1O+3axO4Le4NsG8LiUbwhq/VL4CjD+eS4cogn3Wg6D1Kf53zrcml8yboTLcCUH/cCeNntbczwR9UHm1xYpAFNsw9PBV5SeUCgYB14okt7XbiqfVRQ0/GG1prgZOHaAhRiSf25q4HEOpOZ+3oEIZSbya6IVf10rRFepDinpeBTpn969IbRzN1xoVimwoAd7A3FytkuLm+G2H1nq2IMUXvU3/y0soypmZbRmhCsCxLr1B8lZX3AogmUPLI+e6Pat1hepgYklnrp1iurw==";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwaAuN7K1xjvePTMX9zcGfmyjOx8/5Kn/jTXo7Zcgt4uzDAOleVMyuq9LC9HF4LGizavW0LjgTERGfttolt1bZij40Rz4UGmXWdjJrBnfyrFl/Mng/6QSmLz4U1U78eLu3P3Q1fEd73z+3tShQKdc6D0/em/rHLxGciiRmavFpDjnjy7cg/nCqfo/bLjNpZIml2F3mKQ8AK7q0mchJ8Kvbxwr8TTdzRVlVDCSZAj1cFNd6G3eQZcSLpIN8qCOW7HpxY/8kSf82qaiT9ku3t3f2/QwhzKT9wRtu7TGaMe5co4K+ksFwkND3toYwVzTrd2i5DdwDI1UhYcsOEh+eW6UiQIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://公网地址/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://127.0.0.1/returnUrl.do";

    //支付订单
    @RequestMapping("alipay")
    public void alipay(Integer orderId,HttpServletResponse httpResponse) throws IOException {
        Random ran = new Random();
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);
        //根据订单编号,查询订单相关信息
        Order order = orderService.selectById(orderId);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        //UUID.randomUUID().toString()
        String out_trade_no = order.getOrderId().toString();
        //付款金额，必填
        //Integer.toString(ran.nextInt(99999)+10000)
        String total_amount = order.getOrderMoney().toString();

        //订单名称，必填
        //order.getOrderName()
        String subject = "";
        if(order.getBankState() == 1){
            subject = "(储蓄卡)存款";
        }else if (order.getBankState() == 2){
            subject = "(信用卡)还款";
        }
        //商品描述，可空
        String body = "";
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    //支付完成后返回的页面
    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    @ResponseBody
    public String returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        System.out.println("=================================同步回调=====================================");

        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost",6379,15,"rrp");
        Jedis jedis = pool.getResource();

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); // 调用SDK验证签名
        //验证签名通过
        if(signVerified){
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            bankService.updatebyBankNo(out_trade_no,Double.parseDouble(total_amount));

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+total_amount);

            jedis.persist(out_trade_no.toString());
            //支付成功，修复支付状态
            orderService.updateById(out_trade_no);
            return "../ok";//跳转付款成功页面
        }else{
            return "../no";//跳转付款失败页面
        }

    }
}
