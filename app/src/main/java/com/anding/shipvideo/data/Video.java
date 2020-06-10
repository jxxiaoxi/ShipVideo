package com.anding.shipvideo.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class Video implements Serializable {
    static final long serialVersionUID = 727566175075960653L;
    String vid;//视频播放地址
    String vname;//视频名称
    String vpic;//视频描述
    String vcate;//所属二级分类

    @Generated(hash = 131036936)
    public Video(String vid, String vname, String vpic, String vcate) {
        this.vid = vid;
        this.vname = vname;
        this.vpic = vpic;
        this.vcate = vcate;
    }

    @Generated(hash = 237528154)
    public Video() {
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVname() {
        return this.vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getVpic() {
        return this.vpic;
    }

    public void setVpic(String vpic) {
        this.vpic = vpic;
    }

    public String getVcate() {
        return vcate;
    }

    public void setVcate(String vcate) {
        this.vcate = vcate;
    }
}