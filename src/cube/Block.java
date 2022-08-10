package cube;

public class Block {
	
	public int x, y, z;
	
	public static final int GRASS = 0;
	public static final int DIRT = 1;
	public static final int STONE = 2;
	public static final int TREEBARK = 3;
	public static final int TREELEAF = 4;
	
	
	public int type;
	
	public Block(int x, int y, int z, int type) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}
}
