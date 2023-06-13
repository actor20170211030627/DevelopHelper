package com.actor.develop_helper.activity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Pair;
import android.view.View;

import com.actor.develop_helper.R;
import com.actor.develop_helper.databinding.ActivityEncryptBinding;
import com.actor.myandroidframework.widget.ItemSpinnerLayout;
import com.actor.myandroidframework.widget.ItemTextInputLayout;
import com.actor.myandroidframework.widget.webview.BaseWebView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * description: 加密解密
 * https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/test/java/com/blankj/utilcode/util/EncryptUtilsTest.java
 *
 * date       : 2020/9/12 o 14:43
 * @version 1.0
 */
public class EncryptActivity extends BaseActivity<ActivityEncryptBinding> {

    //单向加密
    private ItemSpinnerLayout<String>   islEncodeType1;//加密类型
    private ItemTextInputLayout itilEncode1;//要加密的字符串
    private ItemTextInputLayout itilEncodeResult1;//加密结果
    //双向加密
    private ItemSpinnerLayout<String>   islEncodeType2;//加密类型
    private ItemTextInputLayout itilEncode2;//要加密的字符串
    private ItemTextInputLayout itilEncodeResult2;//加密结果
    private ItemTextInputLayout itilDecodeResult;//解密结果
    private BaseWebView webView;

