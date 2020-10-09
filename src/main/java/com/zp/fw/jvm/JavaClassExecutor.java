package com.zp.fw.jvm;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author zhoupeng
 * @Date 2020-05-11 10:01
 */
public class JavaClassExecutor {
    /**
     * 执行外部过来的代表一个Java类的byte数组
     * 将输入的byte数据中代表java.lang.System的CONSTRANT_Utf8_info常量修改为劫持后的HackSystem
     * 执行方法为该类的static main(String[] args)方法，输出结果为该类的System.out/err输出的信息
     *
     * @param classByte 代表一个Java类的byte数组
     * @return 执行结果
     */
    public static String execute(byte[] classByte) {
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "com/zp/fw/jvm/HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class<?> clazz = loader.loadByte(modiBytes);
        try {
            Method method = clazz.getMethod("main", new Class[]{String[].class});
            method.invoke(null, new String[]{null});
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return HackSystem.getBufferString();
    }

    /**
     * 执行指定路径下的class文件
     *
     * @param path 路径
     */
    public static void execute(String path) {
        try {
            FileInputStream is = new FileInputStream(path);
            byte[] b = new byte[is.available()];
            is.read(b);
            System.out.println(execute(b));
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
