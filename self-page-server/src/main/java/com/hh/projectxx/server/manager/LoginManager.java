package com.hh.projectxx.server.manager;


import com.hh.projectxx.base.common.ServiceException;
import com.hh.projectxx.base.db.entity.User;
import com.hh.projectxx.base.db.mapper.UserMapper;
import com.hh.projectxx.base.util.AuthUtil;
import com.hh.projectxx.base.util.CookieUtil;
import com.hh.projectxx.base.util.CryptUtil;
import com.hh.projectxx.base.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class LoginManager {
	private Logger logger = LoggerFactory.getLogger(LoginManager.class);
	
	@Resource
	private UserMapper userMapper;
	
//	@Resource
//	private AdminOperLogMapper adminOperLogMapper;
//	
//	@Resource
//	private AdminPermissionMapper adminPermissionMapper;
//	
//	@Resource
//	private AdminRoleMapper adminRoleMapper;
//	
//	@Resource
//	private AdminRolePermissionMapper adminRolePermissionMapper;
//	
//	@Resource
//	private AdminUserRoleMapper adminUserRoleMapper;
//	//---------------------------------------------------------------------------------------------------------------
	public User getUser(int uid) {
		return userMapper.selectByPrimaryKey(uid);
	}
	
	public User getUserByPassport(String passport) {
		return userMapper.selectByPassport(passport);
	}
//	
//	public List<AdminUser> listAdminUser(Integer status) {
//		AdminUser record = new AdminUser();
//		record.setStatus(status);
//		return adminUserMapper.selectByParams(record);
//	}
//	
//	public List<AdminUser> selectUserByParams(String passport, RowBounds bounds){
//		HashMap<String, Object> record = new HashMap<String, Object>();
//		if(passport != null){
//			record.put("passport", passport);
//		}
//
//		return adminUserMapper.selectUserByParams(record, bounds);
//	}
//	
//	public Integer countOfSelectUserByParams(String passport){
//		HashMap<String, Object> record = new HashMap<String, Object>();
//		if(passport != null){
//			record.put("passport", passport);
//		}
//		return adminUserMapper.countOfSelectUserByParams(record);
//	}
//	
//	public boolean insertAdminUser(String passport, String name, String pwd, String[] assignedRoleIds) {
//		AdminUser record = new AdminUser();
//		record.setPassport(passport);
//		record.setName(name);
//		record.setPassword(CryptUtil.sha1(pwd));
//		record.setStatus(AdminStatus.NORMAL.value());
//		record.setCreateTime(new Date());
//		int row = adminUserMapper.insert(record);
//		if(row == 0){
//			return false;
//		}
//		Integer uid = record.getId();
//		return assignRoleToUser(uid, assignedRoleIds);
//	}
//	
//	public boolean updateAdminUser(Integer uid, String name, String[] assignedRoleIds) {
//		AdminUser record = new AdminUser();
//		record.setId(uid);
//		record.setName(name);
//		int row = adminUserMapper.updateByPrimaryKeySelective(record);
//		if(row == 0){
//			return false;
//		}
//		return assignRoleToUser(uid, assignedRoleIds);
//	}
//	
//	public boolean deleteAdminUser(Integer uid) {
//		
//		int row = adminUserMapper.deleteByPrimaryKey(uid);
//		if(row == 0){
//			return false;
//		}
//		row = adminUserRoleMapper.deleteByUserId(uid);
//		return true;
//	}
//	
//	public boolean assignRoleToUser(Integer uid, String[] assignedIds) {
//		
//		adminUserRoleMapper.deleteByUserId(uid);
//		if(assignedIds == null){
//			return true;
//		}
//		for(String rId : assignedIds){
//			Integer roleId = NumberUtil.getInteger(rId);
//			if(roleId == null){
//				return false;
//			}
//			AdminUserRoleKey record = new AdminUserRoleKey();
//			record.setRoleId(roleId);
//			record.setUid(uid);
//			int row = adminUserRoleMapper.insert(record);
//			if(row == 0){
//				return false;
//			}
//		}
//		return true;
//	}
//	
//	public boolean comparePassword(int uid, String pwd){
//		AdminUser user = getAdminUser(uid);
//		return CryptUtil.sha1(pwd).equals(user.getPassword());
//	}
//
//	
//	public int updatePassword(int uid, String pwd){
//		AdminUser record = new AdminUser();
//		record.setId(uid);
//		record.setPassword(CryptUtil.sha1(pwd));
//		return adminUserMapper.updateByPrimaryKeySelective(record);
//	}
//
	public int adminLogin(HttpServletRequest request, String passport, String pwd, String ip) throws ServiceException {
		if (StringUtil.isEmpty(passport) || StringUtil.isEmpty(pwd)) {
			throw new ServiceException("用户名密码错误，登录失败");
		}
		User user = getUserByPassport(passport.toLowerCase());
		if (user == null) {
			throw new ServiceException("用户名密码错误，登录失败");
		}
		int uid = user.getId();
		String password = user.getPassword();
		if (CryptUtil.sha1(pwd).equals(password)) {
			
			request.getSession().setAttribute(AuthUtil.USER_KEY, uid);
			
//			List<AdminPermission> permissionList = getAdminPermissionByUid(uid);
//			if(permissionList!=null && permissionList.size()>0) {
//				HashSet<String> urlSet = new HashSet<String>();
//				for(AdminPermission permission:permissionList) {
//					urlSet.add(permission.getOperation());
//				}
//				request.getSession().setAttribute(AuthUtil.ADMIN_USER_PERMISSION, urlSet);
//			}
			logger.info("login success, passport: " + passport);
			return uid;
		}
		logger.warn("login failed, passport: " + passport);

		throw new ServiceException("用户名密码错误，登录失败");
	}
	
//
//	//---------------------------------------------------------------------------------------------------------------
//	public AdminRole getAdminRoleById(Integer id) {
//		return adminRoleMapper.selectByPrimaryKey(id);
//	}
//	
//	public List<AdminRole> getAdminRoleByUid(Integer uid) {
//		List<Integer> roleIds = adminUserRoleMapper.selectAdminRoleIdsByUid(uid);
//		if(roleIds == null){
//			return null;
//		}
//		List<AdminRole> roles = new ArrayList<AdminRole>();
//		for(Integer id : roleIds){
//			AdminRole role = getAdminRoleById(id);
//			roles.add(role);
//		}
//		return roles;
//	}
//	
////	
////	public List<AdminRole> listAdminRole(String name) {
////		Map<String, Object> params = new HashMap<String, Object>();
////		params.put("name", name);
////		return AdminDao.getAdminRole(params);
////	}
//	
//	public List<AdminRole> selectAllRoles(){
//		return adminRoleMapper.selectAllRoles();
//	}
//	
//	public boolean assignPermissionToRole(Integer roleId, String[] assignedIds) {
//		if(assignedIds == null ){
//			return true;
//		}
//		for(String pid : assignedIds){
//			Integer permissionId = NumberUtil.getInteger(pid);
//			if(permissionId == null){
//				return false;
//			}
//			AdminRolePermissionKey record = new AdminRolePermissionKey();
//				
//			record.setPermissionId(permissionId);
//			record.setRoleId(roleId);
//			int row = adminRolePermissionMapper.insert(record);
//			if(row == 0){
//				return false;
//			}
//		}
//		return true;
//	}
//	
//	public boolean insertAdminRole(String roleName, String[] assignedPermissionIds) {
//		AdminRole record = new AdminRole();
//		
//		record.setName(roleName);
//		int row = adminRoleMapper.insert(record);
//		if(row == 0){
//			return false;
//		}
//		
//		Integer roleId = record.getId();
//		return assignPermissionToRole(roleId, assignedPermissionIds);
//	}
//	
//	public boolean updateAdminRole(Integer roleId, String roleName, String[] assignedPermissionIds) {
//		AdminRole record = new AdminRole();
//		
//		record.setId(roleId);
//		record.setName(roleName);
//		int row = adminRoleMapper.updateByPrimaryKey(record);
//		if(row == 0){
//			return false;
//		}
//		
//		row = adminRolePermissionMapper.deleteByRoleId(roleId);
//		
//		return assignPermissionToRole(roleId, assignedPermissionIds);
//	}
//	
//	public int deleteAdminRolePermission(Integer roleId, Integer permissionId){
//		AdminRolePermissionKey record = new AdminRolePermissionKey();
//		
//		record.setPermissionId(permissionId);
//		record.setRoleId(roleId);
//		return adminRolePermissionMapper.deleteByPrimaryKey(record);
//	}
//	
//	public boolean deleteAdminRole(Integer roleId) {
//		int row = adminRoleMapper.deleteByPrimaryKey(roleId);
//		if(row == 0){
//			return false;
//		}
//		adminUserRoleMapper.deleteByRoleId(roleId);
//		return true;
//	}
//	
//	//---------------------------------------------------------------------------------------------------------------
//	public AdminPermission getAdminPermission(int id) {
//		return adminPermissionMapper.selectByPrimaryKey(id);
//	}
////	
////	public List<AdminPermission> getAdminPermissionByRoleId(int roleId) {
////		return AdminDao.getAdminPermissionByRoleId(roleId);
////	}
//	
//	public List<AdminPermission> getAdminPermissionByUid(int uid) {
//		return adminPermissionMapper.selectPermissionByUid(uid);
//	}
//	
//	public List<AdminPermission> getAdminPermissionByRoleId(int roleId) {
//		return adminPermissionMapper.selectPermissionByRoleId(roleId);
//	}
//	
////	public List<AdminPermission> listAdminPermission(Integer type, String name, String comment, String operation) {
////		Map<String, Object> params = new HashMap<String, Object>();
////		params.put("type", type);
////		params.put("name", name);
////		params.put("comment", comment);
////		params.put("operation", operation);
////		return AdminDao.getAdminPermission(params);
////	}
//	
//	public int insertAdminPermission(String name, String comment, String operation, PermissionType type) {
//		AdminPermission record = new AdminPermission();
//		record.setName(name);
//		if(comment != null){
//			record.setComment(comment);
//		}
//		record.setOperation(operation);
//		record.setType(type.value());
//		
//		return adminPermissionMapper.insert(record);
//	}
//	
//	public boolean deleteAdminPermission(int id) {
//		int row = adminPermissionMapper.deleteByPrimaryKey(id);
//		if(row == 0){
//			return false;
//		}
//		row = adminRolePermissionMapper.deleteByPermissionId(id);
//		return true;
//	}
//	
//	public int updateAdminPermission(Integer pid, String name, String comment, String operation, PermissionType type) {
//		
//		AdminPermission record = new AdminPermission();
//		record.setId(pid);
//		record.setName(name);
//		record.setComment(comment);
//		record.setOperation(operation);
//		record.setType(type.value());
//		
//		return adminPermissionMapper.updateByPrimaryKey(record);
//	}
//	
//	public List<AdminPermission> selectPermissionByParams(String name, String operation, RowBounds bounds){
//		HashMap<String, Object> record = new HashMap<String, Object>();
//		
//		if(name != null){
//			record.put("name", name);
//		}
//		if(operation != null){
//			record.put("operation", operation);
//		}
//		
//		return adminPermissionMapper.selectPermissionByParams(record, bounds);
//	}
//	
//	public List<AdminPermission> selectAllPermissions(){
//		return adminPermissionMapper.selectAllPermissions();
//	}
//	public Integer countOfSelectPermissionByParams(String name, String operation){
//		HashMap<String, Object> record = new HashMap<String, Object>();
//		
//		if(name != null){
//			record.put("name", name);
//		}
//		if(operation != null){
//			record.put("operation", operation);
//		}
//		return adminPermissionMapper.countOfSelectByParams(record);
//	}
//	//---------------------------------------------------------------------------------------------------------------
//	public void insertAdminOperLog(AdminOperLog operLog) {
//		adminOperLogMapper.insert(operLog);
//	}
//	
//	public List<AdminOperLog> selectOperLogByParams(Integer id, Integer uid, Date beginTime, Date endTime, RowBounds bounds){
//		HashMap<String, Object> record = new HashMap<String, Object>();
//		
//		if(id != null){
//			record.put("id", id);
//		}
//		if(uid != null){
//			record.put("uid", uid);
//		}
//		if(beginTime != null){
//			record.put("beginTime", beginTime);
//		}
//		if(endTime != null){
//			record.put("endTime", endTime);
//		}
//		
//		return adminOperLogMapper.selectByParams(record, bounds);
//	}
//	
//	public Integer countOfSelectOperLogByParams(Integer id, Integer uid, Date beginTime, Date endTime){
//		HashMap<String, Object> record = new HashMap<String, Object>();
//		
//		if(id != null){
//			record.put("id", id);
//		}
//		if(uid != null){
//			record.put("uid", uid);
//		}
//		if(beginTime != null){
//			record.put("beginTime", beginTime);
//		}
//		if(endTime != null){
//			record.put("endTime", endTime);
//		}
//		return adminOperLogMapper.countOfSelectByParams(record);
//	}
////	
////	public Pagination pagiQueryAdminOperLog(Long uid, Date beginCreateTime, Date endCreateTime, int pageIndex, int pageSize) {
////		int startIndex = (pageIndex-1)*pageSize;
////		Map<String, Object> params = new HashMap<String, Object>();
////		params.put("uid", uid);
////		params.put("beginCreateTime", beginCreateTime);
////		params.put("endCreateTime", endCreateTime);
////		params.put("startIndex", startIndex);
////		params.put("count", pageSize);
////		
////		List<AdminOperLog> list = AdminDao.getAdminOperLog(params);
////		int count = AdminDao.getAdminOperLogCount(params);
////		
////		return new Pagination(count, pageIndex, pageSize, list);
////	}
	
	/**
	 * 使用cookie登录
	 * @param request
	 * @return
	 */
	public boolean checkLoginByCookie(HttpServletRequest request){
		String cookieUser = CookieUtil.getCookie(request, CookieUtil.COOKIE_USER_ID);
		Integer userInteger = null;
		if (cookieUser != null) {
			userInteger = Integer.valueOf(cookieUser);
		}
		String cookieKey = CookieUtil.getCookie(request, CookieUtil.COOKIE_USER_KEY);
		String expireTime = CookieUtil.getCookie(request, CookieUtil.COOKIE_EXPIRE_TIME);
		if (userInteger == null || StringUtil.isEmpty(cookieKey) || StringUtil.isEmpty(expireTime)) {
			return false; ///如果cookie中有一个为空则不通过
		}
		User user = getUser(userInteger);
		if (user == null) {
			return false;
		}
		String password = user.getPassword();
		if (!CookieUtil.generateUserKey(password, expireTime).equals(cookieKey)) {   ///验证时生成key
			return false;
		}
		Long timeLong = Long.parseLong(expireTime);
		if (timeLong == null || System.currentTimeMillis() > timeLong) {
			return false;
		}
		request.getSession().setAttribute(AuthUtil.USER_KEY, userInteger);
		return true;

	}
	
}
