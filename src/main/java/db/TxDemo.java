package db;

import java.sql.Connection;
import java.util.Properties;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.zaxxer.hikari.HikariDataSource;


public class TxDemo {

	HikariDataSource ds = new HikariDataSource();
	ActiveRecordPlugin arp;
	
	public TxDemo() {
		ds.setJdbcUrl("jdbc:mysql://119.23.129.7:3306/baitiao_trade");
		ds.setUsername("payuser");
		ds.setPassword("payuser123");
		Properties prop = new Properties();
		prop.setProperty("useSSL", "false");
		ds.setDataSourceProperties(prop);
		
	    arp = new ActiveRecordPlugin(ds);
		arp.addMapping("trade_order", TradeOrder.class);
		arp.start();
	}
	@Override
	protected void finalize() throws Throwable {
	    ds.close();
		super.finalize();
	}
	
	public void test() {
		// F960706661520023552
		// F936691586429919232
		
		new Thread(()->{
			System.out.println("thread111");
			Db.tx(Connection.TRANSACTION_READ_UNCOMMITTED, ()->{
				System.out.println("o1 select...");
				TradeOrder o1 = TradeOrder.dao.selectByOrderNoForUpdate("F960706661520023552");
				System.out.println("o1-amount:" + o1.getAmountShow());
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				o1.setAmount(o1.getAmount()-1);
				o1.update();
				return true;
			});
			TradeOrder o1 = TradeOrder.dao.selectByOrderNo("F960706661520023552");
			System.out.println("o1-amount:" + o1.getAmountShow());
		}).start();
		
		new Thread(()->{
			System.out.println("thread222");
			Db.tx(Connection.TRANSACTION_READ_UNCOMMITTED, ()->{
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("o2 select...");
				TradeOrder o2 = TradeOrder.dao.selectByOrderNoForUpdate("F960706661520023552");
				System.out.println("o2-amount:" + o2.getAmountShow());
				o2.setAmount(o2.getAmount()-1);
				o2.update();
				return true;
			});
			TradeOrder o1 = TradeOrder.dao.selectByOrderNo("F960706661520023552");
			System.out.println("o2-amount:" + o1.getAmountShow());
		}).start();
	}

	public static void main(String[] args) {
		new TxDemo().test();
	}
	
}
