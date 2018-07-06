//package opcv;
//
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//import javax.imageio.ImageIO;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.highgui.Highgui;
//import org.opencv.highgui.VideoCapture;
//
//public class Camera extends JPanel {
//
//	private static final long serialVersionUID = 1L;
//
//	public static void main(String[] args) {
//		//注意程序运行的时候需要在VM option添加该行 指明opencv的dll(so)文件所在路径  
//		//-Djava.library.path=$PROJECT_DIR$\opencv\x64
//       
//        JPanel jp = new Camera();
//        JFrame win = new JFrame();
//        win.setTitle("opencv捕捉摄像头");
//        win.add(jp);
//        win.pack();
//        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        
//        Toolkit tk = Toolkit.getDefaultToolkit();
//        Dimension ss = tk.getScreenSize();
//        int x = (int) ((ss.getWidth() - win.getWidth())/2);
//        int y = (int) ((ss.getHeight() - win.getHeight())/2);
//        win.setLocation(x, y);
//        
//        win.setVisible(true);
//        
//        new Thread(() -> {
//        	while(true){
//        		jp.repaint();
//            	Thread.yield();
//        	}
//        }).start();
//    	
//	}
//
//	
//    private VideoCapture vc;
//    
//	public Camera(){
//		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		System.load("/home/rainleaf/workspace/eclipse-jee/HelloWorld/lib/opcv_so/libopencv_java2413.so");
//		vc = new VideoCapture();
//        if(vc.open(0) == false) {
//        	System.out.println("打开摄像头失败！");
//        	return;
//        }
//        double w = vc.get(Highgui.CV_CAP_PROP_FRAME_WIDTH);
//        double h = vc.get(Highgui.CV_CAP_PROP_FRAME_HEIGHT);
//        setPreferredSize(new Dimension((int)w, (int)h));
//	}
//	
//	@Override
//	public void paint(Graphics g) {
//		Mat mat = new Mat();
//    	vc.read(mat);
//    	BufferedImage img = Mat2Img(mat, ".png");
//    	g.drawImage(img, 0, 0, null);
//	}
//	
//	public static BufferedImage Mat2Img(Mat mat, String fileExtension) {
//		MatOfByte mob = new MatOfByte();
//
//		Highgui.imencode(fileExtension, mat, mob);
//		// convert the "matrix of bytes" into a byte array
//		byte[] byteArray = mob.toArray();
//		BufferedImage bufImage = null;
//		try {
//			InputStream in = new ByteArrayInputStream(byteArray);
//			bufImage = ImageIO.read(in);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return bufImage;
//	}
//}