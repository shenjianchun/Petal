// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.jc.petal.utils;


public class Base64
{

    private static final char map1[];
    private static final byte map2[];
    private static final String systemLineSeparator = System.getProperty("line.separator");

    private Base64()
    {
    }

    public static byte[] decode(String s)
    {
        return decode(s.toCharArray());
    }

    public static byte[] decode(char ac[])
    {
        return decode(ac, 0, ac.length);
    }

    public static byte[] decode(char ac[], int i, int j)
    {
        int k = j;
        if (j % 4 != 0)
        {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        for (; k > 0 && ac[(i + k) - 1] == '='; k--) { }
        int j1 = (k * 3) / 4;
        byte abyte0[] = new byte[j1];
        int k1 = i + k;
        j = 0;
        do
        {
            if (i >= k1)
            {
                break;
            }
            int l = i + 1;
            char c1 = ac[i];
            i = l + 1;
            char c2 = ac[l];
            int i1;
            if (i < k1)
            {
                l = ac[i];
                i++;
            } else
            {
                l = 65;
            }
            if (i < k1)
            {
                i1 = i + 1;
                char c = ac[i];
                i = i1;
                i1 = c;
            } else
            {
                i1 = 65;
            }
            if (c1 > '\177' || c2 > '\177' || l > '\177' || i1 > '\177')
            {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            c1 = (char) map2[c1];
            c2 = (char) map2[c2];
            byte byte0 = map2[l];
            i1 = map2[i1];
            if (c1 < 0 || c2 < 0 || byte0 < 0 || i1 < 0)
            {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            l = j + 1;
            abyte0[j] = (byte)(c1 << 2 | c2 >>> 4);
            if (l < j1)
            {
                j = l + 1;
                abyte0[l] = (byte)((c2 & 0xf) << 4 | byte0 >>> 2);
            } else
            {
                j = l;
            }
            if (j < j1)
            {
                l = j + 1;
                abyte0[j] = (byte)((byte0 & 3) << 6 | i1);
                j = l;
            }
        } while (true);
        return abyte0;
    }

    public static byte[] decodeLines(String s)
    {
        char ac[] = new char[s.length()];
        int j = 0;
        for (int i = 0; i < s.length();)
        {
            char c = s.charAt(i);
            int k = j;
            if (c != ' ')
            {
                k = j;
                if (c != '\r')
                {
                    k = j;
                    if (c != '\n')
                    {
                        k = j;
                        if (c != '\t')
                        {
                            ac[j] = c;
                            k = j + 1;
                        }
                    }
                }
            }
            i++;
            j = k;
        }

        return decode(ac, 0, j);
    }

    public static String decodeString(String s)
    {
        return new String(decode(s));
    }

    public static char[] encode(byte abyte0[])
    {
        return encode(abyte0, 0, abyte0.length);
    }

    public static char[] encode(byte abyte0[], int i)
    {
        return encode(abyte0, 0, i);
    }

    public static char[] encode(byte abyte0[], int i, int j)
    {
        int k1 = (j * 4 + 2) / 3;
        char ac[] = new char[((j + 2) / 3) * 4];
        int l1 = i + j;
        j = 0;
        while (i < l1) 
        {
            int k = i + 1;
            int i2 = abyte0[i] & 0xff;
            char c;
            int l;
            int j1;
            if (k < l1)
            {
                i = k + 1;
                k = abyte0[k] & 0xff;
            } else
            {
                l = 0;
                i = k;
                k = l;
            }
            if (i < l1)
            {
                l = i + 1;
                int i1 = abyte0[i] & 0xff;
                i = l;
                l = i1;
            } else
            {
                l = 0;
            }
            j1 = j + 1;
            ac[j] = map1[i2 >>> 2];
            j = j1 + 1;
            ac[j1] = map1[(i2 & 3) << 4 | k >>> 4];
            if (j < k1)
            {
                c = map1[(k & 0xf) << 2 | l >>> 6];
            } else
            {
                c = '=';
            }
            ac[j] = c;
            j++;
            if (j < k1)
            {
                c = map1[l & 0x3f];
            } else
            {
                c = '=';
            }
            ac[j] = c;
            j++;
        }
        return ac;
    }

    public static String encodeLines(byte abyte0[])
    {
        return encodeLines(abyte0, 0, abyte0.length, 76, systemLineSeparator);
    }

    public static String encodeLines(byte abyte0[], int i, int j, int k, String s)
    {
        int l = (k * 3) / 4;
        if (l <= 0)
        {
            throw new IllegalArgumentException();
        }
        k = ((j + l) - 1) / l;
        StringBuilder stringbuilder = new StringBuilder(((j + 2) / 3) * 4 + s.length() * k);
        int i1;
        for (k = 0; k < j; k += i1)
        {
            i1 = Math.min(j - k, l);
            stringbuilder.append(encode(abyte0, i + k, i1));
            stringbuilder.append(s);
        }

        return stringbuilder.toString();
    }

    public static String encodeString(String s)
    {
        return new String(encode(s.getBytes()));
    }

    static 
    {
        map1 = new char[64];
        char c = 'A';
        int i;
        for (i = 0; c <= 'Z'; i++)
        {
            map1[i] = c;
            c++;
        }

        for (char c1 = 'a'; c1 <= 'z';)
        {
            map1[i] = c1;
            c1++;
            i++;
        }

        for (char c2 = '0'; c2 <= '9';)
        {
            map1[i] = c2;
            c2++;
            i++;
        }

        char ac[] = map1;
        int l = i + 1;
        ac[i] = '+';
        map1[l] = '/';
        map2 = new byte[128];
        for (int j = 0; j < map2.length; j++)
        {
            map2[j] = -1;
        }

        for (int k = 0; k < 64; k++)
        {
            map2[map1[k]] = (byte)k;
        }

    }
}
