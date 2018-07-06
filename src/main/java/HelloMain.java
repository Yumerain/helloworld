
public class HelloMain {

	public static void main(String[] args) {
		String x = "{\"amount\":\"1000\",\"bankType\":\"ABC\",\"cardNo\":\"6228481749154730577\",\"cardType\":\"0\",\"certNo\":\"130733198610101273\",\"certType\":\"01\",\"goodsName\":\"充值\",\"merchCode\":\"000088880000288\",\"merchOrderNo\":\"test0000001341\",\"name\":\"王小宏\",\"notifyUrl\":\"http://localhost:9213/LHPayQuickPay/PayNotify\",\"orderPeriod\":\"10\",\"orgCode\":\"66600197\",\"payType\":\"quick_pay_set\",\"phone\":\"15652225089\",\"remark\":\"快捷测试\",\"userId\":\"test1\",\"version\":\"V1.0\",\"cvn2\":\"123\",\"expired\":\"0120\",\"sign\":\"BB1B14D878C93AF3C751E6BB6DB349F7\"}";
		System.out.println(x);
	}
	
	/** 报文字段屏蔽专用方法 */
	public static String hide(String src, String... kws) {
		StringBuilder sb = new StringBuilder(src);
		for (int i = 0; i < kws.length; i++) {
			String kw = "\""+kws[i]+"\":\"";
			int start = sb.indexOf(kw);
			int end = sb.indexOf("\"", start+kw.length());
			StringBuilder str = new StringBuilder();
			for (int k = 0; k < (end - start-kw.length()); k++) {
				str.append('*');
			}
			sb.replace(start+kw.length(), end, str.toString());
		}
		return sb.toString();
	}
}
