// EPanel NCNU-LAB-114 LWJ 2014-07 -> 2015-08
//標頭檔import
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import javax.swing.border.*;

import java.text.*;
import java.util.*;
import java.text.DateFormat;

import javax.sound.sampled.*;

import java.net.*;
//開啟舊檔
import java.lang.*;

import javax.swing.filechooser.*;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.JOptionPane;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.imageio.*;

import ch.randelshofer.media.avi.AVIOutputStream;

import javax.imageio.ImageIO;

//結構Generation
class Generation_Initial
{
	int Amount;  //當代檔案數量
	int Particle[];

	double X[];  //座標X[粒子]
	double Y[];  //座標Y[粒子]

	int    S[];  //狀態[粒子]
	double R[];  //範圍[粒子]
	int    E[];  //記錄[粒子]
	int    U[];	 //更新半徑

	double Fitness[];  //適應值

	//初始化
	public Generation_Initial(){
		super();
	}
}
//主程式main
public class G14S1
{
	public static void main(String[] args) throws Exception
	{
		//更改UI介面 成Windows
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//SwingUtilities.updateComponentTreeUI(chooser);
		} catch(Exception try_e){try_e.printStackTrace();}
		//宣告視窗
		Show frame_Show = new Show();      //介面
		Show.frame_Player = new Player();  //播放器
		frame_Show.setVisible(true);
		Show.frame_Player.setVisible(false);
	}
}

//Show 介面class 主要的顯示畫面
class Show extends EPanel  implements ActionListener {

	//erace_tabulist
	double[] tabulist;
	
	//---------------------------主要設定區
	//版本號
	static String Version = "G14S1";
	//解空間顯示的  細緻度
	int midu=3;
	//設定數字格式
	//????//
	DecimalFormat df=new DecimalFormat("###0.0");
	//播放器視窗
	static Player frame_Player;

	//上限設定
	//why??? array
	int Image_T_MAX=44;                  //功能圖片數量
	static int Point_MAX=10000;          //粒子數量
	static int Generation_MAX=400000;     //世代數量
	static int Fitness_JLabel_MAX=10;	//左下灰框框

	//---------------------------視窗物件區
	//----------上方灰區域
	JPanel Header_JPanel;

	//滑鼠位置Fitness 與   座標
	JLabel Text_Fitness =new  JLabel("0.000000");
	JLabel Text_XY =new  JLabel("(0.00,0.00)");
	//明暗軸
	JSlider Slider_Transparency= new JSlider(0,200,100);
	JLabel bright= new JLabel();
	JLabel dark= new JLabel();

	//讀取按鈕
	static JButton Button_ReadFile=new JButton();
	//播放器按鈕
	//switch
	static JToggleButton Button_Player=new JToggleButton();
	//照相按鈕
	JButton Button_Camera=new JButton();
	//錄影按鈕
	static JToggleButton  Button_Video = new  JToggleButton();
	static int Video_mode=0;
	static int Video_x1,Video_x2,Video_y1,Video_y2;
	static AVIOutputStream Video_out_file;
	//FB連結按鈕
	JButton Button_Facebook=new JButton();

//	//重疊獨檔
	static JToggleButton Overlapping_ToggleButton2= new JToggleButton();
//
	
	//版本號
	JLabel Text_GS =new  JLabel("EPanel "+Show.Version);

	//方程式
	static JTextField Input_Formula = new  JTextField("0");
	

	
	JSplitPane Header_split;  //上方分隔線

	//----------中間區域
	//維度
	static float Dimension_Mode = 2;
	static int Funtion_Dimension = 2;
	//1Dimension
	static double Resolution = 0.01;
	static int Resolution_flag =0 ;
	static int Resolution_count = 0;
	JButton Button_Borderchange = new JButton();
	//N維模式
	JPanel Middle_JPanel;
	//外框輪廓
	static JLabel Outline_JLabel;
	BufferedImage Outline_Image;
	//解空間顏色
	static JLabel Answer_JLabel;
	BufferedImage Answer_Image;
	static int Answer_Change=1;
	//橡皮擦
	JLabel Eraser_JLabel;
	BufferedImage Eraser_Image;
	//進度軸
	JLabel Progress_JLabel;
	BufferedImage Progress_Image;
	static int Progress_Change=1;
	static int int_Generation_Change=1;
	//進度軸按鈕
/*	static JTextField GoTo_Generation = new JTextField();
	JButton GoTo_Button = new JButton();
	JButton GoTo_Confirm = new JButton();
*/
	//世代數輸入
	JTextField Scan_Generation = new JTextField();
	JButton Button_Confirm = new JButton();
	static JLabel Progress_Unit = new JLabel();
	
	//xy軸
	static JLabel x_axis = new JLabel();
	static JLabel y_axis = new JLabel();
	
	
	//----------播放控制區域
	JLabel Label_temp = new JLabel();

	int Index_x1,Index_x2=0;
	//圓圓的那個
	JLabel Label_Index = new JLabel();
	int Int_Index = 2;

	JPanel Bottom_JPanel;
	static JButton Button_Pause = new JButton();
	static JButton Button_Stop = new JButton();
	static JButton Button_Accelerate = new JButton();
	static JButton Button_Decelerate = new JButton();
	//1 -> 2 -> 4 -> .....64
	static int int_Speed = 1 ;
	static JLabel Text_Speed = new JLabel();
	static JLabel Label_Speed = new JLabel();
	
	static JButton Button_Readfile = new JButton();
	static JButton Button_Recover = new JButton();
	static JToggleButton Eraser_ToggleButton= new JToggleButton();
	static int Eraser_pick = 0;
	static JToggleButton Mist_ToggleButton= new JToggleButton();
	
	//進度軸按鈕
	static JTextField GoTo_Generation = new JTextField();
	static JButton GoTo_Button = new JButton();
	static JButton GoTo_Confirm = new JButton();
	

	
	//----------右邊區域

	//自動調整顏色按鈕
	JToggleButton  Color_ToggleButton=new JToggleButton();
	//顏色上下限設定
	static JTextField Input_Color_Caps= new  JTextField("100");
	static JTextField Input_Color_Limit= new  JTextField("0");
	
	static JLabel Fitness_legend = new JLabel("Fitness");


	//---------------------------變數宣告區

	//世代數
	static JLabel Point_Label[];
	static Generation_Initial Generation[];
	static Generation_Initial temp_Generation[];
	static int Now_Generation=1;
	static int Total_Generation=1;

	//最佳解
	static JLabel Fitness_JLabel[];
	static String Fitness_String[];
	static JLabel Generation_JLabel[];
	static int Fitness_Amount;  //適應值數量
	static int Fitness_JLabel_len=14;
	//static BufferedImage[] Fitness_Img = new BufferedImage[10];
	//static BufferedImage[] History_Img;
	Icon Fitness_Img;
	

	//粒子Fitness(下方檔名區)
	JLabel PFitness_JLabel = new JLabel();
	String PFitness_String = new String();
	
	

	//怪怪的地方
	//顏色對照工具輔助值
	//Color顏色比重                                 藍        青        綠        黃        紅        深紅(黑)
	int Color_Proportion[]={ 40, 40, 30, 20, 60,40}; 
	int Color_P_Code[]=    {  0,210,245,255,255,70};
	static double Color_Caps=100;	//上界
	static double Color_Limit=0;	//下界

	//圖片
	static ImageIcon T[]=new ImageIcon[101];
	static ImageIcon TP[]=new ImageIcon[101];
	static ImageIcon TE[]=new ImageIcon[101];

	//播放
	static int Round=0;
	static int Speed=1; //倍率
	static int Pause=2;

	//視窗輔助值
	int Outside_X=5;
	int Outside_Y=0;
	int Spacing=30;  //間隔區

	//中間區域起點(x,y) 寬長(w,l)
	int M_block_x=Outside_X+Spacing;
	int M_block_y=Outside_Y+Spacing;
	int M_block_w=700;
	int M_block_l=700;

	//右邊區域起點(x,y) 寬長(w,l)
	int R_block_x=M_block_x+M_block_w+Spacing;
	int R_block_y=M_block_y;
	int R_block_w=35;
	int R_block_l=700;

	//外圍?
	int Outline_x=R_block_x+R_block_w+Spacing-Outside_X+15;
	int Outline_y=R_block_y+R_block_l+Spacing-Outside_Y;

	//地圖輔助值
	static double Border_X=0;
	static double Border_Y=0;
	static double Border_L=100;

	static double Border_X_S=100;
	static double Border_X_M=100;
	static double Border_X_N=-100;

	static double Border_Y_S=100;
	static double Border_Y_M=100;
	static double Border_Y_N=-100;
	static double Border_Y_L=100;
	//---------------------------其他宣告區

	static  double Postfix[]=new double[100]; //後置運算式   輸出時的後置int運算   Postfix[0]=Postfix陣列大小   -1～-7為運算元 -8為X -9為Y

	int MouseMod=0;   //滑鼠點擊判斷
	int  MouseXago,MouseYago;

	long temp_time=0; //滑鼠連點兩下判斷用

	int Show_Title=1; //開頭EPanel文字的旗標

	static int JFrame_mod=0; //判斷當前是哪個JFrame


