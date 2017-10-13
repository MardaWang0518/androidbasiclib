package com.hxh.component.business.utils.oss.response;

/**
 * Created by hxh on 2017/5/17.
 */
public class InitOssResponse
{

    /**
     * domain : oss-cn-beijing.aliyuncs.com
     * access_key_id : STS.LNA9mhWVaYx2M5LHyQnRR27Sw
     * access_key_secret : 82U1HS88bW7vZ5RS3ZvpgWMFzepHpDo9LAmxmZ1FxZKv
     * security_token : CAISxgJ1q6Ft5B2yfSjIrY30ctfcuolA7rrZTxP9rHkEYt1+3fL4lTz2IH1Ke3NpAugYt/sznG5R6P4Ylrh+W4NIX0rNaY5t9ZlN9wqkbtJCWn8VZf9W5qe+EE2/VjQXta27OpfuLr70fvOqdCqa9Etayqf7cjOPRkGsNYbz57dsctUQWHvOD19BH8wECQZ+j8UYOHDNV5OqKQW4unfMK0BqtwFg8zUdj56y2cqB8BHToUTnw+sO3eTLL4OjctNnMeU1MZ+81/ckcbHagmwCskcRrvwn1PQapW6Z4o/EXwtR+RLBccisq4I0dlQoPvBkR/ca8KalyccV4LKDy97FrD9WJvxQXijlQ4St/dDJAuvBNKxiKOikYS2Wio7WacWt7Vp6OSgBRwpOess8LHhrEgArSTzcJaKh9UrDfgC5Ua+B3bHYJ37uMM4JlhqAAV4dd85ek66S+ioN5feFvL29FM0yhvJ1/yCAYSd2qAZNObAFkGKWLm2qkti7JiE74GxURQ8u2YNkWoMSMXjwVYql3PyFXmUUFxp8ffM9WweVGDRXa2gZDg5QuKcht1/FDDEa5oc0o8uTEiEegqlKUX7KR42rRhfkqVdBls9rDfit
     * signature :
     * policy :
     * bucket : hro
     * object_path : contract/tenants/83422060109369344/users/105310586673303552/119892304189198336
     * callback_body : eyJjYWxsYmFja1VybCI6Imh0dHA6Ly8xMDYuNzUuMTA3LjU1OjIwMDAwL2ZpbGVtZXRhL3YxL2NhbGxiYWNrcy8xMTk4OTIzMDQxODkxOTgzMzYiLCJjYWxsYmFja0hvc3QiOiIxMDYuNzUuMTA3LjU1IiwiY2FsbGJhY2tCb2R5Ijoie1wiYnVja2V0XCI6JHtidWNrZXR9LFwib2JqZWN0XCI6JHtvYmplY3R9LFwiZXRhZ1wiOiR7ZXRhZ30sXCJzaXplXCI6JHtzaXplfSxcIm1pbWVfdHlwZVwiOiR7bWltZVR5cGV9LFwiYWNjZXNzX3Rva2VuXCI6JHt4OmFjY2Vzc190b2tlbn0sXCJuYW1lXCI6JHt4Om5hbWV9fSIsImNhbGxiYWNrQm9keVR5cGUiOiJhcHBsaWNhdGlvbi9qc29uIn0=
     * callback_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MDM0NzM2NDEsInNjb3BlIjoiZ2xvYmFsX2FjY2VzczplbmRfdXNlcix0ZW5hbnQ6ODM0MjIwNjAxMDkzNjkzNDQiLCJ0ZW5hbnQiOnsiaWQiOiI4MzQyMjA2MDEwOTM2OTM0NCIsIm5hbWUiOiLljJfkuqzlsI_niLHmmbrog73np5HmioDmnInpmZDlhazlj7giLCJhY2NvdW50IjoidGVzdCIsInd4X3B1YmxpY19hY2NvdW50IjoiIiwic3RhdHVzIjoiYWN0aXZlIiwiYml6X3R5cGUiOiIwIn0sInVzZXIiOnsiaWQiOiIxMDUzMTA1ODY2NzMzMDM1NTIiLCJuYW1lIjoi5bCP5b-XIiwibW9iaWxlIjoiMTgzNTMzODU3MDMiLCJzdGF0dXMiOiJhY3RpdmUiLCJ0eXBlIjoiMSIsInd4X29wZW5faWQiOiJvWDRFVnYtTUlTRGFPV2VNeTZKOWNvTEFFeGZrIn19.i_wS5EQm9GZsRHU1lBz0PuIhtfu1Ztxsf0nETfmoB2A
     */

    private String domain;
    private String access_key_id;
    private String access_key_secret;
    private String security_token;
    private String signature;
    private String policy;
    private String bucket;
    private String object_path;
    private String callback_body;
    private String callback_token;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAccess_key_id() {
        return access_key_id;
    }

    public void setAccess_key_id(String access_key_id) {
        this.access_key_id = access_key_id;
    }

    public String getAccess_key_secret() {
        return access_key_secret;
    }

    public void setAccess_key_secret(String access_key_secret) {
        this.access_key_secret = access_key_secret;
    }

    public String getSecurity_token() {
        return security_token;
    }

    public void setSecurity_token(String security_token) {
        this.security_token = security_token;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getObject_path() {
        return object_path;
    }

    public void setObject_path(String object_path) {
        this.object_path = object_path;
    }

    public String getCallback_body() {
        return callback_body;
    }

    public void setCallback_body(String callback_body) {
        this.callback_body = callback_body;
    }

    public String getCallback_token() {
        return callback_token;
    }

    public void setCallback_token(String callback_token) {
        this.callback_token = callback_token;
    }

    @Override
    public String toString() {
        return "InitOssResponse{" +
                "domain='" + domain + '\'' +
                ", access_key_id='" + access_key_id + '\'' +
                ", access_key_secret='" + access_key_secret + '\'' +
                ", security_token='" + security_token + '\'' +
                ", signature='" + signature + '\'' +
                ", policy='" + policy + '\'' +
                ", bucket='" + bucket + '\'' +
                ", object_path='" + object_path + '\'' +
                ", callback_body='" + callback_body + '\'' +
                ", callback_token='" + callback_token + '\'' +
                '}';
    }
}
