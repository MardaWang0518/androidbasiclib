package com.hxh.component.business.utils.oss.bean;

/**
 * 创建者：hxh
 * 时间：  2017/9/8
 * 描述：
 */
public class OssUpLoadResultBean {

    /**
     * object_id : 125974990280462336
     * is_public : n
     * bucket : hro
     * object : contract/tenants/105431435438985216/users/105310586673303552/125974990280462336null
     * name :
     * etag : 012634B2257A81916F70F6E46960253B
     * size : 10547
     * mime_type : application/octet-stream
     */

    private String object_id;
    private String is_public;
    private String bucket;
    private String object;
    private String name;
    private String etag;
    private int size;
    private String mime_type;

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getIs_public() {
        return is_public;
    }

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
