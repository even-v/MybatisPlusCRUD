package cn.youchisoft.mybatis;

import java.util.ArrayList;
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
import cn.youchisoft.mybatisplus.mapper.UserMapper;
import cn.youchisoft.mybatisplus.model.User;

/**
 * 测试MybatisPlus BaseMapper中的各种方法
 * 
 * @author jiangjingwei@yysoft.org.cn
 * @Package cn.youchisoft.mybatis
 * @version Nov 21, 2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	/************************** 插入记录-开始 *******************************/
	/**
	 * 测试插入一条记录
	 */
	@Test
	public void testInsert() {
		User u = new User();
		initModel(u);
		u.setUsername("admin");
		u.setPassword("admin");

		/*** insert()方法传递一个实体对象，则可以插入该记录，返回受影响的行数 ***/
		int t = userMapper.insert(u);

		Assert.assertEquals(t, 1);
	}

	/************************** 插入记录-结束 *******************************/

	/************************** 删除记录-开始 *******************************/
	/**
	 * 测试根据主键删除记录
	 */
	@Test
	public void testDeleteById() {
		String id = "";

		/*** deleteById()方法传递一个实现Serializable接口的主键对象，则可以删除该记录，返回受影响的行数 ***/
		int t = userMapper.deleteById(id);

		Assert.assertEquals(t > 0, true);
	}

	/**
	 * 测试根据Map删除记录
	 */
	@Test
	public void testDeleteByMap() {
		/***
		 * map的key是表字段(非实体类属性)，value是key对应的条件；比如下面map表示删除username是admin的记录
		 ***/
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("username", "admin");

		/*** deleteByMap()方法传递一个map对象，则可以删除记录，返回受影响的行数 ***/
		int t = userMapper.deleteByMap(columnMap);

		Assert.assertEquals(t > 0, true);
	}

	/**
	 * 测试根据主键列表批量删除记录
	 */
	@Test
	public void testDeleteBatchIds() {
		/*** idList是表中主键的集合，idList不能为null或empty ***/
		List<String> idList = new ArrayList<>();
		idList.add("1");
		idList.add("2");

		/*** deleteBatchIds()方法传递id列表，则可以删除记录，返回受影响的行数 ***/
		int t = userMapper.deleteBatchIds(idList);

		Assert.assertEquals(t, 0);
	}

	/**
	 * 测试根据wrapper删除记录
	 */
	@Test
	public void testdelete() {
		/*** QueryWrapper是一个强大的条件构造器，此处只做简单使用，后会对QueryWrapper进行详细介绍 ***/
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		/*** eq()表示相等，第一个参数为数据库字段名(非实体类属性)，第二个参数为数据库字段值 ***/
		wrapper.eq("username", "zs");

		/*** delete()方法传递一个wrapper对象，则可以删除记录，返回受影响的行数 ***/
		int t = userMapper.delete(wrapper);

		Assert.assertEquals(t, 1);
	}

	/************************** 删除记录-结束 *******************************/

	/************************** 修改记录-开始 *******************************/
	/**
	 * 测试根据主键修改对象
	 */
	@Test
	public void testUpdateById() {
		User u = new User();
		initModel(u);
		u.setId("9de963f7-e84d-41a3-9d6b-d5643e888135");
		u.setUsername("");

		/***
		 * updateById()方法传递一个实体对象，则根据主键修改记录，返回受影响的行数； 实体对象中若属性为null则不做修改，为空会修改
		 ***/
		int t = userMapper.updateById(u);

		Assert.assertEquals(t, 1);
	}

	/**
	 * 测试根据whereEntity条件修改对象，该方法测试username是admin的把其密码改为666
	 */
	@Test
	public void testUpdate() {
		User u = new User();
		u.setPassword("666");

		UpdateWrapper<User> wrapper = new UpdateWrapper<>();
		wrapper.eq("username", "admin");

		/***
		 * update()方法传递一个wrapper对象，则根据条件修改记录，返回受影响的行数；
		 * 其中实体类是要修改成的结果，wrapper是匹配条件
		 ***/
		int t = userMapper.update(u, wrapper);

		Assert.assertEquals(t, 1);
	}

	/************************** 修改记录-结束 *******************************/

	/************************** 查询记录-开始 *******************************/

	/**
	 * 测试根据主键查询实体
	 */
	@Test
	public void testSelectById() {
		String id = "9de963f7-e84d-41a3-9d6b-d564388135";

		/*** selectById()方法传入一个主键，返回一个实体对象，若不存在记录则返回null ***/
		User u = userMapper.selectById(id);

		Assert.assertNull(u);
	}

	/**
	 * 测试根据主键列表查询实体列表
	 */
	@Test
	public void testSelectBatchIds() {
		List<String> idList = new ArrayList<>();
		idList.add("123");
		idList.add("321");

		/***
		 * selectById()方法传入一个主键列表，返回一个实体列表，主键列表(不能为 null 以及 empty)，若查询无结果则返回空集合
		 ***/
		List<User> userList = userMapper.selectBatchIds(idList);

		Assert.assertEquals(userList.size(), 0);
	}

	/**
	 * 测试根据Map查询实体列表
	 */
	@Test
	public void testSelectByMap() {
		/*** columnMap是表字段Map对象，key是实体字段名(非属性名)，value是字段值 ***/
		Map<String, Object> columnMap = new HashMap<>();
		/*** 查询username是admin的实体列表 ***/
		columnMap.put("username", "admin");

		/*** selectByMap()根据columnMap条件查询实体列表，若无查询结果则返回空集合 ***/
		List<User> userList = userMapper.selectByMap(columnMap);

		Assert.assertEquals(userList.size(), 0);
	}

	/**
	 * 测试查询一条记录；如果逻辑非唯一该方法不会自动替您 limit 1 你需要 wrapper.last("limit 1") 设置唯一性。
	 */
	@Test
	public void testSelectOne() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		/*** 设置唯一性，此时查询出唯一记录 ***/
		queryWrapper.last("limit 1");
		queryWrapper.eq("username", "admin");

		/*** selectOne()参数为QueryWrapper，参数可为null，根据条件查询一条记录 ***/
		User u = userMapper.selectOne(queryWrapper);

		Assert.assertNotNull(u);
	}

	/**
	 * 测试统计个数
	 */
	@Test
	public void testSelectCount() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", "admin");

		/*** selectCount()参数为QueryWrapper，参数可为null，根据条件统计个数 ***/
		Integer count = userMapper.selectCount(queryWrapper);

		Assert.assertEquals(count, new Integer(1));
	}

	/**
	 * 根据条件查询记录
	 */
	@Test
	public void testSelect() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", "admin");

		/*** selectList()参数为QueryWrapper，参数可为null，根据条件查询实体集合，若无结果则返回空集合 ***/
		List<User> userList = userMapper.selectList(queryWrapper);

		Assert.assertNotNull(userList);
	}

	/**
	 * 根据条件查询记录
	 */
	@Test
	public void testSelectMaps() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", "admin");

		/*** selectMaps()参数为QueryWrapper，参数可为null，根据条件查询实体集合，若无结果则返回空集合 ***/
		/*** 返回的结果是Map列表，map的key是字段名(非属性名)，value是字段值 ***/
		List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);

		Assert.assertNotNull(userList);
		System.out.println(userList);
	}

	/**
	 * 根据条件查询记录
	 */
	@Test
	public void testSelectObjs() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", "admin");

		/*** selectMaps()参数为QueryWrapper，参数可为null，根据条件查询实体集合，若无结果则返回空集合 ***/
		/*** 只返回第一个字段的值！！！ ***/
		List<Object> userList = userMapper.selectObjs(queryWrapper);

		Assert.assertNotNull(userList);
		System.out.println(userList);
	}

	/**
	 * 根据条件查询记录，并分页
	 */
	@Test
	public void testSelectPage() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", "admin");

		/*** Page是分页查询对象，实现IPage接口，下面构造方法表示当前页，每页工几条记录 ***/
		Page<User> page = new Page<>(1, 10);

		/***
		 * selectMaps()参数为IPage(RowBounds.DEFAULT)、QueryWrapper，参数可为null，
		 * 根据条件查询实体集合，若无结果则返回空集合
		 ***/
		IPage<User> pageList = userMapper.selectPage(page, queryWrapper);

		Assert.assertNotNull(pageList);
		List<User> userList = pageList.getRecords();

		/*** 当前页 ***/
		pageList.getCurrent();

		/*** 当前分页总页数 ***/
		pageList.getPages();

		/*** 每页显示条数 ***/
		pageList.getSize();

		/*** 当前分页总记录数 ***/
		pageList.getTotal();

		System.out.println(userList);
	}

	/**
	 * 根据 Wrapper 条件，查询全部记录（并翻页）
	 */
	@Test
	public void testSelectMapsPage() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", "admin");

		/*** Page是分页查询对象，实现IPage接口，下面构造方法表示当前页，每页工几条记录 ***/
		Page<User> page = new Page<>(1, 10);

		/***
		 * selectMaps()参数为IPage(RowBounds.DEFAULT)、QueryWrapper，参数可为null，
		 * 根据条件查询实体集合，若无结果则返回空集合，返回Map，key是字段名(非属性名)，value是字段值
		 ***/
		IPage<Map<String, Object>> pageList = userMapper.selectMapsPage(page, queryWrapper);

		Assert.assertNotNull(pageList);
		List<Map<String, Object>> userList = pageList.getRecords();

		System.out.println(userList);
	}

	/************************** 查询记录-结束 *******************************/
	public void initModel(User u) {
		Date now = new Date();
		u.setCreateTime(now);
		u.setDeleted(false);
		u.setCreateUser("jw");
		u.setId(UUID.randomUUID().toString());
		u.setLocked(false);
		u.setUpdateTime(now);
		u.setUpdateUser("jw");
	}
}
