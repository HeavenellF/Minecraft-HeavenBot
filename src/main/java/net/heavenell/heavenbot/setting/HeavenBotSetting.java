package net.heavenell.heavenbot.setting;

public class HeavenBotSetting {

    private boolean autoGreeting = true;
    private boolean autoResponse = true;
    private boolean autoAccept = true;
    private static final HeavenBotSetting instance = new HeavenBotSetting();

    private HeavenBotSetting() {
    }
    public boolean isAutoGreeting() {
        return this.autoGreeting;
    }

    public boolean isAutoResponse() {
        return autoResponse;
    }

    public boolean isAutoAccept() {
        return autoAccept;
    }

    public void toggleAutoGreeting() {
        this.autoGreeting = !this.autoGreeting;
        System.out.println(this.autoGreeting);
    }
    public void toggleAutoResponse() {
        this.autoResponse = !this.autoResponse;
        System.out.println(this.autoResponse);
    }
    public void toggleAutoAccept() {
        this.autoAccept = !this.autoAccept;
        System.out.println(this.autoAccept);
    }

    public static HeavenBotSetting getInstance(){
        return instance;
    }
}
