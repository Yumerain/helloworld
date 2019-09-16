package db;

import java.text.DecimalFormat;

import com.jfinal.plugin.activerecord.Db;

@SuppressWarnings("serial")
public class TradeOrder extends BaseTradeOrder<TradeOrder>{
	
	public static final TradeOrder dao = new TradeOrder().dao();
	
	public TradeOrder selectByOrderNo(String orderNo) {
		return super.findFirst("select * from trade_order where orderNo = ?",orderNo);
	}
	
	public TradeOrder selectByOrderNoForUpdate(String orderNo) {
		return super.findFirst("select * from trade_order where orderNo = ? for update",orderNo);
	}
	
	public java.lang.String getResp() {
		return getRespCode()+"-"+getRespMsg();
	}

	public java.lang.String getAmountShow() {
		return new DecimalFormat("0.00").format(getAmount()/100.0);
	}

	public java.lang.String getMerchFeeShow() {
		return new DecimalFormat("0.00").format(getMerchFee()/100.0);
	}
	
	public void addBalance(String id, Long val) {
		Db.update("update trade_order set balance = (balance + ?) where id = ?", val, id);
	}

}
