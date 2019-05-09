package com.liyu.cms.data.web.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by liyu on 2018/3/15.
 * RSA加解密，用户登录注册
 */
public class RSAUtil {

    //KeyPair is a simple holder for a key pair.
    private static final KeyPair keyPair = initKey();
    /**
     * 初始化方法，产生key pair，提供provider和random
     * @return KeyPair instance
     */
    private static KeyPair initKey() {

        try {
            KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            return keyPairGenerator.generateKeyPair();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 产生public key
     * @return public key字符串
     */
    public static String generateBase64PublicKey() {
        PublicKey publicKey = (RSAPublicKey)keyPair.getPublic();

        //encodeBase64(): Encodes binary data using the base64
        //algorithm but does not chunk the output.
        //getEncoded():返回key的原始编码形式
        return new String(Base64.encodeBase64(publicKey.getEncoded()));
    }
    /**
     * 解密数据
     * @param string 需要解密的字符串
     * @return  破解之后的字符串
     */
    public static String decryptBase64(String string) {
        //decodeBase64():将Base64数据解码为"八位字节”数据
        return new String(decrypt(Base64.decodeBase64(string.getBytes())));
    }

    private static byte[] decrypt(byte[] byteArray) {
        try {

            Cipher cipher = Cipher.getInstance("RSA");
            PrivateKey privateKey = keyPair.getPrivate();
            //初始化
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //doFinal(): 加密或者解密数据
            byte[] plainText = cipher.doFinal(byteArray);
            return plainText;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
