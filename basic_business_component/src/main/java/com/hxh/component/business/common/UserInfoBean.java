package com.hxh.component.business.common;/**
 * Created by hxh on 2017/8/4.
 */

import com.alibaba.fastjson.JSON;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * 创建者：hxh
 * 时间：  2017/8/4
 * 描述：
 */
@Entity
public class UserInfoBean {

    /**
     * expires_in : 86400
     * token_type : Bearer
     * access_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55X25hbWUiOiIiLCJlbXBsb3llZV9pZCI6IiIsImVtcGxveWVlX3N0YXR1cyI6IiIsImV4cCI6MTQ5Mjg0MTE5NSwic2NvcGUiOiJlbXBsb3llZSIsInRlbmFudF9hY2NvdW50IjoiIiwidGVuYW50X2lkIjoiIiwidGVuYW50X3R5cGUiOiIiLCJ1c2VyX2lkIjoiYWNlOWI5NWQtMjY1OC0xMWU3LTkzNTYtNTI1NDAwZjlmMTJhIiwidXNlcl9tb2JpbGUiOiIxNTUxMDM4MjU4NiIsInVzZXJfbmFtZSI6IiIsInVzZXJfc3RhdHVzIjoiYWN0aXZlIn0.tGhYDN0l9-LyY-d4VZxUcvYHU_VqHAIh6KlpRGuTLhU
     * refresh_token : acebb113-2658-11e7-9356-525400f9f12a
     * user : {"id":81726743211111111,"type":"1","name":"周正友","mobile":"15510382586","status":"active","wx_open_id":""}
     * tenant : {"id":81726743211111111,"name":"北京小牛人力资源有限公司","account":"xnrl","status":"opening"}
     * customer : {"id":"xxxxxxxx","name":"xxxxxxx","status":"active"}
     * scope : global_access:tenant_admin,tenant:81726743211111111,customer:xxxxxxxxxx
     */
    @Id
    private Long id;
    private int expires_in;
    private String token_type;
    private String access_token;
    private String refresh_token;
    @Transient
    private UserBean user;
    private String user_str;
    @Transient
    private TenantBean tenant;
    private String tenant_str;
    @Transient
    private CustomerBean customer;
    private String customer_str;

    private String scope;

