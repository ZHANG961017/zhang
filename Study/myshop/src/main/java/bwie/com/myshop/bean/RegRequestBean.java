package bwie.com.myshop.bean;

/**
 * Created by 13435 on 2017/10/12.
 */

public class RegRequestBean {


    /**
     * code : 200
     * datas : {"username":"andro","userid":"8","key":"ce30718ab0d11dac3036b959350d4d53"}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * username : andro
         * userid : 8
         * key : ce30718ab0d11dac3036b959350d4d53
         */

        private String username;
        private int userid;
        private String key;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return "DatasBean{" +
                    "username='" + username + '\'' +
                    ", userid='" + userid + '\'' +
                    ", key='" + key + '\'' +
                    '}';
        }
    }
}
