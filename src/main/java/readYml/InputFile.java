package readYml;

import com.alibaba.fastjson.JSONObject;
import util.WorkStation;
import vo.AGV;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InputFile {
    private WorkStation workStation=null;
    public static WorkStation load(){
        File path = new File("/home/7ll/项目/springBoot-master/qll-idea/src/main/resources");
        File file = new File(path,"package.json");
        String data=loadDate(file);
        //System.out.println(data);
        JSONObject json=JSONObject.parseObject(data);
        return WorkStation.load(json);
    }
    private static String loadDate(File file){
        String date="";
        BufferedReader reader=null;
        try {
            reader=new BufferedReader(new FileReader(file));
            String temp=null;
            while ((temp =reader.readLine())!=null){
                date+=temp;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args) {
        WorkStation workStation1=null;
       InputFile.load();
//        List<AGV> a=new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            AGV agv=new AGV("234");
//            a.add(agv);
//        }



//        for (int i = 0; i < 100; i++) {
//            int start=(int)(Math.random()*(4));
//            System.out.println(start);
//        }
    }
}
