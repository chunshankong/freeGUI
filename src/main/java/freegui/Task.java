package freegui;

import java.util.concurrent.Callable;

public class Task {
    private Long millis;
    private Function function;
    private long lastTime ;

    public Task(Long millis, Function function){
        this.millis = millis;
        this.function = function;
        this.lastTime = System.nanoTime() / 1000000;// 当前毫秒;
    }
    public Task( Function function){
        this.millis = millis;
        this.function = function;
    }

    public boolean execute(){
        if (millis == null){
            execute0();
            return true;
        }else {
            long currentTime = System.nanoTime() / 1000000;// 当前毫秒;

            if (millis <= (currentTime - lastTime)) {
                execute0();
                return true;
            }else {
                return false;
            }
        }
    }
    private void execute0(){
        try {
            function.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
