package niuwei.com.headlinedemo.bean;

/**
 * Created by One Dream on 2017/9/8.
 */

public class TabTypeBean {
    private String title;
    private String path;

    public TabTypeBean(String title, String path) {
        this.title = title;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}