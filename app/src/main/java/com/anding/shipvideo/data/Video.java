package com.anding.shipvideo.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class Video implements Serializable {
    static final long serialVersionUID = 727566175075960653L;
    String uri;//视频播放地址
    String name;//视频名称
    String description;//视频描述
    String pic;//视频简图
    String category;//视频所属类别

    @Generated(hash = 1856908545)
    public Video(String uri, String name, String description, String pic,
            String category) {
        this.uri = uri;
        this.name = name;
        this.description = description;
        this.pic = pic;
        this.category = category;
    }

    @Generated(hash = 237528154)
    public Video() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
