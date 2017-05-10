package edu.ayd.joyfukitchen.util;

import android.content.Context;

import java.text.DecimalFormat;

import edu.ayd.joyfukitchen.bean.FoodNutrition;
import edu.ayd.joyfukitchen.bean.User;
import edu.ayd.joyfukitchen.dao.FoodNutritionDao;
import edu.ayd.joyfukitchen.dao.UserDao;

/**
 * Created by tangtang on 2017/4/6 10:45.
 */

public class CountUtil {


    private Context context;

    /**
     *计算一顿的能量
     * @param id
     * @param weight
     * @param mealCounts
     * @return
     */
   public Double countMealEnergy(Integer id,double weight,double mealCounts){

      FoodNutrition food = new FoodNutritionDao(context).showFoodByFoodId(id);
      mealCounts = mealCounts+food.getEnergy()*weight;
      return mealCounts;

   }


    /**
     * 计算一天摄入的能量
     * @param mealCounts
     * @param totalCounts
     * @return
     */
    public Double countTotalEnergy(double mealCounts,double totalCounts){
        totalCounts=totalCounts+mealCounts;
        return  totalCounts;
    }

    /**
     *
     * @param totalCounts
     * @param sex
     * @return
     */
    public String tipsRemind(double totalCounts,String sex){
        if(sex.equals("男")){
            if(totalCounts<9250){
                return "亲,你今天的热量没有达到正常人的程度";
            }else if(totalCounts>10090){
                return "亲,你今天的热量超过正常人的程度";
            }else{
                return "亲,你今天的热量属于正常人的程度";
            }
        }else if(sex.equals("女")){
            if(totalCounts<7980){
                return "亲,你今天的热量没有达到正常人的程度";
            }else if(totalCounts>8820){
                return "亲,你今天的热量超过正常人的程度";
            }else{
                return "亲,你今天的热量属于正常人的程度";
            }
        }
        return null;
    }

    /**
     *
     * 小贴士建议：根据当天所需摄入能量与用户称取食材总能量比较的出建议
     * @param username
     * @return
     */
    /*
    public String Tips(String username,double totalCounts){
        User user=new UserDao(context).queryUser(username);
        double basicHeat=0;
        if(user.getTarget().equals("减脂")){
            if(user.getWorkStrength().equals("较轻体力活动")){
               if(user.getSex().equals("男")){
                   if(user.getAge()>=18&&user.getAge()<=30){
                       basicHeat=15.2*user.getWeight()+680+95*user.getWorkTimes();
                       double count=totalCounts-basicHeat;
                       if(count>0 && totalCounts>=5000){
                           double date=count/170;
                           return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                       }else if(count==0 && totalCounts>=5000){
                           return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                       }else if(count<0 && totalCounts>=5000){
                           return "你所食用的热量刚好达到你的目标";
                       }
                   }else if(user.getAge()>=31&&user.getAge()<=60){
                       basicHeat=11.5*user.getWeight()+830+95*user.getWorkTimes();
                       double count=totalCounts-basicHeat;
                       if(count>0 && totalCounts>=5000){
                           double date=count/170;
                           return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                       }else if(count==0 && totalCounts>=5000){
                           return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                       }else if(count<0 && totalCounts>=5000){
                           return "你所食用的热量刚好达到你的目标";
                       }
                   }else if(user.getAge()>60){
                       basicHeat=13.4*user.getWeight()+490+95*user.getWorkTimes();
                       double count=totalCounts-basicHeat;
                       if(count>0 && totalCounts>=5000){
                           double date=count/170;
                           return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                       }else if(count==0 && totalCounts>=5000){
                           return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                       }else if(count<0 && totalCounts>=5000){
                           return "你所食用的热量刚好达到你的目标";
                       }
                   }
               }else if(user.getSex().equals("女")){
                   if(user.getAge()>=18&&user.getAge()<=30){
                       basicHeat=14.6*user.getWeight()+450+95*user.getWorkTimes();
                       double count=totalCounts-basicHeat;
                       if(count>0 && totalCounts>=5000){
                           double date=count/170;
                           return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                       }else if(count==0 && totalCounts>=5000){
                           return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                       }else if(count<0 && totalCounts>=5000){
                           return "你所食用的热量刚好达到你的目标";
                       }
                   }else if(user.getAge()>=31&&user.getAge()<=60){
                       basicHeat=8.6*user.getWeight()+830+95*user.getWorkTimes();
                       double count=totalCounts-basicHeat;
                       if(count>0 && totalCounts>=5000){
                           double date=count/170;
                           return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                       }else if(count==0 && totalCounts>=5000){
                           return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                       }else if(count<0 && totalCounts>=5000){
                           return "你所食用的热量刚好达到你的目标";
                       }

                   }else if(user.getAge()>60){
                       basicHeat=10.4*user.getWeight()+600+95*user.getWorkTimes();
                       double count=totalCounts-basicHeat;
                       if(count>0 && totalCounts>=5000){
                           double date=count/170;
                           return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                       }else if(count==0 && totalCounts>=5000){
                           return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                       }else if(count<0 && totalCounts>=5000){
                           return "你所食用的热量刚好达到你的目标";
                       }

                   }
               }
            }else if(user.getWorkStrength().equals("轻体力活动")){
                if(user.getSex().equals("男")){
                    if(user.getAge()>=18&&user.getAge()<=30){
                        basicHeat=15.2*user.getWeight()+680+120*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }
                    }else if(user.getAge()>=31&&user.getAge()<=60){
                        basicHeat=11.5*user.getWeight()+830+120*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }
                    }else if(user.getAge()>60){
                        basicHeat=13.4*user.getWeight()+490+120*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }
                    }
                }else if(user.getSex().equals("女")){
                    if(user.getAge()>=18&&user.getAge()<=30){
                        basicHeat=14.6*user.getWeight()+450+120*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }
                    }else if(user.getAge()>=31&&user.getAge()<=60){
                        basicHeat=8.6*user.getWeight()+830+120*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }

                    }else if(user.getAge()>60){
                        basicHeat=10.4*user.getWeight()+600+120*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }

                    }
                }
            }else if(user.getWorkStrength().equals("中等体力劳动")){
                if(user.getSex().equals("男")){
                    if(user.getAge()>=18&&user.getAge()<=30){
                        basicHeat=15.2*user.getWeight()+680+170*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }
                    }else if(user.getAge()>=31&&user.getAge()<=60){
                        basicHeat=11.5*user.getWeight()+830+170*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }
                    }else if(user.getAge()>60){
                        basicHeat=13.4*user.getWeight()+490+170*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }
                    }
                }else if(user.getSex().equals("女")){
                    if(user.getAge()>=18&&user.getAge()<=30){
                        basicHeat=14.6*user.getWeight()+450+170*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }
                    }else if(user.getAge()>=31&&user.getAge()<=60){
                        basicHeat=8.6*user.getWeight()+830+170*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }

                    }else if(user.getAge()>60){
                        basicHeat=10.4*user.getWeight()+600+170*user.getWorkTimes();
                        double count=totalCounts-basicHeat;
                        if(count>0 && totalCounts>=5000){
                            double date=count/170;
                            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
                        }else if(count==0 && totalCounts>=5000){
                            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
                        }else if(count<0 && totalCounts>=5000){
                            return "你所食用的热量刚好达到你的目标";
                        }

                    }
                }
            }
        }else if(user.getTarget().equals("增脂")){
            if(user.getWorkStrength().equals("较轻体力活动")){
                if(user.getSex().equals("男")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }else if(user.getSex().equals("女")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }
            }else if(user.getWorkStrength().equals("轻体力活动")){
                if(user.getSex().equals("男")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }else if(user.getSex().equals("女")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }
            }else if(user.getWorkStrength().equals("中等体力劳动")){
                if(user.getSex().equals("男")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }else if(user.getSex().equals("女")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }
            }
        }else if(user.getTarget().equals("维持体重")){
            if(user.getWorkStrength().equals("较轻体力活动")){
                if(user.getSex().equals("男")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }else if(user.getSex().equals("女")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }
            }else if(user.getWorkStrength().equals("轻体力活动")){
                if(user.getSex().equals("男")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }else if(user.getSex().equals("女")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }
            }else if(user.getWorkStrength().equals("中等体力劳动")){
                if(user.getSex().equals("男")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }else if(user.getSex().equals("女")){
                    if(user.getAge()>=18&&user.getAge()<=30){

                    }else if(user.getAge()>=31&&user.getAge()<=60){

                    }else if(user.getAge()>60){

                    }
                }
            }
        }
    }*/





