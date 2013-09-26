package com.enation.framework.util;

import org.apache.commons.codec.binary.Base64;

public class EncryptionUtil
{
  public static String GLOBAL_AUTH_KEY = "e317b362fafa0c96c20b8543d054b850";

  public static final String authCode(String str, String operation)
  {
    String key = GLOBAL_AUTH_KEY;
    String coded = "";
    int keylength = key.length();
    try {
      str = operation == "DECODE" ? new String(Base64.decodeBase64(str.getBytes())) : str;

      byte[] codeList = new byte[str.getBytes().length];
      int index = 0;
      for (int i = 0; i < str.length(); i += keylength) {
        int tmp = i + keylength < str.length() ? i + keylength : str.length();

        byte[] codedbyte = phpXor(str.substring(i, tmp), key);
        coded = coded + codedbyte;
        System.arraycopy(codedbyte, 0, codeList, index, codedbyte.length);

        index = codedbyte.length;
      }
      return operation == "ENCODE" ? new String(Base64.encodeBase64(codeList)) : new String(codeList);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public static final byte[] phpXor(String a, String b)
  {
    try
    {
      byte[] as = a.getBytes();
      byte[] bs = b.getBytes();
      int len = 0;
      if (as.length > bs.length)
        len = bs.length;
      else {
        len = as.length;
      }
      byte[] cs = new byte[len];
      for (int i = 0; i < len; i++) {
        cs[i] = (byte)(as[i] ^ bs[i]);
      }
      return cs;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}

/* Location:           D:\project_resource\eop.jar
 * Qualified Name:     com.enation.framework.util.EncryptionUtil
 * JD-Core Version:    0.6.1
 */