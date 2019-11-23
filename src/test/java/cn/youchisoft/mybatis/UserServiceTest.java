package cn.youchisoft.mybatis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.youchisoft.mybatisplus.App;
import cn.youchisoft.mybatisplus.model.User;
import cn.youchisoft.mybatisplus.service.UserService;

/**
 * 测试MybatisPlus IService中的各种方法
 * 
 * @author jiangjingwei@yysoft.org.cn
 * @Package cn.youchisoft.mybatis
 * @version Nov 21, 2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserServiceTest {

	@Autowired
	private UserService userService;

	/************************** 插入记录-开始 *******************************/

	/**
	 * 插入一条记录，本质调用Mapper的insert方法
	 */
	@Test
	public void testSave() {
		User u = new User();
		initModel(u);
		u.setUsername("jingwei");
		u.setPassword("jingwei");

		/*** save()方法传入实体对象，mybatisPlus内部调用mapper的insert方法插入记录，返回是否插入成功 ***/
		boolean flag = userService.save(u);

		Assert.assertTrue(flag);
	}

	/**
	 * 批量插入数据，MybatisPlus中有2个批量插入的方法，一个需要设置批量插入数量，一个默认有批量插入数量；
	 * 本质上默认有批量插入数量的方法调用了需要设置批量插入数量的方法
	 */
	@Test
	public void testSaveBatch() {
		/*** 初始化数据 ***/
		List<User> userList = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			User u = new User();
			initModel(u);
			u.setUsername("zs" + i);
			u.setPassword("zs" + i);
			userList.add(u);
		}

		/*** saveBatch方法传入一个实体集合，并设置批量插入数量是5，则以5个实体为同一批次插入 ***/
		boolean flag = userService.saveBatch(userList, 5);
		/*** saveBatch方法传入一个实体集合，不设置批量插入数量，则默认以1000个实体为同一批次插入 ***/
		// boolean flag=userService.saveBatch(userList);
		Assert.assertTrue(flag);
	}

	/**
	 * 保存或更新记录，若不存在则保存，若存在则更新记录
	 */
	@Test
	public void testSaveOrUpdate() {
		User u = new User();
		u.setId("123");
		u.setUsername("xiaoyu");
		u.setPassword("xiaoyu");

		/*** saveOrUpdate方法传递一个实体参数，若不存在就保存，存在则更新记录；是否存在只根据主键来判断 ***/
		boolean flag = userService.saveOrUpdate(u);

		Assert.assertTrue(flag);
	}

	/**
	 * 批量保存或更新记录，若不存在则保存，若存在则更新记录
	 */
	@Test
	public void testSaveOrUpdateBatch() {
		List<User> userList = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			User u = new User();
			initModel(u);
			u.setUsername("zs" + i);
			u.setPassword("zs" + i);
			userList.add(u);
		}

		/***
		 * saveOrUpdateBatch方法传递一个实体集合参数，若不存在就保存，存在则更新记录；是否存在只根据主键来判断
		 * ；第二个参数为批量插入数量
		 ***/
		boolean flag = userService.saveOrUpdateBatch(userList, 5);

		// boolean flag = userService.saveOrUpdateBatch(userList);

		Assert.assertTrue(flag);
	}

	/************************** 插入记录-结束 *******************************/

	/************************** 删除记录-开始 *******************************/

	/**
	 * 测试根据主键删除实体
	 */
	@Test
	public void testRemoveById() {
		String id = "a3e7ce98-d210-44a2-8d13-3cd6e78d1d82";

		/*** removeById()传入主键参数，若存在则删除并返回true，删除失败或不存在则返回false ***/
		boolean flag = userService.removeById(id);

		Assert.assertTrue(flag);
	}

	/**
	 * 测试根据主键列表删除实体
	 */
	@Test
	public void testRemoveByIds() {
		List<String> idList = new ArrayList<>();
		idList.add("6e550f68-2f01-4c5f-8a31-ac8b49a08ddb");
		idList.add("5bb87602-02cb-4acf-a256-a4b3d0abe57e");

		/*** removeByIds()传入id列表参数，参数不能为empty或null；若id列表全部不存在则返回false ***/
		boolean flag = userService.removeByIds(idList);
		Assert.assertTrue(flag);
	}

	/**
	 * 测试根据Map条件删除实体
	 */
	@Test
	public void testRemoveByMap() {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("username", "zs10");

		/*** removeByMap()传入Map参数，根据Map的条件进行删除 ***/
		boolean flag = userService.removeByMap(columnMap);

		Assert.assertTrue(flag);
	}

	/**
	 * 测试根据Wrapper条件删除实体
	 */
	@Test
	public void testRemove() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.like("username", "zs");

		/*** remove()方法传递一个Wrapper参数，可为null，若一条记录都未删除则返回false ***/
		boolean flag = userService.remove(wrapper);

		Assert.assertTrue(flag);

	}

	/************************** 删除记录-结束 *******************************/

	/************************** 修改记录-开始 *******************************/

	/**
	 * 测试根据主键修改记录，只修改参数对象中有值的字段，无值则不修改，若主键不存在则修改失败
	 */
	@Test
	public void testUpdateById() {
		User u = new User();
		Date now = new Date();
		u.setId("aee71958-56bd-4165-96e7-455657f50657");
		u.setUpdateTime(now);

		boolean flag = userService.updateById(u);

		Assert.assertTrue(flag);
	}

	/**
	 * 测试批量修改
	 */
	@Test
	public void testUpdateBatchById() {
		List<User> userList = new ArrayList<>();
		User u = new User();
		Date now = new Date();
		u.setId("aee71958-56bd-4165-96e7-455657f50657");
		u.setUpdateTime(now);
		userList.add(u);

		/*** updateBatchById()第一个参数为待批修改的数据列表，第二个参数为批量修改数量 ***/
		boolean flag = userService.updateBatchById(userList, 5);

		// boolean flag = userService.updateBatchById(userList);

		Assert.assertTrue(flag);
	}

	/**
	 * 测试根据Wrapper条件进行修改
	 */
	@Test
	public void testUpdate() {
		UpdateWrapper<User> wrapper = new UpdateWrapper<>();
		wrapper.like("username", "admin");

		User u = new User();
		u.setUpdateTime(new Date());

		/***
		 * update()第一个参数为想要修改的字段及值，第二个参数为修改条件；当前把username是admin的修改时间改为当前时间
		 ***/
		boolean flag = userService.update(u, wrapper);

		Assert.assertTrue(flag);
	}

	/************************** 修改记录-结束 *******************************/

	/************************** 查询记录-开始 *******************************/

	/**
	 * 测试根据主键获取实体
	 */
	@Test
	public void testGetById() {
		String id = "123";

		/*** 根据主键获取对象，若不存在则返回null ***/
		User u = userService.getById(id);

		Assert.assertNull(u);
	}

	/**
	 * 根据主键列表查询对象
	 */
	@Test
	public void testListByIds() {
		List<String> idList = new ArrayList<>();
		idList.add("123");
		idList.add("644747ce-719e-496c-a30a-5d80af095521");

		/*** listByIds根据主键列表获取实体列表 ***/
		Collection<User> userList = userService.listByIds(idList);

		Assert.assertEquals(userList.size(), 1);
	}

	/**
	 * 根据Wrapper，查询一条记录，若有多个则返回第一个(多个不抛异常时)
	 */
	@Test
	public void testGetOne() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("deleted", false);

		/***
		 * getOne()第一个参数为查询参数，第二个参数为是否抛出异常；若查询多个且第二个参数为true则会有异常；getOne(wrapper,
		 * true)等价于getOne(wrapper)
		 ***/
		User u = userService.getOne(wrapper, false);

		Assert.assertNotNull(u);
	}

	/**
	 * 根据Wrapper条件查询map记录
	 */
	@Test
	public void testGetMap() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq("username", "admin");

		/***
		 * getMap()内部调用Mapper的selectMaps()方法，返回List<Map<String, Object>>
		 * ，该方法返回列表的第一个元素
		 ***/
		Map<String, Object> map = userService.getMap(queryWrapper);

		Assert.assertNotNull(map);
	}

	/**
	 * 根据Wrapper条件统计数量
	 */
	@Test
	public void testCount() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.eq("username", "admin");

		/*** 根据wrapper条件统计记录数 ***/
		int t = userService.count(queryWrapper);

		Assert.assertEquals(t, 1);
	}

	/**
	 * 根据Wrapper条件查询列表
	 */
	@Test
	public void testList() {
		QueryWrapper<User> wrapper = new QueryWrapper<User>();
		wrapper.eq("username", "admin");

		/*** list()传入一个wrapper参数，wrapper可以为null或者不传入 ***/
		List<User> userList = userService.list(wrapper);
		// List<User> userList=userService.list(null);
		// List<User> userList=userService.list();

		Assert.assertEquals(1, userList.size());
	}

	/**
	 * 测试分页查询
	 */
	@Test
	public void testPage() {
		IPage<User> page = new Page<>(1, 10);

		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("deleted", false);

		/*** page()方法第一个参数是Ipage对象且必须传递，第二个参数是查询对象，可以不传递，不传递时查全部 ***/
		page = userService.page(page, wrapper);

		List<User> userList = page.getRecords();

		Assert.assertEquals(2, userList.size());
	}

	/**
	 * 查询列表
	 */
	@Test
	public void testListMaps() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("username", "admin");

		/*** listMaps返回一个Map 集合，map中key是字段，value是值；该方法参数是wrapper，也可以不传递参数 ***/
		List<Map<String, Object>> list = userService.listMaps(wrapper);
		// List<Map<String, Object>> list = userService.listMaps();

		Assert.assertEquals(1, list.size());
		System.out.println(list);
	}

	/************************** 查询记录-结束 *******************************/

	public void initModel(User u) {
		Date now = new Date();
		u.setCreateTime(now);
		u.setDeleted(false);
		u.setCreateUser("admin");
		u.setId(UUID.randomUUID().toString());
		u.setLocked(false);
		u.setUpdateTime(now);
		u.setUpdateUser("admin");
	}
}