    @Generated(hash = 786153785)
    public UserInfoBean(Long id, int expires_in, String token_type, String access_token, String refresh_token, String user_str, String tenant_str, String customer_str, String scope) {
        this.id = id;
        this.expires_in = expires_in;
        this.token_type = token_type;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.user_str = user_str;
        this.tenant_str = tenant_str;
        this.customer_str = customer_str;
        this.scope = scope;
    }

    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public UserBean getUser() {
        if(null == user)
        {
            if(null != user_str)
            {
                setUser( JSON.parseObject(user_str,UserBean.class));
            }
        }
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public TenantBean getTenant() {
        if(null == tenant)
        {
            if(null != tenant_str)
            {
                setTenant( JSON.parseObject(tenant_str,TenantBean.class));
            }
        }
        return tenant;
    }

    public void setTenant(TenantBean tenant) {
        this.tenant = tenant;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public static class UserBean {
        /**
         * id : 81726743211111111
         * type : 1
         * name : 周正友
         * mobile : 15510382586
         * status : active
         * wx_open_id :
         */

        private String id;
        private String type;
        private String name;
        private String mobile;
        private String status;
        private String wx_open_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getWx_open_id() {
            return wx_open_id;
        }

        public void setWx_open_id(String wx_open_id) {
            this.wx_open_id = wx_open_id;
        }
    }

    public static class TenantBean {

        /**
         * id : 105431435438985216
         * name : 小爱人力
         * account : lanxun
         * wx_public_account : wx86db3f426c0e9a54
         * service_call :
         * province_code : 110000
         * province : 北京市
         * city_code : 110100
         * city : 市辖区
         * district_code : 110101
         * district : 东城区
         * org_code :
         * contact_name : 姜晓军
         * contact_mobile : 13570145655
         * status : active
         * biz_type : 0
         * created_at : 1499942710
         * updated_at : 1499942710
         * wx_account_secret : 72e5632d1bcb94bee1ada18a24ba41f2
         * logo : {"bucket":"hropublic","object":"tenant_logo/tenants/105431435438985216","etag":"F0E32607CF69E2A1F8406389ADE20DE8","size":49164,"mime_type":"image/jpeg","name":"114423919288520704.jpeg","object_id":"115912017583280128"}
         */

        private String id;
        private String name;
        private String account;
        private String wx_public_account;
        private String service_call;
        private String province_code;
        private String province;
        private String city_code;
        private String city;
        private String district_code;
        private String district;
        private String org_code;
        private String contact_name;
        private String contact_mobile;
        private String status;
        private String biz_type;
        private int created_at;
        private int updated_at;
        private String wx_account_secret;
        private LogoBean logo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getWx_public_account() {
            return wx_public_account;
        }

        public void setWx_public_account(String wx_public_account) {
            this.wx_public_account = wx_public_account;
        }

        public String getService_call() {
            return service_call;
        }

        public void setService_call(String service_call) {
            this.service_call = service_call;
        }

        public String getProvince_code() {
            return province_code;
        }

        public void setProvince_code(String province_code) {
            this.province_code = province_code;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict_code() {
            return district_code;
        }

        public void setDistrict_code(String district_code) {
            this.district_code = district_code;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getOrg_code() {
            return org_code;
        }

        public void setOrg_code(String org_code) {
            this.org_code = org_code;
        }

        public String getContact_name() {
            return contact_name;
        }

        public void setContact_name(String contact_name) {
            this.contact_name = contact_name;
        }

        public String getContact_mobile() {
            return contact_mobile;
        }

        public void setContact_mobile(String contact_mobile) {
            this.contact_mobile = contact_mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBiz_type() {
            return biz_type;
        }

        public void setBiz_type(String biz_type) {
            this.biz_type = biz_type;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public String getWx_account_secret() {
            return wx_account_secret;
        }

        public void setWx_account_secret(String wx_account_secret) {
            this.wx_account_secret = wx_account_secret;
        }

        public LogoBean getLogo() {
            return logo;
        }

        public void setLogo(LogoBean logo) {
            this.logo = logo;
        }

        public static class LogoBean {
            /**
             * bucket : hropublic
             * object : tenant_logo/tenants/105431435438985216
             * etag : F0E32607CF69E2A1F8406389ADE20DE8
             * size : 49164
             * mime_type : image/jpeg
             * name : 114423919288520704.jpeg
             * object_id : 115912017583280128
             */

            private String bucket;
            private String object;
            private String etag;
            private int size;
            private String mime_type;
            private String name;
            private String object_id;

            public String getBucket() {
                return bucket;
            }

            public void setBucket(String bucket) {
                this.bucket = bucket;
            }

            public String getObject() {
                return object;
            }

            public void setObject(String object) {
                this.object = object;
            }

            public String getEtag() {
                return etag;
            }

            public void setEtag(String etag) {
                this.etag = etag;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getMime_type() {
                return mime_type;
            }

            public void setMime_type(String mime_type) {
                this.mime_type = mime_type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getObject_id() {
                return object_id;
            }

            public void setObject_id(String object_id) {
                this.object_id = object_id;
            }
        }
    }

    public static class CustomerBean {
        /**
         * id : xxxxxxxx
         * name : xxxxxxx
         * status : active
         */

        private String id;
        private String name;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


    @Override
    public String toString() {
        return "UserInfoBean{" +
                "expires_in=" + expires_in +
                ", token_type='" + token_type + '\'' +
                ", access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", user=" + user +
                ", tenant=" + tenant +
                ", customer=" + customer +
                ", scope='" + scope + '\'' +
                '}';
    }

    public String getUser_str() {
        return user_str;
    }

    public void setUser_str(String user_str) {
        this.user_str = user_str;
    }

    public String getTenant_str() {
        return tenant_str;
    }

    public void setTenant_str(String tenant_str) {
        this.tenant_str = tenant_str;
    }

    public String getCustomer_str() {
        return customer_str;
    }

    public void setCustomer_str(String customer_str) {
        this.customer_str = customer_str;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}