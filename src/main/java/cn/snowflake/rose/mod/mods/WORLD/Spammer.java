package cn.snowflake.rose.mod.mods.WORLD;


import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.other.AbuseUtils;
import cn.snowflake.rose.utils.time.TimeHelper;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;

import java.util.Random;

public class Spammer extends Module {
    TimeHelper delay = new TimeHelper();
    Random random = new Random();
    double state = 0.0D;
    private Value<Double>  spammerdelay = new Value("Spammer_Delay", 2000.0D, 100.0D, 30000.0D, 100D);
    private Value<Boolean> randomstring = new Value<Boolean>("Spammer_RandomString", true);
    private Value<String> mode = new Value<String>("Spammer","Mode",0);
    private Value<String> text = new Value<String>("Spammer_Text","","Chentg Code by CNSnowFlake. qq\u7fa4:720179568");
    private int num;

    public static String customtext = "\u5e05\u54e5\u4f60\u597d\u0021";

    public Spammer() {
        super("Spammer","Spammer", Category.PLAYER);
        this.mode.addValue("Custome1");
        this.mode.addValue("Custome2");
        this.mode.addValue("Abuse");
        setChinesename("\u81ea\u52a8\u804a\u5929");
    }

    @Override
    public String getDescription() {
        return "自动刷屏!";
    }
    public void onDisable() {
        this.state = 0.0D;
        num = 0;
        super.onDisable();
    }

    @EventTarget
    public void onUpdate(EventUpdate event){
        this.setDisplayName("Delay:" + this.spammerdelay.getValueState() + " Times:" + num);
        new Random();
        if(this.delay.isDelayComplete((this.spammerdelay.getValueState()).longValue())) {
            ++this.state;
            num++;
            String message = "";
            if (this.mode.isCurrentMode("Abuse")){
                message = AbuseUtils.getAbuse()+AbuseUtils.getAbuse();
            }
            if (this.mode.isCurrentMode("Custome2")){
                message = text.getText();
            }
            if (this.mode.isCurrentMode("Custome1")){
                message = customtext;
            }
            if(this.randomstring.getValueState()) {
                message = message + " >" + new StringRandom().getStringRandom(6) + "<";
            }
            this.mc.thePlayer.sendChatMessage(message);
            this.delay.reset();
        }
    }

    public class StringRandom {
        public String getStringRandom(int length) {

            String val = "";
            Random random = new Random();

            for(int i = 0; i < length; i++) {

                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
                if( "char".equalsIgnoreCase(charOrNum) ) {
                    int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val += (char)(random.nextInt(26) + temp);
                } else if( "num".equalsIgnoreCase(charOrNum) ) {
                    val += String.valueOf(random.nextInt(10));
                }
            }
            return val;
        }
    }
}
