package util;

import java.awt.image.BufferedImage;

public class Animation {
	
	public enum PlayMode {
		LOOP_NORMAL,
		LOOP_PINGPONG, 
		LOOP_RANDOM,
		LOOP_REVERSED, 
		NORMAL,
		REVERSED 
	};
	private PlayMode playMode;
	private long duration;
	private BufferedImage [] keyFrames;
	private int index = 0;
	private boolean start = false;
	private boolean finished = false;
	
	private long lastTime = 0;
 
	public void setPlayMode(PlayMode playMode) {
		this.playMode = playMode;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public void setKeyFrames(BufferedImage[] keyFrames) {
		this.keyFrames = keyFrames;
	}
	
	public BufferedImage getKeyFrame(){
		if (!start) {
			start = true;
			lastTime = System.currentTimeMillis();
			return keyFrames[0];
		}else {
			long systemTime = System.currentTimeMillis();
			if ((systemTime - lastTime) >= duration) {
				lastTime = systemTime;
				index++;
				if (index >= keyFrames.length) {
					if (PlayMode.NORMAL.equals(playMode)){
						finished = true;
						index = keyFrames.length - 1;
					}else {
						index = 0;
					}

				}
			}
			return keyFrames[index];
		}
	}
	public void reset(){
		finished = false;
		start = false;
		index = 0;
	}

	public boolean isFinished() {
		return finished;
	}


}
