package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import entities.Entity;
import gamestates.Playing;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[][] RobotArr;
	private ArrayList<Robot> robots = new ArrayList<>();

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}
	
	private void addEnemies() {
		robots = LoadSave.GetRobots();
	}
	public void update(int[][]lvldata, Player player) {
		for (Robot c : robots)
				c.update(lvldata, player);
	}
	
	public void draw(Graphics g, int xLvlOffset) {
		drawRobots(g, xLvlOffset);
		for (Robot a : robots)
			a.drawHitbox(g, xLvlOffset);
		for (Robot a : robots)
			a.drawAttackBox(g, xLvlOffset);
	};

	private void drawRobots(Graphics g, int xLvlOffset) {
		for (Robot c : robots)
				g.drawImage(RobotArr[c.getEnemyState()][c.getAniIndex()], (int) c.getHitbox().x - xLvlOffset - ROBOT_DRAWOFFSET_X + c.flipX(), (int) c.getHitbox().y - 10,
						(int)(64 * 2)*c.flipW(), (int)(40 * 2), null);
//				c.drawHitbox(g,xLvlOffset);
//				c.drawAttackBox(g, xLvlOffset);


	}
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Robot c : robots)
			if (c.isActive())
				if (attackBox.intersects(c.getHitbox())) {
					c.hurt(10);
					return;
				}
	}
	private void loadEnemyImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ROBOT_ATLAS);
		RobotArr = new BufferedImage[4][4];
		for (int j = 0; j < RobotArr.length; j++)
			for (int i = 0; i < RobotArr[j].length; i++)
				RobotArr[j][i] = temp.getSubimage(i * 64, j * 40, 64, 40);
	}
}
