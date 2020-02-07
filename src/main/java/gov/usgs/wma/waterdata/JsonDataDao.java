package gov.usgs.wma.waterdata;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class JsonDataDao {

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Value("classpath:sql/approvals.sql")
	private Resource approvals;

	@Value("classpath:sql/gapTolerances.sql")
	private Resource gapTolerances;

	@Value("classpath:sql/grades.sql")
	private Resource grades;

	@Value("classpath:sql/interpolationTypes.sql")
	private Resource interpolationTypes;

	@Value("classpath:sql/methods.sql")
	private Resource methods;

	@Value("classpath:sql/points.sql")
	private Resource points;

	@Value("classpath:sql/uniqueId.sql")
	private Resource uniqueId;

	public String getUniqueId(Long jsonDataId) throws IOException {
		String sql = new String(FileCopyUtils.copyToByteArray(uniqueId.getInputStream()));
		return jdbcTemplate.queryForObject(sql,
				new Object[] {jsonDataId},
				String.class
			);
	}

	public void doApprovals(Long jsonDataId) throws IOException {
		doUpdate(jsonDataId, approvals);
	}

	public void doGapTolerances(Long jsonDataId) throws IOException {
		doUpdate(jsonDataId, gapTolerances);
	}

	public void doGrades(Long jsonDataId) throws IOException {
		doUpdate(jsonDataId, grades);
	}

	public void doInterpolationTypes(Long jsonDataId) throws IOException {
		doUpdate(jsonDataId, interpolationTypes);
	}

	public void doMethods(Long jsonDataId) throws IOException {
		doUpdate(jsonDataId, methods);
	}

	public void doPoints(Long jsonDataId) throws IOException {
		doUpdate(jsonDataId, points);
	}

	protected void doUpdate(Long jsonDataId, Resource resource) throws IOException {
		String sql = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
		jdbcTemplate.update(sql, jsonDataId);
	}
}
