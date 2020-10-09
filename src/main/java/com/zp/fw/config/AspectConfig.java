package com.zp.fw.config;

import com.alibaba.fastjson.JSON;
import com.zp.fw.annotation.MyParameter;
import com.zp.fw.bean.ZpClient;
import com.zp.fw.properties.LogProperty;
import com.zp.fw.properties.RegistryProperty;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AspectConfig {
    @Autowired
    RegistryProperty registryProperty;
    @Autowired
    LogProperty logProperty;
    @Autowired
    ZpClient zpClient;

    // 2. PointCut表示这是一个切点，@annotation表示这个切点切到一个注解上，后面带该注解的全类名
    // 切面最主要的就是切点，所有的故事都围绕切点发生
    // logPointCut()代表切点名称
//    @Pointcut("@com.zp.fw.annotation(com.example.myfwtest.com.zp.fw.config.MyTest)")
//    @Pointcut("@annotation(com.zp.fw.annotation.MyParameter)")
    @Pointcut("execution(public * *..*.controller..*.*(..))")
    public void parameterPointCut() {
    }


    //@Around：环绕通知
    @Around("parameterPointCut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        /**
         * 通过配置文件判断
         */
        if (logProperty.isEnable()) {
            System.out.println(registryProperty.getIp() + "," + registryProperty.getName());
            zpClient.addClient(registryProperty.getIp(), registryProperty.getName());
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();

            Map<String, Object> data = new HashMap<>(16);
            //获取目标类名称
            String clazzName = pjp.getTarget().getClass().getName();
            //获取目标类方法名称
            String methodName = pjp.getSignature().getName();

            String remoteAddr = request.getRemoteAddr();
            int remotePort = request.getRemotePort();
            //记录访问ip和端口
            data.put("ip",remoteAddr + ":"+remotePort);

            //记录类名称
            data.put("clazzName", clazzName);
            //记录对应方法名称
            data.put("methodName", methodName);

            String method = request.getMethod();
            data.put("methodType", method);
            String queryString = request.getQueryString();
            String params = "";
            Object[] args = pjp.getArgs();
            //获取请求参数集合并进行遍历拼接
            if (args.length > 0) {
                if ("POST".equals(method)) {
                    Object object = args[0];
                    Map map = getKeyAndValue(object);
                    params = JSON.toJSONString(map);
                } else if ("GET".equals(method)) {
                    params = queryString;
                }
            }
            //记录请求参数
            data.put("params", params);

            //开始调用时间
            // 计时并调用目标函数
            long start = System.currentTimeMillis();
            Object result = pjp.proceed();
            Long time = System.currentTimeMillis() - start;

            /**
             * 通过注解判断
             */
//        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
//        Method method1 = methodSignature.getMethod();
//        if (method1.isAnnotationPresent(MyParameter.class)) {
//            MyParameter myParameter = method1.getDeclaredAnnotation(MyParameter.class);
//            if (myParameter.includeResultData()) {
//                //记录返回参数
//                data.put("result", result);
//            }
//        }

            /**
             * 通过配置文件判断
             */
            if (logProperty.isResultInclusive()) {
                //记录返回参数
                data.put("result", result);
            }


            //设置消耗总时间
            data.put("consumeTime", time + "ms");
            System.out.println(data);
            return result;
        } else{
            return pjp.proceed();
        }


    }

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        return setMap(map, obj, obj.getClass());
    }

    private static Map<String, Object> setMap(Map<String, Object> map, Object obj, Class objClass) {
        /* 得到类中的所有属性集合 */
        Field[] fs = objClass.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val = new Object();
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);// 设置键值

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        Class superClass = objClass.getSuperclass();
        // 父类不为Object类时递归
        if (superClass != null && !superClass.getName().contains("Object")) {
            setMap(map, obj, superClass);
        }
        return map;
    }
}