    /**
     * 清空一顿的数据
     * @param mealCounts
     */
    public void colseMeal(double mealCounts){
        mealCounts=0.0;
    }


    /**
     * 清空一天的数据
     * @param totalCounts
     */
    public void closeTotal(double totalCounts){
        totalCounts=0.0;
    }



    private Double a; //*的
    private Double weight;  //体重
    private Double c;  // +的
    private Integer d; //工作强度消耗,单位千卡
    private Integer wt; //工作时间
    private double count;

    /**
     * 计算
     * 根据个人资料提供的信息返回值，返回每天必须的能量
     * */
    public Double getNengLiang(Context context, String email){
        setACAndWeight(context, email);
        return a*weight+c+d*wt;
    }



    DecimalFormat df = new DecimalFormat("#####0.00");

    /**
     * 判断
     * 根据用户一天所食用的量和一天基本所需的量判断用户是否达到目的
     * @param basicheart
     * @param totalCounts
     * @return
     */
    public String Tips(double basicheart,double totalCounts){
        count=totalCounts-basicheart;
        if(count>0 && totalCounts>=5000){
            double date=count/170;
            return "你所食用的热量超过了你的目标,所以你需要在跑步"+df.format(date)+"以上";
        }else if(count==0 && totalCounts>=5000){
            return "你所食用的热量刚好维持你今天所需要的热量，但你还需要跑跑步";
        }else if(count<0 && totalCounts>=5000){
            return "你所食用的热量刚好达到你的目标";
        }
        return null;
    }

    /**
     * 判断
     * */
    public void setACAndWeight(Context context, String email){
        User user = new UserDao(context).queryUser(email);
        Integer age = user.getAge();
        weight = user.getWeight();
        String sex = user.getSex();
        String ws = user.getWorkStrength();
        wt=user.getWorkTimes();


        if(ws.equals("脑力劳动")){
            d = 95;
        } else if(ws.equals("较体力劳动")) {
            d = 120;
        } else if(ws.equals("中度体力劳动")) {
            d = 170;
        }

        if(sex.equals("女")){
            if(age > 18 && age < 30){
                a = 14.6;
                c = 450.0;
            } else if (age > 30 && age <60){
                a = 8.6;
                c = 830.0;
            } else if (age >60) {
                a = 10.4;
                c = 600.0;
            }
        } else {
            if(age > 18 && age < 30){
                a = 15.2;
                c = 680.0;
            } else if (age > 30 && age <60){
                a = 11.5;
                c = 830.0;
            } else if (age >60) {
                a = 13.4;
                c = 490.0;
            }
        }


    }


}
