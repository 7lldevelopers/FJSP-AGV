package readYml;

import com.alibaba.fastjson.JSONObject;
import org.ho.yaml.Yaml;
import util.WorkStation;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class ReadYml {
//    public static Map<String,String> map = new HashMap<>();
    private static JSONObject jsonObject = new JSONObject();
//    public Map<String, String> readPropertyFile(String fileName) {
//        Properties pro = new Properties();
//        InputStream in = getClass().getClassLoader().getResourceAsStream(fileName);//来读取一个文件的,从用来加载类的搜索路径打开具有指定名称的资源，以读取该资源，用这种方法读取
//        try {
//            pro.load(in);//加载文件  InputStream
//            Iterator<String> iterator = pro.stringPropertyNames().iterator();
//            while (iterator.hasNext()) {
//                String key = iterator.next();
//               System.out.println(key);
//                String value = pro.getProperty(key);
//               System.out.println(value);
//                //获得属性
//                map.put(key, value);
//            }
//            in.close();
//        } catch (IOException e) {
//            System.out.printf("加载配置文件出错%s",e.getMessage());
//        }
//        return map;
//    }
    public static JSONObject readYml(){
        try {
            File path = new File("/home/7ll/项目/springBoot-master/qll-idea/src/main/resources");
            File file = new File(path,"package.json");
            InputStream inputStream = new FileInputStream(file);
            jsonObject = Yaml.loadType(inputStream,JSONObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static void main(String[] args) {
//        JSONObject jsonObject = ReadYml.readYml();
//       // WorkStation
//        WorkStation asd=WorkStation.load(jsonObject);

    }
}
