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
          question1.question_context = "你叫什么名字？";
          question1.answer = "我叫";
          arrayList.add(question1);

          Question question2 = new Question();
          question2.question_id = 3;
          question2.question_context = "请跟我读，爸爸";
          question2.answer = "爸爸";
          arrayList.add(question2);

          Question question3 = new Question();
          question3.question_id = 4;
          question3.question_context = "请跟我读，妈妈？";
          question3.answer = "妈妈";
          arrayList.add(question3);

          Question question4 = new Question();
          question4.question_id = 5;
          question4.question_context = "你几岁啦？";
          question4.answer = "岁";
          arrayList.add(question4);

          return arrayList;
     }


}