	//------------------------------------------------------------------主視窗程式碼
	Show()  {

		//一開始畫面的背景
		Expand_Equation("sum(Xi^2-10*cos(2*Pi*Xi)+10)/40",Postfix);
		//Expand_Equation("(sum(X[i]^2+X[i+1]^2))^0.48*((sin(50*(X[i]^2+X[i+1]^2)^0.1))^2+1)",Postfix);
		//Expand_Equation("sum(Xi^2-10*prod(2*Pi*Xi))",Postfix);

		//世代宣告
		Generation = new Generation_Initial[Generation_MAX];
		temp_Generation = new Generation_Initial[Generation_MAX];

		//---------------------------圖片匯入
		DecimalFormat dfT=new DecimalFormat("00");
		//按鈕圖片匯入   全從內部
		for(int i=0;i<=Image_T_MAX;i++)
			T[i]=new ImageIcon(this.getClass().getResource("T/t"+dfT.format(i)+".png"));

		for(int i=0;i<=100;i++)
		{
			//粒子圖片   先從外部匯入
			TP[i]=new ImageIcon("T/P"+dfT.format(i)+".png");
			if(TP[i].getIconHeight()<=0 && i<20)  //少於20個圖片   從內部匯入
			{
				TP[i]=new ImageIcon(this.getClass().getResource("T/P"+dfT.format(i)+".png"));
			}
			//軌跡圖片  先從外部匯入
			TE[i]=new ImageIcon("T/E"+dfT.format(i)+".png");
			if(TE[i].getIconHeight()<=0 && i<1)  //少於1個圖片   從內部匯入
			{
				TE[i]=new ImageIcon(this.getClass().getResource("T/E"+dfT.format(i)+".png"));
			}
		}

		//---------------------------上方區域

		//滑鼠位置    輔助用Fitness值
		Text_Fitness.setBounds(140,0,120,30);
		Text_Fitness.setFont(new Font("微軟正黑體",Font.BOLD,18));
		//Text_Fitness.setToolTipText("滑鼠位置Fitness值");
		//滑鼠位置    輔助用座標值
		Text_XY.setBounds(10,0,300,30);
		Text_XY.setFont(new Font("微軟正黑體",Font.BOLD,18));
		Text_XY.setToolTipText("Position & Fitness of Mouse Cursor");

		//明暗軸  透明度控制條
		Slider_Transparency = new JSlider(0,200,100);
		//Slider_Transparency.setToolTipText("解空間透明度調整控制器");
		Slider_Transparency.setBounds(Outline_x-540,0,160,35);
		Slider_Transparency.setMajorTickSpacing(100);
		Slider_Transparency.setMinorTickSpacing(25);
		Slider_Transparency.setSnapToTicks(true);
		Slider_Transparency.setPaintLabels(true);
		Slider_Transparency.addMouseListener(MyMouseAdapter);
		Slider_Transparency.addMouseMotionListener(MyMouseAdapter);
		Slider_Transparency.addKeyListener(MyKeyAdapter);
		Slider_Transparency.setBackground(new Color(180,180,180));
		Slider_Transparency.setPaintTicks(true);
		//Slider_Transparency.setPaintTicks(false);
		//明暗軸  數值名稱化
		Hashtable<Integer,JLabel>table = new Hashtable<Integer,JLabel>();
		/*JLabel reds = new JLabel();
		reds.setIcon(T[28]);
		table.put(new Integer(0),reds);
		table.put(new Integer(0), new JLabel("dark"));
		table.put(new Integer(100), new JLabel(""));
		table.put(new Integer(200), new JLabel("bright"));
		*/table.put(new Integer(0), new JLabel(""));
		table.put(new Integer(100), new JLabel(""));
		table.put(new Integer(200), new JLabel(""));
		Slider_Transparency.setLabelTable(table);
		
		bright.setBounds(Outline_x-380,3,30,30);
		bright.setIcon(T[28]);
		dark.setBounds(Outline_x-565,3,30,30);
		dark.setIcon(T[27]);
		//視窗按鈕
		//錄影
		Button_Video= new JToggleButton(T[30]);
		Button_Video.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Video.setBounds(Outline_x-330,0,55,35);
		Button_Video.setToolTipText("Record");
		Button_Video.addMouseListener(MyMouseAdapter);
		Button_Video.addKeyListener(MyKeyAdapter);
		Button_Video.setBackground(new Color(180,180,180));
		//拍照
		Button_Camera= new JButton(T[29]);
		Button_Camera.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Camera.setBounds(Outline_x-276,0,35,35);
		Button_Camera.setToolTipText("Print Screen");
		Button_Camera.addMouseListener(MyMouseAdapter);
		Button_Camera.addKeyListener(MyKeyAdapter);
		Button_Camera.setBackground(new Color(180,180,180));
		//播放器
		/*Button_Player= new JToggleButton(T[11]);
		Button_Player.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Player.setBounds(Outline_x-215,0,35,35);
		Button_Player.setToolTipText("演化計算播放器");
		Button_Player.addMouseListener(MyMouseAdapter);
		Button_Player.addKeyListener(MyKeyAdapter);
		Button_Player.setBackground(new Color(180,180,180));*/
		//讀取按鈕
		Button_ReadFile.setIcon(T[5]);
		Button_ReadFile.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_ReadFile.setBounds(Outline_x-241,0,35,35);
		Button_ReadFile.setToolTipText("Read File");
		Button_ReadFile.addMouseListener(MyMouseAdapter);
		Button_ReadFile.addKeyListener(MyKeyAdapter);
		Button_ReadFile.setBackground(new Color(180,180,180));

//
		//重疊讀檔
		Overlapping_ToggleButton2= new JToggleButton(Show.T[16]);
		Overlapping_ToggleButton2.setToolTipText("Compare Files");
		Overlapping_ToggleButton2.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Overlapping_ToggleButton2.addMouseListener(MyMouseAdapter);
		Overlapping_ToggleButton2.setBounds(Outline_x-206,0,35,35);
//
		
		//FB連結
		Button_Facebook= new JButton(T[13]);
		Button_Facebook.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Facebook.setBounds(Outline_x-171,0,35,35);
		Button_Facebook.setToolTipText("EPanel Fans Club");
		Button_Facebook.addMouseListener(MyMouseAdapter);
		Button_Facebook.addKeyListener(MyKeyAdapter);
		Button_Facebook.setBackground(new Color(180,180,180));

		//版本號
		Text_GS.setBounds(Outline_x-125,0,140,35);
		Text_GS.setFont(new Font("SansSerif",Font.BOLD,18));
		//Text_GS.setToolTipText("目前軟體版本");
		
		

		Input_Formula.setBounds(50,35,700,32);
		Input_Formula.setToolTipText("Formula");
		Input_Formula.setFont(new Font("微軟正黑體",Font.BOLD,14));
		Input_Formula.setHorizontalAlignment(JTextField.CENTER);
		Input_Formula.setBorder(BorderFactory.createEtchedBorder());
		add(Input_Formula);

		

		
		
		
		
		//---------------------------中間區域
		
		//外框輪廓(白框框)
		Outline_Image=new BufferedImage(Outline_x,Outline_y,BufferedImage.TYPE_INT_RGB);
		Outline_Panel();
		Outline_JLabel=new JLabel(new ImageIcon(Outline_Image));
		Outline_JLabel.setBounds(0,0,Outline_x,Outline_y);
		Outline_JLabel.addMouseListener(MyMouseAdapter);
		Outline_JLabel.addMouseWheelListener(MyMouseAdapter);
		Outline_JLabel.addMouseMotionListener(MyMouseAdapter);
		Outline_JLabel.addKeyListener(MyKeyAdapter);
		Outline_JLabel.setLayout(null);
		//粒子上Fitness
		PFitness_String="";
		PFitness_JLabel.setSize(240,34);
		PFitness_JLabel.addMouseListener(MyMouseAdapter);
		PFitness_JLabel.setFont(new Font("微軟正黑體",Font.BOLD,24));
		PFitness_JLabel.setForeground(new Color(255,255,255));
		PFitness_JLabel.setBackground(new Color(0,0,0,100));
		PFitness_JLabel.setOpaque(true);
		PFitness_JLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PFitness_JLabel.setLayout(null);
		PFitness_JLabel.setVisible(false);
		//解空間
		Answer_Image=new BufferedImage(M_block_w,M_block_l,BufferedImage.TYPE_INT_RGB);
		Answer_Panel();
		Answer_JLabel=new JLabel(new ImageIcon(Answer_Image));
		Answer_JLabel.setBounds(M_block_x+2,M_block_y+2,M_block_w-3,M_block_l-3);
		Answer_JLabel.addMouseListener(MyMouseAdapter);
		Answer_JLabel.addMouseWheelListener(MyMouseAdapter);
		Answer_JLabel.addMouseMotionListener(MyMouseAdapter);
		Answer_JLabel.addKeyListener(MyKeyAdapter);
		Answer_JLabel.setLayout(null);
		//解空間  拖移讀檔
		Answer_JLabel.setDropTarget(new DropTarget() {
			public void drop(DropTargetDropEvent e) {
				Show_Title=0;

				e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				try {
					File now_file;
					List droppedFiles = (List) e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					now_file=(File)droppedFiles.get(0);
					//System.out.print(file.getAbsolutePath());
					//Player.RF(file);

					String SRF_now=now_file.getName();
					if(SRF_now.toLowerCase().endsWith("epin"))
					{
						Player.Player_file=now_file;
						Reset_Panel();

						frame_Player.setVisible(true);

						JFrame_mod=1;
						Input_Color_Caps.setText("100");
						Input_Color_Limit.setText("0");
						
//for bar						GoTo_Button.setVisible(true);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		//解空間上  粒子宣告
		Point_Label = new JLabel[Point_MAX];
		for(int i=0;i<Point_MAX;i++)
		{
			Point_Label[i]=new JLabel();
			Point_Label[i].setVisible(false);
			Point_Label[i].addMouseListener(MyMouseAdapter);
			Answer_JLabel.add(Point_Label[i]);
			
		}
//		System.out.println(Point_MAX);
		//解空間上  Fitness顯示
		Fitness_String = new String[Fitness_JLabel_MAX];
		Fitness_JLabel = new JLabel[Fitness_JLabel_MAX];
		Generation_JLabel = new JLabel[Fitness_JLabel_MAX];
		for(int i=0;i<Fitness_JLabel_MAX;i++)
		{
			Fitness_String[i]="";
			Fitness_JLabel[i]=new JLabel();
			Fitness_JLabel[i].setBounds(M_block_x+2,M_block_y+M_block_l-35-36*i,240,34);
			Fitness_JLabel[i].addMouseListener(MyMouseAdapter);
			Fitness_JLabel[i].addMouseWheelListener(MyMouseAdapter);
			Fitness_JLabel[i].addMouseMotionListener(MyMouseAdapter);
			Fitness_JLabel[i].addKeyListener(MyKeyAdapter);
			Fitness_JLabel[i].setFont(new Font("微軟正黑體",Font.BOLD,24));
			//Fitness_JLabel[i].setFont(new Font("IrisUPC", Font.BOLD,40));
			Fitness_JLabel[i].setForeground(new Color(255,255,255));
			Fitness_JLabel[i].setBackground(new Color(0,0,0,100));
			Fitness_JLabel[i].setOpaque(true);
			//Fitness_JLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			Fitness_JLabel[i].setLayout(null);
			Fitness_JLabel[i].setVisible(false);
			
			
			Fitness_JLabel[i].setIcon(TP[8]);
			Fitness_JLabel[i].setVerticalTextPosition(JLabel.CENTER);
			Fitness_JLabel[i].setHorizontalTextPosition(JLabel.RIGHT);
			
//			System.out.println("*"+i+"*");
		}
		
		for(int i=0;i<Fitness_JLabel_MAX;i++)
		{
//			Fitness_String[i]="";
			Generation_JLabel[i]=new JLabel();
			Generation_JLabel[i].setBounds(M_block_x+2+300,M_block_y+M_block_l-35-36*i,240,34);
			Generation_JLabel[i].addMouseListener(MyMouseAdapter);
			Generation_JLabel[i].addMouseWheelListener(MyMouseAdapter);
			Generation_JLabel[i].addMouseMotionListener(MyMouseAdapter);
			Generation_JLabel[i].addKeyListener(MyKeyAdapter);
			Generation_JLabel[i].setFont(new Font("微軟正黑體",Font.BOLD,24));
			//Fitness_JLabel[i].setFont(new Font("IrisUPC", Font.BOLD,40));
			Generation_JLabel[i].setForeground(new Color(255,255,255));
			Generation_JLabel[i].setBackground(new Color(0,0,0,100));
			Generation_JLabel[i].setOpaque(true);
			//Fitness_JLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			Generation_JLabel[i].setLayout(null);
			Generation_JLabel[i].setVisible(false);
			
			
			Generation_JLabel[i].setIcon(TP[8]);
			Generation_JLabel[i].setVerticalTextPosition(JLabel.CENTER);
			Generation_JLabel[i].setHorizontalTextPosition(JLabel.RIGHT);
			
//			System.out.println("*"+i+"*");
		}
		
		
		//檔名的圖
		//Fitness_Img = new BufferedImage[10];
//		Fitness_Img = new Icon[10];
		Fitness_Img = TP[8];

		//進度軸
		Progress_Image=new BufferedImage(M_block_w+3,R_block_w,BufferedImage.TYPE_INT_RGB);
		Progress_Panel();
		Progress_JLabel=new JLabel(new ImageIcon(Progress_Image));
		Progress_JLabel.setBounds(M_block_x-1,M_block_y+M_block_l-1,M_block_w+3,R_block_w);
		Progress_JLabel.addMouseListener(MyMouseAdapter);
		Progress_JLabel.addMouseWheelListener(MyMouseAdapter);
		Progress_JLabel.addMouseMotionListener(MyMouseAdapter);
		Progress_JLabel.addKeyListener(MyKeyAdapter);
		Progress_JLabel.setLayout(null);

/*for bar		
		GoTo_Button= new JButton("GOTO");
		GoTo_Button.setFont(new Font("微軟正黑體",Font.BOLD,12));
		//Color_ToggleButton.setToolTipText("自動調整顏色");
		GoTo_Button.setVisible(false);
		GoTo_Button.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		GoTo_Button.addMouseListener(MyMouseAdapter);
		GoTo_Button.addKeyListener(MyKeyAdapter);
		GoTo_Button.setBounds(680,812,50,20);
		add(GoTo_Button);
*/		
		//世代數輸入
		Scan_Generation.setSize(100, 22);
		Scan_Generation.setVisible(false);
		Scan_Generation.setFont(new Font("微軟正黑體",Font.BOLD,16));
		Scan_Generation.addMouseListener(MyMouseAdapter);
		Scan_Generation.addMouseMotionListener(MyMouseAdapter);
		Scan_Generation.addKeyListener(MyKeyAdapter);
		add(Scan_Generation);


		Button_Confirm.setSize(22, 22);
		Button_Confirm.setIcon(T[36]);
		Button_Confirm.setVisible(false);
		Button_Confirm.addMouseListener(MyMouseAdapter);
		Button_Confirm.addMouseMotionListener(MyMouseAdapter);
		Button_Confirm.addKeyListener(MyKeyAdapter);
		add(Button_Confirm);
		
		//xy軸的字
		x_axis.setFont(new Font("微軟正黑體",Font.BOLD,12));
		x_axis.setText("x1");
//		x_axis.setText("x");
		x_axis.setBounds(25,77,15,15);
//		x_axis.setBounds(750,78,15,15);
		add(x_axis);
		y_axis.setFont(new Font("微軟正黑體",Font.BOLD,12));
		y_axis.setText("x2");
//		y_axis.setText("f(x)");
		y_axis.setBounds(3,79,30,25);
//		y_axis.setBounds(5,830,30,25);
		add(y_axis);
		
		//進度軸單位
		Progress_Unit.setBounds(645,805,140,35);
		Progress_Unit.setFont(new Font("SansSerif",Font.BOLD,12));
		//Progress_Unit.setText("unit:generation");
		add(Progress_Unit);
		
		
////	//---------------------------播放控制區域
		Label_Index.setBounds(224,Outline_y+85,32,32);
		Label_Index.setIcon(T[40]);
		Label_Index.setVisible(true);
		Label_Index.addMouseListener(MyMouseAdapter);
		Label_Index.addMouseMotionListener(MyMouseAdapter);
		add(Label_Index);
		add(Label_temp);

		Mist_ToggleButton.setBounds(0,0,32,32);
		Mist_ToggleButton.setIcon(T[43]);
		Mist_ToggleButton.setSelected(true);
		Mist_ToggleButton.setVisible(true);
		Mist_ToggleButton.addMouseListener(MyMouseAdapter);
		Mist_ToggleButton.addKeyListener(MyKeyAdapter);

		Button_Stop.setBounds(32,0,32,32);
		Button_Stop.setIcon(T[1]);
		Button_Stop.setVisible(true);
		Button_Stop.addMouseListener(MyMouseAdapter);
		Button_Stop.addKeyListener(MyKeyAdapter);

		Button_Pause.setBounds(64,0,32,32);
		Button_Pause.setIcon(T[2]);
		Button_Pause.setVisible(true);
		Button_Pause.addMouseListener(MyMouseAdapter);
		Button_Pause.addKeyListener(MyKeyAdapter);

		Button_Decelerate.setBounds(96,0,32,32);
		Button_Decelerate.setIcon(T[37]);
		Button_Decelerate.setVisible(true);
		Button_Decelerate.addMouseListener(MyMouseAdapter);
		Button_Decelerate.addKeyListener(MyKeyAdapter);

		Button_Accelerate.setBounds(128,0,32,32);
		Button_Accelerate.setIcon(T[4]);
		Button_Accelerate.setVisible(true);
		Button_Accelerate.addMouseListener(MyMouseAdapter);
		Button_Accelerate.addKeyListener(MyKeyAdapter);

		Text_Speed.setBounds(160,0,32,32);
		Text_Speed.setText("1");
		Text_Speed.setFont(new Font("微軟正黑體",Font.BOLD,14));
		Text_Speed.setHorizontalAlignment(JLabel.CENTER);
		Text_Speed.setVisible(true);

		Label_Speed.setBounds(160,0,32,32);
		Label_Speed.setIcon(T[9]);
		Label_Speed.setVisible(true);

		Eraser_ToggleButton.setBounds(192,0,32,32);
		Eraser_ToggleButton.setIcon(T[23]);
		Eraser_ToggleButton.setVisible(true);
		Eraser_ToggleButton.addMouseListener(MyMouseAdapter);
		Eraser_ToggleButton.addKeyListener(MyKeyAdapter);

		Button_Recover.setBounds(224,0,32,32);
		Button_Recover.setIcon(T[14]);
		Button_Recover.setVisible(true);
		Button_Recover.addMouseListener(MyMouseAdapter);
		Button_Recover.addKeyListener(MyKeyAdapter);
		
		//Borderchage
/*		Button_Borderchange.setBounds(256,0,32,32);
		Button_Borderchange.setIcon(T[44]);
		Button_Borderchange.setVisible(false);
		Button_Borderchange.addMouseListener(MyMouseAdapter);
		Button_Borderchange.addMouseMotionListener(MyMouseAdapter);
		Button_Borderchange.addMouseWheelListener(MyMouseAdapter);
		Button_Borderchange.addKeyListener(MyKeyAdapter);
*/		
		
		
//for player		
		GoTo_Button.setBounds(256,0,32,32);
		GoTo_Button.setIcon(T[22]);
		GoTo_Button.setVisible(true);
		GoTo_Button.addMouseListener(MyMouseAdapter);
		GoTo_Button.addMouseMotionListener(MyMouseAdapter);
		GoTo_Button.addKeyListener(MyKeyAdapter);

//		add(GoTo_Button);
		
		
		GoTo_Generation.setBounds(256,0,50,32);
		GoTo_Generation.setVisible(false);
		GoTo_Generation.setFont(new Font("微軟正黑體",Font.BOLD,16));
		GoTo_Generation.addMouseListener(MyMouseAdapter);
		GoTo_Generation.addMouseMotionListener(MyMouseAdapter);
		GoTo_Generation.addKeyListener(MyKeyAdapter);
		GoTo_Generation.setText("0");
		GoTo_Generation.setEditable(true);

//		add(GoTo_Generation);
		
		GoTo_Confirm.setBounds(306,0,32,32);
		GoTo_Confirm.setIcon(T[22]);
		GoTo_Confirm.setVisible(false);
		GoTo_Confirm.addMouseListener(MyMouseAdapter);
		GoTo_Confirm.addMouseMotionListener(MyMouseAdapter);
		GoTo_Confirm.addKeyListener(MyKeyAdapter);

//		add(GoTo_Confirm);

		
		

		//---------------------------右方區域
		//顏色自動按鍵
		Color_ToggleButton= new JToggleButton("AUTO");
		Color_ToggleButton.setFont(new Font("微軟正黑體",Font.BOLD,10));
		//Color_ToggleButton.setToolTipText("自動調整顏色");
		Color_ToggleButton.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Color_ToggleButton.addMouseListener(MyMouseAdapter);
		Color_ToggleButton.addKeyListener(MyKeyAdapter);
		Color_ToggleButton.setBounds(R_block_x+R_block_w,R_block_y+50,40,20);
		add(Color_ToggleButton);

		//顏色上限設定
		Input_Color_Caps.setBounds(R_block_x+R_block_w,R_block_y+30,40,20);
		Input_Color_Caps.setFont(new Font("微軟正黑體",Font.BOLD,12));
		Input_Color_Caps.setHorizontalAlignment(JTextField.CENTER);
		//Input_Color_Caps.setToolTipText("顏色上限設定");
		Input_Color_Caps.setEditable(false);
		Input_Color_Caps.addMouseListener(MyMouseAdapter);
		Input_Color_Caps.addKeyListener(MyKeyAdapter);
		//add(Input_Color_Caps);
		//顏色下限設定
		Input_Color_Limit.setBounds(R_block_x+R_block_w,R_block_y+R_block_l+30,40,20);
		Input_Color_Limit.setFont(new Font("微軟正黑體",Font.BOLD,12));
		Input_Color_Limit.setHorizontalAlignment(JTextField.CENTER);
		//Input_Color_Limit.setToolTipText("顏色下限設定");
		Input_Color_Limit.setEditable(false);
		Input_Color_Limit.addMouseListener(MyMouseAdapter);
		Input_Color_Limit.addKeyListener(MyKeyAdapter);
		//add(Input_Color_Limit);
		
		Fitness_legend.setFont(new Font("微軟正黑體",Font.BOLD,12));
		Fitness_legend.setBounds(R_block_x-2,R_block_y+R_block_l+75,140,35);
		add(Fitness_legend);
		
		
		System.out.println(R_block_x);
		System.out.println(R_block_w);
		System.out.println(R_block_y);
		System.out.println(R_block_l);
		

		//---------------------------物件放入 畫布
		//上面畫布
		Header_JPanel=new JPanel();
		Header_JPanel.setSize(Outline_x+8,67);
		Header_JPanel.setLayout(null);
		Header_JPanel.setBackground(new Color(180,180,180));
		Header_JPanel.add(Text_GS);
		Header_JPanel.add(Button_Video);
		Header_JPanel.add(Button_Camera);
		//Header_JPanel.add(Button_Player);
		Header_JPanel.add(Button_ReadFile);
//		
		Header_JPanel.add(Overlapping_ToggleButton2);
//
		Header_JPanel.add(Button_Facebook);
		Header_JPanel.add(Slider_Transparency);
		Header_JPanel.add(Text_Fitness);
		Header_JPanel.add(Text_XY);
		Header_JPanel.add(Input_Formula);
		
		Header_JPanel.add(bright);
		Header_JPanel.add(dark);

//		//播放控制畫布
		Bottom_JPanel=new JPanel();
		Bottom_JPanel.setSize(338,32);
		Bottom_JPanel.setLocation(256,Outline_y+85);
		Bottom_JPanel.setLayout(null);
		Bottom_JPanel.add(Mist_ToggleButton);
		Bottom_JPanel.add(Button_Stop);
		Bottom_JPanel.add(Button_Pause);
		Bottom_JPanel.add(Button_Accelerate);
		Bottom_JPanel.add(Button_Decelerate);
		Bottom_JPanel.add(Text_Speed);
		Bottom_JPanel.add(Label_Speed);
		Bottom_JPanel.add(Eraser_ToggleButton);
		Bottom_JPanel.add(Button_Recover);
		Bottom_JPanel.add(Button_Borderchange);
		Bottom_JPanel.add(GoTo_Button);
		Bottom_JPanel.add(GoTo_Generation);
		Bottom_JPanel.add(GoTo_Confirm);
		Bottom_JPanel.setVisible(true);
		add(Bottom_JPanel);

		//中間畫布
		Middle_JPanel=new JPanel();
		Middle_JPanel.setSize(Outline_x+8,Outline_y+95);
		Middle_JPanel.setLayout(null);
		for(int i=0;i<Fitness_JLabel_MAX;i++)
		{
			//Fitness_JLabel[i].add(new JLabel(Fitness_Img, JLabel.LEFT));
			Middle_JPanel.add(Fitness_JLabel[i]);
			Middle_JPanel.add(Generation_JLabel[i]);
			//Middle_JPanel.add(Fitness_Img[i]);
		}
		Middle_JPanel.add(Progress_JLabel);
		Middle_JPanel.add(Answer_JLabel);
		Middle_JPanel.add(Outline_JLabel);
		Middle_JPanel.setBackground(Color.WHITE);
		add(PFitness_JLabel);

		//Middle_JPanel.add(new JLabel(Fitness_Img, JLabel.CENTER));
		
		//視窗配置    ((只能放在這裡))
		setLayout(new BorderLayout());

		//分隔線 -----------------上中兩個區域
		Header_split=new JSplitPane(JSplitPane.VERTICAL_SPLIT,false,Header_JPanel,Middle_JPanel);
		Header_split.setDividerLocation(70);
		Header_split.setEnabled(false);
		add(Header_split, BorderLayout.CENTER);


		//計時器 0.005s
		Timer timer = new Timer(5,this);
		timer.start();

		//視窗設定
		setResizable(false);
		setIconImage(T[10].getImage()); //icon設定
		setBounds(0,0,Outline_x+8,Outline_y+150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("EPanel "+Show.Version);
		addWindowListener(MyWindowListener);
		addKeyListener(MyKeyAdapter);
		addMouseListener(MyMouseAdapter);

		Answer_JLabel.requestFocus();
		
		

	}
	//------------------------------------------------------------------計時器
	public void actionPerformed(ActionEvent e) {
		//解空間重畫
		if(Answer_Change==1)
		{
			//自動調整顏色
			if(Color_ToggleButton.isSelected())
				Adaptive_Color();

			if(MouseMod==5)
			{
				midu=3;
			}
			Outline_Panel();
			Answer_Panel();
			if(!Mist_ToggleButton.isSelected()&&Show_Title==0)
			{Mist_Panel();}
			Eraser_Panel(1);

			repaint();
			Answer_Change=0;

			if(Total_Generation>1)
			{
				for(int i=Generation[Now_Generation].Amount;i<Point_MAX;i++)
				{
					Point_Label[i].setVisible(false);
				}
			}
			
//			System.out.println(Generation[Now_Generation].Amount);
			
			
			//暫停中
			if(Pause==1 && Total_Generation>1)
			{
				for(int i=0;i<Generation[Now_Generation].Amount;i++)
				{
					Point_Label[i].setVisible(true);
					int T_temp=Generation[Now_Generation].S[i];
					Point_Label[i].setIcon(TP[T_temp]);
					Point_Label[i].setSize(TP[T_temp].getIconWidth(),TP[T_temp].getIconHeight());
					int tempx=(int)((Generation[Now_Generation].X[i]-Border_X+Border_L)*350/Border_L)-Point_Label[i].getWidth()/2;
					int tempy=0;
					if(Dimension_Mode==1)
					{
						tempy=700-(int)((Generation[Now_Generation].Fitness[i]-Border_Y+Border_Y_L)*350/Border_Y_L)-Point_Label[i].getHeight()/2;
					}
					else if(Dimension_Mode==2)
					{
						tempy=700-(int)((Generation[Now_Generation].Y[i]-Border_Y+Border_L)*350/Border_L)-Point_Label[i].getHeight()/2;
					}
					Point_Label[i].setLocation(tempx,tempy);
				}
			}
		}

		//播放中
		if(Pause==0)
		{
			//Answer_Change=1;
			if(Show.Now_Generation<Show.Total_Generation)
			{
				//進入下一個世代
				if(Round>=4096){

					Round=0;
					double temp;

					Now_Generation++;

					Progress_Change=1;

					Player.Slider_Generation.setValue(Now_Generation);
					Eraser_Panel(0);

					for(int i=0;i<Point_MAX;i++)
					{
						Point_Label[i].setVisible(false);
					}
					for(int i=0;i<Generation[Now_Generation].Amount;i++)
					{
						Point_Label[i].setVisible(true);
						int T_temp=Generation[Now_Generation].S[i];
						Point_Label[i].setIcon(TP[T_temp]);
						Point_Label[i].setSize(TP[T_temp].getIconWidth(),TP[T_temp].getIconHeight());

					}
					if(!Mist_ToggleButton.isSelected())
					{
						Mist_Panel();
					}
				}

				for(int i=0;i<Generation[Now_Generation].Amount;i++)
				{
					if(Show.Now_Generation<Show.Total_Generation&&Dimension_Mode==1)
					{
						double tX=Generation[Now_Generation].X[i];
						double tY=Generation[Now_Generation].Fitness[i];
						if(i<Generation[Now_Generation+1].Amount)
						{
							tX+=(Generation[Now_Generation+1].X[i]-tX)*Round/4096;
							//tY+=(Generation[Now_Generation+1].Fitness[i]-tY)*Round/4096;
						}
						tY=Fitness(tX,0,0);

						int tempx=(int)((tX-Border_X+Border_L)*350/Border_L)-Point_Label[i].getWidth()/2;
						int tempy=700-(int)((tY-Border_Y+Border_Y_L)*350/Border_Y_L)-Point_Label[i].getHeight()/2;
						
						//System.out.println("tx = "+tX+" txFit. = "+Fitness(tX,0,0)+" x = "+tempx+" xFit. = "+Fitness(tempx,0,1)+" Fit. = "+Generation[Now_Generation].Fitness[i]+" y = "+tempy+" ty = "+tY);
						//System.out.println("ty = "+tY+" tempy = "+tempy);
						Point_Label[i].setLocation(tempx,tempy);

					}
					else if(Show.Now_Generation<Show.Total_Generation&&Dimension_Mode==2)
					{
						double tX=Generation[Now_Generation].X[i];
						double tY=Generation[Now_Generation].Y[i];
						if(i<Generation[Now_Generation+1].Amount)
						{
							tX+=(Generation[Now_Generation+1].X[i]-tX)*Round/4096;
							tY+=(Generation[Now_Generation+1].Y[i]-tY)*Round/4096;
						}

						int tempx=(int)((tX-Border_X+Border_L)*350/Border_L)-Point_Label[i].getWidth()/2;
						int tempy=700-(int)((tY-Border_Y+Border_L)*350/Border_L)-Point_Label[i].getHeight()/2;

						Point_Label[i].setLocation(tempx,tempy);
					}
				}
				Round+=Speed;

				
//				System.out.println("*"+Generation[Now_Generation].Amount);
				
			}
			else
			{
				Show.Pause=1;
				Player.Button_Start.setIcon(Show.T[2]);
				Button_Pause.setIcon(Show.T[2]);
				for(int i=0;i<Generation[Now_Generation].Amount;i++)
				{
//					System.out.println(Generation[Now_Generation].Amount);
					
					Point_Label[i].setVisible(true);
					int T_temp=Generation[Now_Generation].S[i];
					Point_Label[i].setIcon(TP[T_temp]);
					Point_Label[i].setSize(TP[T_temp].getIconWidth(),TP[T_temp].getIconHeight());
					int tempx=(int)((Generation[Now_Generation].X[i]-Border_X+Border_L)*350/Border_L)-Point_Label[i].getWidth()/2;
					int tempy=0;
					if(Dimension_Mode==1)
					{
						tempy=700-(int)((Generation[Now_Generation].Fitness[i]-Border_Y+Border_Y_L)*350/Border_Y_L)-Point_Label[i].getHeight()/2;
					}
					else if(Dimension_Mode==2)
					{
						tempy=700-(int)((Generation[Now_Generation].Y[i]-Border_Y+Border_L)*350/Border_L)-Point_Label[i].getHeight()/2;
					}
					Point_Label[i].setLocation(tempx,tempy);
				}
			}
		}
		Player.Text_Generation.setText(""+Now_Generation+"/"+Total_Generation);

		//世代數改變
		if(int_Generation_Change>0)
		{
			Generation_Change();
			/*for(int j=1;j<=Now_Generation;j++)
			{
				try
				{
					if(tabulist[j]>0)
						Generation[j].R[0]=tabulist[j];
					if(Generation[j].U[0]>0)
						Generation[j].R[0]=0;
				}
				catch(Exception ex)
				{
					
				}
			}*/
		}

		if(Progress_Change==1)
		{
			Progress_Change=0;
			Progress_Panel();
			repaint();
		}

	}
	//第一層 外框輪廓
	//第一層 輪廓外框
	void Outline_Panel()
	{
		Graphics g = Outline_Image.getGraphics();
		Graphics2D g2D = (Graphics2D)g;

		//數值都設為無小數
		DecimalFormat df0=new DecimalFormat("####0");

		//底部白
		g.setColor(new Color(255,255,255));
		g.fillRect(0,0,Outline_x+8,Outline_y+90);

		//框線  黑色  粗(3)框
		g.setColor(new Color(0,0,0));
		g2D.setStroke(new BasicStroke(3.0f));

		//Middle block中間外框
		g.drawRect(M_block_x,M_block_y,M_block_w,M_block_l);
		//下邊  數值
		for(int i=0;i<=10;i++)
			g.drawString(df0.format(i*Border_L/5+Border_X-Border_L),/*X*/M_block_x+M_block_w/10*i-6,/*Y*/M_block_y-5/*+M_block_l+20*/);
		//左側邊  數值
		for(int i=0;i<=10;i++)
		{
			if(Dimension_Mode==2)
				g.drawString(df0.format((10-i)*Border_L/5+Border_Y-Border_L),/*X*/M_block_x-Spacing,/*Y*/M_block_y+M_block_l/10*i+8);
			else
				g.drawString(df0.format((10-i)*Border_Y_L/5+Border_Y-Border_Y_L),/*X*/M_block_x-Spacing,/*Y*/M_block_y+M_block_l/10*i+8);
		}
		//數值
		for(int i=0;i<=10;i++)
			g.drawString(df0.format(Color_Caps-(double)i*(Color_Caps-Color_Limit)/10),R_block_x+R_block_w+5,R_block_l/10*i+R_block_y+4);

		//右邊顏色對照

		int code_temp;
		double temp;
		int out_temp;
		//右邊漸層
		for(int i=0;i<R_block_l;i++)
		{
			code_temp=ColorDeploy(Color_Caps-(double)i*(Color_Caps-Color_Limit)/R_block_l);
			g.setColor(new Color(code_temp/1000000,(code_temp/1000)%1000,code_temp%1000));
			g.drawLine(R_block_x,R_block_y+i,R_block_x+R_block_w-3,R_block_y+i);
		}

		g.setColor(new Color(0,0,0));
		g2D.setStroke(new BasicStroke(3.0f));
		//Right block 右邊外框  (顏色對照用的框)
		g.drawRect(R_block_x,R_block_y,R_block_w-3,R_block_l-1);

		g2D.setStroke(new BasicStroke(2.0f));

		for(int i=0;i<=10;i++)
		{
			//內側兩邊的標線
			g.drawLine(R_block_x          ,  R_block_y+R_block_l/10*i,  R_block_x+7          ,  R_block_y+R_block_l/10*i);
			g.drawLine(R_block_x+R_block_w-3,  R_block_y+R_block_l/10*i,  R_block_x+R_block_w-7-3,  R_block_y+R_block_l/10*i);
		}

	}
	//第二層 色版
	void Answer_Panel()
	{
		Graphics g = Answer_Image.getGraphics();
		Graphics2D g2D = (Graphics2D)g;

		int code_temp;
		double temp;
		int out_temp;

		//底部白
		g.setColor(new Color(255,255,255));
		g.fillRect(0,0,M_block_w,M_block_l);
		if(Dimension_Mode==1)
		{
			x_axis.setText("x");
			y_axis.setText("f(x)");
			//midu=3;
			//畫機率分怖
			for(int i=0;i<700;i+=midu)
				for(int j=0;j<700;j+=midu)
				{
					//temp=Fitness(i,0,1);
					//code_temp=ColorDeploy(temp);
					double[] x = {0};
					double y,r=0;
					x[0] = (i*Border_L/350+Border_X-Border_L);
					y = j*Border_Y_L/350+Border_Y-Border_Y_L;
					r = Border_Y_S;
					r = r*(Border_X_S/Border_Y_S+2);
					r = Resolution * r*2;
					
	
					if(i*Border_L/350+Border_X-Border_L>=Border_X_M || i*Border_L/350+Border_X-Border_L<=Border_X_N|| j*Border_Y_L/350+Border_Y-Border_Y_L>=Border_Y_M || j*Border_Y_L/350+Border_Y-Border_Y_L<=Border_Y_N)
						out_temp=150;
					else
						out_temp=255;
					
					if(Mist_ToggleButton.isSelected())
					{
						if(Fitness(x[0],0,0)< y-r || Fitness(x[0],0,0)> y+r)
						{
							continue;
						}
						//System.out.println(i+". x = "+x[0]+"  fit = "+Formula(Postfix, x, 1, 0));
						//r = Border_Y_S/2+(Border_Y_S*y/Border_Y_M/2);
						temp = Fitness(x[0],0,0);
						code_temp=ColorDeploy(temp);
						g.setColor(new Color(code_temp/1000000,(code_temp/1000)%1000,code_temp%1000,out_temp));
						/*if(Fitness(x[0],0,0)< y-r || Fitness(x[0],0,0)> y+r)
						{
							//System.out.println("i = "+Formula(Postfix, x, 1, 0)+" j = "+y);
							g.setColor(new Color(255,255,255,out_temp));
						}*/
					}
					else
					{
						g.setColor(new Color(0,0,0));
					}
					g.fillRect(i-1,(700-1-j),midu,midu);
				}
		}
		else if(Dimension_Mode==2)
		{
			x_axis.setText("x1");
			y_axis.setText("x2");
			//畫機率分怖
			for(int i=0;i<700;i+=midu)
				for(int j=0;j<700;j+=midu)
				{
					temp=Fitness(i,j,1);
					code_temp=ColorDeploy(temp);
	
					if(i*Border_L/350+Border_X-Border_L>=Border_X_M || i*Border_L/350+Border_X-Border_L<=Border_X_N || j*Border_L/350+Border_Y-Border_L>=Border_Y_M || j*Border_L/350+Border_Y-Border_L<=Border_Y_N)
						out_temp=150;
					else
						out_temp=255;
	
					if(Mist_ToggleButton.isSelected())
					{
						g.setColor(new Color(code_temp/1000000,(code_temp/1000)%1000,code_temp%1000,out_temp));
					}
					else
					{
						g.setColor(new Color(0,0,0));
					}
					g.fillRect(i-1,(700-1-j),midu,midu);
				}
		}
		//解空間外框(白框框)
		g.setColor(new Color(255,255,255));
		g2D.setStroke(new BasicStroke(3.0f));
		//g2D.setStroke(new BasicStroke(2f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,10.0F,new float[]{30f,3f,2f,3f},0f));
		int ax,ay,bx,by;

		ax=(int)((Border_X_N-Border_X+Border_L)*350/Border_L);
		ay=700-(int)((Border_Y_N-Border_Y+Border_L)*350/Border_L);

		bx=(int)((Border_X_M-Border_X+Border_L)*350/Border_L);
		by=700-(int)((Border_Y_M-Border_Y+Border_L)*350/Border_L);
		if(Dimension_Mode==1)
		{
			ay=700-(int)((Border_Y_N-Border_Y+Border_Y_L)*350/Border_Y_L);
			by=700-(int)((Border_Y_M-Border_Y+Border_Y_L)*350/Border_Y_L);
		}

		g.drawLine(ax,ay,ax,by);
		g.drawLine(ax,ay,bx,ay);
		g.drawLine(ax,by,bx,by);
		g.drawLine(bx,ay,bx,by);

		//明暗軸的遮片
		float Transparency_temp;
		if((float)Slider_Transparency.getValue()>100)
		{
			Transparency_temp=(float)Slider_Transparency.getValue()-100;
			g.setColor(new Color(255,255,255,(int)(Transparency_temp*255/100)));
		}
		else
		{
			Transparency_temp=100-(float)Slider_Transparency.getValue();
			g.setColor(new Color(0,0,0,(int)(Transparency_temp*255/100)));
		}
		g.fillRect(0,0,700,700);

		//初始畫面
		//初始畫面的EPanel
		if(Show_Title==1)
		{
			g.setColor(new Color(0,0,0,255));
			g2D.setFont(new Font("Nyala", Font.BOLD,(int)(25000/Border_L)));
			g2D.drawString("EPanel",(int)((8-98-Border_X+Border_L)*350/Border_L+5-2000/Border_L),700-(int)((-12-Border_Y+Border_L)*350/Border_L-30));

			g.setColor(new Color(255,255,255,255));
			g2D.setFont(new Font("Nyala", Font.BOLD,(int)(25000/Border_L)));
			g2D.drawString("EPanel",(int)((8-100-Border_X+Border_L)*350/Border_L+5-2000/Border_L),700-(int)((-10-Border_Y+Border_L)*350/Border_L-30));
		}

	}
	//第三層 範圍顯示   + 軌跡橡皮擦
	void Eraser_Panel(int Eraser_Change)
	{
		//TABU範圍顯示
		if(Now_Generation>0 && Total_Generation>1)
		{
			int temp_j;
			temp_j=Now_Generation;
			
			
			if(Eraser_Change==1)temp_j=1; //如果刷新了畫面  則要重頭畫記錄
			/*TABU UPDATE*/
			try
			{
				int i = 0;
				for(int j =temp_j;j<=Now_Generation;j++)
					//for(i=0;i<Generation[j].Amount;i++)
						if(Generation[j].U[0]>0)
						{
							Generation[Generation[j].U[0]].R[0]=0;//把不要的TABU 範圍歸零
							Answer_Change=1;//刷新畫面
						}
			}
			catch(Exception ex){}
			
			for(int j=temp_j;j<=Now_Generation;j++)
				for(int i=0;i<Generation[j].Amount;i++)
					if(Generation[j].R[i]>0)
					{
						if(Dimension_Mode==1)
						{
							Graphics g = Answer_Image.getGraphics();
							Graphics2D g2D = (Graphics2D)g;
	
							g2D.setStroke(new BasicStroke(3.0f));
	
							int Rr=(int)(Generation[j].R[i]*350/Border_L)*2;//Rr其實是直徑  但是讀取的值 是半徑 所以要乘2(2015/11/11改)
							//Rr=10;
							g.setColor(new Color(0,0,0));
							g.drawOval((int)((Generation[j].X[i]-Border_X+Border_L)*350/Border_L)-Rr/2,
									700-(int)((Generation[j].Fitness[i]-Border_Y+Border_Y_L)*350/Border_Y_L)-Rr/2,Rr,Rr);
	
							g.setColor(new Color(200,200,200,150));
							g.fillOval((int)((Generation[j].X[i]-Border_X+Border_L)*350/Border_L)-Rr/2,
									700-(int)((Generation[j].Fitness[i]-Border_Y+Border_Y_L)*350/Border_Y_L)-Rr/2,Rr,Rr);
						}
						else if(Dimension_Mode==2)
						{
							Graphics g = Answer_Image.getGraphics();
							Graphics2D g2D = (Graphics2D)g;
	
							g2D.setStroke(new BasicStroke(3.0f));
	
							int Rr=(int)(Generation[j].R[i]*350/Border_L)*2;//Rr其實是直徑  但是讀取的值 是半徑 所以要乘2(2015/11/11改)
							//Rr=10;
							g.setColor(new Color(0,0,0));
							g.drawOval((int)((Generation[j].X[i]-Border_X+Border_L)*350/Border_L)-Rr/2,
									700-(int)((Generation[j].Y[i]-Border_Y+Border_L)*350/Border_L)-Rr/2,Rr,Rr);
	
							g.setColor(new Color(255,255,255,150));
							g.fillOval((int)((Generation[j].X[i]-Border_X+Border_L)*350/Border_L)-Rr/2,
									700-(int)((Generation[j].Y[i]-Border_Y+Border_L)*350/Border_L)-Rr/2,Rr,Rr);
						}
					}
		}
		//軌跡橡皮擦
		if(JFrame_mod==1 && Player.Eraser_pick!=0)
		{

			int temp_i;
			int code_temp;
			double max;

			Graphics g = Answer_Image.getGraphics();
			Graphics2D g2D = (Graphics2D)g;

			temp_i=Now_Generation;
			if(Eraser_Change==1)temp_i=1; //如果刷新了畫面  則要重頭畫記錄

			for(int i=temp_i;i<=Now_Generation;i++)
			{
				for(int j=0;j<Generation[Now_Generation].Amount;j++)
				{
					if(Generation[i].E[j]>=0)
					{
						//g.fillOval((int)((Generation[j].Xr[i]-Border_X+Border_L)*350/Border_L)-2,700-(int)((Generation[j].Yr[i]-Border_Y+Border_L)*350/Border_L)-2,5,5);
						int tempy=0;
						if(Dimension_Mode==1)
						{
							tempy=700-(int)((Generation[i].Fitness[j]-Border_Y+Border_Y_L)*350/Border_Y_L)-TE[Generation[i].E[j]].getIconHeight()/2+1;
						}
						else if(Dimension_Mode==2)
						{
							tempy=700-(int)((Generation[i].Y[j]-Border_Y+Border_L)*350/Border_L)-TE[Generation[i].E[j]].getIconHeight()/2+1;
						}
						g.drawImage(TE[Generation[i].E[j]].getImage(),
								(int)((Generation[i].X[j]-Border_X+Border_L)*350/Border_L)-TE[Generation[i].E[j]].getIconWidth()/2+1,
								tempy,null);
					}
				}
			}
		}
	}
	void Mist_Panel()
	{
		Graphics g = Answer_Image.getGraphics();

		int code_temp;
		double temp;
		int out_temp;

		for(int i=1;i<=Now_Generation;i++)
		{
			for(int j=0;j<Generation[Now_Generation].Amount;j++)
			{
					int tempx = (int)((Generation[i].X[j]-Border_X+Border_L)*350/Border_L)-TE[Generation[i].E[j]].getIconWidth()/2+1;
					int tempy = 700-(int)((Generation[i].Y[j]-Border_Y+Border_L)*350/Border_L)-TE[Generation[i].E[j]].getIconHeight()/2+1;
					temp=Fitness(Generation[i].X[j],Generation[i].Y[j],0);
					code_temp=ColorDeploy(temp);
					g.setColor(new Color(code_temp/1000000,(code_temp/1000)%1000,code_temp%1000,255));
					g.fillRect(tempx+4,tempy+4,midu,midu);
			}
		}
	}
	//進度軸
	void Progress_Panel()
	{
		Graphics g = Progress_Image.getGraphics();
		Graphics2D g2D = (Graphics2D)g;

		
		
		
		//底部白
		//g.setColor(new Color(150,150,150));
		g.setColor(new Color(255,255,255));
		g.fillRect(0,0,M_block_w+4,R_block_w);


		int code_temp=0;
		double temp=0;

		/*
		temp=((double)Now_Generation/(double)Total_Generation)*(Color_Caps-Color_Limit)+Color_Limit;
		code_temp=ColorDeploy(temp);

		g.setColor(new Color(code_temp/1000000,(code_temp/1000)%1000,code_temp%1000));
		 */

		//g.setColor(new Color(0,250,10));
		//g.setColor(new Color(50,255,50));

		//進度條顏色
		if(JFrame_mod==1)
			temp=(double)(Now_Generation-1)/(double)(Total_Generation-1);

		code_temp=(int)(temp*100);

/*		if(Player.Overlapping_ToggleButton.isSelected() )
			g.setColor(new Color(50-code_temp/2,120+code_temp,155+code_temp));
		else
			g.setColor(new Color(50,155+code_temp,50-code_temp/2));
*/		
//		
		if(Overlapping_ToggleButton2.isSelected() )
		{
			g.setColor(new Color(50-code_temp/2,120+code_temp,155+code_temp));
			//System.out.println("2 Pressed 1");
			//Progress_Unit.setText("unit:eval.");
		}
		else
		{
			g.setColor(new Color(50,155+code_temp,50-code_temp/2));
			//Progress_Unit.setText("unit:gener.");
		}

//		
		
		g.fillRect(0,0,(int)(temp*M_block_w)+4,R_block_w);

		
		//Progress_Unit.setText("aaaa");
		
		//光澤與陰影
		/*
		g.setColor(new Color(255,255,255,100));
		g.fillRect(0,0,M_block_w+4,15);

		g.setColor(new Color(255,255,255,100));
		g.fillRect(0,2,M_block_w+4,10);

		g.setColor(new Color(0,0,0,50));
		g.fillRect(0,28,M_block_w+4,5);
		 */

		g.setColor(new Color(255,255,255,100));
		g.fillRect(0,5,(int)(temp*M_block_w)+4,5);

		g.setColor(new Color(0,0,0,20));
		g.fillRect(0,20,(int)(temp*M_block_w)+4,R_block_w);

		g.setColor(new Color(0,0,0,50));
		g.fillRect(0,27,(int)(M_block_w*temp)+4,R_block_w);

		//數值
		String Str_num;
		if(JFrame_mod==1)
		{
			Str_num=""+Now_Generation+"/"+Total_Generation;
//for bar
//			GoTo_Button.setVisible(true);
			
			if(Overlapping_ToggleButton2.isSelected())
				Str_num=Str_num+"(eval.)";
				//Progress_Unit.setText("unit:eval.");
			else
				Str_num=Str_num+"(gener.)";
				//Progress_Unit.setText("unit:generation");
		}
		else
			Str_num="";

		g.setColor(new Color(0,0,0));
		//LilyUPC   Impact 微軟正黑體 Consolas

		g2D.setFont(new Font("IrisUPC", Font.BOLD,40));
		g2D.drawString(Str_num,(M_block_w-Str_num.length()*19)/2,R_block_w/2+10);

		g.setColor(new Color(0,0,0));
		g2D.setStroke(new BasicStroke(3.0f));
		g.drawRect(1,1,M_block_w,R_block_w-3);

	}
	//max 255 x 5
	//顏色轉換
	int colorcode(int int_color)
	{
		//int_color+=300;  //輸入0 ～1200      回傳 300～1500

		// 0 0 <255
		if(int_color<=255)return int_color;
		// 0 <255 255
		if(int_color<=255*2)return (int_color-255)*1000+255;
		// 0 255 255>
		if(int_color<=255*3)return 255000+255-(int_color-255*2);
		// <255 255 0
		if(int_color<=255*4)return (int_color-255*3)*1000000+255000;
		// 255 255> 0
		if(int_color<=255*5)return 255000000+(255-(int_color-255*4))*1000;
		// 255> 0  0
		if(int_color<=255*6)return (255-(int_color-255*5))*1000000;

		return 0;
	}
	//顏色轉換
	int ColorDeploy(double double_color)
	{
		//輸入Color_Limit ～ Color_Caps
		//獲得0 ～ 1200
		if(double_color>Color_Caps)double_color=Color_Caps;
		if(double_color<Color_Limit)double_color=Color_Limit;

		double Color_Gap=Color_Caps-Color_Limit;
		double Color_Amount,Color_temp;


		int temp=0;
		for(int i=0;i<6;i++)
			temp+=Color_Proportion[i];

		Color_Amount=double_color-Color_Limit;
		Color_Amount=Color_Amount/Color_Gap*temp; //0~100


		Color_temp=0;
		int temp_i;
		for(temp_i=0;temp_i<5;temp_i++)
			if(Color_Amount>=Color_Proportion[temp_i])
			{
				Color_Amount-=Color_Proportion[temp_i];
				Color_temp+=Color_P_Code[temp_i];
			}
			else
			{
				break;
			}

		//限制顏色上限
		if(Color_Amount>Color_Proportion[temp_i])Color_Amount=Color_Proportion[temp_i];
		Color_temp+=Color_Amount/Color_Proportion[temp_i]*Color_P_Code[temp_i];

		return colorcode((int)Color_temp+300);
	}
	//計算該位置適應值
	double Fitness(double X,double Y,int change)
	{
		double temp=0;

		if(change==1)
		{
			X=X*Border_L/350+Border_X-Border_L;
			if(Y==0)
				Y=0;
			else
				Y=Y*Border_L/350+Border_Y-Border_L;
		}

		if(Postfix[0]>0)
		{
			double XYtoX[]=new double[2];
			XYtoX[0]=X;
			XYtoX[1]=Y;
			temp=Formula(Postfix,XYtoX,Funtion_Dimension,0);
		}

		return temp;
	}
	//刷新畫面
	//刷新畫面
	void Reset_Panel()
	{
		
		Resolution = 0.01;
		Resolution_flag = 0;
		Resolution_count = 0;
		Pause=1;
		Player.Button_Start.setIcon(T[2]);

		Postfix[0]=0;
		Answer_Change=1;

	}
	//改變世代數
	void Generation_Change()
	{
		if(JFrame_mod==2)return ;
		if(Now_Generation<1)Now_Generation=1;
		if(Now_Generation>Total_Generation)Now_Generation=Total_Generation;

		Player.Slider_Generation.setValue(Now_Generation);

		Show.Answer_Change=1;
		Show.Progress_Change=1;

		Show.Round=0;
		int_Generation_Change=0;
		int j = 1,i=0;
		for(j=1;j<=Total_Generation;j++)
		{
			//for(i=0;i<Generation[j].Amount;i++)
			//{
				try
				{
					if(Generation[j].U[i]>0&&j>=Now_Generation)
					{
						Generation[Generation[j].U[i]].R[0]=tabulist[Generation[j].U[i]];
					}
					else
					{continue;}
				}
				catch(Exception ex)
				{
					
				}
			//}
		}
		Eraser_Panel(1);
	}
	//滑鼠判斷
	public MouseAdapter MyMouseAdapter = new MouseAdapter(){
		//---------------------------------------------------------------------------------------------//
		//進入
		public void mouseEntered(MouseEvent e){
			for(int i=0;i<Point_MAX;i++)
			{
				if(e.getSource()==Point_Label[i])
				{
					DecimalFormat df_11=new DecimalFormat("######0.000000");
					PFitness_String = ""+df_11.format(Fitness(Generation[Now_Generation].X[i],Generation[Now_Generation].Y[i],0));
					PFitness_JLabel.setLocation(Point_Label[i].getX(),Point_Label[i].getY()-10);
					PFitness_JLabel.setText(PFitness_String);
					PFitness_JLabel.setVisible(true);
					//System.out.println("PFit. ="+PFitness_String);
				}
			}
			if(e.getSource()==Label_Index)
			{
				Label_Index.setIcon(T[39+Int_Index]);
			}
		}
		//離開
		public void mouseExited(MouseEvent e){
			for(int i=0;i<Point_MAX;i++)
			{
				if(e.getSource()==Point_Label[i])
				{
					PFitness_String = "";
					PFitness_JLabel.setText(PFitness_String);
					PFitness_JLabel.setVisible(false);
				}
			}
			if(e.getX()<0 || e.getX()>224 || e.getY()<0 || e.getY()>35||e.getSource()==Button_Recover)
			{
				Label_Index.setIcon(T[38+Int_Index]);
			}
		}
		//按下
		public void mousePressed(MouseEvent e){

			MouseMod=1;

			if(e.getSource()!=Scan_Generation)
			{
			Scan_Generation.setVisible(false);
			Button_Confirm.setVisible(false);
			}
/**/
			if(e.getSource()==GoTo_Confirm)
			{
				int_Generation_Change=1;
				try
				{
					System.out.println("This");
					Now_Generation=Integer.parseInt(GoTo_Generation.getText());
					if(Now_Generation<1)Now_Generation=1;
					if(Now_Generation>Total_Generation)Now_Generation=Total_Generation;
					GoTo_Generation.setVisible(false);
					GoTo_Confirm.setVisible(false);
					GoTo_Button.setVisible(true);
				}
				catch (Exception event)
				{
					int_Generation_Change=0;
				}
				GoTo_Generation.setVisible(false);
				GoTo_Confirm.setVisible(false);
				GoTo_Button.setVisible(true);
			}
			
			if(e.getSource()==Button_Confirm)
			{
				int_Generation_Change=1;
				try
				{
					System.out.println("This");
					Now_Generation=Integer.parseInt(Scan_Generation.getText());
					if(Now_Generation<1)Now_Generation=1;
					if(Now_Generation>Total_Generation)Now_Generation=Total_Generation;
				}
				catch (Exception event)
				{
					int_Generation_Change=0;
				}
			}
//for player
			
/*			if(e.getSource()!=GoTo_Generation)
			{
				GoTo_Generation.setVisible(true);
				GoTo_Button.setVisible(true);
				GoTo_Generation.requestFocus();
				//GoTo_Button.requestFocus();
			}

			if(e.getSource()==GoTo_Button)
			{
				int_Generation_Change=1;
				try
				{
					Now_Generation=Integer.parseInt(GoTo_Generation.getText());
					if(Now_Generation<1)Now_Generation=1;
					if(Now_Generation>Total_Generation)Now_Generation=Total_Generation;
				}
				catch (Exception event)
				{
					int_Generation_Change=0;
				}
			}
			
			
*/			
			if(e.getSource()==Button_Borderchange)
			{
				//計算視窗中畫布範圍與位置
				Border_X_S=(Border_X_M-Border_X_N)/2;
				Border_Y_S=(Border_Y_M-Border_Y_N)/2;

				if(Border_X_S>=Border_Y_S)
					Border_L=Border_X_S;
				else
					Border_L=Border_Y_S;
				if(Dimension_Mode==1)
				{
					Border_L=Border_X_S;
				}

				Border_X=Show.Border_X_N+Border_X_S;
				Border_Y=Border_Y_N+Border_Y_S;

				//重製解空間顏色
				Resolution = 0.01;
				if(Dimension_Mode==1)
				{
					if(Border_Y_L==Border_L)
					{
						Border_Y_L=Border_Y_S;
					}
					else
					{
						Border_Y_L=Border_L;
					}
				}
				Answer_Change=1;
			}
			if (e.getSource()==Button_Video)
			{
				if(Video_mode==0)
				{
					Button_Video.setIcon(Show.T[31]);
					Video_x1=getX()+M_block_x;
					Video_x2=700+8;
					Video_y1=getY()+M_block_y+62;
					Video_y2=M_block_w+R_block_w+3;

					SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
					Date current = new Date();

					Video_mode=1;
					try{
						Video_out_file = new AVIOutputStream(new File("EPanelcam_"+sdFormat.format(current)+".avi"), AVIOutputStream.VideoFormat.JPG );
						Video_out_file.setVideoCompressionQuality( 0.5f );
						Video_out_file.setTimeScale(1);
						Video_out_file.setFrameRate(20);
					}catch(Exception es){
						es.printStackTrace();
					}
					new Threading().start();
				}
				else
				{
					//Button_Video.setIcon(Show.T[30]);
					//Threading().stop();
					Video_mode=0;
				}
			}
			if (e.getSource()==Button_Camera)
			{
				Screen(getX()+M_block_x,getY()+M_block_y+62,700+8,M_block_w+R_block_w+5);
			}

			//按下讀檔
			if(e.getSource()==Button_ReadFile)
			{
				
				Show.Pause=1;
				Show.Resolution = 0.01;
				Resolution_flag = 0;
				Button_Pause.setIcon(Show.T[2]);
				frame_Player.Button_Start.setIcon(Show.T[2]);
				//開啟舊檔
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setFileFilter(new FileNameExtensionFilter("EPin","epin"));

				if( chooser.showOpenDialog(null)== JFileChooser.APPROVE_OPTION)
				{
				    Pause = 1;
                    Show_Title = 0;
                    Progress_Change=1;
                    JFrame_mod=1;
                    Input_Color_Caps.setText("100");
					Input_Color_Limit.setText("0");
					frame_Player.SRF=chooser.getSelectedFile().getName();
					frame_Player.SRF_Name=frame_Player.SRF.substring(0,frame_Player.SRF.lastIndexOf("."));
					frame_Player.Text_ReadFile.setText(""+frame_Player.SRF_Name);
					frame_Player.Reader_File(""+chooser.getSelectedFile());
					frame_Player.Reset_Panel();

					Adaptive_Color();

				}
				tabulist = new double[Total_Generation];
				for(int i =0 ;i<Total_Generation;i++)
				{
					try
					{
						if(Generation[i].R[0]>0)
						tabulist[i]=Generation[i].R[0];
					}
					catch(Exception ex)
					{
						
					}
				}
				if(Dimension_Mode==1)
				{
					Button_Borderchange.setVisible(true);
					Bottom_JPanel.setSize(338,32);
				}
				else
				{
					Button_Borderchange.setVisible(false);
					Bottom_JPanel.setSize(338,32);
				}
				Label_Index.setLocation(356-Bottom_JPanel.getWidth()/2,Label_Index.getY());
				Bottom_JPanel.setLocation(356-Bottom_JPanel.getWidth()/2+32,Label_Index.getY());
			}
			/*if (e.getSource()==Button_Player)
			{
				if(Label_Index.isVisible())
				{
					Label_Index.setVisible(false);
					if(Int_Index==2)
					{
						Bottom_JPanel.setVisible(false);
					}
				}
				else
				{
					Label_Index.setVisible(true);
					if(Int_Index==2)
					{
						Bottom_JPanel.setVisible(true);
					}
				}
			}*/
			//原本的PLAYER
			/*if (e.getSource()==Button_Player)
			{
				Show_Title=0;
				Progress_Change=1;
				if(JFrame_mod==1)
				{
					JFrame_mod=0;
					frame_Player.setVisible(false);
					frame_Player.dispose();

					for(int i=0;i<Fitness_Amount;i++)
						Fitness_JLabel[i].setVisible(false);
					Reset_Panel();
				}
				else
				{
					JFrame_mod=1;
					Reset_Panel();

					//移到主視窗的相對位置
					frame_Player.setLocation(getX()+getWidth(),getY()+70);
					frame_Player.setVisible(true);
					Input_Color_Caps.setText("100");
					Input_Color_Limit.setText("0");
				}
			}*/
			if (e.getSource()==Button_Facebook)
			{
				try
				{
					Runtime.getRuntime().exec("cmd /c start https://www.facebook.com/NCNU.114.EPanel");
				}
				catch (java.io.IOException exception)
				{}
			}

			if(e.getSource()==Input_Color_Caps)
				Input_Color_Caps.setEditable(true);
			else
				Input_Color_Caps.setEditable(false);

			if(e.getSource()==Input_Color_Limit)
				Input_Color_Limit.setEditable(true);
			else
				Input_Color_Limit.setEditable(false);
			if(e.getSource()==Color_ToggleButton)
				Adaptive_Color();


			if(e.getSource()==Mist_ToggleButton)
			{
				if(Mist_ToggleButton.isSelected())
				{
					Mist_ToggleButton.setIcon(T[42]);
				}
				else
				{
					Mist_ToggleButton.setIcon(T[43]);
				}
				Answer_Change=1;
			}
			if (e.getSource()==Button_Stop)
			{
				frame_Player.Reset_Panel();
				Button_Pause.setIcon(T[2]);
			}
			if(e.getSource()==Button_Pause)
			{
				//MouseMod = 1;
				System.out.println("Tol_Gen="+Show.Total_Generation);
				int_Generation_Change = 0;
				if(Pause==1)
				{
					Pause=0;
					frame_Player.Button_Start.setIcon(Show.T[3]);
					Button_Pause.setIcon(T[3]);
					if(Now_Generation==Total_Generation)
					{
						int_Generation_Change=1;
						Now_Generation=1;
						Speed=1;
						frame_Player.int_Speed=(int)Math.sqrt(Speed);
						frame_Player.Text_Speed.setText(""+frame_Player.int_Speed);
						int_Speed=(int)Math.sqrt(Speed);
						Text_Speed.setText(""+int_Speed);
					}
				}
				else
				{
					Pause=1;
					frame_Player.Button_Start.setIcon(Show.T[2]);
					Button_Pause.setIcon(Show.T[2]);
				}
			}
			if (e.getSource()==Button_Accelerate)
			{
				Speed*=4;
				if(Speed>4096)Speed=1;
				int_Speed=(int)Math.sqrt(Speed);
				Text_Speed.setText(""+int_Speed);
				frame_Player.int_Speed=(int)Math.sqrt(Speed);
				frame_Player.Text_Speed.setText(""+Player.int_Speed);
			}
			if (e.getSource()==Button_Decelerate)
			{
				Speed/=4;
				if(Speed<1)Speed=4096;
				int_Speed=(int)Math.sqrt(Speed);
				Text_Speed.setText(""+int_Speed);
				frame_Player.int_Speed=(int)Math.sqrt(Speed);
				frame_Player.Text_Speed.setText(""+Player.int_Speed);
			}
			if (e.getSource()==Button_Recover)
			{

				//計算視窗中畫布範圍與位置
				Border_X_S=(Border_X_M-Border_X_N)/2;
				Border_Y_S=(Border_Y_M-Border_Y_N)/2;

				if(Border_X_S>=Border_Y_S)
					Border_L=Border_X_S;
				else
					Border_L=Border_Y_S;
				if(Dimension_Mode==1)
				{
					Border_L=Border_X_S;
				}

				Border_X=Show.Border_X_N+Border_X_S;
				Border_Y=Border_Y_N+Border_Y_S;

				//重製解空間顏色
				Resolution = 0.01;
				Answer_Change=1;
			}
			if(e.getSource()==Eraser_ToggleButton)
			{
				Answer_Change=1;
				//Communication_pick=(Communication_pick+1)%3;
				if(Eraser_pick==0)
				{
					Eraser_pick=1;
					Eraser_ToggleButton.setIcon(Show.T[23]);
					frame_Player.Eraser_pick=1;
					frame_Player.Eraser_ToggleButton.setIcon(Show.T[23]);


				}
				else if(Eraser_pick==1)
				{
					Eraser_pick=0;
					Eraser_ToggleButton.setIcon(Show.T[23]);
					frame_Player.Eraser_pick=0;
					frame_Player.Eraser_ToggleButton.setIcon(Show.T[23]);
				}
			}
			//Input_Color_Caps.setEditable(false);
			/*
		//視窗按鈕
		JButton Button_Player=new JButton();
		JButton Button_Detection=new JButton();
			 */

			//透明軸
			if ( e.getSource() instanceof JSlider ) {

				JSlider jSlider = (JSlider) e.getSource();
				jSlider.setValue( (int) (e.getX()*(jSlider.getMaximum()-1)/jSlider.getWidth())+1);
				Answer_Change=1;
			}

			Answer_JLabel.requestFocus();
			
			if(e.getSource()==Progress_JLabel)
			{
				//進度軸右鍵
				if(e.getButton()== MouseEvent.BUTTON3)
				{
					int getx = e.getXOnScreen();
					if(getx>650)getx=650;
					Scan_Generation.setLocation(getx,M_block_y+M_block_l+14);
					Scan_Generation.setVisible(true);
					Scan_Generation.setText("");
					Button_Confirm.setLocation(getx+100,M_block_y+M_block_l+14);
					Button_Confirm.setVisible(true);
					Scan_Generation.requestFocus();
				}
				else
				{
					Button_Confirm.setVisible(false);
					Scan_Generation.setVisible(false);
					MouseMod=3;
					Now_Generation=(int)(e.getX()*Total_Generation/Progress_JLabel.getWidth()+1);
					Generation_Change();
				}
			}

			if(e.getSource()==GoTo_Button)
			{
				//for player
				GoTo_Button.setVisible(false);
				GoTo_Generation.setVisible(true);
				GoTo_Generation.setText("");
				GoTo_Confirm.setVisible(true);
				GoTo_Generation.requestFocus();
				
				//for bar
/*
				Scan_Generation.setLocation(650,M_block_y+M_block_l+18);
				Scan_Generation.setVisible(true);
				Scan_Generation.setText("");
				Button_Confirm.setLocation(750,M_block_y+M_block_l+18);
				Button_Confirm.setVisible(true);
				Scan_Generation.requestFocus();
*/
			}
		}//按下END
		//放開滑鼠
		public void mouseReleased(MouseEvent e) {

			//透明軸
			if ( e.getSource() instanceof JSlider) {

				JSlider jSlider = (JSlider) e.getSource();
				jSlider.setValue( (int) (e.getX()*(jSlider.getMaximum()-1)/jSlider.getWidth())+1);
				Answer_Change=1;
			}

			if(e.getSource() == Label_Index)
			{
				Label_temp.setVisible(false);
				Label_Index.setVisible(true);
				if(Int_Index == 2)
				{
					Bottom_JPanel.setVisible(true);
				}

				if(e.getX()>=0&&e.getX()<=32)
				{
					Index_x2=0;
				}
				else
				{
					Index_x2 = e.getX();
				}
				Index_x1=Label_Index.getX()+Index_x2-16;
				if(Index_x2!=0)
				{
					if(Index_x1>3&&Index_x1<518)
					{
						Label_Index.setLocation(Index_x1,Label_Index.getY());
						Bottom_JPanel.setLocation(Index_x1+32,Label_Index.getY());
					}
					else if(Index_x1<=3)
					{
						Label_Index.setLocation(4,Label_Index.getY());
						Bottom_JPanel.setLocation(4+32,Label_Index.getY());
					}
					else
					{
						Label_Index.setLocation(806-Bottom_JPanel.getWidth(),Label_Index.getY());
						Bottom_JPanel.setLocation(806-Bottom_JPanel.getWidth()+32,Label_Index.getY());
					}
					//System.out.println("x = "+Label_Index.getX()+" x1 = "+x1+" x2 = "+x2);
				}
				else
				{
					if(Int_Index==0)
					{
						Int_Index=2;
						Label_Index.setIcon(T[39+Int_Index]);
						Bottom_JPanel.setVisible(true);
					}
					else
					{
						Int_Index=0;
						Label_Index.setIcon(T[39+Int_Index]);
						Bottom_JPanel.setVisible(false);
					}
				}
				Index_x2=0;
			}

			if(MouseMod==0)
			{
				midu=3;
				Answer_Change=1;
			}
		}
		//點擊滑鼠
		public void mouseClicked(MouseEvent e) {

			if(e.getSource()==Answer_JLabel)
			{
				if(System.currentTimeMillis()-temp_time<1000)
				{

					if(Show.Pause==1)
					{
						Show.Pause=0;
						Player.Button_Start.setIcon(Show.T[3]);
					}
					else
					{
						Show.Pause=1;
						Player.Button_Start.setIcon(Show.T[2]);

					}
					temp_time=0;
				}
				else
					temp_time=System.currentTimeMillis();
			}
			//透明軸點擊
			if ( e.getSource() instanceof JSlider ) {

				JSlider jSlider = (JSlider) e.getSource();
				jSlider.setValue( (int) (e.getX()*(jSlider.getMaximum()-1)/jSlider.getWidth())+1);
				Answer_Change=1;
			}
		}
		//移動滑鼠
		public void mouseMoved(MouseEvent e) {

			if(e.getSource()==Answer_JLabel)
			{
				//double  check_x=((e.getX()-M_block_x)*Border_L/350)-Border_L+Border_X;
				//double	check_y=(700-(e.getY()-M_block_y))*Border_L/350-Border_L+Border_Y;


				double  check_x=((e.getX()-Answer_JLabel.getAlignmentX())*Border_L/350)-Border_L+Border_X;
				double	check_y=(700-(e.getY()-Answer_JLabel.getAlignmentY()))*Border_L/350-Border_L+Border_Y;
				
				DecimalFormat df_10=new DecimalFormat("0.00");
				DecimalFormat df_11=new DecimalFormat("######0.000000");

				if(Dimension_Mode == 2)
				{
					//Text_Fitness.setText("="+df_11.format(Fitness(check_x,check_y,0)));
					Text_Fitness.setText("");
					Text_XY.setText("f("+df_10.format(check_x)+","+df_10.format(check_y)+")="+df_11.format(Fitness(check_x,check_y,0)));
				}
				else if(Dimension_Mode == 1)
				{
					Text_Fitness.setText(""+df_11.format(Fitness(check_x,0,0)));
					Text_XY.setText("f("+df_10.format(check_x)+","+df_10.format(check_y)+")");
				}
				//Text_Fitness.setText();
			}
		}
		//拖移滑鼠
		public void mouseDragged(MouseEvent e) {
			Scan_Generation.setVisible(false);
			Button_Confirm.setVisible(false);
			GoTo_Generation.setVisible(false);
			GoTo_Confirm.setVisible(false);
			if(e.getSource()==Progress_JLabel)
			{
				Now_Generation=(int)(e.getX()*Total_Generation/Progress_JLabel.getWidth()+1);
				Generation_Change();
			}
			//世代軸拖移
			if ( e.getSource() instanceof JSlider ) {
				JSlider jSlider = (JSlider) e.getSource();
				jSlider.setValue((int)(e.getX()*(jSlider.getMaximum()-1)/jSlider.getWidth())+1);
				Answer_Change=1;
			}
			if(MouseMod==1)
			{
				MouseXago=e.getX();
				MouseYago=e.getY();
				MouseMod=0;
			}

			for(int i=0;i<Fitness_Amount;i++)
				if(e.getSource()==Fitness_JLabel[i])
				{
					int goX=(e.getX()-MouseXago);
					int goY=(e.getY()-MouseYago);
					Fitness_JLabel[i].setLocation(Fitness_JLabel[i].getX()+goX,Fitness_JLabel[i].getY()+goY);
					Generation_JLabel[i].setLocation(Fitness_JLabel[i].getX()+500,Fitness_JLabel[i].getY()-100);
					//Generation_JLabel[i].setLocation(450,650);
				}
			if(e.getX()>=R_block_x && MouseMod<3)
			{
				//if(e.getY()<Progress_JLabel.getY())
				//	System.out.println(""+e.getY());
				Color_Caps-=(double)(e.getY()-MouseYago)*(Color_Caps-Color_Limit)/350;
				Color_Limit-=(double)(e.getY()-MouseYago)*(Color_Caps-Color_Limit)/350;

				Input_Color_Limit.setText(""+df.format(Color_Limit));
				Input_Color_Caps.setText(""+df.format(Color_Caps));

				Answer_Change=1;
				MouseXago=e.getX();
				MouseYago=e.getY();
			}
			if(e.getSource()==Answer_JLabel)
			{

				Border_X-=(double)(e.getX()-MouseXago)*Border_L/350;
				if(Dimension_Mode==2)
				{
					Border_Y+=(double)(e.getY()-MouseYago)*Border_L/350;
				}
				else if(Dimension_Mode==1)
				{
					Border_Y+=(double)(e.getY()-MouseYago)*Border_Y_L/350;
				}
				midu=6;
				Answer_Change=1;
				MouseXago=e.getX();
				MouseYago=e.getY();
			}
			if(e.getSource() == Label_Index)
			{
				Label_Index.setVisible(false);
				if(Int_Index == 2)
				{
					Bottom_JPanel.setVisible(false);
				}

				int x1;

				x1 = Label_Index.getX()+e.getX();

				Label_temp.setVisible(true);
				Label_temp.setSize(32,32);
				Label_temp.setIcon(T[39+Int_Index]);
				if(x1>3&&x1<550)
				{
					Label_temp.setLocation(Label_Index.getX()+e.getX()-16,Label_Index.getY());
				}
				else if(x1<=3)
				{
					Label_temp.setLocation(Label_Index.getX()+e.getX()-16,Label_Index.getY());
				}
				else
				{
					Label_temp.setLocation(Label_Index.getX()+e.getX()-16,Label_Index.getY());
				}
			}
			//MouseXago=e.getX();
			//MouseYago=e.getY();
			//repaint();
		}
		//滾動滑鼠
		public void mouseWheelMoved(MouseWheelEvent e) {
			MouseMod=5;
			if(e.getX()>=R_block_x)
			{
				if(e.getWheelRotation()>0)
					Color_Caps*=1.1;

				if(e.getWheelRotation()<0)
					if(Color_Caps*0.9>Color_Limit)
						Color_Caps*=0.9;
				Input_Color_Caps.setText(""+df.format(Color_Caps));
				Answer_Change=1;
			}
			
			if(e.getSource()==Answer_JLabel)
			{
				midu=6;
				Answer_Change=1;
				//System.out.println(""+Border_L);
				if(Resolution_count !=0)
				{
					Resolution/=Math.pow(1.2,Resolution_count);
					Resolution_count = 0;
				}
				if(e.getWheelRotation()>0)//&& Border_L<100000)
				{
					Border_X+=(-(double)(e.getX()-385)*Border_L/3500);
					if(Dimension_Mode==2)
					{
						Border_Y-=(-(double)(e.getY()-405)*Border_L/3500);
					}
					else if(Dimension_Mode==1)
					{
						Border_Y-=(-(double)(e.getY()-405)*Border_Y_L/3500);
					}
					Border_L*=1.1;
					Border_Y_L*=1.1;
					Resolution *=1.2;
					if(Resolution>=0.01*(Math.pow(1.2,5))&&Resolution_flag == 0)
					{
						Resolution_flag++;
						Resolution = 0.01*(Math.pow(1.2,5));
					}
					if(Resolution_flag!=0)
					{
						Resolution_flag++;
						Resolution/=1.2;
					}
				}
				if(e.getWheelRotation()<0)//&& Border_L>0.01)
				{
					Border_X+=((double)(e.getX()-385)*Border_L/3500);
					if(Dimension_Mode==2)
					{
						Border_Y-=(-(double)(e.getY()-405)*Border_L/3500);
					}
					else if(Dimension_Mode==1)
					{
						Border_Y-=(-(double)(e.getY()-405)*Border_Y_L/3500);
					}
					Border_L/=1.1;
					Border_Y_L/=1.1;
					Resolution /=1.2;
					if(Resolution<=0.01/(Math.pow(1.2,20))&&Resolution_flag==0)
					{
						Resolution_flag--;
						Resolution = 0.01/(Math.pow(1.2,20));
					}
					if(Resolution_flag!=0)
					{
						Resolution_flag--;
						Resolution*=1.2;
					}
				}
				
				Answer_Change=1;
				
			}
			//repaint();
		}
	};
	//鍵盤判斷
	public KeyAdapter MyKeyAdapter = new KeyAdapter(){
		//按下
		public void keyPressed(KeyEvent es)  {
			int proportion;
			proportion = (int)Math.log10((double)Total_Generation);
//			if(es.getKeyCode() == KeyEvent.VK_ENTER&&Scan_Generation.isVisible())
			if(es.getKeyCode() == KeyEvent.VK_ENTER&&GoTo_Generation.isVisible())
			{
				int_Generation_Change=1;
				try
				{
					//Now_Generation=Integer.parseInt(Scan_Generation.getText());
					Now_Generation=Integer.parseInt(GoTo_Generation.getText());
					if(Now_Generation<1)Now_Generation=1;
					if(Now_Generation>Total_Generation)Now_Generation=Total_Generation;
				}
				catch (Exception event)
				{
					int_Generation_Change=0;
				}
//				Scan_Generation.setVisible(false);
//				Button_Confirm.setVisible(false);
				GoTo_Generation.setVisible(false);
				GoTo_Confirm.setVisible(false);
				Answer_JLabel.requestFocus();
				GoTo_Button.setVisible(true);
			}
//			if(!Scan_Generation.isVisible())
			switch(es.getKeyCode()) {
			case KeyEvent.VK_DECIMAL:           //小鍵盤的 POINT  主鍵盤的是 VK_PERIOD;
				Now_Generation=Total_Generation;
				Generation_Change();
				break;
			case KeyEvent.VK_ADD:
				if(Dimension_Mode==2)
				Slider_Transparency.setValue(Slider_Transparency.getValue()+15);
				
				if(Resolution_count<11&&Dimension_Mode==1)
				{
					Resolution *=1.2;
					Resolution_count ++;
				}
				Answer_Change=1;
				break;
			case KeyEvent.VK_SUBTRACT:
				if(Dimension_Mode==2)
				Slider_Transparency.setValue(Slider_Transparency.getValue()-15);
				
				if(Resolution_count>-11&&Dimension_Mode==1)
				{
					Resolution /=1.2;
					Resolution_count --;
				}
				Answer_Change=1;
				break;
			case KeyEvent.VK_NUMPAD1:
				Now_Generation=Total_Generation/10;
				Generation_Change();
				break;
			case KeyEvent.VK_NUMPAD2:
				Now_Generation=Total_Generation/10*2;
				Generation_Change();
				break;
			case KeyEvent.VK_NUMPAD3:
				Now_Generation=Total_Generation/10*3;
				Generation_Change();
				break;
			case KeyEvent.VK_NUMPAD4:
				Now_Generation=Total_Generation/10*4;
				Generation_Change();
				break;
			case KeyEvent.VK_NUMPAD5:
				Now_Generation=Total_Generation/10*5;
				Generation_Change();
				break;
			case KeyEvent.VK_NUMPAD6:
				Now_Generation=Total_Generation/10*6;
				Generation_Change();
				break;
			case KeyEvent.VK_NUMPAD7:
				Now_Generation=Total_Generation/10*7;
				Generation_Change();
				break;
			case KeyEvent.VK_NUMPAD8:
				Now_Generation=Total_Generation/10*8;
				Generation_Change();
				break;
			case KeyEvent.VK_NUMPAD9:
				Now_Generation=Total_Generation/10*9;
				Generation_Change();
				break;
			case KeyEvent.VK_NUMPAD0:
				Now_Generation=1;
				Generation_Change();
				break;
			case KeyEvent.VK_END:
				Now_Generation=Total_Generation;
				Generation_Change();
				break;
			case KeyEvent.VK_UP:     // 向上
				Speed*=4;
				if(Speed>4096)Speed=1;
				frame_Player.int_Speed=(int)Math.sqrt(Speed);
				frame_Player.Text_Speed.setText(""+Player.int_Speed);
				int_Speed=(int)Math.sqrt(Speed);
				Text_Speed.setText(""+int_Speed);
				break;
			case KeyEvent.VK_DOWN:   // 向下
				Speed/=4;
				if(Speed<1)Speed=4096;
				frame_Player.int_Speed=(int)Math.sqrt(Speed);
				frame_Player.Text_Speed.setText(""+Player.int_Speed);
				int_Speed=(int)Math.sqrt(Speed);
				Text_Speed.setText(""+int_Speed);
				break;
			case KeyEvent.VK_LEFT:   // 向左
				Now_Generation-=Total_Generation/Math.pow(10.0, proportion);
				Generation_Change();
				break;
			case KeyEvent.VK_RIGHT:  // 向右
				Now_Generation+=Total_Generation/Math.pow(10.0, proportion);
				Generation_Change();
				break;
			case KeyEvent.VK_SPACE:  // 空白
				if(Pause==1)
				{
					Pause=0;
					frame_Player.Button_Start.setIcon(Show.T[3]);
					Button_Pause.setIcon(T[3]);
					if(Now_Generation==Total_Generation)
					{
						int_Generation_Change=1;
						Now_Generation=1;
						Speed=1;
						frame_Player.int_Speed=(int)Math.sqrt(Speed);
						frame_Player.Text_Speed.setText(""+frame_Player.int_Speed);
						int_Speed=(int)Math.sqrt(Speed);
						Text_Speed.setText(""+int_Speed);
					}
				}
				else
				{
					Pause=1;
					frame_Player.Button_Start.setIcon(Show.T[2]);
					Button_Pause.setIcon(Show.T[2]);
				}
			}
		}
	};//KEY-END
	//視窗判斷
	public WindowAdapter MyWindowListener = new WindowAdapter(){

		//System.out.println("焦點");
		public void windowActivated(WindowEvent e){
		}
		//System.out.println("失焦");
		public void windowDeactivated(WindowEvent e){
		}
		public void windowClosing(WindowEvent e){
			Video_mode=0;
		}
		public void windowClosed(WindowEvent e){

			//System.exit( -1 );
		}
	};

}//Show END

//播放控制器
class Player extends EPanel implements ChangeListener {

	static JLabel Text_Speed =new  JLabel("1");
	JLabel Label_Speed =new  JLabel();
	static int int_Speed=1;

	JButton Button_Stop=new JButton();
	static JButton Button_Start=new JButton();
	//JButton Button_Pause=new JButton();
	JButton Button_Accelerate=new JButton();
	JButton Button_ReadFile=new JButton();

	JButton Button_Auto=new JButton();
	JButton Button_Recovery=new JButton();

	JButton Button_Decelerate=new JButton();

	//世代數控調
	static JSlider Slider_Generation;
	static float float_Generation=0;

	static JTextField Scan_Generation = new JTextField();
	static JButton Button_Confirm = new JButton();
	static JLabel Text_Generation =new  JLabel("1/1");
	JPanel  Panel_Generation = new JPanel();


	//「橡皮擦」選項
	JToggleButton Eraser_ToggleButton= new JToggleButton();
	static int Eraser_pick=0;
	/*
	static JCheckBox Eraser_Box = new JCheckBox();
	static JCheckBox Eraser_Color_Box = new JCheckBox(); */

	//讀重疊設定
	static JToggleButton Overlapping_ToggleButton= new JToggleButton();

	//讀檔名稱
	JLabel Text_ReadFile= new  JLabel("");
	String SRF;
	static File Player_file;
	static String SRF_Name;

	//最佳解
	static JLabel Text_Fitness =new  JLabel("0.0");
	JPanel Panel_Fitness = new JPanel();


	//方程式
	JTextField Input_Formula = new  JTextField("0");

	//------------------------------------------------------------------主視窗程式碼
	Player ()//throws Exception
	{
		//讀檔
		Button_ReadFile= new JButton(Show.T[5]);
		//Button_ReadFile.setToolTipText("開啟檔案EPin");
		Button_ReadFile.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_ReadFile.setBounds(4,4,32,32);
		Button_ReadFile.addMouseListener(MyMouseAdapter);
		add(Button_ReadFile);
		//讀重疊設定
		Overlapping_ToggleButton= new JToggleButton(Show.T[16]);
		//Overlapping_ToggleButton.setToolTipText("重疊讀檔");
		Overlapping_ToggleButton.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Overlapping_ToggleButton.addMouseListener(MyMouseAdapter);
		Overlapping_ToggleButton.setBounds(36,4,32,32);
		add(Overlapping_ToggleButton);

		//讀檔檔案名稱
		//Text_ReadFile.setToolTipText("檔案名稱");
		Text_ReadFile.setBounds(68,4,150,32);
		Text_ReadFile.setFont(new Font("微軟正黑體",Font.BOLD,18));
		Text_ReadFile.setHorizontalAlignment(JTextField.CENTER);
		Text_ReadFile.setBorder(BorderFactory.createEtchedBorder());
		add(Text_ReadFile);

		//世代數外框
		Panel_Generation.setBounds(220,1,147,38);
		//Panel_Generation.setBorder(new TitledBorder("世代數"));
		Panel_Generation.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"世代數"));
		//Panel_Generation.setBackground(new Color(250,250,250));
		Panel_Generation.setLayout(null);
		add(Panel_Generation);

		//世代數輸入
		Scan_Generation.setBounds(20,12,80,22);
		Scan_Generation.setVisible(false);
		Scan_Generation.setFont(new Font("微軟正黑體",Font.BOLD,16));
		Panel_Generation.add(Scan_Generation);

		Button_Confirm.setBounds(100, 12, 22, 22);
		Button_Confirm.setIcon(Show.T[36]);
		Button_Confirm.setFont(new Font("微軟正黑體",Font.BOLD,12));
		Button_Confirm.setVisible(false);
		Button_Confirm.addMouseListener(MyMouseAdapter);
		Panel_Generation.add(Button_Confirm);

		//世代數
		//Text_Generation.setToolTipText("當前世代數/總世代數");
		Text_Generation.setBounds(0,12,147,20);
		Text_Generation.setFont(new Font("微軟正黑體",Font.BOLD,18));
		Text_Generation.setHorizontalAlignment(JLabel.CENTER);
		Text_Generation.addMouseListener(MyMouseAdapter);
		/*Text_Generation.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(),"Slider 2",TitledBorder.LEFT,
		        TitledBorder.TOP)); */
		Panel_Generation.add(Text_Generation);

		//世代數控制條
		Slider_Generation = new JSlider(0,0,0);
		//Slider_Generation.setToolTipText("世代數播放控制條");
		Slider_Generation.setBounds(4,46,364,30);
		//Slider_Generation.setBackground(new Color(255,255,255));
		//Slider_Generation.setPaintTicks(true);// setPaintTicks()方法是設置是否在JSlider加上刻度，若為true則下面兩行才有作用。
		//Slider_Generation.setMajorTickSpacing(100);
		//Slider_Generation.setMinorTickSpacing(100);
		//Slider_Generation.setSnapToTicks(true);// setSnapToTicks()方法表示一次移動一個小刻度，而不再是一次移動一個單位刻度。
		//Slider_Generation.setPaintLabels(true);
		//Slider_Generation.setPaintTrack(false);//setPaintTrack()方法表示是否出現滑動桿的橫桿。默認值為true.
		//Slider_Generation.putClientProperty("JSlider.isFilled", Boolean.TRUE);
		Slider_Generation.setMinimum(1);
		Slider_Generation.addChangeListener(this);
		Slider_Generation.addMouseListener(MyMouseAdapter);
		Slider_Generation.addMouseMotionListener(MyMouseAdapter);
		Slider_Generation.setPaintTicks(true); //指針狀

		add(Slider_Generation);

		//停止
		Button_Stop= new JButton(Show.T[1]);
		Button_Stop.setToolTipText("Stop");
		Button_Stop.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Stop.setBounds(4,78,32,32);
		Button_Stop.addMouseListener(MyMouseAdapter);
		add(Button_Stop);
		//開始
		Button_Start= new JButton(Show.T[2]);
		Button_Start.setToolTipText("Play");
		Button_Start.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Start.setBounds(36,78,32,32);
		Button_Start.addMouseListener(MyMouseAdapter);
		add(Button_Start);
		//反轉
		Button_Decelerate= new JButton(Show.T[37]);
		Button_Decelerate.setToolTipText("Decelerate");
		Button_Decelerate.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Decelerate.setBounds(68,78,32,32);
		Button_Decelerate.addMouseListener(MyMouseAdapter);
		add(Button_Decelerate);
		//快轉
		Button_Accelerate= new JButton(Show.T[4]);
		Button_Accelerate.setToolTipText("Accelerate");
		Button_Accelerate.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Accelerate.setBounds(100,78,32,32);
		Button_Accelerate.addMouseListener(MyMouseAdapter);
		add(Button_Accelerate);
		//速度
		//Text_Speed.setToolTipText("目前播放倍率");
		Text_Speed.setBounds(132,78,32,32);
		Text_Speed.setFont(new Font("微軟正黑體",Font.BOLD,14));
		//Text_Speed.setBackground(new Color(255,000,0));
		Text_Speed.setOpaque(false);
		Text_Speed.setHorizontalAlignment(JLabel.CENTER);
		add(Text_Speed);
		//速度底圖
		Label_Speed =new  JLabel(Show.T[9]);
		Label_Speed.setBounds(132,78,32,32);
		//Text_Speed.setBackground(new Color(255,000,0));
		//Label_Speed.setOpaque(false);
		add(Label_Speed);



		//橡皮擦選項按鈕
		Eraser_ToggleButton= new JToggleButton(Show.T[23]);
		Eraser_ToggleButton.setToolTipText("History");
		Eraser_ToggleButton.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Eraser_ToggleButton.addMouseListener(MyMouseAdapter);
		Eraser_ToggleButton.setBounds(164,78,32,32);
		add(Eraser_ToggleButton);
		/*
		//橡皮擦勾選
		Eraser_Box.setToolTipText("顯示粒子停留過的點");
		Eraser_Box.setText("記錄");
		Eraser_Box.setFont(new Font("微軟正黑體",Font.BOLD,12));
		Eraser_Box.setBounds(268,80,49,15);
		add(Eraser_Box);
		//橡皮擦顏色勾選
		Eraser_Color_Box.setToolTipText("顏色顯示粒子停留過的點");
		Eraser_Color_Box.setText("顏色");
		Eraser_Color_Box.setFont(new Font("微軟正黑體",Font.BOLD,12));
		Eraser_Color_Box.setBounds(268,95,49,15);
		add(Eraser_Color_Box);*/

		//自動播放
		Button_Auto= new JButton(Show.T[27]);
		//Button_Auto.setToolTipText("依照檔案自動暫停、延遲、加減速");
		Button_Auto.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Auto.setBounds(228,77,34,34);
		Button_Auto.addMouseListener(MyMouseAdapter);
		//add(Button_Auto);

		Button_Recovery= new JButton(Show.T[14]);
		Button_Recovery.setToolTipText("Default");
		Button_Recovery.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Recovery.setBounds(196,77,34,34);
		Button_Recovery.addMouseListener(MyMouseAdapter);
		add(Button_Recovery);

		//最佳解外框
		Panel_Fitness.setBounds(232,75,135,38);
		Panel_Fitness.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Fitness"));
		//Panel_Generation.setBackground(new Color(250,250,250));
		Panel_Fitness.setLayout(null);
		add(Panel_Fitness);

		//最佳解
		//Text_Fitness.setToolTipText("當前世代找到的最好解");
		Text_Fitness.setBounds(0,12,135,20);
		Text_Fitness.setFont(new Font("微軟正黑體",Font.BOLD,18));
		Text_Fitness.setHorizontalAlignment(JLabel.CENTER);
		Panel_Fitness.add(Text_Fitness);

		Input_Formula.setBounds(5,120,364,32);
		//Input_Formula.setToolTipText("方程式顯示框");
		Input_Formula.setFont(new Font("微軟正黑體",Font.BOLD,14));
		Input_Formula.setHorizontalAlignment(JTextField.CENTER);
		Input_Formula.setBorder(BorderFactory.createEtchedBorder());
		add(Input_Formula);
		/*
		//停止
		Button_Stop= new JButton(T[1]);
		Button_Stop.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Stop.setBounds(20,25,32,32);
		Button_Stop.addMouseListener(check);
		Panel_Generation.add(Button_Stop);
		//暫停
		Button_Pause= new JButton(T[0]);
		Button_Pause.setMargin(new Insets(0, 0, 0, 0)); //設置邊框和標籤的間距
		Button_Pause.setBounds(140,75,32,32);
		Button_Pause.addMouseListener(check);
		Button_Pause.setOpaque(false);
		add(Button_Pause);
		 */

		Text_ReadFile.setDropTarget(new DropTarget() {
			public void drop(DropTargetDropEvent e) {
				e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				try {
					List droppedFiles = (List) e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					File file=(File)droppedFiles.get(0);
					System.out.print(file.getAbsolutePath());

					Reader_File(""+file.getAbsolutePath());
					SRF=file.getName();
					SRF_Name=SRF.substring(0,SRF.lastIndexOf("."));

					Text_ReadFile.setText(""+SRF_Name);

					Reset_Panel();

					Adaptive_Color();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		setIconImage(Show.T[11].getImage()); //圖標設定
		setLayout(null);
		setTitle("演化計算播放器  "+Show.Version);
		setBounds(855,160,392,200);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(MyWindowListener);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//滑鼠判斷
	public MouseAdapter MyMouseAdapter = new MouseAdapter(){
		//---------------------------------------------------------------------------------------------//
		//按下滑鼠
		public void mousePressed(MouseEvent e){
			Show.Answer_JLabel.requestFocus();
//			Scan_Generation.setVisible(false);
//			Button_Confirm.setVisible(false);
			Text_Generation.setVisible(true);
			if(e.getSource()==Scan_Generation)
			{
				Scan_Generation.setVisible(true);
				Button_Confirm.setVisible(true);
				Text_Generation.setVisible(false);
			}
			if (e.getSource()==Text_Generation)
			{
				Text_Generation.setVisible(false);
				Scan_Generation.setVisible(true);
				Button_Confirm.setVisible(true);
				Scan_Generation.setText("");
			}

			
			if(e.getSource()==Button_Confirm)
			{
				Text_Generation.setVisible(true);
				Scan_Generation.setVisible(false);
				Button_Confirm.setVisible(false);

				Show.int_Generation_Change=1;
				try
				{
					//Show.Now_Generation=Integer.parseInt(GoTo_Generation.getText());
					Show.Now_Generation=Integer.parseInt(Scan_Generation.getText());
					if(Show.Now_Generation<1)Show.Now_Generation=1;
					if(Show.Now_Generation>Show.Total_Generation)Show.Now_Generation=Show.Total_Generation;
				}
				catch (Exception event)
				{
					Show.int_Generation_Change=0;
				}
			}
			if (e.getSource()==Button_Stop)
			{
				Reset_Panel();
			}
			if (e.getSource()==Button_Start)
				if(Show.Pause==0)
				{
					Show.Pause=1;
					Button_Start.setIcon(Show.T[2]);
					Show.Button_Pause.setIcon(Show.T[2]);

				}
				else
				{
					//播放
					Button_Start.setIcon(Show.T[3]);
					Show.Button_Pause.setIcon(Show.T[3]);
					Show.Pause=0;
					if(Show.Now_Generation==Show.Total_Generation)
					{
						Show.int_Generation_Change=1;
						Show.Now_Generation=1;
						Show.Speed=1;
						int_Speed=(int)Math.sqrt(Show.Speed);
						Text_Speed.setText(""+int_Speed);
					}
				}
			/*if (e.getSource()==Button_Auto)
			{

				if(Show.Auto_Pause==1)
				{
					Show.Auto_Pause=0;
					Button_Auto.setIcon(Show.T[28]);
				}
				else
				{
					Show.Auto_Pause=1;
					Button_Auto.setIcon(Show.T[27]);
				}
			}*/
			if (e.getSource()==Button_Recovery)
			{

				//計算視窗中畫布範圍與位置
				Show.Border_X_S=(Show.Border_X_M-Show.Border_X_N)/2;
				Show.Border_Y_S=(Show.Border_Y_M-Show.Border_Y_N)/2;

				if(Show.Border_X_S>=Show.Border_Y_S)
					Show.Border_L=Show.Border_X_S;
				else
					Show.Border_L=Show.Border_Y_S;
				
				if(Show.Dimension_Mode==1)
				{
						Show.Border_L=Show.Border_X_S;
						Show.Border_Y_L = Show.Border_Y_S;
				}
				Show.Border_X=Show.Border_X_N+Show.Border_X_S;
				Show.Border_Y=Show.Border_Y_N+Show.Border_Y_S;

				//重製解空間顏色
				Show.Answer_Change=1;
				//Show.Progress_Change=1;

			}
			//讀檔鍵
			if (e.getSource()==Button_ReadFile)
			{
				Show.Pause=1;
				Button_Start.setIcon(Show.T[2]);
				Show.Button_Pause.setIcon(Show.T[2]);
				Slider_Generation.setValue(0);
				//開啟舊檔
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setFileFilter(new FileNameExtensionFilter("EPin","epin"));

				if( chooser.showOpenDialog(null)== JFileChooser.APPROVE_OPTION)
				{
					SRF=chooser.getSelectedFile().getName();
					SRF_Name=SRF.substring(0,SRF.lastIndexOf("."));
					Text_ReadFile.setText(""+SRF_Name);
					Reader_File(""+chooser.getSelectedFile());
					Reset_Panel();

					Adaptive_Color();
				}

			}
			if (e.getSource()==Button_Accelerate)
			{
				Show.Speed*=4;
				if(Show.Speed>4096)Show.Speed=1;
				int_Speed=(int)Math.sqrt(Show.Speed);
				Text_Speed.setText(""+int_Speed);
				Show.int_Speed=(int)Math.sqrt(Show.Speed);
				Show.Text_Speed.setText(""+Show.int_Speed);
			}
			if (e.getSource()==Button_Decelerate)
			{
				Show.Speed/=4;
				if(Show.Speed<1)Show.Speed=4096;
				int_Speed=(int)Math.sqrt(Show.Speed);
				Text_Speed.setText(""+int_Speed);
				Show.int_Speed=(int)Math.sqrt(Show.Speed);
				Show.Text_Speed.setText(""+Show.int_Speed);
			}
			//橡皮擦選項按鈕
			if(e.getSource()==Eraser_ToggleButton)
			{
				Show.Answer_Change=1;
				//Communication_pick=(Communication_pick+1)%3;
				if(Eraser_pick==0)
				{
					Eraser_pick=1;
					Eraser_ToggleButton.setIcon(Show.T[23]);
					Show.Eraser_pick=1;

				}
				else if(Eraser_pick==1)
				{
					/*	Eraser_pick=2;
					Eraser_ToggleButton.setIcon(Show.T[24]);
					Eraser_ToggleButton.setSelected(false);
				}
				else if(Eraser_pick==2)
				{  */
					Eraser_pick=0;
					Eraser_ToggleButton.setIcon(Show.T[23]);
					Show.Eraser_pick=0;
				}
			}
			//世代軸
			if (e.getSource()==Slider_Generation ) {

				//Show.Answer_Change=1;
				//JSlider jSlider = (JSlider) e.getSource();
				//jSlider.setValue( );
				Show.int_Generation_Change=1;
				Show.Now_Generation=(int) (e.getX()*(Slider_Generation.getMaximum()-1)/Slider_Generation.getWidth())+1;
				}
		}//按下
		//放開滑鼠
		public void mouseReleased(MouseEvent e) {
			//世代軸
			if (e.getSource()==Slider_Generation ) {

				Show.int_Generation_Change=1;
				Show.Now_Generation=(int) (e.getX()*(Slider_Generation.getMaximum()-1)/Slider_Generation.getWidth())+1;
				if(Show.Now_Generation<1)Show.Now_Generation=1;
				if(Show.Now_Generation>Show.Total_Generation)Show.Now_Generation=Show.Total_Generation;
			}
		}
		//點擊滑鼠
		public void mouseClicked(MouseEvent e) {
			//世代軸點擊
			if (e.getSource()==Slider_Generation ) {

				Show.int_Generation_Change=1;
				Show.Now_Generation=(int) (e.getX()*(Slider_Generation.getMaximum()-1)/Slider_Generation.getWidth())+1;
				if(Show.Now_Generation<1)Show.Now_Generation=1;
				if(Show.Now_Generation>Show.Total_Generation)Show.Now_Generation=Show.Total_Generation;
			}
		}
		//拖曳滑鼠
		public void mouseDragged(MouseEvent e) {
			//世代軸拖移
			if (e.getSource()==Slider_Generation ) {
				Show.int_Generation_Change=1;
				Show.Now_Generation=(int) (e.getX()*(Slider_Generation.getMaximum()-1)/Slider_Generation.getWidth())+1;
				if(Show.Now_Generation<1)Show.Now_Generation=1;
				if(Show.Now_Generation>Show.Total_Generation)Show.Now_Generation=Show.Total_Generation;
			}
		}
	};//滑鼠判斷END
	//滑動軸判斷
	public void stateChanged(ChangeEvent e) {


		if ((JSlider) e.getSource() == Slider_Generation)
		{
			Text_Fitness.setText(""+Show.Generation[Show.Now_Generation].Fitness[0]);

			for(int i=0;i<Show.Fitness_Amount;i++)
			{
				Show.Fitness_JLabel[i].setText(Show.Fitness_String[i]+Show.Generation[Show.Now_Generation].Fitness[i]);//+"    # of    :"+(Show.Generation[Show.Now_Generation].Amount*Show.Now_Generation));
				Show.Fitness_JLabel[i].setSize(Show.Fitness_JLabel[i].getText().length()*Show.Fitness_JLabel_len,Show.Fitness_JLabel[i].getHeight());
				Show.Fitness_JLabel[i].setIcon(Show.TP[Show.Generation[Show.Now_Generation].S[i]]);
				
				Show.Generation_JLabel[i].setText("Eval.:"+(Show.Generation[Show.Now_Generation].Amount*Show.Now_Generation));
				Show.Generation_JLabel[i].setSize(Show.Generation_JLabel[i].getText().length()*Show.Fitness_JLabel_len,Show.Generation_JLabel[i].getHeight());
				Show.Generation_JLabel[i].setIcon(Show.TE[Show.Generation[Show.Now_Generation].E[i]]);
				
				/*int T_temp=Show.Generation[Show.Now_Generation].S[i];
				int w=Show.TP[T_temp].getIconWidth();
				int h=Show.TP[T_temp].getIconHeight();
				int times=34/h;
				int new_w=w*times;
				int new_h=h*times;*/
				//BufferedImage Fitness_Img[0];
				
			}
			
			
		}
	}

	//重置畫面(XY軸,世代數1)
	void Reset_Panel()
	{

		Show.Fitness_String[Show.Fitness_Amount-1]=SRF_Name+" : ";
		//世代數設為1
		Show.Now_Generation=1;
		Slider_Generation.setValue(1);
		Show.Round=0;

		Show.Pause=1;
		Button_Start.setIcon(Show.T[2]);

		//重製解空間顏色
		Show.Answer_Change=1;
		Show.Progress_Change=1;

		//計算視窗中畫布範圍與位置
		Show.Border_X_S=(Show.Border_X_M-Show.Border_X_N)/2;
		Show.Border_Y_S=(Show.Border_Y_M-Show.Border_Y_N)/2;

		if(Show.Border_X_S>=Show.Border_Y_S)
			Show.Border_L=Show.Border_X_S;
		else
			Show.Border_L=Show.Border_Y_S;

		if(Show.Dimension_Mode==1)
		{
				Show.Border_L=Show.Border_X_S;
				Show.Border_Y_L = Show.Border_Y_S;
		}
		Show.Border_X=Show.Border_X_N+Show.Border_X_S;
		Show.Border_Y=Show.Border_Y_N+Show.Border_Y_S;

		//DecimalFormat df=new DecimalFormat("###0.00");
		Text_Fitness.setText(""+Show.Generation[Show.Now_Generation].Fitness[0]);

		for(int i=0;i<Show.Fitness_Amount;i++)
		{
			Show.Fitness_JLabel[i].setText(Show.Fitness_String[i]+Show.Generation[Show.Now_Generation].Fitness[i]);//+"    # of    :"+(Show.Generation[Show.Now_Generation].Amount*Show.Now_Generation));
			Show.Fitness_JLabel[i].setSize(Show.Fitness_JLabel[i].getText().length()*Show.Fitness_JLabel_len,Show.Fitness_JLabel[i].getHeight());
			Show.Fitness_JLabel[i].setIcon(Show.TP[Show.Generation[Show.Now_Generation].S[i]]);
			
			Show.Generation_JLabel[i].setText("Eval.:"+(Show.Generation[Show.Now_Generation].Amount*Show.Now_Generation));
			Show.Generation_JLabel[i].setSize(Show.Generation_JLabel[i].getText().length()*Show.Fitness_JLabel_len,Show.Generation_JLabel[i].getHeight());
			Show.Generation_JLabel[i].setIcon(Show.TE[Show.Generation[Show.Now_Generation].E[i]]);
			
			
		}
	}
	//讀檔
	void Reader_File(String SRF)
	{

		//                         0        1        2           3
		String Should_S_Str[]={"Range","Formula","Position","Dimension"};
		int    Should_S_int[]={      0,        0,         0,          0};
		
		int temp_Total_Generation = Show.Total_Generation;
		int temp_Count = 0;

		if(!Show.Overlapping_ToggleButton2.isSelected() )
		{
			Show.Fitness_Amount=0;
		}
		Show.Fitness_Amount++;
		
		//讀取解空間檔
		try
		{
			FileReader FR=new FileReader(SRF);
			BufferedReader BFR=new BufferedReader(FR);

			for(String temp_Str=BFR.readLine(); BFR.ready(); temp_Str=BFR.readLine())
			{
				//讀取維度 Dimension
				if(temp_Str.length()>=Should_S_Str[3].length() && temp_Str.substring(0,Should_S_Str[3].length()).compareToIgnoreCase(Should_S_Str[3])==0)
                {
                    Should_S_int[3] = 1;

                    String[] token = temp_Str.split(": ");
                    Show.Dimension_Mode = Integer.parseInt(token[1]);
                    if(Show.Dimension_Mode==1)
                    {
                    	Show.Funtion_Dimension = 1;
                    }
                    else if(Show.Dimension_Mode==2)
                    {
                    	Show.Funtion_Dimension = 2;
                    }
                    else
                    {
                    	Show.Funtion_Dimension = 3;
                    }
                }
				//範圍 Range
				if(temp_Str.length()>=Should_S_Str[0].length() && temp_Str.substring(0,Should_S_Str[0].length()).compareToIgnoreCase(Should_S_Str[0])==0)
				{
					Should_S_int[0]=1;

					//讀取X範圍
					String[] tokens_X_range = temp_Str.split(":");
					String[] tokens_X_MN = tokens_X_range[1].split("~");

					Show.Border_X_M=Double.parseDouble(tokens_X_MN[0].trim());
					if(tokens_X_MN.length>1)
						Show.Border_X_N=Double.parseDouble(tokens_X_MN[1].trim());
					else
						Show.Border_X_N=0;

					double temp;
					if(Show.Border_X_N>Show.Border_X_M)
					{temp=Show.Border_X_N;Show.Border_X_N=Show.Border_X_M;Show.Border_X_M=temp;}
					
					//Show.Border_Y_M=Show.Border_X_M;
					//Show.Border_Y_N=Show.Border_X_N;
					if(Show.Dimension_Mode==2)
					{
						Show.Border_Y_M=Show.Border_X_M;
						Show.Border_Y_N=Show.Border_X_N;
					}
					else if(Show.Dimension_Mode==1)
					{
						Show.Border_Y_M=Show.Border_X_M;
						Show.Border_Y_N=Show.Border_X_N;
						try
						{
							double[] x1 = {0};
							Show.Border_Y_M=0;
							Show.Border_Y_N=20922789;
							for(double i = Show.Border_X_N; i<=Show.Border_X_M;i+=(Show.Border_X_M-Show.Border_X_N)/32768)
							{
								x1[0]=i;
								if(Formula(Show.Postfix,x1,1,0)>Show.Border_Y_M)
								{
									Show.Border_Y_M = Formula(Show.Postfix,x1,1,0);
								}
								if(Formula(Show.Postfix,x1,1,0)<Show.Border_Y_N)
								{
									Show.Border_Y_N = Formula(Show.Postfix,x1,1,0);
								}
							}
							//Show.Border_Y_M*=2;
							//Show.Border_Y_N=0-Show.Border_Y_M;
							//x1[0] =Show.Border_X_M;
							//Show.Border_Y_M=Formula(Show.Postfix,x1,1,0);
							//Show.Border_Y_N=0;
						}
						catch(Exception es)
						{
							es.printStackTrace();
						}
					}
				}
				//讀取方程式 Formula
				if(temp_Str.length()>=Should_S_Str[1].length() && temp_Str.substring(0,Should_S_Str[1].length()).compareToIgnoreCase(Should_S_Str[1])==0)
				{
					Should_S_int[1]=1;
					//轉化方程式
					String[] tokens_Formula = temp_Str.split(":");
					Expand_Equation(tokens_Formula[1].trim(),Show.Postfix);
					Input_Formula.setText(tokens_Formula[1].trim());
					Show.Input_Formula.setText(tokens_Formula[1].trim());
				}
				//讀取粒子位置紀錄檔Position
				if(temp_Str.length()>=Should_S_Str[2].length() && temp_Str.substring(0,Should_S_Str[2].length()).compareToIgnoreCase(Should_S_Str[2])==0)
				{
					Should_S_int[2]=1;

					for(int i=0;i<Show.Fitness_Amount;i++)
					{
						Show.Fitness_JLabel[i].setVisible(false);
						Show.Generation_JLabel[i].setVisible(false);
					}
					//System.out.println(Show.Fitness_Amount);


					
					Show.Total_Generation=0;

//					
					int Interval_temp=1;
					int i=1;//目前代數
					for(temp_Str=BFR.readLine();temp_Str!=null;temp_Str=BFR.readLine())
					{
						temp_Count++;
						if(temp_Count>temp_Total_Generation)
							temp_Count = temp_Total_Generation;
						
						//根據切割前後字串
						String[] tokens1 = temp_Str.split(":");
						//切割出每個粒子資訊
						String[] tokens2 = tokens1[1].trim().split(" ");
						//每個世代最佳解
						String[] tokens4 = tokens1[0].trim().split(" ");
						
//						System.out.println(tokens4[0]);
//						System.out.println("------");
//						System.out.println("i="+i);

						if(!Show.Overlapping_ToggleButton2.isSelected())
						{
							Show.temp_Generation[i]=new Generation_Initial();
							Show.temp_Generation[i].Amount=0;
						}
						
						//得知間隔長度
						if(Show.Overlapping_ToggleButton2.isSelected())
						{
							//幾個粒子
							Interval_temp=tokens2.length;
							
//							if(i+Interval_temp>temp_Total_Generation)		//??
//								Interval_temp=temp_Total_Generation-i;		//??
						}
//				Interval_temp=tokens2.length;
//						System.out.println("In_tmp="+Interval_temp);

//??						if(Show.Fitness_Amount>1)
//??							Show.Generation[719]=new Generation_Initial();
						
//						System.out.println("Tol_Gen="+Show.Total_Generation);
						
						//開陣列
						for(int k=0;k<Interval_temp;k++)
						{
							Show.Generation[i+k]=new Generation_Initial();
//							Show.Generation[i+k].Amount=tokens2.length+Show.temp_Generation[i+k].Amount;
							if(Show.Fitness_Amount>1)
								if(i+k>temp_Total_Generation)
									Show.Generation[i+k].Amount=tokens2.length+Show.temp_Generation[temp_Total_Generation].Amount;
								else
									Show.Generation[i+k].Amount=tokens2.length+Show.temp_Generation[temp_Count+k].Amount;
							else
								Show.Generation[i+k].Amount=tokens2.length;
								
							Show.Generation[i+k].Fitness=new double[Show.Fitness_Amount];
							Show.Generation[i+k].Particle=new int[Show.Fitness_Amount];
							Show.Generation[i+k].X=new double[Show.Generation[i+k].Amount];
							Show.Generation[i+k].Y=new double[Show.Generation[i+k].Amount];
							Show.Generation[i+k].S=new int[Show.Generation[i+k].Amount];
							Show.Generation[i+k].R=new double[Show.Generation[i+k].Amount];
							Show.Generation[i+k].E=new int[Show.Generation[i+k].Amount];
							Show.Generation[i+k].U=new int[Show.Generation[i+k].Amount];
//							System.out.println("k="+k);
						}
						
						//放入新資料
						for(int k=0;k<Interval_temp;k++)
						{
							Show.Generation[i+k].Fitness[Show.Fitness_Amount-1]=Double.parseDouble(tokens4[1]);
							Show.Generation[i+k].Particle[Show.Fitness_Amount-1]=tokens2.length;
							
//							System.out.println("i="+(i+k));
//							System.out.println("Fit="+Show.Generation[i+k].Fitness[Show.Fitness_Amount-1]);
						}
//						System.out.println("F&P ok");
//						System.out.println(Show.Generation[i].Particle[Show.Fitness_Amount-1]);
						
						//單檔
						if(!Show.Overlapping_ToggleButton2.isSelected())
						{
							for(int j=Show.temp_Generation[i].Amount;j<Show.Generation[i].Amount;j++)
							{
								String[] tokens3 = tokens2[0+j-Show.temp_Generation[i].Amount].split(",");
								Show.Generation[i].X[j]=Double.parseDouble(tokens3[0]);
								Show.Generation[i].Y[j]=0;
								Show.Generation[i].S[j]=0;
								Show.Generation[i].R[j]=0;
								Show.Generation[i].E[j]=0;
								Show.Generation[i].U[j]=0;
								if(Show.Dimension_Mode==1)
								{
									if(tokens3.length>1)
										Show.Generation[i].S[j]=Integer.parseInt(tokens3[1]);
									if(tokens3.length>2)
										Show.Generation[i].R[j]=Double.parseDouble(tokens3[2]);
									if(tokens3.length>3)
										Show.Generation[i].E[j]=Integer.parseInt(tokens3[3]);
									if(tokens3.length>4)
										Show.Generation[i].U[j]=Integer.parseInt(tokens3[4]);
								}
								else if(Show.Dimension_Mode==2)
								{
									Show.Generation[i].Y[j]=Double.parseDouble(tokens3[1]);
									if(tokens3.length>2)
										Show.Generation[i].S[j]=Integer.parseInt(tokens3[2]);
									if(tokens3.length>3)
										Show.Generation[i].R[j]=Double.parseDouble(tokens3[3]);
									if(tokens3.length>4)
										Show.Generation[i].E[j]=Integer.parseInt(tokens3[4]);
									if(tokens3.length>5)
										Show.Generation[i].U[j]=Integer.parseInt(tokens3[5]);
								}
							}
						}
						//多檔
						else
						{
//							System.out.println("tmp_G[i].Amo="+Show.temp_Generation[temp_Count].Amount);

							for(int k=Show.temp_Generation[temp_Count].Amount;k<Show.Generation[i].Amount;k++)
//臨時用							for(int k=0;k<Show.Generation[i].Amount;k++)
							{
//								System.out.println("G[i].Particle="+Show.Generation[i].Particle[Show.Fitness_Amount-1]);
								String[] tokens3 = tokens2[0+k-Show.temp_Generation[temp_Count].Amount].split(",");
								
//								System.out.println("tokens3[0]="+tokens3[0]);
//								System.out.println("tempAm="+Show.temp_Generation[i].Amount);
//								System.out.println("Am="+Show.Generation[i].Amount);
//								System.out.println("i+Interval_temp="+(i+Interval_temp));
//								System.out.println("i="+i);
//								System.out.println("k="+k);
								for(int m=0;m<Show.Generation[i].Particle[Show.Fitness_Amount-1];m++)
								{
									int t1=i;
									int t2=k;
//									if(i+m == 719)
//										System.out.println("i="+(i+m));
//									System.out.println("m="+m);
									Show.Generation[i+m].Y[k]=0;
//									System.out.println("Y="+Show.Generation[i+m].Y[k]);
									Show.Generation[i+m].S[k]=0;
									Show.Generation[i+m].R[k]=0;
									Show.Generation[i+m].E[k]=0;
									Show.Generation[i+m].U[k]=0;
//									System.out.println("Fitness_Amount="+Show.Fitness_Amount);
								if(m<(k-Show.temp_Generation[temp_Count].Amount))
//臨時用										if(m<k)
									{
										t1=i-1;
										if(t1<1)
											t1=1;
										if(i==1)
											t2=0;
										
//										System.out.println("t1="+t1);
//										System.out.println("t2="+t2);
//										System.out.println("G[t1].X[t2]="+Show.Generation[t1].X[t2]);
										Show.Generation[i+m].X[k]=Show.Generation[t1].X[t2];
										Show.Generation[i+m].Y[k]=Show.Generation[t1].Y[t2];
										Show.Generation[i+m].S[k]=Show.Generation[t1].S[t2];
										Show.Generation[i+m].R[k]=Show.Generation[t1].R[t2];
										Show.Generation[i+m].E[k]=Show.Generation[t1].E[t2];
										Show.Generation[i+m].U[k]=Show.Generation[t1].U[t2];
									}
									else
									{
										Show.Generation[i+m].X[k]=Double.parseDouble(tokens3[0]);
//										System.out.println("G[i+m].X[k]="+Show.Generation[i+m].X[k]);
										if(Show.Dimension_Mode==1)
										{
											if(tokens3.length>1)
												Show.Generation[i+m].S[k]=Integer.parseInt(tokens3[1]);
											if(tokens3.length>2)
												Show.Generation[i+m].R[k]=Double.parseDouble(tokens3[2]);
											if(tokens3.length>3)
												Show.Generation[i+m].E[k]=Integer.parseInt(tokens3[3]);
											if(tokens3.length>4)
												Show.Generation[i+m].U[k]=Integer.parseInt(tokens3[4]);
										}
										else if(Show.Dimension_Mode==2)
										{
											Show.Generation[i+m].Y[k]=Double.parseDouble(tokens3[1]);
											if(tokens3.length>2)
												Show.Generation[i+m].S[k]=Integer.parseInt(tokens3[2]);
											if(tokens3.length>3)
												Show.Generation[i+m].R[k]=Double.parseDouble(tokens3[3]);
											if(tokens3.length>4)
												Show.Generation[i+m].E[k]=Integer.parseInt(tokens3[4]);
											if(tokens3.length>5)
												Show.Generation[i+m].U[k]=Integer.parseInt(tokens3[5]);
										}
									}
								}
							}
						}
						
						i+=Interval_temp;
					}
					Show.Total_Generation=i-1;
					System.out.println("Tol_Gen="+Show.Total_Generation);
//					System.out.println("Fitness_Amount="+Show.Fitness_Amount);
//					System.out.println(Show.Generation[Show.Total_Generation].X[0]);
//					if(Show.Total_Generation>718)
//						System.out.println("X[2]="+Show.Generation[1].X[2]);
				}
			}
			BFR.close();
//			System.out.println("X[719]="+Show.Generation[719].X[0]);
			//放入之前的檔
			if(Show.Fitness_Amount>1)
			{
				int i=1;
//				System.out.println("X[1]="+Show.temp_Generation[1].X[0]);
				//第1個檔為多粒子時
//				System.out.println("i="+i+".....1");
				if(Show.Fitness_Amount==2 && Show.temp_Generation[1].Amount>1)
				{
					System.out.println("i="+i+".....2");
					for(int j=1;j<=temp_Total_Generation;j++)
					{
//						System.out.println("i="+i);
//						System.out.println("j="+j);
						for(int k=0;k<Show.temp_Generation[j].Amount;k++)
						{
//							System.out.println("i="+i+".....8");

							for(int m=0;m<Show.temp_Generation[j].Amount;m++)
							{
//								System.out.println("i="+i+".....9");
//								System.out.println("i+m="+(i+m));
								//若新檔eval較少，補足空缺
								if((i+m>Show.Total_Generation) && k == 0)
								{
//									System.out.println("i+m="+(i+m));
									Show.Generation[i+m]=new Generation_Initial();
									Show.Generation[i+m].Amount=Show.Generation[Show.Total_Generation].Amount+Show.temp_Generation[j].Amount-Show.temp_Generation[Show.Total_Generation].Amount;
									Show.Generation[i+m].Fitness=new double[Show.Fitness_Amount];
									Show.Generation[i+m].Particle=new int[Show.Fitness_Amount];
									Show.Generation[i+m].X=new double[Show.Generation[i].Amount];
									Show.Generation[i+m].Y=new double[Show.Generation[i].Amount];
									Show.Generation[i+m].S=new int[Show.Generation[i].Amount];
									Show.Generation[i+m].R=new double[Show.Generation[i].Amount];
									Show.Generation[i+m].E=new int[Show.Generation[i].Amount];
									Show.Generation[i+m].U=new int[Show.Generation[i].Amount];
									
									Show.Generation[i+m].Fitness[1]=Show.Generation[Show.Total_Generation].Fitness[1];
									Show.Generation[i+m].Particle[1]=Show.Generation[Show.Total_Generation].Particle[1];
									
//									System.out.println("i="+i+".....11");
//									System.out.println("Amo="+Show.Generation[i].Amount);
									for(int n=Show.temp_Generation[j].Amount;n<Show.Generation[i].Amount;n++)
									{
//										System.out.println("n="+n+".....1");
										Show.Generation[i+m].X[n]=Show.Generation[Show.Total_Generation].X[n];
//										System.out.println("n="+n+".....2");
										Show.Generation[i+m].Y[n]=Show.Generation[Show.Total_Generation].Y[n];
//										System.out.println("n="+n+".....3");
										Show.Generation[i+m].S[n]=Show.Generation[Show.Total_Generation].S[n];
//										System.out.println("n="+n+".....4");
										Show.Generation[i+m].R[n]=Show.Generation[Show.Total_Generation].R[n];
//										System.out.println("n="+n+".....5");
										Show.Generation[i+m].E[n]=Show.Generation[Show.Total_Generation].E[n];
//										System.out.println("n="+n+".....6");
										Show.Generation[i+m].U[n]=Show.Generation[Show.Total_Generation].U[n];
									}
//									System.out.println("i="+i+".....12");
								}
														
								int t1=j;
								int t2=k;
								if(m<k)
								{
									t1=j-1;
									if(t1<1)
										t1=1;
									if(j==1)
										t2=0;
								}
//									System.out.println("t1="+t1);
//									System.out.println("t2="+t2);
								Show.Generation[i+m].Fitness[0]=Show.temp_Generation[t1].Fitness[0];
								Show.Generation[i+m].Particle[0]=Show.temp_Generation[t1].Particle[0];
								Show.Generation[i+m].X[k]=Show.temp_Generation[t1].X[t2];
								Show.Generation[i+m].Y[k]=Show.temp_Generation[t1].Y[t2];
								Show.Generation[i+m].S[k]=Show.temp_Generation[t1].S[t2];
								Show.Generation[i+m].R[k]=Show.temp_Generation[t1].R[t2];
								Show.Generation[i+m].E[k]=Show.temp_Generation[t1].E[t2];
								Show.Generation[i+m].U[k]=Show.temp_Generation[t1].U[t2];
							
							}
//							System.out.println("i="+i+".....10");
						}
						i+=Show.temp_Generation[j].Amount;
//						System.out.println("/i="+i);
					}
					System.out.println("-i="+i);
					
					//新檔eval較多
					if((i+1)<Show.Total_Generation)
					{
						i++;
						for(int j=i;j<Show.Total_Generation;j++)
						{
							Show.Generation[j].Fitness[0]=Show.temp_Generation[temp_Total_Generation].Fitness[0];
							Show.Generation[j].Particle[0]=Show.temp_Generation[temp_Total_Generation].Particle[0];
							for(int k=0;k<Show.Generation[i].Amount;k++)
							{
								Show.Generation[j].X[k]=Show.temp_Generation[temp_Total_Generation].X[k];
								Show.Generation[j].Y[k]=Show.temp_Generation[temp_Total_Generation].Y[k];
								Show.Generation[j].S[k]=Show.temp_Generation[temp_Total_Generation].S[k];
								Show.Generation[j].R[k]=Show.temp_Generation[temp_Total_Generation].R[k];
								Show.Generation[j].E[k]=Show.temp_Generation[temp_Total_Generation].E[k];
								Show.Generation[j].U[k]=Show.temp_Generation[temp_Total_Generation].U[k];
							}
							i++;
						}
					}
				}
				else//第1個檔非多粒子
				{
//					System.out.println("i="+i+".....3");
					for(i=1;i<=temp_Total_Generation;i++)
					{
						//舊檔eval較多
						if(i>Show.Total_Generation)
						{
//							System.out.println("i="+i+".....4");
							Show.Generation[i]=new Generation_Initial();
							Show.Generation[i].Amount=Show.Generation[Show.Total_Generation].Amount+Show.temp_Generation[i].Amount-Show.temp_Generation[Show.Total_Generation].Amount;
							Show.Generation[i].Fitness=new double[Show.Fitness_Amount];
							Show.Generation[i].Particle=new int[Show.Fitness_Amount];
							Show.Generation[i].X=new double[Show.Generation[i].Amount];
							Show.Generation[i].Y=new double[Show.Generation[i].Amount];
							Show.Generation[i].S=new int[Show.Generation[i].Amount];
							Show.Generation[i].R=new double[Show.Generation[i].Amount];
							Show.Generation[i].E=new int[Show.Generation[i].Amount];
							Show.Generation[i].U=new int[Show.Generation[i].Amount];
							
							//填入新的
							Show.Generation[i].Fitness[Show.Fitness_Amount-1]=Show.Generation[Show.Total_Generation].Fitness[Show.Fitness_Amount-1];
							Show.Generation[i].Particle[Show.Fitness_Amount-1]=Show.Generation[Show.Total_Generation].Particle[Show.Fitness_Amount-1];
							
							for(int n=Show.temp_Generation[i].Amount;n<Show.Generation[Show.Total_Generation].Amount;n++)
							{
								Show.Generation[i].X[n]=Show.Generation[Show.Total_Generation].X[n];
								Show.Generation[i].Y[n]=Show.Generation[Show.Total_Generation].Y[n];
								Show.Generation[i].S[n]=Show.Generation[Show.Total_Generation].S[n];
								Show.Generation[i].R[n]=Show.Generation[Show.Total_Generation].R[n];
								Show.Generation[i].E[n]=Show.Generation[Show.Total_Generation].E[n];
								Show.Generation[i].U[n]=Show.Generation[Show.Total_Generation].U[n];
							}
						}
						//填入舊檔
//						System.out.println("i="+i);
						for(int n=0;n<Show.Fitness_Amount-2;n++)
						{
							Show.Generation[i].Fitness[n]=Show.temp_Generation[i].Fitness[n];
							Show.Generation[i].Particle[n]=Show.temp_Generation[i].Particle[n];
						}
						for(int n=0;n<Show.temp_Generation[i].Amount;n++)
						{
							Show.Generation[i].X[n]=Show.temp_Generation[i].X[n];
							Show.Generation[i].Y[n]=Show.temp_Generation[i].Y[n];
							Show.Generation[i].S[n]=Show.temp_Generation[i].S[n];
							Show.Generation[i].R[n]=Show.temp_Generation[i].R[n];
							Show.Generation[i].E[n]=Show.temp_Generation[i].E[n];
							Show.Generation[i].U[n]=Show.temp_Generation[i].U[n];
						}
					}
//					System.out.println("*i="+i);
					//新檔eval較多
					if(i<Show.Total_Generation)
					{
						System.out.println("i="+i+".....5");
//						System.out.println("N_Tol_Gen="+Show.Total_Generation);
//						System.out.println("O_Tol_Gen="+temp_Total_Generation);
						for(;i<=Show.Total_Generation;i++)
						{
//							System.out.println("i="+i+".....6");
							
							for(int n=0;n<Show.Fitness_Amount-1;n++)
							{
								Show.Generation[i].Fitness[n]=Show.temp_Generation[temp_Total_Generation].Fitness[n];
								Show.Generation[i].Particle[n]=Show.temp_Generation[temp_Total_Generation].Particle[n];
							}
//							System.out.println("i="+i+".....7");
//							System.out.println("Amo="+Show.temp_Generation[temp_Total_Generation].Amount);
							for(int n=0;n<Show.temp_Generation[temp_Total_Generation].Amount;n++)
							{
								Show.Generation[i].X[n]=Show.temp_Generation[temp_Total_Generation].X[n];
								Show.Generation[i].Y[n]=Show.temp_Generation[temp_Total_Generation].Y[n];
								Show.Generation[i].S[n]=Show.temp_Generation[temp_Total_Generation].S[n];
								Show.Generation[i].R[n]=Show.temp_Generation[temp_Total_Generation].R[n];
								Show.Generation[i].E[n]=Show.temp_Generation[temp_Total_Generation].E[n];
								Show.Generation[i].U[n]=Show.temp_Generation[temp_Total_Generation].U[n];
							}
//							System.out.println("i="+i+".....8");
						}
						
					}
//					System.out.println("+i="+i);
				}
				Show.Total_Generation = i-1;
			}
						
///以上，目前						
				
			System.out.println("------");

			//世代數相關
			Show.Now_Generation=1;

			Slider_Generation.setMaximum(Show.Total_Generation);

			System.arraycopy(Show.Generation, 0, Show.temp_Generation, 0, Show.Generation.length);

			for(int k=0;k<Show.Fitness_Amount;k++)
			{
				Show.Fitness_JLabel[k].setVisible(true);
				Show.Generation_JLabel[k].setVisible(true);
			}
//					System.out.println(Show.Generation[717].Amount);
//					System.out.println(Show.temp_Generation[717].Amount);
			//System.out.println(Show.Generation[1].Amount);
//					if(Show.Generation[717].Amount>30)
//						System.out.println(Show.Generation[719].Amount);
//			System.out.println(Show.Generation[1].X[0]);
//			if(Show.Fitness_Amount>1)
//				System.out.println(Show.Generation[1].X[1]);
			System.out.println("Tol_Gen="+Show.Total_Generation);

//				}
//				System.out.println(Show.Generation.length);
//				System.out.println(Show.Generation[716].Amount);
//				if(Show.Generation[716].Amount>30)
//					System.out.println(Show.Generation[1].X[2]);
//				Show.Generation[719] = new Generation_Initial();
//				Show.Generation[719].Amount = 0;
						
///以下是新的
/*新檔較少時			
			if(temp_Total_Generation>(i+Interval_temp))
			{
				for(int k=i+Interval_temp+1;k<(temp_Total_Generation+1);k++)
				{
					Show.Generation[k]=new Generation_Initial();
					Show.Generation[k].Amount=tokens2.length+Show.temp_Generation[i+k].Amount;
					Show.Generation[k].Fitness=new double[Show.Fitness_Amount];
					Show.Generation[k].Particle=new int[Show.Fitness_Amount];
					Show.Generation[k].X=new double[Show.Generation[i+k].Amount];
					Show.Generation[k].Y=new double[Show.Generation[i+k].Amount];
					Show.Generation[k].S=new int[Show.Generation[i+k].Amount];
					Show.Generation[i+k].R=new double[Show.Generation[i+k].Amount];
					Show.Generation[i+k].E=new int[Show.Generation[i+k].Amount];
					Show.Generation[i+k].U=new int[Show.Generation[i+k].Amount];
				}
				
			}
			
*/
			
			//BFR.close();
		}catch (Exception fe){ JOptionPane.showMessageDialog(null, "錯誤碼："+fe.toString()+"\n請檢查檔案。", "錯誤",JOptionPane.ERROR_MESSAGE);}
		/*
		for(int i=0;i<3;i++)
			if(Should_S_int[i]==0)
			{
				Should_S_int[i]=1;
				JOptionPane.showMessageDialog(null, "找不到："+Should_S_Str[i]+"\n請檢查檔案。", "錯誤",JOptionPane.ERROR_MESSAGE);
				break;
			}
		 */
		for(int i=0;i<3;i++)
			if(Should_S_int[i]==0)
			{
				Input_Formula.setText("找不到："+Should_S_Str[i]+"\n請重開並檢查檔案。");
			}
	}
	//捕捉關閉視窗
	public WindowAdapter MyWindowListener = new WindowAdapter(){

		//System.out.println("焦點");
		public void windowActivated(WindowEvent e)
		{

			if(Player_file!=null)
			{
				Reader_File(""+Player_file.getAbsolutePath());

				String SRF=Player_file.getName();
				SRF_Name=SRF.substring(0,SRF.lastIndexOf("."));
				Text_ReadFile.setText(""+SRF_Name);

				Reset_Panel();

				Adaptive_Color();
				Player_file=null;
			}
		}
		//System.out.println("失焦");
		public void windowDeactivated(WindowEvent e) {
		}
		public void windowClosing(WindowEvent e) {
			dispose();
		}

		public void windowClosed(WindowEvent e) {
			Player_file=null;
			SRF=null;

			if(Show.JFrame_mod==1)
				Show.JFrame_mod=0;
			Show.Progress_Change=1;

			Slider_Generation.setValue(1);
			Slider_Generation.setMaximum(1);
			Slider_Generation.setMaximum(1);

			Text_ReadFile.setText("");
			Text_Generation.setText("1/1");
			Show.Total_Generation=Show.Now_Generation=1;
			Text_Fitness.setText("0.0");
			Input_Formula.setText("0");

			Show.Button_Player.setSelected(false);
			Show.Pause=1;
			Button_Start.setIcon(Show.T[2]);

			for(int i=0;i<Show.Point_MAX;i++)
			{
				Show.Point_Label[i].setVisible(false);
			}
			Show.Postfix[0]=0;
			Show.Answer_Change=1;


		}
	};
}

class Threading extends Thread {
	public void run() {
		//System.out.println("Thread runnung...");
		long temp_time=0;
		int temp=0;
		int temp_s=0;
		while (Show.Video_mode==1)
		{
			if(System.currentTimeMillis()-temp_time>50)
			{
				temp_time=System.currentTimeMillis();
				try {
					Screenshots("EPanel_temp",Show.Video_x1,Show.Video_y1,Show.Video_x2,Show.Video_y2);
					Show.Video_out_file.writeFrame( ImageIO.read(new File( "EPanel_temp" ) ) );
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				if(temp_s++>=20)
				{
					temp_s=0;
					if(temp==0)
						Show.Button_Video.setIcon(Show.T[32]);
					else
						Show.Button_Video.setIcon(Show.T[31]);
					temp=1-temp;
				}
			}
		}

		try{
			Show.Video_out_file.close();
			new File("EPanel_temp").delete();
		}catch(Exception es){
			es.printStackTrace();
		}
		Show.Button_Video.setIcon(Show.T[30]);
	}
	//截圖
	void Screenshots(String path,int x1,int y1,int x2,int y2) throws Exception {

		Robot robot = new Robot();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rect = new Rectangle(x1,y1,x2,y2);
		BufferedImage image = robot.createScreenCapture(rect);
		ImageIO.write(image, "jpg", new File(path));

	}

}
//共用函式
class EPanel extends JFrame {
	EPanel(){}
	//讀取方程式  轉換為後置
	static void Expand_Equation(String Equation_S,double Postfix[])
	{
		//java用
		Equation_S+=";;;;;;";
		char Equation[]=Equation_S.toLowerCase().toCharArray();
		//C用
		//strcat(Equation,";;;;;;");
		//for(int i=0;i<strlen(Equation);i++)
		//	Equation[i]=tolower(Equation[i]);


		//堆疊
		double Stack[]=new double[100];
		int top=0;
		//               0  1   2   3  4   5    6   7 8sum 9prod 10s 11c 12t   13| 14PI  15X , 16i  17sum後  18prod後  19exp
		int Priority[]={0 , 3,  2,  2,  1,  1,  0,  0,  3,  3,  3,  3,  3,  0  , 0    ,  3  ,  0    ,  0       ,  0   ,  3 };

		double temp=-1;
		double dot_s=0;

		//前一個為  1=運算元  2=運算子
		int Prev=1;


		Postfix[0]=1;

		for(int i=0;i<Equation.length;i++)
		{
			//各種函數

			//sum
			if(Equation[i]=='s' && Equation[i+1]=='u' && Equation[i+2]=='m')
			{Postfix[(int)Postfix[0]++]=-17;  Stack[top++]=-8;Prev=2;i+=3;}
			//prod
			if(Equation[i]=='p' && Equation[i+1]=='r' && Equation[i+2]=='o' && Equation[i+3]=='d')
			{Postfix[(int)Postfix[0]++]=-18;  Stack[top++]=-9;Prev=2;i+=4;}
			//sin
			if(Equation[i]=='s' && Equation[i+1]=='i' && Equation[i+2]=='n')
			{Stack[top++]=-10;Prev=1;i+=3;}
			//cos
			if(Equation[i]=='c' && Equation[i+1]=='o' && Equation[i+2]=='s')
			{Stack[top++]=-11;Prev=1;i+=3;}
			//tan
			if(Equation[i]=='t' && Equation[i+1]=='a' && Equation[i+2]=='n')
			{Stack[top++]=-12;Prev=1;i+=3;}
			//exp
			if(Equation[i]=='e' && Equation[i+1]=='x' && Equation[i+2]=='p')
			{Stack[top++]=-19;Prev=1;i+=3;}
			//pi
			if(Equation[i]=='p' && Equation[i+1]=='i')
			{Postfix[(int)Postfix[0]++]=-14;Prev=2;i+=2;}

			if(Equation[i]=='x')
			{Stack[top++]=-15;Prev=1;}

			if(Equation[i]=='i')
			{Postfix[(int)Postfix[0]++]=-16;Prev=2;}

			//數字
			if(Equation[i]>='0' && Equation[i]<='9')
			{
				if(temp==-1)temp=0;

				//在小數點後  每一次除更大的數
				if(dot_s>0)
				{
					temp=temp+(double)(Equation[i]-'0')/dot_s;
					dot_s*=10;
				}
				else
					temp=temp*10+(Equation[i]-'0');
				Prev=2;
			}
			//小數點
			else if(Equation[i]=='.')
			{
				dot_s=10;
			}
			//數字完結  塞進堆疊
			else if(temp>=0)
			{
				Postfix[(int)Postfix[0]++]=temp;
				temp=-1;
				dot_s=0;
			}
			//絕對值
			if(Equation[i]=='|')
			{   //判定為開頭
				if(Prev==1)
				{
					Stack[top++]=-13;//push();
				}
				//收尾
				else
				{
					while(top>0)
					{
						if(Stack[top-1]!=-13)
							Postfix[(int)Postfix[0]++]=Stack[--top];//pop();
						else
						{
							top--;
							break;
						}
					}

					Prev=2;
					Postfix[(int)Postfix[0]++]=-13;
				}
			}
			//開頭括號
			if(Equation[i]=='(' || Equation[i]=='[')
			{
				Stack[top++]=-6;//push();
				Prev=1;
			}
			//收尾括號
			if(Equation[i]==')' || Equation[i]==']')
			{
				while(top>0)
				{
					if(Stack[top-1]!=-6)
						Postfix[(int)Postfix[0]++]=Stack[--top];//pop();
					else
					{
						top--;
						break;
					}
				}
				Prev=2;
			}

			//其他運算元
			char Operand[]={' ','^','*','/','+','-'};
			for(int j=1;j<6;j++)
				if(Equation[i]==Operand[j])
				{
					while(top>0)
					{
						if(Priority[-(int)Stack[top-1]]>=Priority[j])
							Postfix[(int)Postfix[0]++]=Stack[--top];//pop();
						else
							break;
					}
					Stack[top++]=-j;//push();
					Prev=1;
				}

			//已到方程式結尾
			if(Equation[i]==';')
				break;

		}
		if(temp>0)
			Postfix[(int)Postfix[0]++]=temp;
		while(top>0)
			Postfix[(int)Postfix[0]++]=Stack[--top];//pop();
	}
	//整數後置+XY 計算答案
	static double Formula(double Postfix[],double X[],int D,int in)
	{
		double Answer[]=new double[100];
		int Answer_s=0;

		//-----

		for(int i=1;i<Postfix[0];i++)
		{
			//數字
			if(Postfix[i]>=0)
				Answer[Answer_s++]=Postfix[i];
			// i
			else if(Postfix[i]==-16)
				Answer[Answer_s++]=in;
			//圓周率pi
			else if(Postfix[i]==-14)
				Answer[Answer_s++]=Math.PI;
			//sigma
			else if(Postfix[i]==-17)
			{
				int temp_j=i+1;
				double Postfix_n[]=new double[100];
				for(int j=i+1;j<Postfix[0];j++)
				{
					temp_j=j;
					if(Postfix[j]==-8)break;
					Postfix_n[j-i]=Postfix[j];
				}
				Postfix_n[0]=temp_j-i;

				i=temp_j;

				double Sum=0;
				for(int j=1;j<=D;j++)
					Sum+=Formula(Postfix_n,X,D,j);

				Answer[Answer_s++]=Sum;

			}
			//mcl
			else if(Postfix[i]==-18)
			{
				int temp_j=i+1;
				double Postfix_n[]=new double[100];
				for(int j=i+1;j<Postfix[0];j++)
				{
					temp_j=j;
					if(Postfix[j]==-9)break;
					Postfix_n[j-i]=Postfix[j];
				}
				Postfix_n[0]=temp_j-i;

				i=temp_j;

				double Sum=1;
				for(int j=1;j<=D;j++)
					Sum*=Formula(Postfix_n,X,D,j);

				Answer[Answer_s++]=Sum;

			}
			//函數
			else if(Postfix[i]==-10)
				Answer[Answer_s-1]=Math.sin(Answer[Answer_s-1]);
			else if(Postfix[i]==-11)
				Answer[Answer_s-1]=Math.cos(Answer[Answer_s-1]);
			else if(Postfix[i]==-12)
				Answer[Answer_s-1]=Math.tan(Answer[Answer_s-1]);
			else if(Postfix[i]==-13)
				Answer[Answer_s-1]=Math.abs(Answer[Answer_s-1]);
			else if(Postfix[i]==-19)
				Answer[Answer_s-1]=Math.exp(Answer[Answer_s-1]);
			//X  (從1開始到D)
			else if(Postfix[i]==-15)
			{
				if((int)Answer[Answer_s-1]<1)Answer[Answer_s-1]=D;
				if((int)Answer[Answer_s-1]>D)Answer[Answer_s-1]=1;
				Answer[Answer_s-1]=X[(int)Answer[Answer_s-1]-1];
			}
			//其他運算元
			else
			{
				Answer[Answer_s-2]=Operational((double)Answer[Answer_s-2],(double)Answer[Answer_s-1],Postfix[i]);
				Answer_s--;
			}

		}

		return Answer[0];
	}
	//a 運算元  b
	static double Operational(double a,double b,double Operand)
	{
		if(Operand==-1)return Math.pow(a,b);
		if(Operand==-2)return a*b;
		if(Operand==-3)return a/b;
		if(Operand==-4)return a+b;
		if(Operand==-5)return a-b;
		return 0;
	}
	//自適應調整顏色上下限
	void Adaptive_Color()
	{
		double XYtoX[]=new double[2];
		double  Total_sum=0;
		double  Total_square=0;
		double Standard_Deviation=0;
		
		int S=101;

		double SB_X,SB_Y;

		//SB_X

		if(Show.Dimension_Mode==2)
		{
			for(int i=0;i<S;i++)
			{
				double tempY=0,tempX=0;
				
				tempX=i*Show.Border_L/50+Show.Border_X-Show.Border_L;
				//Show.Border_Y_N+(Show.Border_Y_M-Show.Border_Y_N)/(S-1)*i;
				for(int j=0;j<S;j++)
				{
					tempY=(101-j)*Show.Border_L/50+Show.Border_Y-Show.Border_L;
					//Show.Border_X_N+(Show.Border_X_M-Show.Border_X_N)/(S-1)*j;
	
					XYtoX[0]=tempX;
					XYtoX[1]=tempY;
	
					double temp=Formula(Show.Postfix,XYtoX,2,0);
					//System.out.print("  "+temp);
					Total_sum+=temp;
					Total_square+=temp*temp;
				}
				//System.out.println("");
			}
			Total_sum/=(S*S);
			Total_square/=(S*S);
			
			Standard_Deviation=Math.sqrt(Total_square-Total_sum*Total_sum);
		}
		else if(Show.Dimension_Mode==1)
		{
			/*Total_sum = Show.Border_Y_L;
			Standard_Deviation = Total_sum/2;*/
			
			for(int i=0;i<S;i++)
			{
				double tempY=0,tempX=0;
				
				tempX=i*Show.Border_L/50+Show.Border_X-Show.Border_L;
				//Show.Border_Y_N+(Show.Border_Y_M-Show.Border_Y_N)/(S-1)*i;
				for(int j=0;j<S;j++)
				{
					tempY=0;
					//Show.Border_X_N+(Show.Border_X_M-Show.Border_X_N)/(S-1)*j;
	
					XYtoX[0]=tempX;
					XYtoX[1]=tempY;
	
					double temp=Formula(Show.Postfix,XYtoX,1,0);
					//System.out.print("  "+temp);
					Total_sum+=temp;
					Total_square+=temp*temp;
				}
				//System.out.println("");
			}
			Total_sum/=(S*S);
			Total_square/=(S*S);
			
			Standard_Deviation=Math.sqrt(Total_square-Total_sum*Total_sum);
		}

		/*
			System.out.println("平均 "+Total_sum);
			System.out.println("標準差 "+Standard_Deviation);
		 */

		//System.out.println("上限 "+(Total_sum+Standard_Deviation));
		//System.out.println("下限 "+(Total_sum-Standard_Deviation));

		Show.Input_Color_Caps.setText(""+(Total_sum+Standard_Deviation*2));
		Show.Input_Color_Limit.setText(""+(Total_sum-Standard_Deviation*2));

		//設定顏色上下限
		double tempa=Show.Color_Limit,tempb=Show.Color_Caps;
		try
		{
			tempa=Double.parseDouble(Show.Input_Color_Limit.getText());
			tempb=Double.parseDouble(Show.Input_Color_Caps.getText());
		}
		catch (NumberFormatException te)
		{}
		finally
		{
			Show.Color_Limit=tempa;
			Show.Color_Caps=tempb;
		}


		//Show.Input_Color_Limit.setText("0");
		//重製解空間顏色
		Show.Answer_Change=1;
	}

	static void Screen(int x1,int y1,int x2,int y2)
	{
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
		Date current = new Date();
		//System.out.println(sdFormat.format(current));
		try{
			Screenshots("EPanelcam_"+sdFormat.format(current)+".jpg",x1,y1,x2,y2);
		}catch(Exception es){
			es.printStackTrace();
		}

	}
	//截圖
	static void Screenshots(String path,int x1,int y1,int x2,int y2) throws Exception {

		Robot robot = new Robot();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rect = new Rectangle(x1,y1,x2,y2);
		BufferedImage image = robot.createScreenCapture(rect);
		ImageIO.write(image, "jpg", new File(path));

	}
	//存檔時詢問覆蓋問題
	class MyChooser extends JFileChooser {
		MyChooser(String path) {
			super(path);
		}
		/*同樣是覆蓋approveSelection方法，首先獲得輸入檔的路徑，然後判斷其是否在目前的目錄下，如果存在，那麼彈出對話方塊詢問是否需要覆蓋當前檔，如果選擇取消，則回到保存對話方塊，用戶可以繼續進行保存的操作。*/
		public void approveSelection() {
			File file = this.getSelectedFile();
			if (file.exists())
			{
				int copy = JOptionPane.showConfirmDialog(null,"檔案"+file.getName()+"已經存在。\n您要取代它嗎?","EPanel",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
				if (copy == JOptionPane.YES_OPTION)
					super.approveSelection();
			}
			else
				super.approveSelection();
		}
	}
}
