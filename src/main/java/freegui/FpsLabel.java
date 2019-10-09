package freegui;


import java.awt.*;

public class FpsLabel extends Sprite {

	int show = 0;
	int fpsCount = 0;
	long fpsTime = 0;
	
	public FpsLabel(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void afterEndLive() {

	}

	@Override
	public void draw(Context context) {
		
		long starttime = System.nanoTime();
		starttime = starttime / 1000000;// 当前毫秒

		//compute FPS
		if (1000 <= starttime - fpsTime) {
			fpsTime = starttime;
			show = fpsCount;
			fpsCount = 0;
		}
		fpsCount++;

		context.setColor(Color.LIGHT_GRAY);
		context.drawString("FPS:" + String.valueOf(show), x, y,30);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void up() {

	}

	@Override
	protected void down() {

	}

	@Override
	protected void left() {

	}

	@Override
	protected void right() {

	}

	@Override
	protected void space() {

	}
}
