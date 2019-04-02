package eu.toon.dao;


import eu.toon.models.Range;
import eu.toon.models.RangeAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class AdviceRangeDAO {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AdviceRangeDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public RangeAdvice addRangeAdvice(RangeAdvice rangeAdvice) {
        String sql = "insert into RangeAdvice(min, max, advice) values(?,?,?)";

        jdbcTemplate.update(sql, new Object[]{rangeAdvice.getRange().getMin(), rangeAdvice.getRange().getMax(), rangeAdvice.getAdvice()});

        return rangeAdvice;
    }

    public List<RangeAdvice> getRangeAdvices() {
        String sql = "select * from RangeAdvice";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
           RangeAdvice rangeAdvice = new RangeAdvice();
            Range range = new Range(rs.getInt(1), rs.getInt(2));
           rangeAdvice.setRange(range);
           rangeAdvice.setAdvice(rs.getString(3));
           return rangeAdvice;
        });
    }

    public RangeAdvice updateRangeAdvice(RangeAdvice rangeAdvice) {
        String sql = "update RangeAdvice set advice = ? where min  =? and max=?";

        jdbcTemplate.update(sql, new Object[]{rangeAdvice.getAdvice(), rangeAdvice.getRange().getMin(), rangeAdvice.getRange().getMax()});

        return rangeAdvice;
    }

    public String getAdvice(double cityTem) {
        String sql = "select advice from RangeAdvice where min <= ? and max >= ?";

        List<String> returnList = jdbcTemplate.query(sql, new Object[]{cityTem, cityTem}, (rs, rowNum) -> {
            return rs.getString(1);
        });

        return returnList != null && returnList.size() > 0 ? returnList.get(0) : "";
    }

    public Optional<RangeAdvice> getRangeAdvice(String range, String advice) {
        String sql = "select * from RangeAdvice where advice = ?";
        String sql1 = "Select * from RangeAdvice where min <= ? and max >= ?";

        List<RangeAdvice> rangeAdviceList = null;
        if(StringUtils.isEmpty(range) && !StringUtils.isEmpty(advice)) {

            rangeAdviceList = jdbcTemplate.query(sql, new Object[]{advice}, (rs, rowNum) -> {
               RangeAdvice rangeAdvice = new RangeAdvice();
               rangeAdvice.setRange(new Range(rs.getDouble(1), rs.getDouble(2)));
               rangeAdvice.setAdvice(rs.getString(3));
               return rangeAdvice;
            });
        } else {
            String[] rangeArr = range.split(",");
            rangeAdviceList = jdbcTemplate.query(sql1, new Object[]{rangeArr[0],rangeArr[1]}, (rs, rowNum) -> {
                RangeAdvice rangeAdvice = new RangeAdvice();
                rangeAdvice.setRange(new Range(rs.getDouble(1), rs.getDouble(2)));
                rangeAdvice.setAdvice(rs.getString(3));
                return rangeAdvice;
            });
        }

        return rangeAdviceList != null && rangeAdviceList.size() > 0 ? Optional.of(rangeAdviceList.get(0)) : Optional.empty();
    }
}
