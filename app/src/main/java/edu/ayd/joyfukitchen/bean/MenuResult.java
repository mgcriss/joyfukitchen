package edu.ayd.joyfukitchen.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 * 返回结果类
 */
public class MenuResult {

    //http状态码
    private String resultcode;

    //结果状态
    private String reason;

    //返回的数据类
    private ResultBean result;

    //错误码
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

   public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    /**结果信息类*/
    public static class ResultBean {


        //结果集总条数
        private int totalNum;

        //起始下标
        private int pn;

        //结束下标
        private int rn;

        //结果集
        private List<DataBean> data;

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getRn() {
            return rn;
        }

        public void setRn(int rn) {
            this.rn = rn;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        //存储进数据库
        /**菜谱类*/
        public static class DataBean {


            private String id;
            //名字
            private String title;
            //标签
            private String tags;
            //说明
            private String imtro;
            //配料
            private String ingredients;
            //佐料
            private String burden;
            //菜图片
            private List<String> albums;
            //步骤
            private List<StepsBean> steps;

            @Override
            public String toString() {
                return "DataBean{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", tags='" + tags + '\'' +
                        ", imtro='" + imtro + '\'' +
                        ", ingredients='" + ingredients + '\'' +
                        ", burden='" + burden + '\'' +
                        ", albums=" + albums +
                        ", steps=" + steps +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getImtro() {
                return imtro;
            }

            public void setImtro(String imtro) {
                this.imtro = imtro;
            }

            public String getIngredients() {
                return ingredients;
            }

            public void setIngredients(String ingredients) {
                this.ingredients = ingredients;
            }

            public String getBurden() {
                return burden;
            }

            public void setBurden(String burden) {
                this.burden = burden;
            }

            public List<String> getAlbums() {
                return albums;
            }

            public void setAlbums(List<String> albums) {
                this.albums = albums;
            }

            public List<StepsBean> getSteps() {
                return steps;
            }

            public void setSteps(List<StepsBean> steps) {
                this.steps = steps;
            }

            /**步骤信息类*/
            public static class StepsBean {

                private String img;
                //步骤说明
                private String step;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getStep() {
                    return step;
                }

                public void setStep(String step) {
                    this.step = step;
                }
            }
        }
    }
}
