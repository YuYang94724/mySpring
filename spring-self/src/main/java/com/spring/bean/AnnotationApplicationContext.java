package com.spring.bean;

import com.spring.anno.Di;
import com.spring.anno.MyBean;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationApplicationContext implements ApplicationContext{
    //創建map集合,放bean對象
    private Map<Class, Object> beanFactory = new HashMap<>();
    private static String rootPath = "/Users/houyuyang/Documents/testCode/spring6/spring6/spring-self/target/classes/";

    //返回對象
    @Override
    public Object getBean(Class clazz) {
        return beanFactory.get(clazz);
    }

    //創建有參數構造，傳遞包路徑，設置包掃描規則
    //當前包及其子包，哪個類有＠bean註解，把這個類通過反射實例化
    public AnnotationApplicationContext(String basePackage){
//    public static void pathDemo(String basePackage){
        //com.spring

        try {
            //1 把.換成斜線/
            String packagePath = basePackage.replaceAll("\\.", "/");
            //2 獲取包的絕對路徑
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packagePath);

            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
                
                //獲取包前面的固定路徑
                rootPath = filePath.substring(0, filePath.length() - packagePath.length());

                loadBean(new File(filePath));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //屬性注入
        loadDi();
    }

    //屬性注入
    private void loadDi() {
        //1 實例化的對象都在beanFactory的map集合裡面
        //遍歷map集合
        Set<Map.Entry<Class, Object>> entries = beanFactory.entrySet();

        for (Map.Entry<Class, Object> entry : entries){
            //2 獲取map集合每個對象(value), 每個對象屬性獲取到
            Object obj = entry.getValue();
            //獲取對象
            Class<?> clazz = obj.getClass();
            //獲取每個對象中的屬性
            Field[] declaredFields = clazz.getDeclaredFields();

            //3 遍歷得到每個對象屬性的陣列, 得到每個屬性
            for (Field field : declaredFields){
                //4 判斷屬性上面是否有@Di註解
                Di annotation = field.getAnnotation(Di.class);
                if (annotation != null){
                    //如果私有屬性, 設置可以給值
                    field.setAccessible(true);

                    //5 如果有@Di註解, 把對象進設置(注入進去)
                    try {
                        System.out.println("obj = "+ obj);
                        System.out.println("field.getType() = "+ field.getType());
                        field.set(obj, beanFactory.get(field.getType()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    //包掃描過程，實例化
    private void loadBean(File file) throws Exception {
        //1 先判斷當前內容是否為文件夾
        if (file.isDirectory()){
            //2 獲取文件夾內的所有內容
            File[] childrenFiles = file.listFiles();

            //3 判斷文件夾內容為空，直接返回
            if (childrenFiles == null || childrenFiles.length ==0){
                return;
            }

            //4 如果文件夾內容不為空，遍歷文件夾所有內容
            for (File child : childrenFiles){
                //4.1 遍歷得到的每個file對象，繼續判斷，如果還是文件夾，遞迴
                if (child.isDirectory()){
                    loadBean(child);
                }else {

                    //4.2 如果遍歷得到的file對象不是文件夾而是文件，
                    //4.3 得到包路徑+類名稱部分-字符串擷取
                    System.out.println(child.getAbsolutePath());
                    System.out.println(rootPath);
                    String pathWithClass = child.getAbsolutePath().substring(rootPath.length());
                    System.out.println(pathWithClass);
                    //4.4 判斷當前文件類型是否為.class
                    if (pathWithClass.contains(".class")){
                        //4.5 如果是.class類型，把路徑中的/替換成.，然後把.class去掉
                        String allName = pathWithClass.replaceAll("/", ".")
                                .replace(".class", "");
                        //4.6 開始判斷類別上面是否有註解 ＠myBean，如果有實例化過程
                        //4.6.1 獲取類別的class對象
                        Class<?> clazz = Class.forName(allName);
                        //4.6.2 判斷不是接口
                        if (!clazz.isInterface()){
                            //4.6.3 判斷上面是否有註解＠myBean
                            MyBean annotation = clazz.getAnnotation(MyBean.class);
                            if (annotation != null){
                                //4.6.4
                                Object instance = clazz.getConstructor().newInstance();
                                //4.7 把對象實例化之後，放到map集合beanFactory
                                //4.7.1 判斷當前類別若有interface，則讓它作為map的key
                                if (clazz.getInterfaces().length > 0){
                                    beanFactory.put(clazz.getInterfaces()[0], instance);
                                }else {
                                    beanFactory.put(clazz, instance);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
//        ApplicationContext context = new AnnotationApplicationContext("com.spring");
//        pathDemo("com.spring");
    }

}
