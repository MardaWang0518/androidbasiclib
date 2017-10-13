package com.hxh.component.business.utils.oss.request;

/**
 * Created by hxh on 2017/5/17.
 */
public class Request_GetOssInfo {

    public Request_GetOssInfo(String object_type, String access_type, String instance_id) {
        this.object_type = object_type;
        this.access_type = access_type;
        this.instance_id = instance_id;
    }

    /**
     * object_type : personal
     * action : put_object
     * instance_id :
     */

    private String object_type;
    private String access_type;
    private String instance_id;

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public String getAction() {
        return access_type;
    }

    public void setAction(String action) {
        this.access_type = action;
    }

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }
}
