package com.zhuxiyungu.autisticchildren.bean;

import java.util.ArrayList;

/**
 * Created by null on 17-2-18.
 * 问题集合（暂时用于本地合成）
 */

public class QuestionBean {

     public static ArrayList<Question> bean(){
          ArrayList<Question> arrayList = new ArrayList<>();

          Question question = new Question();
          question.question_id = 1;
          question.question_context = "你喜欢小叮当吗?";
          question.answer = "喜欢";
          arrayList.add(question);

          Question question1 = new Question();
          question1.question_id = 2;
          question1.question_context = "你叫什么名字呢？";
          question1.answer = "自闭症儿童";
          arrayList.add(question1);

          Question question2 = new Question();
          question2.question_id = 3;
          question2.question_context = "2+3等于多少呢？";
          question2.answer = "5";
          arrayList.add(question2);

          Question question3 = new Question();
          question3.question_id = 4;
          question3.question_context = "地球是圆的还是方的呢？";
          question3.answer = "圆的";
          arrayList.add(question3);

          return arrayList;
     }


}
