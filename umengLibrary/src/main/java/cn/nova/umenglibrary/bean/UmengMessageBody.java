package cn.nova.umenglibrary.bean;

import java.util.Map;

/**
 * 友盟离线推送-body信息解析
 * @author Zyq
 * @date 2019/11/25　17:06.
 */
public class UmengMessageBody {

    /**
     * display_type : notification
     * extra : {"url":"https://www.bus365.com"}
     * msg_id : uujua9u157464968593710
     * body : {"after_open":"go_activity","play_lights":"false","ticker":"测试标题","play_vibrate":"false","activity":"cn.nova.phone.ui.UrlLauncherActivity","text":"测试内容","title":"测试标题","play_sound":"true"}
     * random_min : 0
     */

    public String display_type;
    public Map<String,String> extra;
    public String msg_id;
    public BodyBean body;
}