    //密码加盐值
    private static final String PWD_SALT = "MUIE*/82-DAlc3b1`";
    //对称加密的Key, 是16进制String
//    private static final String KEY_SYMMETRICAL_ENCRYPTION = "18a15d151f1512b21e1c1c9ef";
    //如果上方是16进制字符串, 可用这个方法
//    private static final byte[] BYTES_SYMMETRICAL_ENCRYPTION = ConvertUtils.hexString2Bytes(KEY_SYMMETRICAL_ENCRYPTION);
    //对称加密的Key, 非16进制String
    private static final String KEY_SYMMETRICAL_ENCRYPTION = "M2929p1_3*122;kmz12!(lfp";
    private static final byte[] BYTES_SYMMETRICAL_ENCRYPTION = ConvertUtils.string2Bytes(KEY_SYMMETRICAL_ENCRYPTION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("加密解密");

        islEncodeType1 = viewBinding.islEncodeType1;
        itilEncode1 = viewBinding.itilEncode1;
        itilEncodeResult1 = viewBinding.itilEncodeResult1;
        islEncodeType2 = viewBinding.islEncodeType2;
        itilEncode2 = viewBinding.itilEncode2;
        itilEncodeResult2 = viewBinding.itilEncodeResult2;
        itilDecodeResult = viewBinding.itilDecodeResult;
        webView = viewBinding.webView;

        itilEncode1.setText("这是密码123456");
        itilEncode2.setText("这是密码123456");
        //设置后字体很小
//        BaseWebSettings.defaultInit(webView.getSettings());
        webView.loadUrl("file:///android_asset/encode_instruction.html");
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_encode1://单向加密
                int position1 = islEncodeType1.getSelectedItemPosition();
                switch (position1) {
                    case 0://MD2
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptMD2ToString(getText(itilEncode1));
                            itilEncodeResult1.setText(result);
                            if (isEmpty(result)) {
                                showToast("本机未找到MD2加密方式!");
                            }
                        }
                        break;
                    case 1://MD5(可加密文件)
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptMD5ToString(getText(itilEncode1));
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    case 2://SHA1
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptSHA1ToString(getText(itilEncode1));
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    case 3://SHA224
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptSHA224ToString(getText(itilEncode1));
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    case 4://SHA256
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptSHA256ToString(getText(itilEncode1));
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    case 5://SHA384
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptSHA384ToString(getText(itilEncode1));
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    case 6://SHA512
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptSHA512ToString(getText(itilEncode1));
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    case 7://HmacMD5
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptHmacMD5ToString(getText(itilEncode1), KEY_SYMMETRICAL_ENCRYPTION);
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    case 8://HmacSHA1
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptHmacSHA1ToString(getText(itilEncode1), KEY_SYMMETRICAL_ENCRYPTION);
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    case 9://HmacSHA224
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptHmacSHA224ToString(getText(itilEncode1), KEY_SYMMETRICAL_ENCRYPTION);
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    case 10://HmacSHA256
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptHmacSHA256ToString(getText(itilEncode1), KEY_SYMMETRICAL_ENCRYPTION);
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    case 11://HmacSHA384
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptHmacSHA384ToString(getText(itilEncode1), KEY_SYMMETRICAL_ENCRYPTION);
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    case 12://HmacSHA512
                        if (isNoEmpty(itilEncode1)) {
                            String result = EncryptUtils.encryptHmacSHA512ToString(getText(itilEncode1), KEY_SYMMETRICAL_ENCRYPTION);
                            itilEncodeResult1.setText(result);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case R.id.btn_encode2://双向加密
                itilEncodeResult2.setText("");
                int position2 = islEncodeType2.getSelectedItemPosition();
                switch (position2) {
                    case 0://DES(双向对称)
                        encodeDES();
                        break;
                    case 1://3DES(三重DES)
                        encode3DES();
                        break;
                    case 2://AES
                        encodeAES();
                        break;
                    case 3://RC4
                        encodeRC4();
                        break;
                    case 4://IDEA
                        encodeIDEA();
                        break;
                    case 5://RSA(双向非对称)
                        encodeRSA();
                        break;
                    case 6://ECC(非对称加密,椭圆曲线加密算法)
                        showToast("暂未实现!");
                        break;
                    default:
                        break;
                }
                break;
            case R.id.btn_decode://解密
                itilDecodeResult.setText("");
                int position22 = islEncodeType2.getSelectedItemPosition();
                switch (position22) {
                    case 0://DES(双向对称)
                        decodeDES();
                        break;
                    case 1://3DES(三重DES)
                        decode3DES();
                        break;
                    case 2://AES
                        decodeAES();
                        break;
                    case 3://RC4
                        decodeRC4();
                        break;
                    case 4://IDEA
                        decodeIDEA();
                        break;
                    case 5://RSA(双向非对称)
                        decodeRSA();
                        break;
                    case 6://ECC(非对称加密,椭圆曲线加密算法)
                        showToast("暂未实现!");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    //DES(双向对称加密)
    private void encodeDES() {
        if (isNoEmpty(itilEncode2)) {
            String input = PWD_SALT + getText(itilEncode2);//盐 + 输入字符串
            byte[] bytes = ConvertUtils.string2Bytes(input);
            /**
             * 参1: 16进制字符串bytes
             * 参2: 16进制key
             * 参3: 转换的名称，例如DES/CBC/PKCS5Padding  /NoPadding(不自动填充, 有可能报错)
             * 参4: 该缓冲液与IV.的内容, 复制缓冲区以防止后续修改。
             * 结果:16进制String
             */
            String result = EncryptUtils.encryptDES2HexString(bytes, BYTES_SYMMETRICAL_ENCRYPTION, "DES/ECB/PKCS5Padding", null);
            itilEncodeResult2.setText(result);
        }
    }
    //DES(双向对称解密)
    private void decodeDES() {
        if (isNoEmpty(itilEncodeResult2)) {
            /**
             * 参1: 16进制String
             * 参2: 16进制key
             * 结果:bytes
             */
            byte[] bytes = EncryptUtils.decryptHexStringDES(getText(itilEncodeResult2), BYTES_SYMMETRICAL_ENCRYPTION, "DES/ECB/PKCS5Padding", null);
            String result = ConvertUtils.bytes2String(bytes);
            itilDecodeResult.setText(result);
        }
    }



    //3DES(三重DES, 双向对称加密)
    private void encode3DES() {
        if (isNoEmpty(itilEncode2)) {
            String input = PWD_SALT + getText(itilEncode2);//盐 + 输入字符串
            byte[] bytes = ConvertUtils.string2Bytes(input);
            /**
             * 参1: 字符串bytes
             * 参2: key, key size must be 128 or 192 bits
             * 参3: 转换的名称
             * 参4: 该缓冲液与IV.的内容, 复制缓冲区以防止后续修改。
             * 结果:16进制String
             */
            String result = EncryptUtils.encrypt3DES2HexString(bytes, BYTES_SYMMETRICAL_ENCRYPTION, "DESede/ECB/PKCS5Padding", null);
            itilEncodeResult2.setText(result);
        }
    }
    //3DES(三重DES, 双向对称解密)
    private void decode3DES() {
        if (isNoEmpty(itilEncodeResult2)) {
            /**
             * 参1: 16进制String
             * 参2: key
             * 结果:bytes
             */
            byte[] bytes = EncryptUtils.decryptHexString3DES(getText(itilEncodeResult2), BYTES_SYMMETRICAL_ENCRYPTION, "DESede/ECB/PKCS5Padding", null);
            String result = ConvertUtils.bytes2String(bytes);
            itilDecodeResult.setText(result);
        }
    }



    //AES(双向对称加密)
    private void encodeAES() {
        if (isNoEmpty(itilEncode2)) {
            String input = PWD_SALT + getText(itilEncode2);//盐 + 输入字符串
            byte[] bytes = ConvertUtils.string2Bytes(input);
            /**
             * 参1: 16进制字符串bytes
             * 参2: 16进制key, 长度24, 32可以
             * 参3: 转换的名称，例如DES/CBC/PKCS5Padding  /NoPadding(不自动填充, 有可能报错)
             * 参4: 该缓冲液与IV.的内容, 复制缓冲区以防止后续修改。
             * 结果:16进制String
             */
            String result = EncryptUtils.encryptAES2HexString(bytes, BYTES_SYMMETRICAL_ENCRYPTION, "AES/ECB/PKCS5Padding", null);
            itilEncodeResult2.setText(result);
        }
    }
    //AES(双向对称解密)
    private void decodeAES() {
        if (isNoEmpty(itilEncodeResult2)) {
            /**
             * 参1: 16进制String
             * 参2: 16进制key
             * 结果:bytes
             */
            byte[] bytes = EncryptUtils.decryptHexStringAES(getText(itilEncodeResult2), BYTES_SYMMETRICAL_ENCRYPTION, "AES/ECB/PKCS5Padding", null);
            String result = ConvertUtils.bytes2String(bytes);
            itilDecodeResult.setText(result);
        }
    }



    //RC4(双向对称加密)
    private void encodeRC4() {
        if (isNoEmpty(itilEncode2)) {
            String input = PWD_SALT + getText(itilEncode2);//盐 + 输入字符串
            byte[] bytes = ConvertUtils.string2Bytes(input);
            byte[] bytesResult = EncryptUtils.rc4(bytes, BYTES_SYMMETRICAL_ENCRYPTION);
            //这儿要转换成16进制String, 否则result是乱码. 不能解密
//            String result = ConvertUtils.bytes2String(bytesResult);
            String result = ConvertUtils.bytes2HexString(bytesResult);
            itilEncodeResult2.setText(result);
        }
    }
    //RC4(双向对称解密)
    private void decodeRC4() {
        if (isNoEmpty(itilEncodeResult2)) {
            //将16进制String转butes
//            byte[] bytes = ConvertUtils.string2Bytes(getText(itilEncodeResult2));
            byte[] bytes = ConvertUtils.hexString2Bytes(getText(itilEncodeResult2));
            /**
             * 参1: 16进制String
             * 参2: 16进制key
             * 结果:bytes
             */
            byte[] bytesResult = EncryptUtils.rc4(bytes, BYTES_SYMMETRICAL_ENCRYPTION);
            String result = ConvertUtils.bytes2String(bytesResult);
            itilDecodeResult.setText(result);
        }
    }



    //IDEA(双向对称加密)
    private Cipher cipher;
    private Key key;
    private void encodeIDEA() {
        if (isNoEmpty(itilEncode2)) {
            String input = PWD_SALT + getText(itilEncode2);//盐 + 输入字符串
            byte[] bytes = ConvertUtils.string2Bytes(input);

            KeyGenerator keyGenerator = null;

            try {
                //生成key
                keyGenerator = KeyGenerator.getInstance("IDEA");
                cipher = Cipher.getInstance("IDEA/ECB/ISO10126Padding");
            } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                e.printStackTrace();
                showToast(e.getMessage());
            }
            if (keyGenerator != null && cipher != null) {
//                int i = Security.addProvider(new BouncyCastleProvider());
                keyGenerator.init(128);
                SecretKey secretKey = keyGenerator.generateKey();
                byte[] keyBytes = secretKey.getEncoded();
                //转换密钥
                key = new SecretKeySpec(keyBytes, "IDEA");
                try {
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
                }
                try {
                    //加密
                    byte[] bytesResult = cipher.doFinal(bytes);
                    String result = ConvertUtils.bytes2String(bytesResult);
                    itilEncodeResult2.setText(result);
                } catch (BadPaddingException | IllegalBlockSizeException e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
                }
            }

        }
    }
    //IDEA(双向对称解密)
    private void decodeIDEA() {
        if (isNoEmpty(itilEncodeResult2) && cipher != null && key != null) {
            byte[] bytes = ConvertUtils.string2Bytes(getText(itilEncodeResult2));
            //解密
            try {
                cipher.init(Cipher.DECRYPT_MODE, key);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
                showToast(e.getMessage());
            }
            try {
                byte[] bytesResult = cipher.doFinal(bytes);
                String result = ConvertUtils.bytes2String(bytesResult);
                itilDecodeResult.setText(result);
            } catch (BadPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
                showToast(e.getMessage());
            }

        }
    }



    //RSA(双向非对称加密), 最好是公钥加密/私钥解密, 因为公钥很多个, 私钥只有1个
    private void encodeRSA() {
        if (isNoEmpty(itilEncode2)) {
            String input = PWD_SALT + getText(itilEncode2);//盐 + 输入字符串
            byte[] bytes = ConvertUtils.string2Bytes(input);

            int keySize = 1024;
            Pair<String, String> publicPrivateKey = genKeyPair(keySize);//公钥/私钥

            String publicKey = publicPrivateKey.first;
            String privateKey = publicPrivateKey.second;
            byte[] publicKeyByte = Base64.decode(publicKey.getBytes(), Base64.NO_WRAP);
            /**
             * 参1: 16进制字符串bytes
             * 参2: 公钥
             * 参3: 转换的名称，例如DES/CBC/PKCS5Padding  /NoPadding(不自动填充, 有可能报错)
             * 参4: 该缓冲液与IV.的内容, 复制缓冲区以防止后续修改。
             * 结果:16进制String
             */
            String result = EncryptUtils.encryptRSA2HexString(bytes, publicKeyByte, keySize, "RSA/None/PKCS1Padding");
            itilEncodeResult2.setText(result);
        }
    }
    //RSA(双向非对称解密)
    private void decodeRSA() {
        if (isNoEmpty(itilEncodeResult2)) {
            int keySize = 1024;
            Pair<String, String> publicPrivateKey = genKeyPair(keySize);//公钥/私钥

            String publicKey = publicPrivateKey.first;
            String privateKey = publicPrivateKey.second;
            byte[] privateKeyByte = Base64.decode(privateKey.getBytes(), Base64.NO_WRAP);
            /**
             * 参1: 16进制String
             * 参2: 16进制key
             * 结果:bytes
             */
            byte[] bytes = EncryptUtils.decryptHexStringRSA(getText(itilEncodeResult2), privateKeyByte, keySize, "RSA/None/PKCS1Padding");
            String result = ConvertUtils.bytes2String(bytes);
            itilDecodeResult.setText(result);
        }
    }



    private Pair<String, String> genKeyPair(int size) {
        if (size == 1024) {
            return Pair.create(
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCYHGvdORdwsK5i+s9rKaMPL1O5eDK2XwNHRUWaxmGB/cxLxeinJrrqdAN+mME7XtGN9bklnOR3MUBQLVnWIn/IU0pnIJY9DpPTVc7x+1zFb8UUq1N0BBo/NpUG5olxuQULuAAHZOg28pnP/Pcb5XVEvpNKL0HaWjN8pu/Dzf8gZwIDAQAB",
                    "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJgca905F3CwrmL6z2spow8vU7l4MrZfA0dFRZrGYYH9zEvF6Kcmuup0A36YwTte0Y31uSWc5HcxQFAtWdYif8hTSmcglj0Ok9NVzvH7XMVvxRSrU3QEGj82lQbmiXG5BQu4AAdk6Dbymc/89xvldUS+k0ovQdpaM3ym78PN/yBnAgMBAAECgYAFdX+pgNMGiFC53KZ1AhmIAfrPPTEUunQzqpjE5Tm6oJEkZwXiedFbeK5nbLQCnXSH07nBT9AjNvFH71i6BqLvT1l3/ezPq9pmRPriHfWQQ3/J3ASf1O9F9CkYbq/s/qqkXEFcl8PdYQV0xU/kS4jZPP+60Lv3sPkLg2DpkhM+AQJBANTl+/v6sBqqQSS0Anl5nE15Ck3XGBcq0nvATHfFkJYtG9rrXz3ZoRATLxF1iJYwGSAtirhev9W7qFayjci0ztcCQQC25/kkFbeMEWT6/kyV8wcPIog1mKy8RVB9+2l6C8AzbWBPZYtLlB7uaGSJeZBTEGfvRYzpFm5xO0JqwCfDddjxAkBmxtgM3wqg9MwaAeSn6/Nu2x4EUfBJTtzp7P19XJzeQsyNtM73ttYwQnKYhRr5FiMrC5FKTENj1QIBSJV17QNlAkAL5cUAAuWgl9UQuo/yxQ81fdKMYfUCfiPBPiRbSv5imf/Eyl8oOGdWrLW1d5HaxVttZgHHe60NcoRce0la3oSRAkAe8OqLsm9ryXNvBtZxSG+1JUvePVxpRSlJdZIAUKxN6XQE0S9aEe/IkNDBgVeiUEtop76R2NkkGtGTwzbzl0gm"
            );
        } else if (size == 2048) {
            return Pair.create(
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjLLeJZIO7dfQKb6tHE+TlhvD1m3UdTefKvl4uNQboDXy2ztgPcksjLDXxsT+znxMBh4RpXxfVPgnrcSLewGVhTb3uXh9sWo6tvvshNaMKBTebaZePhE7grq+LHH3NILscVssK24rDSvIquZ4nUbDipF/Iscge4LwnypcCuun/3RCn4HYzXW+0YFFZC8Vq4zabIxtzzkvgZlAlvuD6tT76Uuo5kD8b36yYNALI+ZStOj283wlL8PgyyitRGaqCH+MjWYqDb5C0DN31kcoSU7ARTGWgNNAoexAdNujkBvVRFyR2cH9FpjJDu18Oa8v9uSjlRftVWPj0OQXE7vRUsrrawIDAQAB",
                    "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMst4lkg7t19Apvq0cT5OWG8PWbdR1N58q+Xi41BugNfLbO2A9ySyMsNfGxP7OfEwGHhGlfF9U+CetxIt7AZWFNve5eH2xajq2++yE1owoFN5tpl4+ETuCur4scfc0guxxWywrbisNK8iq5nidRsOKkX8ixyB7gvCfKlwK66f/dEKfgdjNdb7RgUVkLxWrjNpsjG3POS+BmUCW+4Pq1PvpS6jmQPxvfrJg0Asj5lK06PbzfCUvw+DLKK1EZqoIf4yNZioNvkLQM3fWRyhJTsBFMZaA00Ch7EB026OQG9VEXJHZwf0WmMkO7Xw5ry/25KOVF+1VY+PQ5BcTu9FSyutrAgMBAAECggEAHJQ4i2kfnzA3GEOi5h1D3TnGjcfBYA3sRs5ltyVedyx+KAnngqVaZzmEmtto5ohY6OUysGqS8q91X9aMfm/T7zs7FnFjFqZ9Rq3lXRY3YezbQWqJuhHGBMfp2R1NGV1+qYfbcPbvx70dBZnK5id5kKv9JxNLhcsTFUGFcLJtbXXixY2CGiS/dIbFvFHGMbAz3+9l9HXaL4AS7KQXvnauwJW1a5vIAVFYZVBj0qY9Viy2vq6ShH+9pdxOSsWBt08WpxIhjkTr+ZkFck67la2Jn0SBlClB0FIygTqbAmsM3p1nqcR55jdx3hfs31rIfM1Rx5epMm48KYErb2ktowngAQKBgQDL9FEumMMagPy4+EjR1puFHNvADlAi8tIUNt1W5zKKnd+T6gYGn8nqiiy5pvwLLUp8JISmq50tMC3cgAPw+G4kIe5zoBO2EU9X6aPhMd/ScUlVdk0IzEMXa3kMAOjOInWvoevJ4cwWcBPH2aRuDg5wZdh3TpB9LQP4uQ0QHwmE3wKBgQCwmkL6rJDrNo1GNUsjw+WIsXkuS3PYJahbg/uhRdGSsX2BRIPQVCRJP7MkgaUMhZRilt1ROfQy4d2BPxTxvUiGJcKfpsW8xi39PrYWZC5TvEA839q39Uak+ISCsYtZaHk5dvzmE9nF5gv0ivjCr81N2/1KwXO8VmNofzWUqNd+9QKBgQCs39QICRgm2Ppd1qXyp1N/SuzBJ+CpHuUOmUqXpLRkZljiSVT+PGar1J8AZhfxaVxfSZzeoUxCxzm4UxIEKK9DFTfG7gKHKrj0LWfpM5siB0A/nlzBflHIAiLCF+s8/lx+mGMB5dBVnH5HwaTsXCHFB66pwgAa+hMJueDmr0gkRQKBgDKhd1Rwxvd4Y1ZejxVI43SmFOzt2t98JGFgXHLnFmdtFWNLJlNC3EhXx99Of+gwH9OIFxljeRxhXuTgFfwcXT+AceTdplExrBuvr/qJbDK7hNsu/oDBBCjlyu/BQQc4CZEtCOJZjJTNGF5avWjrh/urd1nITosPZV6fIdhl86pFAoGAfOwK0Wte6gO5glAHP9RNktDeyFJCfFH1KUFiAG7XUww6bRpL2fEAqBIcDVgsS565ihxDSbUjgQgg/Ckh2+iBrwf1K9ViO4XUuwWqRS26rn4Is/W5kbPtnC4HS5cQIH1aWi3xUMJcWxV4ZrwiMVdw91leYWC0IbXC/yrc/PBW+sE="
            );
        }

        SecureRandom secureRandom = new SecureRandom();

        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        keyPairGenerator.initialize(size, secureRandom);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        Key publicKey = keyPair.getPublic();

        Key privateKey = keyPair.getPrivate();

        byte[] publicKeyBytes = publicKey.getEncoded();
        byte[] privateKeyBytes = privateKey.getEncoded();

        String publicKeyBase64 = EncodeUtils.base64Encode2String(publicKeyBytes);
        String privateKeyBase64 = EncodeUtils.base64Encode2String(privateKeyBytes);

        return Pair.create(publicKeyBase64, privateKeyBase64);
    }
}