package com.bet.daos;

import java.util.Map;

import com.bet.orms.MatchInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IMatchInfoDao extends IAbstractDao<MatchInfo>
{
	public static final String ID = "id";
	public static final String PHASE_ID = "phaseId";
	public static final String OUT_ID = "outId";
	public static final String MATCH_NAME = "matchName";
	public static final String MATCH_NUM = "matchNum";
	public static final String MATCH_DATE = "matchDate";
	public static final String TIME_ENDSALE = "timeEndsale";
	public static final String HOME_TEAM_ID = "homeTeamId";
	public static final String HOME_TEAM = "homeTeam";
	public static final String HOME_TEAM_PIC_URL = "homeTeamPicUrl";
	public static final String AWAY_TEAM_ID = "awayTeamId";
	public static final String AWAY_TEAM = "awayTeam";
	public static final String AWAY_TEAM_PIC_URL = "awayTeamPicUrl";
	public static final String VIDEO_URL = "videoUrl";
	public static final String FINAL_SCORE1 = "finalScore1";
	public static final String FINAL_SCORE2 = "finalScore2";
	public static final String UPDATE_FINAL_SCORE_DATE_LONG = "updateFinalScoreDateLong";
	public static final String HALF_SCORE1 = "halfScore1";
	public static final String HALF_SCORE2 = "halfScore2";
	public static final String UPDATE_HALF_SCORE_DATE_LONG = "updateHalfScoreDateLong";
	public static final String HANDICAP = "handicap";
	public static final String HALF_RESULT_STATUS = "halfResultStatus";
	public static final String FINAL_RESULT_STATUS = "finalResultStatus";
	public static final String HALF_PROFIT_STATUS = "halfProfitStatus";
	public static final String FINAL_PROFIT_STATUS = "finalProfitStatus";
	public static final String LEADER_PROFIT_STATUS = "leaderProfitStatus";
	public static final String SORT_INDEX = "sortIndex";
	public static final String STATUS = "status";
	public static final String ADD_DATE_LONG = "addDateLong";
	public static final String UPDATE_DATE_LONG = "updateDateLong";

	public void deleteMatchInfoByPhaseId(String phaseId) throws HibernateJsonResultException;

	public MatchInfo getMatchInfoByPhaseId(String phaseId) throws HibernateJsonResultException;

	public long updateValueByPhaseId(String phaseId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public void deleteMatchInfoByOutId(String outId) throws HibernateJsonResultException;

	public MatchInfo getMatchInfoByOutId(String outId) throws HibernateJsonResultException;

	public long updateValueByOutId(String outId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
