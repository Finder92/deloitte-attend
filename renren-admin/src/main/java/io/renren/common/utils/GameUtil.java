package io.renren.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GameUtil
 * @Description TODO
 * @Author Finder
 * @Date 2019/8/8 13:31
 **/
public class GameUtil {
    public static String getDescription(String age,String gender){
        String description ="";
        Map<String, List<String>> mapMan = new HashMap<>();
        List<String> listLevel1 = new ArrayList<>();
        listLevel1.add("风度翩翩");
        listLevel1.add("面如冠玉");
        listLevel1.add("英俊潇洒");
        listLevel1.add("品貌非凡");
        listLevel1.add("翩翩少年");
        listLevel1.add("玉树临风");
        listLevel1.add("英姿勃勃");
        mapMan.put("18-24",listLevel1);

        List<String> listLevel2 = new ArrayList<>();
        listLevel2.add("温文而雅");
        listLevel2.add("彬彬有礼");
        listLevel2.add("功成名就");
        listLevel2.add("学富五车");
        listLevel2.add("品貌非凡");
        mapMan.put("24-30",listLevel2);

        List<String> listLevel3 = new ArrayList<>();
        listLevel3.add("雅人深致");
        listLevel3.add("逸群之才");
        listLevel3.add("谦谦君子");
        listLevel3.add("相貌堂堂");
        listLevel3.add("威风凛凛");
        mapMan.put("30-40",listLevel3);

        List<String> listLevel4 = new ArrayList<>();
        listLevel4.add("成熟稳重");
        listLevel4.add("气宇轩昂");
        listLevel4.add("风度翩翩");
        listLevel4.add("学识渊博");
        listLevel4.add("神采奕奕");
        mapMan.put("40-50",listLevel4);


        Map<String, List<String>> mapWoman = new HashMap<>();
        List<String> listLevelWoman1 = new ArrayList<>();
        listLevelWoman1.add("人间尤物");
        listLevelWoman1.add("才华绝代");
        listLevelWoman1.add("翩若惊鸿");
        listLevelWoman1.add("婉如遊龙");
        mapWoman.put("18-24",listLevelWoman1);

        List<String> listLevelWoman2 = new ArrayList<>();
        listLevelWoman2.add("出水芙蓉");
        listLevelWoman2.add("芙蓉如面");
        listLevelWoman2.add("柳叶弯眉");
        mapWoman.put("24-30",listLevelWoman2);

        List<String> listLevelWoman3 = new ArrayList<>();
        listLevelWoman3.add("风姿绰约");
        listLevelWoman3.add("淡如秋水");
        listLevelWoman3.add("丽质天成");
        listLevelWoman3.add("若玉轻风");
        mapWoman.put("30-40",listLevelWoman3);

        List<String> listLevelWoman4 = new ArrayList<>();
        listLevelWoman4.add("秀外慧中");
        listLevelWoman4.add("温文尔雅");
        listLevelWoman4.add("丰神绰约");
        mapWoman.put("40-50",listLevelWoman4);
       if("女".equals(gender)){
            Integer intAge = Integer.parseInt(age);
           for (String s : mapWoman.keySet()) {
               String [] arrS= s.split("-");
               int ageLeft = Integer.parseInt(arrS[0]);
               int ageRight = Integer.parseInt(arrS[1]);
               if(intAge>ageLeft&&intAge<=ageRight){
                   List<String> descriList = mapWoman.get(s);
                   description=descriList.get((int)(Math.random()*2));
               }
           }

       }else {
           Integer intAge = Integer.parseInt(age);
           for (String s : mapMan.keySet()) {
               String [] arrS= s.split("-");
               int ageLeft = Integer.parseInt(arrS[0]);
               int ageRight = Integer.parseInt(arrS[1]);
               if(intAge>ageLeft&&intAge<=ageRight){
                   List<String> descriList = mapMan.get(s);
                   description=descriList.get((int)(Math.random()*5));
               }
           }
       }

        return description;
    }

    public static void main(String[] args) {
        System.out.println(getDescription("19", "女"));
    }
}
