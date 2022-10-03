import java.util.HashMap;
import java.util.Map;

public class TestNSOCR {

	public static void main(String[] args) {
		if (NSOCR.Engine.IsDllLoaded()) {
			System.out.println("NSOCR library loaded successfully !!");
		} else {
			System.out.println("NSOCR library is not loaded !!");
			System.exit(1);
		}
		
		NSOCR.Engine.Engine_SetLicenseKey("AB2A4DD5FF2A");
		NSOCR.HCFG CfgObj = new NSOCR.HCFG();
		NSOCR.HOCR OcrObj = new NSOCR.HOCR();
		NSOCR.HIMG ImgObj = new NSOCR.HIMG();
		StringBuffer sb = new StringBuffer();
		
		NSOCR.Engine.Engine_InitializeAdvanced(CfgObj, OcrObj, ImgObj);
		NSOCR.Engine.Img_LoadFile(ImgObj, "C:\\Users\\dhine\\JAVA_WS\\NSOCR\\NKING.PNG");
		NSOCR.Engine.Img_OCR(ImgObj, NSOCR.Constant.OCRSTEP_FIRST, NSOCR.Constant.OCRSTEP_LAST, NSOCR.Constant.OCRFLAG_NONE);
		NSOCR.Engine.Img_GetImgText(ImgObj, sb, NSOCR.Constant.FMT_EXACTCOPY);
		NSOCR.Engine.Engine_Uninitialize();
		System.out.println(sb.toString());
		
		Map<String,String> ppDetails=new HashMap<String,String>();
		String[] lines=sb.toString().split("\r\n");
		for(int i=0;i<lines.length;i++) {
			if(lines[i].contains("Passport No")) {
				String[] line1=lines[i].split("\\s+");
				String[] line2=lines[i+1].split("\\s+");
				ppDetails.put(line1[1]+" "+line1[2], line2[1]);
				ppDetails.put(line1[3]+" "+line1[4], line2[2]);
			}else if(lines[i].contains("Name")) {
				ppDetails.put(lines[i], lines[i+1]);
			}else if(lines[i].contains("Nationality")) {
				String[] line1=lines[i].split("\\s+");
				String[] line2=lines[i+1].split("\\s+");
				ppDetails.put(line1[0], line2[0]);
				ppDetails.put(line1[1], line2[1]);
			}else if(lines[i].contains("Date of birth")) {
				String[] line1=lines[i].split("\\s+");
				String[] line2=lines[i+1].split("\\s+");
				ppDetails.put(line1[0]+" "+line1[1]+" "+line1[2], line2[0]);
			}else if(lines[i].contains("Date of issue")) {
				String[] line1=lines[i].split("\\s+");
				String[] line2=lines[i+1].split("\\s+");
				ppDetails.put(line1[0]+" "+line1[1]+" "+line1[2], line2[0]+"/"+line2[1]+"/"+line2[2]);
				ppDetails.put(line1[3]+" "+line1[4]+" "+line1[5], line2[3]+"/"+line2[4]+"/"+line2[5]);
			}
		}
		System.out.println(ppDetails);
	}
}
