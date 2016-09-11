package com.evan.xtool;

import com.evan.xtool.utils.ThreeDes;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        String szSrc = "11233AFFA";

        String iv = "evantanx";
        String key = "tanxuewen198811182016189";

        System.out.println("加密前的字符串:" + szSrc);

        byte[] encoded = ThreeDes.encryptMode(iv, key, szSrc);

        String encodestr = ThreeDes.encodeHex(encoded);
        System.out.println("加密后的字符串:" + encodestr);
        String encodestr1 = new String(encoded);
        System.out.println("加密后的字符串2:" + encodestr1);

        byte[] srcBytes = ThreeDes.decryptMode(iv, key, ThreeDes.decodeHex(encodestr));

        System.out.println("解密后的字符串:" + (new String(srcBytes)));
    }
}