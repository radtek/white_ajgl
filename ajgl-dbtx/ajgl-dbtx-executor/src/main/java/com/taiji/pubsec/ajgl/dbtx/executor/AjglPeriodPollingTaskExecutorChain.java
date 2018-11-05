/**
 * 
 */
package com.taiji.pubsec.ajgl.dbtx.executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.taiji.pubsec.common.tool.mission.executor.core.PeriodTaskExecutorChain;
import com.taiji.pubsec.common.tool.mission.task.core.MainPeriodTask;
import com.taiji.pubsec.common.tool.mission.task.core.MainTask;
import com.taiji.pubsec.common.tool.mission.task.core.PeriodTaskExecutor;
import com.taiji.pubsec.common.tool.mission.task.core.Task;
import com.taiji.pubsec.common.tool.mission.task.service.MainPeriodTaskService;

/**
 * 重载了超类，使task的获取分页处理以及通过jdbc来获取任务信息，避免任务过多造成内存消耗太大
 * @author yucy
 *
 */
public class AjglPeriodPollingTaskExecutorChain implements PeriodTaskExecutorChain {
	private static final Logger LOGGER = LoggerFactory.getLogger(AjglPeriodPollingTaskExecutorChain.class);
	
	JdbcTemplate jdbcTemplate;
	
	protected Map<String, PeriodTaskExecutor> taskExecutors = new HashMap<String, PeriodTaskExecutor>();

	private Integer pageSize = 100;
	
	@Override
	public void donChain() {
		
		LOGGER.debug("AbstractPeriodPollingTaskExecutorChain开始执行");
		Integer pageno = 0;
		List<MainPeriodTask> tasks = new ArrayList<MainPeriodTask>();
		
		do{
			tasks = findMainPeriodTasksByStatus(MainTask.MAINTASK_STATUS_ON, pageno, pageSize);
			for(Task task:tasks){
				for (Entry<String, PeriodTaskExecutor> entry : taskExecutors.entrySet()) {
					LOGGER.debug("AbstractPeriodPollingTaskExecutorChain执行到：" + entry.getValue().getClass().getName());
					if(entry.getValue().execute(task)){
						break ;
					}
				}
			}
			pageno = pageno +1;
		}while(tasks.size() >= pageSize);
		
	}

	private List<MainPeriodTask> findMainPeriodTasksByStatus(String status, Integer pageNo, Integer paseSize) {
		String sql = "select p.id from t_mission_main_period_task as p, t_mission_main_task as m where p.mission_main_task_id = m.id and m.status = ? limit ?,?";
		
		Integer start = pageNo * paseSize;
		return jdbcTemplate.query(sql, new Object[]{status, start, paseSize}, new RowMapper<MainPeriodTask>(){

			@Override
			public MainPeriodTask mapRow(ResultSet rs, int rowNum) throws SQLException {
				MainPeriodTask task = new MainPeriodTask();
				task.setId(rs.getString("id"));
				return task;
			}
			
		});
	}
	
	@Override
	public void setTaskExecutors(Map<String, PeriodTaskExecutor> taskExecutors) {
		this.taskExecutors.putAll(taskExecutors);
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Task> findNotExecutedPeriodTasks() {
		return null;
	}

	@Override
	public void setMainPeriodTaskService(MainPeriodTaskService mainPeriodTaskService) {
	}
	
	
	public static void main(String[] args) throws ParseException {
        Date t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").parse("2017-03-30 21:07:19,349");
		long a = t.getTime() - 1490879100000L;
		System.out.println(a);
	}

}
