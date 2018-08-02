package com.hh.projectxx.base.manager;


import com.hh.projectxx.base.db.entity.CoinBill;
import com.hh.projectxx.base.db.entity.CoinConsume;
import com.hh.projectxx.base.db.entity.User;
import com.hh.projectxx.base.db.field.CoinBillType;
import com.hh.projectxx.base.db.field.CoinConsumeType;
import com.hh.projectxx.base.db.mapper.CoinBillMapper;
import com.hh.projectxx.base.db.mapper.CoinConsumeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用于管理和计算用户积分系统的manager
 */
@Service
public class UserCoinManager {
	private static int DAILY_BASE_INCOME = 1000;
	private static int DAILY_GROWTH = 100;
	private static int DAILY_LIMIT = 2000;
	
	private static Logger logger = LoggerFactory.getLogger(UserCoinManager.class);
	@Resource
	private CoinBillMapper coinBillMapper;
	
	@Resource
	private CoinConsumeMapper consumeMapper;
	
	@Resource
	private UserManager userManager;
	
	/**
	 * 当增加金币时使用此方法,自动更新user表
	 * @param userId 用户id
	 * @param type 类型，增加原因
	 * @param amount 数量
	 * 返回账单结果
	 */
	@Transactional
	public boolean earnNewCoin(User user, CoinBillType type, int amount) {
		if (type == null) {
			type = CoinBillType.INCOME_LOGIN;
		}
		if (user == null) {
			return false;
		}
		if (amount < 0) {
			logger.error("cannot add amount which is less than 0");
			return false;
		}
		CoinBill bill = coinBillMapper.selectLatestRecordByUserId(user.getId());
		if (bill == null) {   ///之前无记录，第一次增加
			bill = new CoinBill();
			bill.setUserId(user.getId());
			bill.setAmount(amount);
			bill.setCreateTime(new Date());
			bill.setRemain(amount);
			bill.setStatus(0);
			bill.setType(type.value());
			coinBillMapper.insert(bill);
			user.setCoinCount(bill.getRemain());
			userManager.updateUser(user);
			return true;
		}else {
			int currentAmount = bill.getRemain();
			CoinBill newBill = new CoinBill();
			newBill.setRemain(currentAmount + amount);
			newBill.setAmount(amount);
			newBill.setCreateTime(new Date());
			newBill.setStatus(0);
			newBill.setType(type.value());
			newBill.setUserId(user.getId());
			coinBillMapper.insert(newBill);
			user.setCoinCount(newBill.getRemain());
			userManager.updateUser(user);
			return true;
		}
	}
	
	/**
	 * 花费金币时使用， amount必须小于0,自动更新user表
	 * @param userId
	 * @param type
	 * @param amount
	 * @return
	 */
	@Transactional
	public boolean spendCoin(User user, CoinBillType type, int amount) {
		if (type == null) {
			type = CoinBillType.OUTCOME;
		}
		if (user == null) {
			return false;
		}
		if (amount > 0) {
			logger.error("the amount must be less than 0");
			return false;
		}
		CoinBill bill = coinBillMapper.selectLatestRecordByUserId(user.getId());
		if (bill == null) {   ///之前无记录不允许消费
			return false;
		}else {
			int currentAmount = bill.getRemain();
			if (currentAmount + amount < 0) {  ///余额不足
				logger.info("no enough coins;user id is " + user.getId());
				return false;
			}
			CoinBill newBill = new CoinBill();
			newBill.setRemain(currentAmount + amount);
			newBill.setAmount(amount);
			newBill.setCreateTime(new Date());
			newBill.setStatus(0);
			newBill.setType(type.value());
			newBill.setUserId(user.getId());
			coinBillMapper.insert(newBill);
			user.setCoinCount(newBill.getRemain());
			userManager.updateUser(user);
			return true;
		}
	}
	
	/**
	 * 获取每日连续登陆的奖励，第一日1000，每日递增100
	 * 第二日1100以此类推，上限2000
	 * @param userId
	 * @return 返回null说明奖励失败，否则返回奖励数值,数值0说明已经领取
	 */
	
	public Integer getLoginIncome(User user) {
		if (user == null) {
			return null;
		}
		CoinBill bill = coinBillMapper.selectLatestLoginRecordByUserId(user.getId());  ///选择上次login奖励的账单
		if (bill == null) {  ///第一次登陆奖励
			earnNewCoin(user, CoinBillType.INCOME_LOGIN, DAILY_BASE_INCOME);
			return DAILY_BASE_INCOME;
		}
		Date lastLogin = bill.getCreateTime();
		int lastPaid = bill.getAmount();
		Calendar lastLoginCalendar = Calendar.getInstance();
		lastLoginCalendar.setTime(lastLogin);
		
		Calendar current = Calendar.getInstance();
		Calendar today = Calendar.getInstance();	//今天起始点
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		//  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set( Calendar.HOUR_OF_DAY, 0);
		today.set( Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		Calendar yesterday = Calendar.getInstance();	//昨天起始点
		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
		yesterday.set( Calendar.HOUR_OF_DAY, 0);
		yesterday.set( Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);

		if(lastLoginCalendar.after(today)){///上次登录领取在今天，不允许再次领取
			logger.info("today's salary paid, no need to contine for user " + user.getId());
			return 0;
			
		}else if(lastLoginCalendar.before(today) && lastLoginCalendar.after(yesterday)){ ///上次领取是昨天，今天属于连续领取，有增量
			int salary = lastPaid + DAILY_GROWTH;
			if (salary > DAILY_LIMIT) {   ///不允许超过领取上限
				salary = DAILY_LIMIT;
			}
			earnNewCoin(user, CoinBillType.INCOME_LOGIN, salary);
			logger.info("continue get salary amount " + salary + " for user " + user.getId());
			return salary;
		}else{ ///上次领取是1天之前，回归领取起点
			int salary = DAILY_BASE_INCOME;
			earnNewCoin(user, CoinBillType.INCOME_LOGIN, salary);
			logger.info("start get salary amount " + salary + " for user " + user.getId());
			return salary;
		}
		
	}
	
	/**
	 * 检查用户是否有某一特定资源的访问权限，如果曾经使用金币购买返回true，没有则为false
	 * @param user
	 * @param type
	 * @param resourceId
	 * @return 是否有记录
	 */
	public boolean checkHasResourceRecord(User user, CoinConsumeType type, int resourceId) {
		CoinConsume param = new CoinConsume();
		if (user == null && type == null) {
			return false;
		}
		param.setUserId(user.getId());
		param.setType(type.value());
		param.setResourceId(resourceId);
		List<CoinConsume> result = consumeMapper.selectByParams(param);
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 花费金币获取资源的接口
	 * @param user
	 * @param type
	 * @param resourceId
	 * @param price 需要传入的花费金币数(正数)
	 * @return
	 */
	public boolean getResourceByCoin(User user, CoinConsumeType type, int resourceId, int price) {
		if (user == null || type == null) {
			return false;
		}
		if (spendCoin(user, CoinBillType.OUTCOME, -price)) {
			CoinConsume consume = new CoinConsume();
			consume.setBillId(coinBillMapper.selectLatestRecordByUserId(user.getId()).getId());
			consume.setCreateTime(new Date());
			consume.setResourceId(resourceId);
			consume.setType(type.value());
			consume.setStatus(0);
			consume.setUserId(user.getId());
			consumeMapper.insert(consume);
			return true;
			
		}else {
			logger.info("pay coin failed,cannot get resource");
			return false;
		}
		
	}
}
