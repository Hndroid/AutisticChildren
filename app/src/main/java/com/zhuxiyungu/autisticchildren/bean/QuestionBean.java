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

          Question question4 = new Question();
          question4.question_id = 5;
          question4.question_context = "你几岁了呢？";
          question4.answer = "岁";
          arrayList.add(question4);

          Question question5 = new Question();
          question5.question_id = 6;
          question5.question_context = "你喜欢什么颜色呢？";
          question5.answer = "色";
          arrayList.add(question5);

          Question question6 = new Question();
          question6.question_id = 7;
          question6.question_context = "你能自己穿衣服吗？";
          question6.answer = "能";
          arrayList.add(question6);

          Question question7 = new Question();
          question7.question_id = 8;
          question7.question_context = "天空是什么颜色的呢？";
          question7.answer = "色";
          arrayList.add(question7);

          Question question8 = new Question();
          question8.question_id = 9;
          question8.question_context = "5乘于5等于多少呢？";
          question8.answer = "25";
          arrayList.add(question8);

          return arrayList;
     }


}
