import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

//import com.zhidu.common.utility.Base64;

import javax.crypto.Cipher;

//import org.apache.commons.io.IOUtils;

/**
 * Created by liyu on 2018/3/16.
 */
public class MyTest {

//    public static final String CHARSET = "UTF-8";
//    public static final String RSA_ALGORITHM = "RSA";
//
//
//    /*
//    @Test
//    public void test() {
//        try {
//            JSONObject keyJson = this.httpGet("http://127.0.0.1:8090/game-user/public_key");
//            String publicKey = keyJson.getString("publicKey");
//            PublicKey keyObj = this.getPublicKey(publicKey);
//
//            String userName = "liyu02";
//            String pws = "123456";
////            String userName = "admin";
////            String pws = "zhidu@123";
//            JSONObject param = new JSONObject();
//            param.put("username",this.publicEncrypt(userName,(RSAPublicKey)keyObj));
//            param.put("password",this.publicEncrypt(pws,(RSAPublicKey)keyObj));
//            JSONObject postResut = this.sendPost("http://127.0.0.1:8090/game-user/login",param.toJSONString());
//            System.out.println(postResut);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//
//    } */
//
//    private JSONObject httpGet(String url) {
//        //get请求返回结果
//        JSONObject jsonResult = null;
//        try {
//            //发送get请求
//            HttpGet request = new HttpGet(url);
//            HttpResponse response = HttpClients.createDefault().execute(request);
//
//            /**请求发送成功，并得到响应**/
//            if (response.getStatusLine().getStatusCode() == 200) {
//                /**读取服务器返回过来的json字符串数据**/
//                String strResult = EntityUtils.toString(response.getEntity());
//                /**把json字符串转换成json对象**/
//                jsonResult = JSONObject.parseObject(strResult);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
////            logger.error("get请求提交失败:" + url, e);
//        }
//        return jsonResult;
//    }
//
//    public JSONObject sendPost(String url,String Params)throws IOException {
//        OutputStreamWriter out = null;
//        BufferedReader reader = null;
//        String response="";
//        try {
//            URL httpUrl = null; //HTTP URL类 用这个类来创建连接
//            //创建URL
//            httpUrl = new URL(url);
//            //建立连接
//            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("connection", "keep-alive");
//            conn.setUseCaches(false);//设置不要缓存
//            conn.setInstanceFollowRedirects(true);
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.connect();
//            //POST请求
//            out = new OutputStreamWriter(
//                    conn.getOutputStream());
//            out.write(Params);
//            out.flush();
//            //读取响应
//            reader = new BufferedReader(new InputStreamReader(
//                    conn.getInputStream()));
//            String lines;
//            while ((lines = reader.readLine()) != null) {
//                lines = new String(lines.getBytes(), "utf-8");
//                response+=lines;
//            }
//            reader.close();
//            // 断开连接
//            conn.disconnect();
//
//        } catch (Exception e) {
//            System.out.println("发送 POST 请求出现异常！"+e);
//            e.printStackTrace();
//        }
//        //使用finally块来关闭输出流、输入流
//        finally{
//            try{
//                if(out!=null){
//                    out.close();
//                }
//                if(reader!=null){
//                    reader.close();
//                }
//            }
//            catch(IOException ex){
//                ex.printStackTrace();
//            }
//        }
//
//        return JSONObject.parseObject(response);
//    }
//
//    private JSONObject httpPost(String url, String params) {
//        //get请求返回结果
//        JSONObject jsonResult = null;
//        try {
//            //发送Post请求
//            HttpPost request = new HttpPost(url);
//            if (null != params) {
//                HttpEntity httpEntity = new StringEntity(params, ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
//                request.setEntity(httpEntity);
//            }
//            HttpResponse response = HttpClients.createDefault().execute(request);
//
//            /**请求发送成功，并得到响应**/
//            if (response.getStatusLine().getStatusCode() == 200) {
//                /**读取服务器返回过来的json字符串数据**/
//                String strResult = EntityUtils.toString(response.getEntity());
//                /**把json字符串转换成json对象**/
//                jsonResult = JSONObject.parseObject(strResult);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return jsonResult;
//    }
//
////    private PublicKey getPublicKey(String key) throws Exception {
////        byte[] keyBytes;
//////        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
////        keyBytes = com.zhidu.common.utility.Base64.decode(key);
////
////        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
////        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
////        PublicKey publicKey = keyFactory.generatePublic(keySpec);
////        return publicKey;
////    }
//
//    /**
//     * 公钥加密
//     * @param data
//     * @param publicKey
//     * @return
//     */
//    private String publicEncrypt(String data, RSAPublicKey publicKey){
//        try{
//            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//            return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
//        }catch(Exception e){
//            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
//        }
//    }
//
//    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
//        int maxBlock = 0;
//        if(opmode == Cipher.DECRYPT_MODE){
//            maxBlock = keySize / 8;
//        }else{
//            maxBlock = keySize / 8 - 11;
//        }
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        int offSet = 0;
//        byte[] buff;
//        int i = 0;
//        try{
//            while(datas.length > offSet){
//                if(datas.length-offSet > maxBlock){
//                    buff = cipher.doFinal(datas, offSet, maxBlock);
//                }else{
//                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
//                }
//                out.write(buff, 0, buff.length);
//                i++;
//                offSet = i * maxBlock;
//            }
//        }catch(Exception e){
//            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
//        }
//        byte[] resultDatas = out.toByteArray();
////        IOUtils.closeQuietly(out);
//        return resultDatas;
//    }
}
