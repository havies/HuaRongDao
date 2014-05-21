package edu.bjfu.klotski.UI;

public class ShowHelp {

	public static void ShowLayout(long n, int p)
	{
		String[][] map = new String[4][5];
		int[][] mark = new int[4][5];  // 用来判断下一个棋子应当放在什么位置的标记数组

		String[] line = {"+---+---+---+---+",
							"+---+---+---+---+",
							"+---+---+---+---+",
							"+---+---+---+---+",
							"+---+---+---+---+"};
		String[] split = {"||||","||||","||||","||||","||||"};
		
		long a = 7; // 二进制的 “111”
		long mask;
		int chessmanType, x, y;
		String leftMark, rightMark;

		//初始化MAP
		for(int i=0; i<4; i++)
			for(int j=0; j<5; j++)
				map[i][j] = "   ";

		for(int i = 0; i<20; i++)
		{
			mask = a<<(i*3);
			chessmanType = (int)((n & mask)>>(i*3));
			x = i%4;
			y = i/4;

			if(i == p)
			{
				leftMark = "(";
				rightMark = ")";
			}
			else
			{
				leftMark = " ";
				rightMark = " ";
			}

			switch(chessmanType)
			{
				case 0:	// 空格
					break;
				case 1: // 曹操
					MarkAPosition(x, y, map, mark, leftMark + "G" + rightMark, line, split);
					break;
				case 2: // 竖放
					MarkVPosition(x, y, map, mark, leftMark + "V" + rightMark, line, split);
					break;
				case 3: // 横放
					MarkHPosition(x, y, map, mark, leftMark + "H" + rightMark, line, split);
					break;
				case 4: // 横放
					MarkSPosition(x, y, map, mark, leftMark + "S" + rightMark, line, split);
					break;
			}
		}

		// 打印输出
		System.out.println("+---+---+---+---+");
		for(int j = 0; j<5; j++)
		{
			System.out.print("|");

			for(int i = 0; i<4; i++)
			{
				String s1=map[i][j];
				String s2=split[j];
			
				try
				{
					
					s2=s2.substring(i,i+1);
				}
				catch(Exception e)
				{
					//throw e;
				}
				
				System.out.print(s1 + s2);
			}

			System.out.println("\n" + line[j]);
		}

		return;
	}
	
	public static String ByteArrayToBinaryString(byte[] buff) 
	{ 
		StringBuilder sb = new StringBuilder(buff.length); 
		for(int i = buff.length-1; i >=0; i--) 
			sb.append(HexToBinary(buff[i]) + "-"); 

		sb.delete(sb.length() - 1, 1);
 
		return sb.toString();
	}
	
	private static String HexToBinary(byte b)
	{
		int a = (int)b;
		String s = "";
		while(a>=2)
		{
			s = a%2 + s;
			a = a/2;
		}
		s = "00000000" + a + s;
		return s.substring(s.length() - 8);
	}

	private static void MarkAPosition(int x, int y, String[][] map, int[][] mark, String c, String[] line, String[] split)
	{
		map[x][y] = c;
		map[x + 1][ y + 1] = c;
		map[x + 1][ y] = c;
		map[x][ y + 1] = c;

		mark[x][ y] = 1;
		mark[x + 1][ y + 1] = 1;
		mark[x + 1][ y] = 1;
		mark[x][ y + 1] = 1;

//		String s = line[y].Remove(4*x+1,7);
//		line[y] = s.insert(4*x+1,"       ");
//		s = split[y].Remove(x,1);
//		split[y] = s.Insert(x," ");
//		s = split[y+1].Remove(x,1);
//		split[y+1] = s.Insert(x," ");
		
		line[y] = line[y].substring(0, 4 * x + 1)+"       "+line[y].substring( 4 * x + 1 + 7);
		split[y] = split[y].substring(0, x)+" "+split[y].substring(x+1);
		split[y+1] = split[y+1].substring(0, x)+" "+split[y+1].substring(x+1);
	}

	private static void MarkHPosition(int x, int y, String[][] map, int[][] mark, String c, String[] line, String[] split)
	{
		map[x][y] = c;
		map[x + 1][ y] = c;

		mark[x][ y] = 1;
		mark[x + 1][ y] = 1;

//		string s = split[y].Remove(x,1);
//		split[y] = s.Insert(x," ");
		split[y] = split[y].substring(0, x)+" "+split[y].substring(x+1);
	}

	private static void MarkVPosition(int x, int y, String[][] map, int[][] mark, String c, String[] line, String[] split)
	{
		map[x][ y] = c;
		map[x][ y + 1] = c;

		mark[x][ y] = 1;
		mark[x][ y + 1] = 1;
//
//		string s = line[y].Remove(4*x+1,3);
//		line[y] = s.Insert(4*x+1,"   ");
		line[y] = line[y].substring(0, 4*x+1)+"   "+line[y].substring(4*x+1+3);
	}

	private static void MarkSPosition(int x, int y, String[][] map, int[][] mark, String c, String[] line, String[] split)
	{
		map[x][y] = c;

		mark[x][y] = 1;
	}

	private static void MarkBPosition(int x, int y, String[][] map, int[][] mark, String c, String[] line, String[] split)
	{
		map[x][y] = c;

		mark[x][y] = 1;
	}


	
	
}
